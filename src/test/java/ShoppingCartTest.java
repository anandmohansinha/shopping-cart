import com.java.cart.ShoppingCart;
import com.java.client.PriceApi;
import com.java.config.TaxProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    void setup() {

        // fake price API (no real API call)
        PriceApi fakeApi = new MockPriceApi(Map.of(
                "cornflakes", 2.52,
                "weetabix", 9.98,
                "cheerios", 5.00
        ));

        // fixed tax rate (no config file dependency)
        TaxProvider fixedTaxProvider = () -> 0.125;

        cart = new ShoppingCart(fakeApi, fixedTaxProvider);
    }

    // ---------------- POSITIVE TESTS ----------------

    @Test
    void shouldAddSingleProduct() throws Exception {
        cart.addProduct("cornflakes", 1);
        assertEquals(1, cart.getItems().size());
        assertEquals(2.52, cart.getSubtotal());
    }

    @Test
    void shouldAddMultipleQuantitySameProduct() throws Exception {
        cart.addProduct("cornflakes", 2);
        assertEquals(5.04, cart.getSubtotal());
    }

    @Test
    void shouldMergeSameProductEntries() throws Exception {
        cart.addProduct("cornflakes", 1);
        cart.addProduct("cornflakes", 1);

        assertEquals(1, cart.getItems().size());
        assertEquals(5.04, cart.getSubtotal());
    }

    @Test
    void shouldAddMultipleDifferentProducts() throws Exception {
        cart.addProduct("cornflakes", 2);
        cart.addProduct("weetabix", 1);

        assertEquals(15.02, cart.getSubtotal());
    }

    @Test
    void shouldCalculateTaxCorrectly() throws Exception {
        cart.addProduct("cornflakes", 2); // subtotal = 5.04
        assertEquals(0.63, cart.getTax());
    }

    @Test
    void shouldCalculateTotalCorrectly() throws Exception {
        cart.addProduct("cornflakes", 2);
        cart.addProduct("weetabix", 1);

        assertEquals(16.90, cart.getTotal());
    }

    @Test
    void shouldRoundToTwoDecimalPlaces() throws Exception {
        cart.addProduct("cheerios", 1); // 5 * 0.125 = 0.625 → 0.63
        assertEquals(0.63, cart.getTax());
    }

    // ---------------- NEGATIVE TESTS ----------------

    @Test
    void emptyCartShouldReturnZeroTotals() {
        assertEquals(0.0, cart.getSubtotal());
        assertEquals(0.0, cart.getTax());
        assertEquals(0.0, cart.getTotal());
    }

    @Test
    void emptyOrNullProductNameShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> cart.addProduct(null, 5));
        assertThrows(IllegalArgumentException.class, () -> cart.addProduct("", 5));
    }

    @Test
    void negativeProductQuantityShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> cart.addProduct("cheerios", -1));
    }

    @Test
    void addingUnknownProductShouldUseZeroPrice() throws Exception {
        cart.addProduct("unknown", 1);
        assertEquals(0.0, cart.getSubtotal());
    }

    @Test
    void shouldHandleLargeQuantity() throws Exception {
        cart.addProduct("cornflakes", 1000);
        assertEquals(2520.00, cart.getSubtotal());
    }

    @Test
    void itemsMapShouldBeUnmodifiable() throws Exception {
        cart.addProduct("cornflakes", 1);
        assertThrows(UnsupportedOperationException.class,
                () -> cart.getItems().put("test", null));
    }
}