package br.com.fiap.gerenciador_tarefas.service;

import br.com.fiap.gerenciador_tarefas.repository.QuadroRepository;
import br.com.fiap.gerenciador_tarefas.service.ListaService;

import com.gerenciadortarefas.dto.QuadroDTO;
import br.com.fiap.gerenciador_tarefas.models.Quadro;
import br.com.fiap.gerenciador_tarefas.repository.QuadroRepository;
import br.com.fiap.gerenciador_tarefas.service.ListaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuadroService {

    private final QuadroRepository quadroRepository;

    @Transactional(readOnly = true)
    public Page<QuadroDTO.Resposta> listarTodos(Pageable pageable) {
        return quadroRepository.findAll(pageable).map(QuadroDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<QuadroDTO.Resumo> listarResumo(Pageable pageable) {
        return quadroRepository.findAll(pageable).map(QuadroDTO.Resumo::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<QuadroDTO.Resposta> buscarPorNome(String nome, Pageable pageable) {
        return quadroRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(QuadroDTO.Resposta::fromEntity);
    }

    @Transactional(readOnly = true)
    public QuadroDTO.RespostaCompleta buscarPorId(Long id) {
        return QuadroDTO.RespostaCompleta.fromEntity(buscarOuLancarExcecao(id));
    }

    @Transactional(readOnly = true)
    public QuadroDTO.Resumo buscarResumo(Long id) {
        return QuadroDTO.Resumo.fromEntity(buscarOuLancarExcecao(id));
    }

    @Transactional
    public QuadroDTO.Resposta criar(QuadroDTO.Requisicao requisicao) {
        Quadro quadro = Quadro.builder()
                .nome(requisicao.getNome())
                .descricao(requisicao.getDescricao())
                .build();
        return QuadroDTO.Resposta.fromEntity(quadroRepository.save(quadro));
    }

    @Transactional
    public QuadroDTO.Resposta atualizar(Long id, QuadroDTO.Requisicao requisicao) {
        Quadro quadro = buscarOuLancarExcecao(id);
        quadro.setNome(requisicao.getNome());
        quadro.setDescricao(requisicao.getDescricao());
        return QuadroDTO.Resposta.fromEntity(quadroRepository.save(quadro));
    }

    @Transactional
    public void deletar(Long id) {
        buscarOuLancarExcecao(id);
        quadroRepository.deleteById(id);
    }

    private Quadro buscarOuLancarExcecao(Long id) {
        return quadroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quadro não encontrado com id: " + id));
    }
}
