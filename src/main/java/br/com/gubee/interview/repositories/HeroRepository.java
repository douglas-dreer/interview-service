package br.com.gubee.interview.repositories;

import br.com.gubee.interview.entities.HeroEntity;
import br.com.gubee.interview.model.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeroRepository extends CrudRepository<HeroEntity, UUID> {

    List<HeroEntity> findHeroEntitiesByNameContainingIgnoreCase(String name);
}
