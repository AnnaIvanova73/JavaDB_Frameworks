package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.CarExportDto;
import softuni.jsonprocessing.domain.dto.export.query4.CarExportDtoQuery4;
import softuni.jsonprocessing.domain.dto.importDto.CarDtoImport;
import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.repo.CarRepository;
import softuni.jsonprocessing.domain.repo.PartRepository;
import softuni.jsonprocessing.service.CarService;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_PATH = "src/main/resources/json/cars.json";
    private static final String CARS_EXPORT_PATH_QUERY_2 = "src/main/resources/json-export/query2.json";
    private static final String CARS_EXPORT_PATH_QUERY_4 = "src/main/resources/json-export/query4.1.json";


    private final Gson gson;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final PartRepository partRepository;

    @Autowired
    public CarServiceImpl(Gson gson,
                          ModelMapper modelMapper,
                          CarRepository carRepository,
                          PartRepository partRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
    }


    @Override
    @Transactional
    public void seedCars() throws Exception {
        String source = String.join("", Files.readAllLines(Path.of(CARS_PATH)));

        CarDtoImport[] carDtoImports = this.gson.fromJson(source, CarDtoImport[].class);

        for (CarDtoImport currentDto : carDtoImports) {
            Car car = this.modelMapper.map(currentDto, Car.class);
            car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);
        }


    }

    @Override
    public String findCarsByMakerToyota() throws IOException {
        String make = "Toyota";
        Set<Car> cars = this.carRepository.findByMakeIsLike(make);

        List<CarExportDto> exportCar = new ArrayList<>();
        for (Car car : cars) {
            CarExportDto ced = this.modelMapper.map(car, CarExportDto.class);
            exportCar.add(ced);
        }

        System.out.println(this.gson.toJson(exportCar));

        Writer writer = new FileWriter(CARS_EXPORT_PATH_QUERY_2);

        this.gson.toJson(exportCar, writer);

        writer.flush();
        writer.close();
        return "Done --> resources/json-export/query2.json";
    }

    @Override
    public String getAllCarsWithParams() throws IOException {
        List<Car> all = this.carRepository.findAll();

        List<CarExportDtoQuery4> dtoCars = new ArrayList<>();

        for (Car carEntity : all) {
            CarExportDtoQuery4 car = this.modelMapper.map(carEntity, CarExportDtoQuery4.class);
//            List<PartExportDtoQuery4> dtoParts = new ArrayList<>();
//
//
//            for (Part partEntity : carEntity.getParts()) {
//                PartExportDtoQuery4 part = this.modelMapper.map(partEntity, PartExportDtoQuery4.class);
//                dtoParts.add(part);
//
//            }
//
//            car.setParts(dtoParts); No need manual mapping
            dtoCars.add(car);

        }

        System.out.println(this.gson.toJson(dtoCars));


        Writer writer = new FileWriter(CARS_EXPORT_PATH_QUERY_4);
        this.gson.toJson(dtoCars, writer);
        writer.flush();
        writer.close();

        return "Done --> resources/json-export/query4.1.json";

    }

    private List<Part> getRandomParts() throws Exception {
        Random random = new Random();
        int lowerBound = 10;
        int upperBound = 20;

        List<Part> parts = new ArrayList<>();

        int bound = random.nextInt(upperBound - lowerBound) + lowerBound;
        for (int i = 0; i < bound; i++) {
            Part part = getRandomPart();


            parts.add(part);
        }

        return parts;
    }


    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long randomId = (long) random.nextInt((int) this.partRepository.count() - 1) + 1;

        Optional<Part> part = this.partRepository.findById(randomId);

        if (part.isEmpty()) {
            throw new Exception("Invalid part id");
        }

        return part.get();
    }

}
