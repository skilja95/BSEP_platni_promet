package yu.ac.ns.ftn.informatika.ws.mbp;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import model.mbp.MedjubankarskiPrenosi;
import model.xmlws.Nalozi;

@WebService(name = "mbp",targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/mbp")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public interface MBP {
	public String doMbp(@WebParam(name = "medjBan") MedjubankarskiPrenosi medjubankarskiPrenosi);

}
