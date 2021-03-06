package es.upm.annalsscience.domain.services;

import es.upm.annalsscience.domain.exceptions.CategoryAlreadyExistException;
import es.upm.annalsscience.domain.exceptions.CategoryNotExistException;
import es.upm.annalsscience.domain.model.Category;
import es.upm.annalsscience.domain.model.CreateCategory;
import es.upm.annalsscience.domain.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void shouldCreateCategory() {
        //GIVEN
        String categoryName = "categoryName";
        Long categoryId = 1L;
        CreateCategory createCategory = new CreateCategory(categoryName, categoryId);
        Category expectedCategory = Utils.getCategory();

        when(categoryRepository.findByName(createCategory.getName()))
                .thenReturn(Optional.empty());
        when(categoryRepository.findById(createCategory.getParentId()))
                .thenReturn(Optional.of(expectedCategory));
        when(categoryRepository.save(createCategory))
                .thenReturn(expectedCategory);
        //WHEN
        Category category = categoryService.create(createCategory);
        //THEN
        assertEquals(expectedCategory, category);
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExist() {
        //GIVEN
        String categoryName = "categoryName";
        Long categoryId = 1L;
        CreateCategory createCategory = new CreateCategory(categoryName, categoryId);

        when(categoryRepository.findByName(createCategory.getName()))
                .thenReturn(Optional.of(Utils.getCategory()));
        //WHEN
        assertThrows(CategoryAlreadyExistException.class, () -> {
            categoryService.create(createCategory);
        });
    }

    @Test
    void shouldThrowExceptionWhenParentNotExist() {
        //GIVEN
        String categoryName = "categoryName";
        Long categoryId = 1L;
        CreateCategory createCategory = new CreateCategory(categoryName, categoryId);

        when(categoryRepository.findByName(createCategory.getName()))
                .thenReturn(Optional.empty());
        when(categoryRepository.findById(createCategory.getParentId()))
                .thenReturn(Optional.empty());
        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            categoryService.create(createCategory);
        });
    }

    @Test
    void shouldFindAll() {
        //GIVEN
        List<Category> expectedCategories = Arrays.asList(Utils.getCategory());
        when(categoryRepository.findAll())
                .thenReturn(expectedCategories);
        //WHEN
        List<Category> categoreis = categoryService.findAll();
        //THEN
        assertEquals(expectedCategories, categoreis);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotExistInDelete() {
        //GIVEN
        Long categoryId = 1L;

        //WHEN
        assertThrows(CategoryNotExistException.class, () -> {
            categoryService.delete(categoryId);
        });
    }

    @Test
    void shouldDelete() {
        //GIVEN
        Long categoryId = 1L;
        Category category = Utils.getCategory();
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));

        //WHEN
        categoryService.delete(categoryId);

        //THEN
        verify(categoryRepository, times(1)).delete(category);
    }

}