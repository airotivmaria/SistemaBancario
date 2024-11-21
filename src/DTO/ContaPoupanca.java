package DTO;

public class ContaPoupanca extends Conta {

    public void transferirParaPoupanca(ContaCorrente cc, double valor) {
        if (valor > 0 && valor <= cc.getSaldoConta()) {
            cc.sacar(valor);
            depositar(valor);
            System.out.println("Transferência de R$" + valor + " para a poupança realizada com sucesso!");
        } else {
            System.out.println("Saldo insuficiente na Conta Corrente ou valor inválido.");
        }
    }
}
