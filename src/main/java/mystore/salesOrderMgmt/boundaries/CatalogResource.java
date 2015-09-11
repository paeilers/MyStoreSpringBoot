package mystore.salesOrderMgmt.boundaries;

import java.util.List;

import mystore.MystoreProperties;
import mystore.salesOrderMgmt.controls.CatalogService;
import mystore.salesOrderMgmt.entities.Catalog;
import mystore.salesOrderMgmt.entities.ProductCategory;
import mystore.salesOrderMgmt.entities.ProductCategoryItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogResource {

	@Autowired
	MystoreProperties props;

	@Autowired
	CatalogService catalogService;
	
	@RequestMapping(value="/catalog", method=RequestMethod.GET)
	public Catalog getCatalog(@RequestParam(value="callback", required=false) String callback) {
		Catalog retrievedCatalog = catalogService.retrieveCurrentCatalog();
		if (retrievedCatalog == null) {
			System.out.println("No current Catalog could be found.");
		} else {
			System.out.println("Current catalog retrieved successfully.");
		}
		return retrievedCatalog;
	}

	@RequestMapping(value="/productCategories/{catalogUid}", method=RequestMethod.GET)
	public List<ProductCategory> getCatalogProductCategories(@PathVariable("catalogUid") Integer catalogUid, 
																			@RequestParam(value="callback", required=false) String callback) {
		List<ProductCategory> productCategories = null;
		if (catalogUid != null) {
			productCategories = catalogService.retrieveCatalogProductCategories(catalogUid);			
		} else {
			productCategories = catalogService.retrieveAllProductCategories();
		}
		if (productCategories != null) {
			System.out.println("List of Product Categories retrieved");
		} 
		return productCategories;
	}
	
	@RequestMapping(value="/productCategories", method=RequestMethod.GET)
	public List<ProductCategory> getAllProductCategories(@RequestParam(value="callback", required=false) String callback) {
		List<ProductCategory> productCategories = catalogService.retrieveAllProductCategories();
		if (productCategories != null) {
			System.out.println("List of Product Categories retrieved");
		} 
		return productCategories;
	}
	
	@RequestMapping(value="/productCategoryItems/{categoryUid}", method=RequestMethod.GET)
	public List<ProductCategoryItem> getProductCategoryItems(@PathVariable("categoryUid") Integer categoryUid, @RequestParam(value="callback", required=false) String callback) {
		List<ProductCategoryItem> productCategoryItems = catalogService.retrieveAllProductCategoryItems(categoryUid);
		if (productCategoryItems != null) {
			System.out.println("List of Product Category Items retrieved");
		} 
		return productCategoryItems;
		
	}

	@RequestMapping(value="/productCategoryItems", method=RequestMethod.GET)
	public List<ProductCategoryItem> getAllProductCategoryItems(@RequestParam(value="callback", required=false) String callback) {
		List<ProductCategoryItem> productCategoryItems = catalogService.retrieveAllProductCategoryItems();
		if (productCategoryItems != null) {
			System.out.println("List of Product Category Items retrieved");
		} 
		return productCategoryItems;
		
	}
	
}
