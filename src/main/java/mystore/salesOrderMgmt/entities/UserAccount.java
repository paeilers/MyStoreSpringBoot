package mystore.salesOrderMgmt.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the user_account database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="user_account")
@NamedQuery(name="UserAccount.findAll", query="SELECT u FROM UserAccount u")
public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ACCOUNT_UID")
	private int userAccountUid;

	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="IS_ACTIVE")
	private int isActive;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="PASSWORD")
	private String password;


	@XmlTransient
	@OneToMany(mappedBy="userAccount", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<Address> addresses;

	@XmlTransient
	@OneToMany(mappedBy="userAccount", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<CreditCardOnFile> creditCardsOnFile;

	@XmlTransient
	@OneToMany(targetEntity=SalesOrder.class, mappedBy="userAccount")
	@JsonBackReference
	private Set<SalesOrder> salesOrders;

	public UserAccount() {
		this.salesOrders = new HashSet<SalesOrder>();
	}

	public int getUserAccountUid() {
		return this.userAccountUid;
	}

	public void setUserAccountUid(int userAccountUid) {
		this.userAccountUid = userAccountUid;
	}
	
	public int getIsActive1() {
		return this.isActive;
	}
	
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<CreditCardOnFile> getCreditCardsOnFile() {
		return this.creditCardsOnFile;
	}

	public void setCreditCardsOnFile(Set<CreditCardOnFile> creditCardsOnFile) {
		this.creditCardsOnFile = creditCardsOnFile;
	}
	
	public Set<SalesOrder> getSalesOrders() {
		return this.salesOrders;
	}

	public void setSalesOrders(Set<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}
	
}