package yu.ac.ns.ftn.informatika.ws.address;

import java.util.Enumeration;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.jws.WebService;

import yu.ac.ns.ftn.informatika.ws.address.data.Contact;

@Stateless
@WebService(portName = "AddressBookPort", 
			serviceName = "AddressBookService", 
			targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address", 
			endpointInterface = "yu.ac.ns.ftn.informatika.ws.address.AddressBook", 
			wsdlLocation = "WEB-INF/wsdl/AddressBook.wsdl",
			name = "AddressBook")
public class AddressBookImpl implements AddressBook {
	
	private Vector<Contact> contacts = new Vector<Contact>();
	
	public AddressBookImpl() {
		System.out.println("Created AddressBookImpl object");
	}

	public int addContact(String firstName, String lastName, String email, String[] phones, int defaultPhoneIndex) {
		System.out.println("Invoked addContact");
		contacts.add(new Contact(firstName, lastName, email, phones, defaultPhoneIndex));
        return contacts.size() - 1;
	}

	public int addContact(Contact contact) {
		contacts.add(contact);
		System.out.println("Invoked addContact");
        return contacts.size() - 1;
	}

	public Contact getContact(int index) {
		System.out.println("Invoked getContact");
		return (Contact)contacts.get(index);
	}
	
	public Contact[] getContacts() {
		System.out.println("Invoked getContacts");
		return toArray();
	}

	public void removeContact(int index) {
		System.out.println("Invoked removeContact");
		contacts.remove(index);

	}
	
	private Contact[] toArray() {
        if(contacts.size() == 0)
            return null;
        
        Contact[] retVal = new Contact[contacts.size()];
        Enumeration<Contact> enumeration = contacts.elements();
        
        int i = 0;
        while(enumeration.hasMoreElements()) {
            Contact item = enumeration.nextElement();
            retVal[i] = item;
            i++;
        }
        
        return retVal;
    }

}
