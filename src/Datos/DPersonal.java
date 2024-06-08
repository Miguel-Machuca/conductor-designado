/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author mmach
 */
public class DPersonal {
    private final  sqlconnection connection;
    
    public DPersonal(){
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
    
    public void guardar(String nombre, String apellido, String correo, String password, String celular, String fechaNacimiento, String genero, String salario, String cargo, String estado) throws SQLException, ParseException{
        // Insertar directamente en la tabla personal con encriptación de contraseña
        String query = "INSERT INTO personal (nombre, apellido, correo, password, celular, fecha_de_nacimiento, genero, salario, cargo, estado, tipo_usuario, id_rol) "
                + "VALUES (?, ?, ?, crypt(?, gen_salt('bf')), ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, correo);
        ps.setString(4, password);  // La contraseña será encriptada en la consulta SQL
        ps.setString(5, celular);
        ps.setDate(6, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(7, genero);
        ps.setFloat(8, Float.parseFloat(salario));
        ps.setString(9, cargo);
        ps.setString(10, estado);
        ps.setString(11, "personal");
        ps.setInt(12, 1);
        
        
        if (ps.executeUpdate() == 0){
            System.err.println("Class DPersonal.java: "
                    + "Ocurrió un error al insertar un personal en guardarPersonal()");
            throw new SQLException();            
        }
    }
    
    public void editar(int id, String nombre, String apellido, String correo, String password, String celular, String fechaNacimiento, String genero, String salario, String cargo, String estado) throws SQLException, ParseException{
    // Construir la consulta SQL para actualizar los campos, incluyendo la encriptación de la contraseña si se proporciona        
        String query = "UPDATE personal SET nombre = ?, apellido = ?, correo = ?, "
                + "celular = ?, fecha_de_nacimiento = ?, genero = ?, salario = ?, cargo = ?, "
                + "estado = ? " + (password != null && !password.isEmpty() ? ", password = crypt(?, gen_salt('bf')) " : " ")
                + "WHERE id = ?";
        
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, correo);        
        ps.setString(4, celular);
        ps.setDate(5, DateString.StringToDateSQL(fechaNacimiento));
        ps.setString(6, genero);
        ps.setFloat(7,  Float.parseFloat(salario));
        ps.setString(8, cargo);
        ps.setString(9, estado);       
        
        if (password != null && !password.isEmpty()) {
            ps.setString(10, password);
            ps.setInt(11, id);
        } else {
            ps.setInt(10, id);
        }
        
        
        if (ps.executeUpdate() == 0){
            System.err.println("Class DPersonal.java: "
                    + "Ocurrió un error en modificar un personal en editarPersonal()");
            throw new SQLException();            
        }    
    }
    
    public void eliminar(int id) throws SQLException{
        String query = "DELETE FROM personal WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ningún personal con el ID especificado.");
            throw new SQLException(); 
        } else {
            System.out.println("Personal eliminado exitosamente.");
        } 
    }
    
    public List<String[]> listar() throws SQLException{
        List<String[]> personals = new ArrayList<>();
        String query = "SELECT * FROM personal";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] personal = {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("celular"),
                rs.getString("fecha_de_nacimiento"),
                rs.getString("genero"),
                rs.getString("salario"),
                rs.getString("estado"),
                rs.getString("cargo")
            };
            personals.add(personal);
        }
    
    return personals;
    }
    
    public String[] verPersonal(int id) throws SQLException{
        String[] personal = null;
        String query = "SELECT * FROM personal WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            personal = new String[] {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("celular"),
                rs.getString("fecha_de_nacimiento"),
                rs.getString("genero"),
                rs.getString("salario"),
                rs.getString("estado"),
                rs.getString("cargo")
            };
        }

        return personal;
    }
    
     public int getIdByCorreo(String correo) throws SQLException {
        int id = -1;
        String query = "SELECT * FROM personal WHERE correo=?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, correo);
                
        ResultSet set = ps.executeQuery();
        if(set.next()) {
            id = set.getInt("id");
        } 
        return id;
    }
     
    
}
