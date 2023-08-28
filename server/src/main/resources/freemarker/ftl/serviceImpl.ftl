package ${serviceImplUrl};

import ${entityUrl}.${entityName};
import ${daoUrl}.${entityName}Dao;
import ${serviceUrl}.${entityName}Service;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 *
 * @author huen on ${date} ${time}
 * 
 */
@Service
public class ${entityName}ServiceImpl  extends ServiceImpl<${entityName}Dao, ${entityName}> implements ${entityName}Service  {
	
}