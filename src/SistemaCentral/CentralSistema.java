package SistemaCentral;

import Datos.DGastoOperativo;
import Datos.DTransacciones;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class CentralSistema {

    public static void main(String[] args) throws IOException {
        MailApplication app = new MailApplication();
        app.start();
    }
     /*  DTransacciones transacciones = new DTransacciones();

        try {
            // Prueba del método listarGraficaTransaccionesPorMetodoPago
            transacciones.listarGraficaTransaccionesPorMetodoPago("2024-07-01", "2024-07-10");
            System.out.println("El gráfico se ha generado correctamente.");
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
            System.err.println("Error al generar el gráfico: " + e.getMessage());
        } finally {
            transacciones.Disconnect();
        }
    }*/
}

