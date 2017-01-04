import domain.order.Order;
import domain.order.OrderItem;
import domain.order.exceptions.ProductInOrderIsAlreadyOrderedException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestOrder {

    @Test(expected = ProductInOrderIsAlreadyOrderedException.class)
    public void constructor() throws ProductInOrderIsAlreadyOrderedException {
        OrderItem item1 = new OrderItem(2, "Apples");
        OrderItem item2 = new OrderItem(5, "Milk");
        OrderItem item3 = new OrderItem(1, "Milk");
        List<OrderItem> items = new ArrayList<OrderItem>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Order order = new Order("Alex", items);
    }

    @Test(expected = ProductInOrderIsAlreadyOrderedException.class)
    public void addItems() throws ProductInOrderIsAlreadyOrderedException {
        OrderItem item1 = new OrderItem(2, "Apples");
        OrderItem item2 = new OrderItem(5, "Milk");
        OrderItem item3 = new OrderItem(1, "Milk");
        OrderItem item4 = new OrderItem(1, "Cake");

        List<OrderItem> items = new ArrayList<OrderItem>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);

        List<OrderItem> addableItems = new ArrayList<OrderItem>();
        addableItems.add(item4);
        addableItems.add(item3);

        Order order = new Order("Alex", items);
        order.addOrderItems(addableItems);
    }

    @Test
    public void deleteItems() throws ProductInOrderIsAlreadyOrderedException {
        OrderItem item1 = new OrderItem(2, "Apples");
        OrderItem item2 = new OrderItem(5, "Milk");
        OrderItem item3 = new OrderItem(1, "Cake");
        List<OrderItem> items = new ArrayList<OrderItem>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Order order = new Order("Alex", items);

        List<String> deletableItems = new ArrayList<String>();
        deletableItems.add("Milk");
        deletableItems.add("Cake");
        order.deleteOrderItems(deletableItems);

        List<OrderItem> expectedItems = new ArrayList<OrderItem>();
        expectedItems.add(new OrderItem(25, "Apples"));

        assertThat(order.getOrderItems(), equalTo(expectedItems));
    }
}
