package pt.rho.showmethemoney.api;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRatesServiceImpl {

    private String apiBaseURL = "https://api.exchangeratesapi.io";

    @Cacheable("rates")
    public ExchangeRates getExchangeRates(String exchange) {
        RestTemplate restTemplate = new RestTemplate();

        ExchangeRates er;
        try {
            er = restTemplate.getForObject(apiBaseURL + "/latest?base=" + exchange.toUpperCase(), ExchangeRates.class);
            er.setValue(1.0);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(exchange.toUpperCase() + " is an invalid exchange!", e);
        } catch (Exception e) {
            throw new RuntimeException("Service problem detect!", e);
        }
        return er;
    }
}
