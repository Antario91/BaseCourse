package webservice.validators;

import webservice.dtos.order.OrderDTOForCreation;
import webservice.dtos.order.OrderItemDTOForCreation;
import webservice.dtos.order.UpdatedOrderDTO;
import webservice.exceptions.*;

/**
 * Created by olgo on 14-Dec-16.
 */
public class OrderValidator {
    public static boolean checkOrderDTOForCreation (OrderDTOForCreation orderDTOForCreation) {
        if (orderDTOForCreation.getOrdersCustomersName() == null ||
                orderDTOForCreation.getOrdersCustomersName().isEmpty()) {
            throw new NullCustomerNameException();
        }

        if (orderDTOForCreation.getOrderItems() == null ||
                orderDTOForCreation.getOrderItems().isEmpty()) {
            throw new NullOrderItemsException();
        }

        for ( OrderItemDTOForCreation orderItemDTOForCreation : orderDTOForCreation.getOrderItems() ) {
            if (orderItemDTOForCreation.getProductName() == null ||
                    orderItemDTOForCreation.getProductName().isEmpty()) {
                throw new NullProductNameException();
            }

            if (new Double(
                    orderItemDTOForCreation.getQuantity().doubleValue()
            ).compareTo(0.0) == 0 ) {
                throw new NullProductQuantityException();
            }
        }

        return true;
    }

    public static boolean checkUpdatedOrderDTO (UpdatedOrderDTO updatedOrderDTO) {
        if (updatedOrderDTO.getBillingNumberOfUpdatedOrder() == 0){
            throw new NullOrderBillingNumberException();
        }

        if (updatedOrderDTO.getNewOrderItems() == null ||
                updatedOrderDTO.getNewOrderItems().isEmpty()) {
            throw new NullOrderItemsException();
        }

        for ( OrderItemDTOForCreation orderItemDTOForCreation : updatedOrderDTO.getNewOrderItems() ) {
            if (orderItemDTOForCreation.getProductName() == null ||
                    orderItemDTOForCreation.getProductName().isEmpty()) {
                throw new NullProductNameException();
            }

            if (new Double(
                    orderItemDTOForCreation.getQuantity().doubleValue()
            ).compareTo(0.0) == 0) {
                throw new NullProductQuantityException();
            }
        }

        return true;
    }
}
