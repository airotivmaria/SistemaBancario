import DAO.ContaDAO;
import DAO.UsuarioDAO;
import DTO.ContaCorrente;
import DTO.ContaPoupanca;
import DTO.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ContaPoupanca cp = new ContaPoupanca();
        ContaCorrente cc = new ContaCorrente();
        ContaDAO contaDAO = new ContaDAO();
        Random random = new Random();

        boolean continuar = true;

        while (continuar) {
            menu();
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarUsuario(usuario, scanner, usuarioDAO, cc, cp, contaDAO, random);
                    break;
                case "2":
                    if (validarLogin(usuario, scanner, usuarioDAO, cc, cp)) {
                        menuOperacoes(usuario, cc, cp, scanner, contaDAO);
                    }
                    break;
                case "0":
                    continuar = false;
                    break;
                default:
                    System.out.println("Essa opção não é válida");
            }
        }
    }

    public static void menu() {
        System.out.println("""
                    [1] Primeiro acesso
                    [2] Login
                    [0] Sair
                    """);
    }

    public static void cadastrarUsuario(Usuario u, Scanner s, UsuarioDAO usuarioDAO, ContaCorrente cc, ContaPoupanca cp, ContaDAO
            cDAO, Random random){
        System.out.print("Nome do usuário: ");
        u.setNome(s.nextLine());
        System.out.print("Idade: ");
        u.setIdade(s.nextInt());
        s.nextLine();
        System.out.print("CPF: ");
        u.setCpf(s.nextLine());
        System.out.print("Saldo inicial da conta: ");
        cc.setSaldoConta(s.nextDouble());
        s.nextLine();
        System.out.print("Digite uma senha: ");
        u.setSenha(s.nextLine());
        cc.setUsuario(u);
        cp.setUsuario(u);

        int numeroConta = random.nextInt(999) + 100;
        cc.setNumeroConta(numeroConta);
        cp.setNumeroConta(numeroConta);
        u.setConta(cc);

        cDAO.cadastrarConta(cc);
        usuarioDAO.inserirUsuario(u);

        System.out.println("Cadastro realizado com sucesso!");
        System.out.println("Número da sua conta: " + cc.getNumeroConta());
    }

    public static boolean validarLogin (Usuario u, Scanner s, UsuarioDAO uD, ContaCorrente cc, ContaPoupanca cp){
        try {
            System.out.print("Número da conta: ");
            cc.setNumeroConta(s.nextInt());
            s.nextLine();
            System.out.print("Digite uma senha: ");
            u.setSenha(s.nextLine());
            u.setConta(cc);

            ResultSet rsUsuario = uD.verificarUsuario(u);

            if (rsUsuario.next()) {
                System.out.println("Login realizado com sucesso!");
                return true;
            } else {
                //mensagem de usuario não cadastrado ou usuario ou senha errados
                System.out.println("Usuário não cadastrado ou senha incorreta");
            }
            return false;
        } catch (SQLException erro) {
            System.out.println("Erro ao validar usuario: " + erro);
            return false;
        }
    }

    public static void salvarSaldo(ContaDAO cDao, ContaCorrente cc, ContaPoupanca cp){
        boolean salvo = cDao.salvarSaldo(cc, cp);
    }


    public static void menuOperacoes (Usuario usuario, ContaCorrente cc, ContaPoupanca cp, Scanner s, ContaDAO cDAO){
        boolean continuar = true;
        while (continuar) {
            System.out.println("""
                        [1] Depositar na conta corrente
                        [2] Sacar da conta corrente
                        [3] Transferir para poupança
                        [4] Consultar saldo
                        [5] Aplicar rendimento
                        [0] Sair
                        """);
            String opcao = s.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Valor para depósito: ");
                    double valorDeposito = s.nextDouble();
                    s.nextLine();
                    cc.depositar(valorDeposito);
                    System.out.println(cc.getSaldoConta());
                    cDAO.atualizarSaldoCorrente(cc);
                    salvarSaldo(cDAO, cc, cp);
                    break;
                case "2":
                    System.out.print("Valor para saque: ");
                    double valorSaque = s.nextDouble();
                    s.nextLine();
                    cc.sacar(valorSaque);
                    cDAO.atualizarSaldoCorrente(cc);
                    salvarSaldo(cDAO, cc, cp);
                    break;
                case "3":
                    System.out.print("Valor para transferir para poupança: ");
                    double valorTransferencia = s.nextDouble();
                    s.nextLine();
                    cp.transferirDaCorrente(cc, valorTransferencia);
                    cDAO.atualizarSaldoPoupanca(cp);
                    salvarSaldo(cDAO, cc, cp);
                    break;
                case "4":
                    System.out.println("Saldo conta corrente: R$" + cc.getSaldoConta());
                    System.out.println("Saldo poupança: R$" + cp.getSaldoConta());
                    break;
                case "5":
                    cc.aplicarRendimento();
                    cDAO.atualizarSaldoCorrente(cc);
                    salvarSaldo(cDAO, cc, cp);
                    break;
                case "0":
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}