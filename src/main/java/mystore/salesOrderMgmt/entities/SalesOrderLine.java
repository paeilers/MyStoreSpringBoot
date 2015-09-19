
package mystore.salesOrderMgmt.entities;

/* Author: PEilers */


import java.io.Serializable;
import java.math.BigDecimal;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@XmlRootElement 
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="sales_order_line")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="salesOrderLineUid")
@NamedQuery(name="SalesOrderLine.findAll", query="SELECT s FROM SalesOrderLine s")
public class SalesOrderLine implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int salesOrderLineUid;
	private int salesOrderUid;
	private SalesOrder salesOrder;
	private int catalogItemUid;
	private CatalogItem catalogItem;
	private int itemQuantity;
	private BigDecimal itemPrice;
	private BigDecimal extendedPrice;
	private Shipment shipment;
	
	public SalesOrderLine() {
	}
	
	public SalesOrderLine(SalesOrder salesOrder, CatalogItem catalogItem, int itemQuantity) {
		this.catalogItemUid = catalogItem.getCatalogItemUid();
		this.salesOrder = salesOrder;
		this.catalogItem = catalogItem;
		this.itemQuantity = itemQuantity;
		this.itemPrice = catalogItem.getItemPrice();
		this.extendedPrice = this.itemPrice.multiply(new BigDecimal(this.itemQuantity));
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SALES_ORDER_LINE_UID")
	public int getSalesOrderLineUid() {
		return this.salesOrderLineUid;
	}
	
	public void setSalesOrderLineUid(int salesOrderLineUid) {
		this.salesOrderLineUid = salesOrderLineUid;
	}
	
	@Column(name="EXTENDED_PRICE", nullable=false)
	public BigDecimal getExtendedPrice() {
		return this.extendedPrice;
	}
	
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}

	@Column(name="ITEM_QUANTITY")
	public int getItemQuantity() {
		return this.itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	@Column(name="ITEM_PRICE")
	public BigDecimal getItemPrice() {
		return this.itemPrice;
	}
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	@Column(nullable=false, insertable=false, updatable=false)
	public int getSalesOrderUid() {
		return this.salesOrderUid;
	}
	
	public void setSalesOrderUid(int salesOrderUid) {
		this.salesOrderUid = salesOrderUid;
	}
	
	@ManyToOne(targetEntity=SalesOrder.class)
	@JoinColumn(name="salesOrderUid")
	@JsonBackReference
	public SalesOrder getSalesOrder() {
		return this.salesOrder;
	}
	
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	@Column(name="CATALOG_ITEM_UID", nullable=false)
	public int getCatalogItemUid() {
		return this.catalogItemUid;
	}
	
	public void setCatalogItemUid(int catalogItemUid) {
		this.catalogItemUid = catalogItemUid;
	}
	
	@ManyToOne(targetEntity=CatalogItem.class, fetch=FetchType.EAGER)
	@JoinColumn(name="CATALOG_ITEM_UID", nullable=false, insertable=false, updatable=false)
	public CatalogItem getCatalogItem() {
		return this.catalogItem;
	}
	
	public void setCatalogItem(CatalogItem catalogItem) {
		this.catalogItem = catalogItem;
	}
	
	@ManyToOne(targetEntity=Shipment.class, optional = true)
	@JoinColumn(name="SHIPMENT_UID")
	public Shipment getShipment() {
		return this.shipment;
	}
	
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	public void calculateLineItem() {
		this.setItemPrice(this.getCatalogItem().getItemPrice());
		this.setExtendedPrice(this.getItemPrice().multiply(new BigDecimal(this.getItemQuantity())));
	}

}
