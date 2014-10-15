/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.receta_medica.ManagedBean;

import com.receta_medica.ejb.facade.Crud;
import com.receta_medica.entidades.Administrador;
import com.receta_medica.entidades.MedicoEspecialista;
import com.receta_medica.entidades.MedicoGeneral;
import com.receta_medica.entidades.Paciente;
import com.receta_medica.entidades.Registro;
import com.receta_medica.entidades.RegistroPK;
import com.receta_medica.entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "registrar")
@SessionScoped
public class Registrar implements Serializable {

//    @EJB
    Crud crud;

    Usuario usuarioActual;
    Registro registroActual;
    Administrador administrador;
    String perfil;

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
            registroActual.getRegistroPK().setAdministradoridAdministrador(administrador.getAdministradorPK().getIdAdministrador());
            registroActual.getRegistroPK().setUsuarioidUsuario(usuarioActual.getIdUsuario());
            crud.generarConsecutivo(registroActual);
            registroActual.setAdministrador(administrador);
            registroActual.setUsuario(usuarioActual);
            registroActual.setEstado("en espera");
            System.out.println("REGISTRO " + registroActual);
            crud.save(usuarioActual);
            crud.save(registroActual);
        } catch (Exception ex) {
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    

    public Crud getCrud() {
        return crud;
    }

    public void setCrud(Crud crud) {
        this.crud = crud;
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
            administrador = crud.find(new Administrador(1, 1088304075));
        } catch (Exception ex) {
            Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
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
