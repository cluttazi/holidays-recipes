package com.chrisluttazi.holidaysrecipes;

import com.chrisluttazi.holidaysrecipes.model.User;
import com.chrisluttazi.holidaysrecipes.model.persistance.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SmokeWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void homePageIsPublic() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void loginPageIsPublic() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    void registerPageIsPublic() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk());
    }

    @Test
    void protectedPageRedirectsAnonymousToLogin() throws Exception {
        mockMvc.perform(get("/cheff/recipe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void anonymousRegistrationIsDeniedByServiceLayerSecurity() throws Exception {
        // Pre-existing behavior (also true on Spring Boot 1.3 / Security 4):
        // UserService.create is @PreAuthorize("hasAuthority('ADMIN')"), so an
        // anonymous POST /register is bounced to the login page and nothing
        // is persisted. Pinned here to preserve behavior across the upgrade.
        mockMvc.perform(post("/register").with(csrf())
                        .param("username", "anon@example.com")
                        .param("password", "s3cretpass")
                        .param("enabled", "true")
                        .param("name", "Anon"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

        assertNull(userService.findByUsername("anon@example.com"),
                "anonymous registration must not persist a user");
    }

    @Test
    @WithMockUser(username = "admin@admin", authorities = "ADMIN")
    void adminRegistrationCreatesUserWithEncodedPassword() throws Exception {
        mockMvc.perform(post("/register").with(csrf())
                        .param("username", "chef@example.com")
                        .param("password", "s3cretpass")
                        .param("enabled", "true")
                        .param("name", "Chef"))
                .andExpect(status().is3xxRedirection());

        User created = userService.findByUsername("chef@example.com");
        assertNotNull(created, "registered user should be persisted");
        assertNotEquals("s3cretpass", created.getPassword(),
                "password must be stored BCrypt-encoded, never in clear text");
    }

}
