package br.com.fiap.gerenciador_tarefas.repository;

import br.com.fiap.gerenciador_tarefas.models.Quadro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadroRepository extends JpaRepository<Quadro, Long> {

    Page<Quadro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByNome(String nome);
}