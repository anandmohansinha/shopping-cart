import com.java.client.PriceApi;
import com.java.client.PriceApiClient;

import java.util.Map;

public class MockPriceApi implements PriceApi {

    private final Map<String, Double> prices;

    public MockPriceApi(Map<String, Double> prices){
        this.prices = prices;
    }
    @Override
    public double getPrice(String productName) throws Exception {
        return prices.getOrDefault(productName, 0.0);
    }
}
