package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class NaseljenoMesto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, length =3)
	public String oznaka;
	@Column(nullable = false, length = 40)
	public String naziv;
	@Column(nullable = false, length = 5)
	public String postanskiBroj;
	
	
	@ManyToOne
	public Drzava drzava;
	
	


	public NaseljenoMesto() {
		super();
	}


	public NaseljenoMesto(String oznaka, String naziv, String postanskiBroj,
			Drzava drzava) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getOznaka() {
		return oznaka;
	}


	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getPostanskiBroj() {
		return postanskiBroj;
	}


	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}


	public Drzava getDrzava() {
		return drzava;
	}


	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	
	
	
	
}
