/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Connection.sqlconnection;
import Utils.DateString;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 *
 * @author mmach
 */
public class DGastoOperativo {
    private final sqlconnection connection;
    
    public DGastoOperativo(){
        this.connection = new sqlconnection(
                "grupo04sc",
                "grup004grup004", 
                "mail.tecnoweb.org.bo",
                "5432", "db_grupo04sc");
    }
    
    
    public void Disconnect(){
        if( connection!= null ){
            connection.closeConnection();
        }
    }
    

    public void guardar(String monto, String fecha, String descripcion, int idPersonal) throws SQLException, ParseException {
        
        String query = "INSERT INTO gasto_operativo (monto, fecha, descripcion, id_personal) VALUES (?, ?, ?, ?)";

        // Validación de entradas
        if (Float.parseFloat(monto) < 0 || fecha == null || fecha.isEmpty()) {
            throw new IllegalArgumentException("Parámetros inválidos: monto, fecha y idPersonal son obligatorios y deben ser válidos.");
        }

         try (Connection conn = connection.connect(); 
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setFloat(1, Float.parseFloat(monto));
            ps.setDate(2, DateString.StringToDateSQL(fecha));
            ps.setString(3, descripcion);
            ps.setInt(4, idPersonal);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar el registro en gasto_operativo guardar()");
            }
        } catch (SQLException | ParseException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al guardar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public void editar(int id, float monto, String fecha, String descripcion, int idPersonal) throws SQLException, ParseException {
        String query = "UPDATE gasto_operativo SET monto = ?, fecha = ?, descripcion = ?, id_personal = ? WHERE id = ?";

        // Validación de entradas
        if (id <= 0 || monto < 0 || fecha == null || fecha.isEmpty() || idPersonal <= 0) {
            throw new IllegalArgumentException("Parámetros inválidos: id, monto, fecha y idPersonal son obligatorios y deben ser válidos.");
        }

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setFloat(1, monto);
            ps.setDate(2, DateString.StringToDateSQL(fecha));
            ps.setString(3, descripcion);
            ps.setInt(4, idPersonal);
            ps.setInt(5, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo actualizar el registro en gasto_operativo editar()");
            }
        } catch (SQLException | ParseException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al actualizar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }

    public void eliminar(int id) throws SQLException {
        String query = "DELETE FROM gasto_operativo WHERE id = ?";

        // Validación de entradas
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que 0.");
        }

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo eliminar el registro en gasto_operativo");
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al eliminar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }
    }
    
    public String[] ver(int id) throws SQLException {
        String[] gastoOperativo = null;
        String query = "SELECT * FROM gasto_operativo WHERE id = ?";

        // Validación de entradas
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor que 0.");
        }

        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gastoOperativo = new String[]{
                        String.valueOf(rs.getInt("id")),
                        String.valueOf(rs.getFloat("monto")),
                        String.valueOf(rs.getDate("fecha")),
                        rs.getString("descripcion"),
                        String.valueOf(rs.getInt("id_personal"))
                    };
                }
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al consultar el gasto operativo", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }

        return gastoOperativo;
    }
    
    public List<String[]> listar() throws SQLException {
        List<String[]> gastosOperativos = new ArrayList<>();
        String query = "SELECT * FROM gasto_operativo";
        
        try (Connection conn = connection.connect(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String[] gastoOperativo = new String[]{
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getFloat("monto")),
                    String.valueOf(rs.getDate("fecha")),
                    rs.getString("descripcion"),
                    String.valueOf(rs.getInt("id_personal"))
                };
                gastosOperativos.add(gastoOperativo);
            }
        } catch (SQLException e) {
            // Logging del error
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al listar los gastos operativos", e);
            // Relanza la excepción para que el llamador la maneje
            throw e;
        }

        return gastosOperativos;
    }

     
     
    public void listarGraficaGastosPorFecha(String fechaInicio, String fechaFin) throws SQLException, IOException, ParseException {
    String query = "SELECT fecha, SUM(monto) AS total_gastos FROM gasto_operativo WHERE fecha BETWEEN ? AND ? GROUP BY fecha ORDER BY fecha";
    
    ResultSet resultado = null;
    JFreeChart grafica;
    DefaultCategoryDataset datos = new DefaultCategoryDataset();
    PreparedStatement ps = connection.connect().prepareStatement(query);
    ps.setDate(1, DateString.StringToDateSQL(fechaInicio));
    ps.setDate(2, DateString.StringToDateSQL(fechaFin));

    resultado = ps.executeQuery();
    
    if (!resultado.isBeforeFirst()) {
        throw new SQLException("No se encontraron registros en el rango de fechas especificado.");
    }
    
    while (resultado.next()) {
        datos.setValue(resultado.getDouble("total_gastos"), "Gastos Operativos", resultado.getDate("fecha").toString());
    }
    
    grafica = ChartFactory.createBarChart(
        "Reporte de Gastos Operativos por Fecha",
        "Fecha",
        "Total Gastos",
        datos,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );
    
    String dir = System.getProperty("user.dir");
    String path = dir + "\\src\\Imagen\\graficaGastosPorFecha.png";
    ChartUtilities.saveChartAsPNG(new File(path), grafica, 800, 400);
    
        System.out.println("se creo la imagen ");
}

   public List<String[]> reporte(String fechaInicio, String fechaFin) throws SQLException, ParseException {
        List<String[]> gastos = new ArrayList<>();
        String query = "SELECT fecha, SUM(monto) AS total_gastos FROM gasto_operativo WHERE fecha BETWEEN ? AND ? GROUP BY fecha ORDER BY fecha";
        
        try (Connection conn = connection.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDate(1, DateString.StringToDateSQL(fechaInicio));
            ps.setDate(2, DateString.StringToDateSQL(fechaFin));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                gastos.add(new String[] {
                    rs.getDate("fecha").toString(),
                    String.valueOf(rs.getFloat("total_gastos"))
                });
            }

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            throw e;
        }

        return gastos;
    }
    
    
}

