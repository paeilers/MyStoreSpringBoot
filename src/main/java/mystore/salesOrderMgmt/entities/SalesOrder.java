package mystore.salesOrderMgmt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the sales_order database table.
 * 
 */
@Entity()
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="sales_order")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="salesOrderUid")
@NamedQuery(name="SalesOrder.findAll", query="SELECT s FROM SalesOrder s")
public class SalesOrder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int salesOrderUid;
	private String orderNumber;
	private String billToCity;
	private String billToFirstName;
	private String billToLastName;
	private String billToState;
	private String billToStreetName;
	private String billToStreetNumber;
	private String billToUnitNumber;
	private int billToZipCode;
	private int creditCardCsv;
	private String creditCardExpiryMonth;
	private String creditCardExpiryYear;
	private String creditCardNumber;
	private String creditCardType;
	private String emailAddress;
	private String nameOnCreditCard;
	private Date salesOrderDate;
	private String salesOrderStatus;
	private String shipToCity;
	private String shipToState;
	private String shipToStreetName;
	private String shipToStreetNumber;
	private String shipToUnitNumber;
	private int shipToZipCode;
	private String promoCode;
	private Timestamp lastUpdatedTime;
	
	//Calculated and persisted for auditing and data retrieval performance
	private BigDecimal subTotal;
	private BigDecimal discount;
	private BigDecimal salesTax;
	private BigDecimal shipping;
	private BigDecimal total;

	private UserAccount userAccount; // Optional
	private List<SalesOrderLine> lineItems;  // Required

	// Initialization block
	// userAccount is lazy instantiated
	{ 
		lineItems = new ArrayList<SalesOrderLine>();	
	}
	
	public SalesOrder() {
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");		
		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		result.append(" SalesOrderUid: " + salesOrderUid);
		result.append(" SalesOrderNumber: " + orderNumber);
		result.append(" Number of Line Items: " + getLineItems().size());
		result.append("}");
		return result.toString();
	}
			
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SALES_ORDER_UID")
	@XmlID
	public int getSalesOrderUid() {
		return this.salesOrderUid;
	}
	
	public void setSalesOrderUid(int salesOrderUid) {
		this.salesOrderUid = salesOrderUid;
	}
	
	@Version
	@Column(name="LAST_UPDATED_TIME")
	public Timestamp getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	
	public void setLastUpdatedTime(Timestamp timeStamp) {
		this.lastUpdatedTime = timeStamp;
	}
	
	@Column(name="ORDER_NUMBER", nullable=false)
	public String getOrderNumber() {
		return this.orderNumber;
	};

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name="BILL_TO_CITY")
	public String getBillToCity() {
		return this.billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}
	
	@Column(name="BILL_TO_FIRST_NAME")
	public String getBillToFirstName() {
		return this.billToFirstName;
	}

	public void setBillToFirstName(String billToFirstName) {
		this.billToFirstName = billToFirstName;
	}
	
	@Column(name="BILL_TO_LAST_NAME")
	public String getBillToLastName() {
		return this.billToLastName;
	}

	public void setBillToLastName(String billToLastName) {
		this.billToLastName = billToLastName;
	}

	@Column(name="BILL_TO_STATE")
	public String getBillToState() {
		return this.billToState;
	}

	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}

	@Column(name="BILL_TO_STREET_NAME")
	public String getBillToStreetName() {
		return this.billToStreetName;
	}

	public void setBillToStreetName(String billToStreetName) {
		this.billToStreetName = billToStreetName;
	}

	@Column(name="BILL_TO_STREET_NUMBER")
	public String getBillToStreetNumber() {
		return this.billToStreetNumber;
	}

	public void setBillToStreetNumber(String billToStreetNumber) {
		this.billToStreetNumber = billToStreetNumber;
	}

	@Column(name="BILL_TO_UNIT_NUMBER")
	public String getBillToUnitNumber() {
		return this.billToUnitNumber;
	}

	public void setBillToUnitNumber(String billToUnitNumber) {
		this.billToUnitNumber = billToUnitNumber;
	}

	@Column(name="BILL_TO_ZIP_CODE")
	public int getBillToZipCode() {
		return this.billToZipCode;
	}

	public void setBillToZipCode(int billToZipCode) {
		this.billToZipCode = billToZipCode;
	}

	@Column(name="CREDIT_CARD_CSV")
	public int getCreditCardCsv() {
		return this.creditCardCsv;
	}

	public void setCreditCardCsv(int creditCardCsv) {
		this.creditCardCsv = creditCardCsv;
	}

	@Column(name="CREDIT_CARD_EXPIRY_MONTH")
	public String getCreditCardExpiryMonth() {
		return this.creditCardExpiryMonth;
	}

	public void setCreditCardExpiryMonth(String creditCardExpiryMonth) {
		this.creditCardExpiryMonth = creditCardExpiryMonth;
	}

	@Column(name="CREDIT_CARD_EXPIRY_YEAR")
	public String getCreditCardExpiryYear() {
		return this.creditCardExpiryYear;
	}

	public void setCreditCardExpiryYear(String creditCardExpiryYear) {
		this.creditCardExpiryYear = creditCardExpiryYear;
	}

	@Column(name="CREDIT_CARD_NUMBER")
	public String getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	@Column(name="CREDIT_CARD_TYPE")
	public String getCreditCardType() {
		return this.creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	@Column(name="EMAIL_ADDRESS")
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name="NAME_ON_CREDIT_CARD")
	public String getNameOnCreditCard() {
		return this.nameOnCreditCard;
	}

	public void setNameOnCreditCard(String nameOnCreditCard) {
		this.nameOnCreditCard = nameOnCreditCard;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SALES_ORDER_DATE")
	public Date getSalesOrderDate() {
		return this.salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	@Column(name="SALES_ORDER_STATUS")
	public String getSalesOrderStatus() {
		return this.salesOrderStatus;
	}

	public void setSalesOrderStatus(String salesOrderStatus) {
		this.salesOrderStatus = salesOrderStatus;
	}

	@Column(name="SHIP_TO_CITY")
	public String getShipToCity() {
		return this.shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	@Column(name="SHIP_TO_STATE")
	public String getShipToState() {
		return this.shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	@Column(name="SHIP_TO_STREET_NAME")
	public String getShipToStreetName() {
		return this.shipToStreetName;
	}

	public void setShipToStreetName(String shipToStreetName) {
		this.shipToStreetName = shipToStreetName;
	}

	@Column(name="SHIP_TO_STREET_NUMBER")
	public String getShipToStreetNumber() {
		return this.shipToStreetNumber;
	}

	public void setShipToStreetNumber(String string) {
		this.shipToStreetNumber = string;
	}

	@Column(name="SHIP_TO_UNIT_NUMBER")
	public String getShipToUnitNumber() {
		return this.shipToUnitNumber;
	}

	public void setShipToUnitNumber(String shipToUnitNumber) {
		this.shipToUnitNumber = shipToUnitNumber;
	}

	@Column(name="SHIP_TO_ZIP_CODE")
	public int getShipToZipCode() {
		return this.shipToZipCode;
	}

	public void setShipToZipCode(int shipToZipCode) {
		this.shipToZipCode = shipToZipCode;
	}

	@Column(name="SUB_TOTAL")
	public void setSubTotal(BigDecimal subTotal){
		this.subTotal = subTotal;
	}
	public BigDecimal getSubTotal() {
		return this.subTotal;
	}
	
	@Column(name="DISCOUNT")
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getDiscount(){
		return this.discount;
	}
	
	@Column(name="SALES_TAX")
	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = salesTax;
	}
	public BigDecimal getSalesTax() {
		return this.salesTax;
	}
	
	@Column(name="SHIPPING")
	public BigDecimal getShipping() {
		return this.shipping;
	}
	
	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	@Column(name="TOTAL")
	public BigDecimal getTotal() {
		return this.total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	@Column(name="PROMO_CODE")
	public String getPromoCode() {
		return this.promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	
	@OneToMany(targetEntity=SalesOrderLine.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="salesOrder", orphanRemoval=true)
	@JsonManagedReference
	@OrderBy("salesOrderLineUid")
	public List<SalesOrderLine> getLineItems() {
		return lineItems;
	}
	
	public void setLineItems (List<SalesOrderLine> lineItems) {
		this.lineItems = lineItems;
	}
	
	@ManyToOne(targetEntity=UserAccount.class, fetch=FetchType.LAZY, optional = true)
	@JoinColumn(name="USER_ACCOUNT_UID", nullable=true)
	@JsonManagedReference
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public void addOrderLineItem(SalesOrderLine lineItem) {
		lineItems.add(lineItem);
	}
	
	public void removeOrderLineItem(SalesOrderLine lineItem) {
		lineItems.remove(lineItem);
	}

}