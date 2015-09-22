package mystore.salesOrderMgmt.controls;

//import model.SalesOrder;
/* This boundary service should expose course grained methods to the client */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mystore.salesOrderMgmt.controls.api.DiscountCalculator;
import mystore.salesOrderMgmt.controls.api.ShippingCalculator;
import mystore.salesOrderMgmt.controls.api.TaxCalculator;
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.SalesOrder;
import mystore.salesOrderMgmt.entities.SalesOrderLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* This boundary service should expose course grained methods to the client */
@Service
public class SalesOrderService implements Serializable {

	private static final long serialVersionUID = -3072083708013257602L;

	@PersistenceContext
	EntityManager emgr;
	
	// Inject other EJB services here as required to support complex transactions
	@Autowired
	TaxCalculator taxCalculator;
	
	@Autowired
	ShippingCalculator shippingCalculator;
	
	@Autowired
	DiscountCalculator discountCalculator;
	
	@Autowired
	CatalogService catalogService;
	
	@Transactional
	public SalesOrder createSalesOrder(SalesOrder salesOrder) throws Exception {
				
		salesOrder.setOrderNumber(generateOrderNumber(salesOrder));
		salesOrder.setSalesOrderStatus("NEW");
		salesOrder.setSalesOrderDate(new Date());
		
		// Go through the line items and create a new array of line items by assigning the catalogItem directly
		// This is required else the many-to-one relationship between SalesOrderLine and CatalogItem does not get carried through
		// This is required else the many-to-one relationship between SalesOrderLine and CatalogItem does not get 
		// carried through to the database
		List<SalesOrderLine> salesOrderLineItems = salesOrder.getLineItems();
		List<SalesOrderLine> newLineItems = new ArrayList<SalesOrderLine>();
		for (int i = 0; i < salesOrderLineItems.size(); i++) {
			CatalogItem catalogItem = catalogService.retrieveCatalogItem(salesOrderLineItems.get(i).getCatalogItemUid());
			SalesOrderLine lineItem = new SalesOrderLine(salesOrder, catalogItem, salesOrderLineItems.get(i).getItemQuantity());
			lineItem.calculateLineItem();
			newLineItems.add(lineItem);
		}
		salesOrder.setLineItems(newLineItems);				
		calculateSalesOrder(salesOrder);

		emgr.persist(salesOrder);
		emgr.flush(); 
		return salesOrder;
	}
	
	@Transactional
	public SalesOrder addLineItem(SalesOrder salesOrder, SalesOrderLine lineItem) {
		lineItem.setSalesOrder(salesOrder);
		lineItem.calculateLineItem();
		emgr.persist(lineItem);
		salesOrder.getLineItems().add(lineItem);
		calculateSalesOrder(salesOrder);
		emgr.flush();
		return salesOrder;
	}

	@Transactional
	public SalesOrder addLineItem(SalesOrder salesOrder, CatalogItem catItem, int itemQuantity) {
		SalesOrderLine lineItem = new SalesOrderLine(salesOrder, catItem, itemQuantity);
		emgr.persist(lineItem);   
		salesOrder.getLineItems().add(lineItem);
		calculateSalesOrder(salesOrder);
     	emgr.flush();
		return salesOrder;
	}

	@Transactional
	public SalesOrder retrieveSalesOrder(Integer orderUid) {
		
		System.out.println("Top of SalesOrderService.retrieveSalesOrder with orderUid of: " + orderUid);
		
		Query query = emgr.createNamedQuery("SalesOrder.byId");
		query.setParameter("salesOrderUid", orderUid);
		SalesOrder salesOrder = (SalesOrder) query.getSingleResult();

		return salesOrder;
	}	

	@Transactional
	public SalesOrder removeSalesOrderLine(SalesOrder salesOrder, SalesOrderLine salesOrderLine) throws Exception {

		salesOrder = emgr.find(SalesOrder.class, (Integer) salesOrder.getSalesOrderUid(), LockModeType.PESSIMISTIC_WRITE);		
		salesOrderLine = emgr.find(SalesOrderLine.class, (Integer) salesOrderLine.getSalesOrderLineUid(), LockModeType.PESSIMISTIC_WRITE);

		List<SalesOrderLine> lineItems = salesOrder.getLineItems();
		if (lineItems.contains(salesOrderLine)) {
			salesOrder.getLineItems().remove(salesOrderLine);
		} else {
			throw new Exception("ERROR: The line item provided to SalesOrderService.removeSalesOrderLine was not associated with the sales order provided");
		}		
		calculateSalesOrder(salesOrder);
		emgr.merge(salesOrder);
		
		return salesOrder;
	}
	
	@Transactional
	public SalesOrder cancelSalesOrder(SalesOrder salesOrder) {
		// Update salesOrderStatus to "Cancelled" and send email to customer with confirmation of cancellation
		salesOrder = emgr.find(SalesOrder.class, salesOrder.getSalesOrderUid(), LockModeType.PESSIMISTIC_WRITE);
		salesOrder.setSalesOrderStatus("Cancelled");
		emgr.merge(salesOrder);
		// Send email via the email service (TBD)
		return salesOrder;
	}
		
	private String generateOrderNumber(SalesOrder salesOrder) {
		// Simplified for prototyping purposes
		return String.valueOf(new Date().getTime());
	}
	
	public SalesOrder calculateSalesOrder(SalesOrder salesOrder) {
		BigDecimal runningTotal = new BigDecimal(0);
			
		for (SalesOrderLine lineItem : salesOrder.getLineItems()) {
			runningTotal = runningTotal.add(lineItem.getExtendedPrice());
		}
		salesOrder.setSubTotal(runningTotal);
		salesOrder.setSalesTax(taxCalculator.getTaxRate(salesOrder.getBillToZipCode()).multiply(salesOrder.getSubTotal()) );
		salesOrder.setShipping(shippingCalculator.getShippingCost(salesOrder.getBillToZipCode(), salesOrder.getSubTotal()));
		salesOrder.setDiscount(discountCalculator.getDiscount(salesOrder.getPromoCode()));
		salesOrder.setTotal( salesOrder.getSubTotal().subtract(salesOrder.getDiscount()).add(salesOrder.getSalesTax()).add(salesOrder.getShipping()));
		
		return salesOrder;

	}
	
}
