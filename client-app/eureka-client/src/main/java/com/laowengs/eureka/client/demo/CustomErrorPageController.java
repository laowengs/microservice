package com.laowengs.eureka.client.demo;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class CustomErrorPageController extends BasicErrorController {

    public CustomErrorPageController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, new ErrorProperties(), errorViewResolvers);

    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        // 获取 Spring Boot 默认提供的错误信息，然后添加一个自定义的错误信息
        Map<String, Object> model = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        model.put("msg", "出错啦++++++");
        ModelAndView modelAndView = new ModelAndView("errorPage", model, status);
        return modelAndView;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        // 获取 Spring Boot 默认提供的错误信息，然后添加一个自定义的错误信息
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        body.put("msg", "出错啦------");
        return new ResponseEntity<>(body, status);
    }
}
