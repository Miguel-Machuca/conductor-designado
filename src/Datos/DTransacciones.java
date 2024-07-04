/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Connection.sqlconnection;
import Utils.DateString;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author andre
 */
public class DTransacciones {

    private final sqlconnection connection;

    public DTransacciones() {
        this.connection = new sqlconnection(
                "grupo04sc",
                "grup004grup004",
                "mail.tecnoweb.org.bo",
                "5432", "db_grupo04sc");
    }

    // cuando la connecion sea distinto de null se podra hacer la desconeccion a la base de datos 
    public void Disconnect() {
        if (connection != null) {
            connection.closeConnection();
        }
    }

    public void guardar(int id_solicitar, String fecha, float monto, String estado, int id_metodo) throws SQLException, ParseException {
        String query = "INSERT INTO transacciones (id_solicitar, fecha, monto, estado, id_metodo) VALUES (?, ?, ?, ?, ?)";

        // Convertir el String de fecha a Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(fecha, formatter);
        Timestamp fechaTimestamp = Timestamp.valueOf(dateTime);

        try (Connection conn = connection.connect();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id_solicitar);
            ps.setTimestamp(2, fechaTimestamp); // Usamos Timestamp para la fecha
            ps.setFloat(3, monto);
            ps.setString(4, estado);
            ps.setInt(5, id_metodo);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DTransacciones.java: Ocurrió un error al insertar una transacción en guardar()");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al insertar la transacción", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> transacciones = new ArrayList<>();
        String query = "SELECT * FROM transacciones";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String[] transaccion = {
                String.valueOf(rs.getInt("id")),
                rs.getTimestamp("fecha").toString(),
                String.valueOf(rs.getFloat("monto")),
                rs.getString("estado"),
                String.valueOf(rs.getInt("id_metodo"))
            };
            transacciones.add(transaccion);
        }

        return transacciones;
    }

    public String[] ver(int id) throws SQLException {
        String[] transaccion = null;
        String query = "SELECT * FROM transacciones WHERE id = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            transaccion = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getTimestamp("fecha").toString(),
                String.valueOf(rs.getFloat("monto")),
                rs.getString("estado"),
                String.valueOf(rs.getInt("id_metodo"))
            };
        }

        return transaccion;
    }

    //probar al final 
  public void listarGraficaTransaccionesPorMetodoPago(String fechaInicio, String fechaFin) throws SQLException, IOException, ParseException {
    String query = "SELECT metodo_de_pago.tipo_de_metodo_de_pago, SUM(transacciones.monto) AS total_monto "
                + "FROM transacciones "
                + "JOIN metodo_de_pago ON transacciones.id_metodo = metodo_de_pago.id "
                + "WHERE transacciones.fecha BETWEEN ? AND ? "
                + "GROUP BY metodo_de_pago.tipo_de_metodo_de_pago";

    ResultSet resultado = null;
    JFreeChart grafica;
    DefaultCategoryDataset datos = new DefaultCategoryDataset();
    try (Connection conn = connection.connect();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setTimestamp(1, Timestamp.valueOf(fechaInicio + " 00:00:00"));
        ps.setTimestamp(2, Timestamp.valueOf(fechaFin + " 23:59:59"));

        resultado = ps.executeQuery();

        if (!resultado.isBeforeFirst()) {
            throw new SQLException("No se encontraron registros en el rango de fechas especificado.");
        }

        while (resultado.next()) {
            datos.setValue(resultado.getDouble("total_monto"), "Monto de Transacciones", resultado.getString("tipo_de_metodo_de_pago"));
        }

        grafica = ChartFactory.createBarChart(
                "Reporte de Transacciones por Método de Pago",
                "Método de Pago",
                "Monto Total",
                datos,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        String dir = System.getProperty("user.dir");
        String path = dir + "\\src\\Imagen\\graficaTransaccionesPorMetodoPago.png";
        ChartUtilities.saveChartAsPNG(new File(path), grafica, 800, 400);
    }
}





   public List<String[]> reporte(String fechaInicio, String fechaFin) throws SQLException, ParseException {
    String query = "SELECT metodo_de_pago.tipo_de_metodo_de_pago, SUM(transacciones.monto) AS total_monto "
                + "FROM transacciones "
                + "JOIN metodo_de_pago ON transacciones.id_metodo = metodo_de_pago.id "
                + "WHERE transacciones.fecha BETWEEN ? AND ? "
                + "GROUP BY metodo_de_pago.tipo_de_metodo_de_pago";

    List<String[]> resultados = new ArrayList<>();

    try (Connection conn = connection.connect();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setTimestamp(1, Timestamp.valueOf(fechaInicio + " 00:00:00"));
        ps.setTimestamp(2, Timestamp.valueOf(fechaFin + " 23:59:59"));

        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.isBeforeFirst()) {
                throw new SQLException("No se encontraron registros en el rango de fechas especificado.");
            }

            while (rs.next()) {
                String[] fila = new String[2];
                fila[0] = rs.getString("tipo_de_metodo_de_pago");
                fila[1] = String.valueOf(rs.getDouble("total_monto"));
                resultados.add(fila);
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al generar el reporte de transacciones", e);
        throw e;
    }

    return resultados;
}


    public void finalizarTransaccion(int id, int idMetodoPago) throws SQLException, ParseException {
        String query = "UPDATE transacciones SET estado = ?, id_metodo = ? WHERE id = ?";

        try (Connection conn = connection.connect();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "completado");
            ps.setInt(2, idMetodoPago);
            ps.setInt(3, id);

            if (ps.executeUpdate() == 0) {
                System.err.println("Class DTransacciones.java dice: Ocurrió un error al finalizar la transacción en finalizarTransaccion()");
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al finalizar la transacción", e);
            throw e;
        }
    }

    public void actualizarMetodoPago(int idSolicitud, int idMetodoPago) throws SQLException {
        String query = "UPDATE transacciones SET id_metodo = ? WHERE id_solicitar = ?";
        PreparedStatement ps = connection.connect().prepareStatement(query);
        ps.setInt(1, idMetodoPago);
        ps.setInt(2, idSolicitud);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DTransaccion.java: Ocurrió un error al actualizar el método de pago en actualizarMetodoPago()");
        }
    }
}
