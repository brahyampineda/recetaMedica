package com.utp.receta_medica.ManagedBean;

import com.utp.receta_medica.entidades.*;
import com.utp.receta_medica.facade.TablasFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

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

    private static BeanGeneral beanGeneral = new BeanGeneral();
    private Administrador administrador;

    private Medicamento medicamentoActual;
    private Tratamiento tratamientoActual;
    private Medico medicoGeneralActual;
    private Medico medicoEspecialistaActual;
    private Ley leyActual;
    private Articulo articuloActual;
    private GrupoApoyo grupoApoyoActual;
    private Laboratorio laboratorioActual;
    private Entidad entidadActual;
    private Enfermedad enfermedadActual;

    private SolicitudQuejasReclamos solicitudActual;
    private List<Usuario> lstUsuarios;
    private List<Registro> lstRegistros;
    private List<SolicitudQuejasReclamos> lstSolicitudesQR;
    private String respuestaSolicitud;
    private Usuario usuarioFiltro;

    //***************************************
    // METODOS
    //***************************************
    public BeanAdministrador() {
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Perfil Administrador
    ////////////////////////////////////////////////////////////////////////////
    public void preparaAgregarContenido() {
        System.out.println("Preparando entidades.......");
        preparaCrearEnfermedad();
        preparaCrearEntidad();
        preparaCrearGrupoApoyo();
        preparaCrearLaboratorio();
        preparaCrearLey();
        preparaCrearMedicamento();
        preparaCrearTratamiento();
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Enfermedad
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearEnfermedad() {
        enfermedadActual = null;
        getEnfermedadActual();
    }

    public void preparaEditarEnfermedad(Enfermedad obj) {
        enfermedadActual = obj;
    }

    public void guardarEnfermedad() {
        try {
            crud.guardar(enfermedadActual);
            notificaciones(1, "La enfermedad " + enfermedadActual.getNombre());
            preparaCrearEnfermedad();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "La enfermedad " + enfermedadActual.getNombre());
        }
    }

    public void eliminarEnfermedad(Enfermedad obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "La enfermedad " + enfermedadActual.getNombre());
        } catch (Exception ex) {
            if ("PersistenceException".equals(ex.getCause().getCause().getClass().getSimpleName())) {
                notificaciones(-1, "La enfermedad no se puede borrar porque se está usando.");
            } else {
                notificaciones(0, "La enfermedad " + enfermedadActual.getNombre());
            }
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Medicamento
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearMedicamento() {
        medicamentoActual = null;
        getMedicamentoActual();
    }

    public void preparaEditarMedicamento(Medicamento obj) {
        medicamentoActual = (Medicamento) obj;
    }

    public void guardarMedicamento() {
        try {
            crud.guardar(medicamentoActual);
            notificaciones(1, "El medicamento " + medicamentoActual.getNombre());
            preparaCrearMedicamento();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "El medicamento " + medicamentoActual.getNombre());
        }
    }

    public void eliminarMedicamento(Medicamento obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El medicamento " + medicamentoActual.getNombre());
        } catch (Exception ex) {
            if ("PersistenceException".equals(ex.getCause().getCause().getClass().getSimpleName())) {
                notificaciones(-1, "El medicamento no se puede borrar porque se está usando.");
            } else {
                notificaciones(0, "El medicamento " + medicamentoActual.getNombre());
            }
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Tratamiento
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearTratamiento() {
        tratamientoActual = null;
        getTratamientoActual();
    }

    public void preparaEditarTratamiento(Tratamiento obj) {
        tratamientoActual = obj;
    }

    public void guardarTratamiento() {
        try {
            crud.guardar(tratamientoActual);
            notificaciones(1, "El tratamiento " + tratamientoActual.getNombre());
            preparaCrearTratamiento();
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            notificaciones(0, "El tratamiento " + tratamientoActual.getNombre());
        }
    }

    public void eliminarTratamiento(Tratamiento obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El tratamiento " + tratamientoActual.getNombre());
        } catch (Exception e) {
            if ("PersistenceException".equals(e.getCause().getCause().getClass().getSimpleName())) {
                notificaciones(-1, "El tratamiento no se puede borrar porque se está usando.");
            } else {
                notificaciones(0, "El tratamiento " + tratamientoActual.getNombre());
            }
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Grupo de apoyo
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearGrupoApoyo() {
        grupoApoyoActual = null;
        getGrupoApoyoActual();
    }

    public void preparaEditarGrupoApoyo(GrupoApoyo obj) {
        grupoApoyoActual = obj;
    }

    public void guardarGrupoApoyo() {
        try {
            //Organizar
            crud.guardar(grupoApoyoActual);
            notificaciones(1, "El grupo de apoyo " + grupoApoyoActual.getNombre());
            preparaCrearGrupoApoyo();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "El grupo de apoyo " + grupoApoyoActual.getNombre());
        }
    }

    public void eliminarGrupoApoyo(GrupoApoyo obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El grupo de apoyo " + grupoApoyoActual.getNombre());
        } catch (Exception e) {
            if ("PersistenceException".equals(e.getCause().getCause().getClass().getSimpleName())) {
                notificaciones(-1, "El grupo de apoyo no se puede borrar porque se está usando.");
            } else {
                notificaciones(0, "El grupo de apoyo " + grupoApoyoActual.getNombre());
            }
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Entidad
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearEntidad() {
        entidadActual = null;
        getEntidadActual();
    }

    public void preparaEditarEntidad(Entidad obj) {
        entidadActual = obj;
    }

    public void guardarEntidad() {
        try {
            crud.guardar(entidadActual);
            notificaciones(1, "La entidad " + entidadActual.getNombre());
            preparaCrearEntidad();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "La entidad " + entidadActual.getNombre());
        }
    }

    public void eliminarEntidad(Entidad obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "La entidad " + entidadActual.getNombre());
        } catch (Exception e) {
            if ("PersistenceException".equals(e.getCause().getCause().getClass().getSimpleName())) {
                notificaciones(-1, "La entidad no se puede borrar porque se está usando.");
            } else {
                notificaciones(0, "La entidad " + entidadActual.getNombre());
            }
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Laboratorio
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearLaboratorio() {
        laboratorioActual = null;
        getLaboratorioActual();
    }

    public void preparaEditarLaboratorio(Laboratorio obj) {
        laboratorioActual = obj;
    }

    public void guardarLaboratorio() {
        try {
            crud.guardar(laboratorioActual);
            notificaciones(1, "El laboratorio " + laboratorioActual.getNombre());
            preparaCrearLaboratorio();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "El laboratorio " + laboratorioActual.getNombre());
        }
    }

    public void eliminarLaboratorio(Laboratorio obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El laboratorio " + laboratorioActual.getNombre());
        } catch (Exception e) {
            if ("PersistenceException".equals(e.getCause().getCause().getClass().getSimpleName())) {
                notificaciones(-1, "El laboratorio no se puede borrar porque se está usando.");
            } else {
                notificaciones(0, "El laboratorio " + laboratorioActual.getNombre());
            }
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Ley y artículo
    ////////////////////////////////////////////////////////////////////////////
    public void preparaCrearLey() {
        leyActual = null;
        getLeyActual();
    }

    public void preparaEditarLey(Ley obj) {
        leyActual = obj;
    }

    public void guardarLey() {
        try {
            crud.guardar(leyActual);
            notificaciones(1, "La ley " + leyActual.getNombre());
            preparaCrearLey();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "La ley " + leyActual.getNombre());
        }
    }

    public void eliminarLey(Ley obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "La ley " + obj.getNombre());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "La ley " + obj.getNombre());
        }
    }

    public void preparaCrearArticulo() {
        articuloActual = null;
        getArticuloActual();
    }

    public void preparaEditarArticulo(Articulo obj) {
        articuloActual = obj;
    }

    public void agregarArticulo() {
        try {
            if (articuloActual.getDescripcion().equals("") || articuloActual.getNombre().equals("")) {
                notificaciones(-1, "El nombre y la descripción son necesarias");
            } else {
                if (articuloActual.getLey() != null) {
                    notificaciones(1, "El artículo " + articuloActual.getNombre() + " ha sido modificado correctamente");
                } else {
                    articuloActual.getArticuloPK().setLeycodigo(leyActual.getCodigo());
                    articuloActual.setLey(leyActual);
                    leyActual.getArticuloCollection().add(articuloActual);
                    notificaciones(1, "El artículo " + articuloActual.getNombre() + " ha sido agregado correctamente");
                }
                preparaCrearArticulo();
            }
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, null);
        }
    }

    public void eliminarArticulo(Articulo obj) {
        try {
            leyActual.getArticuloCollection().remove(obj);
            notificaciones(3, "El artículo " + obj.getNombre());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, "El artículo " + obj.getNombre());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Usuario
    ////////////////////////////////////////////////////////////////////////////
    public void eliminarUsuario(SolicitudQuejasReclamos sol, boolean confirmar) {
        try {
            if (confirmar) {
                Usuario user = sol.getUsuario();
                String nombre = user.getNombre() + " " + user.getApellidos();
                crud.eliminar(user);
                notificaciones(3, "El usuario " + nombre);
                lstUsuarios = new ArrayList<>();
            } else {
                sol.setEstado("Resuelta");
                crud.guardar(sol);
            }
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preparaConsultaUsuario() {
        usuarioFiltro = new Usuario();
    }

    public void buscarUsuario() {
        try {
            if ("".equals(usuarioFiltro.getIdentificacion())) {
                usuarioFiltro.setIdentificacion(null);
            }
            lstUsuarios = crud.buscarTodos(usuarioFiltro);
            preparaConsultaUsuario();
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Médico general y Especialista
    ////////////////////////////////////////////////////////////////////////////
    public void eliminarMedicoGeneral(Medico obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El perfil Médico general del usuario " + obj.getUsuario().getNombre());
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarMedicoEspecialista(Medico obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El perfil Médico especialista del usuario " + obj.getUsuario().getNombre());
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarPaciente(Paciente obj) {
        try {
            crud.eliminar(obj);
            notificaciones(3, "El perfil paciente del usuario " + obj.getUsuario().getNombre());
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Solicitudes
    ////////////////////////////////////////////////////////////////////////////
    public void aceptarSolicitudRegistro(Registro registro) {
        try {
            boolean auxMedicoGen = false;
            registro.setEstado("Aceptada");
            crud.guardar(registro);

            if (registro.getPerfil().contains("Paciente")) {
                Paciente nuevoPaciente = new Paciente(registro.getUsuario().getIdentificacion());
                nuevoPaciente.setUsuario(registro.getUsuario());
                registro.getUsuario().getPacienteCollection().add(nuevoPaciente);
            }
            if (registro.getPerfil().contains("Medico general")) {
                Medico nuevoMedicoG = new Medico(registro.getUsuario().getIdentificacion());
                nuevoMedicoG.setEsEspecialista(false);
                nuevoMedicoG.setUsuario(registro.getUsuario());
                registro.getUsuario().getMedicoCollection().add(nuevoMedicoG);
                auxMedicoGen = true;
            }
            if (registro.getPerfil().contains("Medico especialista")) {
                Medico nuevoMedicoE = new Medico(registro.getUsuario().getIdentificacion());
                if (auxMedicoGen) {
                    nuevoMedicoE.setIdentificacion(nuevoMedicoE.getIdentificacion() + "00");
                }
                nuevoMedicoE.setEsEspecialista(true);
                nuevoMedicoE.setUsuario(registro.getUsuario());
                registro.getUsuario().getMedicoCollection().add(nuevoMedicoE);
                auxMedicoGen = false;
            }
            registro.getUsuario().setContrasena("RecetaMedica123");
            registro.getUsuario().setFoto("/resources/imagenes/imagenes de perfil/sinImagen.png");
            crud.guardar(registro.getUsuario());
            beanGeneral.enviarCorreo(getAdministrador().getUsuario().getEmail(), registro.getUsuario().getEmail(),
                    "Solicitud de registro aceptada", "Señ@r " + registro.getUsuario().getNombre() + ",\n\n"
                    + "Le informamos que su solicitud ha sido aceptada con los perfiles " + registro.getPerfil()
                    + ". Su nueva contraseña es: " + registro.getUsuario().getContrasena()
                    + ".\nInicie sesión para cambiar la contraseña y terminar de rellenar los datos de su perfil."
                    + "\n\nAtt: Adminitrador");
            notificaciones(4, null);
            crud.eliminar(registro);

        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void rechazarSolicitudRegistro(Registro registro) {
        try {
            String email = registro.getUsuario().getEmail();
            String nombreUsuario = registro.getUsuario().getNombre();
            Usuario aux = registro.getUsuario();
            crud.eliminar(registro);
            crud.eliminar(aux);
            beanGeneral.enviarCorreo(getAdministrador().getUsuario().getEmail(), email,
                    "Solicitud de registro rechazada", "Señ@r " + nombreUsuario + ",\n\n"
                    + "Lamentamos informarle que su solicitud de registro ha sido rechazada."
                    + "\n\nAtt: Adminitrador");
            notificaciones(4, null);
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
//            notificaciones(0, null);
//            notificaciones(5, null);
        }
    }

    public void preparaResponderSolicitud(SolicitudQuejasReclamos obj) {
        solicitudActual = obj;
        respuestaSolicitud = "";
    }

    public void responderSolicitudGeneral() {
        try {
            respuestaSolicitud = "";
            solicitudActual.setEstado("Resuelta");
            crud.guardar(solicitudActual);
            solicitudActual = new SolicitudQuejasReclamos(new SolicitudQuejasReclamosPK());
            getBeanGeneral().enviarCorreo(getAdministrador().getUsuario().getEmail(), solicitudActual.getUsuario().getEmail(),
                    "Respuesta a la solicitud sobre: " + solicitudActual.getTitulo(), "Señ@r " + solicitudActual.getUsuario().getNombre() + ",\n\n"
                    + respuestaSolicitud);
            notificaciones(4, null);
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
            notificaciones(0, null);
            notificaciones(5, null);
        }
    }

    public Boolean desactivarBotones(String estado) {
        return estado.equalsIgnoreCase("En espera");
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////  Otros métodos
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Genera el mensaje que será notificado al usuario en la vista.
     *
     * @param codigoMensaje El código del mensaje a borrar:<br/>
     * -1. Escribir el mensaje exacto que quiere visualizar <br/>
     * 0. Se produjo un error <br/>
     * 1. Objeto <code>tipoObjeto</code> guardado <br/>
     * 2. Objeto <code>tipoObjeto</code> editado <br/>
     * 3. Objeto <code>tipoObjeto</code> eliminado <br/>
     * 4. Mensaje enviado<br/>
     * 5. Mensaje no enviado<br/>
     * 6. Perfil guardado<br/>
     * @param tipoObjeto El nombre del objeto
     */
    public void notificaciones(int codigoMensaje, String tipoObjeto) {
        String mensaje = "";

        switch (codigoMensaje) {
            case -1: // Mensaje Libre
                mensaje = tipoObjeto;
                break;

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

            case 6: // Perfil guardado
                mensaje = "El perfil se ha guardado correctamente";
                break;

            default:
                break;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("", mensaje));
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////// Getters y Setters
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
//------------------------------------------------------------------------------
//-------------------- Getters
//------------------------------------------------------------------------------
    public TablasFacade getCrud() {
        return crud;
    }

    public static BeanGeneral getBeanGeneral() {
        if (beanGeneral == null) {
            beanGeneral = new BeanGeneral();
        }
        return beanGeneral;
    }

    public Administrador getAdministrador() {
        administrador = crud.buscar(new Administrador("1"));
        if (administrador.getRegistroCollection() == null) {
            administrador.setRegistroCollection(new ArrayList<Registro>());
        }
        if (administrador.getSolicitudQuejasReclamosCollection() == null) {
            administrador.setSolicitudQuejasReclamosCollection(new ArrayList<SolicitudQuejasReclamos>());
        }
        return administrador;
    }

//....... Listas ...............................................................
    public List<SolicitudQuejasReclamos> getLstSolicitudesQR() {
        try {
            lstSolicitudesQR = crud.buscarTodos(new SolicitudQuejasReclamos());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
        return lstSolicitudesQR;
    }

    public List<Usuario> getLstUsuarios() {
        if (lstUsuarios == null) {
            lstUsuarios = new ArrayList<>();
        }
        return lstUsuarios;
    }

    public List<Registro> getLstRegistros() {
        try {
            lstRegistros = crud.buscarTodos(new Registro());
        } catch (Exception e) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, e);
        }
        return lstRegistros;
    }

//..............................................................................
    public Medicamento getMedicamentoActual() {
        if (medicamentoActual == null) {
            medicamentoActual = new Medicamento();
//            medicamentoActual.setLaboratorioCollection(new ArrayList<Laboratorio>());
//            medicamentoActual.setMedicoCollection(new ArrayList<Medico>());
//            medicamentoActual.setRecetaMedicaCollection(new ArrayList<RecetaMedica>());
        }
        return medicamentoActual;
    }

    public Tratamiento getTratamientoActual() {
        if (tratamientoActual == null) {
            tratamientoActual = new Tratamiento(new TratamientoPK());
//            tratamientoActual.setEnfermedad(new Enfermedad());
//            tratamientoActual.setMedicoCollection(new ArrayList<Medico>());
//            tratamientoActual.setPacienteCollection(new ArrayList<Paciente>());
        }
        return tratamientoActual;
    }

    public Medico getMedicoGeneralActual() {
        if (medicoGeneralActual == null) {
            medicoGeneralActual = new Medico();
            medicoGeneralActual.setEsEspecialista(false);
//            medicoGeneralActual.setConsultaCollection(new ArrayList<Consulta>());
//            medicoGeneralActual.setEntidadCollection(new ArrayList<Entidad>());
//            medicoGeneralActual.setMedicamentoCollection(new ArrayList<Medicamento>());
//            medicoGeneralActual.setTratamientoCollection(new ArrayList<Tratamiento>());
//            medicoGeneralActual.setUsuario(new Usuario());
        }
        return medicoGeneralActual;
    }

    public Medico getMedicoEspecialistaActual() {
        if (medicoEspecialistaActual == null) {
            medicoEspecialistaActual = new Medico();
            medicoEspecialistaActual.setEsEspecialista(false);
//            medicoEspecialistaActual.setConsultaCollection(new ArrayList<Consulta>());
//            medicoEspecialistaActual.setEntidadCollection(new ArrayList<Entidad>());
//            medicoEspecialistaActual.setMedicamentoCollection(new ArrayList<Medicamento>());
//            medicoEspecialistaActual.setTratamientoCollection(new ArrayList<Tratamiento>());
//            medicoEspecialstaActual.setUsuario(new Usuario());
        }
        return medicoEspecialistaActual;
    }

    public Ley getLeyActual() {
        if (leyActual == null) {
            leyActual = new Ley();
            leyActual.setArticuloCollection(new ArrayList<Articulo>());
        }
        return leyActual;
    }

    public Articulo getArticuloActual() {
        if (articuloActual == null) {
            articuloActual = new Articulo(new ArticuloPK());
        }
        return articuloActual;
    }

    public GrupoApoyo getGrupoApoyoActual() {
        if (grupoApoyoActual == null) {
            grupoApoyoActual = new GrupoApoyo(new GrupoApoyoPK());
            grupoApoyoActual.setConsultaCollection(new ArrayList<Consulta>());
            grupoApoyoActual.setEnfermedad(new Enfermedad());
            grupoApoyoActual.setEntidad(new Entidad());
        }
        return grupoApoyoActual;
    }

    public Laboratorio getLaboratorioActual() {
        if (laboratorioActual == null) {
            laboratorioActual = new Laboratorio();
            laboratorioActual.setMedicamentoCollection(new ArrayList<Medicamento>());
        }
        return laboratorioActual;
    }

    public Entidad getEntidadActual() {
        if (entidadActual == null) {
            entidadActual = new Entidad();
            entidadActual.setGrupoApoyoCollection(new ArrayList<GrupoApoyo>());
            entidadActual.setMedicoCollection(new ArrayList<Medico>());
        }
        return entidadActual;
    }

    public Enfermedad getEnfermedadActual() {
        if (enfermedadActual == null) {
            enfermedadActual = new Enfermedad();
            enfermedadActual.setGrupoApoyoCollection(new ArrayList<GrupoApoyo>());
            enfermedadActual.setTratamientoCollection(new ArrayList<Tratamiento>());
            enfermedadActual.setConsultaCollection(new ArrayList<Consulta>());
        }
        return enfermedadActual;
    }

    public SolicitudQuejasReclamos getSolicitudActual() {
        if (solicitudActual == null) {
            solicitudActual = new SolicitudQuejasReclamos(new SolicitudQuejasReclamosPK());
            solicitudActual.setAdministrador(administrador);
//            solicitudActual.setUsuario(new Usuario());
        }
        return solicitudActual;
    }

    public String getRespuestaSolicitud() {
        return respuestaSolicitud;
    }

    public Usuario getUsuarioFiltro() {
        if (usuarioFiltro == null) {
            usuarioFiltro = new Usuario();
        }
        return usuarioFiltro;
    }

//------------------------------------------------------------------------------
//-------------------- Setters
//------------------------------------------------------------------------------
    public void setCrud(TablasFacade crud) {
        this.crud = crud;
    }

    public static void setBeanGeneral(BeanGeneral beanGeneral) {
        BeanAdministrador.beanGeneral = beanGeneral;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
//....... Listas ...............................................................

    public void setLstSolicitudesQR(List<SolicitudQuejasReclamos> lstSolicitudesQR) {
        this.lstSolicitudesQR = lstSolicitudesQR;
    }

    public void setLstUsuarios(List<Usuario> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }

    public void setLstRegistros(List<Registro> lstRegistros) {
        this.lstRegistros = lstRegistros;
    }

//..............................................................................
    public void setMedicamentoActual(Medicamento medicamentoActual) {
        this.medicamentoActual = medicamentoActual;
    }

    public void setTratamientoActual(Tratamiento tratamientoActual) {
        this.tratamientoActual = tratamientoActual;
    }

    public void setMedicoGeneralActual(Medico medicoGeneralActual) {
        this.medicoGeneralActual = medicoGeneralActual;
    }

    public void setMedicoEspecialistaActual(Medico medicoEspecialistaActual) {
        this.medicoEspecialistaActual = medicoEspecialistaActual;
    }

    public void setLeyActual(Ley leyActual) {
        this.leyActual = leyActual;
    }

    public void setArticuloActual(Articulo articuloActual) {
        this.articuloActual = articuloActual;
    }

    public void setGrupoApoyoActual(GrupoApoyo grupoApoyoActual) {
        this.grupoApoyoActual = grupoApoyoActual;
    }

    public void setLaboratorioActual(Laboratorio laboratorioActual) {
        this.laboratorioActual = laboratorioActual;
    }

    public void setEntidadActual(Entidad entidadActual) {
        this.entidadActual = entidadActual;
    }

    public void setEnfermedadActual(Enfermedad enfermedadActual) {
        this.enfermedadActual = enfermedadActual;
    }

    public void setSolicitudActual(SolicitudQuejasReclamos solicitudActual) {
        this.solicitudActual = solicitudActual;
    }

    public void setRespuestaSolicitud(String respuestaSolicitud) {
        this.respuestaSolicitud = respuestaSolicitud;
    }

    public void setUsuarioFiltro(Usuario usuarioFiltro) {
        this.usuarioFiltro = usuarioFiltro;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////// Converters
    ////////////////////////////////////////////////////////////////////////////
    public Enfermedad getEnfermedad(Enfermedad id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "EnfermedadConverter")
    public static class EnfermedadConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getEnfermedad(new Enfermedad(getKey(value)));
        }

        Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Enfermedad) {
                Enfermedad o = (Enfermedad) object;
                return getStringKey(o.getIdEnfermedad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Enfermedad.class.getName()});
                return null;
            }
        }

    }

    //==========================================================================
    public Laboratorio getLaboratorio(Laboratorio id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "LaboratorioConverter")
    public static class LaboratorioConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getLaboratorio(new Laboratorio(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Laboratorio) {
                Laboratorio o = (Laboratorio) object;
                return o.getNit();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Laboratorio.class.getName()});
                return null;
            }
        }
    }

    //==========================================================================
    public Entidad getEntidad(Entidad id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "EntidadConverter")
    public static class EntidadConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getEntidad(new Entidad(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Entidad) {
                Entidad o = (Entidad) object;
                return o.getNit();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Entidad.class.getName()});
                return null;
            }
        }

    }

    //==========================================================================
    public GrupoApoyo getGrupoApoyo(GrupoApoyo id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "GrupoApoyoConverter", forClass = GrupoApoyo.class)
    public static class GrupoApoyoConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getGrupoApoyo(new GrupoApoyo(getKey(value)));
        }

        GrupoApoyoPK getKey(String value) {
            GrupoApoyoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new GrupoApoyoPK();
            key.setIdGrupoapoyo(Integer.parseInt(values[0]));
            key.setEntidadnit(values[1]);
            key.setEnfermedadidEnfermedad(Integer.parseInt(values[2]));
            return key;
        }

        String getStringKey(GrupoApoyoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdGrupoapoyo());
            sb.append(SEPARATOR);
            sb.append(value.getEntidadnit());
            sb.append(SEPARATOR);
            sb.append(value.getEnfermedadidEnfermedad());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GrupoApoyo) {
                GrupoApoyo o = (GrupoApoyo) object;
                return getStringKey(o.getGrupoApoyoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), GrupoApoyo.class.getName()});
                return null;
            }
        }

    }

    //==========================================================================
    public Medico getMedico(Medico id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "MedicoConverter", forClass = Medico.class)
    public static class MedicoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getMedico(new Medico(getKey(value)));
        }

        String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Medico) {
                Medico o = (Medico) object;
                return getStringKey(o.getIdentificacion());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Medico.class.getName()});
                return null;
            }
        }

    }

    //==========================================================================
    public Medicamento getMedicamento(Medicamento id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "MedicamentoConverter", forClass = Medicamento.class)
    public static class MedicamentoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getMedicamento(new Medicamento(getKey(value)));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Medicamento) {
                Medicamento o = (Medicamento) object;
                return getStringKey(o.getIdMedicamento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Medicamento.class.getName()});
                return null;
            }
        }

    }

    //==========================================================================
    public Paciente getPaciente(Paciente id) {
        try {
            return getCrud().find(id);
        } catch (Exception ex) {
            Logger.getLogger(BeanAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FacesConverter(value = "PacienteConverter", forClass = Paciente.class)
    public static class PacienteConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeanAdministrador controller = (BeanAdministrador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beanAdministrador");
            return controller.getPaciente(new Paciente(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Paciente) {
                Paciente o = (Paciente) object;
                return o.getIdentificacion();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Paciente.class.getName()});
                return null;
            }
        }

    }
}
