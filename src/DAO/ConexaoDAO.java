package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {

    //METODO PARA CONECTAR COM O BANCO DE DADOS, RETORNA UMA CONEXÃO COM UM BANCO JÁ EXISTENTE
    public Connection conectaBD (){
        Connection conn = null;

        try { //TENTAR A CONEXÃO
            Class.forName("com.mysql.cj.jdbc.Driver"); //DRIVER DE CONEXÃO

            String user = "root";
            String password = "123";
            String nomeBanco = "sistemabancario";
            String url = "jdbc:mysql://localhost:3306/"+ nomeBanco + "?user=" + user + "&password=" + password; //LINK DE CONEXÃO

            conn = DriverManager.getConnection(url); //ATRIBUI A CONEXÃO (conn) A URL DO BANCO DE DADOS

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado: " + e.getMessage());
        }  catch (SQLException erro) {
            System.out.println("Erro de conexão: " + erro.getMessage()); //exebi mensagem de erro
        }
        return conn;
    }
}
