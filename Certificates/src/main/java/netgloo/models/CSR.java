package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "csr")
public class CSR {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer csrId;

	@NotNull
	private String status;
	
	@NotNull
	private String publicKey;
	
	@NotNull
	private String idCsrDoc;

	public CSR() {
		super();
	}

	public CSR(String status, String publicKey, String idCsrDoc) {
		super();
		this.status = status;
		this.publicKey = publicKey;
		this.idCsrDoc = idCsrDoc;
	}

	public Integer getCsrId() {
		return csrId;
	}

	public void setCsrId(Integer csrId) {
		this.csrId = csrId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getIdCsrDoc() {
		return idCsrDoc;
	}

	public void setIdCsrDoc(String idCsrDoc) {
		this.idCsrDoc = idCsrDoc;
	}
	
	
}
