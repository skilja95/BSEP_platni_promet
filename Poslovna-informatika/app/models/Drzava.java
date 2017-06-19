package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;



import play.db.jpa.Model;

@Entity
public class Drzava extends Model{
	
	@Column(nullable = false, length=3)
	public String oznaka;
	@Column(nullable = false, length=40)
	public String naziv;
	
	
	@OneToMany(mappedBy= "drzava")
	public List<NaseljenoMesto> naseljenaMesta;





	public Drzava(String oznaka, String naziv,
			List<NaseljenoMesto> naseljenaMesta) {
		super();
		this.oznaka = oznaka;
		this.naziv = naziv;
		this.naseljenaMesta = naseljenaMesta;
	}


	
	
	

}
