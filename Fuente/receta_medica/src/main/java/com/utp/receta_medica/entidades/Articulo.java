/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "articulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a"),
    @NamedQuery(name = "Articulo.findByIdArticulo", query = "SELECT a FROM Articulo a WHERE a.articuloPK.idArticulo = :idArticulo"),
    @NamedQuery(name = "Articulo.findByNombre", query = "SELECT a FROM Articulo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Articulo.findByNombreCapitulo", query = "SELECT a FROM Articulo a WHERE a.nombreCapitulo = :nombreCapitulo"),
    @NamedQuery(name = "Articulo.findByDescripcion", query = "SELECT a FROM Articulo a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Articulo.findByLeycodigo", query = "SELECT a FROM Articulo a WHERE a.articuloPK.leycodigo = :leycodigo")})
public class Articulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArticuloPK articuloPK;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "nombreCapitulo")
    private String nombreCapitulo;
    @Size(max = 1500)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "Ley_codigo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ley ley;

    public Articulo() {
    }

    public Articulo(ArticuloPK articuloPK) {
        this.articuloPK = articuloPK;
    }

    public Articulo(ArticuloPK articuloPK, String nombre) {
        this.articuloPK = articuloPK;
        this.nombre = nombre;
    }

    public Articulo(int idArticulo, String leycodigo) {
        this.articuloPK = new ArticuloPK(idArticulo, leycodigo);
    }

    public ArticuloPK getArticuloPK() {
        return articuloPK;
    }

    public void setArticuloPK(ArticuloPK articuloPK) {
        this.articuloPK = articuloPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCapitulo() {
        return nombreCapitulo;
    }

    public void setNombreCapitulo(String nombreCapitulo) {
        this.nombreCapitulo = nombreCapitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ley getLey() {
        return ley;
    }

    public void setLey(Ley ley) {
        this.ley = ley;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articuloPK != null ? articuloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.articuloPK == null && other.articuloPK != null) || (this.articuloPK != null && !this.articuloPK.equals(other.articuloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Articulo[ articuloPK=" + articuloPK + " ]";
    }
    
}
