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
public class ProductDAO implements DAO<Product, Integer>{
    private static final String SQL_SELECT = "SELECT * FROM PRODUCT WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE PRODUCT SET ";
    private static final String SQL_DELETE = "DELETE FROM PRODUCT WHERE ";
    
    private MotorMySQL motorMySQL = null;
    
    public ProductDAO() {
           this.motorMySQL = new  MotorMySQL();
    }
    
    @Override
    public void add(Product bean) {
        String sql_filtro = "";
        String sql_final ="";
        
        
        try {
            this.motorMySQL.connect();
            
            
                   
            // EN CASO DE LA PAGINA DISPONER DE UN MENU "ADMIN" PARA AÑADIR PRODUCTOS, rellenaríamos 
            // este método ADD para añadiro los producots
                    
                    
           
            //sql_final = SQL_UPDATE  + sql_filtro + " WHERE ID_PRODUCT=" + bean.getUserID() ;
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /**
     * Desc : actualiza el STOCK de un producto según lo que haya comprado el usuario
     * @param bean  : objeto de clase Product que lleva el ID y la cantidad que se quiere updatear en la BBDD
     */
    @Override
    public void update(Product bean) {
        String sql_filtro = "";
        String sql_final = "";
        
        try {
            this.motorMySQL.connect();
            if (bean != null) {
                // dos parámeteros para el update de las existencias de un product (añadir más en caso de que se quiera updatear más cosas

                if(bean.getProductStock() > 0){
                    sql_filtro += "STOCK=(STOCK-" + bean.getProductStock() + ") ";
                }

            } else {
                System.out.println("Ha llegado un bean 'null'");
            }
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_UPDATE  + sql_filtro + " WHERE ID_PRODUCT=" + bean.getProductID();
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Desc : devuelve una o más filas de productos según la parametrización
     * @param bean : objeto de clase Product que trae la información del producto que se quiere buscar
     * @return 
     */
    @Override
    public ArrayList<Product> findAll(Product bean) {
        String sql_filtro = "";
        
        String sql_final = "";
        ArrayList<Product> productList = null;
        try {
            this.motorMySQL.connect();
            /*Parametrizar la consulta para que pueda filtrar por cualquier campo*/
            if(bean!=null){
                /*Quieres filtrar*/
                if(bean.getProductID()>0){
                    sql_filtro+=" AND ID_PRODUCT=" + bean.getProductID()+"";
                }//FALSE
                if(bean.getProductName() != null && bean.getProductName().length()>0){
                    sql_filtro+=" AND UPPER(NAME)='" + bean.getProductName().toUpperCase() + "'";
                }
                if(bean.getProductCategory() != null && bean.getProductCategory().length() > 0){
                    sql_filtro+=" AND CATEGORY = '" + bean.getProductCategory() + "'";
                }
                if(String.valueOf(bean.getTopProduct()) != null && bean.getTopProduct()> 0){
                    sql_filtro+=" AND TOP_PRODUCT = '" + bean.getTopProduct() + "'";
                }
                //SELECT * FROM `pelicula` WHERE `SINOPSIS` LIKE '%Wallace%'
            }
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_SELECT + sql_filtro;
            
            ResultSet resultset = this.motorMySQL.executeQuery(sql_final);
            // TRANSFORMADOR
            if(resultset!=null){
                    productList = new ArrayList();

                    while(resultset.next()){// 300, Braveheart
                        Product product = new Product();
                        product.setProductID(resultset.getInt(1));
                        product.setProductName(resultset.getString(2));
                        product.setProductImage(resultset.getString(3));
                        product.setProductDescription(resultset.getString(4));
                        product.setProductCost(resultset.getFloat(5));
                        product.setProductCategory(resultset.getString(6));
                        product.setTopProduct(resultset.getInt(7));
                        product.setProductStock(resultset.getInt(8));
                        productList.add(product);
                    }
            }
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productList;
    }
    
    
   
}
