package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.*;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "beanGeneral")
@ManagedBean
@SessionScoped
public class BeanGeneral implements Serializable {

    @EJB
    private TablasFacade crud;

    @ManagedProperty(value = "#{beanAdministrador}")
    private static BeanAdministrador adminBean = new BeanAdministrador();

    //Inicio de sesión
    private Usuario loggedUser;
    private boolean loggedIn;
    private boolean admin;
    private boolean paciente;
    private boolean medicoGeneral;
    private boolean medicoEspecialista;
    private boolean visitante = true;
    private boolean editarPerfil;
    private String username;
    private String password;

    private List<String> lstPerfiles;
    private String perfilActual;
//..............................................................................
    // Para el cambio de contraseña
    private boolean cambioContraseña;
    private String contraseñaActual;
    private String nuevaContraseña;
    private String verificacionNuevaContraseña;
    private String emailRestablecerContraseña;
//..............................................................................
    // Perfiles
    private Usuario usuarioActual;
    private Administrador administrador;
    private Paciente pacienteActual;
    private Medico medicoActual;

    private Registro registroActual;
    private String[] perfiles;

    // Datos de contacto
    private String nombreContacto;
    private String correoContacto;
    private String mensajeContacto;
//..............................................................................
    // Listas
    private List<Medicamento> lstMedicamentos;
    private List<Tratamiento> lstTratamientos;
    private List<GrupoApoyo> lstGruposApoyo;
    private List<Laboratorio> lstLaboratorios;
    private List<Entidad> lstEntidades;
    private List<Ley> lstLeyes;
    private List<Enfermedad> lstEnfermedades;

    private List<Paciente> lstPacientes;
    private List<Medico> lstMedicosEspecialistas;
    private List<Medico> lstMedicosGenerales;

    private List<Consulta> lstConsultasPorMedico;
//..............................................................................

    private SolicitudQuejasReclamos solicitudActual;
    private Consulta consultaMedicaActual;
    private Paciente pacienteConsulta;
    private String identificacionPacienteConsultas;
    private RecetaMedica recetaMedicaActual;
    private List<String> deportesConsulta;

    private CroppedImage croppeFoto;
    private String imageTemp;

    //**************************************************************************
    //**************************************************************************
    //*******************************  MÉTODOS  ********************************
    //**************************************************************************
    //**************************************************************************
    ////////////////////////////////////////////////////////////////////////////
    //////////  Perfiles
    ////////////////////////////////////////////////////////////////////////////
    public void preparaInicioDeSesion() {
        loggedUser = new Usuario();
        username = "";
        password = "";
        loggedIn = false;
        admin = false;
        paciente = false;
        medicoGeneral = false;
        medicoEspecialista = false;
        visitante = true;
        editarPerfil = false;
    }

    public void preparaListaPerfiles() {
        lstPerfiles = new ArrayList<>();
        if (paciente) {
            lstPerfiles.add("Paciente");
        }
        if (medicoEspecialista) {
            lstPerfiles.add("Médico especialista");
        }
        if (medicoGeneral) {
            lstPerfiles.add("Médico general");
        }
    }

    public String accederAperfil() {
        System.out.println("Perfil seleccionado: " + perfilActual);
        editarPerfil = false;
        cambioContraseña = false;
        inicializarVariables();
        if (perfilActual.equals("Paciente")) {
            paciente = true;
            medicoEspecialista = false;
            medicoGeneral = false;
            return "/perfilPaciente";
        }
        if (perfilActual.equals("Médico especialista")) {
            paciente = false;
            medicoEspecialista = true;
            medicoGeneral = false;
            if (loggedUser.getMedicoCollection().size() > 1) {
                for (Medico medico : loggedUser.getMedicoCollection()) {
                    if (medico.getEsEspecialista()) {
                        medicoActual = medico;
                        break;
                    }
                }
            }
            return "/perfilMedicoEspecialista";
        }
        if (perfilActual.equals("Médico general")) {
            paciente = false;
            medicoEspecialista = false;
            medicoGeneral = true;
            if (loggedUser.getMedicoCollection().size() > 1) {
                for (Medico medico : loggedUser.getMedicoCollection()) {
                    if (!medico.getEsEspecialista()) {
                        medicoActual = medico;
                        break;
                    }
                }
            }
            return "/perfilMedicoGeneral";
        }
        return null;
    }

    public void cambioPerfil(AjaxBehaviorEvent event) {
        perfilActual = (String) ((UIOutput) event.getSource()).getValue();
        System.out.println("Perfil cambió a: " + perfilActual);
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Manejo de sesión
    ////////////////////////////////////////////////////////////////////////////
    public String doLogin() {
        System.out.println("USERNAME: " + username);
        System.out.println("PASSWORD: " + password);

        Usuario usuario = new Usuario();
        usuario.setIdentificacion(username);
        List<Usuario> usuarios;
        try {
            usuarios = crud.buscarTodos(usuario);
            if (usuarios.isEmpty()) {
                adminBean.notificaciones(-1, "El usuario no está registrado");
                return null;
            } else {
                usuario = usuarios.get(0);
            }
        } catch (Exception ex) {
            usuario = null;
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        if (usuario.getContrasena() != null) {
            if (usuario.getContrasena().equals(password)) {
                loggedIn = true;
                loggedUser = usuario;
                admin = false;
                paciente = false;
                medicoGeneral = false;
                medicoEspecialista = false;
                visitante = false;

                if (!usuario.getAdministradorCollection().isEmpty()) { // Verifica si es administrador
                    admin = true;
                    adminBean.notificaciones(-1, "Bienvenido " + loggedUser.getNombre());
                    perfilActual = "Administrador";
                    for (Administrador perfil : loggedUser.getAdministradorCollection()) {
                        System.out.println("A");
                        administrador = perfil;
                    }
                    return "/perfilAdmin";
                } else {
                    admin = false;
                    if (!usuario.getPacienteCollection().isEmpty()) {
                        System.out.println("Es Paciente");
                        paciente = true;
                        for (Paciente perfil : loggedUser.getPacienteCollection()) {
                            System.out.println("P");
                            pacienteActual = perfil;
                        }
                    }
                    if (!usuario.getMedicoCollection().isEmpty()) {
                        for (Medico item : usuario.getMedicoCollection()) {
                            if (item.getEsEspecialista()) {
                                medicoEspecialista = true;
                                medicoActual = item;
                                System.out.println("Es Médico Especialista");
                            } else {
                                medicoGeneral = true;
                                medicoActual = item;
                                System.out.println("Es Médico General");
                            }
                        }
                    }
                }
                preparaListaPerfiles();
                if (paciente) {
                    adminBean.notificaciones(-1, "Bienvenido " + loggedUser.getNombre());
                    perfilActual = "Paciente";
                    medicoEspecialista = false;
                    medicoGeneral = false;
                    return "/perfilPaciente";
                }
                if (medicoEspecialista) {
                    adminBean.notificaciones(-1, "Bienvenido " + loggedUser.getNombre());
                    perfilActual = "Médico especialista";
                    paciente = false;
                    medicoGeneral = false;
                    return "/perfilMedicoEspecialista";
                }
                if (medicoGeneral) {
                    adminBean.notificaciones(-1, "Bienvenido " + loggedUser.getNombre());
                    perfilActual = "Médico general";
                    paciente = false;
                    medicoEspecialista = false;
                    return "/perfilMedicoGeneral";
                }
            } else {
                adminBean.notificaciones(-1, "Contraseña incorrecta");
                return null;
            }
        }
        System.out.println("ojooooo!!!!");
        return null;
    }

    public String doLogout() {
        loggedIn = false;
        loggedUser = null;
        admin = false;
        paciente = false;
        medicoGeneral = false;
        medicoEspecialista = false;
        visitante = true;
        perfilActual = "Visitante";
        editarPerfil = false;
        lstConsultasPorMedico = new ArrayList<>();
        lstPerfiles = new ArrayList<>();
        username = "";
        password = "";
        usuarioActual = null;
        pacienteActual = null;
        medicoActual = null;
        solicitudActual = null;
        recetaMedicaActual = null;
        pacienteConsulta = null;
        identificacionPacienteConsultas = "";

        System.out.println("\n\nCerrando sesión.\n\n");
        adminBean.notificaciones(-1, "Se ha cerrado la sesión correctamente");
        return "/index";
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Perfil de Usuario
    ////////////////////////////////////////////////////////////////////////////
    public void preparaEditarPerfil() {
        editarPerfil = true;
        contraseñaActual = "";
        nuevaContraseña = "";
        verificacionNuevaContraseña = "";
    }

    public void guardarPerfil() {
        try {
            if (verificarCambioContraseña()) {
                System.out.println("Perfil actual a guardar: " + perfilActual);
                switch (perfilActual) {
                    case "Administrador":
                        administrador.setUsuario(loggedUser);
                        crud.guardar(administrador);
                        crud.guardar(administrador.getUsuario());
                        break;
                    case "Paciente":
                        pacienteActual.getUsuario().setFoto("/resources/imagenes/imagenes de perfil/" + pacienteActual.getUsuario().getEmail() + ".jpg");
                        crud.guardar(pacienteActual);
                        crud.guardar(pacienteActual.getUsuario());
                        break;
                    case "Médico especialista":
                        medicoActual.getUsuario().setFoto("/resources/imagenes/imagenes de perfil/" + medicoActual.getUsuario().getEmail() + ".jpg");
                        crud.guardar(medicoActual);
                        crud.guardar(medicoActual.getUsuario());
                        break;
                    case "Médico general":
                        medicoActual.getUsuario().setFoto("/resources/imagenes/imagenes de perfil/" + medicoActual.getUsuario().getEmail() + ".jpg");
                        crud.guardar(medicoActual);
                        crud.guardar(medicoActual.getUsuario());
                        break;
                }
                editarPerfil = false;
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
        } else if (contraseñaActual.equals(loggedUser.getContrasena())) {
            if (nuevaContraseña.equals(verificacionNuevaContraseña)) {
                if (!nuevaContraseña.equals("")) {
                    loggedUser.setContrasena(nuevaContraseña);
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

    public void preparaCambioContraseña() {
        cambioContraseña = true;
        nuevaContraseña = "";
        verificacionNuevaContraseña = "";
    }

    public void inicializarVariables() {
        lstConsultasPorMedico = new ArrayList<>();
        identificacionPacienteConsultas = "";
        pacienteConsulta = null;
        getPacienteConsulta();
    }

    public String solicitarEliminarCuenta() {
        SolicitudQuejasReclamos aux = new SolicitudQuejasReclamos(new SolicitudQuejasReclamosPK(null, loggedUser.getEmail()));
        aux.setAdministrador(getAdministrador());
        aux.setUsuario(loggedUser);
        aux.setDescripcion("El usuario " + loggedUser.getNombre() + " " + loggedUser.getApellidos()
                + " solicita eliminar su cuenta de usuario.");
        aux.setEstado("Eliminación");
        aux.setTitulo("Eliminar cuenta");
        crud.guardar(aux);
        getAdministrador().getSolicitudQuejasReclamosCollection().add(aux);
        crud.guardar(getAdministrador());
        loggedUser.getSolicitudQuejasReclamosCollection().add(aux);
        crud.guardar(loggedUser);

        enviarCorreo(aux.getUsuario().getEmail(), getAdministrador().getUsuario().getEmail(), "Eliminar cuenta de usuario", "El usuario "
                + aux.getUsuario().getNombre() + " " + aux.getUsuario().getApellidos() + " solicita eliminar su cuenta de usuario.");

        return doLogout();
    }

    public void actionFoto() {
        this.croppeFoto = null;
        this.imageTemp = null;
    }

    public void actionGuardarFoto() {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/imagenes/imagenes de perfil");
        String archivo = path + File.separator + this.getLoggedUser().getEmail() + ".jpg";

        loggedUser.setFoto("/resources/imagenes/imagenes de perfil/" + loggedUser.getEmail() + ".jpg");
        crud.guardar(loggedUser);

        try {

            if (croppeFoto != null) {
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(archivo));
                imageOutput.write(croppeFoto.getBytes(), 0, croppeFoto.getBytes().length);
                imageOutput.close();
            } else {
                OutputStream outStream = new FileOutputStream(new File(archivo));
                InputStream inputStream = new FileInputStream(path + "/temp/" + this.getImageTemp());
                byte[] buffer = new byte[6124];
                int bulk;
                while (true) {
                    bulk = inputStream.read(buffer);
                    if (bulk < 0) {
                        break;
                    }
                    outStream.write(buffer, 0, bulk);
                    outStream.flush();
                }
                outStream.close();
                inputStream.close();
            }
            actionFoto();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile(FileUploadEvent event) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/imagenes/imagenes de perfil/temp");
            String archivo = path + File.separator + event.getFile().getFileName();

            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            byte[] buffer = new byte[6124];
            int bulk;
            InputStream inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();

            this.setImageTemp(event.getFile().getFileName());

        } catch (IOException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al subir el archivo"));
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Correos
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Envía un correo de contacto a al administrador
     */
    public void enviarCorreoDeContacto() {
        String de = correoContacto;     // La dirección de la cuenta de envío (from)
        String para = getAdministrador().getUsuario().getEmail();
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
        String para = getAdministrador().getUsuario().getEmail();
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
     * @param para El usuario que recibe el correo
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
            adminBean.notificaciones(-1, "El email no se encuentra registrado");
            emailRestablecerContraseña = "";
        } else {
            String pass = "Password123";
            aux.setContrasena(pass);
            crud.guardar(aux);
            enviarCorreo(getAdministrador().getUsuario().getEmail(), aux.getEmail(), "Restablecer contraseña", "Buenas " + aux.getNombre()
                    + ",\n\nUsted acaba de solicitar el cambio de contraseña de su usuario. La nueva contraseña de acceso es " + pass
                    + ".\n\nRecuerde que la puede volver a cambiar en cualquier momento desde el perfil.");
            adminBean.notificaciones(-1, "Se envió a su email una nueva contraseña");
            emailRestablecerContraseña = "";
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
            lstLeyes = crud.buscarTodos(new Ley());
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
        registroActual.setAdministrador(getAdministrador());
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
                if (crud.buscar(usuarioActual) != null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Ya existe un usuario con ese email"));
                } else {
                    crud.guardar(usuarioActual);
                    registroActual.setEstado("En espera");
                    registroActual.setUsuario(usuarioActual);
                    registroActual.setPerfil(Arrays.toString(perfiles));
                    crud.guardar(registroActual);
                    getAdministrador().getRegistroCollection().add(registroActual);
                    crud.guardar(getAdministrador());
                    enviarCorreoSolicitudRegistro(usuarioActual.getEmail(), usuarioActual.getNombre() + " " + usuarioActual.getApellidos(), perfiles);
                    prepararRegistroUsuario();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BeanGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////// Solicitudes
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearSolicitud() {
        solicitudActual = null;
        getSolicitudActual();
    }

    public void crearSolicitud() {
        try {
            solicitudActual.setEstado("Nueva");
            solicitudActual.setUsuario(loggedUser);
            solicitudActual.setAdministrador(getAdministrador());
            solicitudActual.getSolicitudQuejasReclamosPK().setUsuarioemail(loggedUser.getEmail());
            crud.guardar(solicitudActual);
            solicitudActual.getAdministrador().getSolicitudQuejasReclamosCollection().add(solicitudActual);
            crud.guardar(solicitudActual.getAdministrador());
            loggedUser.getSolicitudQuejasReclamosCollection().add(solicitudActual);
            crud.guardar(loggedUser);

            String mensaje = "El usuario " + loggedUser.getNombre() + " " + loggedUser.getApellidos() + " envía la solicitud con el motivo: "
                    + solicitudActual.getTitulo() + "\n\nDescripción del mensaje: " + solicitudActual.getDescripcion();
            enviarCorreo(loggedUser.getEmail(), getAdministrador().getUsuario().getEmail(), "Solicitud General", mensaje);
            adminBean.notificaciones(-1, "La solicitud se ha enviado correctamente");
            preparaCrearSolicitud();

        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////// Consultas médicas
    ////////////////////////////////////////////////////////////////////////////
    public void buscarTodosPacientes() {
        try {
            lstPacientes = crud.buscarTodos(new Paciente());
            System.out.println("Cantidad de pacientes: " + lstPacientes.size());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void preparaCrearConsultaMedica() {
        consultaMedicaActual = null;
        getConsultaMedicaActual();
        deportesConsulta = new ArrayList<>();
        buscarTodosPacientes();
    }

//    public void preparaEditarConsultaMedica(Consulta obj) {
//        consultaMedicaActual = obj;
//        buscarTodosPacientes();
//    }
    public void guardarConsultaMedica() {
        try {
            if (consultaMedicaActual.getAltura() < 0 || consultaMedicaActual.getPeso() < 0) {
                adminBean.notificaciones(-1, "No se permiten valores negativos.");
            } else {
                consultaMedicaActual.setDeportes(deportesConsulta.toString());
                consultaMedicaActual.setMedico(medicoActual);
                consultaMedicaActual.getConsultaPK().setMedicoidentificacion(consultaMedicaActual.getMedico().getUsuario().getIdentificacion());
                consultaMedicaActual.getConsultaPK().setPacienteidentificacion(consultaMedicaActual.getPaciente().getUsuario().getIdentificacion());
                crud.guardar(consultaMedicaActual);
                adminBean.notificaciones(1, "La consulta médica ");
                preparaCrearConsultaMedica();
            }
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

//    public void borrarConsultaMedica(Consulta obj) {
//        try {
//            crud.eliminar(obj);
//            adminBean.notificaciones(3, "La consulta médica ");
//        } catch (Exception e) {
//            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
    public void preparaBusquedaConsultasPaciente() {
        pacienteConsulta = null;
        getPacienteConsulta();
        identificacionPacienteConsultas = "";
        lstConsultasPorMedico = new ArrayList<>();
    }

    public void buscarConsultasPaciente() {
        try {
            pacienteConsulta = crud.buscar(new Paciente(identificacionPacienteConsultas));

            Consulta aux = new Consulta();
            Medico medico = medicoActual;
            if (medico.getIdentificacion().endsWith("00")) {
                medico.setIdentificacion(medico.getUsuario().getIdentificacion());
            }
            aux.setMedico(medico);
            aux.setPaciente(pacienteConsulta);
            if (pacienteConsulta != null) {
                lstConsultasPorMedico = crud.buscarTodos(aux);
            }else{
                lstConsultasPorMedico = new ArrayList<>();
            }
            System.out.println("Cantidad consultas: " + lstConsultasPorMedico.size());
            System.out.println("Medico: " + medico.getIdentificacion());

        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void preparaCrearRecetaMedica() {
        recetaMedicaActual = null;
        getRecetaMedicaActual();
    }

    public void preparaEditarRecetaMedica(RecetaMedica obj) {
        recetaMedicaActual = obj;
    }

    public void guardarRecetaMedica() {
        try {
            if (recetaMedicaActual.getDosis() == null || recetaMedicaActual.getFechaInicio() == null
                    || recetaMedicaActual.getMedicamento() == null || recetaMedicaActual.getPeriodicidad() == null) {
                adminBean.notificaciones(-1, "Debe llenar todos los campos");
            } else {
                if (recetaMedicaActual.getDosis() <= 0 || recetaMedicaActual.getPeriodicidad() <= 0) {
                    adminBean.notificaciones(-1, "No se permiten valores negativos.");
                } else if (recetaMedicaActual.getDosis() < recetaMedicaActual.getPeriodicidad()) {
                    adminBean.notificaciones(-1, "La periodicidad no debe ser mayor a la dosis total.");
                } else {
                    if (recetaMedicaActual.getConsulta() == null) {
                        recetaMedicaActual.setConsulta(consultaMedicaActual);
                        consultaMedicaActual.getRecetaMedicaCollection().add(recetaMedicaActual);
                    }
                    adminBean.notificaciones(2, "La receta médica ");
                    preparaCrearRecetaMedica();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void borrarRecetaMedica(RecetaMedica obj) {
        try {
            consultaMedicaActual.getRecetaMedicaCollection().remove(obj);
            adminBean.notificaciones(3, "La receta médica ");
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
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
    //**************************************************************************
    // Booleanos
    //**************************************************************************
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

    public boolean isPaciente() {
        return paciente;
    }

    public void setPaciente(boolean paciente) {
        this.paciente = paciente;
    }

    public boolean isMedicoGeneral() {
        return medicoGeneral;
    }

    public void setMedicoGeneral(boolean medicoGeneral) {
        this.medicoGeneral = medicoGeneral;
    }

    public boolean isMedicoEspecialista() {
        return medicoEspecialista;
    }

    public void setMedicoEspecialista(boolean medicoEspecialista) {
        this.medicoEspecialista = medicoEspecialista;
    }

    public boolean isVisitante() {
        return visitante;
    }

    public void setVisitante(boolean visitante) {
        this.visitante = visitante;
    }

    public boolean getEditarPerfil() {
        return editarPerfil;
    }

    public void setEditarPerfil(boolean editarPerfil) {
        this.editarPerfil = editarPerfil;
    }

    public boolean getCambioContraseña() {
        return cambioContraseña;
    }

    public void setCambioContraseña(boolean cambioContraseña) {
        this.cambioContraseña = cambioContraseña;
    }

    //**************************************************************************
    // Strings
    //**************************************************************************
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

    public String getPerfilActual() {
        return perfilActual;
    }

    public void setPerfilActual(String perfilActual) {
        this.perfilActual = perfilActual;
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

    public String getIdentificacionPacienteConsultas() {
        return identificacionPacienteConsultas;
    }

    public void setIdentificacionPacienteConsultas(String identificacionPacienteConsultas) {
        this.identificacionPacienteConsultas = identificacionPacienteConsultas;
    }

    //**************************************************************************
    // Listas
    //**************************************************************************
    public String[] getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(String[] perfiles) {
        this.perfiles = perfiles;
    }

    public List<String> getLstPerfiles() {
        if (lstPerfiles == null) {
            lstPerfiles = new ArrayList<>();
        }
        return lstPerfiles;
    }

    public void setLstPerfiles(List<String> lstPerfiles) {
        this.lstPerfiles = lstPerfiles;
    }

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

    public List<Paciente> getLstPacientes() {
        buscarTodosPacientes();
        return lstPacientes;
    }

    public void setLstPacientes(List<Paciente> lstPacientes) {
        this.lstPacientes = lstPacientes;
    }

    public List<Ley> getLstLeyes() {
        buscarTodosLeyes();
        return lstLeyes;
    }

    public void setLstLeyes(List<Ley> lstLeyes) {
        this.lstLeyes = lstLeyes;
    }

    public List<Consulta> getLstConsultasPorMedico() {
        if (lstConsultasPorMedico == null) {
            lstConsultasPorMedico = new ArrayList<>();
        }
        return lstConsultasPorMedico;
    }

    public void setLstConsultasPorMedico(List<Consulta> lstConsultasPorMedico) {
        this.lstConsultasPorMedico = lstConsultasPorMedico;
    }

    public List<String> getDeportesConsulta() {
        if (deportesConsulta == null) {
            deportesConsulta = new ArrayList<>();
        }
        return deportesConsulta;
    }

    public void setDeportesConsulta(List<String> deportesConsulta) {
        this.deportesConsulta = deportesConsulta;
    }

    //**************************************************************************
    // Otros
    //**************************************************************************
    public Administrador getAdministrador() {
        administrador = crud.buscar(new Administrador("1"));
        System.out.println("Admin: " + administrador.getUsuario().getNombre());
        if (administrador.getRegistroCollection() == null) {
            administrador.setRegistroCollection(new ArrayList<Registro>());
        }
        if (administrador.getSolicitudQuejasReclamosCollection() == null) {
            administrador.setSolicitudQuejasReclamosCollection(new ArrayList<SolicitudQuejasReclamos>());
        }
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
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

    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    public void setPacienteActual(Paciente pacienteActual) {
        this.pacienteActual = pacienteActual;
    }

    public Medico getMedicoActual() {
        return medicoActual;
    }

    public void setMedicoActual(Medico medicoActual) {
        this.medicoActual = medicoActual;
    }

    public SolicitudQuejasReclamos getSolicitudActual() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudQuejasReclamos(new SolicitudQuejasReclamosPK());
        }
        return solicitudActual;
    }

    public void setSolicitudActual(SolicitudQuejasReclamos solicitudActual) {
        this.solicitudActual = solicitudActual;
    }

    public Consulta getConsultaMedicaActual() {
        if (consultaMedicaActual == null) {
            consultaMedicaActual = new Consulta(new ConsultaPK());
            consultaMedicaActual.setEnfermedadCollection(new ArrayList<Enfermedad>());
            consultaMedicaActual.setGrupoApoyoCollection(new ArrayList<GrupoApoyo>());
            consultaMedicaActual.setRecetaMedicaCollection(new ArrayList<RecetaMedica>());
            consultaMedicaActual.setMedico(medicoActual);
//            consultaMedicaActual.setPaciente(new Paciente());
        }
        return consultaMedicaActual;
    }

    public void setConsultaMedicaActual(Consulta consultaMedicaActual) {
        this.consultaMedicaActual = consultaMedicaActual;
    }

    public Paciente getPacienteConsulta() {
        if (pacienteConsulta == null) {
            pacienteConsulta = new Paciente();
            pacienteConsulta.setConsultaCollection(new ArrayList<Consulta>());
            pacienteConsulta.setTratamientoCollection(new ArrayList<Tratamiento>());
            pacienteConsulta.setUsuario(new Usuario());
        }
        return pacienteConsulta;
    }

    public void setPacienteConsulta(Paciente pacienteConsulta) {
        this.pacienteConsulta = pacienteConsulta;
    }

    public RecetaMedica getRecetaMedicaActual() {
        if (recetaMedicaActual == null) {
            recetaMedicaActual = new RecetaMedica(new RecetaMedicaPK());
        }
        return recetaMedicaActual;
    }

    public void setRecetaMedicaActual(RecetaMedica recetaMedicaActual) {
        this.recetaMedicaActual = recetaMedicaActual;
    }

    public CroppedImage getCroppeFoto() {
        return croppeFoto;
    }

    public void setCroppeFoto(CroppedImage croppeFoto) {
        this.croppeFoto = croppeFoto;
    }

    public String getImageTemp() {
        return imageTemp;
    }

    public void setImageTemp(String imageTemp) {
        this.imageTemp = imageTemp;
    }

}
