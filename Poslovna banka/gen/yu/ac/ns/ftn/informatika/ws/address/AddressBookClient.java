
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package yu.ac.ns.ftn.informatika.ws.address;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-19T01:10:50.140+02:00
 * Generated source version: 2.6.5
 * 
 */
public class AddressBookClient {

    public static void main(String[] args) throws Exception {
        QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/address", "AddressBookService");
        QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/address", "AddressBookPort");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
                        "http://localhost:9090/AddressBookPort"); 
        yu.ac.ns.ftn.informatika.ws.address.AddressBook client = service.getPort(portName,  yu.ac.ns.ftn.informatika.ws.address.AddressBook.class);
        
        // Insert code to invoke methods on the client here
    }

}