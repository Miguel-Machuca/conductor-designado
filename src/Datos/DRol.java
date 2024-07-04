/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Connection.sqlconnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mmach
 */
public class DRol {
    
    
    private final sqlconnection connection;
    
    public DRol(){
       this.connection = new sqlconnection(
                "grupo04sc",
                "grup004grup004", 
                "mail.tecnoweb.org.bo",
                "5432", "db_grupo04sc");
    }
    
    
    public void guardar(String nombre, String descripcion) throws SQLException{
       String query = "INSERT INTO rol (nombre, descripcion) VALUES (?, ?)";
       
       PreparedStatement ps = connection.connect().prepareStatement(query);
       
       ps.setString(1, nombre);
       ps.setString(2, descripcion);
       
       int filasAfectadas = ps.executeUpdate();
       if (filasAfectadas == 0){
           System.err.println("Error en Class DRol.java dice:"+                    
                   "No se pudo insertar el rol en la base de datos -> guardar().");
           
           throw new SQLException();
       }else{
           System.err.println("Rol insertado correctamente en la base de datos.");
       }
    }   
    
    public void editar(int id, String nombre, String descripcion) throws SQLException{
       String query = "UPDATE rol SET nombre = ?, descripcion = ? WHERE id = ?";
       PreparedStatement ps = connection.connect().prepareStatement(query);
       
       ps.setString(1, nombre);
       ps.setString(2, descripcion);
       ps.setInt(3, id);
       
       int filasAfectadas = ps.executeUpdate();
       if (filasAfectadas == 0){
           System.err.println("Error en Class DRol.java dice:"+ 
                   "No se pudo modificar el rol en la base de datos -> modificar().");
           throw new SQLException();
       }else{
           System.err.println("Rol modificado correctamente en la base de datos.");
       }
    }
    
    public void eliminar(int id) throws SQLException{
        String query = "DELETE FROM rol WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas == 0){
            System.err.println("Error en Class DRol.java dice:"+ 
                    "No se pudo eliminar el rol en la base de datos -> eliminar().");
            throw new SQLException();
        }else{
            System.err.println("Rol eliminado correctamente en la base de datos.");
        }
    }
    
    public List<String[]> listar() throws SQLException{
        List<String[]> roles = new ArrayList<>();
        String query = "SELECT * FROM rol";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            String[] rol = {
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion")
            };
            roles.add(rol);
        }
        
        return roles;
        
    }
    
    public String[] ver(int id) throws SQLException{
        String [] rol = null;
        String query = "SELECT * FROM rol WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            rol = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion")
            };
        }
        if(rol == null){
            System.err.println("No se encuentra rol especificado o no Existe el ID");
            throw new SQLException();
        }
          
        return rol;
    }
    
    public void Disconnect(){
        if(connection != null){
            connection.closeConnection();
        }
    }
}
