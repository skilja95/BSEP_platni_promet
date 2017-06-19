package yu.ac.ns.ftn.informatika.ws.bezbjednost;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.w3c.dom.Document;

@WebService(targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/bezbjednost")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public interface Bezbjednost {
	public String sayHi(@WebParam(name = "text") String document);
}
