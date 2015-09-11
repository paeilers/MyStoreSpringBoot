package mystore.salesOrderMgmt.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the product_category_item database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="product_category_item")
@NamedQuery(name="ProductCategoryItem.findAll", query="SELECT p FROM ProductCategoryItem p")
public class ProductCategoryItem implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int productCategoryItemUid;	
	private ProductCategory productCategory;
	private Item item;
	
	public ProductCategoryItem() {
	}
	
	public ProductCategoryItem(ProductCategory productCategory, Item item) {
		this.productCategory = productCategory;
		this.item = item; 
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_CATEGORY_ITEM_UID")
	public int getProductCategoryItemUid() {
		return this.productCategoryItemUid;
	}

	public void setProductCategoryItemUid(int productCategoryItemUid) {
		this.productCategoryItemUid = productCategoryItemUid;
	}

	@ManyToOne(targetEntity=Item.class, fetch=FetchType.EAGER)
	@JoinColumn(name="ITEM_UID")
	@JsonManagedReference
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(targetEntity=ProductCategory.class, fetch=FetchType.EAGER)
	@JoinColumn(name="PRODUCT_CATEGORY_UID")
	@JsonManagedReference
	public ProductCategory getProductCategory() {
		return this.productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

}