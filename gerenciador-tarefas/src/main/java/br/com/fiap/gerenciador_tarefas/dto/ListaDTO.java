package br.com.fiap.gerenciador_tarefas.dto;

import br.com.fiap.gerenciador_tarefas.models.ListaTarefas;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ListaDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Requisicao {

        @NotBlank(message = "O nome da lista é obrigatório")
        @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
        private String nome;

        @NotNull(message = "A posição é obrigatória")
        private Integer posicao;

        @NotNull(message = "O ID do quadro é obrigatório")
        private Long quadroId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Resposta {
        private Long id;
        private String nome;
        private Integer posicao;
        private Long quadroId;
        private List<TarefaDTO.Resposta> tarefas;

        public static Resposta fromEntity(ListaTarefas lista) {
            return Resposta.builder()
                    .id(lista.getId())
                    .nome(lista.getNome())
                    .posicao(lista.getPosicao())
                    .quadroId(lista.getQuadro().getId())
                    .tarefas(lista.getTarefas().stream()
                            .map(TarefaDTO.Resposta::fromEntity)
                            .toList())
                    .build();
        }
    }
}