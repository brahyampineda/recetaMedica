/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "administrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a"),
    @NamedQuery(name = "Administrador.findByIdAdministrador", query = "SELECT a FROM Administrador a WHERE a.administradorPK.idAdministrador = :idAdministrador"),
    @NamedQuery(name = "Administrador.findByUsuarioidUsuario", query = "SELECT a FROM Administrador a WHERE a.administradorPK.usuarioidUsuario = :usuarioidUsuario")})
public class Administrador implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AdministradorPK administradorPK;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "administrador")
    private Collection<SolicitudQuejasReclamos> solicitudQuejasReclamosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "administrador")
    private Collection<Registro> registroCollection;

    public Administrador() {
    }

    public Administrador(AdministradorPK administradorPK) {
        this.administradorPK = administradorPK;
    }

    public Administrador(String idAdministrador, String usuarioidUsuario) {
        this.administradorPK = new AdministradorPK(idAdministrador, usuarioidUsuario);
    }

    public AdministradorPK getAdministradorPK() {
        return administradorPK;
    }

    public void setAdministradorPK(AdministradorPK administradorPK) {
        this.administradorPK = administradorPK;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<SolicitudQuejasReclamos> getSolicitudQuejasReclamosCollection() {
        return solicitudQuejasReclamosCollection;
    }

    public void setSolicitudQuejasReclamosCollection(Collection<SolicitudQuejasReclamos> solicitudQuejasReclamosCollection) {
        this.solicitudQuejasReclamosCollection = solicitudQuejasReclamosCollection;
    }

    @XmlTransient
    public Collection<Registro> getRegistroCollection() {
        return registroCollection;
    }

    public void setRegistroCollection(Collection<Registro> registroCollection) {
        this.registroCollection = registroCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (administradorPK != null ? administradorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.administradorPK == null && other.administradorPK != null) || (this.administradorPK != null && !this.administradorPK.equals(other.administradorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Administrador[ administradorPK=" + administradorPK + " ]";
    }
    
}
