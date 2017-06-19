
package yu.ac.ns.ftn.informatika.ws.bezbjednost;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-15T14:17:39.825+02:00
 * Generated source version: 2.6.5
 * 
 */
 
public class BezbjednostServer{

    protected BezbjednostServer() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new yu.ac.ns.ftn.informatika.ws.bezbjednost.BezbjednostImpl();
        String address = "http://localhost:9090/BezbjednostPort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new BezbjednostServer();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
 
 