package br.com.fiap.gerenciador_tarefas.dto;

import br.com.fiap.gerenciador_tarefas.models.Tarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TarefaDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Requisicao {

        @NotBlank(message = "O título é obrigatório")
        @Size(min = 3, max = 200, message = "Título deve ter entre 3 e 200 caracteres")
        private String titulo;

        @Size(max = 1000)
        private String descricao;

        @NotNull(message = "A prioridade é obrigatória")
        private Tarefa.Prioridade prioridade;

        private LocalDate prazo;

        @NotNull(message = "O ID da lista é obrigatório")
        private Long listaId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Resposta {
        private Long id;
        private String titulo;
        private String descricao;
        private Tarefa.Prioridade prioridade;
        private LocalDate prazo;
        private boolean concluida;
        private LocalDateTime criadaEm;
        private Long listaId;
        private String nomeLista;

        public static Resposta fromEntity(Tarefa tarefa) {
            return Resposta.builder()
                    .id(tarefa.getId())
                    .titulo(tarefa.getTitulo())
                    .descricao(tarefa.getDescricao())
                    .prioridade(tarefa.getPrioridade())
                    .prazo(tarefa.getPrazo())
                    .concluida(tarefa.isConcluida())
                    .criadaEm(tarefa.getCriadaEm())
                    .listaId(tarefa.getLista().getId())
                    .nomeLista(tarefa.getLista().getNome())
                    .build();
        }
    }
}