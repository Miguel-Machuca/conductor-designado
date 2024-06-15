package Utils;
import java.util.List;

public class HtmlBuilder {

    public static String generateTable(String title, String[] headers, List<String[]> data) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(title));
        html.append("<table><thead>");

        for (String header : headers) {
            html.append("<th>").append(header).append("</th>");
        }
        html.append("</thead><tbody>");

        for (String[] row : data) {
            html.append("<tr>");
            for (String value : row) {
                html.append("<td>").append(value).append("</td>");
            }
            html.append("</tr>");
        }

        html.append("</tbody></table>");
        html.append(getHTMLFin());
        return html.toString();
    }
    
    public static String generarText(String[] args) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(args[0]));
        html.append("<div class=\"content\">");
        for (int i = 1; i < args.length; i++) {
            html.append("<center><h3>").append(args[i]).append("</h3></center>");
        }
        html.append("</div>");
        html.append(getHTMLFin());
        return html.toString();
    }

    public static String generateAyuda(String title) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(title));
        html.append("<div class=\"content\">");
        html.append(getUseCaseTable());
        html.append("</div>");
        html.append(getHTMLFin());
        return html.toString();
    }
    
    public static String generateAyudaPersonal(String title) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(title));
        html.append("<div class=\"content\">");
        html.append(getUseCaseEmpleado());
        html.append("</div>");
        html.append(getHTMLFin());
        return html.toString();
    }

    public static String generateAyudaConductor(String title) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(title));
        html.append("<div class=\"content\">");
        html.append(getUseCaseConductor());
        html.append("</div>");
        html.append(getHTMLFin());
        return html.toString();
    }
    
    public static String generateAyudaCliente(String title) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(title));
        html.append("<div class=\"content\">");
        html.append(getUseCaseCliente());
        html.append("</div>");
        html.append(getHTMLFin());
        return html.toString();
    }

    private static String getHTMLInicio() {
        return "<!DOCTYPE html>"
                + "<html lang=\"es\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Casos de uso del sistema para EMAIL</title>"
                + "<style>"
                + "body {font-family: Arial, sans-serif; margin: 0; padding: 0;  }"
                + "h1, h2 { text-align: center; }"
                + "header { background-color: #0568fd; color: white; padding: 15px 20px; display: flex; align-items: center; justify-content: space-between; } "
                + ".header-content { display: flex; align-items: center; justify-content: center; flex: 1; position: relative; } "
                + ".header-content h1 { position: absolute; left: 50%; transform: translateX(-50%); margin: 0; } "
                + ".logo { margin-left: auto; } "
                + ".dibujo { margin-right: auto; } "
                + "header img { height: 75px; } "
                + "footer { background-color: #333; color: white; padding: 10px 0; text-align: center; position: fixed; width: 100%; bottom: 0; } "
                + ".content { padding: 20px; } "
                + "table { border-collapse: collapse; width: 100%; } "
                + "th, td { text-align: left; padding: 8px; border: 1px solid #ddd; } "
                + "th { background-color: #011f4b; color: #ffffff; } "
                + ".highlight { background-color: #f3f3f3; } "
                + "h1, h2 { font-family: Arial, sans-serif; } "
                + "ul { list-style-type: none; padding: 0; } "
                + "ul li { margin-bottom: 5px; } "
                + "</style></head><body>";
    }

    private static String getHTMLFin() {
        return "<footer><p>&copy; 2024 INF513-SC Grupo04. Todos los derechos reservados.</p></footer></body></html>";
    }

    private static String getHeader(String title) {
        String dibujo = "https://tse2.mm.bing.net/th?id=OIG3.MNdznBVn.3rxfYskX1.O&pid=ImgGn";
        String logoUrl = "https://th.bing.com/th/id/OIP.0Qj2z6_GZaKWX8xMctxpfgAAAA?rs=1&pid=ImgDetMain"; // Reemplaza esta URL con el enlace de tu imagen
        return "<header>"
                + "<div class=\"dibujo\">"
                + "<img src=\"" + dibujo + "\" alt=\"Imagen de la Aplicacion\">"
                + "</div>"
                + "<div class=\"header-container\">"
                + "<h1>Sistema de Gestión de Conductores Designados</h1>"
                + "<h2>" + title + "</h2>"
                + "</div>"
                + "<div class=\"logo\">"
                + "<img src=\"" + logoUrl + "\" alt=\"Logo de la Empresa\">"
                + "</div>"
                + "</header>";
    }
    
    private static String getUseCaseTable() {
        return "<h1>Casos de uso del sistema para EMAIL</h1>"
                + "<h2>Consideraciones</h2>"
                + "<ul>"
                + "<li>1) No ingresar caracteres especiales, la letra Ñ (ñ), ni caracteres con tildes.</li>"
                + "<li>2) El formato para fechas es AAAA-MM-DD, por ejemplo 1998-03-14.</li>"
                + "<li>3) El sexo es el caracter 'F' para femenino y 'M' para masculino.</li>"
                + "<li>4) Solo se aceptaran numeros de celular de hasta 12 digitos.</li>"
                + "<li>5) Solo se aceptan contraseñas de 8 a 10 caracteres, minimo una minuscula, mayuscula, numero y caracter especial.</li>"
                + "<li>6) Todos los campos que contengan espacios entre medio, deben ser reemplazados por el barra baja_EJ: Agustin_Rossi</li>"
                + "<li>7) El estado del personal puede ser activo o inactivo</li>"
                + "<li>8) La categoria de la licencia puede ser A,B o C</li>"
                + "<li>9) La categoria de la licencia puede ser A,B o C</li>"
                + "<li>10) El estado del vehiculo puede ser \"activo\", \"inactivo\", \"en servicio\", \"fuera de servicio\"</li>"
                + "<li>11) </li>"
                + "</ul>"                
                + "<table>"
                + "<tbody>"
                + "<tr><th colspan=\"3\">CU1. Gestión de Usuarios(Personal, Conductores, Clientes).</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Personal</td></tr>"
                + "<tr><td>Formato: personal agregar [ nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, salario, estado, cargo] </td></tr>"
                + "<tr><td>Ejemplo: personal agregar [ Andrea, chavez , Andrea@gmail.com, password, 63489070, 1998-06-21, femenino, 2500.7, activo, empleado] </td></tr>"
                + "<tr><td class=\"highlight\">Registrar Conductor</td></tr>"
                + "<tr><td>Formato: conductor agregar [ nombres, apellidos , email, contraseña, celular, fecha de nacimiento, genero, Nº de licencia, categoria de licencia, fecha de vencimiento de licencia]   </td></tr>"
                + "<tr><td>Ejemplo: conductor agregar [ roger, sossa, roger@gmail.com, password, 63489070, 1998-06-22, masculino, 76364, A, 2025-08-22]   </td></tr>"
                + "<tr><td class=\"highlight\">Registrar Cliente</td></tr>"
                + "<tr><td>Formato: cliente agregar [nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, cedula de identidad]  </td></tr>"
                + "<tr><td>Ejemplo: cliente agregar [felipe, chavez , felipe@gmail.com,12345678,63489070, 1998-06-21, masculino,7987833]  </td></tr>"
                + "<tr><td class=\"highlight\">Modificar Personal</td></tr>"
                + "<tr><td>Formato: personal editar [id del personal, nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, femenino, salario, estado, cargo] </td></tr>"
                + "<tr><td>Ejemplo: personal editar [13, Andrea editado , chavez , Andrea@gmail.com,   12345678,63489070, 1998-06-21, femenino,2500.7,activo,empleado] </td></tr>"
                + "<tr><td class=\"highlight\">Modificar Conductor</td></tr>"
                + "<tr><td>Formato: conductor editar [id del conductor, nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, Nº de licencia, categoria de licencia, fecha de vencimiento de licencia] </td></tr>"
                + "<tr><td>Ejemplo: conductor editar [11, erika editado , mamani , erika@gmail.com,   12345678,63489070, 1998-06-21, masculino,76364,A, 2025-08-22] </td></tr>"
                + "<tr><td class=\"highlight\">Modificar Cliente</td></tr>"
                + "<tr><td>Formato: cliente editar [id del cliente, nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, cedula de identidad] </td></tr>"
                + "<tr><td>Ejemplo: cliente editar [6,Andres editado , chavez , andy@gmail.com,12345,63489070, 1998-06-21, masculino,7987833] </td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Personal</td></tr>"
                + "<tr><td>Formato: personal eliminar [id del personal] </td></tr>"
                + "<tr><td>Ejemplo: personal eliminar [14] </td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Conductor</td></tr>"
                + "<tr><td>Formato: conductor eliminar [id del conductor] </td></tr>"
                + "<tr><td>Ejemplo: conductor eliminar [11] </td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Cliente</td></tr>"
                + "<tr><td>Formato: cliente eliminar [id del cliente] </td></tr>"
                + "<tr><td>Ejemplo: cliente eliminar [8] </td></tr>"
                + "<tr><td class=\"highlight\">Listar Personal</td></tr>"
                + "<tr><td>Formato: personal listar []</td></tr>"
                + "<tr><td>Ejemplo: personal listar </td></tr>"
                + "<tr><td class=\"highlight\">Listar Conductor</td></tr>"
                + "<tr><td>Formato: conductor listar [] </td></tr>"
                + "<tr><td>Ejemplo: conductor listar </td></tr>"
                + "<tr><td class=\"highlight\">Listar Cliente</td></tr>"
                + "<tr><td>Formato: cliente listar [] </td></tr>"
                + "<tr><td>Ejemplo: cliente listar </td></tr>"
                + "<tr><th colspan=\"3\">CU2. Gestión de Vehículos.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo agregar [marca, modelo, placa, Nº de seguro, fecha de vencimiento del seguro, estado, email del conductor]</td></tr>"
                + "<tr><td>Ejemplo: vehiculo agregar [Audi, A4, Bol123, 5367272, 2028-08-22, activo, luisfelipe.lfgo59@gmail.com]</td></tr>"
                + "<tr><td class=\"highlight\">Modificar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo editar [id del vehiculo, marca, modelo, placa, Nº de seguro, fecha de vencimiento del seguro, estado, email del conductor]</td></tr>"
                + "<tr><td>Ejemplo: vehiculo editar [1,Audi,A4,Bol123,5367272,2028-08-22,activo,juan@gmail.com]</td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo eliminar [id del vehiculo]</td></tr>"
                + "<tr><td>Ejemplo: vehiculo eliminar [1]</td></tr>"
                + "<tr><td class=\"highlight\">Listar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo listar []</td></tr>"
                + "<tr><td>Ejemplo: vehiculo listar </td></tr>"
                + "<tr><th colspan=\"3\">CU3. Gestión de Servicios.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Servicio</td></tr>"
                + "<tr><td>Formato: servicio agregar[nombre del servicio, descripcion del servicio ]</td></tr>"
                + "<tr><td>Ejemplo: servicio agregar[taxi premium, servcio de trasporte privado ]</td></tr>"
                + "<tr><td class=\"highlight\">Modificar Servicio</td></tr>"
                + "<tr><td>Formato: servicio editar [id del servicio, nombre del servicio, descripcion del servicio ]</td></tr>"
                + "<tr><td>Ejemplo: servicio editar [2, moto, servcio de trasporte privado ]</td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Servicio</td></tr>"
                + "<tr><td>Formato: servicio eliminar [id del servicio]</td></tr>"
                + "<tr><td>Ejemplo: servicio eliminar [3]</td></tr>"
                + "<tr><td class=\"highlight\">Listar Servicio</td></tr>"
                + "<tr><td>Formato: servicio listar []</td></tr>"
                + "<tr><td>Ejemplo: servicio listar </td></tr>"
                + "<tr><th colspan=\"3\">CU4. Gestión de Reservas.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Reserva</td></tr>"
                + "<tr><td>Formato: reserva agregar []</td></tr>"
                + "<tr><td>Ejemplo: reserva agregar []</td></tr>"
                + "<tr><td class=\"highlight\">Modificar Reserva</td></tr>"
                + "<tr><td>Formato: reserva editar []</td></tr>"
                + "<tr><td>Ejemplo: reserva editar []</td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Reserva</td></tr>"
                + "<tr><td>Formato: reserva eliminar []</td></tr>"
                + "<tr><td>Ejemplo: reserva eliminar []</td></tr>"
                + "<tr><td class=\"highlight\">Listar Reserva</td></tr>"
                + "<tr><td>Formato: reserva listar []</td></tr>"
                + "<tr><td>Ejemplo: reserva listar </td></tr>"
                + "<tr><th colspan=\"3\">CU5. Gestión de Gastos Operativos.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos agregar[monto del gasto operativo, fecha del gasto operativo, descripcion del gasto operativo, email del personal]</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos agregar[120.3,2024-10-15,pago de almuerzos, carlos@gmail.com]</td></tr>"                
                + "<tr><td class=\"highlight\">Modificar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos editar[id del gasto operativo, monto del gasto operativo, fecha del gasto operativo, descripcion del gasto operativo, email del personal]</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos editar[6, 122.3,2024-09-12,pago de biaticos editado, carlos@gmail.com]</td></tr>"                
                + "<tr><td class=\"highlight\">Eliminar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos eliminar[id del gasto operativo]</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos eliminar[1 ]</td></tr>"                
                + "<tr><td class=\"highlight\">Listar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos listar []</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos listar </td></tr>"                
                + "<tr><th colspan=\"3\">CU6. Gestión de Promociones.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Promocion</td></tr>"
                + "<tr><td>Formato: promocion agregar [nombre de la promocion, descripcion de la promocion, descuento, id del servicio,  fecha de inicio de la promocion,  fecha final de la promocion]</td></tr>"
                + "<tr><td>Ejemplo: promocion agregar [dia de la madre , promocion por el dia de la madre, 0.10,   1   ,  2024-05-01,  2024-05-31]</td></tr>"
                + "<tr><td class=\"highlight\">Modificar Promocion</td></tr>"
                + "<tr><td>Formato: promocion editar [id de la promocion, nombre de la promocion, descripcion de la promocion, descuento, id del servicio,  fecha de inicio de la promocion,  fecha final de la promocion]</td></tr>"
                + "<tr><td>Ejemplo: promocion editar [1,dia de la madre , promocion por el dia de la madre, 0.15,   1   ,  2024-05-01,  2024-05-30]</td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Promocion</td></tr>"
                + "<tr><td>Formato: promocion eliminar [id de la promocion]</td></tr>"
                + "<tr><td>Ejemplo: promocion eliminar [1]</td></tr>"
                + "<tr><td class=\"highlight\">Listar Promocion</td></tr>"
                + "<tr><td>Formato: promocion listar []</td></tr>"
                + "<tr><td>Ejemplo: promocion listar </td></tr>"
                + "<tr><th colspan=\"3\">CU7. Gestión de Pagos.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar pago</td></tr>"
                + "<tr><td>Formato: metodoDePago agregar[tipo de pago de pago, numero de tarjeta, nombre en la tarjeta, fecha de vencimiento en la tarjeta, cvv_cvc]</td></tr>"
                + "<tr><td>Ejemplo: metodoDePago agregar[Tarjeta de credito, 637828293,banco BNB,2025-07-09,7865]</td></tr>"
                + "<tr><td class=\"highlight\">Modificar pago</td></tr>"
                + "<tr><td>Formato: metodoDePago editar[id de metodo de pago,tipo de pago de pago, numero de tarjeta, nombre en la tarjeta, fecha de vencimiento en la tarjeta, cvv_cvc]</td></tr>"
                + "<tr><td>Ejemplo: metodoDePago editar[1,Tarjeta de credito, 637828293,banco union editado ,2025-07-09,7865]</td></tr>"
                + "<tr><th colspan=\"3\">CU8. Reportes y Estadísticas.</th></tr>"
                + "<tr><td class=\"highlight\">Ver Reportes y Estadisiticas</td></tr>"
                + "<tr><td>Formato</td></tr>"
                + "<tr><td>Ejemplo</td></tr>"
                + "<tr><th colspan=\"3\">CU9. Gestión de Roles.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Rol</td></tr>"
                + "<tr><td>Formato: rol agregar [ nombre del rol, descripcion del rol ] </td></tr>"
                + "<tr><td>Ejemplo: rol agregar [ a,b] </td></tr>"
                + "<tr><td class=\"highlight\">Modificar Rol</td></tr>"
                + "<tr><td>Formato: rol editar [ id del rol, nombre del rol, descripcion del rol] </td></tr>"
                + "<tr><td>Ejemplo: rol editar [ 2,conductor, es un conductor de la empresa] </td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Rol</td></tr>"
                + "<tr><td>Formato: rol eliminar [id del rol] </td></tr>"
                + "<tr><td>Ejemplo: rol eliminar [1] </td></tr>"
                + "<tr><td class=\"highlight\">Listar Rol</td></tr>"
                + "<tr><td>Formato: rol listar []</td></tr>"
                + "<tr><td>Ejemplo: rol listar </td></tr>"
                + "<tr><td class=\"highlight\">Asignar Rol</td></tr>"
                + "<tr><td>Formato: rol </td></tr>"
                + "<tr><td>Ejemplo: rol </td></tr>"
                + "<tr><th colspan=\"3\">CUx. Gestión de .</th></tr>"
                + "<tr><td class=\"highlight\">Registrar </td></tr>"
                + "<tr><td>Formato</td></tr>"
                + "<tr><td>Ejemplo</td></tr>"         
                + "</tbody>"
                + "</table>";
    }
    
    private static String getUseCaseEmpleado(){
        return "<h1>Casos de uso del sistema para EMAIL</h1>"
                + "<h2>Consideraciones</h2>"
                + "<ul>"
                + "<li>1) No ingresar caracteres especiales, la letra Ñ (ñ), ni caracteres con tildes.</li>"
                + "<li>2) El formato para fechas es AAAA-MM-DD, por ejemplo 1998-03-14.</li>"
                + "<li>3) El sexo es el caracter 'F' para femenino y 'M' para masculino.</li>"
                + "<li>4) Solo se aceptaran numeros de celular de hasta 12 digitos.</li>"
                + "<li>5) Solo se aceptan contraseñas de 8 a 10 caracteres, minimo una minuscula, mayuscula, numero y caracter especial.</li>"
                + "<li>6) Todos los campos que contengan espacios entre medio, deben ser reemplazados por el barra baja_EJ: Agustin_Rossi</li>"
                + "</ul>"                
                + "<table>"
                + "<tbody>"
                + "<tr><th colspan=\"3\">CU1. Gestión de Usuarios(Personal).</th></tr>"
                + "<tr><td class=\"highlight\">Modificar Personal</td></tr>"
                + "<tr><td>Formato: personal editar [id del personal, nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, femenino, salario, estado, cargo] </td></tr>"
                + "<tr><td>Ejemplo: personal editar [13, Andrea editado , chavez , Andrea@gmail.com,   12345678,63489070, 1998-06-21, femenino,2500.7,activo,empleado] </td></tr>"                  
                + "<tr><th colspan=\"3\">CU5. Gestión de Gastos Operativos.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos agregar[monto del gasto operativo, fecha del gasto operativo, descripcion del gasto operativo]</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos agregar[120.3,2024-10-15,pago de almuerzos, carlos@gmail.com]</td></tr>"                
                + "<tr><td class=\"highlight\">Modificar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos editar[id del gasto operativo, monto del gasto operativo, fecha del gasto operativo, descripcion del gasto operativo]</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos editar[6, 122.3,2024-09-12,pago de biaticos editado, carlos@gmail.com]</td></tr>"                
                + "<tr><td class=\"highlight\">Eliminar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos eliminar[id del gasto operativo]</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos eliminar[1 ]</td></tr>"                
                + "<tr><td class=\"highlight\">Listar Gasto Operativo</td></tr>"
                + "<tr><td>Formato: gastosOperativos listar []</td></tr>"
                + "<tr><td>Ejemplo: gastosOperativos listar </td></tr>"                
                + "<tr><th colspan=\"3\">CUx. Gestión de .</th></tr>"
                + "<tr><td class=\"highlight\">Registrar </td></tr>"
                + "<tr><td>Formato</td></tr>"
                + "<tr><td>Ejemplo</td></tr>"         
                + "</tbody>"
                + "</table>";
    }
    
    private static String getUseCaseConductor(){
        return "<h1>Casos de uso del sistema para EMAIL</h1>"
                + "<h2>Consideraciones</h2>"
                + "<ul>"
                + "<li>1) No ingresar caracteres especiales, la letra Ñ (ñ), ni caracteres con tildes.</li>"
                + "<li>2) El formato para fechas es AAAA-MM-DD, por ejemplo 1998-03-14.</li>"
                + "<li>3) El sexo es el caracter 'F' para femenino y 'M' para masculino.</li>"
                + "<li>4) Solo se aceptaran numeros de celular de hasta 12 digitos.</li>"
                + "<li>5) Solo se aceptan contraseñas de 8 a 10 caracteres, minimo una minuscula, mayuscula, numero y caracter especial.</li>"
                + "<li>6) Todos los campos que contengan espacios entre medio, deben ser reemplazados por el barra baja_EJ: Agustin_Rossi</li>"
                + "</ul>"                
                + "<table>"
                + "<tbody>"
                + "<tr><th colspan=\"3\">CU1. Gestión de Usuarios(Conductores).</th></tr>"                
                + "<tr><td class=\"highlight\">Modificar Conductor</td></tr>"
                + "<tr><td>Formato: conductor editar [id del conductor, nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, Nº de licencia, categoria de licencia, fecha de vencimiento de licencia] </td></tr>"
                + "<tr><td>Ejemplo: conductor editar [11, erika editado , mamani , erika@gmail.com,   12345678,63489070, 1998-06-21, masculino,76364,A, 2025-08-22] </td></tr>"                
                + "<tr><th colspan=\"3\">CU2. Gestión de Vehículos.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo agregar [marca, modelo, placa, Nº de seguro, fecha de vencimiento del seguro, estado, email del conductor]</td></tr>"
                + "<tr><td>Ejemplo: vehiculo agregar [Audi, A4, Bol123, 5367272, 2028-08-22, activo, luisfelipe.lfgo59@gmail.com]</td></tr>"
                + "<tr><td class=\"highlight\">Modificar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo editar [id del vehiculo, marca, modelo, placa, Nº de seguro, fecha de vencimiento del seguro, estado, email del conductor]</td></tr>"
                + "<tr><td>Ejemplo: vehiculo editar [1,Audi,A4,Bol123,5367272,2028-08-22,activo,juan@gmail.com]</td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Vehiculo</td></tr>"
                + "<tr><td>Formato: vehiculo eliminar [id del vehiculo]</td></tr>"
                + "<tr><td>Ejemplo: vehiculo eliminar [1]</td></tr>"
                + "<tr><th colspan=\"3\">CUx. Gestión de .</th></tr>"
                + "<tr><td class=\"highlight\">Modificar estado del conductor </td></tr>"
                + "<tr><td>Formato: conductor estado [] </td></tr>"
                + "<tr><td>Ejemplo: conductor estado [] </td></tr>"
                + "<tr><th colspan=\"3\">CUx. Gestión de .</th></tr>"
                + "<tr><td class=\"highlight\">Terminar Viaje </td></tr>"
                + "<tr><td>Formato: conductor viaje [] </td></tr>"
                + "<tr><td>Ejemplo: conductor viaje [] </td></tr>"
                + "<tr><th colspan=\"3\">CUx. Gestión de .</th></tr>"
                + "<tr><td class=\"highlight\">Registrar </td></tr>"
                + "<tr><td>Formato</td></tr>"
                + "<tr><td>Ejemplo</td></tr>"         
                + "</tbody>"
                + "</table>";
    }
    
    private static String getUseCaseCliente(){
        return "<h1>Casos de uso del sistema para EMAIL</h1>"
                + "<h2>Consideraciones</h2>"
                + "<ul>"
                + "<li>1) No ingresar caracteres especiales, la letra Ñ (ñ), ni caracteres con tildes.</li>"
                + "<li>2) El formato para fechas es AAAA-MM-DD, por ejemplo 1998-03-14.</li>"
                + "<li>3) El sexo es el caracter 'F' para femenino y 'M' para masculino.</li>"
                + "<li>4) Solo se aceptaran numeros de celular de hasta 12 digitos.</li>"
                + "<li>5) Solo se aceptan contraseñas de 8 a 10 caracteres, minimo una minuscula, mayuscula, numero y caracter especial.</li>"
                + "<li>6) Todos los campos que contengan espacios entre medio, deben ser reemplazados por el barra baja_EJ: Agustin_Rossi</li>"
                + "</ul>"                
                + "<table>"
                + "<tbody>"
                + "<tr><th colspan=\"3\">CU1. Gestión de Usuarios(Clientes).</th></tr>"                
                + "<tr><td class=\"highlight\">Registrar Cliente</td></tr>"
                + "<tr><td>Formato: cliente agregar [nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, cedula de identidad]  </td></tr>"
                + "<tr><td>Ejemplo: cliente agregar [felipe, chavez , felipe@gmail.com,12345678,63489070, 1998-06-21, masculino,7987833]  </td></tr>"                
                + "<tr><td class=\"highlight\">Modificar Cliente</td></tr>"
                + "<tr><td>Formato: cliente editar [id del cliente, nombres, apellidos, email, contraseña, celular, fecha de nacimiento, genero, cedula de identidad] </td></tr>"
                + "<tr><td>Ejemplo: cliente editar [6,Andres editado , chavez , andy@gmail.com,12345,63489070, 1998-06-21, masculino,7987833] </td></tr>"                
                + "<tr><td class=\"highlight\">Eliminar Cliente</td></tr>"
                + "<tr><td>Formato: cliente eliminar [id del cliente] </td></tr>"
                + "<tr><td>Ejemplo: cliente eliminar [8] </td></tr>"                                
                  + "<tr><th colspan=\"3\">CU4. Gestión de Reservas.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Reserva</td></tr>"
                + "<tr><td>Formato: reserva agregar []</td></tr>"
                + "<tr><td>Ejemplo: reserva agregar []</td></tr>"
                + "<tr><td class=\"highlight\">Modificar Reserva</td></tr>"
                + "<tr><td>Formato: reserva editar []</td></tr>"
                + "<tr><td>Ejemplo: reserva editar []</td></tr>"
                + "<tr><td class=\"highlight\">Eliminar Reserva</td></tr>"
                + "<tr><td>Formato: reserva eliminar []</td></tr>"
                + "<tr><td>Ejemplo: reserva eliminar []</td></tr>"                
                + "<tr><th colspan=\"3\">CU7. Gestión de Pagos.</th></tr>"
                + "<tr><td class=\"highlight\">Registrar pago</td></tr>"
                + "<tr><td>Formato: metodoDePago agregar[tipo de pago de pago, numero de tarjeta, nombre en la tarjeta, fecha de vencimiento en la tarjeta, cvv_cvc]</td></tr>"
                + "<tr><td>Ejemplo: metodoDePago agregar[Tarjeta de credito, 637828293,banco BNB,2025-07-09,7865]</td></tr>"
                + "<tr><td class=\"highlight\">Modificar pago</td></tr>"
                + "<tr><td>Formato: metodoDePago editar[id de metodo de pago,tipo de pago de pago, numero de tarjeta, nombre en la tarjeta, fecha de vencimiento en la tarjeta, cvv_cvc]</td></tr>"
                + "<tr><td>Ejemplo: metodoDePago editar[1,Tarjeta de credito, 637828293,banco union editado ,2025-07-09,7865]</td></tr>"                
                + "<tr><th colspan=\"3\">CUx. Gestión de Viajes</th></tr>"
                + "<tr><td class=\"highlight\">Registrar Viaje</td></tr>"
                + "<tr><td>Formato</td></tr>"
                + "<tr><td>Ejemplo</td></tr>"         
                + "<tr><td class=\"highlight\">Cancelar Viaje</td></tr>"
                + "<tr><td>Formato</td></tr>"
                + "<tr><td>Ejemplo</td></tr>"         
                + "</tbody>"
                + "</table>";
    }

    public static String generarTable(String title, String[] headers, String[] data) {
        StringBuilder html = new StringBuilder();
        html.append(getHTMLInicio());
        html.append(getHeader(title));
        html.append("<div class=\"content\">");
        html.append(getTable(title, headers, data));
        html.append("</div>");
        html.append(getHTMLFin());
        return html.toString();
    }

    private static String getTable(String title, String[] headers, String[] data) {
        StringBuilder table = new StringBuilder();
        table.append("<div align=\"center\"><h2>").append(title).append("<br></h2></div>");
        table.append("<table width=\"98%\" border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"2\" bgcolor=\"#CCCCCC\">");
        table.append("<thead><tr>");
        for (String header : headers) {
            table.append("<th>").append(header).append("</th>");
        }
        table.append("</tr></thead>");
        table.append("<tbody>");
        boolean alternate = false;
        for (String row : data) {
            String[] columns = row.split(",", 3);
            table.append("<tr style=\"background-color: ")
                    .append(alternate ? "#f3f3f3" : "#ffffff")
                    .append("; font-weight: bold; color: #252850;\">");
            for (String column : columns) {
                table.append("<td>").append(column).append("</td>");
            }
            table.append("</tr>");
            alternate = !alternate;
        }
        table.append("</tbody>");
        table.append("</table>");
        return table.toString();
    }

    public static String casoDeUso() {
    String title = "AYUDA PARA EL ROL ";
    String[] headers = {
        "Nombre de Caso de Uso", 
        "Función", 
        "Detalle"
    };
    String[] data = {
        
        "Rol,Insertar Rol<br>Listar los Roles<br>Editar Rol<br>Eliminar Rol,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Usuario,Insertar Usuario<br>Listar los Usuarios<br>Editar Usuario<br>Eliminar Usuario,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Cliente,Insertar Cliente<br>Listar los Clientes<br>Editar Cliente<br>Eliminar Cliente,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Conductor,Insertar Conductor<br>Listar los Conductores<br>Editar Conductor<br>Eliminar Conductor,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Personal,Insertar Personal<br>Listar los Personal<br>Editar Personal<br>Eliminar Personal,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Vehiculo,Insertar Vehiculo<br>Listar los Vehiculos<br>Editar Vehiculo<br>Eliminar Vehiculo,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Promocion,Insertar Promocion<br>Listar los Promociones<br>Editar Promocion<br>Eliminar Promocion,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Servicio,Insertar Servicio<br>Listar los Servicios<br>Editar Servicio<br>Eliminar Rol,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Transaccion,Insertar Transaccion<br>Listar los Transacciones<br>Editar Transaccion<br>Eliminar Transaccion,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Gasto Operativo,Insertar Gasto Operativo<br>Listar los Gastos Operativos<br>Editar Gasto Operativo<br>Eliminar Gasto Operativo,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Metodo de Pago,Insertar Metodo de Pago<br>Listar los Metodos de Pago<br>Editar Metodo de Pago<br>Eliminar Metodo de Pago,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        "Viaje,Insertar Viaje<br>Listar los Viajes<br>Editar Viaje<br>Eliminar Viaje,rol agregar [nombre, descripcion]<br>rol listar<br>rol editar [id, nombre, descripcion<br>rol eliminar [id]",
        
 
    };

    StringBuilder html = new StringBuilder();
    html.append(getHTMLInicio());
    html.append(getHeader(title));
    html.append("<div class=\"content\">");
    html.append("<h2 style=\"padding-left: 10px;\">CONSIDERACIONES</h2>");
    html.append("<h3 style=\"padding-left: 10px;\">1) No ingresar caracteres especiales</h3>");    
    html.append("<h3 style=\"padding-left: 10px;\">2) Los campos de fecha y dia_desembolso tienen que ser de forma: YYYY-MM-DD</h3>");
    html.append("<h3 style=\"padding-left: 10px;\">3) Los campos de fecha y dia_desembolso tienen que ser de forma: YYYY-MM-DD</h3>");
    html.append("<h3 style=\"padding-left: 10px;\">4) Los campos de fecha y dia_desembolso tienen que ser de forma: YYYY-MM-DD</h3>");
    html.append(getTable(title, headers, data));
    html.append("</div>");
    html.append(getHTMLFin());
    return html.toString();
}
    
    
    
}
