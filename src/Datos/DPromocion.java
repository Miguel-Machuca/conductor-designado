/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Connection.sqlconnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utils.DateString;
import java.text.ParseException;
/**
 *
 * @author andre
 */
public class DPromocion {
     private final  sqlconnection connection;
     
     public DPromocion(){
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
       public void guardar(String nombre, String descripcion, float descuento, int idServicio, String fechaInicio, String fechaFin) throws SQLException, ParseException {
        String query = "INSERT INTO promocion (nombre, descripcion, descuento, fecha_inicio, fecha_fin, id_servicio) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, descripcion);
        ps.setFloat(3, descuento);
        ps.setDate(4, DateString.StringToDateSQL(fechaInicio) );
        ps.setDate(5, DateString.StringToDateSQL(fechaFin));
        ps.setInt(6, idServicio);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DPromocion.java: Ocurrió un error al insertar una promoción en guardar()");
        }
    }

    public void editar(int id, String nombre, String descripcion, float descuento, int idServicio, String fechaInicio, String fechaFin) throws SQLException, ParseException {
        String query = "UPDATE promocion SET nombre = ?, descripcion = ?, descuento = ?, fecha_inicio = ?, fecha_fin = ?, id_servicio = ? WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, descripcion);
        ps.setFloat(3, descuento);
         ps.setDate(4, DateString.StringToDateSQL(fechaInicio) );
        ps.setDate(5, DateString.StringToDateSQL(fechaFin));
        ps.setInt(6, idServicio);
        ps.setInt(7, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DPromocion.java: Ocurrió un error al editar una promoción en editar()");
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM promocion WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ninguna promoción con el ID especificado.");
        } else {
            System.out.println("Promoción eliminada exitosamente.");
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> promociones = new ArrayList<>();
        String query = "SELECT * FROM promocion";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] promocion = {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                String.valueOf(rs.getFloat("descuento")),
                rs.getString("fecha_inicio"),
                rs.getString("fecha_fin"),
                String.valueOf(rs.getInt("id_servicio"))
            };
            promociones.add(promocion);
        }

        return promociones;
    }

    public String[] ver(int id) throws SQLException {
        String[] promocion = null;
        String query = "SELECT * FROM promocion WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            promocion = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                String.valueOf(rs.getFloat("descuento")),
                rs.getString("fecha_inicio"),
                rs.getString("fecha_fin"),
                String.valueOf(rs.getInt("id_servicio"))
            };
        }

        return promocion;
    }
}
