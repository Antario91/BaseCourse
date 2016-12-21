package webservice.converters;


import domain.product.DateIntersectionInProductPriceException;
import domain.product.Product;
import domain.product.ProductPrice;
import domain.EntityDoesNotExistException;
import domain.product.ProductDoesNotExistException;
import domain.product.ProductRepo;
import utils.XMLGregorianCalendarProducer.DateProducer;
import webservice.dtos.product.ProductDTO;
import webservice.dtos.product.ProductPriceDTO;
import webservice.dtos.product.UpdatedProductDTO;
import webservice.validators.ProductValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgo on 14-Dec-16.
 */
public class ProductConverter {
    public static Product productDTOtoProduct(ProductDTO productDTO) throws DateIntersectionInProductPriceException {
        if (productDTO != null) {
            ProductValidator.checkProductDTO(productDTO);

            Product product = new Product(productDTO.getProductName(), productDTO.getProductUnits());

            List<ProductPrice> productPrices = new ArrayList<ProductPrice>();

            List<ProductPriceDTO> productPricesDTO = productDTO.getProductPrices();
            for (ProductPriceDTO productPriceDTO : productPricesDTO) {
                productPrices.add(
                        new ProductPrice(productPriceDTO.getPrice().doubleValue(),
                        productPriceDTO.getEndEffectDay()
                                .toGregorianCalendar()
                                .getTime())
                );
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

                productPriceDTO.setPrice( new BigDecimal(
                        productPrice.getPrice()
                        ).setScale(2, RoundingMode.HALF_UP)
                );
                productPriceDTO.setEndEffectDay(DateProducer.produce(productPrice.getEndEffectDay()));

                productDTO.getProductPrices().add(productPriceDTO);
            }

            return productDTO;
        }

        return null;
    }

    public static Product updatedProductDTOtoProduct (UpdatedProductDTO updatedProductDTO, ProductRepo productRepo) throws DateIntersectionInProductPriceException, EntityDoesNotExistException {
        if (updatedProductDTO != null && productRepo != null) {
            ProductValidator.checkUpdatedProductDTO(updatedProductDTO);

            Product product = (Product) productRepo.get(updatedProductDTO.getProductName());
            if (product == null) {
                throw new ProductDoesNotExistException();
            }

            List<ProductPrice> productPrices = new ArrayList<ProductPrice>();

            List<ProductPriceDTO> productPriceDTOs = updatedProductDTO.getNewProductPrices();
            for (ProductPriceDTO productPriceDTO : productPriceDTOs) {
                productPrices.add(new ProductPrice(productPriceDTO.getPrice().doubleValue(),
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
