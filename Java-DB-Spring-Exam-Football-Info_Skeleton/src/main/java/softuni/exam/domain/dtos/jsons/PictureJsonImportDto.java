package softuni.exam.domain.dtos.jsons;

import com.google.gson.annotations.Expose;

public class PictureJsonImportDto {

    // "picture": {
    //        "url": "fc_pictures_2"
    //      }
    @Expose
    private String url;

    public PictureJsonImportDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
