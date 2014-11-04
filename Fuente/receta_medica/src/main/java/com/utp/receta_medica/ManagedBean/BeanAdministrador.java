package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.*;
import com.utp.receta_medica.facade.LoginService;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
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
public class BeanAdministrador implements Serializable {

    //***************************************
    // ATRIBUTOS
    //***************************************
    @EJB
    private TablasFacade crud;

    //////Atributos para manejo de login//////
//    @ManagedProperty(value = "#{loginService}")
//    private LoginService loginService;
    private Boolean esAdmin = true;
    /////////////////////////////////////////

    private static BeanGeneral beanGeneral;
    Administrador administrador;

    Medicamento medicamentoActual;
    Tratamiento tratamientoActual;
    Medico medicoGeneralActual;
    Medico medicoEspecialistaActual;
    String leyActual;
    GrupoApoyo grupoApoyoActual;
    Laboratorio laboratorioActual;
    Entidad entidadActual;

    SolicitudQuejasReclamos sqr;
    List<Usuario> lstUsuarios;

    //***************************************
    // METODOS
    //***************************************
    public BeanAdministrador() {
    }
    
    
    @PostConstruct
    private void init() {
        //Cuando la aplicacion renderize asigna la fila administrador a la variable
        administrador = new Administrador();
        //Comprueba si es administrador
//        esAdmin = getLoginService().isAdmin();
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Medicamento
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearMedicamento() {
        medicamentoActual = new Medicamento();
//        medicamentoActual.setLaboratorioCollection(new ArrayList<Laboratorio>());
    }

    public void preparaEditarMedicamento(Medicamento obj) {
        medicamentoActual = (Medicamento) obj;
    }

    public void guardarMedicamento() {
        try {
            crud.guardar(medicamentoActual);
            notificaciones(1, "El Medicamento");
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "El Medicamento");
        }
    }

    public void eliminarMedicamento(Medicamento obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El medicamento");
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "El medicamento");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Tratamiento
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearTratamiento() {
        tratamientoActual = new Tratamiento(new TratamientoPK());
    }

    public void preparaEditarTratamiento(Tratamiento obj) {
        tratamientoActual = obj;
    }

    public void guardarTratamiento() {
        try {
            System.out.println("nombre: " + tratamientoActual.getNombre());
            crud.guardar(tratamientoActual);
            preparaCrearTratamiento();
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    //////////  Médico general
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    //////////  Médico especialista
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////  Ley
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////  Grupo de apoyo
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////  Laboratorio
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////  Entidad
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////  Usuario
    ////////////////////////////////////////////////////////////////////////////
    public void crearUsuario(Usuario user) {

        crud.guardar(user);
        System.out.println("USER " + user);
    }

    public String eliminarUsuario(Usuario user) {
        try {
            System.out.println("DSDSSDSD " + user.getNombre());
            crud.eliminar(user);
            System.out.println("DSDSSDSD " + user.getNombre());
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    public String btnAdministradorTemp() {
        beanGeneral = new BeanGeneral();
        beanGeneral.prepararListasDeConsulta();
        preparaCrearMedicamento();
        preparaCrearTratamiento();

        return "administrador";
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Otros métodos
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Genera el mensaje que será notificado al usuario en la vista.
     *
     * @param codigoMensaje El código del mensaje a borrar:<br/>
     * 0. Se produjo un error <br/>
     * 1. Objeto <code>tipoObjeto</code> guardado <br/>
     * 2. Objeto <code>tipoObjeto</code> editado <br/>
     * 3. Objeto <code>tipoObjeto</code> eliminado <br/>
     * 4. Mensaje enviado<br/>
     * 5. Mensaje no enviado<br/>
     * @param tipoObjeto El nombre del objeto
     */
    public void notificaciones(int codigoMensaje, String tipoObjeto) {
        String mensaje = "";

        switch (codigoMensaje) {
            case 0: // Error
                mensaje = "Se produjo un error.";
                break;

            case 1: // Guardar
                mensaje = tipoObjeto + " se ha guardado correctamente.";
                break;

            case 2: // Editar
                mensaje = tipoObjeto + " se ha editado correctamente.";
                break;

            case 3: // Eliminar
                mensaje = tipoObjeto + " se ha eliminado correctamente.";
                break;

            case 4: // Mensaje enviado
                mensaje = "El mensaje se envió correctamente";
                break;

            case 5: // Mensaje no enviado
                mensaje = "No se pudo enviar el mensaje";
                break;

            default:
                break;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", mensaje));
    }

    public String prepararAdministracion() {
        System.out.println("11111");
        try {
            administrador = new Administrador();
//            administrador.setAdministradorPK(new AdministradorPK("1", "1"));
            administrador.setRegistroCollection(new ArrayList<Registro>());
//            administrador.setSolicitudQuejasReclamosCollection(new ArrayList<SolicitudQuejasReclamos>());
            System.out.println("AMIN " + administrador);
            medicamentoActual = new Medicamento();
            tratamientoActual = new Tratamiento();
            administrador = crud.find(administrador);
//            System.out.println("AMIN22 " + administrador.getUsuario().getIdUsuario());
            for (Registro registro : administrador.getRegistroCollection()) {
                System.out.println("REGISTRO " + registro.getUsuario().getNombre());
            }

        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "administrador";
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////// Getters y Setters
    ////////////////////////////////////////////////////////////////////////////
    public static BeanGeneral getBeanGeneral() {
        if (beanGeneral == null) {
            beanGeneral = new BeanGeneral();
        }
        return beanGeneral;
    }

    public static void setBeanGeneral(BeanGeneral beanGeneral) {
        BeanAdministrador.beanGeneral = beanGeneral;
    }

    public Medico getMedicoGeneralActual() {
        return medicoGeneralActual;
    }

    public void setMedicoGeneralActual(Medico medicoGeneralActual) {
        this.medicoGeneralActual = medicoGeneralActual;
    }

    public Medico getMedicoEspecialistaActual() {
        return medicoEspecialistaActual;
    }

    public void setMedicoEspecialistaActual(Medico medicoEspecialistaActual) {
        this.medicoEspecialistaActual = medicoEspecialistaActual;
    }

    public String getLeyActual() {
        return leyActual;
    }

    public void setLeyActual(String leyActual) {
        this.leyActual = leyActual;
    }

    public GrupoApoyo getGrupoApoyoActual() {
        return grupoApoyoActual;
    }

    public void setGrupoApoyoActual(GrupoApoyo grupoApoyoActual) {
        this.grupoApoyoActual = grupoApoyoActual;
    }

    public Laboratorio getLaboratorioActual() {
        return laboratorioActual;
    }

    public void setLaboratorioActual(Laboratorio laboratorioActual) {
        this.laboratorioActual = laboratorioActual;
    }

    public Entidad getEntidadActual() {
        return entidadActual;
    }

    public void setEntidadActual(Entidad entidadActual) {
        this.entidadActual = entidadActual;
    }

//    public LoginService getLoginService() {
//        return loginService;
//    }
//
//    public void setLoginService(LoginService loginService) {
//        this.loginService = loginService;
//    }
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

    public Medicamento getMedicamentoActual() {
        if (medicamentoActual == null) {
            medicamentoActual = new Medicamento();
        }
        return medicamentoActual;
    }

    public void setMedicamentoActual(Medicamento medicamentoActual) {
        this.medicamentoActual = medicamentoActual;
    }

    public Tratamiento getTratamientoActual() {
        if (tratamientoActual == null) {
            tratamientoActual = new Tratamiento();
        }
        return tratamientoActual;
    }

    public void setTratamientoActual(Tratamiento tratamientoActual) {
        this.tratamientoActual = tratamientoActual;
    }

}
