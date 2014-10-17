/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utp.receta_medica.entidades;

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
@Table(name = "medico_general")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicoGeneral.findAll", query = "SELECT m FROM MedicoGeneral m"),
    @NamedQuery(name = "MedicoGeneral.findByIdMedicogeneral", query = "SELECT m FROM MedicoGeneral m WHERE m.medicoGeneralPK.idMedicogeneral = :idMedicogeneral"),
    @NamedQuery(name = "MedicoGeneral.findByUniversidad", query = "SELECT m FROM MedicoGeneral m WHERE m.universidad = :universidad"),
    @NamedQuery(name = "MedicoGeneral.findByAnosExperiencia", query = "SELECT m FROM MedicoGeneral m WHERE m.anosExperiencia = :anosExperiencia"),
    @NamedQuery(name = "MedicoGeneral.findByTarifa", query = "SELECT m FROM MedicoGeneral m WHERE m.tarifa = :tarifa"),
    @NamedQuery(name = "MedicoGeneral.findByUsuarioidUsuario", query = "SELECT m FROM MedicoGeneral m WHERE m.medicoGeneralPK.usuarioidUsuario = :usuarioidUsuario")})
public class MedicoGeneral implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedicoGeneralPK medicoGeneralPK;
    @Size(max = 45)
    @Column(name = "universidad")
    private String universidad;
    @Size(max = 2)
    @Column(name = "anos_experiencia")
    private String anosExperiencia;
    @Column(name = "tarifa")
    private Integer tarifa;
    @JoinTable(name = "medico_general_has_tratamiento", joinColumns = {
        @JoinColumn(name = "Medico_general_idMedico_general", referencedColumnName = "idMedico_general"),
        @JoinColumn(name = "Medico_general_Usuario_idUsuario", referencedColumnName = "Usuario_idUsuario")}, inverseJoinColumns = {
        @JoinColumn(name = "Tratamiento_idTratamiento", referencedColumnName = "idTratamiento")})
    @ManyToMany
    private Collection<Tratamiento> tratamientoCollection;
    @ManyToMany(mappedBy = "medicoGeneralCollection")
    private Collection<Medicamento> medicamentoCollection;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public MedicoGeneral() {
    }

    public MedicoGeneral(MedicoGeneralPK medicoGeneralPK) {
        this.medicoGeneralPK = medicoGeneralPK;
    }

    public MedicoGeneral(int idMedicogeneral, String usuarioidUsuario) {
        this.medicoGeneralPK = new MedicoGeneralPK(idMedicogeneral, usuarioidUsuario);
    }

    public MedicoGeneralPK getMedicoGeneralPK() {
        return medicoGeneralPK;
    }

    public void setMedicoGeneralPK(MedicoGeneralPK medicoGeneralPK) {
        this.medicoGeneralPK = medicoGeneralPK;
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

    @XmlTransient
    public Collection<Tratamiento> getTratamientoCollection() {
        return tratamientoCollection;
    }

    public void setTratamientoCollection(Collection<Tratamiento> tratamientoCollection) {
        this.tratamientoCollection = tratamientoCollection;
    }

    @XmlTransient
    public Collection<Medicamento> getMedicamentoCollection() {
        return medicamentoCollection;
    }

    public void setMedicamentoCollection(Collection<Medicamento> medicamentoCollection) {
        this.medicamentoCollection = medicamentoCollection;
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
        hash += (medicoGeneralPK != null ? medicoGeneralPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoGeneral)) {
            return false;
        }
        MedicoGeneral other = (MedicoGeneral) object;
        if ((this.medicoGeneralPK == null && other.medicoGeneralPK != null) || (this.medicoGeneralPK != null && !this.medicoGeneralPK.equals(other.medicoGeneralPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.MedicoGeneral[ medicoGeneralPK=" + medicoGeneralPK + " ]";
    }
    
}
