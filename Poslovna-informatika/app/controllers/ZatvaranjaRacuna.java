package controllers;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.ir.BankaIr;
import model.ir.DetaljiIzvodaRacuna;
import model.ir.DuznikIr;
import model.ir.IzvodRacuna;
import model.ir.PoverilacIr;
import model.mbp.BankaPosiljalac;
import model.mbp.BankaPrimalac;
import model.mbp.DetaljiPrenosa;
import model.mbp.MedjubankarskiPrenosi;
import models.AnalitikaIzvoda;
import models.Banka;
import models.DnevnoStanjeRacuna;
import models.Drzava;
import models.Klijent;
import models.MedjubankarskiPrenos;
import models.NaseljenoMesto;
import models.Racun;
import models.StavkePrenosa;
import models.ZatvaranjeRacuna;
import play.mvc.Controller;

public class ZatvaranjaRacuna extends Controller{
	
	public static void show(String mode, Long selectedId)
	{
		
		List<ZatvaranjeRacuna> zatvaranjaRacuna = ZatvaranjeRacuna.findAll();
		List<Racun> racuni = Racun.findAll();
		List<AnalitikaIzvoda> analitikeIzvoda = AnalitikaIzvoda.findAll();
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(zatvaranjaRacuna,racuni, analitikeIzvoda, mode,selectedId);
	}
	
	public static void create(ZatvaranjeRacuna zatvaranjeRacuna, Long racun)
	{
		System.out.println("RACUN ID JE " + racun);
		System.out.println("PREBACENO NA RACUN: "+zatvaranjeRacuna.prebacenoNaRacun);
		Racun racun1 = Racun.findById(racun);
		racun1.setStatus(false);
		racun1.save();
		java.util.List<Racun> racuni2=  Racun.find("byBrojRacuna", zatvaranjeRacuna.getPrebacenoNaRacun()).fetch();
		
		if(racuni2.size()!=0)
		{
			double lovaZaTransfer =0;
			Racun racun2 = racuni2.get(0);
			AnalitikaIzvoda aix=new AnalitikaIzvoda();
			String banka_id = session.get("banka_id");
			
			System.out.println("BANKA ID: "+banka_id);
			
			if(/*String.valueOf(racun1.getBanka().getId()).equals(banka_id)*/true)
			{
				//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
				//Date date = new Date();
				//System.out.println(/*dateFormat.format(date)*/);
				
				//System.out.println(cal.get); //2016/11/16 12:08:43
				//odradiAutomatizovaneRadnje( racun1, nalogZaPrenos);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date date = cal.getTime();
				//System.out.println(dateFormat.format(date));
				
				java.util.List<DnevnoStanjeRacuna> dsr_list = DnevnoStanjeRacuna.find("byDatumAndRacun", date,racun1).fetch();
				
				double iznos=0;
				DnevnoStanjeRacuna dsr = null;
				if(dsr_list.size()!=0)
				{
					dsr = dsr_list.get(0);
					
					
					double pare =dsr.novoStanje;/*Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());*/
					iznos=pare;
					if(iznos<0)
					{
						show("add",zatvaranjeRacuna.id);
						return;
					}
					lovaZaTransfer=iznos;
					double prethodni_p_n_t=dsr.prometNaTeret;
					//double prethodni_p_u_k=dsr.prometUKorist;
					double prethodno_novo_stanje=dsr.novoStanje;
					
					
					dsr.setNovoStanje(prethodno_novo_stanje-pare);
					dsr.setPrometNaTeret(prethodni_p_n_t-pare);
					//dsr.setPrometUKorist(prethodni_p_u_k);
				}
				else
				{
					java.util.List<DnevnoStanjeRacuna> dsr_list2 = DnevnoStanjeRacuna.find("byRacun",racun1).fetch();

					if(dsr_list2.size()==0)
					{
						double pare =0;/*Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());*/
						iznos=pare;
						if(iznos<0)
						{
							show("add",zatvaranjeRacuna.id);
							return;
						}
						lovaZaTransfer=iznos;
						dsr=new DnevnoStanjeRacuna(date,0,0-pare,0,0-pare,racun1);
					}
					else
					{
						DnevnoStanjeRacuna prethodno_dnevno_stanje = dsr_list2.get(0);
						
						for(DnevnoStanjeRacuna dsrx : dsr_list2)
						{
							if(dsrx.getId()>prethodno_dnevno_stanje.getId())
							{
								prethodno_dnevno_stanje=dsrx;
							}
						}
						
						double pare =prethodno_dnevno_stanje.getNovoStanje();/*Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());*/
						iznos=pare;
						if(iznos<0)
						{
							show("add",zatvaranjeRacuna.id);
							return;
						}
						lovaZaTransfer=iznos;
						double staro_stanje = prethodno_dnevno_stanje.getNovoStanje();
						dsr=new DnevnoStanjeRacuna(date,staro_stanje,0-pare,0,staro_stanje-pare,racun1);
						
					}
					
				}
				dsr.save();
				
				//iznos = Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());
				
				
				
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//String dateInString = nalogZaPrenos.getDatumValute();
				//Date datumAnal=null;
				Date datumAnal = date;
				/*
				try {
					datumAnal = sdf.parse(dateInString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				/*
				AnalitikaIzvoda ai = new AnalitikaIzvoda(date,'-',nalogZaPrenos.getPodaciODuzniku().getIme()+" "+nalogZaPrenos.getPodaciODuzniku().getPrezime(),
						nalogZaPrenos.getSvrhaPlacanja(),nalogZaPrenos.getPodaciOPoveriocu().getNaziv(),
						datumAnal,datumAnal,nalogZaPrenos.getTransferPodaciDuznik().getRacunDuznika(),
						nalogZaPrenos.getTransferPodaciDuznik().getModel(),
						nalogZaPrenos.getTransferPodaciDuznik().getPozivNaBrojDuznika(),
						nalogZaPrenos.getTransferPodaciPoverioc().getRacunPoverioca(),
						nalogZaPrenos.getTransferPodaciPoverioc().getModel(),
						nalogZaPrenos.getTransferPodaciPoverioc().getPozivNaBrojPoverioca(),
						iznos,nalogZaPrenos.getPodaciOPrenosu().getValuta(),dsr);
				*/
				aix = new AnalitikaIzvoda(date,'-',racun1.getKlijent().getIme()+racun1.getKlijent().getPrezime(),
						"zatvaranje racuna",racun2.getKlijent().getIme()+racun2.getKlijent().getPrezime(),date,date,racun1.getBrojRacuna(),"56","225",racun2.getBrojRacuna(),
						"56","225",iznos,racun1.getValuta().getSifra(),dsr);
				
				aix.save();
				
				
			}
			else
			{
				//TODO MEDJUBANKARSKI PRENOS VALJDA ... NESTO
				System.out.println("Ovde sad valjda treba eksportovati jer je ovo DUznicki koji je u DRUGOJ banci");
				//mislim da ovde ne treba eksport jer se ovo ne bi trebalo dogoditi
				//nisam siguran, provjeriti
			}
			double iznos=0;
			System.out.println("RACUN 1 BANKA ID: "+racun1.getBanka().getId());
			System.out.println("RACUN 2 BANKA ID: "+racun2.getBanka().getId());
			if(racun1.getBanka().getId()==racun2.getBanka().getId()/*String.valueOf(racun2.getBanka().getId()).equals(banka_id)*/)
			{
				//odradiAutomatizovaneRadnje( racun2, nalogZaPrenos);
				//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
				//Date date = new Date();
				//System.out.println(/*dateFormat.format(date)*/);
				
				//System.out.println(cal.get); //2016/11/16 12:08:43
				//odradiAutomatizovaneRadnje( racun1, nalogZaPrenos);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				//System.out.println("OVO JE: "+cal.getTime());
				//cal.set(Calendar.MILLISECOND,0);
				Date date = cal.getTime();
				
				
				
				//System.out.println(dateFormat.format(date));
				
				java.util.List<DnevnoStanjeRacuna> dsr_list = DnevnoStanjeRacuna.find("byDatumAndRacun", date,racun2).fetch();
				
				
				DnevnoStanjeRacuna dsr = null;
				if(dsr_list.size()!=0)
				{
					dsr = dsr_list.get(0);
					
					double pare = lovaZaTransfer;/*dsr.novoStanje;*//*Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());*/
					//double prethodni_p_n_t=dsr.prometNaTeret;
					iznos=pare;
					double prethodni_p_u_k=dsr.prometUKorist;
					double prethodno_novo_stanje=dsr.novoStanje;
					
					
					dsr.setNovoStanje(prethodno_novo_stanje+pare);
					//dsr.setPrometNaTeret(prethodni_p_n_t-pare);
					dsr.setPrometUKorist(prethodni_p_u_k+pare);
				}
				else
				{
					java.util.List<DnevnoStanjeRacuna> dsr_list2 = DnevnoStanjeRacuna.find("byRacun",racun2).fetch();

					if(dsr_list2.size()==0)
					{
						double pare =lovaZaTransfer/*0*/;/*Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());*/
						iznos=pare;
						dsr=new DnevnoStanjeRacuna(date,0,0,0+pare,0+pare,racun2);
					}
					else
					{
						DnevnoStanjeRacuna prethodno_dnevno_stanje = dsr_list2.get(0);
						
						for(DnevnoStanjeRacuna dsrx : dsr_list2)
						{
							if(dsrx.getId()>prethodno_dnevno_stanje.getId())
							{
								prethodno_dnevno_stanje=dsrx;
							}
						}
						
						double pare =lovaZaTransfer;/*prethodno_dnevno_stanje.getNovoStanje();*//*Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());*/
						iznos=pare;
						double staro_stanje = prethodno_dnevno_stanje.getNovoStanje();
						dsr=new DnevnoStanjeRacuna(date,staro_stanje,0,0+pare,staro_stanje+pare,racun2);
					}
					
				}
				dsr.save();
				
				
				//double iznos = Double.valueOf(nalogZaPrenos.getPodaciOPrenosu().getIznos());
				
				
				/*
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateInString = nalogZaPrenos.getDatumValute();
				Date datumAnal=null;
				try {
					datumAnal = sdf.parse(dateInString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				/*
				AnalitikaIzvoda ai = new AnalitikaIzvoda(date,'+',nalogZaPrenos.getPodaciODuzniku().getIme()+" "+nalogZaPrenos.getPodaciODuzniku().getPrezime(),
						nalogZaPrenos.getSvrhaPlacanja(),nalogZaPrenos.getPodaciOPoveriocu().getNaziv(),
						datumAnal,datumAnal,nalogZaPrenos.getTransferPodaciDuznik().getRacunDuznika(),
						nalogZaPrenos.getTransferPodaciDuznik().getModel(),
						nalogZaPrenos.getTransferPodaciDuznik().getPozivNaBrojDuznika(),
						nalogZaPrenos.getTransferPodaciPoverioc().getRacunPoverioca(),
						nalogZaPrenos.getTransferPodaciPoverioc().getModel(),
						nalogZaPrenos.getTransferPodaciPoverioc().getPozivNaBrojPoverioca(),
						iznos,nalogZaPrenos.getPodaciOPrenosu().getValuta(),dsr);
				ai.save();
				*/
				AnalitikaIzvoda ai = new AnalitikaIzvoda(date,'+',racun1.getKlijent().getIme()+racun1.getKlijent().getPrezime(),
						"zatvaranje racuna",racun2.getKlijent().getIme()+racun2.getKlijent().getPrezime(),date,date,racun1.getBrojRacuna(),"56","225",racun2.getBrojRacuna(),
						"56","225",iznos,racun1.getValuta().getSifra(),dsr);
				
				ai.save();
				
			}
			else
			{
				//TODO MEDJUBANKARSKI PRENOS VALJDA ... NESTO
				System.out.println(" OVO SAD VALJDA TREBA EKSPORTOVATI JER JE OVO PRIMAOCKI RACUN U DRUGOJ BANCI");
				/*
				 * Racun poverioca nije iz te banke koja je prijavljena
				 * Sto znaci da je potrebno napraviti medjubankarski prenos za narodnoj banci
				 * */
				
				//AKO JE HITNO ILI IZNOS VECI OD 250000
				String iznosIzXml = String.valueOf(iznos);/* nalogZaPrenos.getPodaciOPrenosu().getIznos();*/
				if(/*nalogZaPrenos.isHitno() || */Double.parseDouble(iznosIzXml)>250000) {
					System.out.println("USAO U IF");
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					String datum = dateFormat.format(date);
					//String izn = nalogZaPrenos.getPodaciOPrenosu().getIznos();
					//Double iznos = Double.parseDouble(izn);
					Banka bankaPosiljalac = racun1.getBanka();
					Banka bankaPrimalac = racun2.getBanka();
					MedjubankarskiPrenos mbp = new MedjubankarskiPrenos("MT103", datum, iznos, bankaPosiljalac, bankaPrimalac);
					
					StavkePrenosa sp = new StavkePrenosa(aix,mbp);
					sp.save();
					//i sada snimiti taj objekat
					//MARSHALLING
					try {
						JAXBContext context = JAXBContext.newInstance("model.mbp");
						// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
						Marshaller marshaller = context.createMarshaller();
						
						// Podešavanje marshaller-a
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
						
						MedjubankarskiPrenosi medjBanPrenosi = new MedjubankarskiPrenosi();
						//Izgen ponovo
						BankaPosiljalac bp1 = new BankaPosiljalac();
						bp1.setNaziv(bankaPosiljalac.getNazivBanke());
						bp1.setObracunskiRacun(bankaPosiljalac.getObracunskiRacun());
						bp1.setSifra(bankaPosiljalac.getSifraBanke());
						bp1.setSwiftKod(bankaPosiljalac.getSwiftKod());
						
						BankaPrimalac bp2 = new BankaPrimalac();
						bp2.setNaziv(bankaPrimalac.getNazivBanke());
						bp2.setObracunskiRacun(bankaPrimalac.getObracunskiRacun());
						bp2.setSifra(bankaPrimalac.getSifraBanke());
						bp2.setSwiftKod(bankaPrimalac.getSwiftKod());
						
						DetaljiPrenosa dp = new DetaljiPrenosa();
						dp.setDatum(datum);
						dp.setIznos(String.valueOf(iznos));
						dp.setPoruka("MT103");
						
						
						medjBanPrenosi.setBankaPosiljalac(bp1);
						medjBanPrenosi.setBankaPrimalac(bp2);
						medjBanPrenosi.setDetaljiPrenosa(dp);
						

					    FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/public/xmloviMBP/MBP "+medjBanPrenosi.getBankaPosiljalac().getNaziv() +".xml");
					    ObjectOutputStream oos = new ObjectOutputStream(fos);
					    oos.writeObject(medjBanPrenosi);
					   
						
						// Umesto System.out-a, može se koristiti FileOutputStream
						marshaller.marshal(medjBanPrenosi, System.out);
						marshaller.marshal(medjBanPrenosi, fos);
						 oos.close();
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					//UKOLIKO JE MANJE I NIJE HITNO, onda mozda sacuvati u bazu pa poslati nekad kasnije
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					String datum = dateFormat.format(date);
					//String izn = nalogZaPrenos.getPodaciOPrenosu().getIznos();
					//Double iznos = Double.parseDouble(izn);
					Banka bankaPosiljalac = racun1.getBanka();
					Banka bankaPrimalac = racun2.getBanka();
					MedjubankarskiPrenos mbp = new MedjubankarskiPrenos("MT103", datum, iznos, bankaPosiljalac, bankaPrimalac);
					
					mbp._save();
					StavkePrenosa sp = new StavkePrenosa(aix,mbp);
					sp.save();
				}
				
				
			}//END ELSE
			
			//AKO SU IM BANKE ISTE
			if(racun1.getBanka().getId()==racun2.getBanka().getId()/*String.valueOf(racun1.getBanka().getId()).equals(banka_id) && String.valueOf(racun2.getBanka().getId()).equals(banka_id)*/) {
				/*
				 * Ako oba racuna imaju istu banku, onda se vrsi izvod racuna
				 * Prikazati svakom klijentu njegov transfer i detalje transfera
				 * Za sada je to xml file pa mozda i baza dolazi u obzir
				 * 
				 * */
				try {
					JAXBContext context = JAXBContext.newInstance("model.ir");
					// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
					Marshaller marshaller = context.createMarshaller();
					
					// Podešavanje marshaller-a
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date date = new Date();
					String datum = dateFormat.format(date);
					
					IzvodRacuna ir = new IzvodRacuna();
					
					BankaIr bankaIr = new BankaIr();
					bankaIr.setNaziv(racun1.getBanka().getNazivBanke());
					bankaIr.setSifraBanke(racun1.getBanka().getSifraBanke());
					
					DuznikIr duznikIr = new DuznikIr();
					duznikIr.setBrojRacuna(racun1.getBrojRacuna());
					duznikIr.setIme(racun1.getKlijent().getIme()/*nalogZaPrenos.getPodaciODuzniku().getIme()*/);
					duznikIr.setPrezime(racun1.getKlijent().getPrezime()/*nalogZaPrenos.getPodaciODuzniku().getPrezime()*/);
					
					PoverilacIr poverilacIr = new PoverilacIr();
					poverilacIr.setBrojRacuna(racun2.getBrojRacuna());
					poverilacIr.setNaziv(racun2.getKlijent().getIme()+" "+racun2.getKlijent().getPrezime()/*nalogZaPrenos.getPodaciOPoveriocu().getNaziv()*/);
					
					DetaljiIzvodaRacuna dir = new DetaljiIzvodaRacuna(); 
					dir.setDatum(datum);
					dir.setIznos(String.valueOf(iznos)/*nalogZaPrenos.getPodaciOPrenosu().getIznos()*/);
					dir.setValuta(racun1.getValuta().getSifra()/*nalogZaPrenos.getPodaciOPrenosu().getValuta()*/);
					
					ir.setBankaIr(bankaIr);
					ir.setDetaljiIzvodaRacuna(dir);
					ir.setDuznikIr(duznikIr);
					ir.setPoverilacIr(poverilacIr);
					
					FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/public/xmloviIR/IR "+ir.getBankaIr().getSifraBanke()+" " +ir.getDuznikIr().getBrojRacuna() +  ".xml");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(ir);
					   
						
						// Umesto System.out-a, može se koristiti FileOutputStream
						marshaller.marshal(ir, System.out);
						marshaller.marshal(ir, fos);
						 oos.close();
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			Date date = new Date();
			ZatvaranjeRacuna zatRac = new ZatvaranjeRacuna(date,racun2.getBrojRacuna(),racun1,aix);
			zatRac.save();
			
		}
		
		//Racun racun2 = Racun.find
		//double stanjeNaracunu = racun1.get
		//zatvaranjeRacuna.racun=racun1;
		//zatvaranjeRacuna.save();
		
		/*
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = cal.getTime();
		
		java.util.List<DnevnoStanjeRacuna> dsr_list = DnevnoStanjeRacuna.find("byDatumAndRacun", date,racun1).fetch();
		*/
		
		
		show("add",zatvaranjeRacuna.id);
	}
	

	
	
	public static void filter(String filterx)
	{
		//OVDE JOS DORADITI
		//List<Klijent> racuni = Klijent.find("byBrojRacunaLikeAndStatusLike", "%"+racun.brojRacuna+"%", "%"+racun.status+"%").fetch();
		//String mode = "edit";
		List<ZatvaranjeRacuna> zatvaranjaRacuna = ZatvaranjeRacuna.find("byRacunLike", "%"+filterx+"%").fetch();
		
		renderTemplate("ZatvaranjeRacuna/show.html", zatvaranjaRacuna );
	}
	
	
	public static void nextMehanizam(Long id,String sta)
	{
		System.out.println(id);
		
		if(sta.equals("rac"))
		{
			Racun rac = Racun.findById(id);
			List<ZatvaranjeRacuna> zatvaranjaRacuna = ZatvaranjeRacuna.findAll();
			List<ZatvaranjeRacuna> zatvaranjaRacunaZaPrikaz = new ArrayList<ZatvaranjeRacuna>();
			
			for(ZatvaranjeRacuna nm : zatvaranjaRacuna)
			{
				if(nm.racun.id == rac.id)
				{
					zatvaranjaRacunaZaPrikaz.add(nm);
					System.out.println("naslo neko zatvaranje racuna ..");
				}
			}
			String mode = "edit";
			zatvaranjaRacuna.clear();
			zatvaranjaRacuna.addAll(zatvaranjaRacunaZaPrikaz);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("ZatvaranjaRacuna/show.html",zatvaranjaRacuna,mode,0,idZaPrikaz);
		}
		else
		{
			AnalitikaIzvoda ai = AnalitikaIzvoda.findById(id);
			List<ZatvaranjeRacuna> zatvaranjaRacuna = ZatvaranjeRacuna.findAll();
			List<ZatvaranjeRacuna> zatvaranjaRacunaZaPrikaz = new ArrayList<ZatvaranjeRacuna>();
			
			for(ZatvaranjeRacuna nm : zatvaranjaRacuna)
			{
				if(nm.racunIspraznjenNalogom.id == ai.id)
				{
					zatvaranjaRacunaZaPrikaz.add(nm);
					System.out.println("naslo neko zatvaranje racuna ..");
				}
			}
			String mode = "edit";
			zatvaranjaRacuna.clear();
			zatvaranjaRacuna.addAll(zatvaranjaRacunaZaPrikaz);
			//render(drzave,mestaZaPrikaz,"edit",0);
			Long idZaPrikaz = id;
			renderTemplate("ZatvaranjaRacuna/show.html",zatvaranjaRacuna,mode,0,idZaPrikaz);
		}
		
		
		
	}

}
