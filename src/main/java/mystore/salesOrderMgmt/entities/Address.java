package mystore.salesOrderMgmt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int addressUid;
	private String city;
	private int isDefaultBillTo;
	private int isDefaultShipTo;
	private String state;
	private String streetName;
	private String streetNumber;
	private String unitNumber;
	private int zipCode;
	private UserAccount userAccount;

	public Address() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ADDRESS_UID")
	public int getAddressUid() {
		return this.addressUid;
	}

	public void setAddressUid(int addressUid) {
		this.addressUid = addressUid;
	}

	@Column(name="CITY")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="IS_DEFAULT_BILL_TO")
	public int getIsDefaultBillTo() {
		return this.isDefaultBillTo;
	}

	public void setIsDefaultBillTo(int i) {
		this.isDefaultBillTo = i;
	}

	@Column(name="IS_DEFAULT_SHIP_TO")
	public int getIsDefaultShipTo() {
		return this.isDefaultShipTo;
	}

	public void setIsDefaultShipTo(int i) {
		this.isDefaultShipTo = i;
	}

	@Column(name="STATE")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="STREET_NAME")
	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@Column(name="STREET_NUMBER")
	public String getStreetNumber() {
		return this.streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	@Column(name="UNIT_NUMBER")
	public String getUnitNumber() {
		return this.unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	@Column(name="ZIP_CODE")
	public int getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	//bi-directional many-to-one association to UserAccount
	@ManyToOne(targetEntity=UserAccount.class, fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ACCOUNT_UID")
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}