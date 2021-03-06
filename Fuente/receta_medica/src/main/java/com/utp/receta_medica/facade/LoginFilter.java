package com.utp.receta_medica.facade;

/**
 *
 * @author JorgeRivera
 */
import com.utp.receta_medica.ManagedBean.BeanGeneral;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        BeanGeneral loginBean = (BeanGeneral)((HttpServletRequest)request).getSession().getAttribute("beanGeneral");
         
        if (loginBean == null || !loginBean.isLoggedIn()) {
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/faces/index.xhtml");
        }
         
        chain.doFilter(request, response);
             
    }
 
    @Override
    public void init(FilterConfig config) throws ServletException {
        
    }
 
    @Override
    public void destroy() {
        
    }  
     
}

