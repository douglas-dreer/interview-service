package br.com.gubee.interview.services;

import br.com.gubee.interview.entities.PowerStatsEntity;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.repositories.PowerStatsRepostory;
import br.com.gubee.interview.utils.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class PowerStatsService implements IPowerStats {
    @Autowired
    private PowerStatsRepostory repository;

    @Autowired
    private MapperUtil mapperUtil;


    @Override
    public List<PowerStats> list() {
        return mapperUtil.mapList((List<PowerStatsEntity>) repository.findAll(), PowerStats.class);
    }

    @Override
    public PowerStats findById(UUID id) {
        return repository.existsById(id) ? mapperUtil.convertTo(repository.findById(id).get(), PowerStats.class) : null;
    }

    @Override
    public PowerStats save(PowerStats model) {
        PowerStatsEntity entity = mapperUtil.convertTo(model, PowerStatsEntity.class);
        return mapperUtil.convertTo(repository.save(entity), PowerStats.class);
    }
}
