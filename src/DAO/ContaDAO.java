package DAO;

import DTO.ContaCorrente;
import DTO.ContaPoupanca;
import DTO.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class ContaDAO {
    Connection conn;
    PreparedStatement pstm; //recebe os dados

    public void cadastrarConta(ContaCorrente cc){
        String sql = "INSERT INTO conta(titular_conta, numero_conta, saldo) VALUES (?, ?, ?)";

        conn = new ConexaoDAO().conectaBD();
        Random random = new Random();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, cc.getUsuario().getNome());
            pstm.setInt(2, cc.getNumeroConta());
            pstm.setDouble(3, cc.getSaldoConta());

            pstm.execute();
            System.out.println("Usuário inserido com ID: " + cc.getNumeroConta());
            pstm.close();

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar conta " + erro);
        }
    }
}
