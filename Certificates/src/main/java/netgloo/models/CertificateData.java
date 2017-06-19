package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "certificateData")
public class CertificateData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer certificateDataId;

	@NotNull
	private String certificateDataSN;

	@NotNull
	private String certificateDataCA;

	@NotNull
	private String certificateDataAlias;

	@NotNull
	private String certificateDataKeyStoreName;
	
	@NotNull
	private Boolean status;

	public CertificateData() {
		super();
	}

	public CertificateData(String certificateDataSN, String certificateDataCA, String certificateDataAlias,
			String certificateDataKeyStoreName, Boolean status) {
		super();
		this.certificateDataSN = certificateDataSN;
		this.certificateDataCA = certificateDataCA;
		this.certificateDataAlias = certificateDataAlias;
		this.certificateDataKeyStoreName = certificateDataKeyStoreName;
		this.status= status;
	}

	public Integer getCertificateDataId() {
		return certificateDataId;
	}

	public void setCertificateDataId(Integer certificateDataId) {
		this.certificateDataId = certificateDataId;
	}

	public String getCertificateDataSN() {
		return certificateDataSN;
	}

	public void setCertificateDataSN(String certificateDataSN) {
		this.certificateDataSN = certificateDataSN;
	}

	public String getCertificateDataCA() {
		return certificateDataCA;
	}

	public void setCertificateDataCA(String certificateDataCA) {
		this.certificateDataCA = certificateDataCA;
	}

	public String getCertificateDataAlias() {
		return certificateDataAlias;
	}

	public void setCertificateDataAlias(String certificateDataAlias) {
		this.certificateDataAlias = certificateDataAlias;
	}

	public String getCertificateDataKeyStoreName() {
		return certificateDataKeyStoreName;
	}

	public void setCertificateDataKeyStoreName(String certificateDataKeyStoreName) {
		this.certificateDataKeyStoreName = certificateDataKeyStoreName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	

}
