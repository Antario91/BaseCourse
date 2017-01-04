import domain.customer.CustomerRepo;
import domain.order.*;
import domain.order.exceptions.ProductInOrderIsAlreadyOrderedException;
import domain.product.Product;
import domain.product.ProductPrice;
import domain.product.ProductRepo;
import domain.product.exceptions.DateIntersectionInProductPriceException;
import domain.product.exceptions.NotAvailableProductPriceException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

import java.util.*;

import static org.mockito.Mockito.when;

public class TestOrderService {
    @Mock
    CustomerRepo customerRepo;
    @Mock
    OrderRepo orderRepo;
    @Mock
    ProductRepo productRepo;

    @InjectMocks
    OrderServiceImpl orderService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrderPrice() throws ProductInOrderIsAlreadyOrderedException, DateIntersectionInProductPriceException, NotAvailableProductPriceException {
        List<OrderItem> items = new ArrayList<OrderItem>();
        items.add(new OrderItem(2, "Apples"));
        items.add(new OrderItem(5, "Milk"));
        items.add(new OrderItem(1, "Cake"));
        when(orderRepo.get("1")).thenReturn(new Order("Alex", items));

        ProductPrice price1 = new ProductPrice(11, new GregorianCalendar(2001, 0, 15).getTime());
        ProductPrice price2 = new ProductPrice(17, new GregorianCalendar(2017, 0, 3).getTime());
        ProductPrice price3 = new ProductPrice(25, new GregorianCalendar(2020, 0, 17).getTime());
        List<ProductPrice> prices = new ArrayList<ProductPrice>();
        prices.add(price1);
        prices.add(price2);
        prices.add(price3);

        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Apples", "kg", prices));
        products.add(new Product("Milk", "L", prices));
        products.add(new Product("Cake", "kg", prices));

        List<String> productIds = new ArrayList<String>();
        productIds.add("Apples");
        productIds.add("Milk");
        productIds.add("Cake");
        when(productRepo.getProducts(productIds)).thenReturn(products);

        double orderPrice = orderService.getOrderPrice("1");

        assertEquals(136.0, orderPrice, 0.0);
    }
}
