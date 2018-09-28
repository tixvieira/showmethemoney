package pt.rho.showmethemoney.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/showmethemoney", name = "Enjoy the currencies")
public class ExchangeRatesController {

    @Autowired
    private ExchangeRatesServiceImpl exchangeRatesService;

    /**
     * Get exchange rate from Currency A to Currency B
     *
     * @param exchangeA
     * @param exchangeB
     * @return
     */
    @RequestMapping(value = "/{exchangeA}/{exchangeB}", method = RequestMethod.GET)
    public Double getExchangeRateFromAtoB(@PathVariable("exchangeA") String exchangeA, @PathVariable("exchangeB") String exchangeB) {
        ExchangeRates er = exchangeRatesService.getExchangeRates(exchangeA);
        Double rateB = er.getRates().get(exchangeB.toUpperCase());
        return rateB;
    }

    /**
     * Get all exchange rates from Currency A
     *
     * @param exchange
     * @return
     */
    @RequestMapping(value = "/{exchange}", method = RequestMethod.GET)
    public Map getExchangeRatesForCurrency(@PathVariable("exchange") String exchange) {
        ExchangeRates er = exchangeRatesService.getExchangeRates(exchange);
        // remove the searched currency
        er.getRates().remove(exchange.toUpperCase());
        return er.getRates();
    }

    /**
     * Get value conversion from Currency A to Currency B
     * or
     * Get value conversion from Currency A to a list of supplied currencies
     *
     * @param value
     * @param exchangeA
     * @param exchanges
     * @return
     */
    @RequestMapping(value = "/{value}/{exchangeA}/{exchanges}", method = RequestMethod.GET)
    public Map getConversionRateFromAtoB(@PathVariable("value") Double value, @PathVariable("exchangeA") String exchangeA, @PathVariable("exchanges") String exchanges) {
        ExchangeRates er = exchangeRatesService.getExchangeRates(exchangeA);

        String[] listOfCurrencies = exchanges.toUpperCase().split(",");

        er.setValue(value);

        Map<String, String> result = new HashMap<>();
        for (String c : listOfCurrencies) {
            Double rate = er.getRates().get(c);
            if (rate != null) {
                Double conversion = value * rate;
                result.put(c, conversion.toString());
            } else {
                result.put(c, "Exchange rate not found!");
            }
        }

        return result;
    }

}
