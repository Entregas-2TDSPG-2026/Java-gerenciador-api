package br.com.fiap.gerenciador_tarefas.dto;

import br.com.fiap.gerenciador_tarefas.models.Quadro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class QuadroDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Requisicao {

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        private String nome;

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        private String descricao;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Resposta {
        private Long id;
        private String nome;
        private String descricao;
        private LocalDateTime criadoEm;
        private int totalListas;
        private int totalTarefas;

        public static Resposta fromEntity(Quadro quadro) {
            int totalTarefas = quadro.getListas().stream()
                    .mapToInt(l -> l.getTarefas().size())
                    .sum();

            return Resposta.builder()
                    .id(quadro.getId())
                    .nome(quadro.getNome())
                    .descricao(quadro.getDescricao())
                    .criadoEm(quadro.getCriadoEm())
                    .totalListas(quadro.getListas().size())
                    .totalTarefas(totalTarefas)
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RespostaCompleta {
        private Long id;
        private String nome;
        private String descricao;
        private LocalDateTime criadoEm;
        private List<ListaDTO.Resposta> listas;

        public static RespostaCompleta fromEntity(Quadro quadro) {
            return RespostaCompleta.builder()
                    .id(quadro.getId())
                    .nome(quadro.getNome())
                    .descricao(quadro.getDescricao())
                    .criadoEm(quadro.getCriadoEm())
                    .listas(quadro.getListas().stream()
                            .map(ListaDTO.Resposta::fromEntity)
                            .toList())
                    .build();
        }
    }
}