package br.ufms.cpcx.rodeio_api.services;

import br.ufms.cpcx.rodeio_api.dtos.LocalizacaoDTO;
import br.ufms.cpcx.rodeio_api.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RodeioApiServiceFacade {
    private final CompetidorService competidorService;
    private final TropeiroService tropeiroService;
    private final EventoService eventoService;
    private final EventoCompetidorInscricaoService eventoCompetidorInscricaoService;
    private final AnimalService animalService;

    public RodeioApiServiceFacade(CompetidorService ccompetidorService, TropeiroService tropeiroService, EventoService eventoService, EventoCompetidorInscricaoService eventoCompetidorInscricaoService, AnimalService animalService) {
        this.competidorService = ccompetidorService;
        this.tropeiroService = tropeiroService;
        this.eventoService = eventoService;
        this.eventoCompetidorInscricaoService = eventoCompetidorInscricaoService;
        this.animalService = animalService;
    }

    /**
     * COMPETIDORES
     **/
    public Page<CompetidorModel> getCompetidores(Pageable pageable) {
        return this.competidorService.findAll(pageable);
    }

    public Optional<CompetidorModel> getCompetidorById(Long id) {
        return this.competidorService.findById(id);
    }

    public CompetidorModel createCompetidor(CompetidorModel competidorModel) {
        return this.competidorService.save(competidorModel);
    }

    public void deleteCompetidor(CompetidorModel competidorModel) {
        this.competidorService.delete(competidorModel);
    }

    public Page<CompetidorModel> getCompetidoresByNome(Pageable pageable, String nome){
        return this.competidorService.findByName(pageable, nome);
    }

    /**
     * TROPEIROS
     **/
    public Page<TropeiroModel> getTropeiros(Pageable pageable) {
        return this.tropeiroService.findAll(pageable);
    }

    public Optional<TropeiroModel> getTropeiroById(Long id) {
        return this.tropeiroService.findById(id);
    }

    public TropeiroModel createTropeiro(TropeiroModel tropeiroModel) {
        return this.tropeiroService.save(tropeiroModel);
    }

    public void deleteTropeiro(TropeiroModel tropeiroModel) {
        this.tropeiroService.delete(tropeiroModel);
    }

    public boolean tropeiroExistsByNameAndSigla(String nome, String sigla) {
        return this.tropeiroService.existsByNameAndSigla(nome, sigla);
    }


    /**
     * ANIMAL
     **/
    public AnimalModel createAnimal(AnimalModel animalModel) {
        return this.animalService.save(animalModel);
    }

    public Optional<AnimalModel> getAnimal(Long id) {
        return this.animalService.findById(id);
    }

    public Page<AnimalModel> getAnimais(Pageable pageable) {
        return this.animalService.findAll(pageable);
    }

    public void deleteAnimal(AnimalModel animalModel) {
        TropeiroModel tropeiroModel = animalModel.getProprietario();

        if (tropeiroModel.getBoiada().size() < 2) {
            this.tropeiroService.delete(tropeiroModel);
            return;
        }
        this.animalService.delete(animalModel);
    }

    public boolean animalExistsByNomeAndProprietario(String nome, TropeiroModel tropeiroModel) {
        return this.animalService.existsByNomeAndProprietario(nome, tropeiroModel);
    }

    public Page<AnimalModel> findAnimalByNome(Pageable pageable, String nome){
        return this.animalService.findByName(pageable, nome);
    }
    /* EVENTOS */

    public Optional<EventoModel> getEvento(Long id) {
        return this.eventoService.findById(id);
    }

    public Page<EventoModel> getEventos(Pageable pageable) {
        return this.eventoService.findAll(pageable);
    }

    public EventoModel createEvento(EventoModel eventoModel) {
        return this.eventoService.save(eventoModel);
    }

    public void deleteEvento(EventoModel eventoModel) {
        this.eventoService.delete(eventoModel);
    }

    public boolean eventoExistsByTituloAndLocalizacao(String titulo, LocalizacaoDTO localizacao) {
        return this.eventoService.existsByTituloAndLocalizacao(titulo, localizacao);
    }

    /* INSCRICAO COMPETIDORES */
    public Optional<EventoCompetidorInscricaoModel> getEventoCompetidorInscricao(Long id){
        return this.eventoCompetidorInscricaoService.findById(id);
    }

    public EventoCompetidorInscricaoModel createEventoCompetidorInscricao(EventoCompetidorInscricaoModel eventoCompetidorInscricaoModel){
        return this.eventoCompetidorInscricaoService.save(eventoCompetidorInscricaoModel);
    }

    public Optional<EventoCompetidorInscricaoModel> findEventoCompetidorInscricaoByCompetidorAndEvento(CompetidorModel competidorModel, EventoModel eventoModel){
        return this.eventoCompetidorInscricaoService.findByCompetidorAndEvento(competidorModel, eventoModel);
    }

    public void deleteEventoCompetidorInscricao(EventoCompetidorInscricaoModel eventoCompetidorInscricaoModel) {
        this.eventoCompetidorInscricaoService.delete(eventoCompetidorInscricaoModel);
    }
}