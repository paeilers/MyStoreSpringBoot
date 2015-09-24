/**
 * 
 */
package mystore.controls;


import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.fail;

import java.sql.Timestamp;
import java.util.Date;

import mystore.MystoreApplication;
import mystore.salesOrderMgmt.controls.api.CatalogService;
import mystore.salesOrderMgmt.controls.api.SalesOrderService;
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.SalesOrder;
import mystore.salesOrderMgmt.entities.SalesOrderLine;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Patty Eilers
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MystoreApplication.class)
@WebAppConfiguration
//@Test
//@ContextConfiguration(classes = MystoreApplication.class)
public class SalesOrderServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	SalesOrderService salesOrderService;
	
	@Autowired
	CatalogService catalogService;
	
	protected SalesOrder salesOrder;
	protected SalesOrderLine salesOrderLine;
	
	@Test
	public void getSalesOrderService() {
		assertNotNull(salesOrderService);
	}
	
	@Test
	public void getCatalogService() {
		assertNotNull(catalogService);
	}
	
	@Test
	public void testCreateSalesOrder() throws Exception {

		// Create the SalesOrder entity
		this.salesOrder = new SalesOrder();
		salesOrder.setPromoCode("Test");
		// Set non-relational properties
		salesOrder.setSalesOrderStatus("New");
		salesOrder.setBillToFirstName("Duke");
		salesOrder.setBillToLastName("Developer");
		salesOrder.setBillToStreetNumber("12345");
		salesOrder.setBillToStreetName("Technology Row");
		salesOrder.setBillToCity("Golden");
		salesOrder.setBillToState("CO");
		salesOrder.setBillToZipCode(80401);
		// Set Ship To properties
		salesOrder.setShipToStreetNumber("12345");
		salesOrder.setShipToStreetName("Technology Row");
		salesOrder.setShipToCity("Golden");
		salesOrder.setShipToState("CO");
		salesOrder.setShipToZipCode(80401);		
		// Billing properties
		salesOrder.setNameOnCreditCard("Duke Developer");
		salesOrder.setCreditCardType("Visa");
		salesOrder.setCreditCardNumber("123456789012");
		salesOrder.setCreditCardCsv(123);
		salesOrder.setCreditCardExpiryMonth("01");
		salesOrder.setCreditCardExpiryYear("2017");
		salesOrder.setEmailAddress("ddeveloper@techexpertconsulting.com");		
		salesOrder.setSalesOrderDate(new Timestamp(new Date().getTime()));

		// Associate the first line item with the new sales order
		CatalogItem catItem = catalogService.retrieveCatalogItem(2);
		salesOrderLine = new SalesOrderLine(salesOrder, catItem, 20);
		salesOrderLine = new SalesOrderLine(salesOrder, catItem, 20);		
		salesOrder.getLineItems().add(salesOrderLine);				
		salesOrderService.createSalesOrder(salesOrder);			
				
		assertNotNull(salesOrder.getOrderNumber());
		assertNotNull(salesOrder.getSalesOrderUid());
		assertNotNull(salesOrderLine.getSalesOrderLineUid());
	}


	@Test(dependsOnMethods={"testCreateSalesOrder"})
	public void testAddLineItem() throws Exception {
		int initialNumberOfLines = 0;
		if (salesOrder.getLineItems() != null) {
			initialNumberOfLines = salesOrder.getLineItems().size();
		} 
		
		// Assumes pre-existence of a SalesOrder
		// The service method should take a SalesOrder object as input as well as a CatalogItem and Quantity
		// Set line item details
		CatalogItem catItem = catalogService.retrieveCatalogItem(1);		
		salesOrderLine = new SalesOrderLine(salesOrder, catItem, 10);
		// Associate new lineItem with salesOrder
		salesOrderService.addLineItem(salesOrder, salesOrderLine);
		AssertJUnit.assertNotSame(salesOrder.getLineItems().size(), initialNumberOfLines);	
		}

	@Test(dependsOnMethods = {"testCreateSalesOrder"})
	public void testRetrieveSalesOrder() {
		SalesOrder retrievedOrder = salesOrderService.retrieveSalesOrder(this.salesOrder.getSalesOrderUid());
		if (retrievedOrder != null) {
			assertEquals(retrievedOrder.toString(), this.salesOrder.toString());
		} else {
			fail("No salesOrder was retrieved from the database");
		}
	}
	
	@Test(dependsOnMethods={"testAddLineItem"})
	public void testRemoveLineItem() throws Exception {
		this.salesOrder = salesOrderService.removeSalesOrderLine(this.salesOrder, this.salesOrderLine);
		assertFalse(this.salesOrder.getLineItems().contains(this.salesOrderLine));
	}
	
	@Test(dependsOnMethods={"testCreateSalesOrder"})
	public void cancelSalesOrder() {
		this.salesOrder = salesOrderService.cancelSalesOrder(salesOrder);
		assertEquals(salesOrder.getSalesOrderStatus(), "Cancelled");
	}

}
