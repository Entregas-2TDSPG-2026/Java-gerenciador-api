# Gerenciador de Tarefas

Sistema de gerenciamento de tarefas estilo Trello, desenvolvido com Spring Boot + PostgreSQL.

## Integrantes

| Nome | RM |
|---|---|
| Arthur Brito da Silva | 562085 |
| Pedro Brum Lopes | 561780 |
| Luiz Felipe Flosi | 563197 |

## Tecnologias

- Java 21 + Spring Boot 4.0.6
- Spring Data JPA + Hibernate
- PostgreSQL (hospedado na **Oracle Cloud Infrastructure – OCI**)
- Lombok
- Frontend em HTML/CSS/JS puro (servido como static resource)

## Banco de Dados

O banco PostgreSQL está hospedado na **OCI (Oracle Cloud Infrastructure)**. As credenciais de conexão estão configuradas em `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://163.176.225.114:5432/tarefas_user
spring.datasource.username=felipeflosii
```

> Certifique-se de que o IP da sua máquina tem acesso liberado nas regras de segurança da OCI.

## Como executar

```bash
# Clone o repositório
git clone <url-do-repo>
cd gerenciador-tarefas

# Execute com Maven Wrapper
./mvnw spring-boot:run
```

Acesse o frontend em: [http://localhost:8080](http://localhost:8080)

## Endpoints principais

| Recurso | Base URL |
|---|---|
| Quadros | `/api/quadros` |
| Listas | `/api/listas` |
| Tarefas | `/api/tarefas` |

## Estrutura do projeto

```
src/main/java/.../
├── controller/   # QuadroController, ListaController, TarefaController
├── service/      # Regras de negócio
├── repository/   # Spring Data JPA
├── models/       # Quadro, ListaTarefas, Tarefa
├── dto/          # DTOs de requisição e resposta
├── validation/   # @PrioridadeValida
└── config/       # ManipuladorExcecoes
```
