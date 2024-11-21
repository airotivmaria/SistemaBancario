import DTO.ContaCorrente;
import DTO.ContaPoupanca;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ContaPoupanca cp = new ContaPoupanca();
        ContaCorrente cc = new ContaCorrente();

        cc.setSaldoConta(1000.0);
        cp.setSaldoConta(0.0);

        realizarOperacoes(cc, cp, scanner);

        scanner.close();
    }

    public static void realizarOperacoes(ContaCorrente cc, ContaPoupanca cp, Scanner scanner) {
        String opcao;
        do {
            menu();
            opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    realizarSaque(cc, scanner);
                    break;
                case "2":
                    realizarDeposito(cc, scanner);
                    break;
                case "3":
                    aplicarRendimento(cc);
                    break;
                case "4":
                    transferirParaPoupanca(cc, cp, scanner);
                    break;
                case "5":
                    exibirSaldo(cc, cp);
                    break;
                case "6":
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (!opcao.equals("6"));
    }

    public static void menu(){
        System.out.println("""
            Escolha uma operação:
            [1] Sacar
            [2] Depositar
            [3] Aplicar rendimento (Conta Corrente)
            [4] Transferir para poupança
            [5] Ver saldo
            [6] Sair
            """);
    }

    public static void realizarSaque(ContaCorrente conta, Scanner scanner) {
        System.out.print("Digite o valor para saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        conta.sacar(valor);
    }

    public static void realizarDeposito(ContaCorrente conta, Scanner scanner) {
        System.out.print("Digite o valor para depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        conta.depositar(valor);
    }
    public static void aplicarRendimento(ContaCorrente contaCorrente) {
        contaCorrente.aplicarRendimento();
    }
    public static void transferirParaPoupanca(ContaCorrente cc, ContaPoupanca cp, Scanner scanner) {
        System.out.print("Digite o valor para transferir para a poupança: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        cp.transferirParaPoupanca(cc, valor);
    }
    public static void exibirSaldo(ContaCorrente cc, ContaPoupanca cp) {
        System.out.println("Saldo Conta Corrente: R$" + cc.getSaldoConta());
        System.out.println("Saldo Conta Poupança: R$" + cp.getSaldoConta());
    }
}
