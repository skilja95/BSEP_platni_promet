package yu.ac.ns.ftn.informatika.ws.client;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Service;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import yu.ac.ns.ftn.informatika.ws.hello.Hello;

public class HelloClient {

    public void testIt() {
    	
		try {
			//kreiranje web servisa
			URL wsdlLocation = new URL("http://localhost:8080/poslovnaBanka/services/Hello?wsdl");
			QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "HelloService");
			QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "HelloPort");

			Service service = Service.create(wsdlLocation, serviceName);
			
			Hello hello = service.getPort(portName, Hello.class); 
			
			//poziv web servisa
			String signed = "C:\\Users\\skilj\\Documents\\GitHub\\BSEP_platni_promet\\Poslovna-informatika\\public\\keystores\\KOMBANK 0 signedFile.xml";
			Document doc = loadDocument(signed);
			String s = toString1(doc);
			String response = hello.sayHi(s);
			System.out.println("Response from WS: " + response);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		
		HelloClient client = new HelloClient();
		client.testIt();
    }
	
	private static Document loadDocument(String file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(file));

			return document;
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static String toString1(Document doc) {
		System.out.println("NOVI DOM");
		  try
		    {
		       DOMSource domSource = new DOMSource(doc);
		       StringWriter writer = new StringWriter();
		       StreamResult result = new StreamResult(writer);
		       TransformerFactory tf = TransformerFactory.newInstance();
		       Transformer transformer = tf.newTransformer();
		       transformer.transform(domSource, result);
		       return writer.toString();
		    }
		    catch(TransformerException ex)
		    {
		       ex.printStackTrace();
		       return null;
		    }
	}

}