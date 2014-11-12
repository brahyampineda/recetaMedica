package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.*;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.primefaces.event.TabChangeEvent;

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

    @ManagedProperty(value = "#{beanAdministrador}")
    private static BeanAdministrador adminBean = new BeanAdministrador();

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("loginBean");;

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

    private List<Medicamento> lstMedicamentos;
    private List<Tratamiento> lstTratamientos;
    private List<Medico> lstMedicosGenerales;
    private List<Medico> lstMedicosEspecialistas;
    private List<GrupoApoyo> lstGruposApoyo;
    private List<Laboratorio> lstLaboratorios;
    private List<Entidad> lstEntidades;
    private List<String> lstLeyes;
    private List<Enfermedad> lstEnfermedades;

    private String emailRestablecerContraseña;
    
    // Para el cambio de contraseña
    private boolean esEdicion;
    private boolean cambioContraseña;
    private String contraseñaActual;
    private String nuevaContraseña;
    private String verificacionNuevaContraseña;

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
    public void enviarCorreo(String de, String para, String asunto, String mensajeCorreo) {
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

    public void enviarCorreoRestablecerContraseña() {
        Usuario aux = crud.buscar(new Usuario(emailRestablecerContraseña));
        
        if (aux == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "El email no se encuentra registrado"));
            emailRestablecerContraseña = "";
        } else {
            String pass = "password";
            aux.setContrasena(pass);
            crud.guardar(aux);
            enviarCorreo(getAdmin().getUsuario().getEmail(), aux.getEmail(), "Restablecer contraseña", "Buenas " + aux.getNombre() +
                    ",\n\nUsted acaba de solicitar el cambio de contraseña de su usuario. La nueva contraseña de acceso es " + pass +
                    ".\n\nRecuerde que la puede volver a cambiar en cualquier momento.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Solicitud realizada", "Se envió a su email la nueva contraseña"));
            emailRestablecerContraseña = "";
        }
    }

    
    
    ////////////////////////////////////////////////////////////////////////////
    //////////  Perfil
    ////////////////////////////////////////////////////////////////////////////
    
    
    public boolean getEsEdicion() {
        return esEdicion;
    }

    public void setEsEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }

    public boolean getCambioContraseña() {
        return cambioContraseña;
    }

    public void setCambioContraseña(boolean cambioContraseña) {
        this.cambioContraseña = cambioContraseña;
    }

    public String getContraseñaActual() {
        return contraseñaActual;
    }

    public void setContraseñaActual(String contraseñaActual) {
        this.contraseñaActual = contraseñaActual;
    }

    public String getNuevaContraseña() {
        return nuevaContraseña;
    }

    public void setNuevaContraseña(String nuevaContraseña) {
        this.nuevaContraseña = nuevaContraseña;
    }

    public String getVerificacionNuevaContraseña() {
        return verificacionNuevaContraseña;
    }

    public void setVerificacionNuevaContraseña(String verificacionNuevaContraseña) {
        this.verificacionNuevaContraseña = verificacionNuevaContraseña;
    }

    public void preparaEditarPerfil() {
        usuarioActual = loginBean.loggedUser;
        esEdicion = true;
        contraseñaActual = "";
        nuevaContraseña = "";
        verificacionNuevaContraseña = "";
        
        System.out.println("OEeeee: " + loginBean.loggedUser.getNombre());
        System.out.println("OEeeeeUSUARIO: " + usuarioActual.getNombre());
    }

    public void guardarPerfil() {
        try {
            if (verificarCambioContraseña()) {
                crud.guardar(usuarioActual);
                esEdicion = false;
                contraseñaActual = "";
                nuevaContraseña = "";
                verificacionNuevaContraseña = "";
                adminBean.notificaciones(6, null);
            }
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(-1, "El perfil no se ha podido guardar exitosamente.");
        }
    }

    private boolean verificarCambioContraseña() {
        if ("".equals(contraseñaActual)) {
            adminBean.notificaciones(-1, "Debe escribir la contraseña para guardar el perfil");
            return false;
        } else if (contraseñaActual.equals(usuarioActual.getContrasena())) {
            if (nuevaContraseña.equals(verificacionNuevaContraseña)) {
                if (!nuevaContraseña.equals("")) {
                    usuarioActual.setContrasena(nuevaContraseña);
                }
                cambioContraseña = false;
                return true;
            } else {
                adminBean.notificaciones(-1, "La confirmación de la nueva contraseña no coincide.");
                return false;
            }
        } else {
            adminBean.notificaciones(-1, "La contraseña no es correcta.");
            return false;
        }
    }

    public void preparaCambioContraseña(){
        cambioContraseña = true;
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
//        try {
//            lstLeyes = new ArrayList();
//            for (int i = 0; i < 2; i++) {
//                lstLeyes.add(leerExcel("D:\\OneDrive\\UTP\\SEMESTRE 10\\Laboratorio de software\\Repositorio\\Fuente\\Leyes.xlsx", "Hoja1", "A"+(i+2)));
//            }
//            System.out.println("Longituddddddddd: " + lstLeyes.size());
//
//        } catch (Exception e) {
//            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
//        }
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

    public void buscarTodosEnfermedades() {
        try {
            lstEnfermedades = crud.buscarTodos(new Enfermedad());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            adminBean.notificaciones(0, null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Registro de Usuario
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
                registroActual.setEstado("En espera");
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
    ////////// Otros Métodos
    ////////////////////////////////////////////////////////////////////////////
//
//    public String leerExcel(String excel_file, String sheet_name, String cell_num) {
//        String datoCelda = "";
//
//        try {
//            Workbook workbook = Workbook.getWorkbook(new File(excel_file));
//
//            //Gets the sheet
//            Sheet sheet = workbook.getSheet(sheet_name);
//
//            /* Reads cell data*/
//            Cell cell = sheet.getCell(cell_num);
//            datoCelda = cell.getContents();
//
//            workbook.close();
//        } catch (IOException | BiffException e) {
//            System.out.println("readExcel ->" + e);
//        }
//        return datoCelda;
//    }
//
//    public void escribirExcel(String excel_file, String sheet_name, int row, int column, Double value) {
//        String cellData = new String();
//        try {
//            Workbook target_workbook = Workbook.getWorkbook(new File(excel_file));
//            WritableWorkbook workbook = Workbook.createWorkbook(new File(excel_file), target_workbook);
//
//            target_workbook.close();
//
//            WritableSheet sheet = workbook.getSheet(sheet_name);
//
//            jxl.write.Number number = new jxl.write.Number(column, row, value);
//            sheet.addCell(number);
//
//            workbook.write();
//
//            workbook.close();
//        } catch (Exception e) {
//            System.out.println("writeExcel ->" + e);
//        }
//    }
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

    public String getEmailRestablecerContraseña() {
        return emailRestablecerContraseña;
    }

    public void setEmailRestablecerContraseña(String emailRestablecerContraseña) {
        this.emailRestablecerContraseña = emailRestablecerContraseña;
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

    public List<String> getLstLeyes() {
        buscarTodosLeyes();
        return lstLeyes;
    }

    public void setLstLeyes(List<String> lstLeyes) {
        this.lstLeyes = lstLeyes;
    }

}
