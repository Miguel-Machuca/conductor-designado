/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Connection.sqlconnection;
import Utils.DateString;
import java.text.ParseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mmach
 */
public class DGastoOperativo {
    private final sqlconnection connection;
    
    public DGastoOperativo(){
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
    

    public void guardar(String monto, String fecha, String descripcion, int idPersonal) throws SQLException, ParseException {
        
        String query = "INSERT INTO gasto_operativo (monto, fecha, descripcion, id_personal) VALUES (?, ?, ?, ?)";

        // Validación de entradas
        if (Float.parseFloat(monto) < 0 || fecha == null || fecha.isEmpty()) {
            throw new IllegalArgumentException("Parámetros inválidos: monto, fecha y idPersonal son obligatorios y deben ser válidos.");
        }

         try (Connection conn = connection.connect(); 
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setFloat(1, Float.parseFloat(monto));
            ps.setDate(2, DateString.StringToDateSQL(fecha));
            ps.setString(3, descripcion);
            ps.setInt(4, idPersonal);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el registro en gasto_operativo guardar()");
            }
        } catch (SQLException | ParseException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al guardar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public void editar(int id, float monto, String fecha, String descripcion, int idPersonal) throws SQLException, ParseException {
        String query = "UPDATE gasto_operativo SET monto = ?, fecha = ?, descripcion = ?, id_personal = ? WHERE id = ?";

        // Validación de entradas
        if (id <= 0 || monto < 0 || fecha == null || fecha.isEmpty() || idPersonal <= 0) {
            throw new IllegalArgumentException("Parámetros inválidos: id, monto, fecha y idPersonal son obligatorios y deben ser válidos.");
        }

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setFloat(1, monto);
            ps.setDate(2, DateString.StringToDateSQL(fecha));
            ps.setString(3, descripcion);
            ps.setInt(4, idPersonal);
            ps.setInt(5, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el registro en gasto_operativo editar()");
            }
        } catch (SQLException | ParseException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al actualizar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM gasto_operativo WHERE id = ?";

        // Validación de entradas
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que 0.");
        }

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo eliminar el registro en gasto_operativo");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al eliminar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public String[] ver(int id) throws SQLException {
        String[] gastoOperativo = null;
        String query = "SELECT * FROM gasto_operativo WHERE id = ?";

        // Validación de entradas
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que 0.");
        }

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gastoOperativo = new String[]{
                        String.valueOf(rs.getInt("id")),
                        String.valueOf(rs.getFloat("monto")),
                        String.valueOf(rs.getDate("fecha")),
                        rs.getString("descripcion"),
                        String.valueOf(rs.getInt("id_personal"))
                    };
                }
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al consultar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }

        return gastoOperativo;
    }
    
    public List<String[]> listar() throws SQLException {
        List<String[]> gastosOperativos = new ArrayList<>();
        String query = "SELECT * FROM gasto_operativo";
        
        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String[] gastoOperativo = new String[]{
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getFloat("monto")),
                    String.valueOf(rs.getDate("fecha")),
                    rs.getString("descripcion"),
                    String.valueOf(rs.getInt("id_personal"))
                };
                gastosOperativos.add(gastoOperativo);
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al listar los gastos operativos", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }

        return gastosOperativos;
    }

    

}
