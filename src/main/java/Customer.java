


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="customer")
public class Customer 
{

	private String customerId;
	private String companyName;
	private String telephone;
	private String notes;
	private int version;
	
	
	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}

	@XmlTransient
	private List<Call> calls;
	
	public Customer(String customerId, String companyName, String email,
			        String telephone, String notes) {
		this(customerId, companyName, notes);
		this.telephone = telephone;
	}
	
	
	public Customer(String customerId, String companyName, String notes)
	{
		this.customerId = customerId;
		this.companyName = companyName;
		this.notes = notes;
		this.calls = new ArrayList<Call>();
	}
	
	/**
	 * Add a new call for this customer
	 */
	public void addCall(Call callDetails) 
	{
		this.calls.add(callDetails);		
	}
	
	/**
	 * A Simple toString implementation
	 */
	public String toString()
	{
		return this.customerId + ": " + this.companyName ;
	}

	public String getCustomerId() 
	{
		return this.customerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getNotes() {
		return notes;
	}
	
	@XmlTransient
	public List<Call> getCalls() {
		return calls;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setCalls(List<Call> calls) {
		this.calls = calls;
	}

	// needed for JPA - ignore until then
	public Customer() {}
}
