package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.*;
import com.utp.receta_medica.facade.LoginService;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.Serializable;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "beanGeneral")
@ManagedBean
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

    @ManagedProperty(value = "#{beanAdministrador}")
    private static BeanAdministrador adminBean;

    private Boolean esAdmin = true;
    /////////////////////////////////////////

    private Usuario usuarioActual;
    private Administrador admin;

    private Registro registroActual;
    private String[] perfiles;

    //Datos de contacto
    private String nombreContacto;
    private String correoContacto;
    private String mensajeContacto;

    List<Medicamento> lstMedicamentos;
    List<Tratamiento> lstTratamientos;
    List<Medico> lstMedicosGenerales;
    List<Medico> lstMedicosEspecialistas;
    List<String> lstLeyes;
    List<GrupoApoyo> lstGruposApoyo;
    List<Laboratorio> lstLaboratorios;
    List<Entidad> lstEntidades;
    
    List<Enfermedad> lstEnfermedades;

    public BeanGeneral() {
    }

    //***************************************
    // METODOS
    //***************************************
    /**
     * Envía un correo de contacto a al administrador
     */
    public void enviarCorreoDeContacto() {
        String de = correoContacto;     // La dirección de la cuenta de envío (from)
        String para = getAdmin().getUsuario().getEmail();
        String asunto = "Mensaje de contacto de: " + correoContacto;
        String mensajeEnvio = nombreContacto + " envía el siguiente mensaje de contacto: \n\n" + mensajeContacto;

        try {
            enviarCorreo(de, para, asunto, mensajeEnvio);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje de contacto", "El mensaje fue enviado."));
            System.out.println("Mensaje enviado");
            correoContacto = "";
            nombreContacto = "";
            mensajeContacto = "";
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje de contacto", "El mensaje no se pudo enviar."));
        }
    }

    public void enviarCorreoSolicitudRegistro(String de, String nombre, String perfiles[]) {
        String para = getAdmin().getUsuario().getEmail();
        String asunto = "Solicitud de registro de: " + nombre;
        String mensaje = "El usuario " + nombre + " con correo " + de
                + " solicita registrarse en la plataforma con los siguientes perfiles: " + Arrays.toString(perfiles);
        try {
            enviarCorreo(de, para, asunto, mensaje);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registro", "La solicitud se envió correctamente"));
            System.out.println("Mensaje enviado");
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registro", "La solicitud no puedo ser enviada."));
        }
    }

    /**
     * Envía un correo electrónico al administrador.
     *
     * @param de El usuario que envía el correo
     * @param asunto El asunto del correo
     * @param mensajeCorreo El mensaje del correo
     */
    private void enviarCorreo(String de, String para, String asunto, String mensajeCorreo) {
        try {
            Properties propiedades = System.getProperties();                                // Obtenemos las propiedades del sistema
            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");                    // Configuramos el servidor de correo, host de correo
            propiedades.setProperty("mail.smtp.starttls.enable", "true");                   // TLS si está disponible
            propiedades.setProperty("mail.smtp.port", "587");                               // Puerto de Gmail para el envio de correos
            propiedades.setProperty("mail.smtp.user", "recetamedica.utpereira@gmail.com");  // Configuramos el servidor de correo
            propiedades.setProperty("mail.smtp.password", "adminutp");                      // Configuramos el servidor de correo

            Session sesion = Session.getDefaultInstance(propiedades);

            MimeMessage mensaje = new MimeMessage(sesion);                              // Creamos un objeto mensaje tipo MimeMessage por defecto.
            mensaje.setFrom(new InternetAddress(de));                                   // Asignamos el “de o from” al header del correo.
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));  // Asignamos el “para o to” al header del correo.
            mensaje.setSubject(asunto);                                                 // Asignamos el asunto
            mensaje.setText(mensajeCorreo);                                             // Asignamos el mensaje como tal

            Transport t = sesion.getTransport("smtp");
            t.connect("recetamedica.utpereira@gmail.com", "adminutp");  // Conectamos el usuario que va enviar el correo
            t.sendMessage(mensaje, mensaje.getAllRecipients());         // Enviamos el mensaje
        } catch (MessagingException ex) {
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Consultas
    ////////////////////////////////////////////////////////////////////////////
    public void buscarTodosMedicamentos() {
        try {
            lstMedicamentos = crud.buscarTodos(new Medicamento());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosTratamientos() {
        try {
            lstTratamientos = crud.buscarTodos(new Tratamiento());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosMedicosGenerales() {
        try {
            Medico med = new Medico();
            med.setEsEspecialista(false);
            lstMedicosGenerales = crud.buscarTodos(med);
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosMedicosEspecialistas() {
        try {
            Medico med = new Medico();
            med.setEsEspecialista(true);
            lstMedicosEspecialistas = crud.buscarTodos(med);
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosLeyes() {
        try {
            //crud.buscarTodos(new Ley());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosGruposApoyo() {
        try {
            lstGruposApoyo = crud.buscarTodos(new GrupoApoyo());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosLaboratorios() {
        try {
            lstLaboratorios = crud.buscarTodos(new Laboratorio());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void buscarTodosEntidades() {
        try {
            lstEntidades = crud.buscarTodos(new Entidad());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    public void prepararListasDeConsulta() {
        adminBean = new BeanAdministrador();
        buscarTodosEntidades();
        buscarTodosGruposApoyo();
        buscarTodosLaboratorios();
        buscarTodosLeyes();
        buscarTodosMedicosEspecialistas();
        buscarTodosMedicamentos();
        buscarTodosMedicosGenerales();
        buscarTodosTratamientos();
    }

    public void buscarTodosEnfermedades(){
        try {
            lstEnfermedades = crud.buscarTodos(new Enfermedad());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //////////  OTRAS SDOSFDGDOFSGDFH
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Se inicializan las variables que contendran información del nuevo usuario
     * y registro a guardar
     *
     */
    public void prepararRegistroUsuario() {
        System.out.println("Prepara registro de usuario");
        perfiles = new String[3];
        usuarioActual = new Usuario();
        registroActual = new Registro();
        registroActual.setAdministrador(getAdmin());
    }

    /**
     * Registra un nuevo usuario con los datos ingresados, se crea un registro
     * asociado a ese usuario
     *
     */
    public void solicitarRegistroUsuario() {
        try {
            Usuario aux = new Usuario();
            aux.setIdentificacion(usuarioActual.getIdentificacion());
            if (crud.buscarTodos(aux).size() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Ya existe un usuario con esa identificación"));
            } else {
                crud.guardar(usuarioActual);
                registroActual.setEstado("en espera");
                registroActual.setUsuario(usuarioActual);
                registroActual.setPerfil(Arrays.toString(perfiles));
                crud.guardar(registroActual);
                getAdmin().getRegistroCollection().add(registroActual);
                crud.guardar(getAdmin());
                enviarCorreoSolicitudRegistro(usuarioActual.getEmail(), usuarioActual.getNombre() + " " + usuarioActual.getApellidos(), perfiles);
                prepararRegistroUsuario();
            }
        } catch (Exception ex) {
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////// Getters y Setters
    ////////////////////////////////////////////////////////////////////////////
    public Administrador getAdmin() {
        List<Administrador> aux = crud.buscarTodos(new Administrador());
        admin = aux.get(0);
        if (admin.getRegistroCollection() == null) {
            admin.setRegistroCollection(new ArrayList<Registro>());
        }
        if (admin.getSolicitudQuejasReclamosCollection() == null) {
            admin.setSolicitudQuejasReclamosCollection(new ArrayList<SolicitudQuejasReclamos>());
        }
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    public BeanAdministrador getAdminBean() {
        if (adminBean == null) {
            adminBean = new BeanAdministrador();
        }
        return adminBean;
    }

    public void setAdminBean(BeanAdministrador adminBean) {
        this.adminBean = adminBean;
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
        if (usuarioActual == null) {
            usuarioActual = new Usuario();
        }
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Registro getRegistroActual() {
        if (registroActual == null) {
            registroActual = new Registro();
        }
        return registroActual;
    }

    public void setRegistroActual(Registro registroActual) {
        this.registroActual = registroActual;
    }

    public String[] getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(String[] perfiles) {
        this.perfiles = perfiles;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getMensajeContacto() {
        return mensajeContacto;
    }

    public void setMensajeContacto(String mensajeContacto) {
        this.mensajeContacto = mensajeContacto;
    }

    //------------------------------ Listas ------------------------------------
    public List<Tratamiento> getLstTratamientos() {
        buscarTodosTratamientos();
        return lstTratamientos;
    }

    public void setLstTratamientos(List<Tratamiento> lstTratamientos) {
        this.lstTratamientos = lstTratamientos;
    }

    public List<Medicamento> getLstMedicamentos() {
        buscarTodosMedicamentos();
        return lstMedicamentos;
    }

    public void setLstMedicamentos(List<Medicamento> lstMedicamentos) {
        this.lstMedicamentos = lstMedicamentos;
    }

    public List<Medico> getLstMedicosGenerales() {
        buscarTodosMedicosGenerales();
        return lstMedicosGenerales;
    }

    public void setLstMedicosGenerales(List<Medico> lstMedicosGenerales) {
        this.lstMedicosGenerales = lstMedicosGenerales;
    }

    public List<Medico> getLstMedicosEspecialistas() {
        buscarTodosMedicosEspecialistas();
        return lstMedicosEspecialistas;
    }

    public void setLstMedicosEspecialistas(List<Medico> lstMedicosEspecialistas) {
        this.lstMedicosEspecialistas = lstMedicosEspecialistas;
    }

    public List<String> getLstLeyes() {
        buscarTodosLeyes();
        return lstLeyes;
    }

    public void setLstLeyes(List<String> lstLeyes) {
        this.lstLeyes = lstLeyes;
    }

    public List<GrupoApoyo> getLstGruposApoyo() {
        buscarTodosGruposApoyo();
        return lstGruposApoyo;
    }

    public void setLstGruposApoyo(List<GrupoApoyo> lstGruposApoyo) {
        this.lstGruposApoyo = lstGruposApoyo;
    }

    public List<Laboratorio> getLstLaboratorios() {
        buscarTodosLaboratorios();
        return lstLaboratorios;
    }

    public void setLstLaboratorios(List<Laboratorio> lstLaboratorios) {
        this.lstLaboratorios = lstLaboratorios;
    }

    public List<Entidad> getLstEntidades() {
        buscarTodosEntidades();
        return lstEntidades;
    }

    public void setLstEntidades(List<Entidad> lstEntidades) {
        this.lstEntidades = lstEntidades;
    }

    public List<Enfermedad> getLstEnfermedades() {
        buscarTodosEnfermedades();
        return lstEnfermedades;
    }

    public void setLstEnfermedades(List<Enfermedad> lstEnfermedades) {
        this.lstEnfermedades = lstEnfermedades;
    }
    
    
}
