package ${entityUrl};
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
<#if isSwagger=="true" >
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Data;
import java.io.Serializable;
<#list pkgs as ps>
	<#if ps??>
import ${ps};
	</#if>
</#list>

/**
 *
 * @author huen on ${date} ${time}
 * 
 */
@Data
@TableName("${table}")
public class ${entityName} implements Serializable {

	private static final long serialVersionUID = ${agile}L;
	
<#list cis as ci>
 <#if ci.javaType=="Date">
  <#if ci.jdbcType=="date">
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
  <#elseif ci.jdbcType=="time">
    @DateTimeFormat(pattern = "HH:mm:ss")
	@JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
  <#else>
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  </#if>
 </#if>
 <#if ci.property=="id">
	@TableId(value = "id", type = IdType.AUTO)
 </#if>
 <#if isSwagger=="true" >
	@ApiModelProperty("${ci.comment}")
    @TableField("${ci.column}")
 </#if>
	private ${ci.javaType} ${ci.property};
    
</#list>

}
	