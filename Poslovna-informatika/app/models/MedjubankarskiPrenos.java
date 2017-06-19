package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class MedjubankarskiPrenos extends Model{
	
	@Column(nullable = false, length =5)
	public String vrstaPoruke;
	
	@Column(nullable = false)
	public String datum;
	
	@Column(nullable = false)
	public Double iznos;
	
	@ManyToOne
	private Banka bankaPosiljalac;
	
	@ManyToOne
	private Banka bankaPrimalac;


	public MedjubankarskiPrenos() {
		super();
	}

	




	

	public MedjubankarskiPrenos(String vrstaPoruke, String datum, Double iznos, Banka bankaPosiljalac,
			Banka bankaPrimalac) {
		super();
		this.vrstaPoruke = vrstaPoruke;
		this.datum = datum;
		this.iznos = iznos;
		this.bankaPosiljalac = bankaPosiljalac;
		this.bankaPrimalac = bankaPrimalac;
	}



	public String getVrstaPoruke() {
		return vrstaPoruke;
	}

	public void setVrstaPoruke(String vrstaPoruke) {
		this.vrstaPoruke = vrstaPoruke;
	}



	public String getDatum() {
		return datum;
	}



	public void setDatum(String datum) {
		this.datum = datum;
	}



	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}

	public Banka getBankaPosiljalac() {
		return bankaPosiljalac;
	}

	public void setBankaPosiljalac(Banka bankaPosiljalac) {
		this.bankaPosiljalac = bankaPosiljalac;
	}

	public Banka getBankaPrimalac() {
		return bankaPrimalac;
	}

	public void setBankaPrimalac(Banka bankaPrimalac) {
		this.bankaPrimalac = bankaPrimalac;
	}
	
	

}
