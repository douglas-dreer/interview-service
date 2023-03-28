package br.com.gubee.interview.services;

import br.com.gubee.interview.entities.HeroEntity;
import br.com.gubee.interview.model.Hero;

import java.util.List;
import java.util.UUID;

public interface IHeroService {
    List<Hero> list();
    List<Hero> findByName(String name);
    Hero findById(UUID id);
    Hero save(Hero model);
    List<Hero> saveAll(List<Hero> heroList);
    boolean delete(UUID id);
    boolean existsById(UUID id);
}
