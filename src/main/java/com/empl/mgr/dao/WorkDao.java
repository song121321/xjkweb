package com.empl.mgr.dao;                                                              
                                                                                         
 import org.springframework.stereotype.Repository;                                                                                        
 import com.empl.mgr.dao.support.AbstractDao;                                                                                        
 import com.empl.mgr.field.SsWorkField;                                                                                        
 import com.empl.mgr.model.SsWork;                                                                                        
 @Repository                                                                                        
public class WorkDao extends AbstractDao<SsWork> {                                 
@Override                                                  
public Class<SsWork> getEntityClass() {             
	return SsWork.class;                              
}                                                          
public void deleteById(long id) {                          
	deleteByProperty(SsWorkField.ID, id);             
}                                                          
public SsWork findById(long id) {                    
	return findUniqueByProperty(SsWorkField.ID, id);  
} 
	}
