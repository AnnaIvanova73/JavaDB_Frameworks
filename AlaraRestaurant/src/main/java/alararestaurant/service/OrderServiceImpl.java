package alararestaurant.service;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Boolean ordersAreImported() {
        // TODO : Implement me
        return false;
//        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() {
        // TODO : Implement me
        return null;
    }

    @Override
    public String importOrders() {
        // TODO : Implement me
        return null;
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        // TODO : Implement me
        return null;
    }
}
