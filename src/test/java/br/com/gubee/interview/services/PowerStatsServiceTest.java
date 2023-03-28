package br.com.gubee.interview.services;

import br.com.gubee.interview.Auxilar;
import br.com.gubee.interview.entities.PowerStatsEntity;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.repositories.PowerStatsRepostory;
import br.com.gubee.interview.utils.MapperUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PowerStatsServiceTest extends Auxilar {
    @InjectMocks
    private PowerStatsService service;

    @Mock
    private PowerStatsRepostory repository;

    @Spy
    private MapperUtil mapperUtil;

    @Test
    public void mustReturnSuccess_WhenList() {
        final int QTY = 2;
        List<PowerStatsEntity> powerStatsEntityList = mapperUtil.mapList(createPowerStats(QTY), PowerStatsEntity.class);

        when(repository.findAll()).thenReturn(powerStatsEntityList);

        List<PowerStats> results = service.list();

        assertFalse(results.isEmpty());
        assertEquals(QTY, results.size());
    }

    @Test
    public void mustReturnSuccess_WhenFindById() {
        PowerStatsEntity powerStats = mapperUtil.convertTo(createPowerStats(), PowerStatsEntity.class);
        UUID id = powerStats.getId();

        when(repository.findById(any())).thenReturn(Optional.of(powerStats));
        when(repository.existsById(any())).thenReturn(true);

        PowerStats result = service.findById(id);

        assertNotNull(result);
        assertEquals(powerStats.getId(), result.getId());
    }

    @Test
    public void mustReturnNull_WhenFindById() {
        UUID id = createPowerStats().getId();
        when(repository.existsById(any())).thenReturn(false);

        PowerStats result = service.findById(id);

        assertNull(result);
    }

    @Test
    public void mustReturnSuccess_WhenSave() {
        PowerStatsEntity powerStats = mapperUtil.convertTo(createPowerStats(), PowerStatsEntity.class);
        PowerStats powerStatsModel = createPowerStats();

        when(repository.save(any())).thenReturn(powerStats);

        PowerStats result = service.save(powerStatsModel);

        assertNotNull(result);
        assertEquals(powerStats.getId(), result.getId());
    }


}
