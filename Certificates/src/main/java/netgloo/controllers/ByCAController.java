package netgloo.controllers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
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
@RequestMapping("/byCACertificates")
public class ByCAController {
	
	@Autowired
	private CertificateDataDao certificateDataDao;
	
	@Autowired
	private KeyStoreDataDao keyStoreDataDao;
	
	
	public ArrayList<String> subjectData = new ArrayList<>();
	
	@RequestMapping(value = "/getSubjectData", method = RequestMethod.GET)
	public ArrayList<String> getSubjectData(HttpServletRequest request) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException {
		subjectData.clear();

		List<CertificateData> lista = (List<CertificateData>) certificateDataDao.findAll();
		for(CertificateData cd : lista) {
			if(cd.getCertificateDataCA().equals("CA"))
				subjectData.add(cd.getCertificateDataAlias());
		}
		
		return subjectData;
	}
	

	@RequestMapping(value = "/generateBYCA", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String generateBYCA(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		
		try{
			String iss = sscPom.getIssuerSertificates().trim();
			if(iss ==null || iss.equals(""))
				return null;
			
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
			
			String password1 = sscPom.getPassword1().trim();
			if(password1 == null || password1.equals(""))
				return null;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}


		try {
		CertificateData certData = certificateDataDao.findByCertificateDataAlias(sscPom.getIssuerSertificates().trim());
		System.out.println(certData.getCertificateDataAlias() + "  " + certData.getCertificateDataKeyStoreName());
			
		char[] pass = sscPom.getPassword().trim().toCharArray();
		SubjectData subjectData = generateSubjectData(sscPom);
		IssuerData issuerData = readIssuerFromStore(certData.getCertificateDataKeyStoreName()+".jks",sscPom.getIssuerSertificates().trim(),  pass, pass);
		Certificate certificateIssuer = readIssuerCertificate(certData.getCertificateDataKeyStoreName()+".jks",sscPom.getIssuerSertificates().trim(),  pass, pass);
		X509Certificate cert = generateCertificate(subjectData, issuerData, certificateIssuer);
		
		KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
		//Datoteka se ucitava
		//NE TREBA U TAJ ISTI
		KeyStoreData keyStoreData = keyStoreDataDao.findByKeyStoreType("all");
		String fileNameAll = keyStoreData.getKeyStoreFileName();
		char[] password1 = sscPom.getPassword1().toCharArray();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileNameAll+".jks"));
		String alias = sscPom.getAlias().trim();
		String sn = sscPom.getSerialNumber().trim();
		
		PrivateKey privateKey = issuerData.getPrivateKey();
		System.out.println("ovo je PK " + privateKey.toString());
		keyStore.load(in, password1);
		keyStore.setCertificateEntry(sscPom.getAlias().trim(), cert);
		keyStore.setKeyEntry(alias, privateKey, password1, new Certificate[] {cert});
		keyStore.store(new FileOutputStream(fileNameAll+".jks"), password1);
		
		CertificateData cd = new CertificateData(sn, "NOTCA", alias, fileNameAll, false);
		certificateDataDao.save(cd);
		
		//RevocationStatus ocsp = OCSP.check(cert, (X509Certificate) certificateIssuer);
		//System.out.println(ocsp.getCertStatus().toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}
	
	
	public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) throws NoSuchProviderException {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			//Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			keyStore.load(in, password);
			//Iscitava se sertifikat koji ima dati alias
			Certificate cert = keyStore.getCertificate(alias);
			//Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
			PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);


			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			return new IssuerData(privKey, issuerName);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
			
			//Date startDate = iso8601Formater.parse("2017-01-31");
			//Date endDate = iso8601Formater.parse("2022-12-31");
			
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
	
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, Certificate certIssuer) throws IOException {
		try {
			Security.addProvider(new BouncyCastleProvider());
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");
			
			

			// Activate OCSP
			Security.setProperty("ocsp.enable", "true");
			
			ASN1Sequence seq = null;

	        seq = (ASN1Sequence) new ASN1InputStream(subjectData.getPublicKey().getEncoded()).readObject();
	        SubjectPublicKeyInfo parentPubKeyInfo = new SubjectPublicKeyInfo(seq);

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

			//Postavljaju se podaci za generisanje sertifiakta
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder((X509Certificate)certIssuer, 
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					new X500Principal(subjectData.getX500name().getEncoded()),
					subjectData.getPublicKey())
					.addExtension(
	                        new ASN1ObjectIdentifier("2.5.29.35"),
	                        false,
	                        new AuthorityKeyIdentifier(parentPubKeyInfo))
					.addExtension(
					        new ASN1ObjectIdentifier("2.5.29.19"), 
					        false,
					        new BasicConstraints(false)); // true if it is allowed to sign other certs
//					.addExtension(
//	                        new ASN1ObjectIdentifier("2.5.29.15"),
//	                        true,
//	                        new X509KeyUsage(
//	                                X509KeyUsage.digitalSignature |
//	                                        X509KeyUsage.nonRepudiation |
//	                                        X509KeyUsage.keyEncipherment |
//	                                        X509KeyUsage.dataEncipherment));
					
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
	
	public Certificate readIssuerCertificate(String keyStoreFile, String alias, char[] password, char[] keyPass) throws NoSuchProviderException {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			//Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			keyStore.load(in, password);
			//Iscitava se sertifikat koji ima dati alias
			Certificate cert = keyStore.getCertificate(alias);
			
			
			return cert;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
	

