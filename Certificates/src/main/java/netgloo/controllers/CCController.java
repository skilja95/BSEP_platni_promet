package netgloo.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
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
import org.bouncycastle.asn1.x500.RDN;
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
import org.bouncycastle.jce.X509KeyUsage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import netgloo.dao.CertificateDataDao;
import netgloo.models.CSRPom;
import netgloo.models.CertificateData;
import netgloo.models.IssuerData;
import netgloo.models.KeyStoreData;
import netgloo.models.SSCPom;
import netgloo.models.SubjectData;
import sun.security.pkcs10.PKCS10;

@RestController
@RequestMapping("/customersCertificates")
public class CCController {
	
	
	@Autowired
	private CertificateDataDao certificateDataDao;
	
	private PrivateKey pk;
	private final String COUNTRY = "2.5.4.6";
	private final String STATE = "2.5.4.8";
	private final String LOCALE = "2.5.4.7";
	private final String ORGANIZATION = "2.5.4.10";
	private final String ORGANIZATION_UNIT = "2.5.4.11";
	private final String COMMON_NAME = "2.5.4.3";
	private final String SERIAL_NUMBER = "2.5.4.5";
	
	private static SecureRandom random = new SecureRandom();
	
	
	public ArrayList<String> subjectData = new ArrayList<>();
	
	

	
	@RequestMapping(value = "/getCertAliases", method = RequestMethod.GET)
	public ArrayList<String> getCertAliases(HttpServletRequest request) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException {
		subjectData.clear();

		List<CertificateData> lista = (List<CertificateData>) certificateDataDao.findAll();
		for(CertificateData cd : lista) {
			if(cd.getCertificateDataCA().equals("NOTCA"))
				subjectData.add(cd.getCertificateDataAlias());
		}
		
		return subjectData;
	}
	
	
	@RequestMapping(value = "/genCustomerCertificate", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String genCustomerCertificate(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		try{
			
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
			
			String state = sscPom.getStateName().trim();
			if(state ==null || state.equals(""))
				return null;
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
		//CertificateData certData = certificateDataDao.findByCertificateDataAlias(sscPom.getIssuerSertificates().trim());
		//String fileName1 = certData.getCertificateDataKeyStoreName()+".jks";
		//char[] pass = sscPom.getPassword().trim().toCharArray();
		BigInteger serialNum = BigInteger.valueOf(new SecureRandom().nextLong());
		SubjectData subjectData = generateSubjectData(sscPom, serialNum);
		byte[] csr = generateCSR(serialNum.toString(), sscPom.getCommonName(), sscPom.getOrganisationUnit(),sscPom.getOrganisationName(),
				sscPom.getLocalityName(),sscPom.getCountry(),sscPom.getStateName() ,subjectData.getPublicKey(), pk);
		String s = new String(csr);
		System.out.println(s);
		
		File file = new File(serialNum.toString() + ".csr");
		byte[] buf = csr;

		FileOutputStream os = new FileOutputStream(file);
		os.write(buf);

		Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
		wr.write(new sun.misc.BASE64Encoder().encode(s.getBytes()));
		wr.flush();
		os.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "OK";
	}
	
	@RequestMapping(value = "/generateCertByCSR", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public String generateCertByCSR(@RequestBody SSCPom sscPom, HttpServletRequest request) {
		
		try{
			
			String subjectC = sscPom.getSubjectCertificate().trim();
			if(subjectC ==null || subjectC.equals(""))
				return null;
			
			String pass = sscPom.getPassword().trim();
			if(pass ==null || pass.equals(""))
				return null;
			
			String alias = sscPom.getAlias().trim();
			if(alias ==null || alias.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			CertificateData certData = certificateDataDao.findByCertificateDataAlias(sscPom.getSubjectCertificate().trim());
			String fileName1 = certData.getCertificateDataKeyStoreName()+".jks";
			char[] pass = sscPom.getPassword().trim().toCharArray();
			String sn = sscPom.getSerialNumber().trim();
			IssuerData issuerData = readIssuerFromStore(fileName1,sscPom.getSubjectCertificate().trim(),  pass, pass);
			SubjectData subjectData = generateSubjectData(sscPom,BigInteger.valueOf(Long.parseLong(sn)));
			Certificate certificateIssuer = readIssuerCertificate(fileName1,sscPom.getSubjectCertificate().trim(),  pass, pass);
			X509Certificate cert = generateCertificate(subjectData, issuerData, certificateIssuer);
			
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			//Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName1));
			String alias = sscPom.getAlias().trim();
			
			PrivateKey privateKey = issuerData.getPrivateKey();
	
			keyStore.load(in, pass);
			keyStore.setCertificateEntry(sscPom.getAlias().trim(), cert);
			keyStore.setKeyEntry(alias, privateKey, pass, new Certificate[] {cert});
			keyStore.store(new FileOutputStream(fileName1), pass);
			
			CertificateData cd = new CertificateData(sn, "none", alias, certData.getCertificateDataKeyStoreName(),false);
			certificateDataDao.save(cd);
			
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
	
	@RequestMapping(value = "/uploadFILE", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public CSRPom uploadFILE(@RequestBody SSCPom sscPom, HttpServletRequest request) {
			
		try{
			
			String fileName = sscPom.getFileName().trim();
			if(fileName ==null || fileName.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			
			String fileName = sscPom.getFileName();
			System.out.println(fileName);
			
			
			String ime = readCSRFile(fileName);
			PKCS10CertificationRequest csr = convertPemToPKCS10CertificationRequest(ime);

	        X500Name x500Name = csr.getSubject();
	       if( csr.isSignatureValid(new JcaContentVerifierProviderBuilder().setProvider("BC").build(csr.getSubjectPublicKeyInfo()))) {
	    	   System.out.println("SIGNATURE VALID");
	       }
	        System.out.println("x500Name is: " + x500Name + "\n");
	        CSRPom csrPom = new CSRPom(getX500Field(COUNTRY, x500Name),getX500Field(STATE, x500Name),getX500Field(LOCALE, x500Name),getX500Field(ORGANIZATION, x500Name),
	        		getX500Field(ORGANIZATION_UNIT, x500Name),getX500Field(COMMON_NAME, x500Name), (getX500Field(SERIAL_NUMBER, x500Name)));
	        	
			
			return csrPom;
		} catch (Exception e) {
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
	
	private SubjectData generateSubjectData(SSCPom sscPom, BigInteger sn) {
		try {
			KeyPair keyPairSubject = generateKeyPair();
			pk = keyPairSubject.getPrivate();
			//Datumi od kad do kad vazi sertifikat
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			
			Date startDate = new Date();
			Date endDate = new Date(startDate.getTime() + 365 * 24 * 60 * 60 * 1000L);
			
			//Serijski broj sertifikata
			
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, sscPom.getCommonName());
		    builder.addRDN(BCStyle.O, sscPom.getOrganisationName());
		    builder.addRDN(BCStyle.OU, sscPom.getOrganisationUnit());
		    builder.addRDN(BCStyle.C, sscPom.getCountry());
		    builder.addRDN(BCStyle.L, sscPom.getLocalityName());
		    builder.addRDN(BCStyle.SERIALNUMBER,sn.toString());
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new SubjectData(keyPairSubject.getPublic(), builder.build(), sn.toString(), startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, Certificate certificateIssuer) throws IOException {
		try {
			Security.addProvider(new BouncyCastleProvider());
			//Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
			//Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
			//Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");
			
			ASN1Sequence seq = null;
	        seq = (ASN1Sequence) new ASN1InputStream(subjectData.getPublicKey().getEncoded()).readObject();
	        SubjectPublicKeyInfo parentPubKeyInfo = new SubjectPublicKeyInfo(seq);

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
	                        new ASN1ObjectIdentifier("2.5.29.35"),
	                        false,
	                        new AuthorityKeyIdentifier(parentPubKeyInfo))
					.addExtension(
					        new ASN1ObjectIdentifier("2.5.29.19"), 
					        false,
					        new BasicConstraints(false)) // true if it is allowed to sign other certs
					 .addExtension(
		                        new ASN1ObjectIdentifier("2.5.29.15"),
		                        true,
		                        new X509KeyUsage(
		                                X509KeyUsage.digitalSignature |
		                                        X509KeyUsage.nonRepudiation |
		                                        X509KeyUsage.keyEncipherment |
		                                        X509KeyUsage.keyCertSign |
		                                        X509KeyUsage.cRLSign |
		                                        X509KeyUsage.dataEncipherment));
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
	
    public static byte[] generateCSR(String SN, String CN, String OU, String O,
            String L, String C, String S, PublicKey publicKey, PrivateKey privateKey) throws Exception {
    	
//    	PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
//    		    new X500Principal( "CN="+CN+", OU="+OU+", O="+O+", C="+C+",L="+L+",SERIALNUMBER="+SN), publicKey);
//    	JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
//    	ContentSigner signer = csBuilder.build(privateKey);
//    	PKCS10CertificationRequest csr = p10Builder.build(signer);
    	String sigAlg = "SHA256withRSA";
        PKCS10 pkcs10 = new PKCS10(publicKey);
        Signature signature = Signature.getInstance(sigAlg);
        signature.initSign(privateKey);
        X500Principal principal = new X500Principal( "CN="+CN+", OU="+OU+", O="+O+", C="+C+",L="+L+",SERIALNUMBER="+SN+",S="+S);

   //     pkcs10CertificationRequest kpGen = new PKCS10CertificationRequest(sigAlg, principal, publicKey, null, privateKey);  
     //   byte[] c = kpGen.getEncoded();  
        sun.security.x509.X500Name x500name=null;
        x500name= new  sun.security.x509.X500Name(principal.getEncoded());
      pkcs10.encodeAndSign(x500name, signature);
      /////////-----------------
      

      
      //------------
      
      
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bs);
        pkcs10.print(ps);
        byte[] c = bs.toByteArray();
        try {
            if (ps != null)
                ps.close();
            if (bs != null)
                bs.close();
        } catch (Throwable th) {
        }
        return c;
    	
      

    }
 
    public static String readCSRFile(String fileName) {
    	InputStream inputStream = null;
		BufferedReader br = null;

		try {
			inputStream = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
				if(line.contains("END")) 
					break;
			}

			System.out.println(sb.toString().trim());
			return sb.toString().trim();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
    }
    
    //--------------za ucitavanje PEM TEKSTA
    private String getX500Field(String asn1ObjectIdentifier, X500Name x500Name) {
        RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(asn1ObjectIdentifier));
        String retVal = null;
        for (RDN item : rdnArray) {
            retVal = item.getFirst().getValue().toString();
        }

        return retVal;
    }

    private PKCS10CertificationRequest convertPemToPKCS10CertificationRequest(String pem) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS10CertificationRequest csr = null;
        ByteArrayInputStream pemStream = null;
        try {
            pemStream = new ByteArrayInputStream(pem.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            
        }

        Reader pemReader = new BufferedReader(new InputStreamReader(pemStream));
        PEMParser pemParser = new PEMParser(pemReader);

        try {
            Object parsedObj = pemParser.readObject();

            System.out.println("PemParser returned: " + parsedObj);

            if (parsedObj instanceof PKCS10CertificationRequest) {
                csr = (PKCS10CertificationRequest) parsedObj;

            }
        } catch (IOException ex) {
           
        }

        return csr;
    }

    private String toPEM(Object key) {
        StringWriter sw = new StringWriter();
        PEMWriter pem = new PEMWriter(sw);
        try {
            pem.writeObject(key);
            pem.close();
        } catch (IOException e) {
            System.out.printf("IOException: %s%n", e);
        }
        return sw.toString();
    }
}


