package softuni.springintro.services.impl;

import org.springframework.stereotype.Service;
import softuni.springintro.domain.entities.Author;
import softuni.springintro.domain.entities.Book;
import softuni.springintro.domain.entities.Category;
import softuni.springintro.domain.enums.AgeRestriction;
import softuni.springintro.domain.enums.EditionType;
import softuni.springintro.domain.repositories.AuthorRepository;
import softuni.springintro.domain.repositories.BookRepository;
import softuni.springintro.domain.repositories.CategoryRepository;
import softuni.springintro.services.BookService;
import softuni.springintro.util.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.springintro.constants.Paths.BOOK_FILE_PATH;


@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           FileUtil fileUtil,
                           CategoryRepository categoryRepository) {

        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        if(this.bookRepository.count() != 0){
            return;
        }

        String[] books = this.fileUtil.fileContent(BOOK_FILE_PATH);


        for (String info : books) {
            String[] params = info.split("\\s+");

            Book book = new Book();
            book.setAuthor(randomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);

            LocalDate releaseData =
                    LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseData);

            book.setCopies(Integer.parseInt(params[2]));

            BigDecimal price = new BigDecimal(params[3]);
            book.setPrice(price);


            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            book.setAgeRestriction(ageRestriction);


            StringBuilder builder = new StringBuilder();

            for (int i = 5; i <= params.length -1 ; i++) {
                builder.append(params[i])
                .append(" ");

            }

            book.setTitle(builder.toString().trim());


            book.setCategories(randomCategories());


            this.bookRepository.saveAndFlush(book);
        }



    }

    @Override
    public List<String> findAllTitles() {
        LocalDate releaseDate = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));
        return this.bookRepository
                .findAllByReleaseDateAfter(releaseDate)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthors() {
        LocalDate releaseDate = LocalDate.parse("1/1/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));
       return this.bookRepository.findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(b -> String.format("%s %s",b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getBooksByAuthor() {
        StringBuilder builder = new StringBuilder();

        Author author = this.authorRepository.getByFirstNameAndLastName("George","Powell");
        this.bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author)
                .forEach(a -> builder
                        .append(String.format("Title: %s ReleaseData: %s Copies: %d %n",
                                a.getTitle(),a.getReleaseDate(),a.getCopies())));

        return builder.toString().trim();
    }


    private Author randomAuthor (){

        Random random = new Random();

        int id = random.nextInt((int) this.authorRepository.count()) + 1;


        return this.authorRepository.getOne((long) id);
    }

    private Category randomCategory (){

        Random random = new Random();

        int index = random.nextInt((int) this.categoryRepository.count()) + 1;


        return this.categoryRepository.getOne((long) index);
    }


    private Set<Category> randomCategories (){
        Set<Category> categories = new HashSet<>();

        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count()) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }
        return categories;
    }
}
