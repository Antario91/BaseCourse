package domain.order;

public class OrderItem {
    private final double quantity;
    private final String productId;

    private OrderItem () {
        quantity = 0;
        productId = null;
    }

    public OrderItem(double quantity, String productId) {
        checkConstructorParametersForNull(quantity, productId);
        this.quantity = quantity;
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getProductId() {
        return productId;
    }

    private void checkConstructorParametersForNull(double quantity, String productId) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Parameter \"quantity\" is NULL");
        }
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Parameter \"productId\" is NULL");
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
