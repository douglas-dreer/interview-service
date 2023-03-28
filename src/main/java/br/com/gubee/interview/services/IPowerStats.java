package br.com.gubee.interview.services;

import br.com.gubee.interview.model.PowerStats;

import java.util.List;
import java.util.UUID;

public interface IPowerStats {
    List<PowerStats> list();
    PowerStats findById(UUID id);
    PowerStats save(PowerStats model);

}
