package softuni.jsonprocessing.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.importDto.parts.PartChildImportDto;
import softuni.jsonprocessing.domain.dto.importDto.parts.PartRootImportDto;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.entities.Supplier;
import softuni.jsonprocessing.domain.repo.PartRepository;
import softuni.jsonprocessing.domain.repo.SupplierRepository;
import softuni.jsonprocessing.service.PartService;
import softuni.jsonprocessing.service.RandomValuesService;
import softuni.jsonprocessing.util.xmlparser.XmlParser;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private final static String PARTS_PATH = "src/main/resources/xml-import/parts.xml";

    private final RandomValuesService randomValuesService;
    private final ModelMapper mapper;
    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;

    @Autowired
    public PartServiceImpl(RandomValuesService randomValuesService,
                           XmlParser xmlParser,
                           ModelMapper mapper,
                           PartRepository partRepository,
                           SupplierRepository supplierRepository) {
        this.randomValuesService = randomValuesService;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
    }


    @Override
    public void seedParts() throws Exception {
        PartRootImportDto rootDto =
                this.xmlParser.parseXml(PartRootImportDto.class, PARTS_PATH);

        List<PartChildImportDto> childDto = rootDto.getParts();

        for (PartChildImportDto partDto : childDto) {
            Part part = mapper.map(partDto, Part.class);
            Supplier randomSupplier = this.randomValuesService.getRandomSupplier();
            part.setSupplier(randomSupplier);
            this.partRepository.saveAndFlush(part);
        }

    }

}
