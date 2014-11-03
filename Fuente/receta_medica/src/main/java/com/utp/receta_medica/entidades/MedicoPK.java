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
public class MedicoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMedico")
    private int idMedico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Usuario_idUsuario")
    private String usuarioidUsuario;

    public MedicoPK() {
    }

    public MedicoPK(int idMedico, String usuarioidUsuario) {
        this.idMedico = idMedico;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
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
        hash += (int) idMedico;
        hash += (usuarioidUsuario != null ? usuarioidUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoPK)) {
            return false;
        }
        MedicoPK other = (MedicoPK) object;
        if (this.idMedico != other.idMedico) {
            return false;
        }
        if ((this.usuarioidUsuario == null && other.usuarioidUsuario != null) || (this.usuarioidUsuario != null && !this.usuarioidUsuario.equals(other.usuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.MedicoPK[ idMedico=" + idMedico + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
