package app.ccb.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity{

    private String cardNumber;
    private String cardStatus;
    private BankAccount bankAccount;

    public Card() {
    }

    @Column(name = "card_number",nullable = false)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    @Column(name = "card_status",nullable = false)
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
