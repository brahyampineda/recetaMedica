package com.utp.receta_medica.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "cantidad_medicamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CantidadMedicamento.findAll", query = "SELECT c FROM CantidadMedicamento c"),
    @NamedQuery(name = "CantidadMedicamento.findByIdcantidadMedicamento", query = "SELECT c FROM CantidadMedicamento c WHERE c.cantidadMedicamentoPK.idcantidadMedicamento = :idcantidadMedicamento"),
    @NamedQuery(name = "CantidadMedicamento.findByCantidad", query = "SELECT c FROM CantidadMedicamento c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "CantidadMedicamento.findByCompraidCompra", query = "SELECT c FROM CantidadMedicamento c WHERE c.cantidadMedicamentoPK.compraidCompra = :compraidCompra"),
    @NamedQuery(name = "CantidadMedicamento.findByCompraUsuarioidUsuario", query = "SELECT c FROM CantidadMedicamento c WHERE c.cantidadMedicamentoPK.compraUsuarioidUsuario = :compraUsuarioidUsuario"),
    @NamedQuery(name = "CantidadMedicamento.findByMedicamentoidMedicamento", query = "SELECT c FROM CantidadMedicamento c WHERE c.cantidadMedicamentoPK.medicamentoidMedicamento = :medicamentoidMedicamento")})
public class CantidadMedicamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CantidadMedicamentoPK cantidadMedicamentoPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumns({
        @JoinColumn(name = "Compra_idCompra", referencedColumnName = "idCompra", insertable = false, updatable = false),
        @JoinColumn(name = "Compra_Usuario_idUsuario", referencedColumnName = "Usuario_idUsuario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Compra compra;
    @JoinColumn(name = "Medicamento_idMedicamento", referencedColumnName = "idMedicamento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Medicamento medicamento;

    public CantidadMedicamento() {
    }

    public CantidadMedicamento(CantidadMedicamentoPK cantidadMedicamentoPK) {
        this.cantidadMedicamentoPK = cantidadMedicamentoPK;
    }

    public CantidadMedicamento(int idcantidadMedicamento, int compraidCompra, String compraUsuarioidUsuario, int medicamentoidMedicamento) {
        this.cantidadMedicamentoPK = new CantidadMedicamentoPK(idcantidadMedicamento, compraidCompra, compraUsuarioidUsuario, medicamentoidMedicamento);
    }

    public CantidadMedicamentoPK getCantidadMedicamentoPK() {
        return cantidadMedicamentoPK;
    }

    public void setCantidadMedicamentoPK(CantidadMedicamentoPK cantidadMedicamentoPK) {
        this.cantidadMedicamentoPK = cantidadMedicamentoPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cantidadMedicamentoPK != null ? cantidadMedicamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CantidadMedicamento)) {
            return false;
        }
        CantidadMedicamento other = (CantidadMedicamento) object;
        if ((this.cantidadMedicamentoPK == null && other.cantidadMedicamentoPK != null) || (this.cantidadMedicamentoPK != null && !this.cantidadMedicamentoPK.equals(other.cantidadMedicamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.CantidadMedicamento[ cantidadMedicamentoPK=" + cantidadMedicamentoPK + " ]";
    }
    
}
