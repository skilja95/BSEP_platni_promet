package controllers;

import java.util.List;

import models.Banka;
import models.Drzava;
import play.mvc.Controller;

public class Banke extends Controller{
	
	public static void show(String mode, Long selectedIndex)
	{
		List<Banka> banke = Banka.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(banke,mode,selectedIndex);
	}
	
	
	
	public static void create(Banka banka )
	{
		validation.required(banka.nazivBanke);
		validation.required(banka.obracunskiRacun);
		validation.required(banka.sifraBanke);
		validation.required(banka.swiftKod);
		
		validation.maxSize(banka.nazivBanke, 50);
		validation.maxSize(banka.obracunskiRacun, 18);
		validation.maxSize(banka.sifraBanke, 3);
		validation.maxSize(banka.swiftKod, 8);
		
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		System.out.println("CREATE: "+banka.nazivBanke);
		//System.out.println("Banka: "+banka);
		banka.save();
		show("add",banka.id);
	}
	
	public static void edit(Banka banka)
	{
		validation.required(banka.nazivBanke);
		validation.required(banka.obracunskiRacun);
		validation.required(banka.sifraBanke);
		validation.required(banka.swiftKod);
		
		validation.maxSize(banka.nazivBanke, 50);
		validation.maxSize(banka.obracunskiRacun, 18);
		validation.maxSize(banka.sifraBanke, 3);
		validation.maxSize(banka.swiftKod, 8);
		
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("EDIT: "+banka.nazivBanke+", "+banka.id);
		banka.save();
		show("edit",banka.id);
		
	}
	
	public static void filter(Banka banka)
	{
		List<Banka> banke = Banka.find("bySifraBankeLikeAndNazivBankeLike", "%"+banka.sifraBanke+"%", "%"+banka.nazivBanke+"%").fetch();
		String mode = "edit";
		renderTemplate("Banke/show.html", banke, mode );
	}
	
	public static void delete(Long id)
	{
		Banka ban = Banka.findById(id);
		ban.delete();
		show("edit", ban.id-1);
	}

}
