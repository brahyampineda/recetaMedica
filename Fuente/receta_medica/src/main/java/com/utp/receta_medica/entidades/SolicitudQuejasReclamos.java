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
    @NamedQuery(name = "SolicitudQuejasReclamos.findBySolicitudquejasreclamoscol", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.solicitudquejasreclamoscol = :solicitudquejasreclamoscol"),
    @NamedQuery(name = "SolicitudQuejasReclamos.findByUsuarioidUsuario", query = "SELECT s FROM SolicitudQuejasReclamos s WHERE s.solicitudQuejasReclamosPK.usuarioidUsuario = :usuarioidUsuario")})
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
    @Size(max = 45)
    @Column(name = "Solicitud_quejas_reclamoscol")
    private String solicitudquejasreclamoscol;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumns({
        @JoinColumn(name = "Administrador_idAdministrador", referencedColumnName = "idAdministrador"),
        @JoinColumn(name = "Administrador_Usuario_idUsuario", referencedColumnName = "Usuario_idUsuario")})
    @ManyToOne(optional = false)
    private Administrador administrador;

    public SolicitudQuejasReclamos() {
    }

    public SolicitudQuejasReclamos(SolicitudQuejasReclamosPK solicitudQuejasReclamosPK) {
        this.solicitudQuejasReclamosPK = solicitudQuejasReclamosPK;
    }

    public SolicitudQuejasReclamos(int idSolicitudquejasreclamos, String usuarioidUsuario) {
        this.solicitudQuejasReclamosPK = new SolicitudQuejasReclamosPK(idSolicitudquejasreclamos, usuarioidUsuario);
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

    public String getSolicitudquejasreclamoscol() {
        return solicitudquejasreclamoscol;
    }

    public void setSolicitudquejasreclamoscol(String solicitudquejasreclamoscol) {
        this.solicitudquejasreclamoscol = solicitudquejasreclamoscol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
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
