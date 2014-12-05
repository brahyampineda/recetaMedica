/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Brahyam
 */
@Embeddable
public class RecetaMedicaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idReceta_medica")
    private Integer idRecetamedica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Medicamento_idMedicamento")
    private Integer medicamentoidMedicamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Consulta_idConsulta")
    private Integer consultaidConsulta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Consulta_Medico_identificacion")
    private String consultaMedicoidentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Consulta_Paciente_identificacion")
    private String consultaPacienteidentificacion;

    public RecetaMedicaPK() {
    }

    public RecetaMedicaPK(Integer idRecetamedica, Integer medicamentoidMedicamento, Integer consultaidConsulta, String consultaMedicoidentificacion, String consultaPacienteidentificacion) {
        this.idRecetamedica = idRecetamedica;
        this.medicamentoidMedicamento = medicamentoidMedicamento;
        this.consultaidConsulta = consultaidConsulta;
        this.consultaMedicoidentificacion = consultaMedicoidentificacion;
        this.consultaPacienteidentificacion = consultaPacienteidentificacion;
    }

    public Integer getIdRecetamedica() {
        return idRecetamedica;
    }

    public void setIdRecetamedica(Integer idRecetamedica) {
        this.idRecetamedica = idRecetamedica;
    }

    public Integer getMedicamentoidMedicamento() {
        return medicamentoidMedicamento;
    }

    public void setMedicamentoidMedicamento(Integer medicamentoidMedicamento) {
        this.medicamentoidMedicamento = medicamentoidMedicamento;
    }

    public Integer getConsultaidConsulta() {
        return consultaidConsulta;
    }

    public void setConsultaidConsulta(Integer consultaidConsulta) {
        this.consultaidConsulta = consultaidConsulta;
    }

    public String getConsultaMedicoidentificacion() {
        return consultaMedicoidentificacion;
    }

    public void setConsultaMedicoidentificacion(String consultaMedicoidentificacion) {
        this.consultaMedicoidentificacion = consultaMedicoidentificacion;
    }

    public String getConsultaPacienteidentificacion() {
        return consultaPacienteidentificacion;
    }

    public void setConsultaPacienteidentificacion(String consultaPacienteidentificacion) {
        this.consultaPacienteidentificacion = consultaPacienteidentificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRecetamedica;
        hash += (int) medicamentoidMedicamento;
        hash += (int) consultaidConsulta;
        hash += (consultaMedicoidentificacion != null ? consultaMedicoidentificacion.hashCode() : 0);
        hash += (consultaPacienteidentificacion != null ? consultaPacienteidentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaMedicaPK)) {
            return false;
        }
        RecetaMedicaPK other = (RecetaMedicaPK) object;
        if (this.idRecetamedica != other.idRecetamedica) {
            return false;
        }
        if (this.medicamentoidMedicamento != other.medicamentoidMedicamento) {
            return false;
        }
        if (this.consultaidConsulta != other.consultaidConsulta) {
            return false;
        }
        if ((this.consultaMedicoidentificacion == null && other.consultaMedicoidentificacion != null) || (this.consultaMedicoidentificacion != null && !this.consultaMedicoidentificacion.equals(other.consultaMedicoidentificacion))) {
            return false;
        }
        if ((this.consultaPacienteidentificacion == null && other.consultaPacienteidentificacion != null) || (this.consultaPacienteidentificacion != null && !this.consultaPacienteidentificacion.equals(other.consultaPacienteidentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.RecetaMedicaPK[ idRecetamedica=" + idRecetamedica + ", medicamentoidMedicamento=" + medicamentoidMedicamento + ", consultaidConsulta=" + consultaidConsulta + ", consultaMedicoidentificacion=" + consultaMedicoidentificacion + ", consultaPacienteidentificacion=" + consultaPacienteidentificacion + " ]";
    }
    
}
