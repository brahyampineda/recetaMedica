/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "medico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medico.findAll", query = "SELECT m FROM Medico m"),
    @NamedQuery(name = "Medico.findByIdentificacion", query = "SELECT m FROM Medico m WHERE m.identificacion = :identificacion"),
    @NamedQuery(name = "Medico.findByUniversidad", query = "SELECT m FROM Medico m WHERE m.universidad = :universidad"),
    @NamedQuery(name = "Medico.findByAnosExperiencia", query = "SELECT m FROM Medico m WHERE m.anosExperiencia = :anosExperiencia"),
    @NamedQuery(name = "Medico.findByTarifa", query = "SELECT m FROM Medico m WHERE m.tarifa = :tarifa"),
    @NamedQuery(name = "Medico.findByEspecialidad", query = "SELECT m FROM Medico m WHERE m.especialidad = :especialidad"),
    @NamedQuery(name = "Medico.findByEsEspecialista", query = "SELECT m FROM Medico m WHERE m.esEspecialista = :esEspecialista")})
public class Medico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Size(max = 100)
    @Column(name = "universidad")
    private String universidad;
    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;
    @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tarifa")
    private Float tarifa;
    @Size(max = 45)
    @Column(name = "especialidad")
    private String especialidad;
    @Column(name = "es_especialista")
    private Boolean esEspecialista;
    @ManyToMany(mappedBy = "medicoCollection")
    private Collection<Medicamento> medicamentoCollection;
    @ManyToMany(mappedBy = "medicoCollection")
    private Collection<Entidad> entidadCollection;
    @JoinTable(name = "medico_has_tratamiento", joinColumns = {
        @JoinColumn(name = "Medico_identificacion", referencedColumnName = "identificacion")}, inverseJoinColumns = {
        @JoinColumn(name = "Tratamiento_idTratamiento", referencedColumnName = "idTratamiento"),
        @JoinColumn(name = "Tratamiento_Enfermedad_idEnfermedad1", referencedColumnName = "Enfermedad_idEnfermedad1")})
    @ManyToMany
    private Collection<Tratamiento> tratamientoCollection;
    @JoinColumn(name = "Usuario_email", referencedColumnName = "email")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(mappedBy = "medico")
    private Collection<Consulta> consultaCollection;

    public Medico() {
    }

    public Medico(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public Float getTarifa() {
        return tarifa;
    }

    public void setTarifa(Float tarifa) {
        this.tarifa = tarifa;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getEsEspecialista() {
        return esEspecialista;
    }

    public void setEsEspecialista(Boolean esEspecialista) {
        this.esEspecialista = esEspecialista;
    }

    @XmlTransient
    public Collection<Medicamento> getMedicamentoCollection() {
        return medicamentoCollection;
    }

    public void setMedicamentoCollection(Collection<Medicamento> medicamentoCollection) {
        this.medicamentoCollection = medicamentoCollection;
    }

    @XmlTransient
    public Collection<Entidad> getEntidadCollection() {
        return entidadCollection;
    }

    public void setEntidadCollection(Collection<Entidad> entidadCollection) {
        this.entidadCollection = entidadCollection;
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

    @XmlTransient
    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificacion != null ? identificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medico)) {
            return false;
        }
        Medico other = (Medico) object;
        if ((this.identificacion == null && other.identificacion != null) || (this.identificacion != null && !this.identificacion.equals(other.identificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Medico[ identificacion=" + identificacion + " ]";
    }
    
}
