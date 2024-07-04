/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DTransacciones;
import Datos.DCliente;
import Datos.DMetodo_de_pago;
import java.io.IOException;
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
public class NTransacciones {

    private final DTransacciones dTransacciones;
    private final DCliente dcliente;
    private final DMetodo_de_pago dMetodoDePago;
    
    public static final String[] HEADERSR = {"Método de Pago", "Monto Total"};

    public NTransacciones() {
        this.dTransacciones = new DTransacciones();
        this.dcliente = new DCliente();
        this.dMetodoDePago = new DMetodo_de_pago();
    }

    public void guardarTransaccion(List<String> parametros, String correo)throws SQLException, ParseException, Exception  {
        
        if (parametros.size()==2){
            int idCliente = dcliente.getIdByCorreo(correo);
            int idSolicitud = Integer.parseInt(parametros.get(0));
            String metodoPagoStr = parametros.get(1);
             if (metodoPagoStr.equals("1")) {
            // Mantener pago en efectivo
            dTransacciones.finalizarTransaccion(idSolicitud, 1); // Asumiendo que 1 es el ID para efectivo
             } else {
                 int idMetodoPago = Integer.parseInt(metodoPagoStr);
                 // Validar que el método de pago pertenece al cliente
                if (!dMetodoDePago.perteneceAlCliente(idMetodoPago, idCliente)) {
                    throw new Exception("El método de pago no pertenece al cliente.");
                }
                // Actualizar la transacción con el nuevo método de pago
             dTransacciones.finalizarTransaccion(idSolicitud, idMetodoPago);
             }
    
        }else{
            throw new IndexOutOfBoundsException();
        } 
       // dTransacciones.guardar(idSolicitar, fecha, monto, estado, idMetodo);
    }
    
  
   public void listarGraficaGastosPorFecha(List<String> parametros) throws SQLException, IOException, ParseException, IndexOutOfBoundsException {
    System.out.println("entramos a lista de grafico");
    if (parametros.size() == 2) {
        dTransacciones.listarGraficaTransaccionesPorMetodoPago(parametros.get(0), parametros.get(1));
    } else {
        throw new IndexOutOfBoundsException();
    }
    dTransacciones.Disconnect();
}

public ArrayList<String[]> reporte(List<String> parametros) throws SQLException, ParseException {
    ArrayList<String[]> transacciones = (ArrayList<String[]>) dTransacciones.reporte(parametros.get(0), parametros.get(1));
    dTransacciones.Disconnect();
    return transacciones;
}

}
