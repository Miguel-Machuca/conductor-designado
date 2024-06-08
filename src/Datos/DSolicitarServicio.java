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
import java.sql.Timestamp;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.DateString;
import java.sql.Statement;
import java.text.ParseException;


/**
 *
 * @author andre
 */
public class DSolicitarServicio {
     private final  sqlconnection connection;

    public DSolicitarServicio() {
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
    
    // solicitar servicio sin reserva
    public void solicitarServicio(String fechaSolicitudStr,float costo, String origen, String destino, int idCliente, int idServicio, Object idConductor) throws SQLException {
        String query = "INSERT INTO solicitar_servicio (fecha_solicitud, origen, destino, estado,tipo_servicio, id_cliente, id_servicio,costo_adicional, id_conductor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Convertir las cadenas de fecha a Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp fechaSolicitud = Timestamp.valueOf(LocalDateTime.parse(fechaSolicitudStr, formatter));
        // Timestamp fechaServicio = Timestamp.valueOf(LocalDateTime.parse(fechaServicioStr, formatter));
        
        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setTimestamp(1, fechaSolicitud);
            
            ps.setString(2, origen);
            ps.setString(3, destino);         // Asume que puede ser nulo
            ps.setString(4, "pendiente");// por defecto
            ps.setString(5, "sin reserva");// por defecto
            ps.setInt(6, idCliente);
            ps.setInt(7, idServicio);
            ps.setFloat(8, costo);
            ps.setObject(9, idConductor);    // Utiliza setObject para permitir valores nulos

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DSolicitarServicio.java: Ocurrió un error al insertar una solicitud de servicio en guardar()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al insertar la solicitud de servicio", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public void solicitarServicio(String fechaSolicitudStr, float costo, String origen, String destino, int idCliente, int idServicio) throws SQLException {        
        solicitarServicio(fechaSolicitudStr, costo, origen, destino, idCliente, idServicio, null);
    }
    
    public int idSolicitarServicio(String fechaSolicitudStr, float costo, String origen, String destino, int idCliente, int idServicio, Object idConductor) throws SQLException {
        String query = "INSERT INTO solicitar_servicio (fecha_solicitud, origen, destino, estado, tipo_servicio, id_cliente, id_servicio, costo_adicional, id_conductor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // Convertir las cadenas de fecha a Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp fechaSolicitud = Timestamp.valueOf(LocalDateTime.parse(fechaSolicitudStr, formatter));

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, fechaSolicitud);
            ps.setString(2, origen);
            ps.setString(3, destino);         // Asume que puede ser nulo
            ps.setString(4, "pendiente");     // por defecto
            ps.setString(5, "sin reserva");   // por defecto
            ps.setInt(6, idCliente);
            ps.setInt(7, idServicio);
            ps.setFloat(8, costo);
            ps.setObject(9, idConductor);     // Utiliza setObject para permitir valores nulos

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Ocurrió un error al insertar una solicitud de servicio, no se afectaron filas.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Ocurrió un error al insertar una solicitud de servicio, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al insertar la solicitud de servicio", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public void editarSolicitudDeServicio(int id,String fechaSolicitudStr,String origen, String destino, int idCliente, int idServicio) throws SQLException {
         String query = "UPDATE solicitar_servicio SET fecha_solicitud = ?, origen = ?, destino = ?, estado = ?, tipo_servicio = ?, id_cliente = ?, id_servicio = ? WHERE id = ?";
        // Convertir las cadenas de fecha a Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp fechaSolicitud = Timestamp.valueOf(LocalDateTime.parse(fechaSolicitudStr, formatter));
        // Timestamp fechaServicio = Timestamp.valueOf(LocalDateTime.parse(fechaServicioStr, formatter));
        
        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setTimestamp(1, fechaSolicitud);
            ps.setString(2, origen);
            ps.setString(3, destino);         
            ps.setString(4, "pendiente");// por defecto
            ps.setString(5, "sin reserva");// por defecto
            ps.setInt(6, idCliente);
            ps.setInt(7, idServicio);
            ps.setInt(8, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DSolicitarServicio.java: Ocurrió un error al editar una solicitud de servicio en guardar()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al insertar la solicitud de servicio", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public void reservarServicio(String fechaSolicitudStr,String fechaReserva, Float costoAdicional,String origen, String destino,int idCliente, int idServicio) throws SQLException, ParseException {

         String query = "INSERT INTO solicitar_servicio (fecha_solicitud, fecha_servicio, costo_adicional, origen, destino, estado, tipo_servicio, id_cliente, id_servicio) VALUES (?, ? ,?, ?, ?, ?, ?, ?,?)";
        
         // Convertir las cadenas de fecha a Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp fechaSolicitud = Timestamp.valueOf(LocalDateTime.parse(fechaSolicitudStr, formatter));
        try (Connection conn = connection.connect(); 
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setTimestamp(1, fechaSolicitud);
            ps.setTimestamp(2,DateString.StringToDatetimeSQL(fechaReserva) );
            ps.setObject(3, costoAdicional);  // Utiliza setObject para permitir valores nulos
            ps.setString(4, origen);
            ps.setString(5, destino);         // Asume que puede ser nulo
            ps.setString(6, "pendiente"); // ESTADO
            ps.setString(7,"con reserva");// TIPO
            ps.setInt(8, idCliente);
            ps.setInt(9, idServicio);
            

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DSolicitarServicio.java: Ocurrió un error al insertar una solicitud de servicio en guardar()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al insertar la solicitud de servicio", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
     
    public void actualizarEstadoSolicitudDeServicio(int id, String nuevoEstado) throws SQLException {
        String query = "UPDATE solicitar_servicio SET estado = ? WHERE id = ?";

        try (Connection conn = connection.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DSolicitarServicio.java: Ocurrió un error al actualizar el estado de la solicitud de servicio en actualizarEstadoSolicitudDeServicio()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al actualizar el estado de la solicitud de servicio", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public String obtenerSolicitudPorId(int idSolicitud) {
        String query = "SELECT * FROM solicitar_servicio WHERE id = ?";
        StringBuilder result = new StringBuilder();

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idSolicitud);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_solicitud");
                    Timestamp fechaSolicitud = rs.getTimestamp("fecha_solicitud");
                    String origen = rs.getString("origen");
                    String destino = rs.getString("destino");
                    String estado = rs.getString("estado");
                    String tipoServicio = rs.getString("tipo_servicio");
                    int idCliente = rs.getInt("id_cliente");
                    int idServicio = rs.getInt("id_servicio");
                    float costo = rs.getFloat("costo_adicional");
                    Object idConductor = rs.getObject("id_conductor");

                    result.append("ID Solicitud: ").append(id).append("\n");
                    result.append("Fecha Solicitud: ").append(fechaSolicitud).append("\n");
                    result.append("Origen: ").append(origen).append("\n");
                    result.append("Destino: ").append(destino).append("\n");
                    result.append("Estado: ").append(estado).append("\n");
                    result.append("Tipo Servicio: ").append(tipoServicio).append("\n");
                    result.append("ID Cliente: ").append(idCliente).append("\n");
                    result.append("ID Servicio: ").append(idServicio).append("\n");
                    result.append("Costo Adicional: ").append(costo).append("\n");
                    result.append("ID Conductor: ").append(idConductor).append("\n");
                } else {
                    result.append("No se encontró ninguna solicitud con el ID proporcionado.");
                }
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al obtener la solicitud de servicio por ID", e);
            result.append("Error al obtener la solicitud de servicio por ID: ").append(e.getMessage());
        }

        return result.toString();
    }
    
    public int obtenerIdConductor(int id) throws SQLException {
        String query = "SELECT id_conductor FROM solicitar_servicio WHERE id = ?";
        int idConductor = -1;

        try (Connection conn = connection.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idConductor = rs.getInt("id_conductor");
                } else {
                    System.err.println("No se encontró el id del conductor  ");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al obtener el ID del conductor", e);
            throw e;
        }

        return idConductor;
    }    
    
    
    /*
    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM solicitar_servicio WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ninguna solicitud de servicio con el ID especificado.");
        } else {
            System.out.println("Solicitud de servicio eliminada exitosamente.");
        }
    }

    
    public List<String[]> listar() throws SQLException {
        List<String[]> solicitudesDeServicio = new ArrayList<>();
        String query = "SELECT * FROM solicitar_servicio";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] solicitudDeServicio = {
                String.valueOf(rs.getInt("id")),
                String.valueOf(rs.getInt("id_cliente")),
                String.valueOf(rs.getInt("id_servicio"))
            };
            solicitudesDeServicio.add(solicitudDeServicio);
        }

        return solicitudesDeServicio;
    }

   
    public String[] ver(int id) throws SQLException {
        String[] solicitudDeServicio = null;
        String query = "SELECT * FROM solicitar_servicio WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            solicitudDeServicio = new String[]{
                String.valueOf(rs.getInt("id")),
                String.valueOf(rs.getInt("id_cliente")),
                String.valueOf(rs.getInt("id_servicio"))
            };
        }

        return solicitudDeServicio;
    }
*/



  
}
