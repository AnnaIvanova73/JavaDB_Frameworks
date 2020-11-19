package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.importDto.PartDtoImport;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.entities.Supplier;
import softuni.jsonprocessing.domain.repo.PartRepository;
import softuni.jsonprocessing.domain.repo.SupplierRepository;
import softuni.jsonprocessing.service.PartService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final static String PARTS_PATH = "src/main/resources/json/parts.json";

    private final Gson gson;
    private final ModelMapper mapper;
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public PartServiceImpl(Gson gson,
                           ModelMapper mapper,
                           PartRepository partRepository,
                           SupplierRepository supplierRepository) {
        this.gson = gson;
        this.mapper = mapper;
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
    }


    @Override
    public void seedParts() throws Exception {
        String source = String.join("", Files.readAllLines(Path.of(PARTS_PATH)));

        PartDtoImport[] partDtoImports = this.gson.fromJson(source, PartDtoImport[].class);

        for (PartDtoImport dtoPart : partDtoImports) {
            Part part = this.mapper.map(dtoPart, Part.class);

            Supplier randomSupplier = getRandomSupplier();
            part.setSupplier(randomSupplier);

            this.partRepository.saveAndFlush(part);
        }
    }

    private Supplier getRandomSupplier() throws Exception {
        Random random = new Random();
        long randomSupplier = (long) random.nextInt((int) this.supplierRepository.count() - 1) + 1;

        Optional<Supplier> supplier = this.supplierRepository.findById(randomSupplier);

        if (supplier.isEmpty()) {
            throw new Exception("Supplier don't exits");
        }

        return supplier.get();
    }

}
