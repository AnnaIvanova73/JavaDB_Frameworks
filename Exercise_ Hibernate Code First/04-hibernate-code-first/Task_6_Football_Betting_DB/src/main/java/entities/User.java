package entities;

import entities.base.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private BigDecimal balance;

    public User() {
    }

  @Column(name = "username",length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
  @Column(name = "full_name",length = 50)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
