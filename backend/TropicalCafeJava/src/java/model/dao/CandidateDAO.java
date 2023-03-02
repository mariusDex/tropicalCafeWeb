package model.dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MotorMySQL;

public class CandidateDAO implements DAO<Candidate, Integer>{
    
    // INCIO DE CONSULTAS POR DEFECTO
    private static final String SQL_SELECT = "SELECT * FROM CANDIDATE WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE CANDIDATE SET ";
    private static final String SQL_DELETE = "DELETE FROM CANDIDATE WHERE ";
    private static final String SQL_INSERT = "INSERT INTO CANDIDATE VALUES(";

    
    private MotorMySQL motorMySQL = null;
    
    // BUILDER
    public CandidateDAO() {
           this.motorMySQL = new MotorMySQL();
    }
    /**
     * Desc : añade a un candidate a la BBDD
     * @param bean  : INFORMACIÓN DEL Candidato a introducir en la BBDD
     */
    @Override
    public void add(Candidate bean) {
        String sql_filtro = "";
        String sql_final = "";
        
        
        try {
            this.motorMySQL.connect();
            
            sql_filtro +=  "" + bean.getCandidateID()+ ","
                    + "'" + bean.getCandidateNameSurname()+ "',"
                    + "'" + bean.getCandidateEmail()+ "',"
                    + "'" + bean.getCandidateCv()+ "'";
                    
            // SENTENCIA DEL SELECT COMPLETA
            sql_final = SQL_INSERT + sql_filtro + ")";
            
            this.motorMySQL.execute(sql_final);
            // TRANSFORMADOR
            
            
            
            
            this.motorMySQL.disconnect();
        } catch (Exception ex) {
                Logger.getLogger(CandidateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Candidate bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Candidate> findAll(Candidate bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}

