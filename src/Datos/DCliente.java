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
import java.sql.Connection;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class DCliente {

    private final sqlconnection connection;

    public DCliente() {
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

    public int guardar(String nombre, String apellido, String correo, String password, String celular, String fechaNacimiento, String genero, String ci) throws SQLException, ParseException {
        // Insertar directamente en la tabla cliente con encriptación de contraseña
        String query = "INSERT INTO cliente (nombre, apellido, correo, password, celular, fecha_de_nacimiento, genero, tipo_usuario, ci, id_rol) "
                + "VALUES (?, ?, ?, crypt(?, gen_salt('bf')), ?, ?, ?, ?, ?, ?) RETURNING id";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, correo);
        ps.setString(4, password);  // La contraseña será encriptada en la consulta SQL
        ps.setString(5, celular);
        ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(7, genero);
        ps.setString(8, "cliente");  // Especificar tipo de usuario como 'cliente'
        ps.setString(9, ci);
        ps.setInt(10, 3);

        ResultSet rs = ps.executeQuery();  // Ejecutar la consulta y obtener el ResultSet con el id generado

        if (rs.next()) {
            int id = rs.getInt("id");  // Obtener el id generado
            return id;  // Retornar el id
        } else {
            throw new SQLException("Class DCliente.java: Ocurrió un error al insertar un cliente en guardar() - No ID obtained.");
        }
    }

    public void editar(int id, String nombre, String apellido, String correo, String password, String celular, String fechaNacimiento, String genero, String ci) throws SQLException, ParseException {
        // Construir la consulta SQL para actualizar los campos, incluyendo la encriptación de la contraseña si se proporciona
        String query = "UPDATE cliente SET nombre = ?, apellido = ?, correo = ?, celular = ?, fecha_de_nacimiento = ?, genero = ?, ci = ?"
                + (password != null && !password.isEmpty() ? ", password = crypt(?, gen_salt('bf')) " : " ")
                + "WHERE id = ?";

        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, correo);
        ps.setString(4, celular);
        ps.setDate(5, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(6, genero);
        ps.setString(7, ci);

        if (password != null && !password.isEmpty()) {
            ps.setString(8, password);
            ps.setInt(9, id);
        } else {
            ps.setInt(8, id);
        }

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DCliente.java: Ocurrió un error al editar un cliente en editar()");
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM cliente WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ningún cliente con el ID especificado.");
        } else {
            System.out.println("Cliente eliminado exitosamente.");
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> clientes = new ArrayList<>();
        String query = "SELECT * FROM cliente";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] cliente = {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("celular"),
                rs.getString("fecha_de_nacimiento"),
                rs.getString("genero"),
                rs.getString("ci")
            };
            clientes.add(cliente);
        }

        return clientes;
    }

    public String[] ver(int id) throws SQLException {
        String[] cliente = null;
        String query = "SELECT * FROM cliente WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            cliente = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("celular"),
                rs.getString("fecha_de_nacimiento"),
                rs.getString("genero"),
                rs.getString("ci")
            };
        }

        return cliente;
    }

    public int getIdByCorreo(String correo) throws SQLException {
        int id = -1;
        String query = "SELECT id FROM cliente WHERE correo=?";
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
        String query = "SELECT correo FROM cliente WHERE id = ?";

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

}
