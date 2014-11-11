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
import javax.faces.context.FacesContext;
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

    //SegUsuario loggedUser;
    Usuario loggedUser;

    private boolean loggedIn;
    private boolean admin;
    private boolean paciente;
    private boolean medicoGeneral;
    private boolean medicoEspecialista;
    private boolean visitante = true;

    private String username;
    private String password;

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

    public String doLogin() {
        System.out.println("USERNAME " + username);
        System.out.println("PASSWORD " + password);

        Usuario usuario = new Usuario();
        usuario.setIdentificacion(username);
//        usuario.setAdministradorCollection(new ArrayList<Administrador>());
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

        System.out.println("USUARIO " + usuario);

//        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        System.out.println("YA CASIIIIIIIIII");

//        if (passwordEncryptor.checkPassword(password, usuario.getContrasena())) {
        if (usuario.getContrasena() != null) {
            if (usuario.getContrasena().equals(password)) {
                loggedIn = true;
                loggedUser = usuario;

                admin = false;
                paciente = false;
                medicoGeneral = false;
                medicoEspecialista = false;
                visitante = false;

                if (!usuario.getAdministradorCollection().isEmpty()) {
                    admin = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inicio de sesión", "Bienvenido " + loggedUser.getNombre()));
                    return "/perfilAdmin";
                } else {
                    admin = false;
                    if (!usuario.getPacienteCollection().isEmpty()) {
                        paciente = true;
                    }
                    if (!usuario.getMedicoCollection().isEmpty()) {
                        for (Medico item : usuario.getMedicoCollection()) {
                            System.out.println("asdfeshgrodfsog");
                            if (item.getEsEspecialista()) {
                                medicoEspecialista = true;
                            } else {
                                medicoGeneral = true;
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
//            
//            BeanAdministrador beanAdministrador = new BeanAdministrador();
//            return beanAdministrador.prepararAdministracion();
            } else {
                FacesMessage msg = new FacesMessage("Error al entrar", "Contraseña incorrecta");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
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

        FacesMessage msg = new FacesMessage("Sesión finalizada", "Se ha cerrado la sesión correctamente");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return "/index";
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
