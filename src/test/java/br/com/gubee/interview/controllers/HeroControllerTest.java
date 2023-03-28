package br.com.gubee.interview.controllers;


import br.com.gubee.interview.Auxilar;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.HerosVersus;
import br.com.gubee.interview.services.HeroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest extends Auxilar {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService service;

    @Test
    public void mustReturnSuccess_WhenListHeroes() throws Exception {
        List<Hero> heroList = createHero(4);
        when(service.list()).thenReturn(heroList);

        MockHttpServletRequestBuilder getMethod = get("/heroes")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)));
    }

    @Test
    public void mustReturnSuccess_WhenFindHeroById() throws Exception {
        Hero hero = createHero();
        when(service.findById(any())).thenReturn(hero);

        final String uri = String.format("/hero/%s", hero.getId());
        MockHttpServletRequestBuilder getMethod = get(uri);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(hero.getId().toString()));
    }

    @Test
    void mustReturnNotFound_WhenFindHeroById() throws Exception {
        when(service.findById(any())).thenReturn(null);

        final String uri = String.format("/hero/%s", UUID.randomUUID());
        MockHttpServletRequestBuilder getMethod = get(uri);

        mockMvc.perform(getMethod)
                .andExpect(status().isNotFound());
    }

    @Test
    void mustReturnSuccess_WhenFindHeroByName() throws Exception {
        List<Hero> heroList = createHero(2);
        when(service.findByName(anyString())).thenReturn(heroList);

        final String uri = "/hero";
        MockHttpServletRequestBuilder getMethod = get(uri)
                .param("name", "man");

        mockMvc.perform(getMethod)
                .andExpect(status().isOk());
    }

    @Test
    void mustReturnSuccess_WhenSave() throws Exception {
        Hero heroSaved = createHero();

        when(service.save(any())).thenReturn(heroSaved);

        final String uri = String.format("/hero");

        MockHttpServletRequestBuilder postMethod = post(uri)
                .content(heroSaved.toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc
                .perform(postMethod)
                .andExpect(status().isCreated());
    }

    @Test
    void mustReturnInternalError_WhenSave() throws Exception {

        when(service.save(any())).thenReturn(null);

        final String uri = String.format("/hero");

        MockHttpServletRequestBuilder postMethod = post(uri)
                .content(createHero().toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc
                .perform(postMethod)
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void mustReturnSuccess_WhenUpdate() throws Exception {
        when(service.save(any())).thenReturn(createHero());
        final String uri = String.format("/hero");

        MockHttpServletRequestBuilder patchMethod = patch(uri)
                .content(createHero().toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc
                .perform(patchMethod)
                .andExpect(status().isCreated());
    }

    @Test
    void mustReturnNotFound_WhenUpdate() throws Exception {

        when(service.save(any())).thenReturn(null);

        final String uri = String.format("/hero");

        MockHttpServletRequestBuilder patchMethod = patch(uri)
                .content(createHero().toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc
                .perform(patchMethod)
                .andExpect(status().isNotFound());
    }

    @Test
    public void mustReturnSuccess_WhenDelete() throws Exception {
        when(service.delete(any())).thenReturn(true);

        final String uri = String.format("/hero/%s", UUID.randomUUID());

        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(createHero().toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc
                .perform(deleteMethod)
                .andExpect(status().isOk());

    }

    @Test
    public void mustReturnNotFound_WhenDelete() throws Exception {
        when(service.delete(any())).thenReturn(false);

        final String uri = String.format("/hero/%s", UUID.randomUUID());
        MockHttpServletRequestBuilder deleteMethod = delete(uri)
                .content(createHero().toJSON())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc
                .perform(deleteMethod)
                .andExpect(status().isNotFound());

    }

    @Test
    public void mustReturnSuccess_WhenVersus() throws Exception {
        HerosVersus herosVersus = createVerus();

        herosVersus.toJSON();

        when(service.findById(any())).thenReturn(herosVersus.getChallenger());

        final String uri = String.format(
                "/hero/%s/vs/%s",
                herosVersus.getChallenger().getId(),
                herosVersus.getDefender().getId()
        );

        MockHttpServletRequestBuilder getMethod = get(uri);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk());
    }
}
