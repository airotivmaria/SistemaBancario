package DTO;

public class ContaPoupanca extends Conta{
    public void transferirDaCorrente(ContaCorrente cc, double valor) {
        if (valor > 0 && cc.getSaldoConta() >= valor) {
            cc.sacar(valor);
            depositar(valor);
            System.out.println("Transferência de R$" + valor + " para a poupança realizada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente na conta corrente ou valor inválido.");
        }
    }
}
