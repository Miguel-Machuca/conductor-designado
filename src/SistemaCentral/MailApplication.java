/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaCentral;

import Negocio.NCliente;
import Negocio.NConductor;
import Negocio.NGastosOperativos;
import Negocio.NMetodo_de_pago;
import Negocio.NPersonal;
import Negocio.NPromocion;
import Negocio.NRol;
import Negocio.NServicio;
import Negocio.NSolicitarServicio;
import Negocio.NUsuario;
import Negocio.NVehiculo;
import Utils.Email;
import Utils.HtmlBuilder;

import interfaces.IEmailEventListener;
import interpreter.Main;
import interpreter.analex.Interpreter;
import interpreter.analex.interfaces.ITokenEventListener;
import interpreter.analex.utils.Token;
import interpreter.events.TokenEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import communication.MailVerificationThread;
import communication.SendEmailThread;
import java.util.ArrayList;
/**
 *
 * @author andre
 */
public class MailApplication implements IEmailEventListener, ITokenEventListener{
    
    private static final int CONSTRAINTS_ERROR = -2;
    private static final int NUMBER_FORMAT_ERROR = -3;
    private static final int INDEX_OUT_OF_BOUND_ERROR = -4;
    private static final int PARSE_ERROR = -5;
    private static final int AUTHORIZATION_ERROR = -6;
    private static final int LOGIN_ERROR = -7;
    
    //
    private MailVerificationThread mailVerificationThread;
   
    private NUsuario usuario;
    private NCliente cliente;
    private NConductor conductor;
    private NPersonal personal;
    private NRol rol;
    private NGastosOperativos gastos;
    private NVehiculo vehiculo;
    private NServicio servicio;
    private NPromocion promocion;
    private NSolicitarServicio solicitud;
    private NMetodo_de_pago metodo;
    
    //Constructor
     public MailApplication() {
        mailVerificationThread = new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(MailApplication.this);
        usuario = new NUsuario();
        cliente = new NCliente();
        rol = new NRol();
        conductor = new NConductor();
        personal = new NPersonal();
        gastos = new NGastosOperativos();
        vehiculo = new NVehiculo();
        servicio = new NServicio();
        promocion = new NPromocion();
        solicitud = new NSolicitarServicio();
        metodo = new NMetodo_de_pago();
    }
    
     //*****************************************************************************************    
        // *******************************   ROL    *********************************************
        //*****************************************************************************************
            @Override
            public void roles(TokenEvent event) {
               
              
                try {
                    
                    String Correo=event.getSender();
                    if ( usuario.esUsuario(Correo)){//validamos que sea un usuario del sistema 
                      
                        if(usuario.getRolbyCorreo(Correo)==4){// validamos que sea admin
                            
                            switch (event.getAction()){
                       
                                    case Token.AGREGAR: 
                                        rol.guardarRol(event.getParams());
                                        simpleNotifySuccess(event.getSender(), "Rol guardado correctamente");

                                        break;
                                    case Token.EDITAR:
                                        rol.editarRol(event.getParams());
                                        simpleNotifySuccess(event.getSender(), "Rol Editado correctamente");
                                        break;
                                    case Token.ELIMINAR:
                                        rol.eliminarRol(event.getParams());
                                        simpleNotifySuccess(event.getSender(), "Rol Eliminado correctamente");
                                        break;
                                    case Token.LISTAR:

                                       tableNotifySuccess(event.getSender(), "Lista de Roles", NRol.HEADERS, rol.listarRoles());
                                       break;
                                    case Token.VER:
                                        break;

                                    }
                        }else{
                            handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                        
                  }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                   }
                  
                } catch (SQLException ex) {
                    
                        handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                        
                } catch (ParseException ex) {
                    
                        handleError(PARSE_ERROR, event.getSender(), null);
                        
                }
                  catch (IndexOutOfBoundsException ex) {
                      
                       handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                }
            }
            
        //*****************************************************************************************    
        // *******************************   USUARIO  *******************************************
        //*****************************************************************************************
            @Override
            public void usuarios(TokenEvent event) {
                
                try {
                    String correo = event.getSender();
                    
                    if(usuario.esUsuario(correo)){
                        
                        if(usuario.getRolbyCorreo(correo)== 4 || usuario.getRolbyCorreo(correo)==1){
                            
                             switch (event.getAction()){
                        
                                case Token.AGREGAR: 
                                    usuario.guardarUsuario(event.getParams());
                                    simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                                    break;
                                case Token.EDITAR:
                                    usuario.editarUsuario(event.getParams());
                                    simpleNotifySuccess(event.getSender(), "Usuario editado correctamente");
                                    break;
                                case Token.ELIMINAR:
                                    usuario.eliminarUsuario(event.getParams());
                                     simpleNotifySuccess(event.getSender(), "Usuario eliminado correctamente");
                                    break;
                                case Token.LISTAR:

                                    tableNotifySuccess(event.getSender(), "Lista de Usuarios", NUsuario.HEADERS, usuario.listarUsuarios());

                                    break;
                                case Token.VER:
                                    
                                    break;

                                }
                            
                        }else{
                             handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                    }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }
                   
                    
                } catch (SQLException ex) {
                    
                         handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                         
                } catch (ParseException ex) {
                    
                        handleError(PARSE_ERROR, event.getSender(), null);
                        
                }
                catch (IndexOutOfBoundsException ex) {
                      
                       handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                }
            }
            
        //*****************************************************************************************    
        // *******************************   ClIENTE   *******************************************
        //*****************************************************************************************
             @Override
            public void clientes(TokenEvent event) {
                
                try {
                    
                    String correo = event.getSender();
                    
                    if(usuario.esUsuario(correo)){
                        
                        if(usuario.getRolbyCorreo(correo)== 4 || usuario.getRolbyCorreo(correo)==1){
                            
                           switch (event.getAction()){
                        
                                case Token.AGREGAR: 
                                    
                                    cliente.guardarCliente(event.getParams());
                                    simpleNotifySuccess(event.getSender(), "Cliente guardado correctamente");
                                    break;
                                case Token.EDITAR:
                                    cliente.editarCliente(event.getParams());
                                    simpleNotifySuccess(event.getSender(), "Cliente editado correctamente");
                                    break;
                                case Token.ELIMINAR:
                                    cliente.eliminarCliente(event.getParams());
                                    simpleNotifySuccess(event.getSender(), "Cliente eliminado correctamente");
                                    break;
                                case Token.LISTAR:
                                    tableNotifySuccess(event.getSender(), "Lista de Clientes", NCliente.HEADERS, cliente.listarCliente());
                                break; 
                                case Token.VER:
                                    //
                                    break;

                            }  
                            
                        }else{
                             handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                    }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }
                    
                   
                    
                } catch (SQLException ex) {
                    
                         handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                         
                } catch (ParseException ex) {
                    
                          handleError(PARSE_ERROR, event.getSender(), null);
                          
                }
                 catch (IndexOutOfBoundsException ex) {
          
                         handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                 }
                
            }
            
        //*****************************************************************************************    
        // *******************************   CONDUCTOR   ****************************************
        //*****************************************************************************************
             @Override
        public void conductores(TokenEvent event) {
                  
            try {
                String correo = event.getSender();

                if(usuario.esUsuario(correo)){

                    if(usuario.getRolbyCorreo(correo)== 4 || usuario.getRolbyCorreo(correo)==1){

                        switch (event.getAction()){
                        
                            case Token.AGREGAR: 
                                   
                                conductor.guardarConductor(event.getParams());
                                simpleNotifySuccess(event.getSender(), "Conductor Guardado correctamente");

                                break;
                            case Token.EDITAR:
                                conductor.editarConductor(event.getParams());
                                simpleNotifySuccess(event.getSender(), "Conductor Editado correctamente");

                                break;
                            case Token.ELIMINAR:
                                conductor.eliminarConductor(event.getParams());
                                simpleNotifySuccess(event.getSender(), "Conductor Eliminado correctamente");

                                break;
                            case Token.LISTAR:
                                tableNotifySuccess(event.getSender(), "Lista de Conductores", NConductor.HEADERS, conductor.listarConductor());
                                break; 
                            case Token.VER:
                                      //
                                break;

                        }

                    }else{
                             handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                    }
                }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                }
       
                    
            } catch (SQLException ex) {
                    
                handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                         
            } catch (ParseException ex) {
                    
                handleError(PARSE_ERROR, event.getSender(), null);
                        
            }
            catch (IndexOutOfBoundsException ex) {
          
            handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
            }
        }
            
        //*****************************************************************************************    
        // *******************************   PERSONAL  ********************************************
        //*****************************************************************************************
              @Override
            public void personal(TokenEvent event) {
                 
                try {
                    
                    String correo = event.getSender();
        
                    if(usuario.esUsuario(correo)){

                        if(usuario.getRolbyCorreo(correo)== 4 || usuario.getRolbyCorreo(correo)==1){

                         switch (event.getAction()){
                        
                            case Token.AGREGAR: 
                                
                               personal.guardarPersonal(event.getParams());
                               simpleNotifySuccess(event.getSender(), "Personal guardado correctamente");
                                break;
                            case Token.EDITAR:
                                personal.editarPersonal(event.getParams());
                                simpleNotifySuccess(event.getSender(), "Personal Editado correctamente");
                                break;
                            case Token.ELIMINAR:
                               personal.eliminarPersonal(event.getParams());
                               simpleNotifySuccess(event.getSender(), "Personal Eliminado correctamente");
                                break;
                            case Token.LISTAR:
                               tableNotifySuccess(event.getSender(), "Lista de Personal", NPersonal.HEADERS,personal.listarPersonal());
                                break; 
                            case Token.VER:
                                  //
                                break;

                        }   

                        }else{
                             handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                    }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }
           
                } catch (SQLException ex) {
                    
                        handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                        
                } catch (ParseException ex) {
                    
                        handleError(PARSE_ERROR, event.getSender(), null);
                        
                }
                catch (IndexOutOfBoundsException ex) {
          
                        handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                        
                }
            }
            
        //*****************************************************************************************    
        // *******************************   VEHICULO  *******************************************
        //*****************************************************************************************
            
            @Override
            public void vehiculos(TokenEvent event) {
                  
                try {
                     String correo = event.getSender();
        
                    if(usuario.esUsuario(correo)){

                        if(usuario.getRolbyCorreo(correo)== 4 || usuario.getRolbyCorreo(correo)==1){
                            switch (event.getAction()){
                        
                                case Token.AGREGAR: 
                                    
                                   vehiculo.RegistrarVehiculo(event.getParams(),event.getSender());
                                   simpleNotifySuccess(event.getSender(), "Vehiculo  guardado correctamente");
                                    break;
                                case Token.EDITAR:
                                   vehiculo.editarVehiculo(event.getParams(),event.getSender());
                                    simpleNotifySuccess(event.getSender(), "Vehiculo  editado correctamente");
                                    break;
                                case Token.ELIMINAR:
                                   vehiculo.eliminarVehiculo(event.getParams());
                                    simpleNotifySuccess(event.getSender(), "Vehiculo  guardado correctamente");
                                    break;
                                case Token.LISTAR:
                                    tableNotifySuccess(event.getSender(), "Lista de vehiculos", NVehiculo.HEADERS,vehiculo.listarVehiculos());
                                    break; 
                                case Token.VER:
                                      //
                                    break;

                            }

                        }else{
                             handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                    }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }
                   
                } catch (SQLException ex) {
                    
                        handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                        
                } catch (ParseException ex) {
                    
                       handleError(PARSE_ERROR, event.getSender(), null);
                       
                }
                catch (IndexOutOfBoundsException ex) {
          
                handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                
                }
            }
         
        //*****************************************************************************************    
        // *******************************   SERVICIO  ********************************************
        //*****************************************************************************************
            @Override
            public void servicios(TokenEvent event) {
                   
                try {
                    String correo = event.getSender();
        
                        if(usuario.esUsuario(correo)){

                            if(usuario.getRolbyCorreo(correo)== 4 || usuario.getRolbyCorreo(correo)==1){

                                 switch (event.getAction()){
                        
                                    case Token.AGREGAR: 
                                       
                                       servicio.RegistrarServicio(event.getParams(),event.getSender());
                                       simpleNotifySuccess(event.getSender(), "Servicio Guardado correctamente");
                                        break;
                                    case Token.EDITAR:
                                       servicio.editarServcio(event.getParams(),event.getSender());
                                       simpleNotifySuccess(event.getSender(), "Servicio Editado correctamente");
                                        break;
                                    case Token.ELIMINAR:
                                       servicio.eliminarServicio(event.getParams());
                                       simpleNotifySuccess(event.getSender(), "Servicio Eliminado correctamente");
                                        break;
                                    case Token.LISTAR:
                                       tableNotifySuccess(event.getSender(), "Lista de Servicios", NServicio.HEADERS,servicio.listarServicio());
                                       break; 
                                    case Token.VER:
                                          //
                                        break;
                                }

                            }else{
                                 handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                            }
                        }else{
                            handleError(LOGIN_ERROR, event.getSender(), null);
                        }
                   
                    
                 } catch (SQLException ex) {
        
                     handleError(CONSTRAINTS_ERROR, event.getSender(), null);
             
                 } catch (ParseException ex) {
        
                        handleError(PARSE_ERROR, event.getSender(), null);
            
                    }
                catch (IndexOutOfBoundsException ex) {
          
                       handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
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
                            solicitud.reservarSolicitud(event.getParams(),event.getSender());
                            System.out.println("tarifa del viaje: "+ solicitud.getTarifa());
                           
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
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
        // *******************************   GASTOS OPERATIVOS  ********************************************
        //*****************************************************************************************
            @Override
            public void gastosOperativos(TokenEvent event) {
                
                try {
                    String correo = event.getSender();
                    if (usuario.esUsuario(correo)){
                        if(usuario.getRolbyCorreo(correo)== 1 || usuario.getRolbyCorreo(correo)==4){
                            
                           switch (event.getAction()){
                        
                            case Token.AGREGAR: 
                           
                                gastos.guardarGastosOperativos(event.getParams(),event.getSender());
                                simpleNotifySuccess(event.getSender(), "Gasto Guardado correctamente");
                                break;
                            case Token.EDITAR:
                                gastos.editarGastosOperativos(event.getParams(),event.getSender());
                                simpleNotifySuccess(event.getSender(), "Gasto Editado correctamente");
                                break;
                            case Token.ELIMINAR:
                                gastos.eliminarGastosOperativos(event.getParams());
                                simpleNotifySuccess(event.getSender(), "Gasto Eliminar correctamente");
                                break;
                            case Token.LISTAR:
                                 tableNotifySuccess(event.getSender(), "Lista Gastos Operativos", NGastosOperativos.HEADERS,gastos.listarGastosOperativos());
                                break; 
                            case Token.VER:
                                  //
                                break;
                        } 
                            
                        }else{
                             handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                    }else{
                         handleError(LOGIN_ERROR, event.getSender(), null);
                    }   
                    
                } catch (SQLException ex) {
        
                     handleError(CONSTRAINTS_ERROR, event.getSender(), null);
             
                 } catch (ParseException ex) {
        
                        handleError(PARSE_ERROR, event.getSender(), null);
            
                    }
                catch (IndexOutOfBoundsException ex) {
          
                       handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                 }
            }
            
            
        //*****************************************************************************************    
        // *******************************   PROMOCION  *******************************************
        //*****************************************************************************************
            
            @Override
            public void promociones(TokenEvent event) {
                
                try {
                    
                    String correo = event.getSender();
    
                    if (usuario.esUsuario(correo)){
                        if(usuario.getRolbyCorreo(correo)== 1 || usuario.getRolbyCorreo(correo)==4){

                            switch (event.getAction()){

                                case Token.AGREGAR: 
                                  
                                   promocion.registrarPromocion(event.getParams(),event.getSender());
                                   simpleNotifySuccess(event.getSender(), "Promocion guardado correctamente");
                                    break;
                                case Token.EDITAR:
                                   promocion.editarPromocion(event.getParams(),event.getSender());
                                   simpleNotifySuccess(event.getSender(), "Promocion Editada correctamente");
                                    break;
                                case Token.ELIMINAR:
                                   promocion.eliminarPromocion(event.getParams());
                                   simpleNotifySuccess(event.getSender(), "Promocion Eliminada correctamente");
                                    break;
                                case Token.LISTAR:
                                  tableNotifySuccess(event.getSender(), "Lista De Promociones", NPromocion.HEADERS,promocion.listarPromocion());
                                break; 
                                case Token.VER:
                                      //
                                break;

                            }

                        }else{
                            
                            handleError(AUTHORIZATION_ERROR, event.getSender(), null);
                        }
                        
                    }else{
                        
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }

                            

                  } catch (SQLException ex) {
        
                     handleError(CONSTRAINTS_ERROR, event.getSender(), null);
             
                 } catch (ParseException ex) {
        
                        handleError(PARSE_ERROR, event.getSender(), null);
            
                    }
                catch (IndexOutOfBoundsException ex) {
          
                       handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
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
                handleError(event.getAction(), event.getSender(), event.getParams());
            }

            //*****************************************************************************************    
            // *******************************   VIAJE  ********************************************
            //*****************************************************************************************
            
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
                            System.out.println("tarifa del viaje: "+ solicitud.getTarifa());
                           ArrayList<String[]> p = new ArrayList<>();  // Cambiado de List a ArrayList
                            String[] data = {String.valueOf(idSolicitud), String.valueOf(solicitud.getTarifa()), event.getParams(1)}; // id_solicitud, tarifa del viaje, ruta
                            p.add(data);
                                    
                            tableNotifySuccess("andresrock1611@gmail.com", "VIAJE", NSolicitarServicio.HEADERS,p);
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
        // *******************************   METODO DE PAGO ********************************************
        //*****************************************************************************************
            @Override
            public void metodo_de_pago(TokenEvent event) {
                
                try {
                    
                    String correo = event.getSender();
    
        
                    if (usuario.esUsuario(correo)){
                       switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                           
                           metodo.registrarTarjeta(event.getParams(),event.getSender());
                           simpleNotifySuccess(event.getSender(), "Metodo de Pago guardado correctamente");
                            break;
                        case Token.EDITAR:
                           metodo.editarTarjeta(event.getParams(),event.getSender());
                           simpleNotifySuccess(event.getSender(), "Metodo de Pago editado correctamente");
                            break;
                        case Token.ELIMINAR:
                           metodo.eliminarMetodoDePago(event.getParams());
                           simpleNotifySuccess(event.getSender(), "Metodo de Pago Eliminado correctamente");
                            break;
                        case Token.LISTAR:
                            tableNotifySuccess(event.getSender(), "Lista De Metodos de Pagos", NMetodo_de_pago.HEADERS,metodo.listarMetodoDePago(event.getSender()));
                            break; 
                        case Token.VER:
                              //
                            break;
                       
                    }
                    }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }
                    
                    
                    
                 } catch (SQLException ex) {
        
                     handleError(CONSTRAINTS_ERROR, event.getSender(), null);
             
                 } catch (ParseException ex) {
        
                        handleError(PARSE_ERROR, event.getSender(), null);
            
                    }
                catch (IndexOutOfBoundsException ex) {
          
                       handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                 }
            }
         //*****************************************************************************************    
        // *******************************   METODO DE PAGO ********************************************
        //*****************************************************************************************           
            
        public void ayuda(TokenEvent event) {
                try{
                    if(usuario.esUsuario(event.getSender())){
                        if(usuario.getRolbyCorreo(event.getSender()) == 1){
                            simpleNotifyAyudaPersonal(event.getSender(), "Ayuda para el personal");
                        }else if(usuario.getRolbyCorreo(event.getSender()) == 2){
                            simpleNotifyAyudaConductor(event.getSender(),  "Ayuda para el conductor");
                        }else if(usuario.getRolbyCorreo(event.getSender()) == 3){
                            simpleNotifyAyudaCliente(event.getSender(),  "Ayuda para el cliente");
                        }else if(usuario.getRolbyCorreo(event.getSender()) == 4){
                            simpleNotifyAyuda(event.getSender(),  "Ayuda para el administrador");
                        }else{
                             System.out.println("No existe el id del rol del usuario");       
                        }
                    }else{
                        handleError(LOGIN_ERROR, event.getSender(), null);
                    }
                } catch (NumberFormatException ex) {
                    handleError(NUMBER_FORMAT_ERROR, event.getSender(), null);
                } catch (IndexOutOfBoundsException ex) {
                    handleError(INDEX_OUT_OF_BOUND_ERROR, event.getSender(), null);
                } catch (SQLException ex) {
                    handleError(CONSTRAINTS_ERROR, event.getSender(), null);
                }
            }

        public void start() {
           Thread thread = new Thread(mailVerificationThread);
           thread.setName("Mail Verfication Thread");
           thread.start();
       }
    
    
    @Override
    public void onReceiveEmailEvent(List<Email> emails) {
        emails.stream().map((email) -> new Interpreter(email.getSubject(), email.getFrom())).map((interpreter) -> {
            interpreter.setListener((ITokenEventListener) MailApplication.this);//revisar el casteo
            return interpreter;
        }).map((interpreter) -> new Thread(interpreter)).map((thread) -> {
            thread.setName("Interpreter Thread");
            return thread;
        }).forEachOrdered((thread) -> {
            thread.start();
        });
    
    }

    @Override
    public void solicitar(TokenEvent event) {
       
    }
    
    ////////////////////////////////////////////////////////////////////////////
    private void handleError(int type, String email, List<String> args) {
        Email emailObject = null;

        switch (type) {
            case Token.ERROR_CHARACTER:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Caracter desconocido",
                    "No se pudo ejecutar el comando [" + args.get(0) + "] debido a: ",
                    "El caracter \"" + args.get(1) + "\" es desconocido."
                }));
                break;
            case Token.ERROR_COMMAND:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Comando desconocido",
                    "No se pudo ejecutar el comando [" + args.get(0) + "] debido a: ",
                    "No se reconoce la palabra \"" + args.get(1) + "\" como un comando válido"
                }));
                break;
            case CONSTRAINTS_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Error al interactuar con la base de datos",
                    "Referencia a información inexistente"
                }));
                break;
            case NUMBER_FORMAT_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Error en el tipo de parámetro",
                    "El tipo de uno de los parámetros es incorrecto"
                }));
                break;
            case INDEX_OUT_OF_BOUND_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Cantidad de parámetros incorrecta",
                    "La cantidad de parámetros para realizar la acción es incorrecta"
                }));
                break;
            case PARSE_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Error al procesar la fecha",
                    "La fecha introducida posee un formato incorrecto"
                }));
                break;
            case AUTHORIZATION_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Acceso denegado",
                    "Usted no posee los permisos necesarios para realizar la acción solicitada"
                }));
                break;
            case LOGIN_ERROR:
                emailObject = new Email(email, Email.SUBJECT,
                        HtmlBuilder.generarText(new String[]{
                    "Acceso denegado",
                    "Usted no se encuentra como uno de nuestros usuarios, por favor contacte con nuestro supervisor"
                }));
                break;
        }
        sendEmail(emailObject);
    }
    
    private void simpleNotifyAyuda(String email, String title){
        //Email.SUBJECT = "Request Response";
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateAyuda(title));
        sendEmail(emailObject);   
    }
    
    private void simpleNotifyAyudaPersonal(String email, String title){
            Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateAyudaPersonal(title));
        sendEmail(emailObject); 
    }
    
    private void simpleNotifyAyudaConductor(String email, String title){
            Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateAyudaConductor(title));
        sendEmail(emailObject); 
    }
    
    private void simpleNotifyAyudaCliente(String email, String title){
            Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateAyudaCliente(title));
        sendEmail(emailObject); 
    }

    private void simpleNotifySuccess(String email, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generarText(new String[]{
            "Petición realizada correctamente",
            message
        }));
        sendEmail(emailObject);
    }

    private void simpleNotify(String email, String title, String topic, String message) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generarText(new String[]{
            title, topic, message
        }));
        sendEmail(emailObject);
    }

    private void tableNotifySuccess(String email, String title, String[] headers, ArrayList<String[]> data) {
        Email emailObject;
        emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generateTable(title, headers, data));
        sendEmail(emailObject);
    }

    private void simpleTableNotifySuccess(String email, String title, String[] headers, String[] data) {
        Email emailObject = new Email(email, Email.SUBJECT,
                HtmlBuilder.generarTable(title, headers, data));
                
        sendEmail(emailObject);
    }

    private void sendEmail(Email email) {
        SendEmailThread sendEmail = new SendEmailThread(email);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }    
    
}
