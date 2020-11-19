package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.SupplierExportDto;
import softuni.jsonprocessing.domain.dto.importDto.SupplierDtoImport;
import softuni.jsonprocessing.domain.entities.Supplier;
import softuni.jsonprocessing.domain.repo.SupplierRepository;
import softuni.jsonprocessing.service.SupplierService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIERS_PATH = "src/main/resources/json/suppliers.json";
    private static final String SUPPLIER_EXPORT_PATH_QUERY_3 = "src/main/resources/json-export/query3.json";

    private final Gson gson;
    private final ModelMapper mapper;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(Gson gson,
                               ModelMapper mapper,
                               SupplierRepository supplierRepository) {
        this.gson = gson;
        this.mapper = mapper;
        this.supplierRepository = supplierRepository;
    }


    @Override
    public void seedSuppliers() throws IOException {
        String source = String.join("", Files.readAllLines(Path.of(SUPPLIERS_PATH)));
        SupplierDtoImport[] dtosSupplier =
                this.gson.fromJson(source, SupplierDtoImport[].class);

        for (SupplierDtoImport dto : dtosSupplier) {
            Supplier supplier = this.mapper.map(dto, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier);
        }
    }

    @Override
    public String findAllLocalSuppliers() throws IOException {
        Set<Supplier> suppliers = this.supplierRepository.findAllByImporterFalse();

        List<SupplierExportDto> exportDto = new ArrayList<>();

        for (Supplier supplier : suppliers) {
            SupplierExportDto dto = this.mapper.map(supplier, SupplierExportDto.class);
            int size = supplier.getParts().size();
            dto.setPartsCount(size);
            exportDto.add(dto);
        }

        System.out.println(this.gson.toJson(exportDto));

        Writer writer = new FileWriter(SUPPLIER_EXPORT_PATH_QUERY_3);
        this.gson.toJson(exportDto,writer);
        writer.flush();
        writer.close();
        System.out.println();
        return "Done --> resources/json-export/query3.json";
    }
}
