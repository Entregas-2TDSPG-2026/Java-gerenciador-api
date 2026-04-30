package br.com.fiap.gerenciador_tarefas.controller;

import br.com.fiap.gerenciador_tarefas.dto.QuadroDTO;
import br.com.fiap.gerenciador_tarefas.service.QuadroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quadros")
@RequiredArgsConstructor
public class QuadroController {

    private final QuadroService quadroService;

    @GetMapping
    public ResponseEntity<Page<QuadroDTO.Resposta>> listarTodos(
            @RequestParam(defaultValue = "0")        int pagina,
            @RequestParam(defaultValue = "10")       int tamanho,
            @RequestParam(defaultValue = "criadoEm") String ordenar,
            @RequestParam(defaultValue = "DESC")     String direcao
    ) {
        Sort sort = direcao.equalsIgnoreCase("ASC")
                ? Sort.by(ordenar).ascending()
                : Sort.by(ordenar).descending();
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        return ResponseEntity.ok(quadroService.listarTodos(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<QuadroDTO.Resposta>> buscarPorNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0")  int pagina,
            @RequestParam(defaultValue = "10") int tamanho
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return ResponseEntity.ok(quadroService.buscarPorNome(nome, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuadroDTO.RespostaCompleta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(quadroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<QuadroDTO.Resposta> criar(
            @Valid @RequestBody QuadroDTO.Requisicao requisicao
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quadroService.criar(requisicao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuadroDTO.Resposta> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody QuadroDTO.Requisicao requisicao
    ) {
        return ResponseEntity.ok(quadroService.atualizar(id, requisicao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        quadroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}