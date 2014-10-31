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
public class AdministradorPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idAdministrador")
    private String idAdministrador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Usuario_idUsuario")
    private String usuarioidUsuario;

    public AdministradorPK() {
    }

    public AdministradorPK(String idAdministrador, String usuarioidUsuario) {
        this.idAdministrador = idAdministrador;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
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
        hash += (idAdministrador != null ? idAdministrador.hashCode() : 0);
        hash += (usuarioidUsuario != null ? usuarioidUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdministradorPK)) {
            return false;
        }
        AdministradorPK other = (AdministradorPK) object;
        if ((this.idAdministrador == null && other.idAdministrador != null) || (this.idAdministrador != null && !this.idAdministrador.equals(other.idAdministrador))) {
            return false;
        }
        if ((this.usuarioidUsuario == null && other.usuarioidUsuario != null) || (this.usuarioidUsuario != null && !this.usuarioidUsuario.equals(other.usuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.AdministradorPK[ idAdministrador=" + idAdministrador + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
