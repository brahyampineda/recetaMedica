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
public class MedicoEspecialistaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMedico_especialista")
    private int idMedicoespecialista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Usuario_idUsuario")
    private String usuarioidUsuario;

    public MedicoEspecialistaPK() {
    }

    public MedicoEspecialistaPK(int idMedicoespecialista, String usuarioidUsuario) {
        this.idMedicoespecialista = idMedicoespecialista;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getIdMedicoespecialista() {
        return idMedicoespecialista;
    }

    public void setIdMedicoespecialista(int idMedicoespecialista) {
        this.idMedicoespecialista = idMedicoespecialista;
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
        hash += (int) idMedicoespecialista;
        hash += (usuarioidUsuario != null ? usuarioidUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoEspecialistaPK)) {
            return false;
        }
        MedicoEspecialistaPK other = (MedicoEspecialistaPK) object;
        if (this.idMedicoespecialista != other.idMedicoespecialista) {
            return false;
        }
        if ((this.usuarioidUsuario == null && other.usuarioidUsuario != null) || (this.usuarioidUsuario != null && !this.usuarioidUsuario.equals(other.usuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.MedicoEspecialistaPK[ idMedicoespecialista=" + idMedicoespecialista + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
