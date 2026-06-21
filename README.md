# Projeto DIM0517 — Sistema Bancário em Console

Projeto desenvolvido para a disciplina DIM0517 – Gerência de Configuração e Mudanças, com foco na aplicação de boas práticas de controle de versão utilizando Git e GitHub, seguindo o fluxo GitLabFlow.

O sistema consiste em uma aplicação bancária simples com interação via console, contendo operações básicas de gerenciamento de contas e movimentações financeiras.

---

## Integrantes

- Mateus Nascimento — GitHub: @mateusufrn
- Bianca Antonelly — GitHub: @biancaantonelly
- Caio Vitor — GitHub: @caiovitordm


---

## Nome do Sistema

`BankFlow Console`

---

## Funcionalidades

O sistema possui as seguintes operações:

- Cadastrar Conta
- Consultar Saldo
- Realizar Crédito
- Realizar Débito
- Realizar Transferência

---

## Stack de Desenvolvimento

- Java 17
- Maven
- Aplicação Console (Java Scanner)
- Git + GitHub
- GitLabFlow para gerenciamento de branches
- GitHub Issues para rastreabilidade das tarefas
- GitHub Actions para integração e entrega contínua

---

### Separação em Camadas

O projeto segue uma estrutura em camadas para facilitar manutenção e testes futuros:

- ui/ → interação com o usuário (console)
- service/ → regras de negócio
- model/ → entidades do sistema
- app/ → classe principal de execução

---

## Controle de Versão

O repositório utiliza:

- Branch main, staging, production
- Pull Requests para integração
- Issues vinculadas a commits e alterações

Nenhum commit deve estar desacoplado de uma tarefa, garantindo rastreabilidade durante todo o desenvolvimento.

---

## Observações

- Não há persistência em banco de dados
- O foco principal da disciplina é o uso correto de Git e boas práticas de configuração
- Interface e usabilidade não são critérios de avaliação

---

## Como Executar o Projeto

Para compilar o projeto, execute:

```bash
mvn clean compile`
```

Para rodar a aplicação, execute:

```bash
mvn exec:java -Dexec.mainClass="br.ufrn.banco.app.Main"
```
---

## Professor / Repositório Compartilhado

O repositório foi compartilhado com o usuário:

gibeonufrn

conforme solicitado na especificção da disciplina.

---