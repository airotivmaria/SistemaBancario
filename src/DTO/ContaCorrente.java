package DTO;

public class ContaCorrente extends Conta{
    public void aplicarRendimento() {
        double rendimento = (int) (getSaldoConta() / 100) * 0.05;
        if (rendimento > 0) {
            setSaldoConta(getSaldoConta() + rendimento);
            System.out.println("Rendimento aplicado: R$" + rendimento);
        } else {
            System.out.println("Saldo insuficiente para aplicar rendimento.");
        }
    }
}
