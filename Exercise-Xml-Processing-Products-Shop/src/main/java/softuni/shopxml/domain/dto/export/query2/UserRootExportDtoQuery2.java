package softuni.shopxml.domain.dto.export.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRootExportDtoQuery2 {

    @XmlElement(name = "user")
    private List<UserExportDtoQuery> users;

    public UserRootExportDtoQuery2() {
    }

    public List<UserExportDtoQuery> getUsers() {
        return users;
    }

    public void setUsers(List<UserExportDtoQuery> users) {
        this.users = users;
    }
}
