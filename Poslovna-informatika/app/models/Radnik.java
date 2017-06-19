package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Radnik extends Model{
	
	@Column(nullable =false)
	public String email;
	
	@Column(nullable =false)
	public String sifra;
	
	@Column(nullable =false)
	public String nadimak;
	
	
	@ManyToOne
	public Banka banka;


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSifra() {
		return sifra;
	}


	public void setSifra(String sifra) {
		this.sifra = sifra;
	}


	public String getNadimak() {
		return nadimak;
	}


	public void setNadimak(String nadimak) {
		this.nadimak = nadimak;
	}


	public Banka getBanka() {
		return banka;
	}


	public void setBanka(Banka banka) {
		this.banka = banka;
	}


	public Radnik(String email, String sifra, String nadimak, Banka banka) {
		super();
		this.email = email;
		this.sifra = sifra;
		this.nadimak = nadimak;
		this.banka = banka;
	}


	public Radnik() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
