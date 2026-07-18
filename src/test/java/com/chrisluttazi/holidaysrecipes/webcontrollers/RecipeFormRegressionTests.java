package com.chrisluttazi.holidaysrecipes.webcontrollers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeFormRegressionTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Regression test: with an empty database (fresh install), the recipe
     * form used to throw {@code IllegalArgumentException} from
     * {@code Random.nextInt(0)} in randomCampaign()/randomMarket() and
     * respond 500. The controller must render the form even when no
     * campaigns or supplier markets exist yet.
     */
    @Test
    @WithMockUser(username = "cheff@example.com", authorities = "CHEFF")
    void recipeFormRendersOnEmptyDatabase() throws Exception {
        mockMvc.perform(get("/cheff/recipe")).andExpect(status().isOk());
    }

}
