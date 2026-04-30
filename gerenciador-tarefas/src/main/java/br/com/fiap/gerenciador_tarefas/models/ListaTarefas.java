package br.com.fiap.gerenciador_tarefas.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaTarefas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da lista é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotNull(message = "A posição é obrigatória")
    private Integer posicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quadro_id", nullable = false)
    private Quadro quadro;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Tarefa> tarefas = new ArrayList<>();
}