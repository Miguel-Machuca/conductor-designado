/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DGastoOperativo;
import Datos.DPersonal;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author andre
 */
public class NGastosOperativos {
    
   
   public static final String[] HEADERS =
        {"ID","MONTO","FECHA","DESCRIPCION","ID_PERSONAL"};
    public static final String[] HEADERSR = {"FECHAS", "MONTO"};
    DPersonal dpersonal;
    DGastoOperativo dgastos;
     public NGastosOperativos(){
         dpersonal = new DPersonal();
         dgastos = new DGastoOperativo();
     }
     public void guardarGastosOperativos(List<String> parametros, String correo) throws SQLException, ParseException {
        if (parametros.size() == 3){
            int personalid = dpersonal.getIdByCorreo(correo);
            if(personalid != -1) {
              dgastos.guardar(parametros.get(0),//monto
                      parametros.get(1),//fecha
                      parametros.get(2),// descripcion 
                      personalid);// id personal
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dgastos.Disconnect();
    }
     public void editarGastosOperativos(List<String> parametros, String correo) throws SQLException, ParseException {
        if (parametros.size() == 4){
            int personalid = dpersonal.getIdByCorreo(correo);
            if(personalid != -1) {
              dgastos.editar(Integer.parseInt(parametros.get(0)),//monto
                      Float.parseFloat(parametros.get(1)),//monto
                      parametros.get(2),//fecha
                      parametros.get(3),// descripcion 
                      personalid);// id personal
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          dgastos.Disconnect();
    }
      public void eliminarGastosOperativos(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dgastos.eliminar(Integer.parseInt(parametros.get(0)));
            dgastos.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarGastosOperativos() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dgastos.listar();
        dgastos.Disconnect();
        return usuarios;
    }
  
    
     public void listarGraficaGastosPorFecha(List<String> parametros)throws SQLException, IOException, ParseException, IndexOutOfBoundsException {
        if (parametros.size() == 2){
           
             dgastos.listarGraficaGastosPorFecha(parametros.get(0), parametros.get(1));
            
        }else{
            throw new IndexOutOfBoundsException();
        }
          dgastos.Disconnect();
    }
     public ArrayList<String[]> reporte(List<String> parametros) throws SQLException, ParseException{
        ArrayList<String[]> gastos = (ArrayList<String[]>) dgastos.reporte(parametros.get(0),parametros.get(1));
       dgastos.Disconnect();
        return gastos;
    }
     
}
