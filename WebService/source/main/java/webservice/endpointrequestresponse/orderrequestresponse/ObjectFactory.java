//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.08 at 07:51:17 PM EET 
//


package webservice.endpointrequestresponse.orderrequestresponse;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice.endpointrequestresponse.orderrequestresponse package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice.endpointrequestresponse.orderrequestresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateOrderRequest }
     * 
     */
    public CreateOrderRequest createCreateOrderRequest() {
        return new CreateOrderRequest();
    }

    /**
     * Create an instance of {@link GetOrderRequest }
     * 
     */
    public GetOrderRequest createGetOrderRequest() {
        return new GetOrderRequest();
    }

    /**
     * Create an instance of {@link GetOrderResponse }
     * 
     */
    public GetOrderResponse createGetOrderResponse() {
        return new GetOrderResponse();
    }

    /**
     * Create an instance of {@link UpdateOrderRequest }
     * 
     */
    public UpdateOrderRequest createUpdateOrderRequest() {
        return new UpdateOrderRequest();
    }

    /**
     * Create an instance of {@link DeleteOrderRequest }
     * 
     */
    public DeleteOrderRequest createDeleteOrderRequest() {
        return new DeleteOrderRequest();
    }

}
