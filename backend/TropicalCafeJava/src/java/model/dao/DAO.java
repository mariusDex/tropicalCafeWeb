package model.dao;

import java.util.ArrayList;

/**
 *
 * @author Mariusz Broza
 */
public interface DAO<O,I> { //O=Object, I=Integer
    void add(O bean);
    void delete(I id);
    void update(O bean);
    ArrayList<O> findAll(O bean);
   
}
