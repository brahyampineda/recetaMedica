/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.facade.Crud;
//import com.recetamedica.ejb.facade.Crud;
import com.utp.receta_medica.entidades.Administrador;
import com.utp.receta_medica.entidades.Medicamento;
import com.utp.receta_medica.entidades.MedicoEspecialista;
import com.utp.receta_medica.entidades.MedicoGeneral;
import com.utp.receta_medica.entidades.Paciente;
import com.utp.receta_medica.entidades.Registro;
import com.utp.receta_medica.entidades.SolicitudQuejasReclamos;
import com.utp.receta_medica.entidades.Usuario;
import com.utp.receta_medica.facade.AbstractFacade;
import com.utp.receta_medica.facade.LoginService;
import com.utp.receta_medica.facade.TablasFacade;
//import com.utp.receta_medica.facade.UsuarioFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "beanAdministrador")
@SessionScoped
public class BeanAdministrador implements Serializable{

    @EJB
    private TablasFacade crud;
    
    //////Atributos para manejo de login//////
    @ManagedProperty(value = "#{loginService}")
    private LoginService loginService;
    
    private Boolean esAdmin=true;
    /////////////////////////////////////////
    
    Administrador administrador;
    SolicitudQuejasReclamos sqr;
    List<Usuario> lstUsuarios;
    String perfil;
    Medicamento medicamentoActual;
    List<Medicamento> lstMedicamentos;
    
    @PostConstruct
    private void init() {
        administrador = new Administrador();
            //Comprueba si es administrador
//        esAdmin = getLoginService().isAdmin();
    }
    
    public String prepararAdministracion() {
        System.out.println("11111");
        try {
            administrador = new Administrador();
            administrador.setIdAdministrador("1");
            administrador.setRegistroCollection(new ArrayList<Registro>());
            administrador.setSolicitudQuejasReclamosCollection(new ArrayList<SolicitudQuejasReclamos>());
            System.out.println("AMIN "+administrador);
            medicamentoActual = new Medicamento();
            administrador = crud.find(administrador);
            System.out.println("AMIN22 "+administrador.getCorreo());
            for (Registro registro : administrador.getRegistroCollection()) {
                System.out.println("REGISTRO "+registro.getUsuario().getNombre());
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                crud.save(paciente);
            }
            if (registro.getPerfil().equals("Medico general")) {
                MedicoGeneral medicoGeneral;
                medicoGeneral = new MedicoGeneral();
                medicoGeneral.setUsuario(registro.getUsuario());
                registro.getUsuario().getMedicoGeneralCollection().add(medicoGeneral);
                crud.save(medicoGeneral);
            }
            if (registro.getPerfil().equals("Medico especialista")) {
                MedicoEspecialista medicoEspecialista;
                medicoEspecialista = new MedicoEspecialista();
                medicoEspecialista.setUsuario(registro.getUsuario());
                registro.getUsuario().getMedicoEspecialistaCollection().add(medicoEspecialista);
                crud.save(medicoEspecialista);
            }
            try {
                crud.remove(registro);
            } catch (Exception ex) {
                Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                crud.remove(registro.getUsuario());
                crud.remove(registro);
            } catch (Exception ex) {
                Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "administrador";
    }
    
    public String eliminarUsuario() {
        Usuario user = new Usuario();
        user.setIdUsuario("1088303030");
        try {
            user=crud.find(user);
            System.out.println("DSDSSDSD "+user.getNombre());
            crud.remove(user);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "administrador";
    }
    
    public String crearUsuario() {
        Usuario user = new Usuario();
        user.setNombre("Estefania");
        user.setApellidos("Romero");
        user.setContrasena("estefania");
        user.setCorreo("tefa@gmail.com");
        user.setIdUsuario("1088303030");
        System.out.println("USER "+user);
//        user=crud.save(user);
//        ejbFacadeU.create(user);
        crud.save(user);
        System.out.println("USER "+user);
        return "administrador";
    }
    
    public String añadirMedicamento() {
        try {
            System.out.println("MEDICAMENTO "+medicamentoActual.getNombre());
            crud.generarConsecutivo(medicamentoActual);
            System.out.println("ID "+medicamentoActual.getIdMedicamento());
            crud.save(medicamentoActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Medicamento agregado"));
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "administrador";
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
    
    public Administrador getAdministrador() {
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

    public List<Usuario> getLstUsuarios() {
        try {
            lstUsuarios = crud.findAll(new Usuario());
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstUsuarios;
    }

    public void setLstUsuarios(List<Usuario> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }     

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Medicamento getMedicamentoActual() {
        return medicamentoActual;
    }

    public void setMedicamentoActual(Medicamento medicamentoActual) {
        this.medicamentoActual = medicamentoActual;
    }
       
    public List<Medicamento> getLstMedicamentos() {
        return lstMedicamentos;
    }

    public void setLstMedicamentos(List<Medicamento> lstMedicamentos) {
        try {
            lstMedicamentos = crud.findAll(new Medicamento());
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.lstMedicamentos = lstMedicamentos;
    }   
}
