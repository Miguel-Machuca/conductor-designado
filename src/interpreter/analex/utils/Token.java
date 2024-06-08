/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.analex.utils;

/**
 *
 * @author ronal
 */
public class Token {
    private int name;// si es CU, ACTION o ERROR
    private int attribute; // que tipo ya sea CU, ACTION o ERROR
    
    //constantes numericas para manejar el analex
    public static final int CU = 0;
    public static final int ACTION = 1;
    public static final int PARAMS = 2;
    public static final int END = 3;
    public static final int ERROR = 4;
   
   //  casos de uso aprobados 
    public static final int USUARIOS = 100;
   
    public static final int VEHICULOS = 101;
    public static final int SERVICIOS = 102;
    public static final int RESERVAS = 103;
    public static final int GASTOS_OPERATIVOS = 104;
    public static final int PROMOCIONES = 105;
    public static final int PAGOS = 106;
    public static final int REPORTES_ESTADISTICAS = 107;
    
    //ADICIONAMOS 
    public static final int VIAJES = 108;
    public static final int CLIENTES = 109;
    public static final int CONDUCTOR = 110;
    public static final int PERSONAL = 111;
    public static final int ROL = 112;
    public static final int METODO_DE_PAGO = 113;
    
    
    //ajustar de acuerdo a sus acciones con valores entre 200 a 299
    //Titulos de las acciones generales
    public static final int ADD = 200;
    public static final int DELETE = 201;
    public static final int MODIFY = 202;
    public static final int GET = 203;
    public static final int VERIFY = 204;
    public static final int CANCEL = 205;
    public static final int REPORT = 206;
    
    //acciones par los casos de usos aprobados
    public static final int AGREGAR = 207;
    public static final int EDITAR = 208;
    public static final int ELIMINAR = 209;
    public static final int VER = 210;
    public static final int LISTAR = 211;
    
    
    public static final int ERROR_COMMAND = 300;
    public static final int ERROR_CHARACTER = 301;
    
    //constantes literales para realizar un efecto de impresión
    public static final String LEXEME_CU = "caso de uso";
    public static final String LEXEME_ACTION = "action";
    public static final String LEXEME_PARAMS = "params";
    public static final String LEXEME_END = "end";
    public static final String LEXEME_ERROR = "error";
    
    // ajustar de acuerdo a sus casos de uso con valores en string
    //Titulos de casos de uso con string
    

     public static final String LEXEME_USUARIOS = "usuario";
     public static final String LEXEME_VEHICULOS = "vehiculo";
     public static final String LEXEME_SERVICIOS = "servicio";
     public static final String LEXEME_RESERVAS = "reserva";
     public static final String LEXEME_GASTOS_OPERATIVOS = "gastosOperativos";
     public static final String LEXEME_PROMOCIONES = "promocion";
     public static final String LEXEME_PAGOS = "pago";
     public static final String LEXEME_REPORTES_ESTADISTICAS = "reportes";
     
     public static final String LEXEME_VIAJES = "viaje";
     public static final String LEXEME_CLIENTES = "cliente";
     public static final String LEXEME_CONDUCTOR = "conductor";
     public static final String LEXEME_PERSONAL = "personal";
     public static final String LEXEME_ROL = "rol";
     public static final String LEXEME_METODO_DE_PAGO = "metodoDePago";
    
    //ajustar de acuerdo a sus acciones con valores en string
    //Titulos de las acciones generales en string
    public static final String LEXEME_ADD = "add";
    public static final String LEXEME_DELETE = "delete";
    public static final String LEXEME_MODIFY = "modify";
    public static final String LEXEME_GET = "get";
    public static final String LEXEME_VERIFY = "verify";
    public static final String LEXEME_CANCEL = "cancel";
    public static final String LEXEME_REPORT = "report";
    
    public static final String LEXEME_AGREGAR = "agregar"; 
    public static final String LEXEME_EDITAR = "editar";
    public static final String LEXEME_ELIMINAR = "eliminar";
    public static final String LEXEME_VER = "ver";
    public static final String LEXEME_LISTAR = "listar";
    
    public static final String LEXEME_ERROR_COMMAND = "UNKNOWN COMMAND";
    public static final String LEXEME_ERROR_CHARACTER = "UNKNOWN CHARACTER";
    
    /**
     * Constructor por default.
     */
    public Token(){
        
    }
    
    /**
     * Constructor parametrizado por el literal del token
     * @param token 
     */
    //No Tocar
    public Token(String token){
        int id = findByLexeme(token);
        if(id != -1){
            if(100 <= id && id < 200){
                this.name = CU;
                this.attribute = id;
            } else if(200 <= id && id < 300){
                this.name = ACTION;
                this.attribute = id;
            }
        } else {
            this.name = ERROR;
            this.attribute = ERROR_COMMAND;
            System.err.println("Class Token.Constructor dice: \n "
                    + " El lexema enviado al constructor no es reconocido como un token \n"
                    + "Lexema: "+token);
        }
    }
    
    /**
     * Constructor parametrizado 2.
     * @param name 
     */
    public Token(int name){
        this.name = name;
    }
    
    /**
     * Constructor parametrizado 3.
     * @param name
     * @param attribute 
     */
    public Token(int name, int attribute){
        this.name = name;
        this.attribute = attribute;
    }
    
    // Setters y Getters
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }
    
    @Override
    public String toString(){
        if(0 <= name  && name <=1){
            return "< " + getStringToken(name) + " , " + getStringToken(attribute) + ">";
        }else if(name == 2){
            return "< " + getStringToken(name) + " , " + attribute + ">";
        }else if(3 == name){
            return "< " + getStringToken(name) + " , " + "_______ >";
        } else if(name == 4){
            return "< " + getStringToken(name) + " , " + getStringToken(attribute) + ">";
        }
        return "< TOKEN , DESCONOCIDO>";
    }
    
    /**
     * Devuelve el valor literal del token enviado
     * Si no lo encuentra retorna N: token.
     * @param token
     * @return 
     */
    //ajustar de acuerdo a sus CU
    public String getStringToken(int token){
        switch(token){
            case CU:
                return LEXEME_CU;
            case ACTION:
                return LEXEME_ACTION;
            case PARAMS:
                return LEXEME_PARAMS;
            case END:
                return LEXEME_END;
            case ERROR:
                return LEXEME_ERROR;
                
            //CU
            case ADD:
                return LEXEME_ADD;
            case DELETE:
                return LEXEME_DELETE;
            case MODIFY:
                return LEXEME_MODIFY;
            case GET:
                return LEXEME_GET;
            case VERIFY:
                return LEXEME_VERIFY;
            case CANCEL:
                return LEXEME_CANCEL;
            case REPORT:
                return LEXEME_REPORT;
            //motodos
            case USUARIOS:
                return LEXEME_USUARIOS;
            case VEHICULOS:
                return LEXEME_VEHICULOS;
            case SERVICIOS:
                return LEXEME_SERVICIOS;
            case RESERVAS:
                return LEXEME_RESERVAS;
            case GASTOS_OPERATIVOS:
                return LEXEME_GASTOS_OPERATIVOS;
            case PROMOCIONES:
                return LEXEME_PROMOCIONES;
            case PAGOS:
                return LEXEME_PAGOS;
            case REPORTES_ESTADISTICAS:
                return LEXEME_REPORTES_ESTADISTICAS;
            case VIAJES:
                return LEXEME_VIAJES;
            case CLIENTES:
                return LEXEME_CLIENTES;
            case CONDUCTOR:
                return LEXEME_CONDUCTOR;
               case PERSONAL:
                return LEXEME_PERSONAL;
            case ROL:
                return LEXEME_ROL;    
            case METODO_DE_PAGO:
                return LEXEME_METODO_DE_PAGO;   
                
                
                
            //acciones    
            case AGREGAR:
                return LEXEME_AGREGAR;
            case EDITAR:
                return LEXEME_EDITAR;
            case ELIMINAR:
                return LEXEME_ELIMINAR;
            case VER:
                return LEXEME_VER;
            case LISTAR:
                return LEXEME_LISTAR;
            
                
                
                
            case ERROR_COMMAND:
                return LEXEME_ERROR_COMMAND;
            case ERROR_CHARACTER:
                return LEXEME_ERROR_CHARACTER;
            default:
                return "N: " + token;
        }
    }
    
    /**
     * Devuelve el valor numerico del lexema enviado
     * Si no lo encuentra retorna -1.
     * @param lexeme
     * @return 
     */
    //ajustar de acuerdo a sus CU
    private int findByLexeme(String lexeme){
        switch(lexeme){
            case LEXEME_CU:
                return CU;
            case LEXEME_ACTION:
                return ACTION;
            case LEXEME_PARAMS:
                return PARAMS;
            case LEXEME_END:
                return END;
            case LEXEME_ERROR:
                return ERROR;
              
            //CU
           
            //ACTION    
            case LEXEME_ADD:
                return ADD;
            case LEXEME_DELETE:
                return DELETE;
            case LEXEME_MODIFY:
                return MODIFY;
            case LEXEME_GET:
                return GET;
            case LEXEME_VERIFY:
                return VERIFY;
            case LEXEME_CANCEL:
                return CANCEL;
            case LEXEME_REPORT:
                return REPORT;
           
                
           case LEXEME_USUARIOS:
                return USUARIOS;
            case LEXEME_VEHICULOS:
                return VEHICULOS;
            case LEXEME_SERVICIOS:
                return SERVICIOS;
            case LEXEME_RESERVAS:
                return RESERVAS;
            case LEXEME_GASTOS_OPERATIVOS:
                return GASTOS_OPERATIVOS;
            case LEXEME_PROMOCIONES:
                return PROMOCIONES;
            case LEXEME_PAGOS:
                return PAGOS;
            case LEXEME_REPORTES_ESTADISTICAS:
                return REPORTES_ESTADISTICAS;
            case LEXEME_VIAJES:
                return VIAJES;
            case LEXEME_CLIENTES:
                return CLIENTES;
            case LEXEME_CONDUCTOR:
                return CONDUCTOR;
            case LEXEME_PERSONAL:
                return PERSONAL;
            case LEXEME_ROL:
                return ROL;
            case LEXEME_METODO_DE_PAGO:
                return METODO_DE_PAGO;
                
                
                
            case LEXEME_AGREGAR:
                return AGREGAR;
            case LEXEME_EDITAR:
                return EDITAR;
            case LEXEME_ELIMINAR:
                return ELIMINAR;
            case LEXEME_VER:
                return VER;
            case LEXEME_LISTAR:
                return LISTAR;
                
                
            case LEXEME_ERROR_COMMAND:
                return ERROR_COMMAND;            
            case LEXEME_ERROR_CHARACTER:
                return ERROR_CHARACTER;            
            default:
                return -1;
        }
    }
}
