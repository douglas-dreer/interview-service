package br.com.gubee.interview.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HerosVersus {
    private Hero challenger;
    private Hero defender;
    private PowerStats result = new PowerStats();
    private Double challengerAvg;
    private Double defenderAvg;
    private Double diffAvg;

    public void fight() {
        PowerStats challenger = this.challenger.getPowerStats();
        PowerStats defender = this.defender.getPowerStats();

        this.result.setId(UUID.randomUUID());
        this.result.setStrength(challenger.getStrength() - defender.getStrength());
        this.result.setAgility(challenger.getAgility() - defender.getAgility());
        this.result.setDexterity(challenger.getDexterity() - defender.getDexterity());
        this.result.setIntelligence(challenger.getIntelligence() - defender.getIntelligence());
        this.result.setCreatedAt(LocalDateTime.now());
        this.result.setUpdatedAt(LocalDateTime.now());

        this.challengerAvg = challenger.calcAvg();
        this.defenderAvg = defender.calcAvg();
        this.diffAvg = this.challengerAvg - defenderAvg;
    }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(this);
    }
}
