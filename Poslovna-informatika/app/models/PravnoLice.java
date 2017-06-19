package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class PravnoLice extends Model {

	@Column(nullable = false, length = 10)
	public String pib;

	@Column(nullable = true, length = 20)
	public String fax;

	@Column(nullable = false, length = 60)
	public String odobrio;

	@ManyToOne
	public Klijent klijent;

	@ManyToOne
	public SifarnikDelatnosti sifarnikDelatnosti;

	public PravnoLice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PravnoLice(String pib, String fax, String odobrio, Klijent klijent, SifarnikDelatnosti sifarnikDelatnosti) {
		super();
		this.pib = pib;
		this.fax = fax;
		this.odobrio = odobrio;
		this.klijent = klijent;
		this.sifarnikDelatnosti = sifarnikDelatnosti;
	}

	public String getPib() {
		return pib;
	}

	public void setPib(String pib) {
		this.pib = pib;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOdobrio() {
		return odobrio;
	}

	public void setOdobrio(String odobrio) {
		this.odobrio = odobrio;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public SifarnikDelatnosti getSifarnikDelatnosti() {
		return sifarnikDelatnosti;
	}

	public void setSifarnikDelatnosti(SifarnikDelatnosti sifarnikDelatnosti) {
		this.sifarnikDelatnosti = sifarnikDelatnosti;
	}

}
