package pt.rho.showmethemoney.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/showmethemoney", name = "Enjoy the currencies")
public class ExchangeRatesController {

    String apiBaseURL = "https://api.exchangeratesapi.io";

    /**
     * Get exchange rate from Currency A to Currency B
     *
     * @param exchangeA
     * @param exchangeB
     * @return
     */
    @RequestMapping(value = "/{exchangeA}/{exchangeB}", method = RequestMethod.GET)
    @GetMapping("/{exchangeA}/{exchangeB}")
    public Double getExchangeRateFromAtoB(@PathVariable("exchangeA") String exchangeA, @PathVariable("exchangeB") String exchangeB) {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRates er = restTemplate.getForObject(apiBaseURL + "/latest?base=" + exchangeA, ExchangeRates.class);
        Double rateB = er.getRates().get(exchangeB);
        return rateB;
    }

    /**
     * Get all exchange rates from Currency A
     *
     * @param exchange
     * @return
     */
    @RequestMapping(value = "/{exchange}", method = RequestMethod.GET)
    @GetMapping("/{exchange}")
    public Map getExchangeRatesForCurrency(@PathVariable("exchange") String exchange) {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRates er = restTemplate.getForObject(apiBaseURL + "/latest?base=" + exchange, ExchangeRates.class);
        // remove the searched currency
        er.getRates().remove(exchange);
        return er.getRates();
    }

    /**
     * Get value conversion from Currency A to Currency B
     * or
     * Get value conversion from Currency A to a list of supplied currencies
     *
     * @param exchangeA
     * @param exchanges
     * @return
     */
    @RequestMapping(value = "/{exchangeA}/conversion/{exchanges}", method = RequestMethod.GET)
    @GetMapping("/{exchangeA}/conversion/{exchanges}")
    public Map getConversionRateFromAtoB(@PathVariable("exchangeA") String exchangeA, @PathVariable("exchanges") String exchanges) {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeRates er = restTemplate.getForObject(apiBaseURL + "/latest?base=" + exchangeA, ExchangeRates.class);

        String[] listOfCurrencies = exchanges.split(",");

        Map<String, Double> result = new HashMap<>();
        for (String c : listOfCurrencies) {
            Double rate = er.getRates().get(c);
            Double conversion = 1 / rate;
            result.put(c, conversion);
        }

        return result;
    }

}
