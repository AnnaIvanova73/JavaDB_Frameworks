package app.ccb.domain.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "card-number")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardNumberImportDto {

    @XmlElement(name = "card-number")
    private String cardNumber;

    public CardNumberImportDto() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
