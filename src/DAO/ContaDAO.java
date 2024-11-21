package DAO;

import DTO.ContaCorrente;
import DTO.ContaPoupanca;

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

    public boolean salvarSaldo(ContaCorrente cc, ContaPoupanca cp){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "SELECT saldo, saldo_poupanca FROM conta WHERE numero_conta = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, cc.getNumeroConta());

            ResultSet rs = pstm.executeQuery();

            if(rs.next()) {
                cc.setSaldoConta(rs.getDouble("saldo"));
                cp.setSaldoConta(rs.getDouble("saldo_poupanca"));
                return true;
            } else {
                System.out.println("Erro ao salvar saldo.");
                return false;
            }

        } catch (SQLException erro){
            System.out.println("Erro em salvar saldo " + erro);
            return false;
        }
    }

    public void atualizarSaldoCorrente(ContaCorrente cc){
        String sql = "UPDATE conta SET saldo = ? where numero_conta = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, cc.getSaldoConta());
            pstm.setInt(2, cc.getNumeroConta());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar saldo de saque " + erro);
        }
    }

    public void atualizarSaldoPoupanca (ContaPoupanca cp){
        String sql = "UPDATE conta SET saldo_poupanca = ? where numero_conta = ?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setDouble(1, cp.getSaldoConta());
            pstm.setInt(2, cp.getNumeroConta());

            pstm.execute();
            pstm.close();

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar saldo da Poupança " + erro);
        }
    }
}
