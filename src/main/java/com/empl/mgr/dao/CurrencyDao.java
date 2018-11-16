package com.empl.mgr.dao;                                                              
                                                                                         
 import org.springframework.stereotype.Repository;                                                                                        
 import com.empl.mgr.dao.support.AbstractDao;                                                                                        
 import com.empl.mgr.field.SsCurrencyField;                                                                                        
 import com.empl.mgr.model.SsCurrency;                                                                                        
 @Repository                                                                                        
public class CurrencyDao extends AbstractDao<SsCurrency> {                                 
@Override                                                  
public Class<SsCurrency> getEntityClass() {             
	return SsCurrency.class;                              
}                                                          
public void deleteById(long id) {                          
	deleteByProperty(SsCurrencyField.ID, id);             
}                                                          
public SsCurrency findById(long id) {                    
	return findUniqueByProperty(SsCurrencyField.ID, id);  
} 
	}
