package app.ccb.domain.entities;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BaseEntity {

    private String accountNumber;
    private BigDecimal balance;
    private Client client;
    private Set<Card> cards;

    public BankAccount() {
    }

    @Column(name = "account_number",nullable = false)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    @Column
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @OneToOne(mappedBy = "bankAccount",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToMany(mappedBy = "bankAccount")
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
