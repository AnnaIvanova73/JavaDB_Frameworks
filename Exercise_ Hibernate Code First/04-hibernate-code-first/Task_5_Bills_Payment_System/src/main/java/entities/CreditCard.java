package entities;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Month;

@Entity
@DiscriminatorValue(value = "credit_card")
public class CreditCard extends BillingDetail {
    private String cardType;
    private Month month;
    private int year;


    public CreditCard() {
    }

    @Column(name = "card_type",nullable = false)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    @Column(name = "expiration_month",nullable = false)
    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
    @Column(name = "expiration_year",nullable = false,columnDefinition = "INT")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
