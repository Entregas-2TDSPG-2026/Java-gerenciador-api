package br.com.fiap.gerenciador_tarefas.repository;

import br.com.fiap.gerenciador_tarefas.models.ListaTarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaRepository extends JpaRepository<ListaTarefas, Long> {

    List<ListaTarefas> findByQuadroIdOrderByPosicao(Long quadroId);

    boolean existsByQuadroIdAndPosicao(Long quadroId, Integer posicao);
}