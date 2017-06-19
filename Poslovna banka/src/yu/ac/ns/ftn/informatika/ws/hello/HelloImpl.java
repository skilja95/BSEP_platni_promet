package yu.ac.ns.ftn.informatika.ws.hello;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.PrivateKey;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import utils.KeyStoreReader;
import utils.XMLEncryptionUtility;
import utils.XMLSigningUtility;

@Stateless
@WebService(portName = "HelloPort", serviceName = "HelloService", targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/hello", endpointInterface = "yu.ac.ns.ftn.informatika.ws.hello.Hello")
/*
 * portName, serviceName treba da su isti kao i vrednosti upisane za java2ws
 * task u build.xml-u za vrednosti port.name i service.name
 */
public class HelloImpl implements Hello {
	public static int increment = 0;
	public HelloImpl() {
		System.out.println("Created HelloImpl object");
	}

	public String sayHi(String text) {
		System.out.println("Inovked sayHi method");
		System.out.println("\n================PRISTIGAO TEKST=========================");
		System.out.println(text.trim());
		System.out.println("\n================PRISTIGAO TEKST=========================");
		try {
			
			String endFile ="C:\\Users\\skilj\\Documents\\GitHub\\BSEP_platni_promet\\Poslovna banka\\keystores\\endFile "+increment +".xml";
			String keyStoreFile = "C:\\Users\\skilj\\Documents\\GitHub\\BSEP_platni_promet\\Poslovna banka\\keystores\\primer.jks";
			System.out.println("\nPutanja na koju ce se cuvati end file: " + endFile);
			increment++;
			XMLEncryptionUtility encUtility = new XMLEncryptionUtility();
			XMLSigningUtility sigUtility = new XMLSigningUtility();
			KeyStoreReader ksReader = new KeyStoreReader();
			PrivateKey privateKey = ksReader.readPrivateKey(keyStoreFile, "primer", "primer", "primer");
			
			//Generisanje dokumenta na osnovu teksta koji je pristigao
			Document doc = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(text);
			InputSource is = new InputSource(sr);
			doc = builder.parse(is);
			
			/*
			 * Prvo vrsim validaciju potpisa
			 * Ako potpis ne valja, dalje i ne pokusavam da procitam podatke
			 * */
			boolean res = sigUtility.verifySignature(doc);
			if(res) {
				System.out.println("\nPotpis je validan, dokument se desifruje.");
				doc = encUtility.decrypt(doc, privateKey);
				doc = removeMarks(doc);				
				saveDocument(doc, endFile);
				System.out.println("\nDesifrovanje zavrseno, tacka B je primila dokument");
				System.out.println("Za pregled rezultujucih dokumenata otvoriti  folder ===");
				//Ukoliko je proces uspesan garantovana je poverljivost i integritet poruke, kao i autentifikacija i neporecivost postupka slanja
			} else {
				System.out.println("\n Potpis nije validan, nema potrebe za desifrovanjem");
			}
			return "\nPotpis je validan i dokument je uspjesno desifrovan ";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "\nDesila se greska";
	}
	
	/**
	 * Snima DOM u XML fajl 
	 */
	private static void saveDocument(Document doc, String fileName) {
		try {
			File outFile = new File(fileName);
			FileOutputStream f = new FileOutputStream(outFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			
			transformer.transform(source, result);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Uklanjam oznake jer ne zelim da kad vratim krajnji file, imam podatke oko samog potpisa
	 * */
	private static Document removeMarks(Document doc) {
		Element element =  (Element) doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature").item(0);  
		element.getParentNode().removeChild(element);
		return doc;	
	}
	
	/*
	 * Metoda za validaciju seme sa dokumentom
	 * Nisam je pozivao jer dokument koji napravim, dobije i neke tagove java, object i slicno
	 * Pa nisam uspio da napravim metodu da snimi xml file as is
	 * Ovako bi izgledala metoda za validaciju seme, testirao sam sa problim fajlom i semom, radi
	 * */
	public static boolean validateSchema(Document doc, String sema){
    	try{
	    	SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
	    	Schema schema = factory.newSchema(new URL(sema));
	    	Validator validator = schema.newValidator();
	    	validator.validate(new DOMSource(doc));

    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }

}