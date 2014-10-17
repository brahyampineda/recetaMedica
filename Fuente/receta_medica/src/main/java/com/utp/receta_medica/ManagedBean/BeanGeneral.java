/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.facade.Crud;
//import com.recetamedica.ejb.facade.Crud;
import com.utp.receta_medica.entidades.Administrador;
import com.utp.receta_medica.entidades.Registro;
import com.utp.receta_medica.entidades.RegistroPK;
import com.utp.receta_medica.entidades.Usuario;
import com.utp.receta_medica.facade.LoginService;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "beanGeneral")
//@Dependent
@SessionScoped
public class BeanGeneral implements Serializable {

    @EJB
    private TablasFacade crud;
    
    //////Atributos para manejo de login//////
    @ManagedProperty(value = "#{loginService}")
    private LoginService loginService;
    
    private Boolean esAdmin=true;
    /////////////////////////////////////////

    Usuario usuarioActual;
    Registro registroActual;
    Administrador administrador;
    String perfil;

    @PostConstruct
    private void init() {
        administrador = new Administrador();
            //Comprueba si es administrador
//        esAdmin = getLoginService().isAdmin();
    }
        
    public String prepararRegistroUsuario() {
        System.out.println("1");
        usuarioActual = new Usuario();
        registroActual = new Registro();
        registroActual.setRegistroPK(new RegistroPK());
        return "registro";
    }

    public String registroUsuario() {
        try {
            System.out.println("USUARIO " + usuarioActual);
            System.out.println("2");
            registroActual.getRegistroPK().setAdministradoridAdministrador(getAdministrador().getIdAdministrador());
            registroActual.getRegistroPK().setUsuarioidUsuario(usuarioActual.getIdUsuario());
            
            
            
//            registroActual.getRegistroPK().setIdRegistro(3);
            registroActual.setAdministrador(administrador);
            registroActual.setUsuario(usuarioActual);
            registroActual.setEstado("en espera");
            //NO SIRVE
            crud.generarConsecutivo(registroActual);
            System.out.println("REGISTRO " + registroActual);
//            administrador.getRegistroCollection().add(registroActual);
//            crud.save(administrador);
            crud.save(usuarioActual);
            crud.save(registroActual);
        } catch (Exception ex) {
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Registro getRegistroActual() {
        return registroActual;
    }

    public void setRegistroActual(Registro registroActual) {
        this.registroActual = registroActual;
    }

    public Administrador getAdministrador() {
        try {
            administrador = crud.find(new Administrador("1"));
        } catch (Exception ex) {
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
}
