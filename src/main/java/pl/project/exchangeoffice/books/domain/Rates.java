package pl.project.exchangeoffice.books.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class Rates {

    public Rates(String currency, Float exchangeRateToPLN) {
        this.currency = currency;
        this.exchangeRateToPLN = exchangeRateToPLN;
    }


    @Override
    public String toString() {
        return "Rates{" +
                "currency='" + currency + '\'' +
                ", exchangeRateToPLN=" + exchangeRateToPLN +
                '}';
    }

    private String currency;
    private Float exchangeRateToPLN;


//    @Scheduled(fixedRate = 5000)
//    public void count() {
//        System.out.println(56);
//    }

}