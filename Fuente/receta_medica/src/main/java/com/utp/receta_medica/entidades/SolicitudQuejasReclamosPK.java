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
public class SolicitudQuejasReclamosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSolicitud_quejas_reclamos")
    private int idSolicitudquejasreclamos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Usuario_idUsuario")
    private String usuarioidUsuario;

    public SolicitudQuejasReclamosPK() {
    }

    public SolicitudQuejasReclamosPK(int idSolicitudquejasreclamos, String usuarioidUsuario) {
        this.idSolicitudquejasreclamos = idSolicitudquejasreclamos;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getIdSolicitudquejasreclamos() {
        return idSolicitudquejasreclamos;
    }

    public void setIdSolicitudquejasreclamos(int idSolicitudquejasreclamos) {
        this.idSolicitudquejasreclamos = idSolicitudquejasreclamos;
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
        if ((this.usuarioidUsuario == null && other.usuarioidUsuario != null) || (this.usuarioidUsuario != null && !this.usuarioidUsuario.equals(other.usuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.SolicitudQuejasReclamosPK[ idSolicitudquejasreclamos=" + idSolicitudquejasreclamos + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
