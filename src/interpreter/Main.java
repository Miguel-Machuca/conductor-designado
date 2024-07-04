/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import interpreter.analex.Interpreter;
import interpreter.analex.interfaces.ITokenEventListener;
import interpreter.analex.utils.Token;
import interpreter.events.TokenEvent;

import communication.SendEmailThread;
import Utils.Email;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Negocio.*;
import java.sql.SQLException;
import java.text.ParseException;
/**
 *
 * @author ronal
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NUsuario usuario = new NUsuario();
        NCliente cliente = new NCliente();
        NConductor conductor = new NConductor();
        NPersonal personal = new NPersonal();
        NRol rol = new NRol();
        NGastosOperativos gastos = new NGastosOperativos();
        NVehiculo vehiculo = new NVehiculo();
        NServicio servicio = new NServicio();
        NPromocion promocion = new NPromocion();
        NSolicitarServicio solicitud = new NSolicitarServicio();
        NMetodo_de_pago medodo = new NMetodo_de_pago();
        
        
        String comando1 = "usuario agregar [Andres , chavez , andy@gmail.com,12345678,63489070, 1998-06-21, masculino] ";
        String comando2 = "usuario editar [1,jose   , lopez@gmail.com,12345678,63489070, 1998-06-21, masculino] ";
        String comando3 = "usuario eliminar [1] ";
        
        String comando4 = "cliente agregar [Cliente1, cliente , andresrock1711@gmail.com,12345678,63489070, 1998-06-21, masculino,7987833] ";
        String comando5 = "cliente editar [6,Andres editado , chavez , andy@gmail.com,12345,63489070, 1998-06-21, masculino,7987833] ";
        String comando6 = "cliente eliminar [8] ";
        
        String comando7 = "conductor agregar [ roger , sossa , roger@gmail.com,   126455678,63489070, 1998-06-22, masculino,76364,A, 2025-08-22] ";
        String comando8 = "conductor editar [11, erika editado , mamani , erika@gmail.com,   12345678,63489070, 1998-06-21, masculino,76364,A, 2025-08-22] ";
        String comando9 = "conductor eliminar [11] ";
        
        String comando10 = "personal agregar [ Andrea , chavez , Andrea@gmail.com,   12345678,63489070, 1998-06-21, femenino,2500.7,activo,empleado] ";
        String comando11 = "personal editar [13, Andrea editado , chavez , Andrea@gmail.com,   12345678,63489070, 1998-06-21, femenino,2500.7,activo,empleado] ";
        String comando12 = "personal eliminar [14] ";
             
        String comando13 = "rol agregar [ a,b] ";
        String comando14 = "rol editar [ 2,conductor, es un conductor de la empresa] ";
        String comando15 = "rol eliminar [ 1] ";

        String comando16 = "gastosOperativos agregar[120.3,2024-10-15,pago de almuerzos]";
        String comando17 = "gastosOperativos editar[6, 122.3,2024-09-12,pago de biaticos editado]";
        String comando18 = "gastosOperativos eliminar[1 ]";
        
        String comando19 = "vehiculo agregar [Audi, A4, Bol123, 5367272, 2028-08-22, activo, luisfelipe.lfgo59@gmail.com]";
        String comando20 = "vehiculo editar [1,Audi,A4,Bol123,5367272,2028-08-22,activo,juan@gmail.com]";
        String comando21 = "vehiculo eliminar [1]";
        
        String comando22 ="servicio agregar[taxi, servcio de trasporte privado ]";
        String comando23 ="servicio editar [2, moto, servcio de trasporte privado ]";
        String comando24 ="servicio eliminar [3]";
        
        String comando25 = "promocion agregar [dia de la madre , promocion por el dia de la madre, 0.10,   1   ,  2024-05-01,  2024-05-31]";
        String comando26 = "promocion editar [1,dia de la madre , promocion por el dia de la madre, 0.15,   1   ,  2024-05-01,  2024-05-30]";
        String comando27 = "promocion eliminar [1]";
        
      
        String comando30 = "metodoDePago agregar[Tarjeta de credito, 637828293,banco BNB,2025-07-09,7865]";
        //String comando30 = "metodoDePago agregar[Efectivo]";
        String comando31 = "metodoDePago editar[1,Tarjeta de credito, 637828293,banco union editado ,2025-07-09,7865]";
        String comando32 = "metodoDePago eliminar[1]";
        
        String comando33 = "usuario editar [5, 4] ";
        String comando34 = "conductor editar [15, libre]";
        String comando29 = "solicitar agregar [1, https://maps.app.goo.gl/2Ck75XWJ4tTEcVrW9,2024-06-5 29:41:00]";
        
          
        String comando28 = "viaje solicitar   [1, https://maps.app.goo.gl/kL8J44qZz8xNASSL6]";// cliente solicita un viaje
        //String comando = "viaje aceptar   [1]";//codigo del viajes "id_solicitud"-->conductor avisa que esta en camino
        //String comando55 = "viaje pagar   [111,1]";//codigo del viajes "id_solicitud" y el metodo de pago
        
        
        
        
        
        String correo = "bianka@gmail.com";
        Interpreter interpreter = new Interpreter(comando28, correo);       
        interpreter.setListener(new ITokenEventListener() {
            
            @Override
            public void viajes(TokenEvent event) {
                
                System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.SOLICITAR: 
                            System.out.println("entramos a agregar reserva ");
                            
                            int idSolicitud = solicitud.idRegistrarSolicitud(event.getParams(), event.getSender());
                            
                            int idConductor = solicitud.idBuscarConductor(idSolicitud);
                            
                            System.out.println(conductor.getCorreoById(idConductor));
                           
                            //solicitud.reservarSolicitud(event.getParams(),event.getSender());
                            System.out.println("tarifa del viaje: ");
                           List<String[]> p = new ArrayList<>();
                           
                            break;
                         
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
             //*****************************************************************************************    
        // *******************************   RESERVAR SERVICIO  ********************************************
        //*****************************************************************************************       
            @Override
            public void reservas(TokenEvent event) {
                
                System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar reserva ");
                            
                            int idSolicitud = solicitud.idRegistrarSolicitud(event.getParams(), event.getSender());
                            
                            int idConductor = solicitud.idBuscarConductor(idSolicitud);
                            
                            System.out.println(conductor.getCorreoById(idConductor));
                            
                            //solicitud.reservarSolicitud(event.getParams(),event.getSender());
                           
                           
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.LISTAR:
                           // 
                        break; 
                        case Token.VER:
                           //
                        break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            
            
        //*****************************************************************************************    
        // *******************************   ROL    *********************************************
        //*****************************************************************************************
           @Override
            public void roles(TokenEvent event) {
               
               String Correo=event.getSender();
                     
                try {
                    int a =usuario.getRolbyCorreo(Correo);
                      System.out.println("id admin :"+ a);
                  
                    if ( usuario.esUsuario(Correo)){//validamos que sea un usuario del sistema 
                        System.out.println("id admin :"+usuario.getRolbyCorreo(Correo));
                        if(usuario.getRolbyCorreo(Correo)==4){// validamos que sea admin
                            
                            switch (event.getAction()){
                       
                                    case Token.AGREGAR: 
                                        rol.guardarRol(event.getParams());

                                        break;
                                    case Token.EDITAR:
                                        rol.editarRol(event.getParams());
                                        break;
                                    case Token.ELIMINAR:
                                        rol.eliminarRol(event.getParams());
                                        break;
                                    case Token.LISTAR:

                                       break;
                                    case Token.VER:
                                        break;

                                    }
                        }else{
                            
                        }
                        
                  }else{
                       
                   }
                  
                } catch (SQLException ex) {
                    
                       
                        
                } catch (ParseException ex) {
                    
                        
                        
                }
                  catch (IndexOutOfBoundsException ex) {
                      
                       
                }
            }
            
        //*****************************************************************************************    
        // *******************************   USUARIO  *******************************************
        //*****************************************************************************************
            @Override
            public void usuarios(TokenEvent event) {
                
                System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            usuario.guardarUsuario(event.getParams());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            //usuario.editarUsuario(event.getParams());
                            usuario.asignarRolUsuario(event.getParams());
                            break;
                        case Token.ELIMINAR:
                            usuario.eliminarUsuario(event.getParams());
                            break;
                        case Token.LISTAR:
                           // usuario.listarUsuarios();
                        break;
                        case Token.VER:
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        //*****************************************************************************************    
        // *******************************   ClIENTE   *******************************************
        //*****************************************************************************************
             @Override
            public void clientes(TokenEvent event) {
                 System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar cliente ");
                           cliente.guardarCliente(event.getParams());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            cliente.editarCliente(event.getParams());
                            break;
                        case Token.ELIMINAR:
                           cliente.eliminarCliente(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                        case Token.VER:
                            //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        //*****************************************************************************************    
        // *******************************   CONDUCTOR   ****************************************
        //*****************************************************************************************
             @Override
            public void conductores(TokenEvent event) {
                  System.out.println("cantidad de parametros:    "+event.countParams());
                  System.out.println(event.toString());
                try 
               {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar conductores ");
                           conductor.guardarConductor(event.getParams());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            //conductor.editarConductor(event.getParams());
                            conductor.actualizarEstadoConductor(event.getParams());
                            break;
                        case Token.ELIMINAR:
                           conductor.eliminarConductor(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        //*****************************************************************************************    
        // *******************************   PERSONAL  ********************************************
        //*****************************************************************************************
              @Override
            public void personal(TokenEvent event) {
                 System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar personal ");
                           personal.guardarPersonal(event.getParams());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            personal.editarPersonal(event.getParams());
                            break;
                        case Token.ELIMINAR:
                           personal.eliminarPersonal(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        //*****************************************************************************************    
        // *******************************   VEHICULO  *******************************************
        //*****************************************************************************************
            
            @Override
            public void vehiculos(TokenEvent event) {
                   System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar vehiculos ");
                           vehiculo.RegistrarVehiculo(event.getParams(),event.getSender());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           vehiculo.editarVehiculo(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           vehiculo.eliminarVehiculo(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         
        //*****************************************************************************************    
        // *******************************   SERVICIO  ********************************************
        //*****************************************************************************************
            @Override
            public void servicios(TokenEvent event) {
                    System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar servicios ");
                           servicio.RegistrarServicio(event.getParams(),event.getSender());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           servicio.editarServcio(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           servicio.eliminarServicio(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
       
       
            
        //*****************************************************************************************    
        // *******************************   GASTOS OPERATIVOS  ********************************************
        //*****************************************************************************************
            @Override
            public void gastosOperativos(TokenEvent event) {
                 System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar gastos operativos ");
                            gastos.guardarGastosOperativos(event.getParams(),event.getSender());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            gastos.editarGastosOperativos(event.getParams(),event.getSender());
                            System.out.println("entramos a editar gastos operativos ");
                            break;
                        case Token.ELIMINAR:
                            gastos.eliminarGastosOperativos(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        //*****************************************************************************************    
        // *******************************   PROMOCION  *******************************************
        //*****************************************************************************************
            
            @Override
            public void promociones(TokenEvent event) {
                System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar promocion ");
                           promocion.registrarPromocion(event.getParams(),event.getSender());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           promocion.editarPromocion(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           promocion.eliminarPromocion(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
        //*****************************************************************************************    
        // *******************************   PAGO  ********************************************
        //*****************************************************************************************
            public void pagos(TokenEvent event) {
                
            }

            @Override
            public void reportes(TokenEvent event) {
                
            }

            @Override
            public void error(TokenEvent event) {
                System.out.println(event);
                System.out.println("error de comando");    
            }

            
        //*****************************************************************************************    
        // *******************************   METODO DE PAGO ********************************************
        //*****************************************************************************************
            @Override
            public void metodo_de_pago(TokenEvent event) {
                 System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar Tarjeta de credito ");
                           medodo.registrarTarjeta(event.getParams(),event.getSender());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           medodo.editarTarjeta(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           medodo.eliminarMetodoDePago(event.getParams());
                            break;
                        case Token.LISTAR:
                           // cliente.listarUsuarios();
                        break; 
                          case Token.VER:
                              //
                            break;
                       
                    }
                    
                } catch (SQLException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void ayuda(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

 
        });
        
      
        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }
    
    /* 
        Email emailObject = new Email("andresintel773@gmail.com", Email.SUBJECT,
                "Petición realizada correctamente desde tecno web ");
        
        SendEmailThread sendEmail = new SendEmailThread(emailObject);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
        
    }// borrar luego
    */
}
