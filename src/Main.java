import DAO.ContaDAO;
import DAO.UsuarioDAO;
import DTO.ContaCorrente;
import DTO.ContaPoupança;
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
        ContaPoupança cp = new ContaPoupança();
        ContaCorrente cc = new ContaCorrente();
        ContaDAO contaDAO = new ContaDAO();
        Random random = new Random();

        menu();
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                cadastrarUsuario(usuario, scanner, usuarioDAO, cc, contaDAO, random);
                break;
            case "2":
                validarLogin(usuario, scanner, usuarioDAO, cc);
            default:
                return;
        }

    }

    public static void menu(){
        System.out.println("""
                [1] Primeiro acesso
                [2] Login
                """);
    }

    public static void cadastrarUsuario(Usuario u, Scanner s, UsuarioDAO usuarioDAO, ContaCorrente cc, ContaDAO cDAO, Random random){
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

        int numeroConta = random.nextInt(999) + 100;
        cc.setNumeroConta(numeroConta);
        u.setConta(cc);

        cDAO.cadastrarConta(cc);
        usuarioDAO.inserirUsuario(u);

        System.out.print("O número da sua conta: " + cc.getNumeroConta());
    }

    public static void validarLogin(Usuario u, Scanner s, UsuarioDAO uD, ContaCorrente cc){
        try {
            System.out.print("Número da conta: ");
            cc.setNumeroConta(s.nextInt());
            u.setConta(cc);
            System.out.print("Digite uma senha: ");
            u.setSenha(s.nextLine());

            ResultSet rsUsuario = uD.verificarUsuario(u);

            if(rsUsuario.next()){
                System.out.println("Entrou na conta.");
            } else {
            //mensagem de usuario não cadastrado ou usuario ou senha errados
            System.out.println("Usuário não cadastrado ou incorreto. ");}
        } catch (SQLException erro) {
            System.out.println("Erro ao validar usuario " + erro);
        }
    }
}