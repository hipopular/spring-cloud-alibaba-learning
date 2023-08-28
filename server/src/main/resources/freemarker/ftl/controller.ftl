package ${controllerUrl};

import ${entityUrl}.${entityName};
import ${serviceUrl}.${entityName}Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author huen on ${date} ${time}
 *
 */

@RestController
@RequestMapping("/${objectName}")
public class ${entityName}Controller{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}