package mystore.salesOrderMgmt.boundaries;

/* Author: PEilers */

import java.util.ArrayList;
import java.util.List;

import mystore.MystoreProperties;
import mystore.salesOrderMgmt.controls.CatalogService;
import mystore.salesOrderMgmt.controls.DiscountCalculator;
import mystore.salesOrderMgmt.controls.SalesOrderService;
import mystore.salesOrderMgmt.controls.ShippingCalculator;
import mystore.salesOrderMgmt.controls.TaxCalculator;
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.SalesOrder;
import mystore.salesOrderMgmt.entities.SalesOrderLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

/* This boundary service should expose course grained methods to the client */

@RestController
public class SalesOrderResource {
	
	@Autowired
	MystoreProperties props;

	@Autowired
	SalesOrderService salesOrderService;
	
	@Autowired
	CatalogService catalogService;

	@Autowired
	TaxCalculator taxCalculator;
	
	@Autowired
	ShippingCalculator shippingCalculator;
	
	@Autowired
	DiscountCalculator discountCalculator;
	
	@RequestMapping(value="salesOrders/review", method=RequestMethod.GET)
	public SalesOrder calcualteSalesOrder(@RequestParam("salesOrder") String jsonSalesOrder) {

		Gson gsonSalesOrder = new Gson();
		SalesOrder salesOrder = gsonSalesOrder.fromJson(jsonSalesOrder, SalesOrder.class);
		
		SalesOrder calculatedSalesOrder = null;
		calculatedSalesOrder = salesOrderService.calculateSalesOrder(salesOrder);
	
		return calculatedSalesOrder;
	}

	
	@RequestMapping(value="salesOrders/{orderUid}", method=RequestMethod.GET)
	public SalesOrder getSalesOrder(@PathVariable("orderUid") Integer orderUid, @RequestParam(value="callback", required=false) String callback) {
		System.out.println("Entered resource call to getSalesOrder()...");
		SalesOrder retrievedSalesOrder = salesOrderService.retrieveSalesOrder(orderUid);
		if (retrievedSalesOrder == null) {
			System.out.println("No matching SalesOrder could be found.");
		}				
		return retrievedSalesOrder;	
	}


	@RequestMapping(value="salesOrders", method=RequestMethod.POST)
	public SalesOrder submitSalesOrder(@RequestBody String jsonSalesOrder) {
				
		Gson gsonSalesOrder = new Gson();
		SalesOrder salesOrder = gsonSalesOrder.fromJson(jsonSalesOrder, SalesOrder.class);
		
		// Go through the line items and create a new array of line items by assigning the catalogItem directly
		// This is required else the many-to-one relationship between SalesOrderLine and CatalogItem does not get carried through
		List<SalesOrderLine> salesOrderLineItems = salesOrder.getLineItems();
		List<SalesOrderLine> newLineItems = new ArrayList<SalesOrderLine>();
		for (int i = 0; i < salesOrderLineItems.size(); i++) {
			CatalogItem catalogItem = catalogService.retrieveCatalogItem(salesOrderLineItems.get(i).getCatalogItemUid());
			SalesOrderLine lineItem = new SalesOrderLine(salesOrder, catalogItem, salesOrderLineItems.get(i).getItemQuantity());
			newLineItems.add(lineItem);
		}
		salesOrder.setLineItems(newLineItems);
						
		SalesOrder persistedSalesOrder = null;
		try {
			persistedSalesOrder = salesOrderService.createSalesOrder(salesOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return persistedSalesOrder;
	}

}
