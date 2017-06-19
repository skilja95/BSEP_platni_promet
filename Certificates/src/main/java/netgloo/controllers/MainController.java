package netgloo.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import netgloo.dao.CertificateDataDao;
import netgloo.dao.UserDao;
import netgloo.models.CertificateData;
import netgloo.models.SSCPom;
import netgloo.models.User;

@RestController
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	private CertificateDataDao certificateDataDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/searchData", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String searchData(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		
		try{
			String serialNumber = sscPom.getSearchField().trim();
			if(serialNumber ==null || serialNumber.equals(""))
				return null;
			
			String pass = sscPom.getPassword().trim();
			if(pass ==null || pass.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			String serialNumber = sscPom.getSearchField().trim();
			CertificateData cd = certificateDataDao.findByCertificateDataSN(serialNumber);
			String fileName1 = cd.getCertificateDataKeyStoreName()+".jks";
			char[] password1 = sscPom.getPassword().toCharArray();
			
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName1));
			keyStore.load(in, password1); // ZAKUCANO NA PERA
			Enumeration aliases = keyStore.aliases();
			String alias;
			for (; aliases.hasMoreElements();) {
				alias = (String) aliases.nextElement();

				Certificate cert = keyStore.getCertificate(alias);
				BigInteger SN = ((X509Certificate) cert).getSerialNumber();

				if (SN.toString().equals(serialNumber)) {
					File file = new File(serialNumber + ".cer");
					byte[] buf = cert.getEncoded();

					FileOutputStream os = new FileOutputStream(file);
					os.write(buf);

					Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
					wr.write(new sun.misc.BASE64Encoder().encode(buf));
					wr.flush();
					os.close();
					System.out.println("objekat je sacuvan");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String loginUser(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		try{
			String email = sscPom.getEmail().trim();
			if(email ==null || email.equals(""))
				return null;
			
			String pass = sscPom.getPassword().trim();
			if(pass ==null || pass.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			User user = userDao.findByEmail(sscPom.getEmail());
			String pass = String.valueOf(user.getUserPassword());
			if (pass.equals(sscPom.getPassword())) {
				request.getSession().setAttribute("user", user);
				return user.getUserRole();
			} else {
				return "Error during login";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "EXCEPTION";
		}
	}
	
	@RequestMapping(value = "/logoutUser", method = RequestMethod.GET, headers = { "content-type=application/json" })
	public String loginUser(HttpServletRequest request) {

		try {
			request.getSession().invalidate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "logout";
	}
	
	@RequestMapping(value = "/checkRole", method = RequestMethod.GET, headers = { "content-type=application/json" })
	public String checkRole(HttpServletRequest request) {

		try {
			User u = (User) request.getSession().getAttribute("user");
			return u.getUserRole();
		} catch (Exception ex) {
			
			return "null";
		}
	}
	
	@RequestMapping(value = "/revokeCertificate", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String revokeCertificate(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		try{
			String serialNumber = sscPom.getSearchField().trim();
			if(serialNumber == null || serialNumber.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String serialNumber = sscPom.getSearchField().trim();
			CertificateData cd = certificateDataDao.findByCertificateDataSN(serialNumber);
			cd.setStatus(true);
			certificateDataDao.save(cd);
	

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}
	
	@RequestMapping(value = "/checkStatus", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String checkStatus(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		try{
			String serialNumber = sscPom.getSearchField().trim();
			if(serialNumber == null || serialNumber.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			String serialNumber = sscPom.getSearchField().trim();
			CertificateData cd = certificateDataDao.findByCertificateDataSN(serialNumber);
			if(cd.getStatus()) {
				//AKO JE TRUE, ONDA JE POVUCEN
				return "NOT";
			} else {
				return "OK";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "unknown";
		}

	
	}


}
