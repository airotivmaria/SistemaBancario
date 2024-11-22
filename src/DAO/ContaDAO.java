package DAO;

import DTO.ContaCorrente;
import DTO.ContaPoupanca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaDAO {
    Connection conn;
    PreparedStatement pstm; //recebe os dados

    //METODO PARA CADASTRAR/POVOAR AS TABELAS DA COLUNA 'CONTA' DO BANCO DE DADOS
    public void cadastrarConta(ContaCorrente cc){
        String sql = "INSERT INTO conta(titular_conta, numero_conta, saldo) VALUES (?, ?, ?)"; //COMANDO SQL

        conn = new ConexaoDAO().conectaBD(); //CHAMA O METÓDO DA CLASSE DE CONEXÃO

        try {
            pstm = conn.prepareStatement(sql); //ENVIA E PREPARA O COMANDO SQL
            pstm.setString(1, cc.getUsuario().getNome()); //SETA AS INFORMAÇÕES DOS '???'
            pstm.setInt(2, cc.getNumeroConta());
            pstm.setDouble(3, cc.getSaldoConta());

            pstm.execute(); //EXECUTA A QUERY
            pstm.close(); //FECHA A QUERY

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar conta " + erro);
        }
    }

    //METODO PARA PERSISTIR OS DADOS DE SALDOS DA CONTA
    public boolean salvarSaldo(ContaCorrente cc, ContaPoupanca cp){
        conn = new ConexaoDAO().conectaBD();

        try {
            String sql = "SELECT saldo, saldo_poupanca FROM conta WHERE numero_conta = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, cc.getNumeroConta());

            ResultSet rs = pstm.executeQuery(); //VAI RESULTAR EM VERDADEIRO OU FALSO

            if(rs.next()) { //SE VERDADEIRO, VAI TER NEXT
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

    //METODO PARA ATUALIZAR O SALDO DA CONTA CORRENTE APÓS OPERAÇÃO COM ESSA INFORMAÇÃO
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

    //METODO PARA ATUALIZAR O SALDO DA CONTA POUPANÇA APÓS OPERAÇÃO COM ESSA INFORMAÇÃO
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
