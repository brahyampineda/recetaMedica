/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "ley")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ley.findAll", query = "SELECT l FROM Ley l"),
    @NamedQuery(name = "Ley.findByCodigo", query = "SELECT l FROM Ley l WHERE l.codigo = :codigo"),
    @NamedQuery(name = "Ley.findByNombre", query = "SELECT l FROM Ley l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "Ley.findByFecha", query = "SELECT l FROM Ley l WHERE l.fecha = :fecha"),
    @NamedQuery(name = "Ley.findByPreambulo", query = "SELECT l FROM Ley l WHERE l.preambulo = :preambulo")})
public class Ley implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 500)
    @Column(name = "preambulo")
    private String preambulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ley")
    private Collection<Articulo> articuloCollection;

    public Ley() {
    }

    public Ley(String codigo) {
        this.codigo = codigo;
    }

    public Ley(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPreambulo() {
        return preambulo;
    }

    public void setPreambulo(String preambulo) {
        this.preambulo = preambulo;
    }

    @XmlTransient
    public Collection<Articulo> getArticuloCollection() {
        return articuloCollection;
    }

    public void setArticuloCollection(Collection<Articulo> articuloCollection) {
        this.articuloCollection = articuloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ley)) {
            return false;
        }
        Ley other = (Ley) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Ley[ codigo=" + codigo + " ]";
    }
    
}
