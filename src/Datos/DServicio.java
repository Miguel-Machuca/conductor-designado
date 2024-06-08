/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Connection.sqlconnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Utils.DateString;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author andre
 */
public class DServicio {
     private final  sqlconnection connection;
     
     public DServicio(){
          this.connection = new sqlconnection(
                "postgres",
                "admin", 
                "127.0.0.1",
                "5432", "db_tecno");
     }
     
      public void Disconnect(){
        if( connection!= null ){
            connection.closeConnection();
        }
    }
       public void guardar(String nombre, String descripcion) throws SQLException {
        String query = "INSERT INTO servicio (nombre, descripcion) VALUES (?, ?)";

        try (PreparedStatement ps = connection.connect().prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No se pudo insertar el servicio en la base de datos.");
            } else {
                System.out.println("Servicio insertado correctamente.");
            }
        } catch (SQLException ex) {
            System.err.println("Error al guardar el servicio: " + ex.getMessage());
        }
    }
        public void editar(int id, String nombre, String descripcion) throws SQLException {
        String query = "UPDATE servicio SET nombre = ?, descripcion = ? WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, descripcion);
        ps.setInt(3, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error al editar el servicio con ID: " + id);
        }
    }
        public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM servicio WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("No se encontró ningún servicio con el ID especificado.");
        } else {
            System.out.println("Servicio eliminado exitosamente.");
        }
    }
        
    public List<String[]> listar() throws SQLException {
        List<String[]> servicios = new ArrayList<>();
        String query = "SELECT * FROM servicio";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] servicio = {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion")
            };
            servicios.add(servicio);
        }

        return servicios;
    }
    public String[] ver(int id) throws SQLException {
        String[] servicio = null;
        String query = "SELECT * FROM servicio WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            servicio = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion")
            };
        }

        return servicio;
    }
    public boolean existeServicio(int id) throws SQLException {
    boolean existe = false;
    String query = "SELECT COUNT(*) AS count FROM servicio WHERE id = ?";
    try (PreparedStatement ps = connection.connect().prepareStatement(query)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt("count");
            existe = count > 0;
        }
    } catch (SQLException ex) {
        System.err.println("Error al verificar la existencia del servicio con ID " + id + ": " + ex.getMessage());
        throw ex; // Relanza la excepción para que el llamador pueda manejarla
    }
    return existe;
}

}
