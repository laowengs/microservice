package com.laowengs.eureka.client.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentException(IllegalArgumentException e, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(e.getMessage()+"IllegalArgumentException!");
        out.flush();
        out.close();
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerException(NullPointerException e, HttpServletResponse resp)
            throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "上传文件超出限制!");
        mv.setViewName("error");
        return mv;
    }

}
