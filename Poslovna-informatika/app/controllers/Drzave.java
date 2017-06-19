package controllers;

import java.util.List;

import models.Drzava;
import play.mvc.Controller;

public class Drzave extends Controller {

	public static void show(String mode, Long selectedIndex) {
		List<Drzava> drzave = Drzava.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(drzave, mode, selectedIndex);
	}

	public static void create(Drzava drzava) {
		validation.required(drzava.oznaka);
		validation.required(drzava.naziv);
		validation.maxSize(drzava.oznaka, 3);
		validation.maxSize(drzava.naziv, 40);
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		
		System.out.println("CREATE: " + drzava.naziv + ", " + drzava.id);
		drzava.save();
		show("add", drzava.id);
	}

	public static void edit(Drzava drzava) {
		System.out.println("EDIT: " + drzava.naziv + ", " + drzava.id);
		validation.required(drzava.oznaka);
		validation.required(drzava.naziv);
		validation.maxSize(drzava.oznaka, 3);
		validation.maxSize(drzava.naziv, 40);
		if (validation.hasErrors()) {
			for (play.data.validation.Error error : validation.errors()) {
				System.out.println(error.message());
				validation.keep();
				show("add",null);
			}
		}
		drzava.save();
		show("edit", drzava.id);

	}

	public static void filter(Drzava drzava) {
		List<Drzava> drzave = Drzava
				.find("byOznakaLikeAndNazivLike", "%" + drzava.oznaka + "%", "%" + drzava.naziv + "%").fetch();
		String mode = "edit";
		renderTemplate("Drzave/show.html", drzave, mode);
	}

	public static void delete(Long id) {
		Drzava drz = Drzava.findById(id);
		drz.delete();
		show("edit", drz.id - 1);
	}

}
