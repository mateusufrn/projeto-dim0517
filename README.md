# Projeto DIM0517 - Sistema Bancario API REST

Projeto desenvolvido para a disciplina DIM0517 - Gerencia de Configuracao e
Mudancas. O sistema oferece uma API REST para cadastro de contas e realizacao
de operacoes bancarias.

## Integrantes

- Mateus Nascimento - GitHub: @mateusufrn
- Bianca Antonelly - GitHub: @biancaantonelly
- Caio Vitor - GitHub: @caiovitordm

## Tecnologias

- Java 17
- Spring Boot 3.2
- Maven
- JUnit 5
- PMD
- Docker
- GitHub Actions

## Executar com Maven

Na raiz do repositorio:

```bash
cd sistema-bancario
mvn spring-boot:run
```

A API ficara disponivel em `http://localhost:8080/api`.

## Executar com Docker

A imagem esta publicada no
[Docker Hub](https://hub.docker.com/r/biancaantonellyt/bankflow-api).

```bash
docker pull biancaantonellyt/bankflow-api:latest
docker run --rm -p 8080:8080 biancaantonellyt/bankflow-api:latest
```

## Endpoints

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| `POST` | `/api/banco/conta/` | Cadastrar conta |
| `GET` | `/api/banco/conta/{id}` | Consultar dados da conta |
| `GET` | `/api/banco/conta/{id}/saldo` | Consultar saldo |
| `PUT` | `/api/banco/conta/{id}/credito` | Realizar credito |
| `PUT` | `/api/banco/conta/{id}/debito` | Realizar debito |
| `PUT` | `/api/banco/conta/transferencia` | Realizar transferencia |
| `PUT` | `/api/banco/conta/rendimento` | Aplicar rendimento |

## Exemplos

Cadastrar uma conta:

```bash
curl -X POST http://localhost:8080/api/banco/conta/ \
  -H "Content-Type: application/json" \
  -d '{"numero":1,"tipo":"normal","saldoInicial":500.0}'
```

Consultar os dados da conta:

```bash
curl http://localhost:8080/api/banco/conta/1
```

Consultar o saldo:

```bash
curl http://localhost:8080/api/banco/conta/1/saldo
```

Realizar um credito:

```bash
curl -X PUT http://localhost:8080/api/banco/conta/1/credito \
  -H "Content-Type: application/json" \
  -d '{"valor":100.0}'
```

Realizar uma transferencia:

```bash
curl -X PUT http://localhost:8080/api/banco/conta/transferencia \
  -H "Content-Type: application/json" \
  -d '{"from":1,"to":2,"amount":50.0}'
```

## CI/CD

O projeto utiliza GitHub Actions para integracao continua, estabilizacao e
liberacao em producao.

- Merge de `staging` em `production`: cria uma tag `rel-X.Y`.
- Merge de `hotfix/*` em `production`: cria uma tag `rel-X.Y.Z`.
- Cada liberacao executa build, PMD e testes, gera um artefato ZIP e publica
  uma imagem versionada no Docker Hub.

Nao e realizado deploy em um ambiente de execucao.
