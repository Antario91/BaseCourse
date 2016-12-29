package webservice.endpoints;

import domain.order.exceptions.OrderDoesNotExistException;
import domain.product.ProductPrice;
import domain.product.ProductService;
import domain.product.exceptions.DateIntersectionInProductPriceException;
import domain.product.Product;
import domain.product.exceptions.NotValidStartEffectDayException;
import domain.product.exceptions.ProductAlreadyExistException;
import domain.product.exceptions.ProductDoesNotExistException;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.dtos.product.ProductDTO;
import webservice.dtos.product.ProductPriceDTO;
import webservice.endpointrequestresponse.productrequestresponse.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Endpoint
public class ProductEndpoint {
    final static Logger logger = Logger.getLogger(ProductEndpoint.class);
    private static final String namespaceUri = "http://www.productRequestResponse.endpointRequestResponse.webService";

    private ProductService productService;

    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(localPart = "CreateProductRequest", namespace = namespaceUri)
    public void createProduct(@RequestPayload CreateProductRequest request) throws ProductAlreadyExistException,
            DateIntersectionInProductPriceException {
        ProductPrice[] productPrices = new ProductPrice[ request.getProduct().getProductPrices().size() ];
        productService.createProduct(
                request.getProduct().getProductName(),
                request.getProduct().getProductUnits(),
                convertProductPrices(request.getProduct().getProductPrices()).toArray(productPrices)
        );
    }

    @PayloadRoot(localPart = "GetProductRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) throws ProductDoesNotExistException {
        Product product = productService.getProduct(request.getProductName());

        GetProductResponse response = new GetProductResponse();
        response.setProduct(convertProduct(product));

        return response;
    }

    //TODO add PAGINATION
    @PayloadRoot(localPart = "GetAllProductsRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllProductsResponse getAllProducts (@RequestPayload GetAllProductsRequest request) {
        List<Product> products = productService.getAllProducts();

        GetAllProductsResponse response = new GetAllProductsResponse();

        for ( Product product : products ){
            response.getProduct().add( convertProduct(product) );
        }

        return response;
    }

    @PayloadRoot(localPart = "AddProductPricesRequest", namespace = namespaceUri)
    public void addProductPricesToProduct(@RequestPayload AddProductPricesRequest request) throws NotValidStartEffectDayException,
            ProductDoesNotExistException, DateIntersectionInProductPriceException {
        ProductPrice[] productPrices = new ProductPrice[request.getProductPrices().size()];
        productService.addProductPrices(
                request.getProductName(),
                convertProductPrices(request.getProductPrices()).toArray(productPrices)
        );
    }

    @PayloadRoot(localPart = "DeleteProductPricesRequest", namespace = namespaceUri)
    public void deleteProductPricesFromProduct(@RequestPayload DeleteProductPricesRequest request)
            throws NotValidStartEffectDayException, ProductDoesNotExistException, DateIntersectionInProductPriceException {
        ProductPrice[] productPrices = new ProductPrice[request.getProductPrices().size()];
        productService.deleteProductPrices(
                request.getProductName(),
                convertProductPrices(request.getProductPrices()).toArray(productPrices)
        );
    }

    @PayloadRoot(localPart = "DeleteProductRequest", namespace = namespaceUri)
    public void deleteProduct(@RequestPayload DeleteProductRequest request) throws ProductDoesNotExistException, OrderDoesNotExistException {
        productService.deleteProduct(request.getProductName());
    }

    private List<ProductPrice> convertProductPrices(List<ProductPriceDTO> productPriceDTOs) {
        List<ProductPrice> productPrices = new ArrayList<ProductPrice>();
        for (ProductPriceDTO tempPrice : productPriceDTOs) {
            productPrices.add(
                    new ProductPrice( tempPrice.getPrice().doubleValue(),
                            tempPrice.getStartEffectDay().toGregorianCalendar().getTime() )
            );
        }
        return productPrices;
    }

    private ProductDTO convertProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(product.getName());
        productDTO.setProductUnits(product.getUnits());

        for (ProductPrice productPrice : product.getProductPrices()) {
            ProductPriceDTO productPriceDTO = new ProductPriceDTO();
            productPriceDTO.setPrice(new BigDecimal(productPrice.getPrice()).setScale(2, RoundingMode.HALF_UP));
            productPriceDTO.setStartEffectDay(DateProducer.produce(productPrice.getStartEffectDay()));

            productDTO.getProductPrices().add(productPriceDTO);
        }
        return productDTO;
    }
}
