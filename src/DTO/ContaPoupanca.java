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

//    public void calcularRendimentoContaPoupanca(int meses) {
//        if (getSaldoConta() > 0) {
//            double rendimento = getSaldoConta() * 0.0005 * meses; // 0,05% por mês
//            getSaldoConta() += rendimento; // Adiciona o rendimento ao saldo
//            System.out.println("Rendimento de R$ " + rendimento + " aplicado na Conta Poupança por " + meses + " meses.");
//        } else {
//            System.out.println("Saldo insuficiente para rendimento na Conta Poupança.");
//        }
//    }


}
