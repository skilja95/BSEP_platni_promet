package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;


@Entity
public class StavkePrenosa extends Model{

	
	@ManyToOne
	public AnalitikaIzvoda analitikaIzvoda;
	
	@ManyToOne
	public MedjubankarskiPrenos medjubankarskiPrenos;
	
	

	public AnalitikaIzvoda getAnalitikaIzvoda() {
		return analitikaIzvoda;
	}

	public void setAnalitikaIzvoda(AnalitikaIzvoda analitikaIzvoda) {
		this.analitikaIzvoda = analitikaIzvoda;
	}

	public MedjubankarskiPrenos getMedjubankarskiPrenos() {
		return medjubankarskiPrenos;
	}

	public void setMedjubankarskiPrenos(MedjubankarskiPrenos medjubankarskiPrenos) {
		this.medjubankarskiPrenos = medjubankarskiPrenos;
	}
	
	

	public StavkePrenosa(AnalitikaIzvoda analitikaIzvoda, MedjubankarskiPrenos medjubankarskiPrenos) {
		super();
		this.analitikaIzvoda = analitikaIzvoda;
		this.medjubankarskiPrenos = medjubankarskiPrenos;
	}

	public StavkePrenosa() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
