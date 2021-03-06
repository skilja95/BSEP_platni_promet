
package yu.ac.ns.ftn.informatika.ws.hello;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-13T00:01:38.453+02:00
 * Generated source version: 2.6.5
 * 
 */
 
public class HelloDocument_HelloDocumentPort_Server{

    protected HelloDocument_HelloDocumentPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new HelloDocumentImpl();
        String address = "http://localhost:8080/Hello";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new HelloDocument_HelloDocumentPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
