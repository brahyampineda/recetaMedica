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

/**
 *
 * @author Brahyam
 */
@Embeddable
public class TratamientoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTratamiento")
    private Integer idTratamiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Enfermedad_idEnfermedad1")
    private int enfermedadidEnfermedad1;

    public TratamientoPK() {
    }

    public TratamientoPK(Integer idTratamiento, int enfermedadidEnfermedad1) {
        this.idTratamiento = idTratamiento;
        this.enfermedadidEnfermedad1 = enfermedadidEnfermedad1;
    }

    public Integer getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Integer idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getEnfermedadidEnfermedad1() {
        return enfermedadidEnfermedad1;
    }

    public void setEnfermedadidEnfermedad1(int enfermedadidEnfermedad1) {
        this.enfermedadidEnfermedad1 = enfermedadidEnfermedad1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTratamiento;
        hash += (int) enfermedadidEnfermedad1;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TratamientoPK)) {
            return false;
        }
        TratamientoPK other = (TratamientoPK) object;
        if (this.idTratamiento != other.idTratamiento) {
            return false;
        }
        if (this.enfermedadidEnfermedad1 != other.enfermedadidEnfermedad1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.TratamientoPK[ idTratamiento=" + idTratamiento + ", enfermedadidEnfermedad1=" + enfermedadidEnfermedad1 + " ]";
    }
    
}
