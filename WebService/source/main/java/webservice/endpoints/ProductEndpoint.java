package webservice.endpoints;

import domain.exceptions.DateIntersectionInProductPriceException;
import domain.product.Product;
import domain.product.ProductPrice;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.productRepository.ProductRepo;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.dtos.product.ProductDTO;
import webservice.dtos.product.ProductPriceDTO;
import webservice.dtos.product.UpdatedProductDTO;
import webservice.endpointrequestresponse.productrequestresponse.*;

import java.util.ArrayList;
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
        ProductDTO productDTO = request.getProduct();

        Product product = new Product(productDTO.getProductName(), productDTO.getProductUnits());

        List<ProductPrice> productPrices = new ArrayList<ProductPrice>();

        List <ProductPriceDTO> productPricesDTO = productDTO.getProductPrices();
        for (ProductPriceDTO productPriceDTO : productPricesDTO){
            productPrices.add( new ProductPrice(productPriceDTO.getPrice(),
                    productPriceDTO.getStartEffectDay()
                            .toGregorianCalendar()
                            .getTime(),
                    productPriceDTO.getEndEffectDay()
                            .toGregorianCalendar()
                            .getTime()) );
        }

        product.setProductPrices(productPrices);

        if (!product.checkProduct()){
            throw new DateIntersectionInProductPriceException();
        }

        productRepo.add(product);
    }

    @PayloadRoot(localPart = "GetProductRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        Product product = (Product) productRepo.getByBusinessKey(request.getProductName());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(product.getName());
        productDTO.setProductUnits(product.getUnits());

        List<ProductPrice> productPrices = product.getProductPrices();

        for (ProductPrice productPrice : productPrices){
            ProductPriceDTO productPriceDTO = new ProductPriceDTO();

            productPriceDTO.setPrice(productPrice.getPrice());
            productPriceDTO.setStartEffectDay( DateProducer.produce(productPrice.getStartEffectDay()) );
            productPriceDTO.setEndEffectDay( DateProducer.produce(productPrice.getEndEffectDay()) );

            productDTO.getProductPrices().add(productPriceDTO);
        }

        GetProductResponse response = new GetProductResponse();
        response.setProduct(productDTO);

        return response;
    }

    @PayloadRoot(localPart = "UpdateProductRequest", namespace = namespaceUri)
    public void updateProduct(@RequestPayload UpdateProductRequest request) {
        UpdatedProductDTO updatedProductDTO = request.getUpdatedProduct();

        Product product = (Product) productRepo.getByBusinessKey(updatedProductDTO.getProductName());

        product.setName(updatedProductDTO.getNewProductName());
        product.setUnits(updatedProductDTO.getNewProductUnits());

        List<ProductPrice> productPrices = new ArrayList<ProductPrice>();

        List<ProductPriceDTO> productPriceDTOs = updatedProductDTO.getNewProductPrices();
        for (ProductPriceDTO productPriceDTO : productPriceDTOs){
            productPrices.add( new ProductPrice(productPriceDTO.getPrice(),
                    productPriceDTO.getStartEffectDay()
                            .toGregorianCalendar()
                            .getTime(),
                    productPriceDTO.getEndEffectDay()
                            .toGregorianCalendar()
                            .getTime()) );
        }

        product.setProductPrices(productPrices);

        if (!product.checkProduct()){
            throw new DateIntersectionInProductPriceException();
        }

        productRepo.update(product);
    }

    @PayloadRoot(localPart = "DeleteProductRequest", namespace = namespaceUri)
    public void deleteProduct(@RequestPayload DeleteProductRequest request) {
        productRepo.deleteByBusinessKey(request.getProductName());
    }
}