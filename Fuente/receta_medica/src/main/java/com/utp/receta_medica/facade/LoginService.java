/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.receta_medica.facade;

import com.utp.receta_medica.entidades.Administrador;
import com.utp.receta_medica.entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import com.receta_medica.ejb.facade.Crud;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.jasypt.util.password.BasicPasswordEncryptor;

@ManagedBean
@SessionScoped
public class LoginService implements Serializable {

    private static final long serialVersionUID = 2657487988756493662L;

    @EJB
    Crud crud;

    //SegUsuario loggedUser;
    Usuario loggedUser;

    private boolean loggedIn;
    private boolean admin;

    public String doLogin(String username, String password) {

        Usuario usuario = new Usuario();
//        usuario.setAdministradorCollection(new ArrayList<Administrador>());
        List<Usuario> usuarios;
        try {  
            usuarios = crud.findAll(new Usuario());
            for (Usuario usuarioi : usuarios) {
                if (usuarioi.getCorreo().equals(username)) {
                    usuario = usuarioi;
                    break;
                }
            }
//            usuario = crud.find(new Usuario(username));
        } catch (Exception ex) {
            usuario = null;
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (usuario == null) {
            FacesMessage msg = new FacesMessage("Error al entrar: Direccion de correo no existe.", "ERROR MSG");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);

            return "index";
        }

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        
        System.out.println("YA CASIIIIIIIIII");

        if (passwordEncryptor.checkPassword(password, usuario.getContrasena())) {
            loggedIn = true;
            loggedUser = usuario;
//            Collection<SegRol> roles = usuario.getSegRolCollection();
//            if (roles.isEmpty()) {
//                admin = false;
//            } else {
//                for (SegRol rol : roles) {
//                    if (rol.getNombre().equals("admin")) {
//                        admin = true;
//                        break;
//                    }
//                }
//            }
            
//            if (usuario.getAdministradorCollection().isEmpty()) {
//                admin = false;
//            } else {
//                admin = true;
//            }
            return "/faces/index.xhtml?faces-redirect=true";
        }

        FacesMessage msg = new FacesMessage("Error al entrar: Usuario o conreaseÃ±a incorrectos.", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return "index";

    }

    public String doLogout() {
        loggedIn = false;
        loggedUser = null;

        FacesMessage msg = new FacesMessage("Se ha cerrado la sesiÃ³n", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return "index";
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
