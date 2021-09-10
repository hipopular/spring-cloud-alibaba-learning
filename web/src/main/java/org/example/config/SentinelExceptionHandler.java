package org.example.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by huen on 2021/9/10 13:26
 */
@Component
public class SentinelExceptionHandler {

    public SentinelExceptionHandler() {
        WebCallbackManager.setUrlBlockHandler((request, response, ex) -> {
            Map<String,Object> result = new HashMap<>();
            result.put("code",-1);
            result.put("msg","系统繁忙，请稍候再试！");
            response.setStatus(200);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.setContentType("application/json;charset=utf-8");
            new ObjectMapper()
                    .writeValue(
                            response.getWriter(),
                            result
                    );
        });

    }
}
