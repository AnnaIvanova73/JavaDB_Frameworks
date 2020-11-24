package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entities.Car;
import softuni.exam.models.importDto.jsons.CarDtoImport;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {

    private final static String CARS_PATH = "src/main/resources/files/json/cars.json";

    private final StringBuilder sb;
    private final Gson gson;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(StringBuilder sb, Gson gson,
                          CarRepository carRepository,
                          ModelMapper modelMapper,
                          ValidationUtil validationUtil) {
        this.sb = sb;
        this.gson = gson;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CARS_PATH)));
    }

    @Override
    public String importCars() throws IOException {
        sb.setLength(0);
        CarDtoImport[] carDtoImports = this.gson.fromJson(readCarsFileContent(), CarDtoImport[].class);

        for (CarDtoImport carDtoImport : carDtoImports) {

            if (!this.validationUtil.isValid(carDtoImport)) {
                sb.append("Invalid car").append(System.lineSeparator());
                continue;
            }

            Car car = this.modelMapper.map(carDtoImport, Car.class);
            this.carRepository.saveAndFlush(car);

            sb.append(String.format("Successfully imported car - %s - %s", carDtoImport.getMake(), carDtoImport.getModel()))
                    .append(System.lineSeparator());
        }
        return this.sb.toString().trim();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        sb.setLength(0);
        Set<Car> andOrder = this.carRepository.findAndOrder();
        for (Car car : andOrder) {
            sb.append(String.format("Car make - %s, model - %s", car.getMake(), car.getModel())).append(System.lineSeparator())
                    .append(String.format("\tKilometers - %s", car.getKilometers())).append(System.lineSeparator())
                    .append(String.format("\tRegistered on - %s", car.getRegisteredOn())).append(System.lineSeparator())
                    .append(String.format("\tNumber of pictures - %s", car.getPictures().size())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
