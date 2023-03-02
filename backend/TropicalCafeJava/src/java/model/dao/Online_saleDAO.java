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
public class Online_saleDAO implements DAO<Online_sale, Integer>{
    private static final String SQL_SELECT = "SELECT * FROM ONLINE_SALE WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE ONLINE_SALE SET ";
    private static final String SQL_DELETE = "DELETE FROM ONLINE_SALE WHERE ";
    private static final String SQL_INSERT = "INSERT INTO ONLINE_SALE VALUES(";
    
    private MotorMySQL motorMySQL = null;
    public Online_saleDAO() {
           this.motorMySQL = new  MotorMySQL(); 
    }
    
    @Override
    public void add(Online_sale bean) {
        String sql_filtro = "";
        String sql_final ="";
        
        
        try {
            this.motorMySQL.connect();
            
            sql_filtro += "null," 
                    + "'" + bean.getAddress() + "',"
                    + "CURRENT_DATE,"
                    + "'" + bean.getInvonce() + "',"
                    + "'" + bean.getClientID() + "'";
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_INSERT + sql_filtro + ")";
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(Online_saleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    public void delete(Integer id) {
        String sql_filtro = "";
        String sql_final = "";
        
        
        
        
        try {
            this.motorMySQL.connect();
            
            if(id != 0){
                
                sql_filtro += "ID_ONLINE_SALE IN(SELECT ID_ONLINE_SALE FROM ONLINE_SALE WHERE ID_CLIENTE = " + id + ");";
            }
            
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_DELETE + sql_filtro;
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(Online_saleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void update(Online_sale bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    
    // No se va a utilizar el FindAll para las ofertas
    public ArrayList<Online_sale> findAll(Online_sale bean) {
        String sql_filtro = "";
        String sql_final = "";
        ArrayList<Online_sale> onlineSaleList = null;
        try {
            this.motorMySQL.connect();
            /*Parametrizar la consulta para que pueda filtrar por cualquier campo*/
            if(bean!=null){
                /*Quieres filtrar*/
                
                
                //SELECT * FROM `pelicula` WHERE `SINOPSIS` LIKE '%Wallace%'
            }
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_SELECT + sql_filtro;
            
            ResultSet resultset = this.motorMySQL.executeQuery(sql_final);
            // TRANSFORMADOR
            if(resultset!=null){
                    onlineSaleList = new ArrayList();

                    while(resultset.next()){// 300, Braveheart
                        Online_sale onlineSale = new Online_sale();
                        onlineSale.setOnlineSaleID(resultset.getInt(1));
                        onlineSale.setAddress(resultset.getString(2));
                        onlineSale.setDate(resultset.getString(3));
                        onlineSale.setInvonce(resultset.getString(4));
                        onlineSale.setClientID(resultset.getInt(5));

                        onlineSaleList.add(onlineSale);
                    }
            }
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onlineSaleList;
    }
    
    /*
    public ArrayList<Product> findMovieByGenero(String genero){
        //1º) Connect
        // 2º) ExecuteQuery
        String sqlFinal = SQL_MOVIE_BY_GENERO + genero;
        //3º) Transformar de ResultSet a ArrayList
        // 4º) Desconectar
        return null;

    }
    */
}
