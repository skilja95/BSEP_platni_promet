
package yu.ac.ns.ftn.informatika.ws.address;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-19T01:10:50.093+02:00
 * Generated source version: 2.6.5
 * 
 */
 
public class AddressBookServer{

    protected AddressBookServer() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new yu.ac.ns.ftn.informatika.ws.address.AddressBookImpl();
        String address = "http://localhost:9090/AddressBookPort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new AddressBookServer();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
 
 