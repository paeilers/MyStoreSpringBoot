package mystore.salesOrderMgmt.controls.api;

import java.util.Date;
import java.util.List;

import mystore.salesOrderMgmt.entities.Catalog;
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.Item;
import mystore.salesOrderMgmt.entities.ProductCategory;
import mystore.salesOrderMgmt.entities.ProductCategoryItem;

public interface CatalogService {

	/**
	 * 
	 */
	public Catalog createCatalog(Date validFrom, Date validThrough);
	
	public Catalog retrieveCatalog(Integer catalogUid);		
	
	public Catalog retrieveCurrentCatalog();		
	
	public ProductCategory createProductCategory(ProductCategory categoryParent, String categoryName, String categoryDescription);
	
	public ProductCategory retrieveProductCategory(Integer productCategoryUid);

	public List<ProductCategory> retrieveCatalogProductCategories(Integer catalogUid);
	
	public List<ProductCategory> retrieveAllProductCategories();

	public Item createItem(String productName, String productDescription);
	
	public Item retrieveItem(int itemUid);
	
	public CatalogItem createCatalogItem(Item item, Catalog catalog);
	
	public CatalogItem retrieveCatalogItem(Integer catalogItemUid);	
	
	public List<CatalogItem> retrieveAllCatalogItems(Catalog catalog);		
	
	public ProductCategoryItem createProductCategoryItem(ProductCategory productCategory, Item item);
	
	public ProductCategoryItem retrieveProductCategoryItem(Integer productCategoryItemUid);
	
	public List<ProductCategoryItem> retrieveAllProductCategoryItems(Integer productCategoryUid);	

	public List<ProductCategoryItem> retrieveAllProductCategoryItems();	

	public Catalog addCatalogItem(Catalog catalog, Item item);

	public Catalog removeCatalogItem(Catalog catalog, CatalogItem catalogItem);

}
