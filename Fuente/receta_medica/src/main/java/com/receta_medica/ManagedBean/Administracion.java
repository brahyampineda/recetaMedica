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
import com.receta_medica.entidades.SolicitudQuejasReclamos;
import com.receta_medica.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "administracion")
@Dependent
public class Administracion {
    
    Crud crud;

    Administrador administrador = new Administrador();
    SolicitudQuejasReclamos sqr;
    List<Usuario> usuarios;
    String perfil;
    
    public String prepararAdministracion () {
        System.out.println("11111");
        administrador = new Administrador();
        return "administrador";
    }

    public String gestionarUsuario(Registro registro) {
        System.out.println("22222");
        if (perfil.equals("Aceptar")) {
            if (registro.getPerfil().equals("Paciente")) {
                Paciente paciente;
                paciente = new Paciente();
                paciente.setUsuario(registro.getUsuario());
                registro.getUsuario().getPacienteCollection().add(paciente);
            }
            if (registro.getPerfil().equals("Medico general")) {
                MedicoGeneral medicoGeneral;
                medicoGeneral = new MedicoGeneral();
                medicoGeneral.setUsuario(registro.getUsuario());
                registro.getUsuario().getMedicoGeneralCollection().add(medicoGeneral);
            }
            if (registro.getPerfil().equals("Medico especialista")) {
                MedicoEspecialista medicoEspecialista;
                medicoEspecialista = new MedicoEspecialista();
                medicoEspecialista.setUsuario(registro.getUsuario());
                registro.getUsuario().getMedicoEspecialistaCollection().add(medicoEspecialista);
            }
        } else {
            try {
                crud.remove(registro.getUsuario());
                crud.remove(registro);
            } catch (Exception ex) {
                Logger.getLogger(Registrar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "administrador";
    }

    public Crud getCrud() {
        return crud;
    }

    public void setCrud(Crud crud) {
        this.crud = crud;
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

    public SolicitudQuejasReclamos getSqr() {
        return sqr;
    }

    public void setSqr(SolicitudQuejasReclamos sqr) {
        this.sqr = sqr;
    }     

    public List<Usuario> getUsuarios() {
//        usuarios = crud.findAll(new ArrayList<Usuario>());
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }   

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    
    
}
