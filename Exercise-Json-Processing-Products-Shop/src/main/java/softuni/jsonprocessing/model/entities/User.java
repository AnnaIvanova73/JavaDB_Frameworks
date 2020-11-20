package softuni.jsonprocessing.model.entities;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String firstName;
    private String lastName;
    private int age;

    private Product buyer;
    private Product seller;

    Set<User> friends;
    
    public User() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name",nullable = false)
    @Length(min = 3)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_friends",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "friend_id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }


    @OneToOne(mappedBy = "buyer",fetch = FetchType.EAGER)
    public Product getBuyer() {
        return buyer;
    }

    public void setBuyer(Product buyer) {
        this.buyer = buyer;
    }
    @OneToOne(mappedBy = "seller",fetch = FetchType.EAGER)
    public Product getSeller() {
        return seller;
    }

    public void setSeller(Product seller) {
        this.seller = seller;
    }
}
