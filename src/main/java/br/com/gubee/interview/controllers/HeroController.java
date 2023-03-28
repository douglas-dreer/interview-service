package br.com.gubee.interview.controllers;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.HerosVersus;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.services.HeroService;
import br.com.gubee.interview.services.PowerStatsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static br.com.gubee.interview.enums.MenssagemEnum.INTERNAL_ERROR;
import static br.com.gubee.interview.enums.MenssagemEnum.NOT_FOUND;

@RestController
@Log4j2
public class HeroController {
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private HeroService heroService;

    @Autowired
    private PowerStatsService powerStatsService;

    @GetMapping(path = "heroes")
    public ResponseEntity<List<Hero>> list() {
        return ResponseEntity.ok(heroService.list());
    }

    /*
    @PostMapping(path = "heroes")
    public ResponseEntity<List<Hero>> saveAll(@RequestBody List<Hero> heroList) {
        return ResponseEntity.ok(heroService.saveAll(heroList));
    }
     */

    @GetMapping(path = "hero/{id}")
    public ResponseEntity<Hero> findById(@PathVariable("id") UUID id) {
        Hero result = heroService.findById(id);
        return result != null ?
                ResponseEntity.ok(result) :
                ResponseEntity.notFound().build();
    }

    @GetMapping(path = "hero")
    public ResponseEntity<List<Hero>> findByName(@RequestParam("name") String name) {
        List<Hero> result = heroService.findByName(name);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "hero/{idHeroChallenger}/vs/{idHeroDefender}")
    public ResponseEntity<HerosVersus> versus(
            @PathVariable("idHeroChallenger") UUID idHeroChallenger,
            @PathVariable("idHeroDefender") UUID idHeroDefender
            ) {
        HerosVersus herosVersus = HerosVersus.builder()
                .challenger(heroService.findById(idHeroChallenger))
                .defender(heroService.findById(idHeroDefender))
                .result(new PowerStats())
                .build();
        herosVersus.fight();

        return ResponseEntity.ok(herosVersus);
    }

    @PostMapping(path = "hero", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hero> save(@RequestBody Hero model) throws URISyntaxException {
        model = heroService.save(model);

        if (model == null) {
            log.error(INTERNAL_ERROR.getDescription());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.created(new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), model.getId()))).build();
    }

    @PatchMapping(path = "hero", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hero> update(@RequestBody Hero model) throws URISyntaxException {
        model = heroService.save(model);
        if (model == null) {
            log.error(NOT_FOUND.getDescription());
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(new URI(String.format("%s/%s", httpServletRequest.getRequestURL(), model.getId()))).build();
    }

    @DeleteMapping(path = "hero/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id) {
       boolean isDeleted = heroService.delete(id);
       return isDeleted ?
               ResponseEntity.ok().build() :
               ResponseEntity.notFound().build();
    }

}
