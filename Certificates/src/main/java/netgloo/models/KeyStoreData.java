package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "keyStoreData")
public class KeyStoreData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer keyStoreId;

	@NotNull
	private String keyStoreFileName;
	
	@NotNull
	private String keyStoreType;

	public KeyStoreData() {
		super();
	}

	

	public KeyStoreData(String keyStoreFileName, String keyStoreType) {
		super();
		this.keyStoreFileName = keyStoreFileName;
		this.keyStoreType = keyStoreType;
	}



	public Integer getKeyStoreId() {
		return keyStoreId;
	}

	public void setKeyStoreId(Integer keyStoreId) {
		this.keyStoreId = keyStoreId;
	}

	public String getKeyStoreFileName() {
		return keyStoreFileName;
	}

	public void setKeyStoreFileName(String keyStoreFileName) {
		this.keyStoreFileName = keyStoreFileName;
	}



	public String getKeyStoreType() {
		return keyStoreType;
	}



	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}
	
	

}
