package br.com.fiap.gerenciador_tarefas.controller;

import br.com.fiap.gerenciador_tarefas.dto.TarefaDTO;
import br.com.fiap.gerenciador_tarefas.models.Tarefa;
import br.com.fiap.gerenciador_tarefas.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    // GET /api/tarefas?pagina=0&tamanho=10&ordenar=criadaEm
    @GetMapping
    public ResponseEntity<Page<TarefaDTO.Resposta>> listarTodas(
            @RequestParam(defaultValue = "0")        int pagina,
            @RequestParam(defaultValue = "10")       int tamanho,
            @RequestParam(defaultValue = "criadaEm") String ordenar
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by(ordenar).descending());
        return ResponseEntity.ok(tarefaService.listarTodas(pageable));
    }

    // GET /api/tarefas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO.Resposta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    // GET /api/tarefas/lista/{listaId}
    @GetMapping("/lista/{listaId}")
    public ResponseEntity<List<TarefaDTO.Resposta>> listarPorLista(@PathVariable Long listaId) {
        return ResponseEntity.ok(tarefaService.listarPorLista(listaId));
    }

    // GET /api/tarefas/filtrar?prioridade=ALTA&pagina=0&tamanho=10
    @GetMapping("/filtrar/prioridade")
    public ResponseEntity<Page<TarefaDTO.Resposta>> filtrarPorPrioridade(
            @RequestParam Tarefa.Prioridade prioridade,
            @RequestParam(defaultValue = "0")  int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return ResponseEntity.ok(tarefaService.filtrarPorPrioridade(prioridade, pageable));
    }

    // GET /api/tarefas/filtrar/status?concluida=false
    @GetMapping("/filtrar/status")
    public ResponseEntity<Page<TarefaDTO.Resposta>> filtrarPorStatus(
            @RequestParam boolean concluida,
            @RequestParam(defaultValue = "0")  int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return ResponseEntity.ok(tarefaService.filtrarPorStatus(concluida, pageable));
    }

    // GET /api/tarefas/buscar?palavra=bug
    @GetMapping("/buscar")
    public ResponseEntity<Page<TarefaDTO.Resposta>> buscarPorPalavra(
            @RequestParam String palavra,
            @RequestParam(defaultValue = "0")  int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return ResponseEntity.ok(tarefaService.buscarPorPalavra(palavra, pageable));
    }

    // GET /api/tarefas/prazo?data=2025-12-31
    @GetMapping("/prazo")
    public ResponseEntity<Page<TarefaDTO.Resposta>> buscarComPrazoProximo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(defaultValue = "0")  int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return ResponseEntity.ok(tarefaService.buscarComPrazoProximo(data, pageable));
    }

    // GET /api/tarefas/atrasadas/quadro/{quadroId}
    @GetMapping("/atrasadas/quadro/{quadroId}")
    public ResponseEntity<List<TarefaDTO.Resposta>> buscarAtrasadasPorQuadro(
            @PathVariable Long quadroId
    ) {
        return ResponseEntity.ok(tarefaService.buscarAtrasadasPorQuadro(quadroId));
    }

    // POST /api/tarefas
    @PostMapping
    public ResponseEntity<TarefaDTO.Resposta> criar(
            @Valid @RequestBody TarefaDTO.Requisicao requisicao
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.criar(requisicao));
    }

    // PUT /api/tarefas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO.Resposta> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TarefaDTO.Requisicao requisicao
    ) {
        return ResponseEntity.ok(tarefaService.atualizar(id, requisicao));
    }

    // PATCH /api/tarefas/{id}/concluir
    @PatchMapping("/{id}/concluir")
    public ResponseEntity<TarefaDTO.Resposta> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.concluir(id));
    }

    // PATCH /api/tarefas/{id}/reabrir
    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<TarefaDTO.Resposta> reabrir(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.reabrirTarefa(id));
    }

    // DELETE /api/tarefas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}