package netgloo.controllers;

import java.io.FileOutputStream;
import java.security.KeyStore;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import netgloo.dao.CertificateDataDao;
import netgloo.dao.KeyStoreDataDao;
import netgloo.models.KeyStoreData;
import netgloo.models.SSCPom;

@RestController
@RequestMapping("/keystoreController")
public class KeystoreController {
	@Autowired
	private KeyStoreDataDao keyStoreDataDao;
	
	@Autowired
	private CertificateDataDao certificateDataDao;
	
	@RequestMapping(value = "/generateKeystore", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String generateKeystore(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		try{
			String fileName = sscPom.getFileName().trim();
			if(fileName == null || fileName.equals(""))
				return null;
			
			String pass = sscPom.getPassword();
			if(pass == null || pass.equals(""))
				return null;
			
			String fileName1 = sscPom.getFileName1().trim();
			if(fileName1 == null || fileName1.equals(""))
				return null;
			
			
			String pass1 = sscPom.getPassword1();
			if(pass1 == null || pass1.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			keyStoreDataDao.deleteAll();
			
			//GENERISANJE i cuvanje KeyStore-a
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			String fileName = sscPom.getFileName();
			char[] password = sscPom.getPassword().toCharArray();
			keyStore.load(null, password);
			keyStore.store(new FileOutputStream(fileName+".jks"), password);
			KeyStoreData ksd = new KeyStoreData(fileName, "root");
			keyStoreDataDao.save(ksd);
			
			KeyStore keyStore1 = KeyStore.getInstance("JKS", "SUN");
			String fileName1 = sscPom.getFileName1();
			char[] password1 = sscPom.getPassword1().toCharArray();
			keyStore1.load(null, password1);
			keyStore1.store(new FileOutputStream(fileName1+".jks"), password1);
			KeyStoreData ksd1 = new KeyStoreData(fileName1, "all");
			keyStoreDataDao.save(ksd1);
			
			certificateDataDao.deleteAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}

}
