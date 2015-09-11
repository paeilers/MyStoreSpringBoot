package mystore.salesOrderMgmt.controls;

//import model.SalesOrder;
/* This boundary service should expose course grained methods to the client */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	@Transactional
	public SalesOrder createSalesOrder(SalesOrder salesOrder) throws Exception {
		salesOrder.setOrderNumber(generateOrderNumber(salesOrder));
		salesOrder.setSalesOrderStatus("NEW");
		salesOrder.setSalesOrderDate(new Date());

		List<SalesOrderLine> lineItems = salesOrder.getLineItems();
		if (lineItems != null) {
			for (int i=0; i < lineItems.size(); i++) {
				lineItems.get(i).calculateLineItem();
			}
		}	

		calculateSalesOrder(salesOrder);

		emgr.persist(salesOrder);
		emgr.flush(); 
		return salesOrder;
	}
	
	@Transactional
	public SalesOrder addLineItem(SalesOrder salesOrder, SalesOrderLine lineItem) {
		lineItem.setSalesOrder(salesOrder);
		calculateLineItem(lineItem);
		emgr.persist(lineItem);
		salesOrder.getLineItems().add(lineItem);
		emgr.flush();
		return salesOrder;
	}

	@Transactional
	public SalesOrder addLineItem(SalesOrder salesOrder, CatalogItem catItem, int itemQuantity) throws Exception {
		SalesOrderLine lineItem = new SalesOrderLine();
		lineItem.setSalesOrder(salesOrder);
		lineItem.setCatalogItem(catItem);
		lineItem.setItemQuantity(itemQuantity);
		calculateLineItem(lineItem);
		emgr.persist(lineItem);   
		salesOrder.getLineItems().add(lineItem);
		calculateSalesOrder(salesOrder);
     	emgr.flush();
		return salesOrder;
	}

	public SalesOrder retrieveSalesOrder(Integer orderUid) {
		Query salesOrderQuery = emgr.createQuery("Select so from SalesOrder so where so.salesOrderUid = :orderUid");
		salesOrderQuery.setParameter("orderUid", orderUid);
		SalesOrder salesOrder = null;
		try {
			salesOrder = (SalesOrder) salesOrderQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return salesOrder;
	}	

	@Transactional
	public SalesOrder removeSalesOrderLine(SalesOrder salesOrder, SalesOrderLine salesOrderLine) throws Exception {
		
		SalesOrder managedSalesOrder;
		SalesOrderLine managedSalesOrderLine;
		
		managedSalesOrder = emgr.find(SalesOrder.class, (Integer) salesOrder.getSalesOrderUid(), LockModeType.PESSIMISTIC_WRITE);		
		managedSalesOrderLine = emgr.find(SalesOrderLine.class, (Integer) salesOrderLine.getSalesOrderLineUid(), LockModeType.PESSIMISTIC_WRITE);
		List<SalesOrderLine> lineItems = managedSalesOrder.getLineItems();
		if (lineItems.contains(managedSalesOrderLine)) {
			managedSalesOrder.getLineItems().remove(managedSalesOrderLine);
		} else {
			throw new Exception("ERROR: The line item provided to SalesOrderService.removeSalesOrderLine was not associated with the sales order provided");
		}		
		calculateSalesOrder(managedSalesOrder);
		emgr.merge(managedSalesOrder);
		salesOrder = managedSalesOrder;
		emgr.flush();
		return salesOrder;
	}
	
	public SalesOrderLine retrieveSalesOrderLine(Integer salesOrderLineUid) {
		Query salesOrderLineQuery = emgr.createQuery("");
		salesOrderLineQuery.setParameter("salesOrderLineUid", salesOrderLineUid);
		SalesOrderLine salesOrderLine = null;
		salesOrderLine = (SalesOrderLine) salesOrderLineQuery.getSingleResult();
		return salesOrderLine;
	}
	
	@Transactional
	public SalesOrder cancelSalesOrder(SalesOrder salesOrder) {
		// Update salesOrderStatus to "Cancelled" and send email to customer with confirmation of cancellation
		SalesOrder managedSalesOrder = emgr.find(SalesOrder.class, salesOrder.getSalesOrderUid(), LockModeType.PESSIMISTIC_WRITE);
		managedSalesOrder.setSalesOrderStatus("Cancelled");
		emgr.merge(managedSalesOrder);
		salesOrder = managedSalesOrder;
		// Send email via the email service (TBD)
		return salesOrder;
	}
		
	private String generateOrderNumber(SalesOrder salesOrder) {
		// Simplified for prototyping purposes
		return String.valueOf(new Date().getTime());
	}
	
	public SalesOrderLine calculateLineItem(SalesOrderLine lineItem) {
		lineItem.setItemPrice(lineItem.getCatalogItem().getItemPrice());
		lineItem.setExtendedPrice(lineItem.getItemPrice().multiply(new BigDecimal(lineItem.getItemQuantity())));
		return lineItem;
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
