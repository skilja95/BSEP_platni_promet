package models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Banka extends Model{
	//naziv banke, sifra banke, swift kod, obracunski racun, 
	
	@Column(nullable = false, length =50)
	public String nazivBanke;
	
	@Column(nullable = false, length =3)
	public String sifraBanke;
	
	@Column(nullable = false, length =8)
	public String swiftKod;
	
	@Column(nullable = false, length =18)
	public String obracunskiRacun;
	
	@OneToMany(mappedBy= "banka")
	public Collection<Racun> racuni;
	
	@OneToMany(mappedBy= "banka")
	public Collection<Klijent> klijenti;

	public Banka() {
		super();
	}

	public Banka(String nazivBanke, String sifraBanke, String swiftKod, String obracunskiRacun) {
		super();
		this.nazivBanke = nazivBanke;
		this.sifraBanke = sifraBanke;
		this.swiftKod = swiftKod;
		this.obracunskiRacun = obracunskiRacun;
	}

	public String getNazivBanke() {
		return nazivBanke;
	}

	public void setNazivBanke(String nazivBanke) {
		this.nazivBanke = nazivBanke;
	}

	public String getSifraBanke() {
		return sifraBanke;
	}

	public void setSifraBanke(String sifraBanke) {
		this.sifraBanke = sifraBanke;
	}

	public String getSwiftKod() {
		return swiftKod;
	}

	public void setSwiftKod(String swiftKod) {
		this.swiftKod = swiftKod;
	}

	public String getObracunskiRacun() {
		return obracunskiRacun;
	}

	public void setObracunskiRacun(String obracunskiRacun) {
		this.obracunskiRacun = obracunskiRacun;
	}

	public Collection<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(Collection<Racun> racuni) {
		this.racuni = racuni;
	}

	public Collection<Klijent> getKlijenti() {
		return klijenti;
	}

	public void setKlijenti(Collection<Klijent> klijenti) {
		this.klijenti = klijenti;
	}
	
	
	
	

}
