package yu.ac.ns.ftn.informatika.ws.hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2017-06-13T00:01:38.337+02:00
 * Generated source version: 2.6.5
 * 
 */
@WebService(targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/hello", name = "HelloDocument")
@XmlSeeAlso({yu.ac.ns.ftn.informatika.ws.hello.types.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface HelloDocument {

    @WebMethod
    @WebResult(name = "ResponseMiss", targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/hello/types", partName = "ResponseMiss")
    public java.lang.String sayHelloMiss(
        @WebParam(partName = "RequestMiss", name = "RequestMiss", targetNamespace = "http://informatika.ftn.ns.ac.yu/ws/hello/types")
        yu.ac.ns.ftn.informatika.ws.hello.types.RequestMissType requestMiss
    );
}
