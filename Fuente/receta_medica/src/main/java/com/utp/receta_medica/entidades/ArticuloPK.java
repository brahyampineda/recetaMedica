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
public class ArticuloPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArticulo")
    private Integer idArticulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ley_codigo")
    private String leycodigo;

    public ArticuloPK() {
    }

    public ArticuloPK(Integer idArticulo, String leycodigo) {
        this.idArticulo = idArticulo;
        this.leycodigo = leycodigo;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getLeycodigo() {
        return leycodigo;
    }

    public void setLeycodigo(String leycodigo) {
        this.leycodigo = leycodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idArticulo;
        hash += (leycodigo != null ? leycodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticuloPK)) {
            return false;
        }
        ArticuloPK other = (ArticuloPK) object;
        if (this.idArticulo != other.idArticulo) {
            return false;
        }
        if ((this.leycodigo == null && other.leycodigo != null) || (this.leycodigo != null && !this.leycodigo.equals(other.leycodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.ArticuloPK[ idArticulo=" + idArticulo + ", leycodigo=" + leycodigo + " ]";
    }
    
}
