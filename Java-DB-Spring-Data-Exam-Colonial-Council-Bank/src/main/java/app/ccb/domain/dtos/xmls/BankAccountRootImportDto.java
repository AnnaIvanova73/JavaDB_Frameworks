package app.ccb.domain.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountRootImportDto {

    @XmlElement(name = "bank-account")
    private List<BankAccountImportDto> bankAccounts;


    public BankAccountRootImportDto() {
    }

    public List<BankAccountImportDto> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccountImportDto> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
