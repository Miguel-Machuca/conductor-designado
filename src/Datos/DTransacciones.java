/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Connection.sqlconnection;
import Utils.DateString;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class DTransacciones {
     private final  sqlconnection connection;

    public DTransacciones() {
        this.connection = new sqlconnection(
                "postgres",
                "admin", 
                "127.0.0.1",
                "5432", "db_tecno");
    }
    
    // cuando la connecion sea distinto de null se podra hacer la desconeccion a la base de datos 
    public void Disconnect(){
        if( connection!= null ){
            connection.closeConnection();
        }
    }
    
    public void guardar(int id_solicitar, String fecha, float monto, String estado, int id_metodo) throws SQLException, ParseException {
           String query = "INSERT INTO transacciones (id_solicitar, fecha, monto, estado, id_metodo) "
                   + "VALUES (?, ?, ?, ?, ?)";

           // Convertir la cadena de fecha a Timestamp
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
           Timestamp fechaTimestamp = Timestamp.valueOf(LocalDateTime.parse(fecha, formatter));

           try (Connection conn = connection.connect(); 
                PreparedStatement ps = conn.prepareStatement(query)) {

               ps.setInt(1, id_solicitar);
               ps.setTimestamp(2, fechaTimestamp);
               ps.setFloat(3, monto);
               ps.setString(4, estado);
               ps.setInt(5, id_metodo);

               if (ps.executeUpdate() == 0) {
                   System.err.println("Class DTransacciones.java dice: Ocurrió un error al insertar una transacción en guardar()");
               }
           } catch (SQLException e) {
               // Logging del error
               Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al insertar la transacción", e);
               // Relanza la excepción para que el llamador la maneje
               throw e;
           }
       }

    public void editar(int id, int id_solicitar, String fecha, float monto, String estado, int id_metodo) throws SQLException, ParseException {
        String query = "UPDATE transacciones SET id_solicitar = ?, fecha = ?, monto = ?, estado = ?, id_metodo = ? WHERE id = ?";

        // Convertir la cadena de fecha a Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp fechaTimestamp = Timestamp.valueOf(LocalDateTime.parse(fecha, formatter));

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id_solicitar);
            ps.setTimestamp(2, fechaTimestamp);
            ps.setFloat(3, monto);
            ps.setString(4, estado);
            ps.setInt(5, id_metodo);
            ps.setInt(6, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DTransacciones.java dice: Ocurrió un error al actualizar una transacción en editar()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al actualizar la transacción", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM transacciones WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DTransacciones.java dice: Ocurrió un error al eliminar una transacción en eliminar()");
        }
    }
    public List<String[]> listar() throws SQLException {
        List<String[]> transacciones = new ArrayList<>();
        String query = "SELECT * FROM transacciones";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] transaccion = {
                String.valueOf(rs.getInt("id")),
                rs.getTimestamp("fecha").toString(),
                String.valueOf(rs.getFloat("monto")),
                rs.getString("estado"),
                String.valueOf(rs.getInt("id_metodo"))
            };
            transacciones.add(transaccion);
        }

        return transacciones;
    }

    public String[] ver(int id) throws SQLException {
        String[] transaccion = null;
        String query = "SELECT * FROM transacciones WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            transaccion = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getTimestamp("fecha").toString(),
                String.valueOf(rs.getFloat("monto")),
                rs.getString("estado"),
                String.valueOf(rs.getInt("id_metodo"))
            };
        }

        return transaccion;
    }
}
