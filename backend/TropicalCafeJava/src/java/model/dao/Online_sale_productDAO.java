package model.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MotorMySQL;

/**
 *
 * @author mariusz Broza
 */
public class Online_sale_productDAO implements DAO<Online_sale_product, Integer>{
    // INICIO DE CONSULTAS BÁSICAS
    private static final String SQL_SELECT = "SELECT * FROM ONLINE_SALE_PRODUCT WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE ONLINE_SALE_PRODUCT SET ";
    private static final String SQL_DELETE = "DELETE FROM ONLINE_SALE_PRODUCT WHERE ";
    private static final String SQL_INSERT = "INSERT INTO ONLINE_SALE_PRODUCT VALUES(";
    
    // CONSULTA DE TRIPLE INNER JOIN PARA PODER SACAR EL HISTORIAL DE COMPRA DE UN USUARIO
    private static final String SQL_CONSULTA_HISTORIAL_START = "SELECT DATE, ONLINE_SALE.ID_ONLINE_SALE, PRODUCT.NAME,PRODUCT.IMAGE,QUANTITY,PRODUCT.COST "
                                                                + "FROM online_sale, product, online_sale_product "
                                                                + "WHERE ONLINE_SALE_PRODUCT.ID_PRODUCT = PRODUCT.ID_PRODUCT AND "
                                                                + "ONLINE_SALE.ID_ONLINE_SALE = ONLINE_SALE_PRODUCT.ID_ONLINE_SALE AND " 
                                                                + "ONLINE_SALE.ID_ONLINE_SALE IN (SELECT ONLINE_SALE.ID_ONLINE_SALE WHERE ID_CLIENTE = ";
    private static final String SQL_CONSULTA_HISTORIAL_END = " )GROUP BY ID_ONLINE_SALE,PRODUCT.NAME;";
    
    
    private MotorMySQL motorMySQL = null;
    
    // BUILDER
    public Online_sale_productDAO() {
           this.motorMySQL = new  MotorMySQL(); 
           
    }
    
    /**
     * Desc  : añade el ID de la Online_sale y del product a la tabla N-M de Online_sale_product
     * @param bean : objeto de tipo Online_sale_product con la información a insertar en BBDD;
     */
    @Override
    public void add(Online_sale_product bean) {
        String sql_filtro = "";
        String sql_final ="";
        
        
        try {
            this.motorMySQL.connect();
            
            sql_filtro += "(SELECT MAX(ID_ONLINE_SALE) AS ID_ONLINE_SALE FROM ONLINE_SALE),"
                    + "'" + bean.getProductID() + "',"
                    + "'" + bean.getAmount() + "'";
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_INSERT + sql_filtro + ")";
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(Online_sale_productDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    /**
     * Desc : elimina la información de compra de cierta online_sale (se utiliza al borrar un usuario)
     * @param id : id de las filas donde la id_online_sale se igual a la que se pasa por parámetro 
     */
    @Override
    public void delete(Integer id) {
         String sql_filtro = "";
        String sql_final = "";
        
        
        
        
        try {
            this.motorMySQL.connect();
            
            if(id != 0){
                
                sql_filtro += "ID_ONLINE_SALE IN (SELECT ID_ONLINE_SALE FROM ONLINE_SALE WHERE ID_CLIENTE = " + id + ");";
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
    
    
    @Override
    public void update(Online_sale_product bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Desc : devuelve el historial de compra de un usuario
     * @param bean : objeto de clase Online_sale_product  que lleva toda la información que se quiere buscar en BBDD
     * @return ArrayList<Online_sale_product>
     */
    @Override
    public ArrayList<Online_sale_product> findAll(Online_sale_product bean) {
        String sql_filtro = "";
        String sql_final = "";
        ArrayList<Online_sale_product> onlineSaleProductList = null;
        try {
            this.motorMySQL.connect();
            /*Parametrizar la consulta para que pueda filtrar por cualquier campo*/
            if(bean!=null){
                //aqui es donde metemos la consulta entre la tabla N-M y pro
                if(bean.getClientID() >0){
                    sql_filtro = SQL_CONSULTA_HISTORIAL_START + bean.getClientID() + SQL_CONSULTA_HISTORIAL_END;
                }
                
            }
            // SENTENCIA DEL SELECT COMPLETA
            
            sql_final = sql_filtro;
            
            ResultSet resultset = this.motorMySQL.executeQuery(sql_final);
            // TRANSFORMADOR
            if(resultset!=null){
                    onlineSaleProductList = new ArrayList();
                    
                    while(resultset.next()){// 300, Braveheart
                        Online_sale_product onlineSaleProduct = new Online_sale_product();
                       onlineSaleProduct.setSaleDate(resultset.getString(1));
                       onlineSaleProduct.setOnlineSaleID(resultset.getInt(2));
                       onlineSaleProduct.getProduct().setProductName(resultset.getString(3));
                       onlineSaleProduct.getProduct().setProductImage(resultset.getString(4));
                       onlineSaleProduct.setAmount(resultset.getInt(5));
                       onlineSaleProduct.getProduct().setProductCost(resultset.getFloat(6));
                       
                       onlineSaleProductList.add(onlineSaleProduct);
                    }
            }
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onlineSaleProductList;
    }
    
   
}
