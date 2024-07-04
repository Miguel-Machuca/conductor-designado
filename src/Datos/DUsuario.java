/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import Connection.sqlconnection;
//import com.sun.javafx.scene.control.skin.VirtualFlow;
//import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utils.DateString;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
/**
 *
 * @author andre
 */
public class DUsuario {
   
    private final  sqlconnection connection;

    public DUsuario() {
       this.connection = new sqlconnection(
                "grupo04sc",
                "grup004grup004", 
                "mail.tecnoweb.org.bo",
                "5432", "db_grupo04sc");
    }
    
    // cuando la connecion sea distinto de null se podra hacer la desconeccion a la base de datos 
    public void Disconnect(){
        if( connection!= null ){
            connection.closeConnection();
        }
    }
    
    public void guardar(String nombre, String apellido, String correo, String contraseña, String celular, String fechaNacimiento, String genero) throws SQLException, ParseException {
        String query = "INSERT INTO usuario (nombre, apellido, correo, password, celular, fecha_de_nacimiento, genero, tipo_usuario) VALUES (?, ?, ?, crypt(?, gen_salt('bf')), ?, ?, ?, ?)";
       PreparedStatement ps = connection.connect().prepareStatement(query);
       ps.setString(1, nombre);
       ps.setString(2, apellido);
       ps.setString(3, correo);
       ps.setString(4, contraseña);
       ps.setString(5, celular);
       ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
       ps.setString(7, genero);
       ps.setString(8, ""); // Por defecto, establecemos que el nuevo usuario es un cliente

       if (ps.executeUpdate() == 0) {
           System.err.println("Class DUsuario.java: Ocurrió un error al insertar un usuario en guardar()");
       }
   }

    public void editar(int id, String nombre, String apellido, String correo, String nuevaContraseña ,String celular, String fechaNacimiento, String genero) throws SQLException, ParseException {
        String query;
        PreparedStatement ps;

        if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
            query = "UPDATE usuario SET nombre=?, apellido=?, correo=?, password=crypt(?, gen_salt('bf')), celular=?, fecha_de_nacimiento=?, genero=? WHERE id=?";
            ps = connection.connect().prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, nuevaContraseña);
            ps.setString(5, celular);
            ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
            ps.setString(7, genero);
            ps.setInt(8, id);
        } else {
            query = "UPDATE usuario SET nombre=?, apellido=?, correo=?, celular=?, fecha_de_nacimiento=?, genero=? WHERE id=?";
            ps = connection.connect().prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, celular);
            ps.setString(5, fechaNacimiento);
            ps.setString(6, genero);
            ps.setInt(7, id);
        }

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DUsuario.java: Ocurrió un error al editar un usuario en editar()");
            throw new SQLException();
        }
    }

    public void asignarRol(int id, int idRol) throws SQLException {
        String query = "UPDATE usuario SET id_rol = ? WHERE id = ?";

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, idRol);
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DUsuario.java dice: Ocurrió un error al asignar el rol al usuario en asignarRol()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al asignar el rol al usuario", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }

    public void eliminar(int id ) throws SQLException{
       String query = "delete from usuario where id=?"; 
       PreparedStatement ps = connection.connect().prepareStatement(query);
       ps.setInt(1, id);
       if (ps.executeUpdate()==0){
           System.err.println("Class DUsuario.java dice: "+
                    "Ocurrio un error al eliminar un usuario eliminar()");
            throw new SQLException();
       }
    }

    public List<String[]> listar() throws SQLException {
    List<String[]> usuarios = new ArrayList<>();
    String query = "SELECT id, nombre, apellido, correo, celular, fecha_de_nacimiento, genero, tipo_usuario FROM usuario";
    PreparedStatement ps = connection.connect().prepareStatement(query);
    ResultSet set = ps.executeQuery();
    while (set.next()) {            
        usuarios.add(new String[]{
            String.valueOf(set.getInt("id")),
            set.getString("nombre"),
            set.getString("apellido"),
            set.getString("correo"),
            set.getString("celular"),
            set.getString("fecha_de_nacimiento"),
            set.getString("genero"),
            set.getString("tipo_usuario")
        });
    }
    return usuarios;
}

    public String[] ver(int id) throws SQLException {
    String[] usuario = null;
    String query = "SELECT id, nombre, apellido, correo, celular, fecha_de_nacimiento, genero, tipo_usuario FROM usuario WHERE id=?";
    PreparedStatement ps = connection.connect().prepareStatement(query);
    ps.setInt(1, id);
    
    ResultSet set = ps.executeQuery();
    if (set.next()) {
        usuario = new String[]{
            String.valueOf(set.getInt("id")),
            set.getString("nombre"),
            set.getString("apellido"),
            set.getString("correo"),
            set.getString("celular"),
            set.getString("fecha_de_nacimiento"),
            set.getString("genero"),
            set.getString("tipo_usuario")
        };
    }
    
    return usuario;
}

    public int getIdByCorreo(String correo) throws SQLException{
        int id=-1;
        String query = "SELECT id FROM usuario WHERE correo=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1,correo);
        
        ResultSet set = ps.executeQuery();
        if (set.next()){
            id = set.getInt("id");
        }
        return id;
    }
    
    
    
    
    public int getIdRolByCorreo(String correo) throws SQLException{
        int id = -1;
        String query = "SELECT id_rol FROM usuario WHERE correo=? ";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, correo);
        
        ResultSet set = ps.executeQuery();
        while(set.next()){
            id = set.getInt("id_rol");
        }
        return id;
    }

}
