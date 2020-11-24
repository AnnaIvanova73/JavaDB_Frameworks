package softuni.exam.domain.dtos.xmls;


import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "picture")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureImportDto {

    @XmlElement
    private String url;

    public PictureImportDto() {
    }

    @NotEmpty(message = "Invalid picture")
    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
