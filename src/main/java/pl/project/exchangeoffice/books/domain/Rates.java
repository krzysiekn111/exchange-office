package pl.project.exchangeoffice.books.domain;

import java.util.Objects;

public class Rates {

    private String currency;
    private Float exchangeRateToPLN;

    public Rates() {}

    public Rates(String currency, Float exchangeRateToPLN) {
        this.currency = currency;
        this.exchangeRateToPLN = exchangeRateToPLN;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getExchangeRateToPLN() {
        return exchangeRateToPLN;
    }

    public void setExchangeRateToPLN(Float exchangeRateToPLN) {
        this.exchangeRateToPLN = exchangeRateToPLN;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rates rates = (Rates) o;
        return currency.equals(rates.currency) && exchangeRateToPLN.equals(rates.exchangeRateToPLN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, exchangeRateToPLN);
    }

    @Override
    public String toString() {
        return "Rates{" +
                "currency='" + currency + '\'' +
                ", exchangeRateToPLN=" + exchangeRateToPLN +
                '}';
    }

}