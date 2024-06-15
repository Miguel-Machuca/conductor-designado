/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DCliente;
import Datos.DMetodo_de_pago;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class NMetodo_de_pago {
    
     
    public static final String[] HEADERS =
        {"ID","NOMBRE","NUMERO DE TARJETA","NOMBRE DE LA TARJETA","FECHA DE VENCIMIENTO","CVV","ID_CLIENTE"};
    
    DCliente dcliente;
    DMetodo_de_pago metodo_de_pago;
    
    
    public NMetodo_de_pago(){
        dcliente = new DCliente();
        metodo_de_pago = new DMetodo_de_pago();
    }
   
    public void registrarTarjeta(List<String> parametros, String correo) throws SQLException, ParseException {
       // parametros.size() == 5
        if (parametros.size() == 5){
            
           int clienteid = dcliente.getIdByCorreo(correo);// validamos q un cliente 
            
            if(clienteid != -1) {
              metodo_de_pago.guardar(
                                 parametros.get(0),//tipo_de_metodo_de_pago
                                 parametros.get(1),//numero_tarjeta
                                 parametros.get(2),//nombre_en_la_tarjeta
                                 parametros.get(3),//fecha_vencimiento
                                 Integer.parseInt(parametros.get(4)),//cvv_cvc
                                 clienteid);//
                                
                metodo_de_pago.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          metodo_de_pago.Disconnect();
    }
     public void editarTarjeta(List<String> parametros, String correo) throws SQLException, ParseException {
            
        if (parametros.size() == 6){
           int clienteid = dcliente.getIdByCorreo(correo);// validamos q un cliente 
            
            if(clienteid != -1) {
              metodo_de_pago.editar(Integer.parseInt(parametros.get(0)),//id
                                 parametros.get(1),//tipo_de_metodo_de_pago
                                 parametros.get(2),//numero_tarjeta
                                 parametros.get(3),//nombre_en_la_tarjeta
                                 parametros.get(4),//fecha_vencimiento
                                 Integer.parseInt(parametros.get(5)),//cvv_cvc
                                 clienteid);//
                                
                metodo_de_pago.Disconnect();
            }
        }else{
            throw new IndexOutOfBoundsException();
        }
          metodo_de_pago.Disconnect();
    }
      public void eliminarMetodoDePago(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            metodo_de_pago.eliminar(Integer.parseInt(parametros.get(0)));
            metodo_de_pago.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarMetodoDePago(String correo) throws SQLException {
         int clienteid = dcliente.getIdByCorreo(correo);// validamos q un cliente 
         
        ArrayList<String[]> metodos = (ArrayList<String[]>) metodo_de_pago.listar(clienteid);
        metodo_de_pago.Disconnect();
        return metodos;
    }
}
