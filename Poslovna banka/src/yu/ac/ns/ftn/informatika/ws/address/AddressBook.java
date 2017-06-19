package yu.ac.ns.ftn.informatika.ws.address;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import yu.ac.ns.ftn.informatika.ws.address.data.Contact;

@WebService(name = "AddressBook",
			targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/address")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL) 
public interface AddressBook {
	
	@WebMethod(operationName = "addContactSeparated")  
	public int addContact(@WebParam(name = "firstName")			String firstName, 
			   			  @WebParam(name = "lastName")			String lastName, 
			   			  @WebParam(name = "email")				String email, 
			   			  @WebParam(name = "phones")			String[] phones, 
			   			  @WebParam(name = "defaultPhoneIndex")	int defaultPhoneIndex);

	@WebMethod(operationName = "addContact")   
	public int addContact(@WebParam(name = "contact") Contact contact);

	public Contact getContact(@WebParam(name = "index") int index);
	   
	public Contact[] getContacts();

	public void removeContact(@WebParam(name = "index") int index);
	   
}
