package controllers;

import java.util.List;

import models.SifarnikDelatnosti;
import models.Valuta;
import play.mvc.Controller;

public class SifarniciDelatnosti extends Controller {
	

	public static void show(String mode, Long selectedIndex)
	{
		List<SifarnikDelatnosti> sifarniciDelatnosti = SifarnikDelatnosti.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(sifarniciDelatnosti,mode,selectedIndex);
	}
	
	public static void create(SifarnikDelatnosti sifarnikDelatnosti )
	{
		validation.required(sifarnikDelatnosti.sifra);
		validation.required(sifarnikDelatnosti.naziv);
		validation.maxSize(sifarnikDelatnosti.sifra, 10);
		validation.maxSize(sifarnikDelatnosti.naziv, 10);
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("CREATE: "+sifarnikDelatnosti.naziv+", "+sifarnikDelatnosti.id);
		sifarnikDelatnosti.save();
		show("add",sifarnikDelatnosti.id);
	}
	
	public static void edit(SifarnikDelatnosti sifarnikDelatnosti)
	{
		validation.required(sifarnikDelatnosti.sifra);
		validation.required(sifarnikDelatnosti.naziv);
		validation.maxSize(sifarnikDelatnosti.sifra, 10);
		validation.maxSize(sifarnikDelatnosti.naziv, 10);
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("EDIT: "+sifarnikDelatnosti.naziv+", "+sifarnikDelatnosti.id);
		sifarnikDelatnosti.save();
		show("edit",sifarnikDelatnosti.id);
		
	}
	
	public static void filter(SifarnikDelatnosti sifarnikDelatnosti)
	{
		List<SifarnikDelatnosti> sifarniciDelatnosti = SifarnikDelatnosti.find("bySifraLikeAndNazivLike", "%"+sifarnikDelatnosti.sifra+"%", "%"+sifarnikDelatnosti.naziv+"%").fetch();
		String mode = "edit";
		renderTemplate("SifarniciDelatnosti/show.html", sifarniciDelatnosti, mode );
	}
	
	public static void delete(Long id)
	{
		SifarnikDelatnosti sifarnikDelatnosti = SifarnikDelatnosti.findById(id);
		sifarnikDelatnosti.delete();
		show("edit", sifarnikDelatnosti.id-1);
	}
}
