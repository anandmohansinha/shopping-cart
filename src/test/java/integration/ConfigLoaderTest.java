package integration;

import com.java.client.PriceApiClient;
import com.java.config.ConfigLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {
    @Test
    void shouldLoadTaxRateFromPropertiesFile() {

        double taxRate = ConfigLoader.loadTaxRate();
        assertEquals(0.125, taxRate);
    }


    public static class PriceApiClientTest {
        private final PriceApiClient client = new PriceApiClient();

        @Test
        void shouldReturnCorrectPriceForCornflakes() throws Exception {
            double price = client.getPrice("cornflakes");
            assertEquals(2.52, price);
        }

        @Test
        void shouldReturnCorrectPriceForWeetabix() throws Exception {
            double price = client.getPrice("weetabix");
            assertEquals(9.98, price);
        }

        @Test
        void shouldThrowExceptionForInvalidProduct() {
            assertThrows(Exception.class, () -> {
                client.getPrice("invalid-product");
            });
        }
    }
}
