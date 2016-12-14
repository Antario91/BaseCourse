//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.12 at 11:40:03 AM EET 
//


package webservice.dtos.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import webservice.dtos.product.ProductDTOInOrder;


/**
 * <p>Java class for OrderItemDTOForReception complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderItemDTOForReception"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="product" type="{http://www.product.dtos.webService/}ProductDTOInOrder"/&gt;
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderItemDTOForReception", propOrder = {
    "product",
    "quantity"
})
public class OrderItemDTOForReception {

    @XmlElement(required = true)
    protected ProductDTOInOrder product;
    protected long quantity;

    /**
     * Gets the value of the product property.
     * 
     * @return
     *     possible object is
     *     {@link ProductDTOInOrder }
     *     
     */
    public ProductDTOInOrder getProduct() {
        return product;
    }

    /**
     * Sets the value of the product property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductDTOInOrder }
     *     
     */
    public void setProduct(ProductDTOInOrder value) {
        this.product = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public long getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(long value) {
        this.quantity = value;
    }

}
