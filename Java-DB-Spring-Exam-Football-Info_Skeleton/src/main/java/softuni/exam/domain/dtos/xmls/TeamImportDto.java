package softuni.exam.domain.dtos.xmls;

import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamImportDto {

    @XmlElement
    private String name;
    @XmlElement(name = "picture")
    private PictureImportDto url;


    public TeamImportDto() {
    }

    @Length(min = 3, max = 20, message = "Invalid team")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public PictureImportDto getUrl() {
        return url;
    }

    public void setUrl(PictureImportDto url) {
        this.url = url;
    }
}
