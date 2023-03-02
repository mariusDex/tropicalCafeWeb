
package model.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MotorMySQL;

/**
 *
 * @author Mariusz Broza
 */
public class UserDAO implements DAO<User, Integer>{
    
    // INICIOS DE CONSULTAS POR DEFECTO
    
    
    private static final String SQL_SELECT = "SELECT * FROM CUSTOMER WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE CUSTOMER SET ";
    private static final String SQL_DELETE = "DELETE FROM CUSTOMER WHERE ";
    private static final String SQL_INSERT = "INSERT INTO CUSTOMER VALUES(";
    
    private MotorMySQL motorMySQL = null;
    
    
    //BUILDER
    public UserDAO() {
           this.motorMySQL = new MotorMySQL();
    }
    
    /**
     * Desc : añade un usuario a la base de datos
     * @param bean : un objeto de clase User que porta todos los datos a insertar en la base de datos
     */
    @Override
    public void add(User bean) {
        String sql_filtro = "";
        String sql_final ="";
        
        
        try {
            this.motorMySQL.connect();
            
            sql_filtro += "null," 
                    + "'" + bean.getUserNickname() + "',"
                    + "'" + bean.getUserPassword() + "',"
                    + "'" + bean.getUserName() + "',"
                    + "'" + bean.getUserSurname() + "',"
                    + "'" + bean.getUserEmail() + "',"
                    + "'" + bean.getUserAddress() + "',"
                    + "'" + bean.getUserPhone() + "'";
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_INSERT + sql_filtro + ")";
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    /**
     * Desc : devuelve 1 o más filas de usuario/s
     * @param bean : objeto de tipo User que le pasamos como parámetro para parametrizar la búsqueda en la base de datos
     * @return  ArrayList<User> 
     */
    @Override
    public ArrayList<User> findAll(User bean) {
        String sql_filtro = "";
        String sql_final = "";
        ArrayList<User> userList = null;
        try {
            this.motorMySQL.connect();
            /*Parametrizar la consulta para que pueda filtrar por cualquier campo*/
            if(bean!=null){
                
                /*Quieres filtrar*/
                if(bean.getUserID()>0){
                    sql_filtro+=" AND ID_CLIENTE=" + bean.getUserID();
                }
                if(bean.getUserNickname() != null && bean.getUserNickname().length()>0){
                    sql_filtro+=" AND UPPER(NICKNAME)='" + bean.getUserNickname().toUpperCase() + "'";
                }
                 if(bean.getUserEmail() != null && bean.getUserEmail().length()>0){
                    sql_filtro+= " AND UPPER(EMAIL)='"+ bean.getUserEmail() +"'";
                }
                
            }
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_SELECT + sql_filtro;
            
            ResultSet resultset = this.motorMySQL.executeQuery(sql_final);
            
            // TRANSFORMADOR
            
            if(resultset!=null){
                    userList = new ArrayList();
                    // vamos metiendo todos los usuarios que devuelve el resultset dentro del arrayList "userList"
                    while(resultset.next()){// 3
                        User user = new User();
                        user.setUserID(resultset.getInt(1));
                        user.setUserNickname(resultset.getString(2));
                        user.setUserPassword(resultset.getString(3));
                        user.setUserName(resultset.getString(4));
                        user.setUserSurname(resultset.getString(5));
                        user.setUserEmail(resultset.getString(6));
                        user.setUserAddress(resultset.getString(7));
                        user.setUserPhone(resultset.getString(8));

                        userList.add(user);
                    }
            }
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

  
    /**
     * Desc : updatea al usuario 
     * @param bean : objeto de clase User que porta la información a updatear
     */ 
    @Override
    public void update(User bean) {
        String sql_filtro = "";
        String sql_final ="";
        
        
        try {
            this.motorMySQL.connect();
            if (bean != null) {
                
                if (bean.getUserNickname() != null && bean.getUserNickname().length() > 0) {
                    sql_filtro += "NICKNAME='" + bean.getUserNickname() + "'";
                }
                if (bean.getUserAddress() != null && bean.getUserAddress().length() > 0) {
                    if (sql_filtro.length() > 0) {
                        sql_filtro += ",ADDRESS='" + bean.getUserAddress() + "'";
                    } else {
                        sql_filtro += "ADDRESS='" + bean.getUserAddress() + "'";
                    }
                }

                if (bean.getUserEmail() != null && bean.getUserEmail().length() > 0) {
                    if (sql_filtro.length() > 0) {
                        sql_filtro += ",EMAIL='" + bean.getUserEmail() + "'";
                    } else {
                        sql_filtro += "EMAIL='" + bean.getUserEmail() + "'";
                    }
                }

                if (bean.getUserPhone() != null && bean.getUserPhone().length() > 0) {
                    if (sql_filtro.length() > 0) {
                        sql_filtro += ",PHONE_NUMBER='" + bean.getUserPhone() + "'";
                    } else {
                        sql_filtro += "PHONE_NUMBER='" + bean.getUserPhone() + "'";
                    }
                }

                if (bean.getUserPassword() != null && bean.getUserPassword().length() > 0) {
                    sql_filtro = "PASSWORD='" + bean.getUserPassword() + "'";

                    if (sql_filtro.length() > 0) {
                        sql_filtro += ",PASSWORD='" + bean.getUserPassword() + "'";
                    } else {
                        sql_filtro += "PASSWORD='" + bean.getUserPassword() + "'";
                    }
                }

            } else {
                System.out.println("Ha llegado un bean 'null'");
            }
                 
                   
                   
                    
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_UPDATE  + sql_filtro + " WHERE ID_CLIENTE=" + bean.getUserID() ;
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Desc : elimina a un usuario de la base de datos
     * @param id Id del client que se quiere eliminar de la base de datos
     */
    @Override
    public void delete(Integer id) {
        String sql_filtro = "";
        String sql_final = "";
        
        
        
        
        try {
            this.motorMySQL.connect();
            
            if(id != 0){
                sql_filtro += "ID_CLIENTE = " + id + ";";
            }
            
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_DELETE + sql_filtro;
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // ==================================================
    // PRUEBA UNITARIA USER DAO
    // ==================================================
    public static void main(String[] args){
        // PARA PROBAR EL ADD DE UN USUARIO
        System.out.println("PRUEBA ADD DE USUARIO");
        UserDAO userDAO2 = new UserDAO();
        User newUser = new User();

        newUser.setUserNickname("PruebaUnitaria");
        newUser.setUserPassword("pruebaUnitaria123");
        newUser.setUserName("Prueba");
        newUser.setUserSurname("Unitaria");
        newUser.setUserEmail("unitaria@unitaria.com");
        newUser.setUserAddress("C/ Usuario Usuarios");
        newUser.setUserPhone("665098611");
        userDAO2.add(newUser);
        System.out.println("Registro de usuario completado con éxito");
        System.out.println("");
        System.out.println("==========================================");
        System.out.println("");
        
        // PRUEBA FIND ALL
        System.out.println("PRUEBA SELECT DE USUARIO (FIND ALL)");
        UserDAO userDAO = new UserDAO();
                    User user = new User();
                    ArrayList<User> userList = new ArrayList();
                    String userID = "14";
                    String userNickname = null;
                    String userEmail = null;
                    // Al venir como string , si el suerID no es nulo, lo convertimos a INT para almacener correctamente la ID
                    if (userID != null) {
                        int intUserID = Integer.parseInt(userID);
                        user.setUserID(intUserID);
                    } else {
                        if (userNickname != null) {
                            user.setUserNickname(userNickname);
                        } else {
                            if (userEmail != null) {
                                user.setUserEmail(userEmail);
                            }
                        }
                    }

                    userList = userDAO.findAll(user);

                    if (userList == null) {
                        System.out.println("Ha venido vacÃ­o por lo tanto userList null a js");

                        

                    } else {
                        System.out.println(User.toJSON(userList));
                        
                    }
        
    }
    
}

    


