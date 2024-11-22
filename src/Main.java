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
        //VALIDAÇÃO E CADASTRO DO USUÁRIO
        do {
            System.out.print("Nome do usuário: ");
            u.setNome(s.nextLine());
            if (u.getNome().length() > 0 && u.getNome().length() < 150) {
                break;
            } else {
                System.out.println("O nome deve ser completo.");
            }
        } while (true);

        do {
            System.out.print("Idade: ");
            u.setIdade(s.nextInt());
            s.nextLine();
            if (u.getIdade() >= 18 && u.getIdade() < 100){
                break;
            } else if (u.getIdade() < 18){
                System.out.println("Você não tem idade suficiente.");
                return;
            } else {
                System.out.println("Idade inválida.");
            }
        } while (true);

        do {
            System.out.print("CPF: ");
            u.setCpf(s.nextLine());
            if (u.getCpf().length() == 11){
                break;
            } else {
                System.out.println("O CPF teve ter 11 dígitos e não possuir símbolos.");
            }
        } while (true);

        do {
            System.out.print("Saldo inicial da conta: ");
            cc.setSaldoConta(s.nextDouble());
            s.nextLine();
            if (cc.getSaldoConta() >= 0){
                break;
            } else {
                System.out.println("O saldo não pode ser menor que 0.");
            }
        } while (true);

        do {
            System.out.print("Digite uma senha: ");
            u.setSenha(s.nextLine());
            if (u.getSenha().length() > 0){
                break;
            } else {
                System.out.println("A senha não pode ser em branco.");
            }
        } while (true);

        cc.setUsuario(u);
        cp.setUsuario(u);

        int numeroConta = random.nextInt(999) + 100;
        cc.setNumeroConta(numeroConta);
        cp.setNumeroConta(numeroConta);
        u.setConta(cc);

        //INSERINDO CONTA E USUÁRIO NO BANCO DE DADOS
        cDAO.cadastrarConta(cc);
        usuarioDAO.inserirUsuario(u);

        System.out.println("Cadastro realizado com sucesso!");
        System.out.println("=========================================================");
        System.out.println("Guarde o número da sua conta para o login: " + cc.getNumeroConta());
    }

    public static boolean validarLogin (Usuario u, Scanner s, UsuarioDAO uD, ContaCorrente cc, ContaPoupanca cp){
        try {
            System.out.print("Número da conta: ");
            cc.setNumeroConta(s.nextInt());
            s.nextLine();
            System.out.print("Digite uma senha: ");
            u.setSenha(s.nextLine());
            u.setConta(cc);

            //VALIDA SE O USUÁRIO ESTÁ INSERIDO DENTRO DO BANCO DE DADOS
            ResultSet rsUsuario = uD.verificarUsuario(u);


            if (rsUsuario.next()) { //SE TIVER UM 'next' É PQ ESTÁ INSERIDO
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
        cDao.salvarSaldo(cc, cp);
    }

    public static void menuOperacoes (Usuario usuario, ContaCorrente cc, ContaPoupanca cp, Scanner s, ContaDAO cDAO){
        boolean continuar = true;
        String input;
        while (continuar) {
            System.out.println("""
                        [1] Depositar na conta corrente
                        [2] Sacar da conta corrente
                        [3] Transferir para poupança
                        [4] Consultar saldo
                        [5] Aplicar rendimento
                        [6] Calcular Rendimento
                        [0] Sair
                        """);
            String opcao = s.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Valor para depósito: ");
                    input = s.nextLine();
                    if (input.matches("[0-9]+(\\.[0-9]+)?")) {
                        double valorDeposito = Double.parseDouble(input);
                        cc.depositar(valorDeposito);
                        System.out.println(cc.getSaldoConta());
                        cDAO.atualizarSaldoCorrente(cc);
                        salvarSaldo(cDAO, cc, cp);
                    } else {
                        System.out.println("Você deve digitar apenas números");
                    }
                    break;
                case "2":
                    System.out.print("Valor para saque: ");
                    input = s.nextLine();
                    if (input.matches("[0-9]+(\\.[0-9]+)?")) {
                        double valorSaque = Double.parseDouble(input);
                        cc.sacar(valorSaque);
                        cDAO.atualizarSaldoCorrente(cc);
                        salvarSaldo(cDAO, cc, cp);
                    } else {
                        System.out.println("Você deve digitar apenas números");
                    }
                    break;
                case "3":
                    System.out.print("Valor para transferir para poupança: ");
                    input = s.nextLine();
                    if (input.matches("[0-9]+(\\.[0-9]+)?")) {
                        double valorTransferencia = Double.parseDouble(input);
                        cp.transferirDaCorrente(cc, valorTransferencia);
                        cDAO.atualizarSaldoPoupanca(cp);
                        salvarSaldo(cDAO, cc, cp);
                    } else {
                        System.out.println("Você deve digitar apenas números");
                    }
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
                case "6":
                    System.out.print("Deseja calcular o rendimento para a Conta Corrente [CC] ou Conta Poupaça [CP]: ");
                    String conta = s.nextLine();

                    if(conta.toUpperCase().equals("CC")){
                        cc.calcularRendimentoContaCorrente();
                    } else if (conta.toUpperCase().equals("CP")) {
                        System.out.print("Quantidade de meses: ");
                        int meses = s.nextInt();
                        cp.calcularRendimentoContaPoupanca(meses);
                    } else {
                        System.out.println("Opção inválida.");
                        return;
                    }
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