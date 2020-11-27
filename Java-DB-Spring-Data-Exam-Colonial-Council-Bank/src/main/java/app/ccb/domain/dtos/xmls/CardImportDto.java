package app.ccb.domain.dtos.xmls;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "card")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardImportDto {


    @XmlAttribute(name = "status")
    private String cardStatus;
    @XmlAttribute(name ="account-number" )
    private String bankAccount;
    @XmlElement(name = "card-number")
    String cardNumber;

    public CardImportDto() {
    }

    @NotNull
    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    @NotNull
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
