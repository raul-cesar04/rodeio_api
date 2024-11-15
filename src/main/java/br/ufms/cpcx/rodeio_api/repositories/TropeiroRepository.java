package br.ufms.cpcx.rodeio_api.repositories;

import br.ufms.cpcx.rodeio_api.models.TropeiroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TropeiroRepository extends JpaRepository<TropeiroModel, Long> {
    public boolean existsByNomeAndSigla(String nome, String sigla);
}
