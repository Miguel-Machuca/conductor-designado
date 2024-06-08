/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Connection.sqlconnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Utils.DateString;
import java.text.ParseException;

/**
 *
 * @author andre
 */
public class DMetodo_de_pago {
     private final  sqlconnection connection;

    public DMetodo_de_pago() {
        this.connection = new sqlconnection(
                "postgres",
                "admin", 
                "127.0.0.1",
                "5432", "db_tecno");
        
    }
    // cuando la connecion sea distinto de null se podra hacer la desconeccion a la base de datos 
    public void Disconnect(){
        if( connection!= null ){
            connection.closeConnection();
        }
    }
    public void guardarMetodoEfectivo(int idCliente) throws SQLException, ParseException {
        String query = "INSERT INTO metodo_de_pago (tipo_de_metodo_de_pago, id_cliente) VALUES (?, ?)";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, "Efectivo");
        ps.setInt(2, idCliente);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMetodo_de_pago.java: Ocurrió un error al insertar un método de pago en guardarMetodoEfectivo()");
        }
    }
    public void guardar(String tipoMetodoPago, String numeroTarjeta, String nombreTarjeta, String fechaVencimiento, Integer cvvCvc, int idCliente) throws SQLException, ParseException {
        String query = "INSERT INTO metodo_de_pago (tipo_de_metodo_de_pago, numero_tarjeta, nombre_en_la_tarjeta, fecha_vencimiento, cvv_cvc, id_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, tipoMetodoPago);
        ps.setObject(2, numeroTarjeta);
        ps.setString(3, nombreTarjeta);
        ps.setDate(4, DateString.StringToDateSQL(fechaVencimiento));
        ps.setObject(5, cvvCvc);
        ps.setInt(6, idCliente);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMetodo_de_pago.java: Ocurrió un error al insertar un método de pago en guardar()");
        }
    }

    public void editar(int id, String tipoMetodoPago, String numeroTarjeta, String nombreTarjeta, String fechaVencimiento, Integer cvvCvc, int idCliente) throws SQLException, ParseException {
        String query = "UPDATE metodo_de_pago SET tipo_de_metodo_de_pago = ?, numero_tarjeta = ?, nombre_en_la_tarjeta = ?, fecha_vencimiento = ?, cvv_cvc = ?, id_cliente = ? WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setString(1, tipoMetodoPago);
        ps.setObject(2, numeroTarjeta);
        ps.setString(3, nombreTarjeta);
        ps.setDate(4, DateString.StringToDateSQL(fechaVencimiento));
        ps.setObject(5, cvvCvc);
        ps.setInt(6, idCliente);
        ps.setInt(7, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DMetodo_de_pago.java: Ocurrió un error al editar un método de pago en editar()");
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM metodo_de_pago WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            System.err.println("No se encontró ningún método de pago con el ID especificado.");
        } else {
            System.out.println("Método de pago eliminado exitosamente.");
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> metodosPago = new ArrayList<>();
        String query = "SELECT * FROM metodo_de_pago";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] metodoPago = {
                String.valueOf(rs.getInt("id")),
                rs.getString("tipo_de_metodo_de_pago"),
                rs.getString("numero_tarjeta"),
                rs.getString("nombre_en_la_tarjeta"),
                rs.getString("fecha_vencimiento"),
                rs.getString("cvv_cvc"),
                String.valueOf(rs.getInt("id_cliente"))
            };
            metodosPago.add(metodoPago);
        }

        return metodosPago;
    }

    public String[] ver(int id) throws SQLException {
        String[] metodoPago = null;
        String query = "SELECT * FROM metodo_de_pago WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            metodoPago = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("tipo_de_metodo_de_pago"),
                rs.getString("numero_tarjeta"),
                rs.getString("nombre_en_la_tarjeta"),
                rs.getString("fecha_vencimiento"),
                rs.getString("cvv_cvc"),
                String.valueOf(rs.getInt("id_cliente"))
            };
        }

        return metodoPago;
    }
}
