package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "tratamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tratamiento.findAll", query = "SELECT t FROM Tratamiento t"),
    @NamedQuery(name = "Tratamiento.findByIdTratamiento", query = "SELECT t FROM Tratamiento t WHERE t.tratamientoPK.idTratamiento = :idTratamiento"),
    @NamedQuery(name = "Tratamiento.findByNombre", query = "SELECT t FROM Tratamiento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tratamiento.findByDescripcion", query = "SELECT t FROM Tratamiento t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tratamiento.findByEnfermedadidEnfermedad1", query = "SELECT t FROM Tratamiento t WHERE t.tratamientoPK.enfermedadidEnfermedad1 = :enfermedadidEnfermedad1")})
public class Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TratamientoPK tratamientoPK;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToMany(mappedBy = "tratamientoCollection")
    private Collection<Paciente> pacienteCollection;
    @ManyToMany(mappedBy = "tratamientoCollection")
    private Collection<Medico> medicoCollection;
    @JoinColumn(name = "Enfermedad_idEnfermedad1", referencedColumnName = "idEnfermedad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Enfermedad enfermedad;

    public Tratamiento() {
    }

    public Tratamiento(TratamientoPK tratamientoPK) {
        this.tratamientoPK = tratamientoPK;
    }

    public Tratamiento(int idTratamiento, int enfermedadidEnfermedad1) {
        this.tratamientoPK = new TratamientoPK(idTratamiento, enfermedadidEnfermedad1);
    }

    public TratamientoPK getTratamientoPK() {
        return tratamientoPK;
    }

    public void setTratamientoPK(TratamientoPK tratamientoPK) {
        this.tratamientoPK = tratamientoPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Paciente> getPacienteCollection() {
        return pacienteCollection;
    }

    public void setPacienteCollection(Collection<Paciente> pacienteCollection) {
        this.pacienteCollection = pacienteCollection;
    }

    @XmlTransient
    public Collection<Medico> getMedicoCollection() {
        return medicoCollection;
    }

    public void setMedicoCollection(Collection<Medico> medicoCollection) {
        this.medicoCollection = medicoCollection;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tratamientoPK != null ? tratamientoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tratamiento)) {
            return false;
        }
        Tratamiento other = (Tratamiento) object;
        if ((this.tratamientoPK == null && other.tratamientoPK != null) || (this.tratamientoPK != null && !this.tratamientoPK.equals(other.tratamientoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Tratamiento[ tratamientoPK=" + tratamientoPK + " ]";
    }
    
}
