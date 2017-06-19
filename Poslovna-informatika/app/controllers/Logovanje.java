package controllers;

import java.util.List;

import models.Radnik;
import play.mvc.Controller;

public class Logovanje extends Controller{
	
	
	
	public static void show(String mode)
	{
		if(mode == null || mode.equals(""))
			mode = "login";
		render(mode);
	}
	
	
	public static void login(Radnik radnik)
	{
		System.out.println("Radnik: "+radnik.nadimak+" , "+radnik.sifra);
		
		//List<Radnik> rr= Radnik.find("byNadimakAndSifra", "%"+radnik.nadimak+"%","%"+radnik.sifra+"%").fetch();
		
		List<Radnik> rr = Radnik.findAll();
		boolean naslo = false;
		
		for(Radnik r : rr)
		{
			//System.out.println("NADJENO: "+ r.id);
			if(r.nadimak.equals(radnik.nadimak) && r.sifra.equals(radnik.sifra))
			{
				System.out.println("R ID:"+r.id);
				naslo = true;
				session.put("radnik_nadimak", r.nadimak);
				session.put("radnik_sifra", r.sifra);
				session.put("radnik_id", r.id);
				session.put("banka_id", r.banka.id);
				//session.pu
				String mode = "";
				System.out.println("Doslo do ovde");
				//renderTemplate("UcitavanjeNalogaZaPrenos/show.html", mode );
				UcitavanjeNalogaZaPrenos.show(mode);
				return;
			}
		}
		
		String mode = "login";
		renderTemplate("Logovanje/show.html", mode );
		/*
		if(rr!=null && rr.size()!=0)
		{
			System.out.println("R ID:"+rr.get(0).id);
		}
		else
		{
			System.out.println("Nije moglo naci..");
		}
		*/
		//session.
		//OVDE JOS DORADITI
		//List<Klijent> racuni = Klijent.find("byBrojRacunaLikeAndStatusLike", "%"+racun.brojRacuna+"%", "%"+racun.status+"%").fetch();
		//String mode = "edit";
		//renderTemplate("Racuni/show.html", racuni, mode );
	}

}
