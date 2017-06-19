package controllers;

import java.util.ArrayList;
import java.util.List;

import models.AnalitikaIzvoda;
import models.Banka;
import models.MedjubankarskiPrenos;
import models.Racun;
import models.ZatvaranjeRacuna;
import play.mvc.Controller;

public class MedjubankarskiPrenosii  extends Controller{

	public static void show(String mode, Long selectedId)
	{
		
		List<MedjubankarskiPrenos> medjubankarskiPrenosi = MedjubankarskiPrenos.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(medjubankarskiPrenosi,mode,selectedId);
	}
	
	
	public static void nextMehanizam(Long id,String sta)
	{
		System.out.println(id);
		
		if(sta.equals("bpos"))
		{
			Banka banka = Banka.findById(id);
			List<MedjubankarskiPrenos> mpsi = MedjubankarskiPrenos.findAll();
			List<MedjubankarskiPrenos> mpsiZaPrikaz = new ArrayList<MedjubankarskiPrenos>();
			
			for(MedjubankarskiPrenos nm : mpsi)
			{
				if(nm.getBankaPosiljalac().id == banka.id)
				{
					mpsiZaPrikaz.add(nm);
					System.out.println("naslo neko zatvaranje racuna ..");
				}
			}
			String mode = "edit";
			mpsi.clear();
			mpsi.addAll(mpsiZaPrikaz);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("MedjubankarskiPrenosii/show.html",mpsi,mode,0,idZaPrikaz);
		}
		else
		{
			Banka banka = Banka.findById(id);
			List<MedjubankarskiPrenos> mpsi = MedjubankarskiPrenos.findAll();
			List<MedjubankarskiPrenos> mpsiZaPrikaz = new ArrayList<MedjubankarskiPrenos>();
			
			for(MedjubankarskiPrenos nm : mpsi)
			{
				if(nm.getBankaPrimalac().id == banka.id)
				{
					mpsiZaPrikaz.add(nm);
					System.out.println("naslo neko zatvaranje racuna ..");
				}
			}
			String mode = "edit";
			mpsi.clear();
			mpsi.addAll(mpsiZaPrikaz);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("MedjubankarskiPrenosii/show.html",mpsi,mode,0,idZaPrikaz);
		}
		
		
		
	}
}
