package mystore.salesOrderMgmt.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int itemUid;
	private String productDescription;
	private String productName;	
	private List<CatalogItem> catalogItems;
	private List<ProductCategoryItem> productCategoryItems;
	private List<Catalog> catalogs;

	// Initializer block
	{
		catalogItems = new ArrayList<CatalogItem>();
		productCategoryItems = new ArrayList<ProductCategoryItem>();
		catalogs = new ArrayList<Catalog>();
	}
	
	public Item() {
	}

	public Item(String productName, String productDescription) {
		this.productName = productName;
		this.productDescription = productDescription;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ITEM_UID")
	public int getItemUid() {
		return this.itemUid;
	}

	public void setItemUid(int itemUid) {
		this.itemUid = itemUid;
	}

	@Column(name="PRODUCT_DESCRIPTION")
	public String getProductDescription() {
		return this.productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Column(name="PRODUCT_NAME")
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@OneToMany(targetEntity=CatalogItem.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="item")
	@JsonBackReference
	public List<CatalogItem> getCatalogItems() {
		return this.catalogItems;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

	@OneToMany(targetEntity=ProductCategoryItem.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="item")
	@JsonBackReference
	public List<ProductCategoryItem> getProductCategoryItems() {
		return this.productCategoryItems;
	}

	public void setProductCategoryItems(List<ProductCategoryItem> productCategoryItems) {
		this.productCategoryItems = productCategoryItems;
	}

	/**
	 * @return the catalogs
	 */
	@ManyToMany(mappedBy="items", targetEntity=Catalog.class, fetch=FetchType.LAZY)
	public List<Catalog> getCatalogs() {
		return catalogs;
	}

	/**
	 * @param catalogs the catalogs to set
	 */
	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	
}