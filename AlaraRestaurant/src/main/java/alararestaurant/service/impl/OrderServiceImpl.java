package alararestaurant.service.impl;

import alararestaurant.domain.dtos.xmls.ItemOrderImportDto;
import alararestaurant.domain.dtos.xmls.ItemOrderRootImportDto;
import alararestaurant.domain.dtos.xmls.OrderImportDto;
import alararestaurant.domain.dtos.xmls.OrderRootImportDto;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.service.OrderService;
import alararestaurant.util.fileutil.FileUtil;
import alararestaurant.util.validation.ValidationUtil;
import alararestaurant.util.xmlparser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static alararestaurant.config.constant.ConstantMsg.INVALID_MSG;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_PATH = "src/main/resources/files/orders.xml";

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderServiceImpl(EmployeeRepository employeeRepository,
                            ModelMapper modelMapper,
                            StringBuilder sb,
                            FileUtil fileUtil,
                            XmlParser xmlParser,
                            ValidationUtil validationUtil,
                            OrderRepository orderRepository,
                            ItemRepository itemRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    private static int compare(Order a, Order b) {
        return b.getCustomer().compareTo(a.getCustomer());
    }


    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDER_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        this.sb.setLength(0);

        OrderRootImportDto rootDto =
                this.xmlParser.parseXml(OrderRootImportDto.class, ORDER_PATH);

        searchValidOrder:
        for (OrderImportDto orderDto : rootDto.getOrders()) {

            Optional<Employee> employeeByName = this.employeeRepository.findByNameLike(orderDto.getEmployeeName());
            if (!this.validationUtil.isValid(orderDto) || employeeByName.isEmpty()) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }

            OrderType orderType = OrderType.valueOfLabel(orderDto.getTypeName().name());
            if (orderType == null) {
                orderType = OrderType.ForHere;
            }
            orderDto.setTypeName(orderType);


            Order orderEntity = this.modelMapper.map(orderDto, Order.class);
            orderEntity.setEmployee(employeeByName.get());

            ItemOrderRootImportDto orderItems = orderDto.getOrderItems();

            Set<OrderItem> orderItemsEntity = new HashSet<>();
            for (ItemOrderImportDto orderItemDto : orderItems.getOrderItems()) {
                Optional<Item> item = this.itemRepository.findFirstByName(orderItemDto.getItemName());
                if (item.isEmpty()) {
                    continue searchValidOrder;
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(orderEntity);
                orderItem.setItem(item.get());
                orderItem.setQuantity(orderItemDto.getQuantity());
                orderItemsEntity.add(orderItem);
            }


            orderEntity.setOrderItems(orderItemsEntity);


            this.orderRepository.saveAndFlush(orderEntity);
            this.sb.append(String.format("Order for %s on %s added "
                    , orderDto.getCustomer(), orderDto.getDateTime().toString())).append(System.lineSeparator());

        }


        return this.sb.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        sb.setLength(0);
        Set<Employee> allByPosition = this.employeeRepository.findAllByPosition();
        allByPosition.forEach(e -> {
            sb.append("Name: ").append(e.getName()).append(System.lineSeparator());
            sb.append("Orders:").append(System.lineSeparator());
            Set<Order> collect = e.getOrder().stream()
                    .sorted(Comparator.comparing(BaseEntity::getId))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            collect.forEach(c -> {
                sb.append("\tCustomer: ")
                        .append(c.getCustomer())
                        .append(System.lineSeparator()).append("Items: ").append(System.lineSeparator());

                c.getOrderItems().forEach(i -> {
                    sb.append("Name: ").append(i.getItem().getName()).append(System.lineSeparator())
                            .append("Price: ").append(i.getItem().getPrice()).append(System.lineSeparator())
                            .append("Quantity: ").append(i.getQuantity()).append(System.lineSeparator());
                });
            });


        });
        System.out.println();
        return this.sb.toString().trim();
    }
}
