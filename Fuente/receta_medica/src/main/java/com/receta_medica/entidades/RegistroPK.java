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
public class RegistroPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idRegistro")
    private int idRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private int usuarioidUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Administrador_idAdministrador")
    private int administradoridAdministrador;

    public RegistroPK() {
    }

    public RegistroPK(int idRegistro, int usuarioidUsuario, int administradoridAdministrador) {
        this.idRegistro = idRegistro;
        this.usuarioidUsuario = usuarioidUsuario;
        this.administradoridAdministrador = administradoridAdministrador;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getUsuarioidUsuario() {
        return usuarioidUsuario;
    }

    public void setUsuarioidUsuario(int usuarioidUsuario) {
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getAdministradoridAdministrador() {
        return administradoridAdministrador;
    }

    public void setAdministradoridAdministrador(int administradoridAdministrador) {
        this.administradoridAdministrador = administradoridAdministrador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRegistro;
        hash += (int) usuarioidUsuario;
        hash += (int) administradoridAdministrador;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroPK)) {
            return false;
        }
        RegistroPK other = (RegistroPK) object;
        if (this.idRegistro != other.idRegistro) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        if (this.administradoridAdministrador != other.administradoridAdministrador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.RegistroPK[ idRegistro=" + idRegistro + ", usuarioidUsuario=" + usuarioidUsuario + ", administradoridAdministrador=" + administradoridAdministrador + " ]";
    }
    
}
