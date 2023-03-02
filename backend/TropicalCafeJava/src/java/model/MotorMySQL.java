package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mariusz Broza
 */
public class MotorMySQL {
   /* private static final String URL="jdbc:mysql://localhost:3308/netflix_sanvalero";*/
    private static final String USER="root";
    private static final String PASS="";
    private static final String DRIVER ="com.mysql.cj.jdbc.Driver";
     private static final String URL
            = "jdbc:mysql://127.0.0.1:3306/tropicalcafebbdd?"
            + "useUnicode=true&"
            + "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&"
            + "serverTimezone=UTC";
   
    
    /*JDBC*/
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void connect(){
        try 
        {
            Class.forName(DRIVER);
        } catch( Exception e )
        {
            System.out.println("No se pudo cargar el puente JDBC.");
            return; 
        }
        try{
            connection = DriverManager.getConnection(URL,USER,PASS);
            statement = connection.createStatement();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public int execute(String SQL){
        int filasModificadas=0;
        try{
            filasModificadas = statement.executeUpdate(SQL);
         }catch(Exception e){
             System.out.println(e.getMessage()); 
         }
        return filasModificadas;
    }
    
    
    public ResultSet executeQuery(String SQL){
        try{
            resultSet = statement.executeQuery(SQL);
         }catch(Exception e){
             System.out.println(e.getMessage()); 
         }
        return resultSet;
    }
    
    
    public void disconnect(){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                statement.close();
            }
            //(resultSet!=null)?resultSet.close():null;
        }catch (SQLException ex) {
                Logger.getLogger(MotorMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
