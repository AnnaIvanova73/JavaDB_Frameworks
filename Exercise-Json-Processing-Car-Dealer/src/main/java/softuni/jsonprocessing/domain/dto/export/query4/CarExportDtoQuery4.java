package softuni.jsonprocessing.domain.dto.export.query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarExportDtoQuery4 {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long travelledDistance;
    @Expose
    private List<PartExportDtoQuery4> parts;


    public CarExportDtoQuery4() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<PartExportDtoQuery4> getParts() {
        return parts;
    }

    public void setParts(List<PartExportDtoQuery4> parts) {
        this.parts = parts;
    }
}
