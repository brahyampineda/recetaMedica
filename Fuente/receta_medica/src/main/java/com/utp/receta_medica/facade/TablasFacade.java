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
            }else if (obj.getClass().equals(Usuario.class)) {
                save(obj);
            } else if (obj.getClass().equals(Tratamiento.class)) {
                Tratamiento aux = (Tratamiento) obj;
//                if (aux.getTratamientoPK().getIdTratamiento() == null) {
//                    aux.getTratamientoPK().setEnfermedadidEnfermedad1(1);
//                    generarConsecutivo(aux);
//                }
//                save(aux);
            } else if (obj.getClass().equals(Medicamento.class)) {
                Medicamento aux = (Medicamento) obj;
                if (aux.getIdMedicamento() == null) {
                    generarConsecutivo(aux);
                }
                save(aux);
            } else if (obj.getClass().equals(Registro.class)) {
                Registro aux = (Registro) obj;
                generarConsecutivo(aux);
                save(aux);
            }
            System.out.println("Guardado objeto de tipo " + obj.getClass().getSimpleName());
        } catch (Exception ex) {
            Logger.getLogger(TablasFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
