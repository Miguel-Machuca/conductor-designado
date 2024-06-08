/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnowebproyectoemail;

import Connection.sqlconnection;

import Utils.Email;
import communication.MailVerificationThread;
import interfaces.IEmailEventListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class TecnoWebProyectoEmail {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        // Instanciar la clase sqlconnection
        sqlconnection sqlConn = new sqlconnection("postgres", "root", "127.0.0.1", "5432", "db_tecno");
        
        // Intentar conectar a la base de datos
        if (sqlConn.connect() == null) {
            System.err.println("No se pudo establecer la conexión a la base de datos.");
            return;
        }
        
        // Instanciar la clase DCliente con la conexión establecida
        DCliente dCliente = new DCliente();
        
        try {
            // Guardar un nuevo cliente (si es necesario)
           // dCliente.guardar("Juan", "Perez", "juan.perez@example.com", "password123", "123456789", "1990-01-01", "Masculino", "123456789");

            // Editar el cliente con id = 1 (asegúrate de que este id exista en la base de datos)
            //dCliente.editar(2, "Juan editado", "Perez", "juan.perez@example.com", "newpassword123", "987654321", "1990-01-01", "Masculino", "123456789");

            // Desconectar de la base de datos
            
            //dCliente.eliminar(2);
               // Obtener la lista de clientes
            List<String[]> clientes = dCliente.listar();
            
            // Mostrar los datos de los clientes
            for (String[] cliente : clientes) {
                System.out.println("ID: " + cliente[0]);
                System.out.println("Nombre: " + cliente[1]);
                System.out.println("Apellido: " + cliente[2]);
                System.out.println("Correo: " + cliente[3]);
                System.out.println("Celular: " + cliente[4]);
                System.out.println("Fecha de Nacimiento: " + cliente[5]);
                System.out.println("Género: " + cliente[6]);
                System.out.println("CI: " + cliente[7]);
                System.out.println("----------------------------------------");
            }
       
            
            dCliente.Disconnect();
            
            System.out.println("Prueba completada exitosamente.");
        } catch (SQLException e) {
     System.err.println("Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Ocurrió un error durante la prueba.");
        }  */
        
        /*
                
         DCliente dCliente = new DCliente();
        
        try {
            // ID del cliente que deseas ver
            int idCliente = 4 ;
            
            // Obtener los detalles del cliente
            String[] cliente = dCliente.ver(idCliente);
            
            // Verificar si se encontró el cliente y mostrar los detalles
            if (cliente != null) {
                System.out.println("Detalles del cliente con ID " + idCliente + ":");
                System.out.println("Nombre: " + cliente[1]);
                System.out.println("Apellido: " + cliente[2]);
                System.out.println("Correo: " + cliente[3]);
                System.out.println("Celular: " + cliente[4]);
                System.out.println("Fecha de Nacimiento: " + cliente[5]);
                System.out.println("Género: " + cliente[6]);
                System.out.println("CI: " + cliente[7]);
            } else {
                System.out.println("No se encontró ningún cliente con el ID " + idCliente);
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener los detalles del cliente: " + ex.getMessage());
        }
      */  
         MailVerificationThread mail = new MailVerificationThread();
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emails) {
                for (Email email : emails) {
                    System.out.println(email);
                    //interprete(email);
                }
            }
        });
        
        Thread thread = new Thread(mail);
        thread.setName("Mail Verification Thread");
        thread.start();
    }
    
    

}
