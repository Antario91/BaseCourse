package domain.order;

import domain.ContractViolationException;

public class OrderItem {
    private final double quantity;
    private final String productId;

    private OrderItem () {
        quantity = 0;
        productId = null;
    }

    public OrderItem(double quantity, String productId) throws ContractViolationException {
        validateConstructorsParams(quantity, productId);
        this.quantity = quantity;
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    private void validateConstructorsParams(double quantity, String productId) throws ContractViolationException {
        if (quantity <= 0) {
            throw new ContractViolationException("Parameter \"quantity\" is NULL");
        }
        if (productId == null || productId.isEmpty()) {
            throw new ContractViolationException("Parameter \"productId\" is NULL");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem item = (OrderItem) o;

        return productId.equals(item.productId);

    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
