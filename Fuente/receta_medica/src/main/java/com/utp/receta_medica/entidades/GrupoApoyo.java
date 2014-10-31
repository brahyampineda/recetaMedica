/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "grupo_apoyo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoApoyo.findAll", query = "SELECT g FROM GrupoApoyo g"),
    @NamedQuery(name = "GrupoApoyo.findByIdGrupoapoyo", query = "SELECT g FROM GrupoApoyo g WHERE g.grupoApoyoPK.idGrupoapoyo = :idGrupoapoyo"),
    @NamedQuery(name = "GrupoApoyo.findByNombre", query = "SELECT g FROM GrupoApoyo g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "GrupoApoyo.findByNombreProfesional", query = "SELECT g FROM GrupoApoyo g WHERE g.nombreProfesional = :nombreProfesional"),
    @NamedQuery(name = "GrupoApoyo.findByEntidadnit", query = "SELECT g FROM GrupoApoyo g WHERE g.grupoApoyoPK.entidadnit = :entidadnit"),
    @NamedQuery(name = "GrupoApoyo.findByEnfermedadidEnfermedad", query = "SELECT g FROM GrupoApoyo g WHERE g.grupoApoyoPK.enfermedadidEnfermedad = :enfermedadidEnfermedad")})
public class GrupoApoyo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoApoyoPK grupoApoyoPK;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "nombre_profesional")
    private String nombreProfesional;
    @JoinColumns({
        @JoinColumn(name = "Consulta_idConsulta", referencedColumnName = "idConsulta"),
        @JoinColumn(name = "Consulta_Medico_idMedico", referencedColumnName = "Medico_idMedico"),
        @JoinColumn(name = "Consulta_Medico_Usuario_idUsuario", referencedColumnName = "Medico_Usuario_idUsuario"),
        @JoinColumn(name = "Consulta_Paciente_idPaciente", referencedColumnName = "Paciente_idPaciente"),
        @JoinColumn(name = "Consulta_Paciente_Usuario_idUsuario", referencedColumnName = "Paciente_Usuario_idUsuario")})
    @ManyToOne(optional = false)
    private Consulta consulta;
    @JoinColumn(name = "Entidad_nit", referencedColumnName = "nit", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Entidad entidad;
    @JoinColumn(name = "Enfermedad_idEnfermedad", referencedColumnName = "idEnfermedad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enfermedad enfermedad;

    public GrupoApoyo() {
    }

    public GrupoApoyo(GrupoApoyoPK grupoApoyoPK) {
        this.grupoApoyoPK = grupoApoyoPK;
    }

    public GrupoApoyo(int idGrupoapoyo, String entidadnit, int enfermedadidEnfermedad) {
        this.grupoApoyoPK = new GrupoApoyoPK(idGrupoapoyo, entidadnit, enfermedadidEnfermedad);
    }

    public GrupoApoyoPK getGrupoApoyoPK() {
        return grupoApoyoPK;
    }

    public void setGrupoApoyoPK(GrupoApoyoPK grupoApoyoPK) {
        this.grupoApoyoPK = grupoApoyoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoApoyoPK != null ? grupoApoyoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoApoyo)) {
            return false;
        }
        GrupoApoyo other = (GrupoApoyo) object;
        if ((this.grupoApoyoPK == null && other.grupoApoyoPK != null) || (this.grupoApoyoPK != null && !this.grupoApoyoPK.equals(other.grupoApoyoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.GrupoApoyo[ grupoApoyoPK=" + grupoApoyoPK + " ]";
    }
    
}
