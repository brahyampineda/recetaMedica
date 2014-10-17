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
 * @author JorgeRivera
 */
@Embeddable
public class SolicitudQuejasReclamosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSolicitud_quejas_reclamos")
    private int idSolicitudquejasreclamos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Administrador_idAdministrador")
    private String administradoridAdministrador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Usuario_idUsuario")
    private String usuarioidUsuario;

    public SolicitudQuejasReclamosPK() {
    }

    public SolicitudQuejasReclamosPK(int idSolicitudquejasreclamos, String administradoridAdministrador, String usuarioidUsuario) {
        this.idSolicitudquejasreclamos = idSolicitudquejasreclamos;
        this.administradoridAdministrador = administradoridAdministrador;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getIdSolicitudquejasreclamos() {
        return idSolicitudquejasreclamos;
    }

    public void setIdSolicitudquejasreclamos(int idSolicitudquejasreclamos) {
        this.idSolicitudquejasreclamos = idSolicitudquejasreclamos;
    }

    public String getAdministradoridAdministrador() {
        return administradoridAdministrador;
    }

    public void setAdministradoridAdministrador(String administradoridAdministrador) {
        this.administradoridAdministrador = administradoridAdministrador;
    }

    public String getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(String usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSolicitudquejasreclamos;
        hash += (administradoridAdministrador != null ? administradoridAdministrador.hashCode() : 0);
        hash += (usuarioidUsuario != null ? usuarioidUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudQuejasReclamosPK)) {
            return false;
        }
        SolicitudQuejasReclamosPK other = (SolicitudQuejasReclamosPK) object;
        if (this.idSolicitudquejasreclamos != other.idSolicitudquejasreclamos) {
            return false;
        }
        if ((this.administradoridAdministrador == null && other.administradoridAdministrador != null) || (this.administradoridAdministrador != null && !this.administradoridAdministrador.equals(other.administradoridAdministrador))) {
            return false;
        }
        if ((this.usuarioidUsuario == null && other.usuarioidUsuario != null) || (this.usuarioidUsuario != null && !this.usuarioidUsuario.equals(other.usuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.SolicitudQuejasReclamosPK[ idSolicitudquejasreclamos=" + idSolicitudquejasreclamos + ", administradoridAdministrador=" + administradoridAdministrador + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}