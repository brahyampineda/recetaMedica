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
public class MedicoEspecialistaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMedico_especialista")
    private int idMedicoespecialista;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private int usuarioidUsuario;

    public MedicoEspecialistaPK() {
    }

    public MedicoEspecialistaPK(int idMedicoespecialista, int usuarioidUsuario) {
        this.idMedicoespecialista = idMedicoespecialista;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getIdMedicoespecialista() {
        return idMedicoespecialista;
    }

    public void setIdMedicoespecialista(int idMedicoespecialista) {
        this.idMedicoespecialista = idMedicoespecialista;
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
        hash += (int) idMedicoespecialista;
        hash += (int) usuarioidUsuario;
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
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.MedicoEspecialistaPK[ idMedicoespecialista=" + idMedicoespecialista + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
