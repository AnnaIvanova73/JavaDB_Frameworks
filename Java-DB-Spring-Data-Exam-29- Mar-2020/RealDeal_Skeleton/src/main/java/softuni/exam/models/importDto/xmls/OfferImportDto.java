package softuni.exam.models.importDto.xmls;

import org.hibernate.validator.constraints.Length;
import softuni.exam.config.LocalDateTimeAdapter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

//<offers>
//    <offer>
//        <description>kachvash se i karash populace's irrigating advisories exhausting exceptions headphones abdicating
//            diagnostic devastated newsagents wrapping's discount's
//        </description>
//        <price>222359</price>
//        <added-on>2019-12-23 17:02:39</added-on>
//        <has-gold-status>true</has-gold-status>
//        <car>
//            <id>70</id>
//        </car>
//        <seller>
//            <id>84</id>
//        </seller>
//    </offer>

    @XmlElement
    private BigDecimal price;
    @XmlElement
    private String description;

    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;


    @XmlElement(name = "added-on")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime addedOn;

    @XmlElement(name = "car")
    private CarIdImportDto car;
    @XmlElement(name = "seller")
    private SellerIdImportDto seller;

    public OfferImportDto() {
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public CarIdImportDto getCar() {
        return car;
    }

    public void setCar(CarIdImportDto car) {
        this.car = car;
    }

    public SellerIdImportDto getSeller() {
        return seller;
    }

    public void setSeller(SellerIdImportDto seller) {
        this.seller = seller;
    }
}
