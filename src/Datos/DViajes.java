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

/**
 *
 * @author andre
 */
public class DViajes {
       private final  sqlconnection connection;
     
     public DViajes(){
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
      
 public void guardar(int idConductor, int idSolicitud, String fecha, String origen, String destinos, float costo, String estado) throws SQLException, ParseException {
        String query = "INSERT INTO viajes (id_conductor, id_solicitud, fecha, origen, destinos, costo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, idConductor);
        ps.setInt(2, idSolicitud);
        ps.setTimestamp(3, DateString.StringToDatetimeSQL(fecha));
        ps.setString(4, origen);
        ps.setString(5, destinos);
        ps.setFloat(6, costo);
        ps.setString(7, estado);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DViajes.java: Ocurrió un error al insertar un viaje en guardar()");
            throw new SQLException();
        }
    }

    // Método para editar un viaje existente
    public void editar(int id, int idConductor, int idSolicitud, String fecha, String origen, String destinos, float costo, String estado) throws SQLException, ParseException {
        String query = "UPDATE viajes SET id_conductor = ?, id_solicitud = ?, fecha = ?, origen = ?, destinos = ?, costo = ?, estado = ? WHERE id = ?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, idConductor);
        ps.setInt(2, idSolicitud);
        ps.setTimestamp(3, DateString.StringToDatetimeSQL(fecha));
        ps.setString(4, origen);
        ps.setString(5, destinos);
        ps.setFloat(6, costo);
        ps.setString(7, estado);
        ps.setInt(8, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DViajes.java: Ocurrió un error al editar un viaje en editar()");
            throw new SQLException();
        }
    }

    // Método para eliminar un viaje
    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM viajes WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DViajes.java: Ocurrió un error al eliminar un viaje en eliminar()");
            throw new SQLException();
        }
    }

    // Método para listar todos los viajes
    public List<String[]> listar() throws SQLException {
        List<String[]> viajes = new ArrayList<>();
        String query = "SELECT * FROM viajes";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] viaje = {
                String.valueOf(rs.getInt("id")),
                String.valueOf(rs.getInt("id_conductor")),
                String.valueOf(rs.getInt("id_solicitud")),
                rs.getTimestamp("fecha").toString(),
                rs.getString("origen"),
                rs.getString("destinos"),
                String.valueOf(rs.getFloat("costo")),
                rs.getString("estado")
            };
            viajes.add(viaje);
        }

        return viajes;
    }

    // Método para ver un viaje específico por su ID
    public String[] ver(int id) throws SQLException {
        String[] viaje = null;
        String query = "SELECT * FROM viajes WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            viaje = new String[] {
                String.valueOf(rs.getInt("id")),
                String.valueOf(rs.getInt("id_conductor")),
                String.valueOf(rs.getInt("id_solicitud")),
                rs.getTimestamp("fecha").toString(),
                rs.getString("origen"),
                rs.getString("destinos"),
                String.valueOf(rs.getFloat("costo")),
                rs.getString("estado")
            };
        } else {
            System.err.println("Class DViajes.java: No se encontró un viaje con el ID especificado en ver()");
            throw new SQLException();
        }

        return viaje;
    }
}
