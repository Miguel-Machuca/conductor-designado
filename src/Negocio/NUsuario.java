/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DUsuario;
import Datos.DCliente;
import Datos.DConductor;
import Datos.DPersonal;
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
public  class NUsuario {
    
      private final DUsuario dUsuario;
      
    public NUsuario() {
        dUsuario = new DUsuario();
       
    }
   
    public void guardarUsuario(List<String> parametros) throws SQLException, ParseException {
        if(parametros.size() == 7){//para validar que se manden todos los campos
        dUsuario.guardar(parametros.get(0),//nombre
                         parametros.get(1),//apellido
                         parametros.get(2),//correo
                         parametros.get(3),//contraseña
                         parametros.get(4),//celular
                         parametros.get(5),//fechaNacimiento
                         parametros.get(6)//genero
        );
        dUsuario.Disconnect();
        }else{
            throw new IndexOutOfBoundsException();
        } 
    }
    
    public void editarUsuario(List<String> parametros) throws SQLException, ParseException {
        if (parametros.size() == 8) {
             dUsuario.editar(Integer.parseInt(parametros.get(0)),//id
                         parametros.get(1),//nombre
                         parametros.get(2),//apellido
                         parametros.get(3),//correo
                         parametros.get(4),//contraseña
                         parametros.get(5),//celular
                         parametros.get(6),//fechaNacimiento
                         parametros.get(7)//genero
        );
        dUsuario.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
       
    }
    
    public void asignarRolUsuario(List<String> parametros) throws SQLException{
        if(parametros.size() == 2){
            dUsuario.asignarRol(
                Integer.parseInt(parametros.get(0)),
                Integer.parseInt(parametros.get(1))
            );  
            dUsuario.Disconnect();
        }
    }
    
    public void eliminarUsuario(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dUsuario.eliminar(Integer.parseInt(parametros.get(0)));
            dUsuario.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
 
    public ArrayList<String[]> listarUsuarios() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dUsuario.listar();
        dUsuario.Disconnect();
        return usuarios;
    }
    

    public boolean esUsuario(String correo) throws SQLException {
        try {
            return dUsuario.esUsuario(correo);
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al verificar si el usuario existe", e);            
            throw e;
        }
    }

     
    public boolean esAdmin(String correo) throws SQLException {
        try {
            return dUsuario.esAdmin(correo);
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al verificar si el usuario es admin", e);
            throw e;
        }
    }

   
}
