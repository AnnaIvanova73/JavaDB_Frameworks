package softuni.jsonprocessing.domain.dto.importDto;

import com.google.gson.annotations.Expose;

public class SupplierDtoImport {
    @Expose
    private String name;
    @Expose
    private boolean isImporter;

    public SupplierDtoImport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
