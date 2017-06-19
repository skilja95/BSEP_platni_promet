package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class NaseljenoMesto extends Model{

	@Column(nullable = false, length =3)
	public String oznaka;
	@Column(nullable = false, length = 40)
	public String naziv;
	@Column(nullable = false, length = 5)
	public String postanskiBroj;
	
	
	@ManyToOne
	public Drzava drzava;


	public NaseljenoMesto(String oznaka, String naziv, String postanskiBroj,
			Drzava drzava) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.postanskiBroj = postanskiBroj;
		this.drzava = drzava;
	}
	
	
	
	
}
