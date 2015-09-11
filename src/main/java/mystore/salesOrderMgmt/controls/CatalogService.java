package mystore.salesOrderMgmt.controls;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mystore.salesOrderMgmt.entities.Catalog;
import mystore.salesOrderMgmt.entities.CatalogItem;
import mystore.salesOrderMgmt.entities.Item;
import mystore.salesOrderMgmt.entities.ProductCategory;
import mystore.salesOrderMgmt.entities.ProductCategoryItem;

import org.springframework.stereotype.Service;

@Service("catalogService")
public class CatalogService implements Serializable {

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
		Query catalogQuery = emgr.createQuery("Select c from Catalog c where c.catalogUid = :catalogUid");
		catalogQuery.setParameter("catalogUid", catalogUid);
		Catalog catalog = null;
		catalog = (Catalog) catalogQuery.getSingleResult();
		return catalog;
	}		
	
	public Catalog retrieveCurrentCatalog() {
		Query catalogQuery = emgr.createQuery("Select c from Catalog c where c.validFrom <= CURRENT_DATE and (c.validThrough IS NULL or c.validThrough >= CURRENT_DATE)");
		Catalog catalog = (Catalog) catalogQuery.getSingleResult();
		return catalog;
	}		
	
	public ProductCategory createProductCategory(ProductCategory categoryParent, String categoryName, String categoryDescription) {
		ProductCategory productCategory = new ProductCategory(categoryParent, categoryName, categoryDescription);
		emgr.persist(productCategory);
		return productCategory;
	}
	
	public ProductCategory retrieveProductCategory(Integer productCategoryUid) {
		Query productCatalogQuery = emgr.createQuery("Select pc from ProductCategory pc where pc.productCategoryUid = :productCategoryUid");
		ProductCategory productCategory = (ProductCategory) productCatalogQuery.getSingleResult();
		return productCategory;
	}

	public List<ProductCategory> retrieveCatalogProductCategories(Integer catalogUid) {
		Query query = emgr.createQuery("Select pc from ProductCategory pc, ProductCategoryItem pci, Item i, CatalogItem ci "
										+ "where pc.productCategoryUid = pci.productCategoryUid "
										+ "and pci.itemUid = i.itemUid "
										+ "and i.itemUid = ci.itemUid "
										+ "and ci.catalogUid = :catalogUid");
		query.setParameter("catalogUid", catalogUid);
		List<ProductCategory> productCategories = (List<ProductCategory>) query.getResultList();
		return productCategories;
	}
	
	public List<ProductCategory> retrieveAllProductCategories() {
		Query query = emgr.createQuery("Select pc from ProductCategory pc");
		List<ProductCategory> productCategories = (List<ProductCategory>) query.getResultList();
		return productCategories;
	}

	public Item createItem(String productName, String productDescription) {
		Item item = new Item(productName, productDescription);
		emgr.persist(item);
		return item;
	}
	
	public Item retrieveItem(int itemUid) {
		Query itemQuery = emgr.createQuery("Select i from Item i where i.itemUid = :itemyUid");
		itemQuery.setParameter("itemUid", itemUid);
		Item item = (Item) itemQuery.getSingleResult();
		return item;
		
	}
	
	public CatalogItem createCatalogItem(Item item, Catalog catalog) {
		CatalogItem catalogItem = new CatalogItem(item, catalog);
		emgr.persist(catalogItem);
		return catalogItem;
	}
	
	public CatalogItem retrieveCatalogItem(Integer catalogItemUid) {
		Query catalogItemQuery = emgr.createQuery("Select ci from CatalogItem ci where ci.catalogItemUid = :catalogItemUid");
		catalogItemQuery.setParameter("catalogItemUid", catalogItemUid);
		CatalogItem catalogItem = (CatalogItem) catalogItemQuery.getSingleResult();
		return catalogItem;
	}	
	
	public List<CatalogItem> retrieveAllCatalogItems(Catalog catalog) {
		Query catalogItemQuery = emgr.createQuery("Select ci from CatalogItem c where c.catalogItemUid = :catalogUid");
		catalogItemQuery.setParameter("catalogUid", catalog.getCatalogUid());
		List<CatalogItem> catalogItems = (List<CatalogItem>) catalogItemQuery.getResultList(); 
		return catalogItems;
	}		
	
	public ProductCategoryItem createProductCategoryItem(ProductCategory productCategory, Item item) {
		ProductCategoryItem productCategoryItem = new ProductCategoryItem(productCategory, item);
		emgr.persist(productCategoryItem);
		return productCategoryItem;
	}
	
	public ProductCategoryItem retrieveProductCategoryItem(Integer productCategoryItemUid) {
		Query query = emgr.createQuery("Select pci from ProductCategoryItem pci where pci.productCategoryItemUid = :productCategoryItemUid");
		query.setParameter("productCategoryItemUid", productCategoryItemUid);
		ProductCategoryItem productCategoryItem = (ProductCategoryItem) query.getSingleResult();
		return productCategoryItem;
		
	}
	
	public List<ProductCategoryItem> retrieveAllProductCategoryItems(Integer productCategoryUid) {
		Query query = emgr.createQuery("Select pci from ProductCategoryItem pci where pci.productCategoryUid = :productCategoryUid");
		query.setParameter("productCategoryUid", productCategoryUid);
		List<ProductCategoryItem> productCategoryItems = (List<ProductCategoryItem>) query.getResultList(); 
		return productCategoryItems;
	}	

	public List<ProductCategoryItem> retrieveAllProductCategoryItems() {
		Query query = emgr.createQuery("Select pci from ProductCategoryItem pci");
		List<ProductCategoryItem> productCategoryItems = (List<ProductCategoryItem>) query.getResultList(); 
		return productCategoryItems;
	}	

	// Need to add methods for updating as well as deleting/removing for all of the related Catalog objects
	public Catalog addCatalogItem(Catalog catalog, Item item) {
		catalog = emgr.find(Catalog.class, (Integer) catalog.getCatalogUid(), LockModeType.PESSIMISTIC_WRITE);
		item = emgr.find(Item.class, (Integer) item.getItemUid(), LockModeType.PESSIMISTIC_WRITE);
		CatalogItem catalogItem = new CatalogItem(item, catalog);
		emgr.persist(catalogItem);
		catalog.getCatalogItems().add(catalogItem);
		item.getCatalogItems().add(catalogItem);
		emgr.flush();
		return catalog;
	}

	public Catalog removeCatalogItem(Catalog catalog, CatalogItem catalogItem) {
		catalog = emgr.find(Catalog.class, (Integer) catalog.getCatalogUid(), LockModeType.PESSIMISTIC_WRITE);
		catalogItem = emgr.find(CatalogItem.class, (Integer) catalogItem.getCatalogItemUid(), LockModeType.PESSIMISTIC_WRITE);
		catalog.getCatalogItems().remove(catalogItem);	
		emgr.merge(catalog);
		emgr.flush();
		return catalog;
	}


}
