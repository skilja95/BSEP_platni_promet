package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;


@Entity
public class DnevnoStanjeRacuna extends Model{
	
	@Column(nullable = false)
	public Date datum;
	
	@Column(nullable = false)
	public double prethodnoStanje;
	
	@Column(nullable = false)
	public double prometNaTeret;
	
	@Column(nullable = false)
	public double prometUKorist;
	
	@Column(nullable = false)
	public double novoStanje;
	
	@ManyToOne
	public Racun racun;
	
	/*
	@OneToMany(mappedBy= "dnevnoStanjeRacuna")
	public List<AnalitikaIzvoda> analitikeIzvoda;
	*/

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public double getPrethodnoStanje() {
		return prethodnoStanje;
	}

	public void setPrethodnoStanje(double prethodnoStanje) {
		this.prethodnoStanje = prethodnoStanje;
	}

	public double getPrometNaTeret() {
		return prometNaTeret;
	}

	public void setPrometNaTeret(double prometNaTeret) {
		this.prometNaTeret = prometNaTeret;
	}

	public double getPrometUKorist() {
		return prometUKorist;
	}

	public void setPrometUKorist(double prometUKorist) {
		this.prometUKorist = prometUKorist;
	}

	public double getNovoStanje() {
		return novoStanje;
	}

	public void setNovoStanje(double novoStanje) {
		this.novoStanje = novoStanje;
	}

	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}
/*
	public List<AnalitikaIzvoda> getAnalitikeIzvoda() {
		return analitikeIzvoda;
	}

	public void setAnalitikeIzvoda(List<AnalitikaIzvoda> analitikeIzvoda) {
		this.analitikeIzvoda = analitikeIzvoda;
	}
*/
	public DnevnoStanjeRacuna(Date datum, double prethodnoStanje, double prometNaTeret, double prometUKorist,
			double novoStanje, Racun racun/*, List<AnalitikaIzvoda> analitikeIzvoda*/) {
		super();
		this.datum = datum;
		this.prethodnoStanje = prethodnoStanje;
		this.prometNaTeret = prometNaTeret;
		this.prometUKorist = prometUKorist;
		this.novoStanje = novoStanje;
		this.racun = racun;
		//this.analitikeIzvoda = analitikeIzvoda;
	}

	public DnevnoStanjeRacuna() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
