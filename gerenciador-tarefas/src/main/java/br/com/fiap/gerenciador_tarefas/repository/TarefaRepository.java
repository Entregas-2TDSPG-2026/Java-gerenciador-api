package br.com.fiap.gerenciador_tarefas.repository;

import br.com.fiap.gerenciador_tarefas.models.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Filtro por prioridade com paginação
    Page<Tarefa> findByPrioridade(Tarefa.Prioridade prioridade, Pageable pageable);

    // Filtro por status concluída com paginação
    Page<Tarefa> findByConcluida(boolean concluida, Pageable pageable);

    // Busca por palavra no título
    Page<Tarefa> findByTituloContainingIgnoreCase(String palavra, Pageable pageable);

    // Tarefas com prazo anterior a uma data e não concluídas
    Page<Tarefa> findByPrazoBeforeAndConcluidaFalse(LocalDate data, Pageable pageable);

    // Todas as tarefas de um quadro específico
    List<Tarefa> findByListaQuadroId(Long quadroId);

    // Tarefas atrasadas de um quadro específico
    @Query("""
            SELECT t FROM Tarefa t
            WHERE t.lista.quadro.id = :quadroId
            AND t.prazo < :hoje
            AND t.concluida = false
            ORDER BY t.prazo ASC
            """)
    List<Tarefa> buscarAtrasadasPorQuadro(
            @Param("quadroId") Long quadroId,
            @Param("hoje") LocalDate hoje
    );

    // Filtro combinado prioridade + concluída
    Page<Tarefa> findByPrioridadeAndConcluida(
            Tarefa.Prioridade prioridade,
            boolean concluida,
            Pageable pageable
    );

    // Tarefas de uma lista específica
    List<Tarefa> findByListaIdOrderByCriadaEmDesc(Long listaId);
}