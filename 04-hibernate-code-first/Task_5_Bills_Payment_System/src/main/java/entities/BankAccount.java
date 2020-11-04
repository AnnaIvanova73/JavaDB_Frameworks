package entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "bank_account")
public  class BankAccount extends BillingDetail {
    private String bankName;
    private String swiftCode;

    public BankAccount() {
    }

    @Column(name = "bank_name",nullable = false,length = 45)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    @Column(name = "swift_code",nullable = false,length = 35)
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
