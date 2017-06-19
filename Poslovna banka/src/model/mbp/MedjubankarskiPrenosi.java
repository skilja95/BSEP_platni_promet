//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.03 at 02:46:49 PM CEST 
//


package model.mbp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}detalji_prenosa"/>
 *         &lt;element ref="{}banka_posiljalac"/>
 *         &lt;element ref="{}banka_primalac"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "medjBan", propOrder = {
    "detaljiPrenosa",
    "bankaPosiljalac",
    "bankaPrimalac"
})
@XmlRootElement(name = "medjubankarski_prenosi")
public class MedjubankarskiPrenosi implements Serializable{

    @XmlElement(name = "detalji_prenosa", required = true)
    protected DetaljiPrenosa detaljiPrenosa;
    @XmlElement(name = "banka_posiljalac", required = true)
    protected BankaPosiljalac bankaPosiljalac;
    @XmlElement(name = "banka_primalac", required = true)
    protected BankaPrimalac bankaPrimalac;

    /**
     * Gets the value of the detaljiPrenosa property.
     * 
     * @return
     *     possible object is
     *     {@link DetaljiPrenosa }
     *     
     */
    public DetaljiPrenosa getDetaljiPrenosa() {
        return detaljiPrenosa;
    }

    /**
     * Sets the value of the detaljiPrenosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetaljiPrenosa }
     *     
     */
    public void setDetaljiPrenosa(DetaljiPrenosa value) {
        this.detaljiPrenosa = value;
    }

    /**
     * Gets the value of the bankaPosiljalac property.
     * 
     * @return
     *     possible object is
     *     {@link BankaPosiljalac }
     *     
     */
    public BankaPosiljalac getBankaPosiljalac() {
        return bankaPosiljalac;
    }

    /**
     * Sets the value of the bankaPosiljalac property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankaPosiljalac }
     *     
     */
    public void setBankaPosiljalac(BankaPosiljalac value) {
        this.bankaPosiljalac = value;
    }

    /**
     * Gets the value of the bankaPrimalac property.
     * 
     * @return
     *     possible object is
     *     {@link BankaPrimalac }
     *     
     */
    public BankaPrimalac getBankaPrimalac() {
        return bankaPrimalac;
    }

    /**
     * Sets the value of the bankaPrimalac property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankaPrimalac }
     *     
     */
    public void setBankaPrimalac(BankaPrimalac value) {
        this.bankaPrimalac = value;
    }

}
