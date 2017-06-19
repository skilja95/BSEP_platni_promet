package controllers;

import java.util.ArrayList;
import java.util.List;

import models.AnalitikaIzvoda;
import models.MedjubankarskiPrenos;
import models.Racun;
import models.StavkePrenosa;
import models.ZatvaranjeRacuna;
import play.mvc.Controller;

public class StavkePrenosaa extends Controller{
	
	public static void show(String mode, Long selectedId)
	{
		
		List<StavkePrenosa> stavkePrenosaList = StavkePrenosa.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(stavkePrenosaList,mode,selectedId);
	}
	
	
	public static void nextMehanizam(Long id,String sta)
	{
		System.out.println(id);
		
		if(sta.equals("ai"))
		{
			AnalitikaIzvoda ai = AnalitikaIzvoda.findById(id);
			List<StavkePrenosa> stavkePrenosaList = StavkePrenosa.findAll();
			List<StavkePrenosa> stavkePrenosaZaPrikazList = new ArrayList<StavkePrenosa>();
			
			for(StavkePrenosa nm : stavkePrenosaList)
			{
				if(nm.analitikaIzvoda.id == ai.id)
				{
					stavkePrenosaZaPrikazList.add(nm);
					System.out.println("naslo stavke prenosa racuna ..");
				}
			}
			String mode = "edit";
			stavkePrenosaList.clear();
			stavkePrenosaList.addAll(stavkePrenosaZaPrikazList);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("StavkePrenosaa/show.html",stavkePrenosaList,mode,0,idZaPrikaz);
		}
		else
		{
			MedjubankarskiPrenos mp = MedjubankarskiPrenos.findById(id);
			List<StavkePrenosa> stavkePrenosaList = StavkePrenosa.findAll();
			List<StavkePrenosa> stavkePrenosaZaPrikazList = new ArrayList<StavkePrenosa>();
			
			for(StavkePrenosa nm : stavkePrenosaList)
			{
				if(nm.medjubankarskiPrenos.id == mp.id)
				{
					stavkePrenosaZaPrikazList.add(nm);
					System.out.println("naslo stavke prenosa racuna ..");
				}
			}
			String mode = "edit";
			stavkePrenosaList.clear();
			stavkePrenosaList.addAll(stavkePrenosaZaPrikazList);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("StavkePrenosaa/show.html",stavkePrenosaList,mode,0,idZaPrikaz);
		}

	}
}
