/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DServicio;
import Datos.DPersonal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author andre
 */
public class NServicio {
    DServicio dservicio;
    DPersonal dpersonal;
    public NServicio(){
        dservicio = new DServicio();
        dpersonal = new DPersonal();
    }
     public void RegistrarServicio(List<String> parametros, String correo) throws SQLException, ParseException {
        
        if (parametros.size() == 2){
           int personalid = dpersonal.getIdByCorreo(correo);// validamos q un personal 
            
            if(personalid != -1) {
              dservicio.guardar(parametros.get(0),//nombre
                                 parametros.get(1));//descripcion
                dservicio.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dservicio.Disconnect();
    }
    
    public void editarServcio(List<String> parametros, String correo) throws SQLException, ParseException {
            if (parametros.size() == 3){
           int personalid = dpersonal.getIdByCorreo(correo);// validamos q un personal 
            
            if(personalid != -1) {
              dservicio.editar(Integer.parseInt(parametros.get(0)),//id
                                parametros.get(1),//nombre
                                parametros.get(2));//descripcion
                dservicio.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dservicio.Disconnect();
    }
    
    public void eliminarServicio(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dservicio.eliminar(Integer.parseInt(parametros.get(0)));
            dservicio.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarServicio() throws SQLException {
        ArrayList<String[]> Servicios = (ArrayList<String[]>) dservicio.listar();
        dservicio.Disconnect();
        return Servicios;
    }
    
}
