package DTO;

public class ContaCorrente extends Conta {

    public void aplicarRendimento() {
        double rendimento = (int) (getSaldoConta() / 100) * 0.05;
        setSaldoConta(getSaldoConta() + rendimento);
        System.out.println("Rendimento de R$" + rendimento + " aplicado com sucesso!");
    }
}
