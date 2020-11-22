package softuni.shopxml.domain.dto.export.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRootExportDtoQuery4 {

    @XmlElement(name = "user")
    private List<UserExportDtoQuery4> users;

    public UserRootExportDtoQuery4() {
    }

    public List<UserExportDtoQuery4> getUsers() {
        return users;
    }

    public void setUsers(List<UserExportDtoQuery4> users) {
        this.users = users;
    }
}
