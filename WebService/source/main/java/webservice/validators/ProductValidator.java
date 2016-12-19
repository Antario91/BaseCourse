package webservice.validators;

import webservice.dtos.product.ProductDTO;
import webservice.dtos.product.ProductPriceDTO;
import webservice.dtos.product.UpdatedProductDTO;
import webservice.exceptions.*;

/**
 * Created by olgo on 14-Dec-16.
 */
public class ProductValidator {
    public static boolean checkProductDTO (ProductDTO productDTO){
        if (productDTO.getProductName() == null ||
                productDTO.getProductName().isEmpty()) {
            throw new NullProductNameException();
        }

        if (productDTO.getProductUnits() == null ||
                productDTO.getProductUnits().isEmpty()) {
            throw new NullProductUnitsException();
        }

        if (productDTO.getProductPrices() == null) {
            throw new NullProductPricesException();
        }

        for (ProductPriceDTO productPriceDTO : productDTO.getProductPrices()) {
            if (productPriceDTO.getPrice() == 0) {
                throw new NullProductPriceException();
            }

            if (productPriceDTO.getEndEffectDay() == null) {
                throw new NullPriceEndEffectDayException();
            }
        }

        return true;
    }

    public static boolean checkUpdatedProductDTO (UpdatedProductDTO updatedProductDTO) {
        if (updatedProductDTO.getProductName() == null ||
                updatedProductDTO.getProductName().isEmpty()) {
            throw new NullProductNameException();
        }

        if (updatedProductDTO.getNewProductPrices() == null) {
            throw new NullProductPricesException();
        }

        for (ProductPriceDTO productPriceDTO : updatedProductDTO.getNewProductPrices()) {
            if (productPriceDTO.getPrice() == 0) {
                throw new NullProductPriceException();
            }

            if (productPriceDTO.getEndEffectDay() == null) {
                throw new NullPriceEndEffectDayException();
            }
        }

        return true;
    }
}
