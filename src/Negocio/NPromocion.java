/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DPromocion;
import Datos.DPersonal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author andre
 */
public class NPromocion {
    DPromocion dpromocion;
    DPersonal dpersonal;
    
    public NPromocion(){
        dpromocion = new DPromocion();
        dpersonal = new DPersonal();
    }
    
     public void registrarPromocion(List<String> parametros, String correo) throws SQLException, ParseException {
        
        if (parametros.size() == 6){
           int personalid = dpersonal.getIdByCorreo(correo);// validamos q un personal 
            
            if(personalid != -1) {
              dpromocion.guardar(parametros.get(0),//nombre
                                 parametros.get(1),//descripcion
                                 Float.parseFloat(parametros.get(2)),//descuento
                                 Integer.parseInt( parametros.get(3)),//id servicio 
                                 parametros.get(4),//fecha incio
                                 parametros.get(5));//fecha fin
                                
                dpromocion.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dpromocion.Disconnect();
    }
     public void editarPromocion(List<String> parametros, String correo) throws SQLException, ParseException {
            
        if (parametros.size() == 7){
           int personalid = dpersonal.getIdByCorreo(correo);// validamos q un personal 
            
            if(personalid != -1) {
              dpromocion.editar(Integer.parseInt(parametros.get(0)),
                                parametros.get(1),//nombre
                                parametros.get(2),//descripcion
                                Float.parseFloat(parametros.get(3)),//descuento
                                Integer.parseInt( parametros.get(4)),//id servicio 
                                parametros.get(5),//fecha incio
                                parametros.get(6));//fecha fin
                                
                dpromocion.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dpromocion.Disconnect();
    }
      public void eliminarPromocion(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dpromocion.eliminar(Integer.parseInt(parametros.get(0)));
            dpromocion.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarPromocion() throws SQLException {
        ArrayList<String[]> Promociones = (ArrayList<String[]>) dpromocion.listar();
        dpromocion.Disconnect();
        return Promociones;
    }
}
