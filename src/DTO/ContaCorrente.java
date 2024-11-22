package DTO;

public class ContaCorrente extends Conta{

    public void aplicarRendimento() {
        double rendimento = (int) (getSaldoConta() / 100) * 0.05;
        setSaldoConta(getSaldoConta() + rendimento);
        System.out.println("Rendimento de R$" + rendimento + " aplicado com sucesso!");
    }

//    public void calcularRendimentoContaCorrente() {
//        if (getSaldoConta() >= 100) { // Só aplica rendimento se o saldo for >= 100
//            double rendimento = (getSaldoConta() / 100) * 0.05; // 0,05% para cada 100 reais
//            getSaldoConta() += rendimento; // Adiciona o rendimento ao saldo
//            System.out.println("Rendimento de R$ " + rendimento + " aplicado na Conta Corrente.");
//        } else {
//            System.out.println("Saldo insuficiente para rendimento na Conta Corrente.");
//        }
//    }
}
