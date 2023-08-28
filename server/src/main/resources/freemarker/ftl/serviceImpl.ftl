package ${serviceImplUrl};

import ${entityUrl}.${entityName};
import ${daoUrl}.${entityName}Mapper;
import ${serviceUrl}.${entityName}Service;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 *
 * @author huen on ${date} ${time}
 * 
 */
@Service
public class ${entityName}ServiceImpl  extends ServiceImpl<${entityName}Mapper, ${entityName}> implements ${entityName}Service  {
	
}