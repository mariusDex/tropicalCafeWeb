package model.dao;

import java.util.ArrayList;

public class OfferDAO implements DAO<Offer,Integer>{
    
    private static final String SQL_SELECT = "SELECT * FROM OFFER WHERE 1=1";
    private static final String SQL_UPDATE = "UPDATE OFFER SET ";
    private static final String SQL_DELETE = "DELETE FROM OFFER WHERE ";
    private static final String SQL_INSERT = "INSERT INTO OFFER VALUES("; 

    @Override
    public void add(Offer bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Offer bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Offer> findAll(Offer bean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
