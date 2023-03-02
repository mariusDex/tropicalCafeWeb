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
public class Offer_candidateDAO implements DAO<Offer_candidate, Integer>{
    private static final String SQL_SELECT = "SELECT * FROM OFFER_CANDIDATE WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE OFFER_CANDIDATE SET ";
    private static final String SQL_DELETE = "DELETE FROM OFFER_CANDIDATE WHERE ";
    private static final String SQL_INSERT = "INSERT INTO OFFER_CANDIDATE VALUES(";
    
    
    
    private MotorMySQL motorMySQL = null;
    
    // BUILDER
    public Offer_candidateDAO() {
           this.motorMySQL = new  MotorMySQL(); 
           
    }
    
    /**
     * Desc : añade la id de la oferta + el id del candidato a la tabla N-M
     * @param bean : información que se quiere introducir dentro de la tabla
     */
    @Override
    public void add(Offer_candidate bean) {
        String sql_filtro = "";
        String sql_final ="";
        
        
        try {
            this.motorMySQL.connect();
            
            if(bean != null){
                
                sql_filtro += bean.getCandidateID() + ","
                        + bean.getOfferID() + ","
                        + bean.getCV() + ");"; 
                
                
            }
            
            // SENTENCIA FINAL
            sql_final = SQL_INSERT + sql_filtro;
            
            // ejecutamos el INSERT a base de datos
            this.motorMySQL.execute(sql_final);
            
            
            
            
            
        } catch (Exception ex) {
                Logger.getLogger(Offer_candidateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    public void delete(Integer id) {
         // VACIO POR MOTIVOS DE INUTILIDAD

    }
    
    
    @Override
    public void update(Offer_candidate bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    
   
    public ArrayList<Offer_candidate> findAll(Offer_candidate bean) {
        String sql_filtro = "";
        String sql_final = "";
        ArrayList<Offer_candidate> offerCandidateList = null;
        try {
            this.motorMySQL.connect();
            /*Parametrizar la consulta para que pueda filtrar por cualquier campo*/
            if(bean!=null){
                //aqui es donde metemos la consulta entre la tabla N-M y pro
                
                
            }
            // SENTENCIA DEL SELECT COMPLETA
            
            sql_final = sql_filtro;
            
            ResultSet resultset = this.motorMySQL.executeQuery(sql_final);
            // TRANSFORMADOR
            if(resultset!=null){
                    offerCandidateList = new ArrayList();
                    
                    while(resultset.next()){// 300, Braveheart
                       Offer_candidate offerCandidate = new Offer_candidate();
                       offerCandidate.setCandidateID(resultset.getInt(1));
                       offerCandidate.setOfferID(resultset.getInt(2));
                       offerCandidate.setCV(resultset.getString(3));
                       
                       
                       offerCandidateList.add(offerCandidate);
                    }
            }
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offerCandidateList;
    }
    
   
}
