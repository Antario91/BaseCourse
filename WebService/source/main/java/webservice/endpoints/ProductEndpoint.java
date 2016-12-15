package webservice.endpoints;

import domain.product.Product;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.productRepository.ProductRepo;
import webservice.converters.ProductConverter;
import webservice.endpointrequestresponse.productrequestresponse.*;

import java.util.List;


@Endpoint
public class ProductEndpoint {
    final static Logger logger = Logger.getLogger(ProductEndpoint.class);
    private static final String namespaceUri = "http://www.productRequestResponse.endpointRequestResponse.webService";

    private ProductRepo productRepo;

    public ProductEndpoint(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @PayloadRoot(localPart = "CreateProductRequest", namespace = namespaceUri)
    @ResponsePayload
    public void createProduct(@RequestPayload CreateProductRequest request) {
        productRepo.add( ProductConverter.productDTOtoProduct(request.getProduct()) );
    }

    @PayloadRoot(localPart = "GetProductRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.setProduct(
                ProductConverter.productToProductDTO( (Product) productRepo.getByBusinessKey(request.getProductName()) )
        );

        return response;
    }

    //TODO add PAGINATION
    @PayloadRoot(localPart = "GetAllProductsRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetAllProductsResponse getAllProducts (@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();

        for ( Product product : (List<Product>) productRepo.getAllProducts() ){
            response.getProduct().add( ProductConverter.productToProductDTO(product) );
        }

        return response;
    }

    @PayloadRoot(localPart = "UpdateProductRequest", namespace = namespaceUri)
    public void updateProduct(@RequestPayload UpdateProductRequest request) {
        productRepo.update(
                ProductConverter.updatedProductDTOtoProduct( request.getUpdatedProduct(), productRepo )
        );
    }

    @PayloadRoot(localPart = "DeleteProductRequest", namespace = namespaceUri)
    public void deleteProduct(@RequestPayload DeleteProductRequest request) {
        productRepo.deleteByBusinessKey(request.getProductName());
    }
}
