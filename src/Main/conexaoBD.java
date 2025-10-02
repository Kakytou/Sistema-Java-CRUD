package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaoBD {

    public static Connection getConexao(){
        try{
            String url = "jdbc:mysql://127.0.0.1:3306/MeuBanco";
            String usuario = "root";
            String senha = "senha";

            return DriverManager.getConnection(url,usuario,senha);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }



}
