package alararestaurant.service.impl;

import alararestaurant.domain.dtos.jsons.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.service.ItemService;
import alararestaurant.util.fileutil.FileUtil;
import alararestaurant.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static alararestaurant.config.constant.ConstantMsg.INVALID_MSG;

@Service
public class ItemServiceImpl implements ItemService {
    private static final String ITEM_PATH = "src/main/resources/files/items.json";

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemServiceImpl(EmployeeRepository employeeRepository,
                           ModelMapper modelMapper,
                           StringBuilder sb,
                           FileUtil fileUtil,
                           Gson gson,
                           ValidationUtil validationUtil,
                           ItemRepository itemRepository,
                           CategoryRepository categoryRepository1) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository1;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEM_PATH);
    }

    @Override
    public String importItems(String items) throws IOException {
        this.sb.setLength(0);

        ItemImportDto[] itemImportDtos = this.gson.fromJson(readItemsJsonFile(), ItemImportDto[].class);

        for (ItemImportDto itemDto : itemImportDtos) {
            if (!this.validationUtil.isValid(itemDto) ||
                    itemDto.getCategory().equalsIgnoreCase("invalid")) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }
            Item itemEntity = this.modelMapper.map(itemDto, Item.class);

            Optional<Category> optCategory =
                    this.categoryRepository.findFirstByNameLike(itemDto.getName());

            Category category;
            if (optCategory.isEmpty()) {
                category = new Category();
                category.setName(itemDto.getName());
                this.categoryRepository.saveAndFlush(category);
            } else {
                category = optCategory.get();
            }

            itemEntity.setCategory(category);
            this.itemRepository.saveAndFlush(itemEntity);

            this.sb.append(String.format("Record %s successfully imported. "
                    ,itemEntity.getName())).append(System.lineSeparator());
        }


        return this.sb.toString().trim();
    }
}
