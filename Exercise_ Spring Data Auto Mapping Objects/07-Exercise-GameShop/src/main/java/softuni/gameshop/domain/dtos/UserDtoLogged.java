package softuni.gameshop.domain.dtos;

public class UserDtoLogged {

    private String fullName;
    private String email;

    public UserDtoLogged() {
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
