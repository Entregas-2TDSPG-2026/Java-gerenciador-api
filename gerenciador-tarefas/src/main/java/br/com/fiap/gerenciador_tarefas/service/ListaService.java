package br.com.fiap.gerenciador_tarefas.service;

import br.com.fiap.gerenciador_tarefas.dto.ListaDTO;
import br.com.fiap.gerenciador_tarefas.models.ListaTarefas;
import br.com.fiap.gerenciador_tarefas.models.Quadro;
import br.com.fiap.gerenciador_tarefas.repository.ListaRepository;
import br.com.fiap.gerenciador_tarefas.repository.QuadroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaService {

    private final ListaRepository listaRepository;
    private final QuadroRepository quadroRepository;

    @Transactional(readOnly = true)
    public List<ListaDTO.Resposta> listarPorQuadro(Long quadroId) {
        return listaRepository.findByQuadroIdOrderByPosicao(quadroId)
                .stream().map(ListaDTO.Resposta::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ListaDTO.Resposta buscarPorId(Long id) {
        return ListaDTO.Resposta.fromEntity(buscarOuLancarExcecao(id));
    }

    @Transactional
    public ListaDTO.Resposta criar(ListaDTO.Requisicao requisicao) {
        Quadro quadro = quadroRepository.findById(requisicao.getQuadroId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Quadro não encontrado com id: " + requisicao.getQuadroId()));
        ListaTarefas lista = ListaTarefas.builder()
                .nome(requisicao.getNome())
                .posicao(requisicao.getPosicao())
                .quadro(quadro)
                .build();
        return ListaDTO.Resposta.fromEntity(listaRepository.save(lista));
    }

    @Transactional
    public ListaDTO.Resposta atualizar(Long id, ListaDTO.Requisicao requisicao) {
        ListaTarefas lista = buscarOuLancarExcecao(id);
        lista.setNome(requisicao.getNome());
        lista.setPosicao(requisicao.getPosicao());
        return ListaDTO.Resposta.fromEntity(listaRepository.save(lista));
    }

    @Transactional
    public void deletar(Long id) {
        buscarOuLancarExcecao(id);
        listaRepository.deleteById(id);
    }

    private ListaTarefas buscarOuLancarExcecao(Long id) {
        return listaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lista não encontrada com id: " + id));
    }
}