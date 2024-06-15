/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DPersonal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class NPersonal {
    
     
    public static final String[] HEADERS =
        {"ID","NOMBRE","APELLIDO","CORREO","CELULAR","FECHA DE NACIMIENTO","GENERO","SALARIO","CARGO","ESTADO"};
    private final DPersonal dpersonal;
      
    public NPersonal() {
        dpersonal = new DPersonal();
       
    }
   
    public void guardarPersonal(List<String> parametros) throws SQLException, ParseException {
        if(parametros.size() == 10){//para validar que se manden todos los campos
        dpersonal.guardar(parametros.get(0),//nombre
                         parametros.get(1),//apellido
                         parametros.get(2),//correo
                         parametros.get(3),//contraseña
                         parametros.get(4),//celular
                         parametros.get(5),//fechaNacimiento
                         parametros.get(6),//genero
                         parametros.get(7),//salario
                         parametros.get(8),//estado
                         parametros.get(9)//cargo
                         
        );
        dpersonal.Disconnect();
        }else{
            throw new IndexOutOfBoundsException();
        } 
    }
    
    public void editarPersonal(List<String> parametros) throws SQLException, ParseException {
        if (parametros.size() == 11) {
             dpersonal.editar(Integer.parseInt(parametros.get(0)),//id
                         parametros.get(1),//nombre
                         parametros.get(2),//apellido
                         parametros.get(3),//correo
                         parametros.get(4),//contraseña
                         parametros.get(5),//celular
                         parametros.get(6),//fechaNacimiento
                         parametros.get(7),//genero
                        parametros.get(8),//salario
                         parametros.get(9),//estado
                         parametros.get(10)//cargo
        );
        dpersonal.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
       
    }
    
    public void eliminarPersonal(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dpersonal.eliminar(Integer.parseInt(parametros.get(0)));
            dpersonal.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarPersonal() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dpersonal.listar();
        dpersonal.Disconnect();
        return usuarios;
    }
     public boolean isUser(String correo) throws SQLException {
        return dpersonal.getIdByCorreo(correo) != -1;
    }
   
}
