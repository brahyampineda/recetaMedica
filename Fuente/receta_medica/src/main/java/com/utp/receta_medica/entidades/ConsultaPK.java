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
public class ConsultaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idConsulta")
    private int idConsulta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Medico_identificacion")
    private String medicoidentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Paciente_identificacion")
    private String pacienteidentificacion;

    public ConsultaPK() {
    }

    public ConsultaPK(int idConsulta, String medicoidentificacion, String pacienteidentificacion) {
        this.idConsulta = idConsulta;
        this.medicoidentificacion = medicoidentificacion;
        this.pacienteidentificacion = pacienteidentificacion;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getMedicoidentificacion() {
        return medicoidentificacion;
    }

    public void setMedicoidentificacion(String medicoidentificacion) {
        this.medicoidentificacion = medicoidentificacion;
    }

    public String getPacienteidentificacion() {
        return pacienteidentificacion;
    }

    public void setPacienteidentificacion(String pacienteidentificacion) {
        this.pacienteidentificacion = pacienteidentificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idConsulta;
        hash += (medicoidentificacion != null ? medicoidentificacion.hashCode() : 0);
        hash += (pacienteidentificacion != null ? pacienteidentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultaPK)) {
            return false;
        }
        ConsultaPK other = (ConsultaPK) object;
        if (this.idConsulta != other.idConsulta) {
            return false;
        }
        if ((this.medicoidentificacion == null && other.medicoidentificacion != null) || (this.medicoidentificacion != null && !this.medicoidentificacion.equals(other.medicoidentificacion))) {
            return false;
        }
        if ((this.pacienteidentificacion == null && other.pacienteidentificacion != null) || (this.pacienteidentificacion != null && !this.pacienteidentificacion.equals(other.pacienteidentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.ConsultaPK[ idConsulta=" + idConsulta + ", medicoidentificacion=" + medicoidentificacion + ", pacienteidentificacion=" + pacienteidentificacion + " ]";
    }
    
}
