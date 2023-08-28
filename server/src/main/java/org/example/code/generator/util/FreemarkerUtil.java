package org.example.code.generator.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.example.code.generator.entity.BasisInfo;
import org.example.code.generator.entity.ResultJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FreemarkerUtil {
	 
	public static ResultJson createFile(BasisInfo dataModel, String templateName, String filePath) {
		ResultJson result=new ResultJson();
		FileWriter out = null;
		String fileName=dataModel.getEntityName()+messageStr(templateName);
		try {
			// 通过FreeMarker的Confuguration读取相应的模板文件
	        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
	        // 设置模板路径
	        configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/freemarker/ftl");
	        // 设置默认字体
	        configuration.setDefaultEncoding("utf-8");
	        // 获取模板
			Template template = configuration.getTemplate(templateName);
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.delete();
			file.createNewFile();
			//设置输出流
            out = new FileWriter(file);
            //模板输出静态文件
            template.process(dataModel, out);
            result.setCode(1);
        	result.setMessage("创建"+fileName+"成功");
        	return result;
		} catch (Exception ignored) {
		} finally {
            if(null != out) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
        }
		result.setCode(-1);
    	result.setMessage("创建"+fileName+"失败");
    	return result;
	}
	
	public static String messageStr(String name) {
        switch (name) {
            case "entity.ftl":
                name = ".java";
                break;
            case "dao.ftl":
                name = "Dao.java";
                break;
            case "mapper.ftl":
                name = "Mapper.xml";
                break;
            case "service.ftl":
                name = "Service.java";
                break;
            case "serviceImpl.ftl":
                name = "ServiceImpl.java";
                break;
            case "controller.ftl":
                name = "Controller.java";
                break;
        }
		return name;
	}
}
