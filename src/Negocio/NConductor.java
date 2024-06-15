/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DConductor;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class NConductor {
    
     
    DConductor dconductor ;
    public static final String[] HEADERS =
        {"ID","NOMBRE","APELLIDO","CORREO","CELULAR","FECHA DE NACIMIENTO","GENERO","NUMERO DE LICENCIA","TIPO DE LICENCIA","FECHA DE VENCIMIENTO"};
    
    public NConductor(){
      dconductor  = new DConductor();
   }
   public void guardarConductor(List<String> parametros) throws SQLException, ParseException {
        if(parametros.size() == 10){//para validar que se manden todos los campos
        dconductor.guardar(parametros.get(0),//nombre
                         parametros.get(1),//apellido
                         parametros.get(2),//correo
                         parametros.get(3),//contraseña
                         parametros.get(4),//celular
                         parametros.get(5),//fechaNacimiento
                         parametros.get(6),//genero
                         parametros.get(7),//nro de licencia
                         parametros.get(8),//tipo de licencia
                         parametros.get(9)//fecha de vencimiento de licencia
                        
        );
        dconductor.Disconnect();
        }else{
            throw new IndexOutOfBoundsException();
        } 
    }
    
   public void editarConductor(List<String> parametros) throws SQLException, ParseException {
        if (parametros.size() == 11) {
             dconductor.editar(Integer.parseInt(parametros.get(0)),//id
                         parametros.get(1),//nombre
                         parametros.get(2),//apellido
                         parametros.get(3),//correo
                         parametros.get(4),//contraseña
                         parametros.get(5),//celular
                         parametros.get(6),//fechaNacimiento
                         parametros.get(7),//genero
                         parametros.get(8),//nro de licencia
                         parametros.get(9),//tipo de licencia
                         parametros.get(10)//fecha de vencimiento de licencia
                    
        );
        dconductor.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
       
    }
    
   public void actualizarEstadoConductor(List<String> parametros) throws SQLException{
        if (parametros.size() == 2){
            dconductor.actualizarEstado(
                    Integer.parseInt(parametros.get(0)), 
                    parametros.get(1)
            );
        }
    }
    
   public void eliminarConductor(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dconductor.eliminar(Integer.parseInt(parametros.get(0)));
            dconductor.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
   public ArrayList<String[]> listarConductor() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dconductor.listar();
        dconductor.Disconnect();
        return usuarios;
    }
   
   public boolean isUser() throws SQLException {
       //return dconductor.getIdByCorreo(correo) != -1;
       return false;
    }
   
   public int getIdByCorreo(String correo) throws SQLException{
       int idConductor = -1;
       if (correo != ""){
           idConductor = dconductor.getIdByCorreo(correo);
       }
       return idConductor;
   }
   
   public String getCorreoById(int idConductor) throws SQLException{
       String correo = "hay un error";
       if (idConductor != -1){
           correo = dconductor.getCorreoById(idConductor);
       } 
       
       return correo;
   }
}
