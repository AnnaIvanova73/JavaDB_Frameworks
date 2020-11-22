package softuni.jsonprocessing.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.query2.CarChildExportDto;
import softuni.jsonprocessing.domain.dto.export.query2.CarRootExportDto;
import softuni.jsonprocessing.domain.dto.export.query4.CarExportDto;
import softuni.jsonprocessing.domain.dto.export.query4.CarExportRootDto;
import softuni.jsonprocessing.domain.dto.importDto.cars.CarChildImportDto;
import softuni.jsonprocessing.domain.dto.importDto.cars.CarRootImportDto;
import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.repo.CarRepository;
import softuni.jsonprocessing.service.CarService;
import softuni.jsonprocessing.service.RandomValuesService;
import softuni.jsonprocessing.util.xmlparser.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_PATH = "src/main/resources/xml-import/cars.xml";
    private static final String CARS_EXPORT_PATH_QUERY_2 = "src/main/resources/xml-export/query2.xml";
    private static final String CARS_EXPORT_PATH_QUERY_4 = "src/main/resources/xml-export/query4.xml";


    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final XmlParser xmlParser;
    private final RandomValuesService randomValue;

    @Autowired
    public CarServiceImpl(ModelMapper modelMapper,
                          CarRepository carRepository,
                          XmlParser xmlParser,
                          RandomValuesService randomValue) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.xmlParser = xmlParser;
        this.randomValue = randomValue;
    }


    @Override
    @Transactional
    public void seedCars() throws Exception {
        CarRootImportDto rootDto =
                this.xmlParser.parseXml(CarRootImportDto.class, CARS_PATH);
        List<CarChildImportDto> cars = rootDto.getCars();

        for (CarChildImportDto carChild : cars) {
            Car car = this.modelMapper.map(carChild, Car.class);
            car.setParts(this.randomValue.getRandomParts());
            this.carRepository.saveAndFlush(car);
        }
    }

    @Override
    public String findAllCarsByMakerToyota() throws JAXBException {
        List<CarChildExportDto> child = this.carRepository.findByMakerToyota()
                .stream()
                .map(c -> this.modelMapper.map(c, CarChildExportDto.class))
                .collect(Collectors.toList());

        CarRootExportDto root = new CarRootExportDto();
        root.setCars(child);

        this.xmlParser.exportToXml(root, CarRootExportDto.class, CARS_EXPORT_PATH_QUERY_2);

        return "Query 2 executed";
    }

    @Override
    public String getCarWithParts() throws JAXBException {

        List<Car> all = this.carRepository.findAll();

        CarExportRootDto carRootDto = new CarExportRootDto();
        List<CarExportDto> carExportDtos = new ArrayList<>();


        for (Car car : all) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
            carExportDtos.add(carExportDto);
        }

        carRootDto.setCars(carExportDtos);

        this.xmlParser.exportToXml(carRootDto,CarExportRootDto.class,CARS_EXPORT_PATH_QUERY_4);

        return "Query 4 done";
    }
}




/* good example
    List<Car> all = this.carRepository.findAll();

    CarExportRootDto carRootDto = new CarExportRootDto();
    List<CarExportDto> carExportDtos = new ArrayList<>();


        for (Car car : all) {
                CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);

        PartExportRootDto partRootDto = new PartExportRootDto();
        List<PartExportDto> partExportDtos = new ArrayList<>();

        for (Part part : car.getParts()) {
        PartExportDto partDto = this.modelMapper.map(part, PartExportDto.class);
        partExportDtos.add(partDto);
        }

        partRootDto.setParts(partExportDtos);
        carExportDto.setParts(partRootDto);
        carExportDtos.add(carExportDto);
        }

        carRootDto.setCars(carExportDtos); */

/* initial decision
        List<Car> all = this.carRepository.findAll();

        List<CarChildWrapperPartsExportDto> child = new ArrayList<>();
        for (Car car : all) {
            CarChildWrapperPartsExportDto dto = this.modelMapper.map(car, CarChildWrapperPartsExportDto.class);
            child.add(dto);
        }

        CarRootWrapperPartsExportDto root = new CarRootWrapperPartsExportDto();
        root.setCars(child);
        this.xmlParser.exportToXml(root,CarRootWrapperPartsExportDto.class,CARS_EXPORT_PATH_QUERY_4);
 */