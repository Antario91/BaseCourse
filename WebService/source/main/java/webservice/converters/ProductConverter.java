package webservice.converters;


import domain.product.Product;
import domain.product.ProductPrice;
import persistence.productRepository.ProductRepo;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.dtos.product.ProductDTO;
import webservice.dtos.product.ProductPriceDTO;
import webservice.dtos.product.UpdatedProductDTO;
import webservice.validators.ProductValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgo on 14-Dec-16.
 */
public class ProductConverter {
    public static Product productDTOtoProduct(ProductDTO productDTO) {
        if (productDTO != null) {
            ProductValidator.checkProductDTO(productDTO);

            Product product = new Product(productDTO.getProductName(), productDTO.getProductUnits());

            List<ProductPrice> productPrices = new ArrayList<ProductPrice>();

            List<ProductPriceDTO> productPricesDTO = productDTO.getProductPrices();
            for (ProductPriceDTO productPriceDTO : productPricesDTO) {
                productPrices.add(new ProductPrice(productPriceDTO.getPrice(),
                        productPriceDTO.getStartEffectDay()
                                .toGregorianCalendar()
                                .getTime(),
                        productPriceDTO.getEndEffectDay()
                                .toGregorianCalendar()
                                .getTime()));
            }

            product.setProductPrices(productPrices);

            return product;
        }

        return null;
    }

    public static ProductDTO productToProductDTO (Product product) {
        if (product != null) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductName(product.getName());
            productDTO.setProductUnits(product.getUnits());

            List<ProductPrice> productPrices = product.getProductPrices();

            for (ProductPrice productPrice : productPrices) {
                ProductPriceDTO productPriceDTO = new ProductPriceDTO();

                productPriceDTO.setPrice(productPrice.getPrice());
                productPriceDTO.setStartEffectDay(DateProducer.produce(productPrice.getStartEffectDay()));
                productPriceDTO.setEndEffectDay(DateProducer.produce(productPrice.getEndEffectDay()));

                productDTO.getProductPrices().add(productPriceDTO);
            }

            return productDTO;
        }

        return null;
    }

    public static Product updatedProductDTOtoProduct (UpdatedProductDTO updatedProductDTO, ProductRepo productRepo){
        if (updatedProductDTO != null && productRepo != null) {
            ProductValidator.checkUpdatedProductDTO(updatedProductDTO);

            Product product = (Product) productRepo.getByBusinessKey(updatedProductDTO.getProductName());

            product.setUnits(updatedProductDTO.getNewProductUnits());

            List<ProductPrice> productPrices = new ArrayList<ProductPrice>();

            List<ProductPriceDTO> productPriceDTOs = updatedProductDTO.getNewProductPrices();
            for (ProductPriceDTO productPriceDTO : productPriceDTOs) {
                productPrices.add(new ProductPrice(productPriceDTO.getPrice(),
                        productPriceDTO.getStartEffectDay()
                                .toGregorianCalendar()
                                .getTime(),
                        productPriceDTO.getEndEffectDay()
                                .toGregorianCalendar()
                                .getTime()));
            }

            product.setProductPrices(productPrices);

            return product;
        }

        return null;
    }
}
