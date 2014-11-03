package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "entidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e"),
    @NamedQuery(name = "Entidad.findByNit", query = "SELECT e FROM Entidad e WHERE e.nit = :nit"),
    @NamedQuery(name = "Entidad.findByNombre", query = "SELECT e FROM Entidad e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Entidad.findByEmail", query = "SELECT e FROM Entidad e WHERE e.email = :email"),
    @NamedQuery(name = "Entidad.findByEsEps", query = "SELECT e FROM Entidad e WHERE e.esEps = :esEps"),
    @NamedQuery(name = "Entidad.findByUrl", query = "SELECT e FROM Entidad e WHERE e.url = :url")})
public class Entidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nit")
    private String nit;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Column(name = "es_eps")
    private Boolean esEps;
    @Size(max = 45)
    @Column(name = "url")
    private String url;
    @JoinTable(name = "entidad_has_medico", joinColumns = {
        @JoinColumn(name = "Entidad_nit", referencedColumnName = "nit")}, inverseJoinColumns = {
        @JoinColumn(name = "Medico_idMedico", referencedColumnName = "idMedico"),
        @JoinColumn(name = "Medico_Usuario_idUsuario", referencedColumnName = "Usuario_idUsuario")})
    @ManyToMany
    private Collection<Medico> medicoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
    private Collection<GrupoApoyo> grupoApoyoCollection;

    public Entidad() {
    }

    public Entidad(String nit) {
        this.nit = nit;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEsEps() {
        return esEps;
    }

    public void setEsEps(Boolean esEps) {
        this.esEps = esEps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public Collection<Medico> getMedicoCollection() {
        return medicoCollection;
    }

    public void setMedicoCollection(Collection<Medico> medicoCollection) {
        this.medicoCollection = medicoCollection;
    }

    @XmlTransient
    public Collection<GrupoApoyo> getGrupoApoyoCollection() {
        return grupoApoyoCollection;
    }

    public void setGrupoApoyoCollection(Collection<GrupoApoyo> grupoApoyoCollection) {
        this.grupoApoyoCollection = grupoApoyoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Entidad[ nit=" + nit + " ]";
    }
    
}
