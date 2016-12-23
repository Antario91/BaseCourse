package domain.order;

import domain.ParamIsNullException;

public class OrderItem {
    private final double quantity;
    private final String productId;

    private OrderItem () {
        quantity = 0;
        productId = null;
    }

    public OrderItem(double quantity, String productId) throws ParamIsNullException {
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

    private void validateConstructorsParams(double quantity, String productId) throws ParamIsNullException {
        if (quantity <= 0) {
            throw new ParamIsNullException("quantity");
        }
        if (productId == null || productId.isEmpty()) {
            throw new ParamIsNullException("productId");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        if (Double.compare(orderItem.quantity, quantity) != 0) return false;
        return productId.equals(orderItem.productId);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantity);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
