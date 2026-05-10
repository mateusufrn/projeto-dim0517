# Changelog

Resumo das principais mudanças por versão.

## [1.1.1] - 2026-05-10

### Corrigido

- Débito e transferência agora são bloqueados quando não há saldo suficiente.
- Ajustes para manter arquivos compilados fora do versionamento.

### Commits incluídos

- `a446c2f` merge da correção de saldo insuficiente na branch de release
- `46c89eb` correção para impedir operações sem saldo suficiente

## [1.1] - 2026-05-04

### Adicionado

- Menu interativo no console para as operações principais da conta.
- Inclusão das operações de débito e transferência entre contas.

### Manutenção

- Inclusão de regras no `.gitignore` para evitar versionar artefatos de build.
- Artefatos compilados removidos do versionamento.

### Commits incluídos

- `f342f80` feat(operacoes-iniciais#2): implementa menu de interação e ajusta main
- `72e5cfc` Merge pull request #6 from mateusufrn/feature/02-funcionalidades-inicias-do-sistema
- `05a919f` feat(operacoes-bancarias#7): implementa débito e transferência entre contas
- `2a1f81c` feat(operacoes-bancarias#7): remove arquivos compilados do repositório
- `12a4702` Merge pull request #8 from
  mateusufrn/feature/07-funcionalidades-de-debito-e-transferencia-entre-contas
