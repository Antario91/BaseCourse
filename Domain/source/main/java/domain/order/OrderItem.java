package domain.order;

public class OrderItem {
    private final double quantity;
    private final long productId;

    private OrderItem () {
        quantity = 0;
        productId = 0;
    }

    public OrderItem(double quantity, long productId) {
        if (quantity <= 0 || productId <= 0){
            throw new IllegalArgumentException ();
        }
        this.quantity = quantity;
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public long getProductId() {
        return productId;
    }

    //TODO equals() и hashcode() всё-таки надо???
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
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantity);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
