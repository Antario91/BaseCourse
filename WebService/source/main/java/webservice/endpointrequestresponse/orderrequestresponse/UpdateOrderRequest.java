//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.12 at 11:40:03 AM EET 
//


package webservice.endpointrequestresponse.orderrequestresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import webservice.dtos.order.UpdatedOrderDTO;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="updatedOrder" type="{http://www.order.dtos.webService/}UpdatedOrderDTO"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "updatedOrder"
})
@XmlRootElement(name = "UpdateOrderRequest")
public class UpdateOrderRequest {

    @XmlElement(required = true)
    protected UpdatedOrderDTO updatedOrder;

    /**
     * Gets the value of the updatedOrder property.
     * 
     * @return
     *     possible object is
     *     {@link UpdatedOrderDTO }
     *     
     */
    public UpdatedOrderDTO getUpdatedOrder() {
        return updatedOrder;
    }

    /**
     * Sets the value of the updatedOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdatedOrderDTO }
     *     
     */
    public void setUpdatedOrder(UpdatedOrderDTO value) {
        this.updatedOrder = value;
    }

}
