package DAO;

import DTO.ContaCorrente;
import DTO.ContaPoupanca;
import DTO.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    Connection conn;
    PreparedStatement pstm; //recebe os dados

    //METODO PARA CADASTRAR/POVOAR AS TABELAS DA COLUNA 'USUARIO'
    public void inserirUsuario(Usuario u){
        String sql = "INSERT INTO usuario(nome_usuario, idade, cpf_usuario, senha_usuario, numero_conta) VALUES (?, ?, ?, ?, ?)"; //COMANDO SQL

        conn = new ConexaoDAO().conectaBD(); //CHAMA O METODO DA CONEXÃO COM BD

        try {
            pstm = conn.prepareStatement(sql); //PREPARA O COMANDO
            pstm.setString(1, u.getNome()); //SUBSTITUIÇÃO DOS '?????' COM AS INFOS DO USUARIO
            pstm.setInt(2, u.getIdade());
            pstm.setString(3, u.getCpf());
            pstm.setString(4, u.getSenha());
            pstm.setInt(5, u.getConta().getNumeroConta());

            pstm.execute(); //EXECUTA A QUERY
            pstm.close(); //FECHA A QUERY

        } catch (SQLException erro) {
            System.out.println("Erro de conexão: " + erro.getMessage());
        }

    }

    //METODO PARA PERSISTIR AS INFORMAÇÕES NECESSÁRIAS PARA O LOGIN - Nº CONTA E SENHA, RETORNA UM RESULTADO
    public ResultSet verificarUsuario(Usuario u){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "SELECT * FROM usuario WHERE numero_conta = ? AND senha_usuario = ?";

            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, u.getConta().getNumeroConta());
            pstm.setString(2, u.getSenha());

            ResultSet rs = pstm.executeQuery(); //executando a consulta sql com os valor de nome e senha definidos
            return rs;

        } catch (SQLException erro){
            System.out.println("Erro de validação do Usuário " + erro);
            return null;
        }
    }
}
