
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package yu.ac.ns.ftn.informatika.ws.bezbjednost;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-15T14:38:27.406+02:00
 * Generated source version: 2.6.5
 * 
 */
public class BezbjednostClient {

    public static void main(String[] args) throws Exception {
        QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/bezbjednost", "BezbjednostService");
        QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/bezbjednost", "BezbjednostPort");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
                        "http://localhost:9090/BezbjednostPort"); 
        yu.ac.ns.ftn.informatika.ws.bezbjednost.Bezbjednost client = service.getPort(portName,  yu.ac.ns.ftn.informatika.ws.bezbjednost.Bezbjednost.class);
        
        // Insert code to invoke methods on the client here
    }

}