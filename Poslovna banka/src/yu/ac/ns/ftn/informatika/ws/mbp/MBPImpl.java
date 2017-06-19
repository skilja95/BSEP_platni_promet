package yu.ac.ns.ftn.informatika.ws.mbp;

import javax.ejb.Stateless;
import javax.jws.WebService;

import model.mbp.MedjubankarskiPrenosi;

@Stateless
@WebService(portName = "MBPPort",
			serviceName = "MBPService",
			targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/mbp",
			endpointInterface = "yu.ac.ns.ftn.informatika.ws.mbp.MBP"
		)

public class MBPImpl implements MBP{

	public MBPImpl() {
		System.out.println("Implementacija medjubankarskog interfejsa");
	}
	
	public String doMbp(MedjubankarskiPrenosi medjubankarskiPrenosi) {
		System.out.println("NAZIV BANKE" +medjubankarskiPrenosi.getBankaPosiljalac().getNaziv());
		
		return "OK";
	}

}
