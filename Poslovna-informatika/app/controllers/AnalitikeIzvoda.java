package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.AnalitikaIzvoda;
import models.Banka;
import models.DnevnoStanjeRacuna;
import models.Racun;
import play.mvc.Controller;

public class AnalitikeIzvoda extends Controller{

	
	public static void show(String mode, Long selectedId)
	{
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(dnevnaStanjaRacuna,analitikeIzvoda,mode,selectedId);
	}
	
	
	public static void filter(Date datum, String smer, String valuta)
	{
		/*
		System.out.println("DOSLO DO OVDE");
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();
		char sm = smer.charAt(0);

		//String d = ai.getDatumAnalitike().getTime();
		List<AnalitikaIzvoda> analitikeIzvoda = new ArrayList<AnalitikaIzvoda>();
		if(datum!=null)
		{
			analitikeIzvoda = AnalitikaIzvoda.find("byDatumAnalitikeLikeAndSmerLikeAndSifraValuteLike", datum, sm, "%"+valuta+"%").fetch();

		}
		else
		{
			analitikeIzvoda = AnalitikaIzvoda.find("bySmerLikeAndSifraValuteLike", sm, "%"+valuta+"%").fetch();

		}
		String mode = "edit";
		*/
		//renderTemplate("AnalitikeIzvoda/show.html", dnevnaStanjaRacuna,analitikeIzvoda, mode );
	}
	
	
	public static void nextMehanizam(Long id)
	{
		System.out.println(id);
		
		DnevnoStanjeRacuna dsr = DnevnoStanjeRacuna.findById(id);
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.findAll();
		List<AnalitikaIzvoda> analitikeIzvodaZaPrikaz = new ArrayList<AnalitikaIzvoda>();
		List<DnevnoStanjeRacuna> dnevnaStanjaRacuna = DnevnoStanjeRacuna.findAll();
		
		for(AnalitikaIzvoda nm : analitikeIzvoda)
		{
			if(nm.dnevnoStanjeRacuna.id == dsr.id)
			{
				analitikeIzvodaZaPrikaz.add(nm);
				System.out.println("naslo neku analitiku izvoda..");
			}
		}
		String mode = "edit";
		analitikeIzvoda.clear();
		analitikeIzvoda.addAll(analitikeIzvodaZaPrikaz);
		//render(drzave,mestaZaPrikaz,"edit",0);
		Long idZaPrikaz = id;
		renderTemplate("AnalitikeIzvoda/show.html",dnevnaStanjaRacuna,analitikeIzvoda,mode,0,idZaPrikaz);
		
	}
	
}
