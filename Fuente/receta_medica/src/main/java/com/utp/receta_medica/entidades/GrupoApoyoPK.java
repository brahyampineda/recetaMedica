package com.utp.receta_medica.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Brahyam
 */
@Embeddable
public class GrupoApoyoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idGrupo_apoyo")
    private int idGrupoapoyo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Entidad_nit")
    private String entidadnit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Enfermedad_idEnfermedad")
    private int enfermedadidEnfermedad;

    public GrupoApoyoPK() {
    }

    public GrupoApoyoPK(int idGrupoapoyo, String entidadnit, int enfermedadidEnfermedad) {
        this.idGrupoapoyo = idGrupoapoyo;
        this.entidadnit = entidadnit;
        this.enfermedadidEnfermedad = enfermedadidEnfermedad;
    }

    public int getIdGrupoapoyo() {
        return idGrupoapoyo;
    }

    public void setIdGrupoapoyo(int idGrupoapoyo) {
        this.idGrupoapoyo = idGrupoapoyo;
    }

    public String getEntidadnit() {
        return entidadnit;
    }

    public void setEntidadnit(String entidadnit) {
        this.entidadnit = entidadnit;
    }

    public int getEnfermedadidEnfermedad() {
        return enfermedadidEnfermedad;
    }

    public void setEnfermedadidEnfermedad(int enfermedadidEnfermedad) {
        this.enfermedadidEnfermedad = enfermedadidEnfermedad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrupoapoyo;
        hash += (entidadnit != null ? entidadnit.hashCode() : 0);
        hash += (int) enfermedadidEnfermedad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoApoyoPK)) {
            return false;
        }
        GrupoApoyoPK other = (GrupoApoyoPK) object;
        if (this.idGrupoapoyo != other.idGrupoapoyo) {
            return false;
        }
        if ((this.entidadnit == null && other.entidadnit != null) || (this.entidadnit != null && !this.entidadnit.equals(other.entidadnit))) {
            return false;
        }
        if (this.enfermedadidEnfermedad != other.enfermedadidEnfermedad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.GrupoApoyoPK[ idGrupoapoyo=" + idGrupoapoyo + ", entidadnit=" + entidadnit + ", enfermedadidEnfermedad=" + enfermedadidEnfermedad + " ]";
    }
    
}
