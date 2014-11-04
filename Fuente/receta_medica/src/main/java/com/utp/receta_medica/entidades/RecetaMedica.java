/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "receta_medica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecetaMedica.findAll", query = "SELECT r FROM RecetaMedica r"),
    @NamedQuery(name = "RecetaMedica.findByIdRecetamedica", query = "SELECT r FROM RecetaMedica r WHERE r.recetaMedicaPK.idRecetamedica = :idRecetamedica"),
    @NamedQuery(name = "RecetaMedica.findByDosis", query = "SELECT r FROM RecetaMedica r WHERE r.dosis = :dosis"),
    @NamedQuery(name = "RecetaMedica.findByPeriodicidad", query = "SELECT r FROM RecetaMedica r WHERE r.periodicidad = :periodicidad"),
    @NamedQuery(name = "RecetaMedica.findByDosisDisponible", query = "SELECT r FROM RecetaMedica r WHERE r.dosisDisponible = :dosisDisponible"),
    @NamedQuery(name = "RecetaMedica.findByFechaInicio", query = "SELECT r FROM RecetaMedica r WHERE r.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "RecetaMedica.findByMedicamentoidMedicamento", query = "SELECT r FROM RecetaMedica r WHERE r.recetaMedicaPK.medicamentoidMedicamento = :medicamentoidMedicamento"),
    @NamedQuery(name = "RecetaMedica.findByConsultaidConsulta", query = "SELECT r FROM RecetaMedica r WHERE r.recetaMedicaPK.consultaidConsulta = :consultaidConsulta"),
    @NamedQuery(name = "RecetaMedica.findByConsultaMedicoidentificacion", query = "SELECT r FROM RecetaMedica r WHERE r.recetaMedicaPK.consultaMedicoidentificacion = :consultaMedicoidentificacion"),
    @NamedQuery(name = "RecetaMedica.findByConsultaPacienteidentificacion", query = "SELECT r FROM RecetaMedica r WHERE r.recetaMedicaPK.consultaPacienteidentificacion = :consultaPacienteidentificacion")})
public class RecetaMedica implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecetaMedicaPK recetaMedicaPK;
    @Column(name = "dosis")
    private Integer dosis;
    @Column(name = "periodicidad")
    private Integer periodicidad;
    @Column(name = "dosis_disponible")
    private Integer dosisDisponible;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @JoinColumn(name = "Medicamento_idMedicamento", referencedColumnName = "idMedicamento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Medicamento medicamento;
    @JoinColumns({
        @JoinColumn(name = "Consulta_idConsulta", referencedColumnName = "idConsulta", insertable = false, updatable = false),
        @JoinColumn(name = "Consulta_Medico_identificacion", referencedColumnName = "Medico_identificacion", insertable = false, updatable = false),
        @JoinColumn(name = "Consulta_Paciente_identificacion", referencedColumnName = "Paciente_identificacion", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Consulta consulta;

    public RecetaMedica() {
    }

    public RecetaMedica(RecetaMedicaPK recetaMedicaPK) {
        this.recetaMedicaPK = recetaMedicaPK;
    }

    public RecetaMedica(int idRecetamedica, int medicamentoidMedicamento, int consultaidConsulta, String consultaMedicoidentificacion, String consultaPacienteidentificacion) {
        this.recetaMedicaPK = new RecetaMedicaPK(idRecetamedica, medicamentoidMedicamento, consultaidConsulta, consultaMedicoidentificacion, consultaPacienteidentificacion);
    }

    public RecetaMedicaPK getRecetaMedicaPK() {
        return recetaMedicaPK;
    }

    public void setRecetaMedicaPK(RecetaMedicaPK recetaMedicaPK) {
        this.recetaMedicaPK = recetaMedicaPK;
    }

    public Integer getDosis() {
        return dosis;
    }

    public void setDosis(Integer dosis) {
        this.dosis = dosis;
    }

    public Integer getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Integer periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Integer getDosisDisponible() {
        return dosisDisponible;
    }

    public void setDosisDisponible(Integer dosisDisponible) {
        this.dosisDisponible = dosisDisponible;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recetaMedicaPK != null ? recetaMedicaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaMedica)) {
            return false;
        }
        RecetaMedica other = (RecetaMedica) object;
        if ((this.recetaMedicaPK == null && other.recetaMedicaPK != null) || (this.recetaMedicaPK != null && !this.recetaMedicaPK.equals(other.recetaMedicaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.RecetaMedica[ recetaMedicaPK=" + recetaMedicaPK + " ]";
    }
    
}
