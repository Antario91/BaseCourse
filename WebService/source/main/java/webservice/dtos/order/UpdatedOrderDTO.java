//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.20 at 12:14:54 PM EET 
//


package webservice.dtos.order;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdatedOrderDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdatedOrderDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="billingNumberOfUpdatedOrder" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="newOrderItems" type="{http://www.order.dtos.webService/}OrderItemDTOForCreation" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdatedOrderDTO", propOrder = {
    "billingNumberOfUpdatedOrder",
    "newOrderItems"
})
public class UpdatedOrderDTO {

    protected long billingNumberOfUpdatedOrder;
    @XmlElement(required = true)
    protected List<OrderItemDTOForCreation> newOrderItems;

    /**
     * Gets the value of the billingNumberOfUpdatedOrder property.
     * 
     */
    public long getBillingNumberOfUpdatedOrder() {
        return billingNumberOfUpdatedOrder;
    }

    /**
     * Sets the value of the billingNumberOfUpdatedOrder property.
     * 
     */
    public void setBillingNumberOfUpdatedOrder(long value) {
        this.billingNumberOfUpdatedOrder = value;
    }

    /**
     * Gets the value of the newOrderItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the newOrderItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNewOrderItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderItemDTOForCreation }
     * 
     * 
     */
    public List<OrderItemDTOForCreation> getNewOrderItems() {
        if (newOrderItems == null) {
            newOrderItems = new ArrayList<OrderItemDTOForCreation>();
        }
        return this.newOrderItems;
    }

}
