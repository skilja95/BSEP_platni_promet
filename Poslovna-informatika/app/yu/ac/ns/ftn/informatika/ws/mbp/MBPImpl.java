
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package yu.ac.ns.ftn.informatika.ws.mbp;
import javax.jws.WebService;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-14T01:04:24.555+02:00
 * Generated source version: 2.6.5
 * 
 */
@WebService(endpointInterface = "yu.ac.ns.ftn.informatika.ws.mbp.MBP", serviceName = "MBPService")                      
public class MBPImpl implements MBP {

    public java.lang.String doMbp(model.mbp.MedjubankarskiPrenosi arg0) {  
        System.out.println(arg0);    
        try {
            java.lang.String _return = "";
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
 
    }

}