package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entities.Picture;
import softuni.exam.models.importDto.jsons.PictureDtoImport;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURES_PATH = "src/main/resources/files/json/pictures.json";

    private final StringBuilder sb;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final CarRepository carRepository;

    public PictureServiceImpl(StringBuilder sb,
                              PictureRepository pictureRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil, Gson gson,
                              CarRepository carRepository) {
        this.sb = sb;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.carRepository = carRepository;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PICTURES_PATH)));
    }

    @Override
    public String importPictures() throws IOException {
        this.sb.setLength(0);
        PictureDtoImport[] pictureDtoImports = this.gson.fromJson(readPicturesFromFile(), PictureDtoImport[].class);

        for (PictureDtoImport dto : pictureDtoImports) {

            if (!this.validationUtil.isValid(dto)) {
                this.sb.append("Invalid picture").append(System.lineSeparator());
                continue;
            }

            int carId = dto.getCar();
            Picture picture = this.modelMapper.map(dto, Picture.class);

            picture.setCar(this.carRepository.getOne(carId));
            this.pictureRepository.saveAndFlush(picture);

            sb.append(String.format("Successfully import picture - %s", dto.getName()))
                    .append(System.lineSeparator());

        }

        return this.sb.toString().trim();
    }
}
