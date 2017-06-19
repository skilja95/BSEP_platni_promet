package models;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class SifarnikDelatnosti {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length =10)
	public String sifra;
	
	@Column(nullable = false, length =10)
	public String naziv;
	
	@OneToMany(mappedBy= "sifarnikDelatnosti")
	public List<PravnoLice> pravnaLica;

	public SifarnikDelatnosti() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SifarnikDelatnosti(String sifra, String naziv, List<PravnoLice> pravnaLica) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.pravnaLica = pravnaLica;
	}

	public SifarnikDelatnosti(String sifra, String naziv) {
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

	public List<PravnoLice> getPravnaLica() {
		return pravnaLica;
	}

	public void setPravnaLica(List<PravnoLice> pravnaLica) {
		this.pravnaLica = pravnaLica;
	}
	
	
	
}
