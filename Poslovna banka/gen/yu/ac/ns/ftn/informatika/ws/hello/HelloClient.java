
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package yu.ac.ns.ftn.informatika.ws.hello;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-19T01:10:45.536+02:00
 * Generated source version: 2.6.5
 * 
 */
public class HelloClient {

    public static void main(String[] args) throws Exception {
        QName serviceName = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "HelloService");
        QName portName = new QName("http://informatika.ftn.ns.ac.yu/ws/hello", "HelloPort");

        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,
                        "http://localhost:9090/HelloPort"); 
        yu.ac.ns.ftn.informatika.ws.hello.Hello client = service.getPort(portName,  yu.ac.ns.ftn.informatika.ws.hello.Hello.class);
        
        // Insert code to invoke methods on the client here
    }

}