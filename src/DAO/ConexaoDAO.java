package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {
    public Connection conectaBD (){
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String user = "root";
            String password = "987093397";
            String url = "jdbc:mysql://localhost:3306/sistemabancario?user=" + user + "&password=" + password;

            conn = DriverManager.getConnection(url);
    } catch (ClassNotFoundException e) {
        System.out.println("Driver JDBC não encontrado: " + e.getMessage());
    }  catch (SQLException erro) {
            System.out.println("Erro de conexão: " + erro.getMessage()); //exebi mensagem de erro
        }

        return conn;
    }
}
