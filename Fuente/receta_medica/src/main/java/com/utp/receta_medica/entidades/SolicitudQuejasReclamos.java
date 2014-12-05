/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
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
@Table(name = "solicitud_quejas_reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudQuejasReclamos.findAll", query = "SELECT s FROM SolicitudQuejasReclamos s"),
    @NamedQuery(name = "SolicitudQuejasReclamos.findByIdSolicitudquejasreclamos", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.solicitudQuejasReclamosPK.idSolicitudquejasreclamos = :idSolicitudquejasreclamos"),
    @NamedQuery(name = "SolicitudQuejasReclamos.findByTitulo", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.titulo = :titulo"),
    @NamedQuery(name = "SolicitudQuejasReclamos.findByDescripcion", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SolicitudQuejasReclamos.findByEstado", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.estado = :estado"),
    @NamedQuery(name = "SolicitudQuejasReclamos.findByUsuarioemail", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.solicitudQuejasReclamosPK.usuarioemail = :usuarioemail")})
public class SolicitudQuejasReclamos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SolicitudQuejasReclamosPK solicitudQuejasReclamosPK;
    @Size(max = 45)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 15)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "Administrador_identificacion", referencedColumnName = "identificacion")
    @ManyToOne(optional = false)
    private Administrador administrador;
    @JoinColumn(name = "Usuario_email", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public SolicitudQuejasReclamos() {
    }

    public SolicitudQuejasReclamos(SolicitudQuejasReclamosPK solicitudQuejasReclamosPK) {
        this.solicitudQuejasReclamosPK = solicitudQuejasReclamosPK;
    }

    public SolicitudQuejasReclamos(int idSolicitudquejasreclamos, String usuarioemail) {
        this.solicitudQuejasReclamosPK = new SolicitudQuejasReclamosPK(idSolicitudquejasreclamos, usuarioemail);
    }

    public SolicitudQuejasReclamosPK getSolicitudQuejasReclamosPK() {
        return solicitudQuejasReclamosPK;
    }

    public void setSolicitudQuejasReclamosPK(SolicitudQuejasReclamosPK solicitudQuejasReclamosPK) {
        this.solicitudQuejasReclamosPK = solicitudQuejasReclamosPK;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudQuejasReclamosPK != null ? solicitudQuejasReclamosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudQuejasReclamos)) {
            return false;
        }
        SolicitudQuejasReclamos other = (SolicitudQuejasReclamos) object;
        if ((this.solicitudQuejasReclamosPK == null && other.solicitudQuejasReclamosPK != null) || (this.solicitudQuejasReclamosPK != null && !this.solicitudQuejasReclamosPK.equals(other.solicitudQuejasReclamosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.SolicitudQuejasReclamos[ solicitudQuejasReclamosPK=" + solicitudQuejasReclamosPK + " ]";
    }
    
}
