package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class StavkePrenosa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
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
