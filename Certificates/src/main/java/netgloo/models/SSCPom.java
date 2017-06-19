package netgloo.models;

public class SSCPom {
	public String fileName;
	public String alias;
	public String password;
	public String searchField;
	
	public String fileName1;
	public String password1;
	
	public String issuerSertificates;
	public String subjectCertificate;
	public String serialNumber;
	public String commonName;
	public String organisationUnit;
	public String organisationName;
	public String localityName;
	public String stateName;
	public String country;
	public String lifeTime;
	
	public String email;
	
	public SSCPom() {
		super();
	}


	public SSCPom(String fileName, String alias, String password, String serialNumber, String commonName,
			String organisationUnit, String organisationName, String localityName, String stateName, String country,
			String lifeTime) {
		super();
		this.fileName = fileName;
		this.alias = alias;
		this.password = password;
		this.serialNumber = serialNumber;
		this.commonName = commonName;
		this.organisationUnit = organisationUnit;
		this.organisationName = organisationName;
		this.localityName = localityName;
		this.stateName = stateName;
		this.country = country;
		this.lifeTime = lifeTime;
	}

	
	
	
	public String getSubjectCertificate() {
		return subjectCertificate;
	}


	public void setSubjectCertificate(String subjectCertificate) {
		this.subjectCertificate = subjectCertificate;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFileName1() {
		return fileName1;
	}


	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}


	public String getPassword1() {
		return password1;
	}


	public void setPassword1(String password1) {
		this.password1 = password1;
	}


	public String getSearchField() {
		return searchField;
	}


	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}


	public String getIssuerSertificates() {
		return issuerSertificates;
	}


	public void setIssuerSertificates(String issuerSertificates) {
		this.issuerSertificates = issuerSertificates;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getOrganisationUnit() {
		return organisationUnit;
	}

	public void setOrganisationUnit(String organisationUnit) {
		this.organisationUnit = organisationUnit;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}


}
