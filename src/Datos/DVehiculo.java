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
public class DVehiculo {
    
     private final  sqlconnection connection;
     
     public DVehiculo(){
          this.connection = new sqlconnection(
                "grupo04sc",
                "grup004grup004", 
                "mail.tecnoweb.org.bo",
                "5432", "db_grupo04sc");
    }
    
     
      public void Disconnect(){
        if( connection!= null ){
            connection.closeConnection();
        }
    }
      
    public void guardar(String modelo, String marca, String placa, Integer numeroSeguro, String fechaVencimientoSeguro, String estado, int idConductor) throws SQLException, ParseException {
        String query = "INSERT INTO vehiculo (modelo, marca, placa, numero_de_seguro, fecha_vencimiento_seguro, estado, id_conductor) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, modelo);
        ps.setString(2, marca);
        ps.setString(3, placa);
        ps.setInt(4, numeroSeguro);
        ps.setDate(5, DateString.StringToDateSQL(fechaVencimientoSeguro));
        ps.setString(6, estado);
        ps.setInt(7, idConductor);

        if (ps.executeUpdate() == 0) {
            System.err.println("Ocurrió un error al insertar un vehículo en guardar()");
        }
    }
    public void editar(int id, String modelo, String marca, String placa, Integer numeroSeguro, String fechaVencimientoSeguro, String estado, int idConductor) throws SQLException, ParseException {
        // Construir la consulta SQL para actualizar los campos del vehículo
        String query = "UPDATE vehiculo SET modelo = ?, marca = ?, placa = ?, numero_de_seguro = ?, fecha_vencimiento_seguro = ?, estado = ?, id_conductor = ? WHERE id = ?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, modelo);
        ps.setString(2, marca);
        ps.setString(3, placa);
        ps.setInt(4, numeroSeguro);
        ps.setDate(5, DateString.StringToDateSQL(fechaVencimientoSeguro));
        ps.setString(6, estado);
        ps.setInt(7, idConductor);
        ps.setInt(8, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Ocurrió un error al editar un vehículo en editar()");
        }
    }
    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM vehiculo WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ningún vehículo con el ID especificado.");
        } else {
            System.out.println("Vehículo eliminado exitosamente.");
        }
    }
    public List<String[]> listar() throws SQLException {
    List<String[]> vehiculos = new ArrayList<>();
    String query = "SELECT * FROM vehiculo";
    PreparedStatement ps = connection.connect().prepareStatement(query);
    ResultSet rs = ps.executeQuery();
    
    while (rs.next()) {
        String[] vehiculo = {
            String.valueOf(rs.getInt("id")),
            rs.getString("modelo"),
            rs.getString("marca"),
            rs.getString("placa"),
            String.valueOf(rs.getInt("numero_de_seguro")),
            rs.getString("fecha_vencimiento_seguro"),
            rs.getString("estado"),
            String.valueOf(rs.getInt("id_conductor"))
        };
        vehiculos.add(vehiculo);
    }
    
    return vehiculos;
}
    public String[] ver(int id) throws SQLException {
        String[] vehiculo = null;
        String query = "SELECT * FROM vehiculo WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            vehiculo = new String[] {
                String.valueOf(rs.getInt("id")),
                rs.getString("modelo"),
                rs.getString("marca"),
                rs.getString("placa"),
                String.valueOf(rs.getInt("numero_de_seguro")),
                rs.getString("fecha_vencimiento_seguro"),
                rs.getString("estado"),
                String.valueOf(rs.getInt("id_conductor"))
            };
        }

        return vehiculo;
    }
}
