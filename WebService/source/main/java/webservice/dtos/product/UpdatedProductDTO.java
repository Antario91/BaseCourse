//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.15 at 07:30:30 PM EET 
//


package webservice.dtos.product;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdatedProductDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdatedProductDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="newProductUnits" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="newProductPrices" type="{http://www.product.dtos.webService/}ProductPriceDTO" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdatedProductDTO", propOrder = {
    "productName",
    "newProductUnits",
    "newProductPrices"
})
public class UpdatedProductDTO {

    @XmlElement(required = true)
    protected String productName;
    @XmlElement(required = true)
    protected String newProductUnits;
    @XmlElement(required = true)
    protected List<ProductPriceDTO> newProductPrices;

    /**
     * Gets the value of the productName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the value of the productName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * Gets the value of the newProductUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewProductUnits() {
        return newProductUnits;
    }

    /**
     * Sets the value of the newProductUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewProductUnits(String value) {
        this.newProductUnits = value;
    }

    /**
     * Gets the value of the newProductPrices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the newProductPrices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNewProductPrices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductPriceDTO }
     * 
     * 
     */
    public List<ProductPriceDTO> getNewProductPrices() {
        if (newProductPrices == null) {
            newProductPrices = new ArrayList<ProductPriceDTO>();
        }
        return this.newProductPrices;
    }

}
