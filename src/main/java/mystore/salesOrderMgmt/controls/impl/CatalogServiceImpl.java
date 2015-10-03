package mystore.salesOrderMgmt.controls.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import mystore.salesOrderMgmt.controls.api.CatalogService;
import mystore.salesOrderMgmt.entities.Catalog;
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.Item;
import mystore.salesOrderMgmt.entities.ProductCategory;
import mystore.salesOrderMgmt.entities.ProductCategoryItem;

@Service("catalogService")
public class CatalogServiceImpl implements Serializable, CatalogService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 681991056069293569L;
	@PersistenceContext
	EntityManager emgr;
		
	public Catalog createCatalog(Date validFrom, Date validThrough) {
		Catalog catalog = new Catalog();
		catalog.setValidFrom(validFrom);
		catalog.setValidThrough(validThrough);
		emgr.persist(catalog);
		return catalog;
	}
	
	public Catalog retrieveCatalog(Integer catalogUid) {
		Query catalogQuery = emgr.createNamedQuery("Catalog.byId");
		catalogQuery.setParameter(0, catalogUid);
		catalogQuery.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		Catalog catalog = null;
		catalog = (Catalog) catalogQuery.getSingleResult();
		return catalog;
	}		
	
	public Catalog retrieveCurrentCatalog() {
		Query catalogQuery = emgr.createNamedQuery("Catalog.current");
		catalogQuery.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		Catalog catalog = (Catalog) catalogQuery.getSingleResult();			
		return catalog;
	}		
	
	public ProductCategory createProductCategory(ProductCategory categoryParent, String categoryName, String categoryDescription) {
		ProductCategory productCategory = new ProductCategory(categoryParent, categoryName, categoryDescription);
		emgr.persist(productCategory);
		return productCategory;
	}
	
	public ProductCategory retrieveProductCategory(Integer productCategoryUid) {
		Query productCatalogQuery = emgr.createNamedQuery("ProductCategory.byId");
		productCatalogQuery.setParameter(0, productCategoryUid);
		productCatalogQuery.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		ProductCategory productCategory = (ProductCategory) productCatalogQuery.getSingleResult();
		return productCategory;
	}

	public List<ProductCategory> retrieveCatalogProductCategories(Integer catalogUid) {
		Query query = emgr.createNativeQuery("Select pc from ProductCategory pc, ProductCategoryItem pci, Item i, CatalogItem ci "
										+ "where pc.productCategoryUid = pci.productCategoryUid "
										+ "and pci.itemUid = i.itemUid "
										+ "and i.itemUid = ci.itemUid "
										+ "and ci.catalogUid = :catalogUid");
		query.setParameter("catalogUid", catalogUid);
		query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		List<ProductCategory> productCategories = (List<ProductCategory>) query.getResultList();
		return productCategories;
	}
	
	public List<ProductCategory> retrieveAllProductCategories() {
		Query query = emgr.createNamedQuery("ProductCategory.findAll");
		query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
		List<ProductCategory> productCategories = (List<ProductCategory>) query.getResultList();
		return productCategories;
	}

	public Item createItem(String productName, String productDescription) {
		Item item = new Item(productName, productDescription);
		emgr.persist(item);
		return item;
	}
	
	public Item retrieveItem(int itemUid) {
		Query itemQuery = emgr.createNamedQuery("Item.byId");
		itemQuery.setParameter(0, itemUid);
		Item item = (Item) itemQuery.getSingleResult();
		return item;
		
	}
	
	public CatalogItem createCatalogItem(Item item, Catalog catalog) {
		CatalogItem catalogItem = new CatalogItem(item, catalog);
		emgr.persist(catalogItem);
		return catalogItem;
	}
	
	public CatalogItem retrieveCatalogItem(Integer catalogItemUid) {
		Query query = emgr.createNamedQuery("CatalogItem.byId");
		query.setParameter(1, catalogItemUid);
		CatalogItem catalogItem = (CatalogItem) query.getSingleResult();
		return catalogItem;
	}	
	
	public List<CatalogItem> retrieveAllCatalogItems(Catalog catalog) {
		Query query = emgr.createNamedQuery("CatalogItem.byCatalog");
		query.setParameter(0, catalog.getCatalogUid());
		List<CatalogItem> catalogItems = (List<CatalogItem>) query.getResultList(); 
		return catalogItems;
	}		
	
	public ProductCategoryItem createProductCategoryItem(ProductCategory productCategory, Item item) {
		ProductCategoryItem productCategoryItem = new ProductCategoryItem(productCategory, item);
		emgr.persist(productCategoryItem);
		return productCategoryItem;
	}
	
	public ProductCategoryItem retrieveProductCategoryItem(Integer productCategoryItemUid) {
		Query query = emgr.createNamedQuery("ProductCategoryItem.byId");
		query.setParameter(0, productCategoryItemUid);
		ProductCategoryItem productCategoryItem = (ProductCategoryItem) query.getSingleResult();
		return productCategoryItem;
		
	}
	
	public List<ProductCategoryItem> retrieveAllProductCategoryItems(Integer productCategoryUid) {
		Query query = emgr.createNamedQuery("ProductCategoryItem.byProductCategory");
		query.setParameter(0, productCategoryUid);
		List<ProductCategoryItem> productCategoryItems = (List<ProductCategoryItem>) query.getResultList(); 
		return productCategoryItems;
	}	

	public List<ProductCategoryItem> retrieveAllProductCategoryItems() {
		Query query = emgr.createNamedQuery("ProductCategoryItem.findAll");
		List<ProductCategoryItem> productCategoryItems = (List<ProductCategoryItem>) query.getResultList(); 
		return productCategoryItems;
	}	

	// Need to add methods for updating as well as deleting/removing for all of the related Catalog objects
	public Catalog addCatalogItem(Catalog catalog, Item item) {
		catalog = emgr.find(Catalog.class, (Integer) catalog.getCatalogUid(), LockModeType.PESSIMISTIC_WRITE);
		item = emgr.find(Item.class, (Integer) item.getItemUid(), LockModeType.PESSIMISTIC_WRITE);
		CatalogItem catalogItem = new CatalogItem(item, catalog);
		catalogItem.setCatalog(catalog);
		catalogItem.setItem(item);
		emgr.persist(catalogItem);
		catalog.getCatalogItems().add(catalogItem);
		item.getCatalogItems().add(catalogItem);
		return catalog;
	}

	public Catalog removeCatalogItem(Catalog catalog, CatalogItem catalogItem) {
		catalog = emgr.find(Catalog.class, (Integer) catalog.getCatalogUid(), LockModeType.PESSIMISTIC_WRITE);
		catalogItem = emgr.find(CatalogItem.class, (Integer) catalogItem.getCatalogItemUid(), LockModeType.PESSIMISTIC_WRITE);
		catalog.getCatalogItems().remove(catalogItem);	
		emgr.remove(catalogItem);
//		emgr.merge(catalog);
//		emgr.flush();
		return catalog;
	}


}
