package yu.ac.ns.ftn.informatika.ws.hello;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/hello")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public interface Hello {
    
	public String sayHi(@WebParam(name = "text") String text);

}
