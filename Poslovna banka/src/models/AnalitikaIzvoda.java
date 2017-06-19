package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class AnalitikaIzvoda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	public Date datumAnalitike;
	
	
	@Column(nullable = false)
	public char smer;
	
	@Column
	public String duznik;
	
	@Column
	public String svrhaPlacanja;
	
	@Column
	public String primaoc;
	
	@Column
	public Date datumNaloga;
	
	@Column
	public Date datumValute;
	
	@Column(length = 18)
	public String racunDuznika;
	
	@Column(length = 2)
	public String modelZaduzenja;
	
	@Column(length = 22)
	public String pozivNaBrojZaduzenja;
	
	@Column(length=18)
	public String racunPoverioca;
	
	@Column(length = 2)
	public String modelOdobrenja;
	
	@Column(length=22)
	public String pozivNaBrojOdobrenja;
	
	@Column
	public double iznos;
	
	@Column(length = 3)
	public String sifraValute;
	
	@ManyToOne
	public DnevnoStanjeRacuna dnevnoStanjeRacuna;
	/*
	@OneToMany(mappedBy= "analitikaIzvoda")
	public List<StavkePrenosa> stavkePrenosa;
	*/

	public Date getDatumAnalitike() {
		return datumAnalitike;
	}

	public void setDatumAnalitike(Date datumAnalitike) {
		this.datumAnalitike = datumAnalitike;
	}

	public char getSmer() {
		return smer;
	}

	public void setSmer(char smer) {
		this.smer = smer;
	}

	public String getDuznik() {
		return duznik;
	}

	public void setDuznik(String duznik) {
		this.duznik = duznik;
	}

	public String getSvrhaPlacanja() {
		return svrhaPlacanja;
	}

	public void setSvrhaPlacanja(String svrhaPlacanja) {
		this.svrhaPlacanja = svrhaPlacanja;
	}

	public String getPrimaoc() {
		return primaoc;
	}

	public void setPrimaoc(String primaoc) {
		this.primaoc = primaoc;
	}

	public Date getDatumNaloga() {
		return datumNaloga;
	}

	public void setDatumNaloga(Date datumNaloga) {
		this.datumNaloga = datumNaloga;
	}

	public Date getDatumValute() {
		return datumValute;
	}

	public void setDatumValute(Date datumValute) {
		this.datumValute = datumValute;
	}

	public String getRacunDuznika() {
		return racunDuznika;
	}

	public void setRacunDuznika(String racunDuznika) {
		this.racunDuznika = racunDuznika;
	}

	public String getModelZaduzenja() {
		return modelZaduzenja;
	}

	public void setModelZaduzenja(String modelZaduzenja) {
		this.modelZaduzenja = modelZaduzenja;
	}

	public String getPozivNaBrojZaduzenja() {
		return pozivNaBrojZaduzenja;
	}

	public void setPozivNaBrojZaduzenja(String pozivNaBrojZaduzenja) {
		this.pozivNaBrojZaduzenja = pozivNaBrojZaduzenja;
	}

	public String getRacunPoverioca() {
		return racunPoverioca;
	}

	public void setRacunPoverioca(String racunPoverioca) {
		this.racunPoverioca = racunPoverioca;
	}

	public String getModelOdobrenja() {
		return modelOdobrenja;
	}

	public void setModelOdobrenja(String modelOdobrenja) {
		this.modelOdobrenja = modelOdobrenja;
	}

	public String getPozivNaBrojOdobrenja() {
		return pozivNaBrojOdobrenja;
	}

	public void setPozivNaBrojOdobrenja(String pozivNaBrojOdobrenja) {
		this.pozivNaBrojOdobrenja = pozivNaBrojOdobrenja;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}

	public String getSifraValute() {
		return sifraValute;
	}

	public void setSifraValute(String sifraValute) {
		this.sifraValute = sifraValute;
	}

	public DnevnoStanjeRacuna getDnevnoStanjeRacuna() {
		return dnevnoStanjeRacuna;
	}

	public void setDnevnoStanjeRacuna(DnevnoStanjeRacuna dnevnoStanjeRacuna) {
		this.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
	}
/*
	public List<StavkePrenosa> getStavkePrenosa() {
		return stavkePrenosa;
	}

	public void setStavkePrenosa(List<StavkePrenosa> stavkePrenosa) {
		this.stavkePrenosa = stavkePrenosa;
	}
*/
	public AnalitikaIzvoda(Date datumAnalitike, char smer, String duznik, String svrhaPlacanja, String primaoc,
			Date datumNaloga, Date datumValute, String racunDuznika, String modelZaduzenja, String pozivNaBrojZaduzenja,
			String racunPoverioca, String modelOdobrenja, String pozivNaBrojOdobrenja, double iznos, String sifraValute,
			DnevnoStanjeRacuna dnevnoStanjeRacuna/*, List<StavkePrenosa> stavkePrenosa*/) {
		super();
		this.datumAnalitike = datumAnalitike;
		this.smer = smer;
		this.duznik = duznik;
		this.svrhaPlacanja = svrhaPlacanja;
		this.primaoc = primaoc;
		this.datumNaloga = datumNaloga;
		this.datumValute = datumValute;
		this.racunDuznika = racunDuznika;
		this.modelZaduzenja = modelZaduzenja;
		this.pozivNaBrojZaduzenja = pozivNaBrojZaduzenja;
		this.racunPoverioca = racunPoverioca;
		this.modelOdobrenja = modelOdobrenja;
		this.pozivNaBrojOdobrenja = pozivNaBrojOdobrenja;
		this.iznos = iznos;
		this.sifraValute = sifraValute;
		this.dnevnoStanjeRacuna = dnevnoStanjeRacuna;
		//this.stavkePrenosa = stavkePrenosa;
	}

	public AnalitikaIzvoda() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
