package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Valuta extends Model{
	
	@Column(nullable = false, length =3)
	public String sifra;
	
	@Column(nullable = false, length =30)
	public String naziv;

	public Valuta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Valuta(String sifra, String naziv) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
}
