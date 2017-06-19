package yu.ac.ns.ftn.informatika.ws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import yu.ac.ns.ftn.informatika.ws.address.AddressBook;
import yu.ac.ns.ftn.informatika.ws.address.data.Contact;

public class AddressBookClient {
	
	public void testIt() {
		try {
			//kreiranje servisa
			URL wsdl = new URL("http://localhost:8080/vezbe-ws/services/AddressBook?wsdl");
	    	QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/address", "AddressBookService");
	    	QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/address", "AddressBookPort");
	    	
	    	Service service = Service.create(wsdl, serviceName);
	    	
	        AddressBook address = service.getPort(portName, AddressBook.class);
			
			//kreiranje objekta
			String[] phones1 = {"222-333", "456-765"}; 
	        Contact contact1 = new Contact();
	        contact1.setFirstName("Mitar");
	        contact1.setLastName("Peric");
	        contact1.setEmail("mitar@pesma.com");
	        contact1.setPhones(phones1);
	        contact1.setDefaultPhoneIndex(1);
	        //poziv metode ws-a
	        System.out.println("\nIndex of added item " + address.addContact(contact1));
	        
	        //kreiranje objekta
	        String[] phones2 = {"232-333", "436-735"};
	        Contact contact2 = new Contact();
	        contact2.setFirstName("Miroslav");
	        contact2.setLastName("Mirkovic");
	        contact2.setEmail("slavuj@pesma.com");
	        contact2.setPhones(phones2);
	        contact2.setDefaultPhoneIndex(1);
	        //poziv metode ws-a
	        System.out.println("\nIndex of added item " + address.addContact(contact2));
	        
	        //poziv metode ws-a
	        String[] phones3 = {"444-444", "111-222", "888-888"};
	        System.out.println("\nIndex of added item " + address.addContact("Stevan", "Steovic", "stevo@pevanje.com", phones3, 3));

	        //ispis svih item-a
	        System.out.println("\nGeting all items");
	        Contact[] items = address.getContacts();
			if(items != null)
			    for(int i = 0 ; i < items.length; i++) 
			        System.out.println(items[i]);
			else
			    System.out.println("No item in address book!");
	        
	        //uklanjanje item-a
			System.out.println("\nGeting item on position 2");
	        System.out.println(address.getContact(2));
	        
	        //uklanjanje item-a
	        System.out.println("\nRemoving item on position 1");
	        address.removeContact(1);
	        
	        //prikaz svih item-a
	        System.out.println("\nGeting all items");
	        items = address.getContacts();
	        if(items != null)
			    for(int i = 0 ; i < items.length; i++) 
			        System.out.println(items[i]);
			else
			    System.out.println("No item in address book!");
	

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void main(String[] args) {
		AddressBookClient test = new AddressBookClient();
		
		test.testIt();
	}

}
