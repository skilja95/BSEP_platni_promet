package controllers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.xmlws.Nalozi;

public class TestModelXmlws {
	public void test() {
		try {
			
			System.out.println("[INFO] Example 5: JAXB unmarshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("model.xmlws");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// Unmarshalling generiše objektni model na osnovu XML fajla 
			Nalozi nalozi = (Nalozi) unmarshaller.unmarshal(new File("xmlPrimjer.xml"));

			// Prikazuje unmarshallovan objekat
			printNalozi(nalozi);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private void printNalozi(Nalozi nalozi) {
		
		// Prikaz svih odseka
		for(Nalozi.NalogZaPrenos nalogZaPrenos : nalozi.getNalogZaPrenos())
			System.out.println(nalogZaPrenos.getPodaciODuzniku().getIme());
		
	}
	
	
    public static void main( String[] args ) {
    	TestModelXmlws test = new TestModelXmlws();
    	test.test();
    }
}
