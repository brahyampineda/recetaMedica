
package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.*;
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

    //***************************************
    // ATRIBUTOS
    //***************************************
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

    
    //***************************************
    // METODOS
    //***************************************
    
    @PostConstruct
    private void init() {
        try {
            //Cuando la aplicacion renderize asigna la fila administrador a la variable
            administrador = new Administrador();
            administrador.setAdministradorPK(new AdministradorPK("1", "1"));
            administrador = crud.find(administrador);
            //Comprueba si es administrador
//        esAdmin = getLoginService().isAdmin();
        } catch (Exception ex) {
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    /**
     * Se inicializan las variables que contendran informacion del nuevo usuario y registro a guardar
     * @return 
     */
    public String prepararRegistroUsuario() {
        System.out.println("1");
        usuarioActual = new Usuario();
        registroActual = new Registro();
        registroActual.setRegistroPK(new RegistroPK());
        return "registro";
    }

    /**
     * Registra un nuevo usuario con los datos ingresados, se crea un registro asociado a ese usuario
     * @return Vista "index"
     */
    public String registroUsuario() {
        try {
            System.out.println("USUARIO " + usuarioActual);
            System.out.println("ID ADMIN "+administrador.getAdministradorPK().getIdAdministrador());
//            registroActual.getRegistroPK().setAdministradoridAdministrador(administrador.getAdministradorPK().getIdAdministrador());
            System.out.println("ID USUARIO "+usuarioActual.getIdUsuario());
            registroActual.getRegistroPK().setUsuarioidUsuario(usuarioActual.getIdUsuario());
            System.out.println("REGISTRO "+registroActual);
            
//            registroActual.getRegistroPK().setAdministradoridAdministrador(administrador.getAdministradorPK().getIdAdministrador());
            registroActual.getRegistroPK().setUsuarioidUsuario(usuarioActual.getIdUsuario());
            
            //NO SIRVE
            crud.generarConsecutivo(registroActual);
            
//            registroActual.getRegistroPK().setIdRegistro(3);
            registroActual.setAdministrador(administrador);
            registroActual.setUsuario(usuarioActual);
            registroActual.setEstado("en espera");
            
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

    
    //***************************************
    // GETTERS Y SETTERS
    //***************************************
    
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
            administrador = crud.find(new Administrador(new AdministradorPK("1", "1")));
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
