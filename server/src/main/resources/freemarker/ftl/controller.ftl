package ${controllerUrl};

import ${abstractControllerUrl}.AbstractController;
import ${entityUrl}.${entityName};
import ${serviceUrl}.${entityName}Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<#if isSwagger=="true" >
import io.swagger.annotations.Api;
</#if>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author huen on ${date} ${time}
 *
 */
 <#if isSwagger=="true" >
</#if>
@RestController
@RequestMapping("/${objectName}")
public class ${entityName}Controller extends AbstractController<${entityName}Service,${entityName}>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}