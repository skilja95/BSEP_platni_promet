package netgloo.controllers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import netgloo.dao.CertificateDataDao;
import netgloo.dao.KeyStoreDataDao;
import netgloo.models.CertificateData;
import netgloo.models.IssuerData;
import netgloo.models.KeyStoreData;
import netgloo.models.SSCPom;
import netgloo.models.SubjectData;


@RestController
@RequestMapping("/ssccontroller")
public class SSCController {
	
	@Autowired
	private KeyStoreDataDao keyStoreDataDao;
	
	@Autowired
	private CertificateDataDao certificateDataDao;
	
	public ArrayList<String> keystores = new ArrayList<>();
	
	@RequestMapping(value = "/getKeystores", method = RequestMethod.GET)
	public ArrayList<String> getKeystores(HttpServletRequest request) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException {
		keystores.clear();

		//PROCI KROZ BAZU I IZVUCI SVA IMENA KEYSTOROVA
		List<KeyStoreData> lista = (List<KeyStoreData>) keyStoreDataDao.findAll();
		for(KeyStoreData ksd:lista) {
			if(ksd.getKeyStoreType().equals("root"))
				keystores.add(ksd.getKeyStoreFileName());
		}
		
		return keystores;
	}

	@RequestMapping(value = "/generateSSC", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String generateSSC(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		try{
			String serialNumber = sscPom.getSerialNumber().trim();
			if(serialNumber ==null || serialNumber.equals(""))
				return null;
			
			String commonName = sscPom.getCommonName().trim();
			if(commonName ==null || commonName.equals(""))
				return null;
			
			String ON = sscPom.getOrganisationName().trim();
			if(ON ==null || ON.equals(""))
				return null;
			
			String OU = sscPom.getOrganisationUnit().trim();
			if(OU ==null || OU.equals(""))
				return null;
			
			String country = sscPom.getCountry().trim();
			if(country ==null || country.equals(""))
				return null;
			
			String locality = sscPom.getLocalityName().trim();
			if(locality ==null || locality.equals(""))
				return null;
			
			String alias = sscPom.getAlias().trim();
			if(alias ==null || alias.equals(""))
				return null;
			
			String password = sscPom.getPassword().trim();
			if(password ==null || password.equals(""))
				return null;
			
			String fileNAme = sscPom.getFileName().trim();
			if(fileNAme ==null || fileNAme.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			
			SubjectData subjectData = generateSubjectData(sscPom);
			
			KeyPair keyPairIssuer = generateKeyPair();
			IssuerData issuerData = generateIssuerData(keyPairIssuer.getPrivate(), sscPom);
			System.out.println("ovo je PRIVATNI KLJUC iz generateSSC" + issuerData.getPrivateKey());
			X509Certificate cert = generateCertificate(subjectData, issuerData);
			
			
			//GENERISANJE i cuvanje KeyStore-a
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			KeyPair keyPair = generateKeyPair();
			
			char[] password = sscPom.getPassword().trim().toCharArray();
			String alias = sscPom.getAlias();
			String fileName = sscPom.getFileName().trim();
			String sn = sscPom.getSerialNumber();
			
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName+".jks"));
			//UMJESTO NULL, UCITATI KEYSTORE
			//keyStore.load(null, password);
			keyStore.load(in, password);
			keyStore.setCertificateEntry(alias, cert);
			keyStore.setKeyEntry(alias, keyPair.getPrivate(), password, new Certificate[] {cert});
			keyStore.store(new FileOutputStream(fileName+".jks"), password);
			
			CertificateData cd = new CertificateData(sn, "CA", alias, fileName, false);
			certificateDataDao.save(cd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}
	
	private KeyPair generateKeyPair() {
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	private SubjectData generateSubjectData(SSCPom sscPom) {
		try {
			KeyPair keyPairSubject = generateKeyPair();
			
			//Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			
			Date startDate = new Date();
			Date endDate = new Date(startDate.getTime() + 365 * 24 * 60 * 60 * 1000L);
			
			//Serijski broj sertifikata
			String sn=sscPom.getSerialNumber();
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, sscPom.getCommonName());
		    builder.addRDN(BCStyle.O, sscPom.getOrganisationName());
		    builder.addRDN(BCStyle.OU, sscPom.getOrganisationUnit());
		    builder.addRDN(BCStyle.C, sscPom.getCountry());
		    builder.addRDN(BCStyle.L, sscPom.getLocalityName());
		    builder.addRDN(BCStyle.SERIALNUMBER, sscPom.getSerialNumber());
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn, startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private IssuerData generateIssuerData(PrivateKey issuerKey, SSCPom sscPom) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		 builder.addRDN(BCStyle.CN, sscPom.getCommonName());
		    builder.addRDN(BCStyle.O, sscPom.getOrganisationName());
		    builder.addRDN(BCStyle.OU, sscPom.getOrganisationUnit());
		    builder.addRDN(BCStyle.C, sscPom.getCountry());
		    builder.addRDN(BCStyle.L, sscPom.getLocalityName());
		    
		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}
	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData) throws CertIOException {
		try {
			Security.addProvider(new BouncyCastleProvider());
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey())
					.addExtension(
					        new ASN1ObjectIdentifier("2.5.29.19"), 
					        false,
					        new BasicConstraints(true)) // true if it is allowed to sign other certs
					.addExtension(
							new ASN1ObjectIdentifier("2.5.29.15"),
					        true,
							new X509KeyUsage(
					           X509KeyUsage.keyCertSign|
					           X509KeyUsage.cRLSign));
					//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}

}
