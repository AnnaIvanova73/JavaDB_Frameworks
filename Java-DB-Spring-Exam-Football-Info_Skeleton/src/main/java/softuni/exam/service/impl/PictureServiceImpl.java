package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.xmls.PictureImportDto;
import softuni.exam.domain.dtos.xmls.PictureRootImportDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.fileutil.FileUtil;
import softuni.exam.util.validator.ValidatorUtil;
import softuni.exam.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final static String PICTURES_PATH = "src/main/resources/files/xml/pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final StringBuilder sb;

    public PictureServiceImpl(PictureRepository pictureRepository,
                              FileUtil fileUtil,
                              XmlParser xmlParser,
                              ModelMapper modelMapper,
                              ValidatorUtil validatorUtil,
                              StringBuilder sb) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
    }


    @Override
    public String importPictures() throws JAXBException {
        this.sb.setLength(0);
        PictureRootImportDto picturesRoot = this.xmlParser.parseXml(PictureRootImportDto.class,PICTURES_PATH);

        for (PictureImportDto dto : picturesRoot.getPictures()) {

            if (!this.validatorUtil.isValid(dto)) {
                this.validatorUtil.violations(dto)
                        .forEach(err -> sb.append(err.getMessage()).append(System.lineSeparator()));
                    continue;

            }
            Picture map = this.modelMapper.map(dto, Picture.class);
            this.pictureRepository.saveAndFlush(map);
            sb.append(String.format("Successfully imported picture - %s", dto.getUrl())).append(System.lineSeparator());
        }


        return this.sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return this.fileUtil.readFile(PICTURES_PATH);
    }

}
