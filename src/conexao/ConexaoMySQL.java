 
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
      private static final String URL = "jdbc:mysql://localhost:3306/apontamento1";
        private static final String USUARIO = "root";
        private static final String SENHA = "9823";

        public static Connection obterConexao() throws SQLException {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        }
}
