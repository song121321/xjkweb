package com.empl.mgr.dao;                                                              
                                                                                         
 import org.springframework.stereotype.Repository;                                                                                        
 import com.empl.mgr.dao.support.AbstractDao;                                                                                        
 import com.empl.mgr.field.SsWorkitemField;                                                                                        
 import com.empl.mgr.model.SsWorkitem;                                                                                        
 @Repository                                                                                        
public class WorkitemDao extends AbstractDao<SsWorkitem> {                                 
@Override                                                  
public Class<SsWorkitem> getEntityClass() {             
	return SsWorkitem.class;                              
}                                                          
public void deleteById(long id) {                          
	deleteByProperty(SsWorkitemField.ID, id);             
}                                                          
public SsWorkitem findById(long id) {                    
	return findUniqueByProperty(SsWorkitemField.ID, id);  
} 
	}
