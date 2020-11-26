package alararestaurant.service;

import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public Boolean itemsAreImported() {
        // TODO : Implement me
        return false;
//        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() {
        // TODO : Implement me
        return null;
    }

    @Override
    public String importItems(String items) {
        // TODO : Implement me
        return null;
    }
}
