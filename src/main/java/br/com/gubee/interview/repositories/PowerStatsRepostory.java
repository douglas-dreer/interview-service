package br.com.gubee.interview.repositories;

import br.com.gubee.interview.entities.PowerStatsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PowerStatsRepostory extends CrudRepository<PowerStatsEntity, UUID> {

}
