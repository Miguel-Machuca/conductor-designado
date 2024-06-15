/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.analex.interfaces;

import interpreter.events.TokenEvent;

/**
 *
 * @author ronal
 */
//AJUSTAR DE ACUERDO A NUESTRO CU
public interface ITokenEventListener {
   
    void roles(TokenEvent event);
    void usuarios(TokenEvent event);
   
    void conductores(TokenEvent event);
    void personal(TokenEvent event);
    void vehiculos(TokenEvent event);
    void servicios(TokenEvent event);     
    void reservas(TokenEvent event); 
    void solicitar(TokenEvent event);
    void gastosOperativos(TokenEvent event);     
    void promociones(TokenEvent event);     
    void pagos(TokenEvent event);     
    void reportes(TokenEvent event);     
    void error(TokenEvent event);
    void viajes(TokenEvent event);
    void clientes(TokenEvent event);
    void metodo_de_pago(TokenEvent event);
    void ayuda(TokenEvent event);
  
    //agregar mas casos de uso de ser necesario
}
