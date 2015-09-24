package mystore.salesOrderMgmt.controls.api;

//import model.SalesOrder;
/* This boundary service should expose course grained methods to the client */
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.SalesOrder;
import mystore.salesOrderMgmt.entities.SalesOrderLine;

import org.springframework.transaction.annotation.Transactional;

/* This boundary service should expose course grained methods to the client */
public interface SalesOrderService {

	@Transactional
	public SalesOrder createSalesOrder(SalesOrder salesOrder) throws Exception;
	
	@Transactional
	public SalesOrder addLineItem(SalesOrder salesOrder, SalesOrderLine lineItem);

	@Transactional
	public SalesOrder addLineItem(SalesOrder salesOrder, CatalogItem catItem, int itemQuantity);

	@Transactional
	public SalesOrder retrieveSalesOrder(Integer orderUid);	

	@Transactional
	public SalesOrder removeSalesOrderLine(SalesOrder salesOrder, SalesOrderLine salesOrderLine) throws Exception;
	
	@Transactional
	public SalesOrder cancelSalesOrder(SalesOrder salesOrder);
		
	public String generateOrderNumber(SalesOrder salesOrder);
	
	public SalesOrder calculateSalesOrder(SalesOrder salesOrder);
	
}
