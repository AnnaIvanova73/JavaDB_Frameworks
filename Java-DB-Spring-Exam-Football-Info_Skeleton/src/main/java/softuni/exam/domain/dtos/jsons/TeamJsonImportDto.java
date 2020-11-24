package softuni.exam.domain.dtos.jsons;

import com.google.gson.annotations.Expose;

public class TeamJsonImportDto {

    @Expose
    private String name;
    @Expose
    private PictureJsonImportDto picture;

    public TeamJsonImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureJsonImportDto getPicture() {
        return picture;
    }

    public void setPicture(PictureJsonImportDto picture) {
        this.picture = picture;
    }
}
