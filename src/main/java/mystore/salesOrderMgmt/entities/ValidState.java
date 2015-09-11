package mystore.salesOrderMgmt.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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


/**
 * The persistent class for the valid_value database table.
 * 
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name="valid_value")
@NamedQuery(name="ValidState.findAll", query="SELECT v FROM ValidState v, ValidValuesList l WHERE l.listName = 'States' AND l.validValuesListUid = v.stateValuesList")
public class ValidState implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int stateUid;

	@Column(name="LIST_VALUE")
	private String stateCode;

	@Column(name="VALUE_DESCRIPTION")
	private String stateName;

	@ManyToOne
	@JoinColumn(name="VALID_VALUES_LIST_UID")
	private ValidValuesList stateValuesList;

	public ValidState() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="VALID_VALUE_UID")
	public int getStateUid() {
		return this.stateUid;
	}

	public void setStateUid(int validValueUid) {
		this.stateUid = validValueUid;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public ValidValuesList getStateValuesList() {
		return this.stateValuesList;
	}

	public void setStateValuesList(ValidValuesList stateValuesList) {
		this.stateValuesList = stateValuesList;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}