//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.09 at 06:49:31 PM EET 
//


package webservice.dtos.order;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderDTOForCreation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderDTOForCreation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ordersCustomersName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orderItems" type="{http://www.order.dtos.webService/}OrderItemDTOForCreation" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderDTOForCreation", propOrder = {
    "ordersCustomersName",
    "orderItems"
})
public class OrderDTOForCreation {

    @XmlElement(required = true)
    protected String ordersCustomersName;
    @XmlElement(required = true)
    protected List<OrderItemDTOForCreation> orderItems;

    /**
     * Gets the value of the ordersCustomersName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdersCustomersName() {
        return ordersCustomersName;
    }

    /**
     * Sets the value of the ordersCustomersName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdersCustomersName(String value) {
        this.ordersCustomersName = value;
    }

    /**
     * Gets the value of the orderItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderItemDTOForCreation }
     * 
     * 
     */
    public List<OrderItemDTOForCreation> getOrderItems() {
        if (orderItems == null) {
            orderItems = new ArrayList<OrderItemDTOForCreation>();
        }
        return this.orderItems;
    }

}
