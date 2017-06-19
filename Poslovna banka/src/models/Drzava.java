package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Drzava {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, length = 3)
	public String oznaka;
	@Column(nullable = false, length = 40)
	public String naziv;

	@OneToMany(mappedBy = "drzava")
	public List<NaseljenoMesto> naseljenaMesta;

	public Drzava(String oznaka, String naziv, List<NaseljenoMesto> naseljenaMesta) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.naseljenaMesta = naseljenaMesta;
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

	public List<NaseljenoMesto> getNaseljenaMesta() {
		return naseljenaMesta;
	}

	public void setNaseljenaMesta(List<NaseljenoMesto> naseljenaMesta) {
		this.naseljenaMesta = naseljenaMesta;
	}

	public Drzava() {
		super();
	}

}
