package com.example.lab.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        //modelMapper().addConverter();
        //modelMapper().addMappings()
        //modelMapper().createTypeMap()
        //modelMapper().map();
        //modelMapper().validate(); след мапването хвърля exception, ако мапването е не валидно

        //ModelMapper modelMapper = new ModelMapper();

        //---> 5

        /* new AbstractConverter<>() ---> директно получава source
        Чрез converter, вече имаме възможност да използваме методи, които променят стойността на нашето velue
                                                               */
    /*  Converter<String, String> stringConverter = new Converter<String, String>() {
            @Override
            public String convert(MappingContext<String, String> mappingContext) {// -> получава mapping context,
//                 което може да достъпи
//                 и source и destination, дава повече възможности
                return mappingContext.getSource() == null ? null : mappingContext.getSource().toLowerCase();
            }
        };*/

   /*  --> 5.1 използване към propertyMap
        Има два начина да се сложи към modelMapper
        5.1.1 има метод addConverter (stringConverter), задава го и директно го сетва, използваме, когато имаме,
         един converter към нашия modelMapper; */

        //5.1.2
       /*PropertyMap<Employee, EmployeeViewDto> propertyMap = //Employee -> source, EmployeeViewDto -> Destination
                new PropertyMap<Employee, EmployeeViewDto>() {
                    @Override
                    protected void configure() {
                        map().setAddress(source.getCity().getName());
                       using(stringConverter).map(source.getFirstName()).setFirstName(null);
                       /* модел мапера, не работи стандартно за java,  в .map му подаваме източника
                       и да го сетнем, като сета тук защо му даваме нул, за да си сетне първоначално нулева стойност.
                       Отзад работи Generic и може да даде грешка, ако се опитаме да работим директно със спринг, тъй като в
                       модел мапъра е още някакъв generic, който ще си останови типа/стойността по време на runtime,
                       за това му подава ме Null за да може да се инициализира и при инициализация си взима стойността от
                       .map И се сетва

                    }
                };
        modelMapper.addMappings(propertyMap); */

        //2.
        /*
        В ТYPEMAP, не може да се използва CONVERTER
        TypeMap<Employee,EmployeeViewDto> typeMap = modelMapper.createTypeMap(Employee.class,EmployeeViewDto.class);
        typeMap.addMappings(m -> m.map(src -> src.getCity().getName(),EmployeeViewDto::setAddress)); */
      /*   на първо място подавме source, на второ дестинацията
         тук може да се намапат повече класове */

        //1.
         /*
        PropertyMap<Employee, EmployeeViewDto> propertyMap = //Employee -> source, EmployeeViewDto -> Destination
                new PropertyMap<Employee, EmployeeViewDto>() {
                    @Override
                    protected void configure() {
                      map().setAddress(source.getCity().getName()); //map().setAddress -> отговаря за дестинацията,
                        //source.getCity().getName() -> намапва със source. Не могат да се използва други методи
                        // очаква се само гет, сет ако искаме промяна използваме converter
                    }
                };
        modelMapper.addMappings(propertyMap); //сетваме мапване, за да е валидно
        // получава propertyMap  */

        //3.
        // Ако трябва да се пишат сложни неща да се използва сетъра направо!!!.


        //4.
        /*
                PropertyMap<Employee, EmployeeViewDto> propertyMap = //Employee -> source, EmployeeViewDto -> Destination
                new PropertyMap<Employee, EmployeeViewDto>() {
                    @Override
                    protected void configure() {
                     skip().setAddress(null); //ако искаме някое пропърти да се скипне,
                        // но ако трябва нещо да се скипне то не трябва да присъства в модела, да се направи нов модел
                    }
                }; */

        // към точка 5.1.1 modelMapper.addConverter(stringConverter); -> ще го изпълни за всеки един стринг, ако не използваме using
        //return modelMapper;

        return new ModelMapper();
    }


}
