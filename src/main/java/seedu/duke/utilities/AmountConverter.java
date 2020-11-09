package seedu.duke.utilities;

//@@author killingbear999
/** It is to convert the amount if the input currency does not match with the default currency in the system. */
public class AmountConverter {
    private String currency;
    private double amount;
    private String defaultCurrency;
    
    private final String[][] exchangeRates = {
            {"SGDUSD", "USDSGD", "SGDCNY", "CNYSGD"},
            {"0.74", "1.36", "4.99", "0.20"},
    };
    
    public AmountConverter(String currency, double amount, String defaultCurrency) {
        this.currency = currency;
        this.amount = amount;
        this.defaultCurrency = defaultCurrency;
    }
    
    /** It is to update the amount after converting to the default currency. */
    public double updateAmount() {
        if (currency.equals("USD") && defaultCurrency.equals("SGD")) {
            amount = amount * Double.parseDouble(exchangeRates[1][1]);
            DecimalFormatter decimalFormatter = new DecimalFormatter();
            amount = decimalFormatter.convert(amount);
        } else if (currency.equals("CNY") && defaultCurrency.equals("SGD")) {
            amount = amount * Double.parseDouble(exchangeRates[1][3]);
            DecimalFormatter decimalFormatter = new DecimalFormatter();
            amount = decimalFormatter.convert(amount);
        } else if (currency.equals("SGD") && defaultCurrency.equals("USD")) {
            amount = amount * Double.parseDouble(exchangeRates[1][0]);
            DecimalFormatter decimalFormatter = new DecimalFormatter();
            amount = decimalFormatter.convert(amount);
        } else if (currency.equals("SGD") && defaultCurrency.equals("CNY")) {
            amount = amount * Double.parseDouble(exchangeRates[1][2]);
            DecimalFormatter decimalFormatter = new DecimalFormatter();
            amount = decimalFormatter.convert(amount);
        }
        return amount;
    }
    
    /** It is to update the symbol of currency to the default currency. */
    public String updateCurrency() {
        currency = defaultCurrency;
        return currency;
    }
}
