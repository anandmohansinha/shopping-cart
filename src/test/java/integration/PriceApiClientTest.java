package integration;

import com.java.client.PriceApiClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PriceApiClientTest {

    @Test
    void shouldFetchRealPriceForCornflakes() throws Exception {

        PriceApiClient client = new PriceApiClient();

        double price = client.getPrice("cornflakes");

        assertEquals(2.52, price);
    }
    @Test
    void shouldFetchRealPriceForWeetabix() throws Exception {

        PriceApiClient client = new PriceApiClient();

        double price = client.getPrice("weetabix");

        assertEquals(9.98, price);
    }

    @Test
    void shouldThrowExceptionForInvalidProduct() {

        PriceApiClient client = new PriceApiClient();

        assertThrows(Exception.class,
                () -> client.getPrice("invalid-product"));
    }
}
