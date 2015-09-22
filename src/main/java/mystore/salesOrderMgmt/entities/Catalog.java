package mystore.salesOrderMgmt.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the catalog database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
	@NamedQuery(name="Catalog.findAll", query="from Catalog"),
	@NamedQuery(name="Catalog.byId", query="from Catalog where catalogUid = ?"),
	@NamedQuery(name="Catalog.current", query="from Catalog where validFrom <= CURRENT_DATE and (validThrough IS NULL or validThrough >= CURRENT_DATE)")	
})
public class Catalog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int catalogUid;
	private Date validFrom;
	private Date validThrough;
	private List<CatalogItem> catalogItems;
	private List<Item> items;

	// Initializer block
	{
		catalogItems = new ArrayList<CatalogItem>();
		items = new ArrayList<Item>();		
	}
	
	public Catalog() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CATALOG_UID")
	public int getCatalogUid() {
		return this.catalogUid;
	}

	public void setCatalogUid(int catalogUid) {
		this.catalogUid = catalogUid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_FROM")
	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VALID_THROUGH")
	public Date getValidThrough() {
		return this.validThrough;
	}

	public void setValidThrough(Date validThrough) {
		this.validThrough = validThrough;
	}


	@OneToMany(targetEntity=CatalogItem.class, mappedBy="catalog", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonManagedReference
	public List<CatalogItem> getCatalogItems() {
		return this.catalogItems;
	}

	public void setCatalogItems(List<CatalogItem> catalogItems) {
		this.catalogItems = catalogItems;
	}

}