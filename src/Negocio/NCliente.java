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
public class NCliente {

   private final DCliente dcliente;
   private DMetodo_de_pago dmetodo;
   public NCliente(){
       dcliente = new DCliente();
       dmetodo = new DMetodo_de_pago();
   }
   public void guardarCliente(List<String> parametros) throws SQLException, ParseException {
        if(parametros.size() == 8){//para validar que se manden todos los campos
        dcliente.guardar(parametros.get(0),//nombre
                         parametros.get(1),//apellido
                         parametros.get(2),//correo
                         parametros.get(3),//contraseña
                         parametros.get(4),//celular
                         parametros.get(5),//fechaNacimiento
                         parametros.get(6),//genero
                         parametros.get(7)//ci
        );
        int clienteid = dcliente.getIdByCorreo(parametros.get(2));
        dmetodo.guardarMetodoEfectivo(clienteid);
        dcliente.Disconnect();
        }else{
            throw new IndexOutOfBoundsException();
        } 
    }
    
    public void editarCliente(List<String> parametros) throws SQLException, ParseException {
        if (parametros.size() == 9) {
             dcliente.editar(Integer.parseInt(parametros.get(0)),//id
                         parametros.get(1),//nombre
                         parametros.get(2),//apellido
                         parametros.get(3),//correo
                         parametros.get(4),//contraseña
                         parametros.get(5),//celular
                         parametros.get(6),//fechaNacimiento
                         parametros.get(7),//genero
                         parametros.get(8)//ci
        );
        dcliente.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
       
    }
    
    public void eliminarCliente(List<String> parametros) throws SQLException {
        if (parametros.size() == 1){
            dcliente.eliminar(Integer.parseInt(parametros.get(0)));
            dcliente.Disconnect();
        }else{
             throw new IndexOutOfBoundsException();
        }
        
    }
    
    public ArrayList<String[]> listarCliente() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dcliente.listar();
        dcliente.Disconnect();
        return usuarios;
    }
     public boolean isUser(String correo) throws SQLException {
        return dcliente.getIdByCorreo(correo) != -1;
    }
   
}