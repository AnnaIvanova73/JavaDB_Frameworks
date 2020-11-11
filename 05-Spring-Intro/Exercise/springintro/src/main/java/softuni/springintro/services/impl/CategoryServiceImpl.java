package softuni.springintro.services.impl;

import org.springframework.stereotype.Service;
import softuni.springintro.domain.entities.Category;
import softuni.springintro.domain.repositories.CategoryRepository;
import softuni.springintro.services.CategoryService;
import softuni.springintro.util.FileUtil;

import java.io.IOException;

import static softuni.springintro.constants.Paths.CATEGORY_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {

        if (this.categoryRepository.count() != 0) {
            return;
        }

        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);

        for (String info : categories) {
            Category category = new Category();
            category.setName(info);
            this.categoryRepository.saveAndFlush(category);
        }
    }
}
