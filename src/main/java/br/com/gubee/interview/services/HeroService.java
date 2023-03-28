package br.com.gubee.interview.services;

import br.com.gubee.interview.entities.HeroEntity;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.repositories.HeroRepository;
import br.com.gubee.interview.utils.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class HeroService implements IHeroService {

    @Autowired
    private HeroRepository repository;

    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public List<Hero> list(){
        return mapperUtil.mapList(
                (List<HeroEntity>) repository.findAll(),
                Hero.class
        );
    }

    @Override
    public List<Hero> findByName(String name) {
        return mapperUtil.mapList(repository.findHeroEntitiesByNameContainingIgnoreCase(name), Hero.class);
    }

    @Override
    public Hero findById(UUID id) {
        return repository.existsById(id) ?
                mapperUtil.convertTo(repository.findById(id).get(), Hero.class) :
                null;
    }

    @Override
    public Hero save(Hero model) {
        HeroEntity entityToSave = mapperUtil.convertTo(model, HeroEntity.class);
        return mapperUtil.convertTo(repository.save(entityToSave), Hero.class);
    }

    @Override
    public List<Hero> saveAll(List<Hero> modelList) {
        List<HeroEntity> entityList = mapperUtil.mapList(modelList, HeroEntity.class);
        return mapperUtil.mapList((List<HeroEntity>) repository.saveAll(entityList), Hero.class);
    }

    @Override
    public boolean delete(UUID id) {
        boolean status = false;
        if (existsById(id)) {
            repository.deleteById(id);
            status = true;
        }
        return status;
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

}
