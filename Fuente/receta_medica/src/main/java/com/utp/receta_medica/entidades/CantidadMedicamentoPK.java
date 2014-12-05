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
public class CantidadMedicamentoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcantidad_medicamento")
    private int idcantidadMedicamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Medicamento_idMedicamento")
    private int medicamentoidMedicamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Compra_idCompra")
    private int compraidCompra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Compra_Usuario_email")
    private String compraUsuario;

    public CantidadMedicamentoPK() {
    }

    public CantidadMedicamentoPK(int idcantidadMedicamento, int medicamentoidMedicamento, int compraidCompra, String compraUsuarioemail) {
        this.idcantidadMedicamento = idcantidadMedicamento;
        this.medicamentoidMedicamento = medicamentoidMedicamento;
        this.compraidCompra = compraidCompra;
        this.compraUsuario = compraUsuarioemail;
    }

    public int getIdcantidadMedicamento() {
        return idcantidadMedicamento;
    }

    public void setIdcantidadMedicamento(int idcantidadMedicamento) {
        this.idcantidadMedicamento = idcantidadMedicamento;
    }

    public int getMedicamentoidMedicamento() {
        return medicamentoidMedicamento;
    }

    public void setMedicamentoidMedicamento(int medicamentoidMedicamento) {
        this.medicamentoidMedicamento = medicamentoidMedicamento;
    }

    public int getCompraidCompra() {
        return compraidCompra;
    }

    public void setCompraidCompra(int compraidCompra) {
        this.compraidCompra = compraidCompra;
    }

    public String getCompraUsuarioemail() {
        return compraUsuario;
    }

    public void setCompraUsuarioemail(String compraUsuarioemail) {
        this.compraUsuario = compraUsuarioemail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcantidadMedicamento;
        hash += (int) medicamentoidMedicamento;
        hash += (int) compraidCompra;
        hash += (compraUsuario != null ? compraUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CantidadMedicamentoPK)) {
            return false;
        }
        CantidadMedicamentoPK other = (CantidadMedicamentoPK) object;
        if (this.idcantidadMedicamento != other.idcantidadMedicamento) {
            return false;
        }
        if (this.medicamentoidMedicamento != other.medicamentoidMedicamento) {
            return false;
        }
        if (this.compraidCompra != other.compraidCompra) {
            return false;
        }
        if ((this.compraUsuario == null && other.compraUsuario != null) || (this.compraUsuario != null && !this.compraUsuario.equals(other.compraUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.CantidadMedicamentoPK[ idcantidadMedicamento=" + idcantidadMedicamento + ", medicamentoidMedicamento=" + medicamentoidMedicamento + ", compraidCompra=" + compraidCompra + ", compraUsuario=" + compraUsuario + " ]";
    }
    
}
