/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DRol;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class NRol {
      private final DRol drol;
      
    public NRol() {
        drol = new DRol();
       
    }
   
    public void guardarRol(List<String> parametros) throws SQLException, ParseException {
        if(parametros.size() == 2){//para validar que se manden todos los campos
        drol.guardar(parametros.get(0),//nombre
                     parametros.get(1)//descripcion
                         
        );
        drol.Disconnect();
        }else{
            throw new IndexOutOfBoundsException();
        } 
    }
    
    public void editarRol(List<String> parametros) throws SQLException, ParseException {
        if (parametros.size() == 3) {
             drol.editar(Integer.parseInt(parametros.get(0)),//id
                         parametros.get(1),//nombre
                         parametros.get(2)//descripcion
                         
        );
        drol.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
       
    }
    
    public void eliminarRol(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            drol.eliminar(Integer.parseInt(parametros.get(0)));
            drol.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarRoles() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) drol.listar();
        drol.Disconnect();
        return usuarios;
    }
  
   
}
