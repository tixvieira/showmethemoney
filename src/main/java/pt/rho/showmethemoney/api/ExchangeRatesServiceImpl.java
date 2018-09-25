package pt.rho.showmethemoney.api;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRatesServiceImpl {

    private String apiBaseURL = "https://api.exchangeratesapi.io";

    @Cacheable("rates")
    public ExchangeRates getExchangeRates(String exchange) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiBaseURL + "/latest?base=" + exchange, ExchangeRates.class);
    }
}
