package controllers;

import java.util.ArrayList;
import java.util.List;

import models.AnalitikaIzvoda;
import models.DnevnoStanjeRacuna;
import models.Klijent;
import models.PravnoLice;
import models.SifarnikDelatnosti;
import play.mvc.Controller;

public class PravnaLica extends Controller {

	public static void show(String mode, Long selectedIndex) {
		List<Klijent> klijenti = Klijent.findAll();
		List<SifarnikDelatnosti> sifarniciDelatnosti = SifarnikDelatnosti.findAll();
		List<PravnoLice> pravnaLica = PravnoLice.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(klijenti, sifarniciDelatnosti, pravnaLica, mode, selectedIndex);
	}
	
	
	public static void nextMehanizam(Long id,String sta)
	{
		if(sta.equals("kli"))
		{


			System.out.println(id);
			List<Klijent> klijenti = Klijent.findAll();
			List<SifarnikDelatnosti> sifarniciDelatnosti = SifarnikDelatnosti.findAll();
			//List<PravnoLice> pravnaLica = PravnoLice.findAll();
			Klijent klijent = Klijent.findById(id);
			List<PravnoLice> pravnaLica = PravnoLice.findAll();
			List<PravnoLice> pravnaLicaZaPrikaz = new ArrayList<PravnoLice>();
			//List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();

			for(PravnoLice nm : pravnaLica)
			{
				if(nm.getKlijent().id == klijent.id)
				{
					pravnaLicaZaPrikaz.add(nm);
					//System.out.println("naslo neku analitiku izvoda..");
				}
			}
			String mode = "edit";
			pravnaLica.clear();
			pravnaLica.addAll(pravnaLicaZaPrikaz);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("PravnaLica/show.html",sifarniciDelatnosti,klijenti,pravnaLica,mode,0,idZaPrikaz);
		}
		else
		{
			//System.out.println(id);
			List<Klijent> klijenti = Klijent.findAll();
			List<SifarnikDelatnosti> sifarniciDelatnosti = SifarnikDelatnosti.findAll();
			//List<PravnoLice> pravnaLica = PravnoLice.findAll();
			SifarnikDelatnosti sifarnikDelatnosti = SifarnikDelatnosti.findById(id);
			List<PravnoLice> pravnaLica = PravnoLice.findAll();
			List<PravnoLice> pravnaLicaZaPrikaz = new ArrayList<PravnoLice>();
			//List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();

			for(PravnoLice nm : pravnaLica)
			{
				if(nm.getSifarnikDelatnosti().id == sifarnikDelatnosti.id)
				{
					pravnaLicaZaPrikaz.add(nm);
					//System.out.println("naslo neku analitiku izvoda..");
				}
			}
			String mode = "edit";
			pravnaLica.clear();
			pravnaLica.addAll(pravnaLicaZaPrikaz);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("PravnaLica/show.html",sifarniciDelatnosti,klijenti,pravnaLica,mode,0,idZaPrikaz);
		}
		
	}

	public static void create(PravnoLice pravnoLice, Long klijent, Long sifarnikDelatnosti) {
		
		validation.required(pravnoLice.pib);
		validation.required(pravnoLice.odobrio);
		validation.maxSize(pravnoLice.pib, 10);
		validation.maxSize(pravnoLice.odobrio, 60);
		validation.maxSize(pravnoLice.fax, 20);
		
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("CREATE: " + pravnoLice.pib + ", " + pravnoLice.id);
		Klijent klijentx = Klijent.findById(klijent);
		SifarnikDelatnosti sifarnikDelatnostix = SifarnikDelatnosti.findById(sifarnikDelatnosti);
		pravnoLice.klijent = klijentx;
		pravnoLice.sifarnikDelatnosti = sifarnikDelatnostix;
		pravnoLice.save();
		show("add", pravnoLice.id);

	}

	public static void edit(PravnoLice pravnoLice, Long klijent, Long sifarnikDelatnosti) {
		validation.required(pravnoLice.pib);
		validation.required(pravnoLice.odobrio);
		validation.maxSize(pravnoLice.pib, 10);
		validation.maxSize(pravnoLice.odobrio, 60);
		validation.maxSize(pravnoLice.fax, 20);
		
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("EDIT: " + pravnoLice.pib + ", " + pravnoLice.id);
		Klijent klijentx = Klijent.findById(klijent);
		SifarnikDelatnosti sifarnikDelatnostix = SifarnikDelatnosti.findById(sifarnikDelatnosti);
		pravnoLice.klijent = klijentx;
		pravnoLice.sifarnikDelatnosti = sifarnikDelatnostix;
		pravnoLice.save();
		show("edit", pravnoLice.id);
	}

	public static void filter(PravnoLice pravnoLice) {
		List<PravnoLice> pravnaLica = PravnoLice.find("byPibLikeAndFaxLikeAndOdobrioLike", "%" + pravnoLice.pib + "%",
				"%" + pravnoLice.fax + "%", "%" + pravnoLice.odobrio + "%").fetch();
		String mode = "edit";
		renderTemplate("PravnaLica/show.html", pravnaLica, mode);
	}

	public static void delete(Long id) {
		PravnoLice pravnoLice = PravnoLice.findById(id);
		pravnoLice.delete();
		show("edit", pravnoLice.id - 1);
	}

	//EVO GA KONTROLER ZA PRAVNA LICA KOJI IZ NEKOG RAZLOGA NECE DA SE KOMITUJE O.o
}
