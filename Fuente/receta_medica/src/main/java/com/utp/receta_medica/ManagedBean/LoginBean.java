package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.Medico;
import com.utp.receta_medica.entidades.Usuario;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

/**
 *
 * @author JorgeRivera
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private TablasFacade crud;

    @ManagedProperty(value = "#{loginService}")
    private static BeanAdministrador adminBean;
    Usuario loggedUser;

    private boolean loggedIn;
    private boolean admin;
    private boolean paciente;
    private boolean medicoGeneral;
    private boolean medicoEspecialista;
    private boolean visitante = true;
    private boolean esEdicion;

    private String username;
    private String password;

    // Para el cambio de contraseña
    private String contraseñaActual;
    private String nuevaContraseña;
    private String verificacionNuevaContraseña;

    private List<String> lstPerfiles;
    private String perfilActual;

    public void preparaInicioDeSesion() {
        username = "";
        password = "";
        loggedIn = false;
        admin = false;
        paciente = false;
        medicoGeneral = false;
        medicoEspecialista = false;
        visitante = true;
        loggedUser = new Usuario();
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
        if (perfilActual.equals("Paciente")) {
            return "/perfilPaciente";
        }
        if (perfilActual.equals("Médico especialista")) {
            return "/perfilMedicoEspecialista";
        }
        if (perfilActual.equals("Médico general")) {
            return "/perfilMedicoGeneral";
        }
        return null;
    }
    
    public void cambioPerfil(AjaxBehaviorEvent event){
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error al entrar", "El usuario no está registrado"));
                return null;
            } else {
                usuario = usuarios.get(0);
            }
        } catch (Exception ex) {
            usuario = null;
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
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
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inicio de sesión", "Bienvenido " + loggedUser.getNombre()));
                    perfilActual = "Administrador";
                    return "/perfilAdmin";
                } else {
                    admin = false;
                    if (!usuario.getPacienteCollection().isEmpty()) {
                        System.out.println("Es Pacienteeeeeeee");
                        paciente = true;
                    }
                    if (!usuario.getMedicoCollection().isEmpty()) {
                        for (Medico item : usuario.getMedicoCollection()) {
                            System.out.println("asdfeshgrodfsog");
                            if (item.getEsEspecialista()) {
                                medicoEspecialista = true;
                                System.out.println("Es Medico Especialista");
                            } else {
                                medicoGeneral = true;
                                System.out.println("Es Medico General");
                            }
                        }
                    }
                }
                preparaListaPerfiles();
                if (paciente) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inicio de sesión", "Bienvenido " + loggedUser.getNombre()));
                    perfilActual = "Paciente";
                    return "/perfilPaciente";
                }
                if (medicoEspecialista) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inicio de sesión", "Bienvenido " + loggedUser.getNombre()));
                    perfilActual = "Médico especialista";
                    return "/perfilMedicoEspecialista";
                }
                if (medicoGeneral) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inicio de sesión", "Bienvenido " + loggedUser.getNombre()));
                    perfilActual = "Médico general";
                    return "/perfilMedicoGeneral";
                }
            } else {
                FacesMessage msg = new FacesMessage("Error al entrar", "Contraseña incorrecta");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
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
        FacesMessage msg = new FacesMessage("Sesión finalizada", "Se ha cerrado la sesión correctamente");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return "/index";
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Perfil de Usuario
    ////////////////////////////////////////////////////////////////////////////
    public void preparaEditarPerfil() {
        esEdicion = true;
        contraseñaActual = "";
        nuevaContraseña = "";
        verificacionNuevaContraseña = "";
    }

    public void guardarPerfil() {
        try {
            if (verificarCambioContraseña()) {
                crud.guardar(loggedUser);
                esEdicion = false;
                contraseñaActual = "";
                nuevaContraseña = "";
                verificacionNuevaContraseña = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil guardado", "El perfil se ha guardado exitosamente"));
            }
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "El perfil no se ha podido guardar exitosamente."));
        }
    }

    private boolean verificarCambioContraseña() {
        if ("".equals(contraseñaActual)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Debe escribir la contraseña para guardar el perfil"));
            return false;
        } else if (contraseñaActual.equals(loggedUser.getContrasena())) {
            if (nuevaContraseña.equals(verificacionNuevaContraseña)) {
                if (!nuevaContraseña.equals("")) {
                    loggedUser.setContrasena(nuevaContraseña);
                }
                return true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "La confirmación de la nueva contraseña no coincide."));
                return false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "La contraseña no es correcta."));
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Getters y Setters
    ////////////////////////////////////////////////////////////////////////////
    public static BeanAdministrador getAdminBean() {
        if (adminBean == null) {
            adminBean = new BeanAdministrador();
        }
        return adminBean;
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

    public static void setAdminBean(BeanAdministrador adminBean) {
        LoginBean.adminBean = adminBean;
    }

    public boolean isEsEdicion() {
        return esEdicion;
    }

    public void setEsEdicion(boolean esEdicion) {
        this.esEdicion = esEdicion;
    }

    public boolean getPaciente() {
        return paciente;
    }

    public void setPaciente(boolean paciente) {
        this.paciente = paciente;
    }

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

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public TablasFacade getCrud() {
        return crud;
    }

    public void setCrud(TablasFacade crud) {
        this.crud = crud;
    }

    public boolean getMedicoGeneral() {
        return medicoGeneral;
    }

    public void setMedicoGeneral(boolean medicoGeneral) {
        this.medicoGeneral = medicoGeneral;
    }

    public boolean getMedicoEspecialista() {
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

    public List<String> getLstPerfiles() {
        if (lstPerfiles == null) {
            lstPerfiles = new ArrayList<>();
        }
        return lstPerfiles;
    }

    public void setLstPerfiles(List<String> lstPerfiles) {
        this.lstPerfiles = lstPerfiles;
    }

    public String getPerfilActual() {
        return perfilActual;
    }

    public void setPerfilActual(String perfilActual) {
        this.perfilActual = perfilActual;
    }

}
