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
public class CompraPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCompra")
    private int idCompra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Usuario_email")
    private String usuarioemail;

    public CompraPK() {
    }

    public CompraPK(int idCompra, String usuarioemail) {
        this.idCompra = idCompra;
        this.usuarioemail = usuarioemail;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getUsuarioemail() {
        return usuarioemail;
    }

    public void setUsuarioemail(String usuarioemail) {
        this.usuarioemail = usuarioemail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCompra;
        hash += (usuarioemail != null ? usuarioemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraPK)) {
            return false;
        }
        CompraPK other = (CompraPK) object;
        if (this.idCompra != other.idCompra) {
            return false;
        }
        if ((this.usuarioemail == null && other.usuarioemail != null) || (this.usuarioemail != null && !this.usuarioemail.equals(other.usuarioemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.CompraPK[ idCompra=" + idCompra + ", usuarioemail=" + usuarioemail + " ]";
    }
    
}
