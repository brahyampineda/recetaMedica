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
public class PagoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPago")
    private int idPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Compra_idCompra")
    private int compraidCompra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Compra_Usuario_idUsuario")
    private String compraUsuarioidUsuario;

    public PagoPK() {
    }

    public PagoPK(int idPago, int compraidCompra, String compraUsuarioidUsuario) {
        this.idPago = idPago;
        this.compraidCompra = compraidCompra;
        this.compraUsuarioidUsuario = compraUsuarioidUsuario;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getCompraidCompra() {
        return compraidCompra;
    }

    public void setCompraidCompra(int compraidCompra) {
        this.compraidCompra = compraidCompra;
    }

    public String getCompraUsuarioidUsuario() {
        return compraUsuarioidUsuario;
    }

    public void setCompraUsuarioidUsuario(String compraUsuarioidUsuario) {
        this.compraUsuarioidUsuario = compraUsuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPago;
        hash += (int) compraidCompra;
        hash += (compraUsuarioidUsuario != null ? compraUsuarioidUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoPK)) {
            return false;
        }
        PagoPK other = (PagoPK) object;
        if (this.idPago != other.idPago) {
            return false;
        }
        if (this.compraidCompra != other.compraidCompra) {
            return false;
        }
        if ((this.compraUsuarioidUsuario == null && other.compraUsuarioidUsuario != null) || (this.compraUsuarioidUsuario != null && !this.compraUsuarioidUsuario.equals(other.compraUsuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.PagoPK[ idPago=" + idPago + ", compraidCompra=" + compraidCompra + ", compraUsuarioidUsuario=" + compraUsuarioidUsuario + " ]";
    }
    
}
