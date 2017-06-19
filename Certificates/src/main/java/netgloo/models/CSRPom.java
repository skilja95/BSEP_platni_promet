package netgloo.models;

public class CSRPom {

	
	public String country;
	public String state;
	public String locale;
	public String organisation;
	public String organisationUnit;
	public String commonName;
	public String serialNumber;
	public CSRPom() {
		super();
	}
	public CSRPom(String country, String state, String locale, String organisation, String organisationUnit,
			String commonName, String serialNumber) {
		super();
		this.country = country;
		this.state = state;
		this.locale = locale;
		this.organisation = organisation;
		this.organisationUnit = organisationUnit;
		this.commonName = commonName;
		this.serialNumber = serialNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getOrganisationUnit() {
		return organisationUnit;
	}
	public void setOrganisationUnit(String organisationUnit) {
		this.organisationUnit = organisationUnit;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
	

}
