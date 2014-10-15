/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.receta_medica.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author JorgeRivera
 */
@Embeddable
public class SolicitudQuejasReclamosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idSolicitud_quejas_reclamos")
    private int idSolicitudquejasreclamos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Administrador_idAdministrador")
    private int administradoridAdministrador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private int usuarioidUsuario;

    public SolicitudQuejasReclamosPK() {
    }

    public SolicitudQuejasReclamosPK(int idSolicitudquejasreclamos, int administradoridAdministrador, int usuarioidUsuario) {
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

    public int getAdministradoridAdministrador() {
        return administradoridAdministrador;
    }

    public void setAdministradoridAdministrador(int administradoridAdministrador) {
        this.administradoridAdministrador = administradoridAdministrador;
    }

    public int getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(int usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSolicitudquejasreclamos;
        hash += (int) administradoridAdministrador;
        hash += (int) usuarioidUsuario;
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
        if (this.administradoridAdministrador != other.administradoridAdministrador) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.SolicitudQuejasReclamosPK[ idSolicitudquejasreclamos=" + idSolicitudquejasreclamos + ", administradoridAdministrador=" + administradoridAdministrador + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
