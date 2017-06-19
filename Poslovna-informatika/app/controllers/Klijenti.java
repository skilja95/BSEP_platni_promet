package controllers;

import java.util.ArrayList;
import java.util.List;

import models.AnalitikaIzvoda;
import models.Banka;
import models.DnevnoStanjeRacuna;
import models.Klijent;
import models.NaseljenoMesto;
import play.mvc.Controller;

public class Klijenti extends Controller{
	
	public static void show(String mode, Long selectedIndex)
	{
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.findAll();
		List<Banka> banke = Banka.findAll();
		List<Klijent> klijenti = Klijent.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(klijenti, naseljenaMesta, banke,mode,selectedIndex);
	}
	
	
	public static void nextMehanizam(Long id)
	{
		System.out.println(id);
		
		NaseljenoMesto naseljenoMesto = NaseljenoMesto.findById(id);
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.findAll();
		List<Banka> banke = Banka.findAll();
		List<Klijent> klijenti = Klijent.findAll();
		List<Klijent> klijentiZaPrikaz = new ArrayList<Klijent>();
		//List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();
		
		for(Klijent nm : klijenti)
		{
			if(nm.getNaseljenoMesto().id == naseljenoMesto.id)
			{
				klijentiZaPrikaz.add(nm);
				System.out.println("naslo neku analitiku izvoda..");
			}
		}
		String mode = "edit";
		klijenti.clear();
		klijenti.addAll(klijentiZaPrikaz);
		//render(drzave,mestaZaPrikaz,"edit",0);
		Long idZaPrikaz = id;
		renderTemplate("Klijenti/show.html",naseljenaMesta,banke,klijenti,mode,0,idZaPrikaz);
		
	}
	
	
	
	public static void create(Klijent klijent, Long klijent_naseljenoMesto, Long klijent_banka )
	{
		validation.required(klijent.jmbg);
		validation.required(klijent.ime);
		validation.required(klijent.prezime);
		validation.maxSize(klijent.jmbg, 13);
		validation.minSize(klijent.jmbg, 13);
		
		validation.maxSize(klijent.ime, 70);
		validation.maxSize(klijent.prezime, 70);
		validation.maxSize(klijent.adresa, 70);
		validation.maxSize(klijent.telefon, 25);
		validation.email(klijent.email);

		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("CREATE: "+klijent.id+", "+klijent.jmbg);
		//System.out.println("NAS MJ: "+ klijent.getNaseljenoMesto().getId());
		System.out.println("NASELJENO MJESTO ID: "+klijent_naseljenoMesto);
		NaseljenoMesto nm = NaseljenoMesto.findById(klijent_naseljenoMesto);
		Banka b = Banka.findById(klijent_banka);
		System.out.println("JEL PRAVNO: "+klijent.getPravno());
		if(klijent.getPravno()!=null && klijent.getPravno()==true)
		{
			
		}
		else
		{
			klijent.setPravno(false);
		}
		klijent.setBanka(b);
		klijent.setNaseljenoMesto(nm);
		klijent.save();
		show("add",klijent.id);
	}
	
	public static void edit(Klijent klijent,  Long klijent_naseljenoMesto, Long klijent_banka)
	{
		validation.required(klijent.jmbg);
		validation.required(klijent.ime);
		validation.required(klijent.prezime);
		validation.maxSize(klijent.jmbg, 13);
		validation.minSize(klijent.jmbg, 13);
		
		validation.maxSize(klijent.ime, 70);
		validation.maxSize(klijent.prezime, 70);
		validation.maxSize(klijent.adresa, 70);
		validation.maxSize(klijent.telefon, 25);
		validation.email(klijent.email);

		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("edit",null);
			}
		}
		
		System.out.println("EDIT: "+klijent.id+", "+klijent.jmbg);
		NaseljenoMesto nm = NaseljenoMesto.findById(klijent_naseljenoMesto);
		Banka b = Banka.findById(klijent_banka);
		System.out.println("JEL PRAVNO: "+klijent.getPravno());
		if(klijent.getPravno())
		{
			klijent.setPravno(true);
		}
		else
		{
			klijent.setPravno(false);
		}
		klijent.setBanka(b);
		klijent.setNaseljenoMesto(nm);
		klijent.save();
		show("edit",klijent.id);
		//System.out.println("OVo je naseljeno mjesto: " + klijent_naseljenoMesto);
		//System.out.println("OVo je banka: " + klijent_banka);
	}
	
	public static void filter(Klijent klijent)
	{
		//OVDE JOS DORADITI
		List<Klijent> klijenti = Klijent.find("byJmbgLikeAndImeLikeAndPrezimeLikeAndAdresaLikeAndTelefonLikeAndEmailLike", "%"+klijent.jmbg+"%", "%"+klijent.ime+"%", "%"+klijent.prezime+"%", "%"+klijent.adresa+"%", "%"+klijent.telefon+"%", "%"+klijent.email+"%").fetch();
		String mode = "edit";
		List<NaseljenoMesto> naseljenaMesta = NaseljenoMesto.findAll();
		List<Banka> banke = Banka.findAll();
		renderTemplate("Klijenti/show.html", klijenti,naseljenaMesta, banke, mode );
	}
	
	public static void delete(Long id)
	{
		Klijent kli = Klijent.findById(id);
		kli.delete();
		show("edit", kli.id-1);
	}
}
