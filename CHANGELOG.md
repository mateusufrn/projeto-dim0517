# Changelog

Todas as mudancas relevantes deste projeto serao documentadas neste arquivo.

## [1.1] - 2026-05-04

### Adicionado

- Menu interativo em console (`ConsoleMenu`) com opcoes para:
  - cadastrar conta
  - consultar saldo
  - realizar credito
  - realizar debito
  - realizar transferencia
  - encerrar a aplicacao
- Fluxo de execucao principal atualizado para iniciar o menu de interacao (`Main` ->
  `ConsoleMenu.start()`).
- Operacao de debito no dominio de conta (`Account.withdraw`) com validacao de valor positivo e
  saldo suficiente.
- Operacao de transferencia entre contas (`AccountService.transfer`) com validacoes de:
  - valor positivo
  - contas de origem e destino diferentes
  - existencia das contas envolvidas
  - saldo suficiente na conta de origem

### Alterado

- Camada de servico (`AccountService`) expandida para suportar as operacoes de debito e
  transferencia.

### Manutencao

- Arquivo `.gitignore` adicionado para ignorar artefatos de build:
  - `sistema-bancario/target/`
  - `*.class`
- Artefatos compilados removidos do versionamento.

### Commits incluidos

- `f342f80` feat(operacoes-iniciais#2): implementa menu de interacao e ajusta main
- `72e5cfc` Merge pull request #6 from mateusufrn/feature/02-funcionalidades-inicias-do-sistema
- `05a919f` feat(operacoes-bancarias#7): implementa debito e transferencia entre contas
- `2a1f81c` feat(operacoes-bancarias#7): remove arquivos compilados do repositorio
- `12a4702` Merge pull request #8 from
  mateusufrn/feature/07-funcionalidades-de-debito-e-transferencia-entre-contas
