package br.com.gubee.interview.services;

import br.com.gubee.interview.Auxilar;
import br.com.gubee.interview.entities.HeroEntity;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.repositories.HeroRepository;
import br.com.gubee.interview.utils.MapperUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HeroServiceTest extends Auxilar {
    @InjectMocks
    private HeroService service;

    @Mock
    private HeroRepository repository;

    @Spy
    private MapperUtil mapperUtil;

    @Test
    public void mustReturnSuccess_WhenList() throws Exception {
        final int QTY = 2;
        List<HeroEntity> heroEntityList = mapperUtil.mapList(createHero(QTY), HeroEntity.class);

        when(repository.findAll()).thenReturn(heroEntityList);

        List<Hero> results = service.list();

        assertFalse(results.isEmpty());
        assertEquals(QTY, results.size());
    }

    @Test
    public void mustReturnSuccess_WhenFindById() {
        HeroEntity heroEntity = mapperUtil.convertTo(createHero(), HeroEntity.class);
        UUID id = heroEntity.getId();

        when(repository.findById(any())).thenReturn(Optional.of(heroEntity));
        when(repository.existsById(any())).thenReturn(true);

        Hero result = service.findById(id);

        assertNotNull(result);
        assertEquals(heroEntity.getId(), result.getId());
    }

    @Test
    public void mustReturnNull_WhenFindById() {
        UUID id = createHero().getId();
        when(repository.existsById(any())).thenReturn(false);

        Hero result = service.findById(id);

        assertNull(result);
    }

    @Test
    public void mustReturnSuccess_WhenFindByName() {
        HeroEntity heroEntity = mapperUtil.convertTo(createHero(), HeroEntity.class);
        String heroName = heroEntity.getName();

        when(repository.findHeroEntitiesByNameContainingIgnoreCase(any())).thenReturn(Arrays.asList(heroEntity));

        List<Hero> result = service.findByName(heroName);

        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(item -> item.getName() == heroName));
    }

    @Test
    public void mustReturnSuccess_WhenSave() {
        HeroEntity heroEntity = mapperUtil.convertTo(createHero(), HeroEntity.class);
        Hero heroModel = createHero();

        when(repository.save(any())).thenReturn(heroEntity);

        Hero result = service.save(heroModel);

        assertNotNull(result);
        assertEquals(heroEntity.getId(), result.getId());
    }

    @Test
    public void mustReturnSuccess_WhenSaveAll() throws Exception {
        final int QTD = 4;
        List<Hero> heroModelList = createHero(QTD);
        List<HeroEntity> heroEntityList = mapperUtil.mapList(heroModelList, HeroEntity.class);

        when(repository.saveAll(anyList())).thenReturn(heroEntityList);

        List<Hero> results = service.saveAll(heroModelList);

        assertFalse(results.isEmpty());
        assertEquals(QTD, results.size());
    }

    @Test
    public void mustReturnTrue_WhenDelete() {
        UUID id = createHero().getId();

        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        boolean isDeleted = service.delete(id);

        assertTrue(isDeleted);
    }

    @Test
    public void mustReturnFalseWhenDelete() {
        UUID id = createHero().getId();

        when(repository.existsById(any())).thenReturn(false);
        doNothing().when(repository).deleteById(id);

        boolean isDeleted = service.delete(id);

        assertFalse(isDeleted);
    }


}
