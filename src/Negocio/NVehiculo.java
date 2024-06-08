/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DVehiculo;
import Datos.DPersonal;
import Datos.DConductor;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author andre
 */
public class NVehiculo {
    DVehiculo dvehiculo ;
    DPersonal dpersonal ;
    DConductor dConductor;
    public NVehiculo(){
        dvehiculo = new DVehiculo();
        dpersonal = new DPersonal();
        dConductor = new DConductor();
    }
    
    public void RegistrarVehiculo(List<String> parametros, String correo) throws SQLException, ParseException {
        
        if (parametros.size() == 7){
           int personalid = dpersonal.getIdByCorreo(correo);// validamos q un personal haga el registro de un vehiculo 
           int conductorid = dConductor.getIdByCorreo(parametros.get(6));
           
            if(personalid != -1) {
              dvehiculo.guardar(parametros.get(0),//modelo
                      parametros.get(1),//marca
                      parametros.get(2),// placa 
                      Integer.parseInt(parametros.get(3)),// numero de seguro
                      parametros.get(4),// fecha de vencimiento del seguro
                      parametros.get(5),//estado
                      conductorid);// id personal
                dvehiculo.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dvehiculo.Disconnect();
    }
     public void editarVehiculo(List<String> parametros, String correo) throws SQLException, ParseException {
          if (parametros.size() == 8){
           int personalid = dpersonal.getIdByCorreo(correo);// validamos q un personal haga el registro de un vehiculo 
           int conductorid = dConductor.getIdByCorreo(parametros.get(7));
            if(personalid != -1) {
              dvehiculo.editar(Integer.parseInt(parametros.get(0)) ,//id
                      parametros.get(1),//modelo
                      parametros.get(2),//marca
                      parametros.get(3),// placa 
                      Integer.parseInt(parametros.get(4)),// numero de seguro
                      parametros.get(5),// fecha de vencimiento del seguro
                      parametros.get(6),//estado
                      conductorid);// id personal
                dvehiculo.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dvehiculo.Disconnect();
    }
      public void eliminarVehiculo(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dvehiculo.eliminar(Integer.parseInt(parametros.get(0)));
            dvehiculo.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarVehiculos() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dvehiculo.listar();
        dvehiculo.Disconnect();
        return usuarios;
    }
    
    
}
