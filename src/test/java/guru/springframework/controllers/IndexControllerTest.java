package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Created by robertf on 2018/09/05
 */
public class IndexControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @Test
    public void getIndexPage() {
        //Given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        Mockito.when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //When
        String viewName = controller.getIndexPage(model);

        //Then
        assertEquals("index",viewName);
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model,Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2,setInController.size());

    }
}
