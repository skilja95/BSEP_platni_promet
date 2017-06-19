package yu.ac.ns.ftn.informatika.ws.address.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contact", 
		namespace="http://informatika.ftn.ns.ac.yu/ws/address/data", 
		propOrder = {"firstName",
					 "lastName",
					 "email",
					 "phones",
					 "defaultPhoneIndex"})

public class Contact  implements java.io.Serializable {
	private static final long serialVersionUID = 4575678568872321L;
	
	@XmlElement(name="first_name", required = true)
    private String firstName;
	
	@XmlElement(name="last_name", required = true)
    private String lastName;
	
	@XmlElement(name="email", required = false)
    private String email;
	
	@XmlElementWrapper(name="phones", required=false)
	@XmlElement(name="phone", required = false)
    private String[] phones;
	
	@XmlElement(name="default_phone_index", required = false)
    private int defaultPhoneIndex;

    public Contact() {
    }
    
    public Contact(String firstName, String lastName, String email, String[] phones, int defaultPhoneIndex) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    	this.phones = phones;
    	this.defaultPhoneIndex = defaultPhoneIndex;
    }

    public int getDefaultPhoneIndex() {
        return defaultPhoneIndex;
    }

    public void setDefaultPhoneIndex(int defaultPhoneIndex) {
        this.defaultPhoneIndex = defaultPhoneIndex;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getFirstName() {
        return firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    public java.lang.String getLastName() {
        return lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    public java.lang.String[] getPhones() {
        return phones;
    }

    public void setPhones(java.lang.String[] phones) {
        this.phones = phones;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(firstName);
        buffer.append(" ");
        buffer.append(lastName);
        buffer.append(" ");
        buffer.append(email);
        buffer.append(" ");
        if(phones != null) {
            for(int i = 0; i < phones.length; i++) {
                buffer.append(phones[i]);
                buffer.append(" ");
            }
            buffer.append(defaultPhoneIndex);
        }
        
        return buffer.toString();
    }

    
}
