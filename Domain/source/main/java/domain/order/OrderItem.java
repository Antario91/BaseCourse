package domain.order;

public class OrderItem {
    private long quantity;
    private long productId;

    private OrderItem () {}

    public OrderItem(long quantity, long productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        if (quantity != orderItem.quantity) return false;
        return productId == orderItem.productId;

    }

    @Override
    public int hashCode() {
        int result = (int) (quantity ^ (quantity >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
