package es.blaster.exchangeConversion;

public class ResponseBean {

    private double amount;  // Agrega un campo para la cantidad de dinero ingresada por el usuario
    private String currencyFrom;  // Agrega un campo para la moneda de origen ingresada por el usuario
    private String currencyTo;  // Agrega un campo para la moneda de destino ingresada por el usuario 
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
