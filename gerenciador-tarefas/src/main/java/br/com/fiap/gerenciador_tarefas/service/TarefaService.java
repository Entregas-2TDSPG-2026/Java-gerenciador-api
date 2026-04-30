package br.com.fiap.gerenciador_tarefas.service;

import br.com.fiap.gerenciador_tarefas.dto.TarefaDTO;
import br.com.fiap.gerenciador_tarefas.models.ListaTarefas;
import br.com.fiap.gerenciador_tarefas.models.Tarefa;
import br.com.fiap.gerenciador_tarefas.repository.ListaRepository;
import br.com.fiap.gerenciador_tarefas.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ListaRepository listaRepository;

    @Transactional(readOnly = true)
    public Page<TarefaDTO.Resposta> listarTodas(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(TarefaDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public TarefaDTO.Resposta buscarPorId(Long id) {
        return TarefaDTO.Resposta.fromEntity(buscarOuLancarExcecao(id));
    }

    @Transactional(readOnly = true)
    public Page<TarefaDTO.Resposta> filtrarPorPrioridade(Tarefa.Prioridade prioridade, Pageable pageable) {
        return tarefaRepository.findByPrioridade(prioridade, pageable).map(TarefaDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TarefaDTO.Resposta> filtrarPorStatus(boolean concluida, Pageable pageable) {
        return tarefaRepository.findByConcluida(concluida, pageable).map(TarefaDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TarefaDTO.Resposta> buscarPorPalavra(String palavra, Pageable pageable) {
        return tarefaRepository.findByTituloContainingIgnoreCase(palavra, pageable).map(TarefaDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TarefaDTO.Resposta> buscarComPrazoProximo(LocalDate data, Pageable pageable) {
        return tarefaRepository.findByPrazoBeforeAndConcluidaFalse(data, pageable).map(TarefaDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<TarefaDTO.Resposta> buscarAtrasadasPorQuadro(Long quadroId) {
        return tarefaRepository.buscarAtrasadasPorQuadro(quadroId, LocalDate.now())
                .stream().map(TarefaDTO.Resposta::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public Page<TarefaDTO.Resposta> filtrarPorPrioridadeEStatus(
            Tarefa.Prioridade prioridade, boolean concluida, Pageable pageable) {
        return tarefaRepository.findByPrioridadeAndConcluida(prioridade, concluida, pageable)
                .map(TarefaDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<TarefaDTO.Resposta> listarPorLista(Long listaId) {
        return tarefaRepository.findByListaIdOrderByCriadaEmDesc(listaId)
                .stream().map(TarefaDTO.Resposta::fromEntity).toList();
    }

    @Transactional
    public TarefaDTO.Resposta criar(TarefaDTO.Requisicao requisicao) {
        ListaTarefas lista = listaRepository.findById(requisicao.getListaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Lista não encontrada com id: " + requisicao.getListaId()));
        Tarefa tarefa = Tarefa.builder()
                .titulo(requisicao.getTitulo())
                .descricao(requisicao.getDescricao())
                .prioridade(requisicao.getPrioridade())
                .prazo(requisicao.getPrazo())
                .lista(lista)
                .build();
        return TarefaDTO.Resposta.fromEntity(tarefaRepository.save(tarefa));
    }

    @Transactional
    public TarefaDTO.Resposta atualizar(Long id, TarefaDTO.Requisicao requisicao) {
        Tarefa tarefa = buscarOuLancarExcecao(id);
        ListaTarefas lista = listaRepository.findById(requisicao.getListaId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Lista não encontrada com id: " + requisicao.getListaId()));
        tarefa.setTitulo(requisicao.getTitulo());
        tarefa.setDescricao(requisicao.getDescricao());
        tarefa.setPrioridade(requisicao.getPrioridade());
        tarefa.setPrazo(requisicao.getPrazo());
        tarefa.setLista(lista);
        return TarefaDTO.Resposta.fromEntity(tarefaRepository.save(tarefa));
    }

    @Transactional
    public TarefaDTO.Resposta concluir(Long id) {
        Tarefa tarefa = buscarOuLancarExcecao(id);
        tarefa.setConcluida(true);
        return TarefaDTO.Resposta.fromEntity(tarefaRepository.save(tarefa));
    }

    @Transactional
    public TarefaDTO.Resposta reabrirTarefa(Long id) {
        Tarefa tarefa = buscarOuLancarExcecao(id);
        tarefa.setConcluida(false);
        return TarefaDTO.Resposta.fromEntity(tarefaRepository.save(tarefa));
    }

    @Transactional
    public void deletar(Long id) {
        buscarOuLancarExcecao(id);
        tarefaRepository.deleteById(id);
    }

    private Tarefa buscarOuLancarExcecao(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com id: " + id));
    }
}