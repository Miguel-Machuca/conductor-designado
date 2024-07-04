/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Connection.sqlconnection;
import Utils.DateString;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class DConductor {

    private final sqlconnection connection;

    public DConductor() {
        this.connection = new sqlconnection(
                "grupo04sc",
                "grup004grup004",
                "mail.tecnoweb.org.bo",
                "5432", "db_grupo04sc");
    }

    // cuando la connecion sea distinto de null se podra hacer la desconeccion a la base de datos 
    public void Disconnect() {
        if (connection != null) {
            connection.closeConnection();
        }
    }

    public void guardar(String nombre, String apellido, String correo, String password, String celular, String fechaNacimiento, String genero, String numeroLicencia, String tipoLicencia, String fechaVencimientoLicencia) throws SQLException, ParseException {
        // Insertar directamente en la tabla conductor con encriptación de contraseña
        String query = "INSERT INTO conductor (nombre, apellido, correo, password, celular, fecha_de_nacimiento, genero, tipo_de_licencia, numero_de_licencia_de_conducir, fecha_de_vencimiento_de_la_licencia, tipo_usuario, id_rol,  estado) "
                + "VALUES (?, ?, ?, crypt(?, gen_salt('bf')), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, correo);
        ps.setString(4, password);  // La contraseña será encriptada en la consulta SQL
        ps.setString(5, celular);
        ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(7, genero);
        ps.setString(8, tipoLicencia);
        ps.setString(9, numeroLicencia);
        ps.setDate(10, DateString.StringToDateSQL(fechaVencimientoLicencia));
        ps.setString(11, "conductor");  // Especificar tipo de usuario como 'conductor'
        ps.setInt(12, 2);
        ps.setString(13, "libre");// estado por defecto

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DConductor.java: "
                    + "Ocurrió un error al insertar un conductor en guardarConductor()");
            throw new SQLException();
        }
    }

    public void editar(int id, String nombre, String apellido, String correo, String password, String celular, String fechaNacimiento, String genero, String numeroLicencia, String tipoLicencia, String fechaVencimientoLicencia) throws SQLException, ParseException {
        // Construir la consulta SQL para actualizar los campos, incluyendo la encriptación de la contraseña si se proporciona
        String query = "UPDATE conductor SET nombre = ?, apellido = ?, correo = ?, celular = ?, fecha_de_nacimiento = ?, genero = ?, tipo_de_licencia = ?, numero_de_licencia_de_conducir = ?, "
                + "fecha_de_vencimiento_de_la_licencia = ?, estado = ? " + (password != null && !password.isEmpty() ? ", password = crypt(?, gen_salt('bf')) " : " ")
                + "WHERE id = ?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, correo);
        ps.setString(4, celular);
        ps.setDate(5, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(6, genero);
        ps.setString(7, tipoLicencia);
        ps.setString(8, numeroLicencia);
        ps.setDate(9, DateString.StringToDateSQL(fechaVencimientoLicencia));
        ps.setString(10, "inactivo");

        if (password != null && !password.isEmpty()) {
            ps.setString(11, password);
            ps.setInt(12, id);
        } else {
            ps.setInt(11, id);
        }

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DConductor.java: "
                    + "Ocurrió un error al editar un conductor en editarConductor()");
            throw new SQLException();
        }
    }

    public int buscarLibre() throws SQLException {
        String query = "SELECT id FROM conductor WHERE estado = 'libre' LIMIT 1";
        int idConductorLibre = -1; // Usar -1 para indicar que no se encontró un conductor libre

        try (Connection conn = connection.connect();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                idConductorLibre = rs.getInt("id");
            } else {
                System.err.println("Class DConductor.java: No se encontró ningún conductor con estado 'libre'");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al obtener el ID del conductor libre", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }

        return idConductorLibre;
    }

    public void actualizarEstado(int id, String nuevoEstado) throws SQLException {
        String query = "UPDATE conductor SET estado = ? WHERE id = ?";

        try (Connection conn = connection.connect();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DConductor.java: Ocurrió un error al actualizar el estado del conductor en actualizarEstadoConductor()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al actualizar el estado del conductor", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM conductor WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ningún conductor con el ID especificado.");
        } else {
            System.out.println("Conductor eliminado exitosamente.");
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> conductores = new ArrayList<>();
        String query = "SELECT * FROM conductor";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] conductor = {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("celular"),
                rs.getString("fecha_de_nacimiento"),
                rs.getString("genero"),
                rs.getString("numero_de_licencia_de_conducir"),
                rs.getString("tipo_de_licencia"),
                rs.getString("fecha_de_vencimiento_de_la_licencia")
            };
            conductores.add(conductor);
        }

        return conductores;
    }

    public String[] ver(int id) throws SQLException {
        String[] conductor = null;
        String query = "SELECT * FROM conductor WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            conductor = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("celular"),
                rs.getString("fecha_de_nacimiento"),
                rs.getString("genero"),
                rs.getString("numero_de_licencia_de_conducir"),
                rs.getString("tipo_de_licencia"),
                rs.getString("fecha_de_vencimiento_de_la_licencia")
            };
        }

        return conductor;
    }

    public int getIdByCorreo(String correo) throws SQLException {
        int id = -1;
        String query = "SELECT id FROM conductor WHERE correo=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, correo);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            id = set.getInt("id");
        }
        return id;
    }

    public String getCorreoById(int id) throws SQLException {
        String correo = null;
        String query = "SELECT correo FROM conductor WHERE id = ?";

        try (Connection conn = connection.connect();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    correo = rs.getString("correo");
                }
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al obtener el correo por ID", e);
            throw e;
        }
        return correo;
    }

    // En la clase DConductor
    public String[] obtenerDatosVehiculo(int idConductor) throws SQLException {
        String[] datosVehiculo = null;
        String query = "SELECT modelo, marca, placa FROM vehiculo WHERE id_conductor = ?";

        try (PreparedStatement ps = connection.connect().prepareStatement(query)) {
            ps.setInt(1, idConductor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    datosVehiculo = new String[]{
                        rs.getString("modelo"),
                        rs.getString("marca"),
                        rs.getString("placa")
                    };
                }
            }
        }
        return datosVehiculo;
    }

}
