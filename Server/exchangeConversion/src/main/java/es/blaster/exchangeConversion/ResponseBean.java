package es.blaster.exchangeConversion;

public class ResponseBean {

    private double amount;
    private String currencyFrom;
    private String currencyTo;
    private double conversionRateFromTo;
    private double conversionRateToFrom;
    private double result;
    private String errorDescription;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public double getConversionRateFromTo() {
        return conversionRateFromTo;
    }

    public void setConversionRateFromTo(double conversionRateFromTo) {
        this.conversionRateFromTo = conversionRateFromTo;
    }

    public double getConversionRateToFrom() {
        return conversionRateToFrom;
    }

    public void setConversionRateToFrom(double conversionRateToFrom) {
        this.conversionRateToFrom = conversionRateToFrom;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
