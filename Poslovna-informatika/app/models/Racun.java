package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Racun extends Model {
	
	//broj racuna, status, datumOtvaranja, datumZatvaranja, Klijent, Banka
	
	@Column(nullable = false, length =18)
	public String brojRacuna;
	
	@Column(nullable = false)
	public Boolean status;
	
	@Column(nullable = false)
	public Date datumOtvaranja;
	
	@Column(nullable = true)
	public Date datumZatvaranja;
	
	@ManyToOne
	private Klijent klijent;
	
	@ManyToOne
	private Banka banka;
	
	@ManyToOne
	private Valuta valuta;
	
	@OneToMany(mappedBy= "racun")
	public List<DnevnoStanjeRacuna> dnevnaStanjaRacuna;
	
	
	
	

	public Racun() {
		super();
	}

	
	public Racun(String brojRacuna, Boolean status, Date datumOtvaranja, Date datumZatvaranja, Klijent klijent,
			Banka banka) {
		super();
		this.brojRacuna = brojRacuna;
		this.status = status;
		this.datumOtvaranja = datumOtvaranja;
		this.datumZatvaranja = datumZatvaranja;
		this.klijent = klijent;
		this.banka = banka;
		
	}
	
	
	
	

	public Racun(String brojRacuna, Boolean status, Date datumOtvaranja, Date datumZatvaranja, Klijent klijent,
			Banka banka, List<DnevnoStanjeRacuna> dnevnaStanjaRacuna) {
		super();
		this.brojRacuna = brojRacuna;
		this.status = status;
		this.datumOtvaranja = datumOtvaranja;
		this.datumZatvaranja = datumZatvaranja;
		this.klijent = klijent;
		this.banka = banka;
		this.dnevnaStanjaRacuna = dnevnaStanjaRacuna;
	}
	
	


	public Racun(String brojRacuna, Boolean status, Date datumOtvaranja, Date datumZatvaranja, Klijent klijent,
			Banka banka, Valuta valuta) {
		super();
		this.brojRacuna = brojRacuna;
		this.status = status;
		this.datumOtvaranja = datumOtvaranja;
		this.datumZatvaranja = datumZatvaranja;
		this.klijent = klijent;
		this.banka = banka;
		this.valuta = valuta;
	}

	
	
	public Valuta getValuta() {
		return valuta;
	}


	public void setValuta(Valuta valuta) {
		this.valuta = valuta;
	}


	public List<DnevnoStanjeRacuna> getDnevnaStanjaRacuna() {
		return dnevnaStanjaRacuna;
	}

	public void setDnevnaStanjaRacuna(List<DnevnoStanjeRacuna> dnevnaStanjaRacuna) {
		this.dnevnaStanjaRacuna = dnevnaStanjaRacuna;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getDatumOtvaranja() {
		return datumOtvaranja;
	}

	public void setDatumOtvaranja(Date datumOtvaranja) {
		this.datumOtvaranja = datumOtvaranja;
	}

	public Date getDatumZatvaranja() {
		return datumZatvaranja;
	}

	public void setDatumZatvaranja(Date datumZatvaranja) {
		this.datumZatvaranja = datumZatvaranja;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public Banka getBanka() {
		return banka;
	}

	public void setBanka(Banka banka) {
		this.banka = banka;
	}
	
	
}
