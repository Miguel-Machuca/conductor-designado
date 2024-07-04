/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
import Datos.DCliente;
import Datos.DConductor;
import Datos.DSolicitarServicio;
import Datos.DServicio;
import Datos.DTransacciones;
import Utils.Email;
import Utils.HtmlBuilder;
import communication.SendEmailThread;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author andre
 */
public class NSolicitarServicio {
    
    public static final String[] HEADERS =
        {"COD.","TARIFA","RUTA"};
    
    DCliente dcliente ;
    DConductor dConductor;
    DSolicitarServicio dSolicitarServicio;
    DServicio dservicio;
    DTransacciones dtransaccion;
    
   // public final float CostoReserva=(float) 0.002;//borrar luego
    private static final double R = 6371;
   
    
    
    public NSolicitarServicio(){
        dcliente = new DCliente();
        dConductor = new DConductor();
        dSolicitarServicio = new DSolicitarServicio();
        dtransaccion = new DTransacciones();
        dservicio = new DServicio();
     
 
    }
    
   
  
    // estoy usando este para viajes sin reservas 
   public int idRegistrarSolicitud(List<String> parametros, String correo) throws SQLException, ParseException, Exception {
    int clienteid = dcliente.getIdByCorreo(correo);
    int servicioid = Integer.parseInt(parametros.get(0));
    final int idMetodoPagoEfectivo = 1; // ID del método de pago "Efectivo" compartido

    if (clienteid != -1 && dservicio.existeServicio(servicioid)) {
      
        int conductor = elegirConductor();
        if (conductor != -1) {
            //String CorreoConductor = dConductor.getCorreoById(conductor);
            String shortUrl = parametros.get(1);
            String expandedUrl = expandUrl(shortUrl);
            System.out.println("Expanded URL: " + expandedUrl);
            String[] coords = extractCoordinates(expandedUrl);
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHorasolicitud = fechaHoraActual.format(formatter);

            double lat1 = Double.parseDouble(coords[0].split(",")[0]);
            double lon1 = Double.parseDouble(coords[0].split(",")[1]);
            double lat2 = Double.parseDouble(coords[1].split(",")[0]);
            double lon2 = Double.parseDouble(coords[1].split(",")[1]);
            double tarifaBase = 5.0;
            double tarifaPorKilometro = 4.50;
            double costoAdicional = 2.0;
            double tarifa = calcularTarifa(lat1, lon1, lat2, lon2, tarifaBase, tarifaPorKilometro, costoAdicional);
           

            int idSolicitud = dSolicitarServicio.idSolicitarServicio(
                                        fechaHorasolicitud,
                                        (float)tarifa,
                                        coords[0],
                                        coords[1],
                                        clienteid,
                                        servicioid,
                                        conductor,
                                        shortUrl
                                    );
            
            dtransaccion.guardar(idSolicitud, fechaHorasolicitud, (float)tarifa, "pendiente",idMetodoPagoEfectivo);// por defecto pagar con efectivo
           
            dSolicitarServicio.Disconnect();

            // Enviar correo al cliente
            String[] datosVehiculo = dConductor.obtenerDatosVehiculo(conductor);
            String mensajeCliente = String.format("Se encontró un conductor con el vehículo %s %s, placa %s. La tarifa es de %.2f Bs.",
                                                  datosVehiculo[1], datosVehiculo[0], datosVehiculo[2], tarifa);
            simpleNotifySuccess(correo, mensajeCliente);

            // Enviar correo al conductor
            String correoConductor = dConductor.getCorreoById(conductor);
            String mensajeConductor = String.format("Se le ha asignado un nuevo cliente. La tarifa del servicio es de %.2f Bs. El cliente tiene el correo %s." + "<br> Ruta:"+" "+ shortUrl
                    +"<br> Codigo Del Viaje:  " + idSolicitud,
                                                    tarifa, correo);
            simpleNotifySuccess(correoConductor, mensajeConductor);

            return idSolicitud;
        } else {
            //System.err.println("No se encuentra ningun conductor");
            simpleNotifySuccess(correo, "Lo sentimos, no hay conductores disponibles.");
        }
    }
    return -1; // Indica que no se pudo registrar la solicitud
}

    
    public void reservarSolicitud(List<String> parametros, String correo) throws SQLException, ParseException, Exception {
        
        int clienteid = dcliente.getIdByCorreo(correo);
        int servicioid = Integer.parseInt(parametros.get(0));

        if (clienteid != -1 && dservicio.existeServicio(servicioid) ) {// validamos que sea un cliente que sea el q solicita el servicio
            String shortUrl = parametros.get(1); //"https://maps.app.goo.gl/xZMLQNyxDjfBj6MR9";
            String expandedUrl = expandUrl(shortUrl);
            System.out.println("Expanded URL: " + expandedUrl);
            String[] coords = extractCoordinates(expandedUrl);
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHorasolicitud= fechaHoraActual.format(formatter);
             
            double lat1 = Double.parseDouble(coords[0].split(",")[0]);
            double lon1 = Double.parseDouble(coords[0].split(",")[1]);
            double lat2 = Double.parseDouble(coords[1].split(",")[0]);
            double lon2 = Double.parseDouble(coords[1].split(",")[1]);
            double tarifaBase = 5.0; // Tarifa base en Bs
            double tarifaPorKilometro = 4.50; // Tarifa por kilómetro en Bs
            double costoAdicional = 2.0; // Costos adicionales en Bs
            double tarifa = calcularTarifa(lat1, lon1, lat2, lon2, tarifaBase, tarifaPorKilometro, costoAdicional);
            // aqui vamos a validar las promociones (descuentos)
            
             //System.out.println("La tarifa del servicio es: " + tarifa);
            dSolicitarServicio.reservarServicio(
                                        fechaHorasolicitud,//fecha 
                                        parametros.get(2),// fecha de solicitud
                                        (float)tarifa,//costo 
                                        coords[0],//origen
                                        coords[1],// destino
                                        clienteid,//id cliente
                                        servicioid//id servicio                                        
                                        );
            dSolicitarServicio.Disconnect();
        }
    }

    private static String[] extractCoordinates(String url) {
    Pattern pattern = Pattern.compile("dir/([-\\d.]+,[-\\d.]+)/([^/@]+)");
    Matcher matcher = pattern.matcher(url);
    String[] coordinates = new String[2];
    if (matcher.find()) {
        String startCoords = matcher.group(1);
        String endCoords = matcher.group(2);
            coordinates[0] = startCoords;
            coordinates[1] = endCoords;
      
    } else {
        System.out.println("No coordinates found in the URL.");
    }
     return coordinates;
    }

    private static String expandUrl(String shortUrl) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(shortUrl).openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.connect();
        String expandedUrl = connection.getHeaderField("Location");
        connection.getInputStream().close();
        return expandedUrl;
    }
    
    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // distancia en kilómetros
    }

    public static double calcularTarifa(double lat1, double lon1, double lat2, double lon2, 
                                        double tarifaBase, double tarifaPorKilometro, 
                                        double costoAdicional) {
        double distancia = calcularDistancia(lat1, lon1, lat2, lon2);
        double costoDistancia = distancia * tarifaPorKilometro;
        return tarifaBase + costoDistancia + costoAdicional;
    }
    
    public Integer elegirConductor() throws SQLException {
        // Buscar un conductor libre
        int idConductor = dConductor.buscarLibre();

        // Verificar si se encontró un conductor libre
        if (idConductor != -1) {
            // Actualizar el estado del conductor a "ocupado" y devolver su ID
            dConductor.actualizarEstado(idConductor, "ocupado");
            return idConductor;
        } 
       
        return -1;
        
    }
    
    public Integer idBuscarConductor(int idRegistrarSolicitud){
        int idConductor = -1;
        try {
            idConductor = dSolicitarServicio.obtenerIdConductor(idRegistrarSolicitud);
        } catch (SQLException ex) {
            Logger.getLogger(NSolicitarServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idConductor;
    }
     public Integer idBuscarCliente(int idRegistrarSolicitud){
        int idConductor = -1;
        try {
            idConductor = dSolicitarServicio.obtenerIdCliente(idRegistrarSolicitud);
        } catch (SQLException ex) {
            Logger.getLogger(NSolicitarServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idConductor;
    }
    
   
    private void simpleNotifySuccess(String email, String message) {
    Email emailObject = new Email(email, Email.SUBJECT, HtmlBuilder.generarText(new String[]{
        "Petición realizada correctamente",
        message
    }));
    sendEmail(emailObject);
}

    private void sendEmail(Email email) {
        SendEmailThread sendEmail = new SendEmailThread(email);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }  
    
    // NSolicitarServicio.java

public void finalizarViaje(String correoConductor) throws SQLException {
    
        // Obtener el ID del conductor por su correo
        int idConductor = dConductor.getIdByCorreo(correoConductor);

        if (idConductor != -1) {
            // Obtener la solicitud pendiente para este conductor
            int idSolicitud = dSolicitarServicio.obtenerSolicitudPendientePorConductor(idConductor);

            if (idSolicitud != -1) {
                
                dSolicitarServicio.actualizarEstadoSolicitudDeServicio(idSolicitud, "completado");//se  finaliza el servicio
               
                dConductor.actualizarEstado(idConductor,"libre");//se libera el estado del conductor 
                
                // Obtener el correo del cliente asociado a la solicitud
                int idCliente = dSolicitarServicio.obtenerIdCliente(idSolicitud);
                String correoCliente = dcliente.getCorreoById(idCliente);
                    
                // Enviar un correo al cliente preguntando cómo desea pagar su viaje
                String mensajeCliente = String.format(
                    "Su viaje ha sido completado. El código del viaje es: "+ "<br>"+"  %d. " + "<br>"+
                    "Por favor, indique cómo desea pagar su viaje: " + "<br>"+
                    "1. Efectivo  " +"<br>"+
                    "2. Tarjeta de crédito  " +"<br>"+
                    "Responda a este correo con la opción de pago que prefiera.",
                    idSolicitud
                );
                simpleNotifySuccess(correoCliente, mensajeCliente);
            } else {
                simpleNotifySuccess(correoConductor, "No hay solicitudes pendientes");
                //System.out.println("No hay solicitudes pendientes para este conductor.");
            }
        } else {
            System.out.println("No se encontró un conductor con ese correo.");
        }

   
}

}
