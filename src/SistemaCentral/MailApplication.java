/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaCentral;
import Datos.DRol;
import Datos.DUsuario;
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
import communication.MailVerificationThread;
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
    
     public MailApplication() {
        mailVerificationThread = new MailVerificationThread();
        mailVerificationThread.setEmailEventListener(MailApplication.this);
        usuario = new NUsuario();
        cliente = new NCliente();
        rol = new NRol();
    }
    
     //*****************************************************************************************    
        // *******************************   ROL    *********************************************
        //*****************************************************************************************
            @Override
            public void roles(TokenEvent event) {
               
                System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            rol.guardarRol(event.getParams());
                            simpleNotifySuccess(event.getSender(), "Rol guardado correctamente");
                            
                            break;
                        case Token.EDITAR:
                            rol.editarRol(event.getParams());
                            break;
                        case Token.ELIMINAR:
                            rol.eliminarRol(event.getParams());
                            break;
                        case Token.LISTAR:
                            
                           tableNotifySuccess(event.getSender(), "Lista de Roles", DRol.HEADERS, rol.listarRoles());
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
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            usuario.editarUsuario(event.getParams());
                            break;
                        case Token.ELIMINAR:
                            usuario.eliminarUsuario(event.getParams());
                            break;
                        case Token.LISTAR:
                            
                            tableNotifySuccess(event.getSender(), "Lista de Usuarios", DUsuario.HEADERS, usuario.listarUsuarios());
                            
                        break;
                        case Token.VER:
                            usuario.listarUsuarios();
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
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
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
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar conductores ");
                           conductor.guardarConductor(event.getParams());
                           // simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            conductor.editarConductor(event.getParams());
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
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           vehiculo.editarVehiculo(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           vehiculo.eliminarVehiculo(event.getParams());
                            break;
                        case Token.LISTAR:
                           //cliente.listarUsuarios();
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
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           servicio.editarServcio(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           servicio.eliminarServicio(event.getParams());
                            break;
                        case Token.LISTAR:
                           //cliente.listarUsuarios();
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
                 System.out.println("cantidad de parametros:    "+event.countParams());
                System.out.println(event.toString());
                try {
                    switch (event.getAction()){
                        
                        case Token.AGREGAR: 
                            System.out.println("entramos a agregar gastos operativos ");
                            gastos.guardarGastosOperativos(event.getParams(),event.getSender());
                            simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                            gastos.editarGastosOperativos(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                            gastos.eliminarGastosOperativos(event.getParams());
                            break;
                        case Token.LISTAR:
                            //cliente.listarUsuarios();
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
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
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

            @Override
            public void viajes(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                           metodo.registrarTarjeta(event.getParams(),event.getSender());
                           simpleNotifySuccess(event.getSender(), "Usuario guardado correctamente");
                            break;
                        case Token.EDITAR:
                           metodo.editarTarjeta(event.getParams(),event.getSender());
                            break;
                        case Token.ELIMINAR:
                           metodo.eliminarMetodoDePago(event.getParams());
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        }
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
