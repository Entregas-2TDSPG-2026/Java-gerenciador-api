package br.com.fiap.gerenciador_tarefas.controller;

import br.com.fiap.gerenciador_tarefas.dto.ListaDTO;
import br.com.fiap.gerenciador_tarefas.service.ListaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
@RequiredArgsConstructor
public class ListaController {

    private final ListaService listaService;

    @GetMapping("/quadro/{quadroId}")
    public ResponseEntity<List<ListaDTO.Resposta>> listarPorQuadro(@PathVariable Long quadroId) {
        return ResponseEntity.ok(listaService.listarPorQuadro(quadroId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO.Resposta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(listaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ListaDTO.Resposta> criar(
            @Valid @RequestBody ListaDTO.Requisicao requisicao
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(listaService.criar(requisicao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaDTO.Resposta> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ListaDTO.Requisicao requisicao
    ) {
        return ResponseEntity.ok(listaService.atualizar(id, requisicao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        listaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}