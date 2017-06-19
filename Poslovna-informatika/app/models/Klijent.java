package models;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Klijent extends Model {
	// jmbg, ime, prezime, adresa,telefon, email, pravno
	@Column(nullable = false, length = 13)
	public String jmbg;

	@Column(nullable = false, length = 70)
	public String ime;

	@Column(nullable = false, length = 70)
	public String prezime;

	@Column(nullable = true, length = 70)
	public String adresa;

	@Column(nullable = true, length = 25)
	public String telefon;

	@Column(nullable = true, length = 80)
	public String email;

	@Column(nullable = true)
	public Boolean pravno;

	@ManyToOne
	private NaseljenoMesto naseljenoMesto;

	@ManyToOne
	private Banka banka;

	@OneToMany(mappedBy = "klijent")
	public Collection<Racun> racuni;

	@OneToMany(mappedBy = "klijent")
	public List<PravnoLice> pravnaLica;

	public Klijent() {
		super();
	}

	public Klijent(String jmbg, String ime, String prezime, String adresa, String telefon, String email, Boolean pravno,
			NaseljenoMesto naseljenoMesto, Banka banka, Collection<Racun> racuni, List<PravnoLice> pravnaLica) {
		super();
		this.jmbg = jmbg;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.telefon = telefon;
		this.email = email;
		this.pravno = pravno;
		this.naseljenoMesto = naseljenoMesto;
		this.banka = banka;
		this.racuni = racuni;
		this.pravnaLica = pravnaLica;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPravno() {
		return pravno;
	}

	public void setPravno(Boolean pravno) {
		this.pravno = pravno;
	}

	public NaseljenoMesto getNaseljenoMesto() {
		return naseljenoMesto;
	}

	public void setNaseljenoMesto(NaseljenoMesto naseljenoMesto) {
		this.naseljenoMesto = naseljenoMesto;
	}

	public Banka getBanka() {
		return banka;
	}

	public void setBanka(Banka banka) {
		this.banka = banka;
	}

	public Collection<Racun> getRacuni() {
		return racuni;
	}

	public void setRacuni(Collection<Racun> racuni) {
		this.racuni = racuni;
	}

	public List<PravnoLice> getPravnaLica() {
		return pravnaLica;
	}

	public void setPravnaLica(List<PravnoLice> pravnaLica) {
		this.pravnaLica = pravnaLica;
	}

}
