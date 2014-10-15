/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JorgeRivera
 */
@Entity
@Table(name = "medico_especialista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicoEspecialista.findAll", query = "SELECT m FROM MedicoEspecialista m"),
    @NamedQuery(name = "MedicoEspecialista.findByIdMedicoespecialista", query = "SELECT m FROM MedicoEspecialista m WHERE m.medicoEspecialistaPK.idMedicoespecialista = :idMedicoespecialista"),
    @NamedQuery(name = "MedicoEspecialista.findByUniversidad", query = "SELECT m FROM MedicoEspecialista m WHERE m.universidad = :universidad"),
    @NamedQuery(name = "MedicoEspecialista.findByAnosExperiencia", query = "SELECT m FROM MedicoEspecialista m WHERE m.anosExperiencia = :anosExperiencia"),
    @NamedQuery(name = "MedicoEspecialista.findByTarifa", query = "SELECT m FROM MedicoEspecialista m WHERE m.tarifa = :tarifa"),
    @NamedQuery(name = "MedicoEspecialista.findByEspecialidad", query = "SELECT m FROM MedicoEspecialista m WHERE m.especialidad = :especialidad"),
    @NamedQuery(name = "MedicoEspecialista.findByUsuarioidUsuario", query = "SELECT m FROM MedicoEspecialista m WHERE m.medicoEspecialistaPK.usuarioidUsuario = :usuarioidUsuario")})
public class MedicoEspecialista implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicoEspecialistaPK medicoEspecialistaPK;
    @Size(max = 45)
    @Column(name = "universidad")
    private String universidad;
    @Size(max = 2)
    @Column(name = "anos_experiencia")
    private String anosExperiencia;
    @Column(name = "tarifa")
    private Integer tarifa;
    @Size(max = 45)
    @Column(name = "especialidad")
    private String especialidad;
    @ManyToMany(mappedBy = "medicoEspecialistaCollection")
    private Collection<Medicamento> medicamentoCollection;
    @JoinTable(name = "medico_especialista_has_tratamiento", joinColumns = {
        @JoinColumn(name = "Medico_especialista_idMedico_especialista", referencedColumnName = "idMedico_especialista")}, inverseJoinColumns = {
        @JoinColumn(name = "Tratamiento_idTratamiento", referencedColumnName = "idTratamiento")})
    @ManyToMany
    private Collection<Tratamiento> tratamientoCollection;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public MedicoEspecialista() {
    }

    public MedicoEspecialista(MedicoEspecialistaPK medicoEspecialistaPK) {
        this.medicoEspecialistaPK = medicoEspecialistaPK;
    }

    public MedicoEspecialista(int idMedicoespecialista, int usuarioidUsuario) {
        this.medicoEspecialistaPK = new MedicoEspecialistaPK(idMedicoespecialista, usuarioidUsuario);
    }

    public MedicoEspecialistaPK getMedicoEspecialistaPK() {
        return medicoEspecialistaPK;
    }

    public void setMedicoEspecialistaPK(MedicoEspecialistaPK medicoEspecialistaPK) {
        this.medicoEspecialistaPK = medicoEspecialistaPK;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(String anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public Integer getTarifa() {
        return tarifa;
    }

    public void setTarifa(Integer tarifa) {
        this.tarifa = tarifa;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @XmlTransient
    public Collection<Medicamento> getMedicamentoCollection() {
        return medicamentoCollection;
    }

    public void setMedicamentoCollection(Collection<Medicamento> medicamentoCollection) {
        this.medicamentoCollection = medicamentoCollection;
    }

    @XmlTransient
    public Collection<Tratamiento> getTratamientoCollection() {
        return tratamientoCollection;
    }

    public void setTratamientoCollection(Collection<Tratamiento> tratamientoCollection) {
        this.tratamientoCollection = tratamientoCollection;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicoEspecialistaPK != null ? medicoEspecialistaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoEspecialista)) {
            return false;
        }
        MedicoEspecialista other = (MedicoEspecialista) object;
        if ((this.medicoEspecialistaPK == null && other.medicoEspecialistaPK != null) || (this.medicoEspecialistaPK != null && !this.medicoEspecialistaPK.equals(other.medicoEspecialistaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.MedicoEspecialista[ medicoEspecialistaPK=" + medicoEspecialistaPK + " ]";
    }
    
}
