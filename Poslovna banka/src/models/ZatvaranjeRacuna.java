package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ZatvaranjeRacuna {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	public Date datumZatvaranja;
	
	@Column(length=18 )
	public String prebacenoNaRacun;
	
	@ManyToOne
	public Racun racun;
	
	@ManyToOne
	public AnalitikaIzvoda racunIspraznjenNalogom;

	public Date getDatumZatvaranja() {
		return datumZatvaranja;
	}

	public void setDatumZatvaranja(Date datumZatvaranja) {
		this.datumZatvaranja = datumZatvaranja;
	}

	public String getPrebacenoNaRacun() {
		return prebacenoNaRacun;
	}

	public void setPrebacenoNaRacun(String prebacenoNaRacun) {
		this.prebacenoNaRacun = prebacenoNaRacun;
	}

	public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public AnalitikaIzvoda getRacunIspraznjenNalogom() {
		return racunIspraznjenNalogom;
	}

	public void setRacunIspraznjenNalogom(AnalitikaIzvoda racunIspraznjenNalogom) {
		this.racunIspraznjenNalogom = racunIspraznjenNalogom;
	}

	public ZatvaranjeRacuna(Date datumZatvaranja, String prebacenoNaRacun, Racun racun,
			AnalitikaIzvoda racunIspraznjenNalogom) {
		super();
		this.datumZatvaranja = datumZatvaranja;
		this.prebacenoNaRacun = prebacenoNaRacun;
		this.racun = racun;
		this.racunIspraznjenNalogom = racunIspraznjenNalogom;
	}

	public ZatvaranjeRacuna() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
