package softuni.jsonprocessing.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.query3.SupplierChildExportDto;
import softuni.jsonprocessing.domain.dto.export.query3.SupplierRootExportDto;
import softuni.jsonprocessing.domain.dto.importDto.suppliers.SupplierChildImportDto;
import softuni.jsonprocessing.domain.dto.importDto.suppliers.SupplierRootImportDto;
import softuni.jsonprocessing.domain.entities.Supplier;
import softuni.jsonprocessing.domain.repo.SupplierRepository;
import softuni.jsonprocessing.service.SupplierService;
import softuni.jsonprocessing.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final static String SUPPLIERS_IMPORT_PATH = "src/main/resources/xml-import/suppliers.xml";
    private final static String SUPPLIERS_EXPORT_PATH_QUERY_3 = "src/main/resources/xml-export/query3.xml";

    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;

    @Autowired
    public SupplierServiceImpl(ModelMapper modelMapper,
                               SupplierRepository supplierRepository,
                               XmlParser xmlParser) {

        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedSuppliers() throws IOException, JAXBException {
        SupplierRootImportDto rootDto = this.xmlParser.parseXml(SupplierRootImportDto.class, SUPPLIERS_IMPORT_PATH);

        for (SupplierChildImportDto supplier : rootDto.getSuppliers()) {
            Supplier supplier1 = this.modelMapper.map(supplier, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier1);
        }
    }

    @Override
    public String findAllLocalSuppliers() throws JAXBException {
        List<SupplierChildExportDto> child = new ArrayList<>();

        Set<Supplier> localSuppliers = this.supplierRepository.findAllByImporterFalse();
        for (Supplier localSupplier : localSuppliers) {
            SupplierChildExportDto dto = this.modelMapper.map(localSupplier, SupplierChildExportDto.class);
            dto.setPartsCount(localSupplier.getParts().size());
            child.add(dto);
        }
        SupplierRootExportDto root = new SupplierRootExportDto();
        root.setSuppliers(child);

        this.xmlParser.exportToXml(root, SupplierRootExportDto.class, SUPPLIERS_EXPORT_PATH_QUERY_3);

        return "Query 3 finished";
    }

}
