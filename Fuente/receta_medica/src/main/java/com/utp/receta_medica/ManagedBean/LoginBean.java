/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.facade.LoginService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.sound.midi.Soundbank;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 2657487988756453662L;
        
    //    @EJB
    @ManagedProperty(value = "#{loginService}")
    LoginService loginService;
    
    private String username;
    private String password;

    public String doLogin() {
        System.out.println("11111");
        return loginService.doLogin(username, password);
    }

    public String doLogout() {
        return loginService.doLogout();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}


