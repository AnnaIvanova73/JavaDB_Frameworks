package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.xmls.planeImports.PlaneImportDto;
import softuni.exam.models.dtos.xmls.planeImports.PlaneRootImportDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final static String PLANES_PATH = "src/main/resources/files/xml/planes.xml";

    private final XmlParser xmlParser;
    private final StringBuilder sb;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PlaneRepository planeRepository;

    public PlaneServiceImpl(XmlParser xmlParser,
                            StringBuilder sb,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil,
                            PlaneRepository planeRepository) {
        this.xmlParser = xmlParser;
        this.sb = sb;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.planeRepository = planeRepository;
    }


    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PLANES_PATH)));
    }

    @Override
    public String importPlanes() throws JAXBException {
        this.sb.setLength(0);
        PlaneRootImportDto rootDto = this.xmlParser.parseXml(PlaneRootImportDto.class,PLANES_PATH);

        for (PlaneImportDto dtoPlane : rootDto.getPlanes()) {

            Optional<Plane> planeCheckExistence =
                    this.planeRepository.findByRegisterNumber(dtoPlane.getRegisterNumber());

            if(!this.validationUtil.isValid(dtoPlane) || planeCheckExistence.isPresent()){
                this.sb.append("Invalid plane").append(System.lineSeparator());
                continue;
            }
            Plane plane = this.modelMapper.map(dtoPlane, Plane.class);
            this.planeRepository.saveAndFlush(plane);

            this.sb.append(String.format("Successfully imported Plane %s",dtoPlane.getRegisterNumber()))
                    .append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }
}
