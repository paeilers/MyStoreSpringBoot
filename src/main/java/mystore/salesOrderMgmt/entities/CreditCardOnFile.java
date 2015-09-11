package mystore.salesOrderMgmt.entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the credit_card_on_file database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="credit_card_on_file")
@NamedQuery(name="CreditCardOnFile.findAll", query="SELECT c FROM CreditCardOnFile c")
public class CreditCardOnFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ccOnFileUid;
	private String cardNumber;
	private String cardType;
	private int csv;
	private Date expirationDate;
	private String nameOnCard;
	private UserAccount userAccount;

	public CreditCardOnFile() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CC_ON_FILE_UID")
	public int getCcOnFileUid() {
		return this.ccOnFileUid;
	}

	public void setCcOnFileUid(int ccOnFileUid) {
		this.ccOnFileUid = ccOnFileUid;
	}

	@Column(name="CARD_NUMBER")
	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Column(name="CARD_TYPE")
	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getCsv() {
		return this.csv;
	}

	public void setCsv(int csv) {
		this.csv = csv;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXPIRATION_DATE")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name="NAME_ON_CARD")
	public String getNameOnCard() {
		return this.nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	@ManyToOne(targetEntity=UserAccount.class, fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ACCOUNT_UID")
	@JsonManagedReference
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}