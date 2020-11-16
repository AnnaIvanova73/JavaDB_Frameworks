package softuni.springintrousers.domain.entities;

import jdk.jfr.Enabled;
import org.springframework.data.annotation.AccessType;
import softuni.springintrousers.annotations.email.Email;
import softuni.springintrousers.annotations.password.Password;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    @Transient
    private String fullName;
    private String username;
    private String password;
    private String email;
    private byte[] profilePicture;
    private LocalDateTime registeredOn;
    private LocalDateTime lastTimeLoggedIn;
    private Integer age;
    private Boolean isDeleted;
    private Set<User> friends;
    private Town bornTown;
    private Town currentlyLiving;
    private Set<Album> albums;
    public User() {
    }

   @Column(name = "first_name",nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name",nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Transient
    public String getFullName() {
        return fullName;
    }


    public void setFullName() {
        this.fullName =  this.getFirstName() + " " + this.getLastName();
    }


    @Column(name = "username",nullable = false)
    @Size(min = 4, max = 30)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password",nullable = false)
    @Password(minLength = 6, maxLength = 40,containsDigit = true,containsLowerCase = true,
    containsUpperCase = true,containsSpecialSymbols = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name = "email",nullable = false,unique = true)
    @Email(maxLength = 50,minLength = 20,isValid = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "registered_on")
    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }
    @Column(name = "last_time_logged_in")
    public LocalDateTime getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDateTime lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    @Column(name="age",nullable = false)
    @Size(min = 1,max = 120)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "is_deleted",columnDefinition = "TINYINT(0)",nullable = false)
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToMany
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "bord_town_id",referencedColumnName = "id")
    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }
    @ManyToOne(optional = false)
    @JoinColumn(name = "current_town_id",referencedColumnName = "id")
    public Town getCurrentlyLiving() {
        return currentlyLiving;
    }

    public void setCurrentlyLiving(Town currentlyLiving) {
        this.currentlyLiving = currentlyLiving;
    }

    @OneToMany(mappedBy = "user",targetEntity = Album.class)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @Lob
    @Column(name = "profile_picture",nullable = true)
    @Size(max = 1024 * 1024)
    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
