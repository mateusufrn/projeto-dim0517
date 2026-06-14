package br.ufrn.banco.api;

import br.ufrn.banco.api.dto.CadastrarContaRequest;
import br.ufrn.banco.api.dto.OperacaoRequest;
import br.ufrn.banco.api.dto.TransferenciaRequest;
import br.ufrn.banco.api.dto.RendimentoRequest;
import br.ufrn.banco.model.Account;
import br.ufrn.banco.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/banco/conta")
public class BancoController {

    @Autowired
    private AccountService accountService;

    /**
     * POST /banco/conta/
     * Cadastra uma nova conta no banco
     *
     * @param request contém: numero (int), tipo (String), saldoInicial (double)
     * @return Account criada ou erro 400 se número duplicado ou saldo negativo
     */
    @PostMapping("/")
    public ResponseEntity<?> cadastrarConta(@RequestBody CadastrarContaRequest request) {
        if (request.getNumero() <= 0 || request.getSaldoInicial() < 0) {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Número deve ser positivo e saldo não pode ser negativo")
            );
        }

        Account account = null;

        if ("bonus".equalsIgnoreCase(request.getTipo())) {
            account = accountService.registerBonusAccount(request.getNumero(), request.getSaldoInicial());
        } else if ("poupanca".equalsIgnoreCase(request.getTipo())) {
            account = accountService.registerSavingsAccount(request.getNumero(), request.getSaldoInicial());
        } else if ("normal".equalsIgnoreCase(request.getTipo())) {
            account = accountService.registerAccount(request.getNumero(), request.getSaldoInicial());
        } else {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Tipo deve ser: 'normal', 'bonus' ou 'poupanca'")
            );
        }

        if (account == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("erro", "Conta com este número já existe ou dados inválidos")
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mapAccountResponse(account));
    }

    /**
     * GET /banco/conta/{id}
     * Consulta os dados de uma conta
     *
     * @param id número da conta
     * @return dados completos da Account
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> consultarConta(@PathVariable int id) {
        Account account = accountService.searchAccount(id);

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("erro", "Conta não encontrada")
            );
        }

        return ResponseEntity.ok(mapAccountResponse(account));
    }

    /**
     * GET /banco/conta/{id}/saldo
     * Consulta o saldo de uma conta
     *
     * @param id número da conta
     * @return saldo atual
     */
    @GetMapping("/{id}/saldo")
    public ResponseEntity<?> consultarSaldo(@PathVariable int id) {
        Account account = accountService.searchAccount(id);

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("erro", "Conta não encontrada")
            );
        }

        return ResponseEntity.ok(Map.of("numero", account.getNumber(), "saldo", account.getBalance()));
    }

    /**
     * PUT /banco/conta/{id}/credito
     * Realiza um depósito (crédito) na conta
     *
     * @param id número da conta
     * @param request contém: valor (double)
     * @return sucesso ou erro
     */
    @PutMapping("/{id}/credito")
    public ResponseEntity<?> realizarCredito(@PathVariable int id, @RequestBody OperacaoRequest request) {
        if (request.getValor() <= 0) {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Valor deve ser positivo")
            );
        }

        boolean success = accountService.deposit(id, request.getValor());

        if (!success) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("erro", "Conta não encontrada ou operação inválida")
            );
        }

        Account account = accountService.searchAccount(id);
        return ResponseEntity.ok(Map.of(
            "mensagem", "Depósito realizado com sucesso",
            "novoSaldo", account.getBalance(),
            "bonusPoints", account.getBonusPoints()
        ));
    }

    /**
     * PUT /banco/conta/{id}/debito
     * Realiza um saque (débito) na conta
     *
     * @param id número da conta
     * @param request contém: valor (double)
     * @return sucesso ou erro
     */
    @PutMapping("/{id}/debito")
    public ResponseEntity<?> realizarDebito(@PathVariable int id, @RequestBody OperacaoRequest request) {
        if (request.getValor() <= 0) {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Valor deve ser positivo")
            );
        }

        boolean success = accountService.withdraw(id, request.getValor());

        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("erro", "Saldo insuficiente ou conta não encontrada")
            );
        }

        Account account = accountService.searchAccount(id);
        return ResponseEntity.ok(Map.of(
            "mensagem", "Saque realizado com sucesso",
            "novoSaldo", account.getBalance()
        ));
    }

    /**
     * PUT /banco/conta/transferencia
     * Realiza uma transferência entre contas
     *
     * @param request contém: from (int), to (int), amount (double)
     * @return sucesso ou erro
     */
    @PutMapping("/transferencia")
    public ResponseEntity<?> realizarTransferencia(@RequestBody TransferenciaRequest request) {
        if (request.getAmount() <= 0) {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Valor deve ser positivo")
            );
        }

        if (request.getFrom() == request.getTo()) {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Não é possível transferir para a mesma conta")
            );
        }

        boolean success = accountService.transfer(request.getFrom(), request.getTo(), request.getAmount());

        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("erro", "Transferência falhou: contas não encontradas ou saldo insuficiente")
            );
        }

        Account sourceAccount = accountService.searchAccount(request.getFrom());
        Account destAccount = accountService.searchAccount(request.getTo());

        return ResponseEntity.ok(Map.of(
            "mensagem", "Transferência realizada com sucesso",
            "contaOrigem", Map.of("numero", sourceAccount.getNumber(), "novoSaldo", sourceAccount.getBalance()),
            "contaDestino", Map.of("numero", destAccount.getNumber(), "novoSaldo", destAccount.getBalance()),
            "bonusPointsDestino", destAccount.getBonusPoints()
        ));
    }

    /**
     * PUT /banco/conta/rendimento
     * Aplica juros às contas de poupança
     *
     * @param request contém: taxaJuros (double)
     * @return número de contas atualizadas
     */
    @PutMapping("/rendimento")
    public ResponseEntity<?> aplicarRendimento(@RequestBody RendimentoRequest request) {
        if (request.getTaxaJuros() <= 0) {
            return ResponseEntity.badRequest().body(
                Map.of("erro", "Taxa de juros deve ser positiva")
            );
        }

        int updatedAccounts = accountService.applyInterestToSavingsAccounts(request.getTaxaJuros());

        return ResponseEntity.ok(Map.of(
            "mensagem", "Juros aplicados com sucesso",
            "contasAtualizadas", updatedAccounts
        ));
    }

    // ==================== Métodos auxiliares ====================

    /**
     * Mapeia uma Account para um Map com seus dados
     */
    private Map<String, Object> mapAccountResponse(Account account) {
        Map<String, Object> response = new HashMap<>();
        response.put("numero", account.getNumber());
        response.put("saldo", account.getBalance());
        response.put("tipo", getTipoContaString(account));
        response.put("bonusPoints", account.getBonusPoints());
        return response;
    }

    /**
     * Converte flags de tipo de conta para string legível
     */
    private String getTipoContaString(Account account) {
        if (account.isBonusAccount()) {
            return "bonus";
        } else if (account.isSavingsAccount()) {
            return "poupanca";
        }
        return "normal";
    }
}

