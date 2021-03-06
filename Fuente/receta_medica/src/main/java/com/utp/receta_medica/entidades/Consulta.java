/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "consulta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consulta.findAll", query = "SELECT c FROM Consulta c"),
    @NamedQuery(name = "Consulta.findByIdConsulta", query = "SELECT c FROM Consulta c WHERE c.consultaPK.idConsulta = :idConsulta"),
    @NamedQuery(name = "Consulta.findByPeso", query = "SELECT c FROM Consulta c WHERE c.peso = :peso"),
    @NamedQuery(name = "Consulta.findByAltura", query = "SELECT c FROM Consulta c WHERE c.altura = :altura"),
    @NamedQuery(name = "Consulta.findByDeportes", query = "SELECT c FROM Consulta c WHERE c.deportes = :deportes"),
    @NamedQuery(name = "Consulta.findByMedicoidentificacion", query = "SELECT c FROM Consulta c WHERE c.consultaPK.medicoidentificacion = :medicoidentificacion"),
    @NamedQuery(name = "Consulta.findByPacienteidentificacion", query = "SELECT c FROM Consulta c WHERE c.consultaPK.pacienteidentificacion = :pacienteidentificacion")})
public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConsultaPK consultaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso")
    private Float peso;
    @Column(name = "altura")
    private Integer altura;
    @Size(max = 200)
    @Column(name = "deportes")
    private String deportes;
    @JoinTable(name = "consulta_has_enfermedad", joinColumns = {
        @JoinColumn(name = "Consulta_idConsulta", referencedColumnName = "idConsulta"),
        @JoinColumn(name = "Consulta_Medico_identificacion", referencedColumnName = "Medico_identificacion"),
        @JoinColumn(name = "Consulta_Paciente_identificacion", referencedColumnName = "Paciente_identificacion")}, inverseJoinColumns = {
        @JoinColumn(name = "Enfermedad_idEnfermedad", referencedColumnName = "idEnfermedad")})
    @ManyToMany
    private Collection<Enfermedad> enfermedadCollection;
    @JoinTable(name = "consulta_has_grupo_apoyo", joinColumns = {
        @JoinColumn(name = "Consulta_idConsulta", referencedColumnName = "idConsulta"),
        @JoinColumn(name = "Consulta_Medico_identificacion", referencedColumnName = "Medico_identificacion"),
        @JoinColumn(name = "Consulta_Paciente_identificacion", referencedColumnName = "Paciente_identificacion")}, inverseJoinColumns = {
        @JoinColumn(name = "Grupo_apoyo_idGrupo_apoyo", referencedColumnName = "idGrupo_apoyo"),
        @JoinColumn(name = "Grupo_apoyo_Entidad_nit", referencedColumnName = "Entidad_nit"),
        @JoinColumn(name = "Grupo_apoyo_Enfermedad_idEnfermedad", referencedColumnName = "Enfermedad_idEnfermedad")})
    @ManyToMany
    private Collection<GrupoApoyo> grupoApoyoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "consulta")
    private Collection<RecetaMedica> recetaMedicaCollection;
    @JoinColumn(name = "Medico_identificacion", referencedColumnName = "identificacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Medico medico;
    @JoinColumn(name = "Paciente_identificacion", referencedColumnName = "identificacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Paciente paciente;

    public Consulta() {
    }

    public Consulta(ConsultaPK consultaPK) {
        this.consultaPK = consultaPK;
    }

    public Consulta(int idConsulta, String medicoidentificacion, String pacienteidentificacion) {
        this.consultaPK = new ConsultaPK(idConsulta, medicoidentificacion, pacienteidentificacion);
    }

    public ConsultaPK getConsultaPK() {
        return consultaPK;
    }

    public void setConsultaPK(ConsultaPK consultaPK) {
        this.consultaPK = consultaPK;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public String getDeportes() {
        return deportes;
    }

    public void setDeportes(String deportes) {
        this.deportes = deportes;
    }

    @XmlTransient
    public Collection<Enfermedad> getEnfermedadCollection() {
        return enfermedadCollection;
    }

    public void setEnfermedadCollection(Collection<Enfermedad> enfermedadCollection) {
        this.enfermedadCollection = enfermedadCollection;
    }

    @XmlTransient
    public Collection<GrupoApoyo> getGrupoApoyoCollection() {
        return grupoApoyoCollection;
    }

    public void setGrupoApoyoCollection(Collection<GrupoApoyo> grupoApoyoCollection) {
        this.grupoApoyoCollection = grupoApoyoCollection;
    }

    @XmlTransient
    public Collection<RecetaMedica> getRecetaMedicaCollection() {
        return recetaMedicaCollection;
    }

    public void setRecetaMedicaCollection(Collection<RecetaMedica> recetaMedicaCollection) {
        this.recetaMedicaCollection = recetaMedicaCollection;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultaPK != null ? consultaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.consultaPK == null && other.consultaPK != null) || (this.consultaPK != null && !this.consultaPK.equals(other.consultaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Consulta[ consultaPK=" + consultaPK + " ]";
    }
    
}
