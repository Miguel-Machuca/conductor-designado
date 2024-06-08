package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class sqlconnection {
    
    private static final String DRIVER ="jdbc:postgresql://";
    private Connection connection;
    private String user;
    private String password;
    private String host;
    private String port;
    private String name; // nombre de la base de datos 
    private String url;

    public sqlconnection(String user, String password, String host, String port, String name) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.name = name;
        this.url = DRIVER + host + ":" + port + "/" + name; // 127.0.0.1:5432/tecno_bd
    }

    public Connection connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException ex) {
            System.out.println("Class sqlconnection.java: Ocurrió un error al momento de establecer una conexión connect()");
            ex.printStackTrace(); // Imprime más detalles sobre la excepción
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Class sqlconnection.java: Ocurrió un error al momento de cerrar la conexión closeConnection()");
            ex.printStackTrace();
        }
    }
}
