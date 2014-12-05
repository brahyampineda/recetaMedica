package com.utp.receta_medica.facade;

import com.utp.receta_medica.entidades.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JorgeRivera
 */
@Stateless
@LocalBean
public class TablasFacade extends Crud {

    @PersistenceContext(unitName = "com.utp_receta_medica_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //****************************
    // Métodos propios del facade
    //****************************
    /**
     * Busca un objeto en la base datos con base en su <code>id</code>
     *
     * @param <T> El tipo de objeto
     * @param obj. El objeto que se desea buscar
     * @return El objeto buscado o <code>null</code> en caso de que no existan
     * coincidencias.
     */
    public <T> T buscar(T obj) {
        try {
            System.out.println("Buscando objeto de la entidad " + obj.getClass().getSimpleName());
            return find(obj);
        } catch (Exception ex) {
            Logger.getLogger(TablasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busca todos las objetos que coincidan con unos parámetros específicos en
     * la base de datos.
     *
     * @param obj. El objeto inicial de búsqueda
     * @return Una lista con las coincidencias, o por el contrario la lista
     * vacía.
     */
    public <T> List<T> buscarTodos(T obj) {
        try {
            System.out.println("Buscando todos los objetos de la entidad " + obj.getClass().getSimpleName());
            return findAll(obj);
        } catch (Exception ex) {
            Logger.getLogger(TablasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Elimina el objeto <code>obj</code> de la base de datos
     *
     * @param <T> El tipo de objeto
     * @param obj El objeto a eliminar
     */
    public <T> void eliminar(T obj) {
        try {
            remove(obj);
            System.out.println("Eliminado objeto de la entidad " + obj.getClass().getSimpleName());
        } catch (Exception ex) {
            Logger.getLogger(TablasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Guarda y/o actualiza el objeto <code>obj</code> en la base de datos
     *
     * @param <T> El tipo de objeto
     * @param obj El objeto a guardar/actualizar
     */
    public <T> void guardar(T obj) {
        try {
            if (obj.getClass().equals(Administrador.class)) {
                Administrador aux = (Administrador) obj;
                save(aux);
            } else if (obj.getClass().equals(Usuario.class)) {
                save(obj);
            } else if (obj.getClass().equals(Enfermedad.class)) {
                Enfermedad aux = (Enfermedad) obj;
                if (aux.getIdEnfermedad() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Medicamento.class)) {
                Medicamento aux = (Medicamento) obj;
                if (aux.getIdMedicamento() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Tratamiento.class)) {
                Tratamiento aux = (Tratamiento) obj;
                aux.getTratamientoPK().setEnfermedadidEnfermedad1(aux.getEnfermedad().getIdEnfermedad());
                if (aux.getTratamientoPK().getIdTratamiento() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(GrupoApoyo.class)) {
                GrupoApoyo aux = (GrupoApoyo) obj;
                aux.getGrupoApoyoPK().setEnfermedadidEnfermedad(aux.getEnfermedad().getIdEnfermedad());
                aux.getGrupoApoyoPK().setEntidadnit(aux.getEntidad().getNit());
                if (aux.getGrupoApoyoPK().getIdGrupoapoyo() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Entidad.class)) {
                save(obj);
            } else if (obj.getClass().equals(Laboratorio.class)) {
                save(obj);
            } else if (obj.getClass().equals(Ley.class)) {
                Ley aux = (Ley) obj;
                Integer a = 1;

                for (Articulo articulo : aux.getArticuloCollection()) {
                    if (a == 1) {
                        if (articulo.getArticuloPK().getIdArticulo() == null) {
                            generarConsecutivo(articulo);
                            a = articulo.getArticuloPK().getIdArticulo() + 1;
                        }
                    } else {
                        articulo.getArticuloPK().setIdArticulo(a++);
                    }
                }
                save(aux);
            } else if (obj.getClass().equals(Articulo.class)) {
                Articulo aux = (Articulo) obj;
                if (aux.getArticuloPK().getIdArticulo() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Registro.class)) {
                Registro aux = (Registro) obj;
                if (aux.getIdRegistro() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Medico.class)) {
                Medico aux = (Medico) obj;
                save(aux);
            } else if (obj.getClass().equals(Paciente.class)) {
                save(obj);
            } else if (obj.getClass().equals(SolicitudQuejasReclamos.class)) {
                SolicitudQuejasReclamos aux = (SolicitudQuejasReclamos) obj;
                if (aux.getSolicitudQuejasReclamosPK().getIdSolicitudquejasreclamos() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Consulta.class)) {
                Consulta aux = (Consulta) obj;
                if (aux.getConsultaPK().getIdConsulta() == null) {
                    generarConsecutivo(aux);
                }
                
                Integer a = 1;
                for (RecetaMedica receta : aux.getRecetaMedicaCollection()) {
                    receta.setConsulta(aux);
                    if (receta.getRecetaMedicaPK() == null) {
                        receta.setRecetaMedicaPK(new RecetaMedicaPK());
                    }
                    receta.getRecetaMedicaPK().setConsultaPacienteidentificacion(aux.getConsultaPK().getPacienteidentificacion());
                    receta.getRecetaMedicaPK().setConsultaidConsulta(aux.getConsultaPK().getIdConsulta());
                    receta.getRecetaMedicaPK().setConsultaMedicoidentificacion(aux.getConsultaPK().getMedicoidentificacion());
                    receta.getRecetaMedicaPK().setMedicamentoidMedicamento(receta.getMedicamento().getIdMedicamento());
                    if (a == 1) {
                        if (receta.getRecetaMedicaPK().getIdRecetamedica() == null) {
                            generarConsecutivo(receta);
                            a = receta.getRecetaMedicaPK().getIdRecetamedica() + 1;
                        }
                    } else {
                        receta.getRecetaMedicaPK().setIdRecetamedica(a++);
                    }
                }
                aux.getMedico().getConsultaCollection().add(aux);
                aux.getPaciente().getConsultaCollection().add(aux);
                
                save(aux);
            }

            System.out.println("Guardado objeto de tipo " + obj.getClass().getSimpleName());
        } catch (Exception ex) {
            Logger.getLogger(TablasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
