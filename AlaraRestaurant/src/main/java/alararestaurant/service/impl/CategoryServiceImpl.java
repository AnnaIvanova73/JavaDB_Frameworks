package alararestaurant.service.impl;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final StringBuilder sb;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ItemRepository itemRepository, StringBuilder sb) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.sb = sb;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        this.sb.setLength(0);
        Set<Category> allByItemsCount = this.categoryRepository.findAllByItemsCount();
        for (Category category : allByItemsCount) {
            sb.append("Category: ").append(category.getName()).append(System.lineSeparator());
            for (Item item : this.itemRepository.findAllByCategoryId(category.getId())) {
                sb.append("--- Item Name: ").append(item.getName()).append(System.lineSeparator());
                sb.append("--- Item Price: ").append(item.getPrice()).append(System.lineSeparator());

            }
        }
        return this.sb.toString().trim();
    }
}
