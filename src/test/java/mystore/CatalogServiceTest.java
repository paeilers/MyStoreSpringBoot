/**
 * 
 */
package mystore;

import static org.junit.Assert.assertNotNull;
import mystore.salesOrderMgmt.controls.CatalogService;
import mystore.salesOrderMgmt.entities.Catalog;
import mystore.salesOrderMgmt.entities.CatalogItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Patty Eilers
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MystoreApplication.class)
@WebAppConfiguration
public class CatalogServiceTest {
	
	@Autowired
	CatalogService catalogService;
	
	@Test
	public void getCatalogService() throws Exception {
		assertNotNull(catalogService);
	}
	
	@Test
	public void testRetrieveCurrentCatalog() {
		Catalog retrievedCatalog = catalogService.retrieveCurrentCatalog();
		assertNotNull(retrievedCatalog);
	}
	
	@Test
	public void testRetrieveCatalogItem() {
		CatalogItem retrievedCatalogItem = catalogService.retrieveCatalogItem(1);
		assertNotNull(retrievedCatalogItem);
	}

}
