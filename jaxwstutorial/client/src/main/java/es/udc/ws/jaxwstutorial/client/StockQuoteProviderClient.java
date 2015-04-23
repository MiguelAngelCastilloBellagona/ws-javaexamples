package es.udc.ws.jaxwstutorial.client;

import es.udc.ws.jaxwstutorial.wsdl.IncorrectTickerSymbolException;
import es.udc.ws.jaxwstutorial.wsdl.StockQuoteProvider;
import es.udc.ws.jaxwstutorial.wsdl.StockQuoteProviderService;
import es.udc.ws.jaxwstutorial.wsdl.TradePrice;

import javax.xml.ws.BindingProvider;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockQuoteProviderClient {

    private final static Log logger = LogFactory.getLog(StockQuoteProviderClient.class);
    public static StockQuoteProviderService stockQuoteProviderService = new StockQuoteProviderService();

    public static void main(String args[]) {

        try {

            if (args.length < 2) {
                logger.error(MessageFormat.format("Usage: {0} stockQuoteProviderURL [tickerSymbol1 tickerSymbol2 ...]",
                        StockQuoteProviderClient.class.getName()));
                System.exit(-1);
            }

            String stockQuoteProviderURL = args[0];
            System.out.println(stockQuoteProviderURL);
            List<String> tickerSymbols = new ArrayList<String>();
            for (int i = 1; i < args.length; i++) {
                tickerSymbols.add(args[i]);
            }

            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("Requesting {0} ticker simbol(s)",
                        Integer.valueOf(tickerSymbols.size())));
            }


            StockQuoteProvider stockQuoteProvider = stockQuoteProviderService
                    .getStockQuoteProviderPort();
            ((BindingProvider) stockQuoteProvider).getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY, stockQuoteProviderURL);

            List<TradePrice> tradePrices =
                    stockQuoteProvider.getLastTradePrices(tickerSymbols);
            if (logger.isDebugEnabled()) {
                logger.debug(MessageFormat.format("Received {0} ticker simbol(s)",
                        Integer.valueOf(tradePrices.size())));
            }


            for (int i = 0; i < tradePrices.size(); i++) {
                if (logger.isDebugEnabled()) {
                    logger.debug(
                            MessageFormat.format("Ticker symbol = {0} | Price = {1} | Elapsed seconds = {2}",
                            tradePrices.get(i).getTickerSymbol(),
                            Double.valueOf(tradePrices.get(i).getPrice()),
                            Integer.valueOf(tradePrices.get(i)
                            .getElapsedSeconds())));
                }

            }

        } catch (IncorrectTickerSymbolException e) {
            logger.error(MessageFormat.format("Unable to get ticker symbol \"{0}\"",
                    e.getFaultInfo().getIncorrectTickerSymbol()));
        }

    }
}
