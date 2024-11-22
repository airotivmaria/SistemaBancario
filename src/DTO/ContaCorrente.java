package DTO;

//HERDA INFORMAÇÕES DA CLASSE CONTA
public class ContaCorrente extends Conta{

    public void aplicarRendimento() {
        double rendimento = (int) (getSaldoConta() / 100) * 0.05;
        setSaldoConta(getSaldoConta() + rendimento);
        System.out.println("Rendimento de R$" + rendimento + " aplicado com sucesso!");
    }

    public void calcularRendimentoContaCorrente() {
        if (getSaldoConta() >= 100) {
            double rendimento = (getSaldoConta() / 100) * 0.05; // 0,05% para cada 100 reais
            System.out.println("O saldo da conta vai render R$ " + rendimento);
        } else {
            System.out.println("Saldo insuficiente para rendimento na Conta Corrente.");
        }
    }
}
