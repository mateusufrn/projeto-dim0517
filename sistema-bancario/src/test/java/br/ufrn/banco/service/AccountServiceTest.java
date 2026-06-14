package br.ufrn.banco.service;

import br.ufrn.banco.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;

    @BeforeEach
    void setUp() {
        service = new AccountService();
    }

    // -------------------------------------------------------------------------
    // Cadastrar Conta
    // -------------------------------------------------------------------------

    @Test
    void cadastrarContaSimples_deveCriarContaComSaldoInicial() {
        Account conta = service.registerAccount(1, 500.0);

        assertNotNull(conta);
        assertEquals(1, conta.getNumber());
        assertEquals(500.0, conta.getBalance());
        assertFalse(conta.isBonusAccount());
        assertFalse(conta.isSavingsAccount());
    }

    @Test
    void cadastrarContaBonus_deveCriarContaComPontosIniciais() {
        Account conta = service.registerBonusAccount(2, 300.0);

        assertNotNull(conta);
        assertEquals(2, conta.getNumber());
        assertEquals(300.0, conta.getBalance());
        assertTrue(conta.isBonusAccount());
        assertEquals(10, conta.getBonusPoints());
    }

    @Test
    void cadastrarContaPoupanca_deveCriarContaCorretamente() {
        Account conta = service.registerSavingsAccount(3, 1000.0);

        assertNotNull(conta);
        assertEquals(3, conta.getNumber());
        assertEquals(1000.0, conta.getBalance());
        assertTrue(conta.isSavingsAccount());
    }

    // -------------------------------------------------------------------------
    // Consultar Conta
    // -------------------------------------------------------------------------

    @Test
    void consultarContaSimples_deveRetornarContaCorreta() {
        service.registerAccount(1, 500.0);

        Account conta = service.searchAccount(1);

        assertNotNull(conta);
        assertEquals(1, conta.getNumber());
        assertFalse(conta.isBonusAccount());
        assertFalse(conta.isSavingsAccount());
    }

    @Test
    void consultarContaBonus_deveRetornarContaComBonus() {
        service.registerBonusAccount(2, 300.0);

        Account conta = service.searchAccount(2);

        assertNotNull(conta);
        assertEquals(2, conta.getNumber());
        assertTrue(conta.isBonusAccount());
        assertEquals(10, conta.getBonusPoints());
    }

    @Test
    void consultarContaPoupanca_deveRetornarContaCorreta() {
        service.registerSavingsAccount(3, 1000.0);

        Account conta = service.searchAccount(3);

        assertNotNull(conta);
        assertEquals(3, conta.getNumber());
        assertTrue(conta.isSavingsAccount());
    }

    // -------------------------------------------------------------------------
    // Consultar Saldo
    // -------------------------------------------------------------------------

    @Test
    void consultarSaldo_deveRetornarSaldoCorreto() {
        service.registerAccount(1, 750.0);

        Account conta = service.searchAccount(1);

        assertEquals(750.0, conta.getBalance());
    }

    // -------------------------------------------------------------------------
    // Crédito
    // -------------------------------------------------------------------------

    @Test
    void credito_casoNormal_deveAumentarSaldo() {
        service.registerAccount(1, 100.0);

        boolean resultado = service.deposit(1, 200.0);

        assertTrue(resultado);
        assertEquals(300.0, service.searchAccount(1).getBalance());
    }

    @Test
    void credito_valorNegativo_naoDeveSerPermitido() {
        service.registerAccount(1, 100.0);

        boolean resultado = service.deposit(1, -50.0);

        assertFalse(resultado);
        assertEquals(100.0, service.searchAccount(1).getBalance());
    }

    @Test
    void credito_contaBonus_deveAdicionarPontosBonus() {
        service.registerBonusAccount(2, 0.0);

        service.deposit(2, 500.0);

        Account conta = service.searchAccount(2);
        // 10 pontos iniciais + (int)(500 / 100) = 10 + 5 = 15
        assertEquals(15, conta.getBonusPoints());
    }

    // -------------------------------------------------------------------------
    // Débito
    // -------------------------------------------------------------------------

    @Test
    void debito_casoNormal_deveReduzirSaldo() {
        service.registerAccount(1, 500.0);

        boolean resultado = service.withdraw(1, 200.0);

        assertTrue(resultado);
        assertEquals(300.0, service.searchAccount(1).getBalance());
    }

    @Test
    void debito_valorNegativo_naoDeveSerPermitido() {
        service.registerAccount(1, 500.0);

        boolean resultado = service.withdraw(1, -100.0);

        assertFalse(resultado);
        assertEquals(500.0, service.searchAccount(1).getBalance());
    }

    @Test
    void debito_saldoInsuficiente_naoDeveSerPermitido() {
        service.registerAccount(1, 100.0);

        boolean resultado = service.withdraw(1, 200.0);

        assertFalse(resultado);
        assertEquals(100.0, service.searchAccount(1).getBalance());
    }

    // -------------------------------------------------------------------------
    // Transferência
    // -------------------------------------------------------------------------

    @Test
    void transferencia_valorNegativo_naoDeveSerPermitido() {
        service.registerAccount(1, 500.0);
        service.registerAccount(2, 100.0);

        boolean resultado = service.transfer(1, 2, -100.0);

        assertFalse(resultado);
        assertEquals(500.0, service.searchAccount(1).getBalance());
        assertEquals(100.0, service.searchAccount(2).getBalance());
    }

    @Test
    void transferencia_saldoInsuficiente_naoDeveSerPermitido() {
        service.registerAccount(1, 100.0);
        service.registerAccount(2, 50.0);

        boolean resultado = service.transfer(1, 2, 200.0);

        assertFalse(resultado);
        assertEquals(100.0, service.searchAccount(1).getBalance());
        assertEquals(50.0, service.searchAccount(2).getBalance());
    }

    @Test
    void transferencia_contaBonusReceptora_deveAdicionarPontosBonus() {
        service.registerAccount(1, 1000.0);
        service.registerBonusAccount(2, 0.0);

        service.transfer(1, 2, 300.0);

        Account contaBonus = service.searchAccount(2);
        // 10 pontos iniciais + (int)(300 / 150) = 10 + 2 = 12
        assertEquals(12, contaBonus.getBonusPoints());
    }

    // -------------------------------------------------------------------------
    // Render Juros
    // -------------------------------------------------------------------------

    @Test
    void renderJuros_deveAplicarRendimentoParaTodasContasPoupanca() {
        service.registerSavingsAccount(1, 1000.0);
        service.registerSavingsAccount(2, 2000.0);
        service.registerAccount(3, 500.0);

        int contasAtualizadas = service.applyInterestToSavingsAccounts(10.0);

        assertEquals(2, contasAtualizadas);
        assertEquals(1100.0, service.searchAccount(1).getBalance());
        assertEquals(2200.0, service.searchAccount(2).getBalance());
        assertEquals(500.0, service.searchAccount(3).getBalance());
    }
}
