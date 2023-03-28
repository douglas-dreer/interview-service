package br.com.gubee.interview;

import br.com.gubee.interview.entities.HeroEntity;
import br.com.gubee.interview.entities.PowerStatsEntity;
import br.com.gubee.interview.enums.RaceEnum;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.HerosVersus;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.utils.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
public class Auxilar {
    Random random = new Random();
    final Map<String, RaceEnum> heroNameMap = new HashMap<>();


    private MapperUtil mapperUtil = new MapperUtil();

    public Auxilar() {
        createHeroNameMap();
    }

    private void createHeroNameMap() {
        this.heroNameMap.put("Superman",         RaceEnum.ALIEN);
        this.heroNameMap.put("Green Lantern",    RaceEnum.ALIEN);
        this.heroNameMap.put("Groot",            RaceEnum.ALIEN);
        this.heroNameMap.put("Mrs. Marvel",      RaceEnum.ALIEN);
        this.heroNameMap.put("Amanda Waller",    RaceEnum.HUMAN);
        this.heroNameMap.put("Ironman",          RaceEnum.HUMAN);
        this.heroNameMap.put("Wolverine",        RaceEnum.HUMAN);
        this.heroNameMap.put("Harley Quinn",     RaceEnum.HUMAN);
        this.heroNameMap.put("Zeus",             RaceEnum.DIVINE);
        this.heroNameMap.put("Hera",             RaceEnum.DIVINE);
        this.heroNameMap.put("Wonder Woman",     RaceEnum.DIVINE);
        this.heroNameMap.put("Kratos",           RaceEnum.DIVINE);
        this.heroNameMap.put("Robocop",          RaceEnum.CYBORG);
        this.heroNameMap.put("Vision",           RaceEnum.CYBORG);
        this.heroNameMap.put("Ultraman",         RaceEnum.CYBORG);
        this.heroNameMap.put("T9",               RaceEnum.CYBORG);
    }

    public PowerStats createPowerStats() {
        return  PowerStats.builder()
                .id(UUID.randomUUID())
                .strength(random.nextInt(1,100))
                .dexterity(random.nextInt(1,100))
                .agility(random.nextInt(1,100))
                .intelligence(random.nextInt(1,100))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public List<PowerStats> createPowerStats(int quantity) {
        List<PowerStats> results = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            results.add(createPowerStats());
        }
        return results;
    }

    public Hero createHero(){
        int position = random.nextInt(0, heroNameMap.size());
        Map.Entry<String, RaceEnum> heroRandom = heroNameMap.entrySet().stream().toList().get(position);

        return Hero.builder()
                .id(UUID.randomUUID())
                .name(heroRandom.getKey())
                .race(heroRandom.getValue())
                .powerStats(createPowerStats())
                .isEnabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public List<Hero> createHero(int quantity) throws Exception {
        List<Hero> heroList = new ArrayList<>();

        if (quantity > heroNameMap.size()) {
            log.error("The amount generated is greater than what we have in memory.");
            return heroList;
        }

        for (int i = 1; i <= quantity; i++) {
            Hero hero = createHero();

            while (checkIfHasExistInList(heroList, hero)) {
                hero = createHero();
            }

            heroList.add(hero);
        }
        return heroList;
    }

    public HerosVersus createVerus() {
        HerosVersus versus = HerosVersus.builder()
                .challenger(createHero())
                .defender(createHero())
                .result(new PowerStats())
                .build();
        versus.fight();
        return versus;
    }

    private boolean checkIfHasExistInList(List<Hero> heroList, Hero hero) {
        return heroList.stream().filter(o -> o.getName().equals(hero.getName())).findFirst().isPresent();
    }
}
