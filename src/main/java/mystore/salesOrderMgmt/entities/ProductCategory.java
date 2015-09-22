package mystore.salesOrderMgmt.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the product_category database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="product_category")
@NamedQueries({
	@NamedQuery(name="ProductCategory.findAll", 
			query="from ProductCategory"),
	@NamedQuery(name="ProductCategory.byId", 
			query="from ProductCategory where productCategoryUid = ?")
})
public class ProductCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	private int productCategoryUid;
	private String categoryDescription;
	private String categoryName;	
	private ProductCategory parentProductCategory;	
	private Set<ProductCategory> subCategories;
	private Set<ProductCategoryItem> productCategoryItems;

	public ProductCategory() {
		this.productCategoryItems = new HashSet<ProductCategoryItem>();
		this.subCategories = new HashSet<ProductCategory>();
	}

	public ProductCategory(ProductCategory parentProductCategory, String categoryName, String categoryDescription) {
		this.productCategoryItems = new HashSet<ProductCategoryItem>();
		this.subCategories = new HashSet<ProductCategory>();
		this.parentProductCategory = parentProductCategory;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCT_CATEGORY_UID")
	public int getProductCategoryUid() {
		return this.productCategoryUid;
	}

	public void setProductCategoryUid(int productCategoryUid) {
		this.productCategoryUid = productCategoryUid;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="PARENT_PRODUCT_CATEGORY_UID")
	@JsonBackReference
	@JsonIgnore
	public ProductCategory getParentProductCategory() {
		return this.parentProductCategory;
	}
	
	public void setParentProductCategory(ProductCategory productCategory) {
		this.parentProductCategory = productCategory;
	}
	
	@OneToMany(targetEntity=ProductCategory.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parentProductCategory")
	@JsonManagedReference
	@OrderBy("categoryName")
	public Set<ProductCategory> getSubCategories() {
		return this.subCategories; 
	}
	
	public void setSubCategories(Set<ProductCategory> subCategories) {
		this.subCategories = subCategories;
	}
	
	@Column(name="CATEGORY_DESCRIPTION")
	public String getCategoryDescription() {
		return this.categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	@Column(name="CATEGORY_NAME")
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@OneToMany(targetEntity=ProductCategoryItem.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="productCategory")
	@JsonBackReference
	public Set<ProductCategoryItem> getProductCategoryItems() {
		return this.productCategoryItems;
	}

	public void setProductCategoryItems(Set<ProductCategoryItem> productCategoryItems) {
		this.productCategoryItems = productCategoryItems;
	}

}