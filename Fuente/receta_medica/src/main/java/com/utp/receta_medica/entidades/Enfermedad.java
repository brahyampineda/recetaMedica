package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "enfermedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enfermedad.findAll", query = "SELECT e FROM Enfermedad e"),
    @NamedQuery(name = "Enfermedad.findByIdEnfermedad", query = "SELECT e FROM Enfermedad e WHERE e.idEnfermedad = :idEnfermedad"),
    @NamedQuery(name = "Enfermedad.findByPosologia", query = "SELECT e FROM Enfermedad e WHERE e.posologia = :posologia"),
    @NamedQuery(name = "Enfermedad.findByNombre", query = "SELECT e FROM Enfermedad e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Enfermedad.findByRecomendaciones", query = "SELECT e FROM Enfermedad e WHERE e.recomendaciones = :recomendaciones")})
public class Enfermedad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEnfermedad")
    private Integer idEnfermedad;
    @Size(max = 150)
    @Column(name = "posologia")
    private String posologia;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 300)
    @Column(name = "recomendaciones")
    private String recomendaciones;
    @ManyToMany(mappedBy = "enfermedadCollection")
    private Collection<Consulta> consultaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enfermedad")
    private Collection<Tratamiento> tratamientoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "enfermedad")
    private Collection<GrupoApoyo> grupoApoyoCollection;

    public Enfermedad() {
    }

    public Enfermedad(Integer idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public Integer getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(Integer idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    @XmlTransient
    public Collection<Consulta> getConsultaCollection() {
        return consultaCollection;
    }

    public void setConsultaCollection(Collection<Consulta> consultaCollection) {
        this.consultaCollection = consultaCollection;
    }

    @XmlTransient
    public Collection<Tratamiento> getTratamientoCollection() {
        return tratamientoCollection;
    }

    public void setTratamientoCollection(Collection<Tratamiento> tratamientoCollection) {
        this.tratamientoCollection = tratamientoCollection;
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
        hash += (idEnfermedad != null ? idEnfermedad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enfermedad)) {
            return false;
        }
        Enfermedad other = (Enfermedad) object;
        if ((this.idEnfermedad == null && other.idEnfermedad != null) || (this.idEnfermedad != null && !this.idEnfermedad.equals(other.idEnfermedad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Enfermedad[ idEnfermedad=" + idEnfermedad + " ]";
    }
    
}
