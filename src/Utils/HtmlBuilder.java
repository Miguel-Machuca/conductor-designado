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

    private static String getHTMLInicio() {
        return "<!DOCTYPE html>"
                + "<html lang=\"es\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Ejemplo de Página con Header y Footer</title>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; } "
                + "h2 { text-align: center; }"
                + "header { background-color: #0568fd; color: white; padding: 15px 20px; display: flex; align-items: center; justify-content: space-between; } "
                + ".header-content { display: flex; align-items: center; justify-content: center; flex: 1; position: relative; } "
                + ".header-content h1 { position: absolute; left: 50%; transform: translateX(-50%); margin: 0; } "
                + ".logo { margin-left: auto; } "
                + "header img { height: 50px; } "
                + "footer { background-color: #333; color: white; padding: 10px 0; text-align: center; position: fixed; width: 100%; bottom: 0; } "
                + ".content { padding: 20px; } table { border-collapse: collapse; margin: 25px 0; font-size: 0.9em; min-width: 400px; border-radius: 5px 5px 0 0; overflow: hidden; width: 98%; } "
                + "thead { background-color: #011f4b; color: #ffffff; text-align: left; font-weight: bold; } "
                + "tbody { border-bottom: 2px solid #011f4b; } th, td { padding: 12px 15px; } tr { border-top: 1px solid #dddddd; border-bottom: 1px solid #dddddd; } "
                + "</style></head><body>";
    }

    private static String getHTMLFin() {
        return "<footer><p>&copy; 2024 Grupo04-SC. Todos los derechos reservados.</p></footer></body></html>";
    }

private static String getHeader(String title) {
    String logoUrl = "https://th.bing.com/th/id/OIP.0Qj2z6_GZaKWX8xMctxpfgAAAA?rs=1&pid=ImgDetMain"; // Reemplaza esta URL con el enlace de tu imagen
    return "<header>"
            + "<div class=\"header-container\">"
            + "<h1>Conductor Designado</h1>"
            + "<h2>" + title + "</h2>"
            + "</div>"
            + "<div class=\"logo\">"
            + "<img src=\"" + logoUrl + "\" alt=\"Logo de la Empresa\">"
            + "</div>"
            + "</header>";
}


    private static String getTable(String title, String[] headers, String[] data) {
        StringBuilder table = new StringBuilder();
        table.append("<div align=\"center\"><h2>").append(title).append("<br></h2></div>");
        table.append("<table width=\"250\" border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"2\" bgcolor=\"#CCCCCC\">");
        for (int i = 0; i < headers.length; i++) {
            table.append("<tr><td>").append(headers[i]).append("</td><td>").append(data[i]).append("</td></tr>");
        }
        table.append("</table>");
        return table.toString();
    }
    
    
    
}
