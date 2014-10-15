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
public class MedicoGeneralPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMedico_general")
    private int idMedicogeneral;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_idUsuario")
    private int usuarioidUsuario;

    public MedicoGeneralPK() {
    }

    public MedicoGeneralPK(int idMedicogeneral, int usuarioidUsuario) {
        this.idMedicogeneral = idMedicogeneral;
        this.usuarioidUsuario = usuarioidUsuario;
    }

    public int getIdMedicogeneral() {
        return idMedicogeneral;
    }

    public void setIdMedicogeneral(int idMedicogeneral) {
        this.idMedicogeneral = idMedicogeneral;
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
        hash += (int) idMedicogeneral;
        hash += (int) usuarioidUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicoGeneralPK)) {
            return false;
        }
        MedicoGeneralPK other = (MedicoGeneralPK) object;
        if (this.idMedicogeneral != other.idMedicogeneral) {
            return false;
        }
        if (this.usuarioidUsuario != other.usuarioidUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.MedicoGeneralPK[ idMedicogeneral=" + idMedicogeneral + ", usuarioidUsuario=" + usuarioidUsuario + " ]";
    }
    
}
