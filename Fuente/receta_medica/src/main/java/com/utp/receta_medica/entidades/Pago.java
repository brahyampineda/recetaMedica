/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.pagoPK.idPago = :idPago"),
    @NamedQuery(name = "Pago.findByDireccion", query = "SELECT p FROM Pago p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Pago.findByTelefono", query = "SELECT p FROM Pago p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Pago.findByHorarioRecibir", query = "SELECT p FROM Pago p WHERE p.horarioRecibir = :horarioRecibir"),
    @NamedQuery(name = "Pago.findByCompraidCompra", query = "SELECT p FROM Pago p WHERE p.pagoPK.compraidCompra = :compraidCompra"),
    @NamedQuery(name = "Pago.findByCompraUsuarioidUsuario", query = "SELECT p FROM Pago p WHERE p.pagoPK.compraUsuarioidUsuario = :compraUsuarioidUsuario")})
public class Pago implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoPK pagoPK;
    @Size(max = 100)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "horario_recibir")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horarioRecibir;
    @JoinColumns({
        @JoinColumn(name = "Compra_idCompra", referencedColumnName = "idCompra", insertable = false, updatable = false),
        @JoinColumn(name = "Compra_Usuario_idUsuario", referencedColumnName = "Usuario_idUsuario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Compra compra;

    public Pago() {
    }

    public Pago(PagoPK pagoPK) {
        this.pagoPK = pagoPK;
    }

    public Pago(int idPago, int compraidCompra, String compraUsuarioidUsuario) {
        this.pagoPK = new PagoPK(idPago, compraidCompra, compraUsuarioidUsuario);
    }

    public PagoPK getPagoPK() {
        return pagoPK;
    }

    public void setPagoPK(PagoPK pagoPK) {
        this.pagoPK = pagoPK;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getHorarioRecibir() {
        return horarioRecibir;
    }

    public void setHorarioRecibir(Date horarioRecibir) {
        this.horarioRecibir = horarioRecibir;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagoPK != null ? pagoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.pagoPK == null && other.pagoPK != null) || (this.pagoPK != null && !this.pagoPK.equals(other.pagoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Pago[ pagoPK=" + pagoPK + " ]";
    }
    
}
