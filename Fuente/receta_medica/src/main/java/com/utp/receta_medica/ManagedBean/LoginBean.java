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
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIOutput;
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
    private static BeanAdministrador adminBean = new BeanAdministrador();
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
    private boolean cambioContraseña;
    private String contraseñaActual;
    private String nuevaContraseña;
    private String verificacionNuevaContraseña;

    private List<String> lstPerfiles;
    private String perfilActual;

    ////////////////////////////////////////////////////////////////////////////
    //////////  Perfiles
    ////////////////////////////////////////////////////////////////////////////
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
        esEdicion = false;
        cambioContraseña = false;
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
            return "/perfilMedicoEspecialista";
        }
        if (perfilActual.equals("Médico general")) {
            paciente = false;
            medicoEspecialista = false;
            medicoGeneral = true;
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
                    adminBean.notificaciones(-1, "Bienvenido " + loggedUser.getNombre());
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

        System.out.println("\n\nCerrando sesión.\n\n");
        adminBean.notificaciones(-1, "Se ha cerrado la sesión correctamente");
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
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Getters y Setters
    ////////////////////////////////////////////////////////////////////////////
    public BeanAdministrador getAdminBean() {
        return adminBean;
    }

    public void setAdminBean(BeanAdministrador adminBean) {
        this.adminBean = adminBean;
    }

    public boolean isCambioContraseña() {
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
