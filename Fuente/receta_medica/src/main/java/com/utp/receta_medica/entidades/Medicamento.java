/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utp.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brahyam
 */
@Entity
@Table(name = "medicamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicamento.findAll", query = "SELECT m FROM Medicamento m"),
    @NamedQuery(name = "Medicamento.findByIdMedicamento", query = "SELECT m FROM Medicamento m WHERE m.idMedicamento = :idMedicamento"),
    @NamedQuery(name = "Medicamento.findByNombre", query = "SELECT m FROM Medicamento m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Medicamento.findByPrecio", query = "SELECT m FROM Medicamento m WHERE m.precio = :precio"),
    @NamedQuery(name = "Medicamento.findByComposicion", query = "SELECT m FROM Medicamento m WHERE m.composicion = :composicion"),
    @NamedQuery(name = "Medicamento.findByIndicaciones", query = "SELECT m FROM Medicamento m WHERE m.indicaciones = :indicaciones"),
    @NamedQuery(name = "Medicamento.findByPrecauciones", query = "SELECT m FROM Medicamento m WHERE m.precauciones = :precauciones"),
    @NamedQuery(name = "Medicamento.findByContraindicaciones", query = "SELECT m FROM Medicamento m WHERE m.contraindicaciones = :contraindicaciones"),
    @NamedQuery(name = "Medicamento.findByPresentacion", query = "SELECT m FROM Medicamento m WHERE m.presentacion = :presentacion"),
    @NamedQuery(name = "Medicamento.findByDosisUsual", query = "SELECT m FROM Medicamento m WHERE m.dosisUsual = :dosisUsual"),
    @NamedQuery(name = "Medicamento.findByCantidad", query = "SELECT m FROM Medicamento m WHERE m.cantidad = :cantidad"),
    @NamedQuery(name = "Medicamento.findByEsPos", query = "SELECT m FROM Medicamento m WHERE m.esPos = :esPos")})
public class Medicamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idMedicamento")
    private Integer idMedicamento;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Min(value=0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Float precio;
    @Size(max = 300)
    @Column(name = "composicion")
    private String composicion;
    @Size(max = 300)
    @Column(name = "indicaciones")
    private String indicaciones;
    @Size(max = 300)
    @Column(name = "precauciones")
    private String precauciones;
    @Size(max = 300)
    @Column(name = "contraindicaciones")
    private String contraindicaciones;
    @Column(name = "presentacion")
    private Integer presentacion;
    @Column(name = "dosis_usual")
    private Integer dosisUsual;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "es_pos")
    private Boolean esPos;
    @JoinTable(name = "medico_has_medicamento", joinColumns = {
        @JoinColumn(name = "Medicamento_idMedicamento", referencedColumnName = "idMedicamento")}, inverseJoinColumns = {
        @JoinColumn(name = "Medico_identificacion", referencedColumnName = "identificacion")})
    @ManyToMany
    private Collection<Medico> medicoCollection;
    @ManyToMany(mappedBy = "medicamentoCollection")
    private Collection<Laboratorio> laboratorioCollection;
    @OneToMany(mappedBy = "medicamento")
    private Collection<RecetaMedica> recetaMedicaCollection;
    @OneToMany(mappedBy = "medicamento")
    private Collection<CantidadMedicamento> cantidadMedicamentoCollection;

    public Medicamento() {
    }

    public Medicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getPrecauciones() {
        return precauciones;
    }

    public void setPrecauciones(String precauciones) {
        this.precauciones = precauciones;
    }

    public String getContraindicaciones() {
        return contraindicaciones;
    }

    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }

    public Integer getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Integer presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getDosisUsual() {
        return dosisUsual;
    }

    public void setDosisUsual(Integer dosisUsual) {
        this.dosisUsual = dosisUsual;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getEsPos() {
        return esPos;
    }

    public void setEsPos(Boolean esPos) {
        this.esPos = esPos;
    }

    @XmlTransient
    public Collection<Medico> getMedicoCollection() {
        return medicoCollection;
    }

    public void setMedicoCollection(Collection<Medico> medicoCollection) {
        this.medicoCollection = medicoCollection;
    }

    @XmlTransient
    public Collection<Laboratorio> getLaboratorioCollection() {
        return laboratorioCollection;
    }

    public void setLaboratorioCollection(Collection<Laboratorio> laboratorioCollection) {
        this.laboratorioCollection = laboratorioCollection;
    }

    @XmlTransient
    public Collection<RecetaMedica> getRecetaMedicaCollection() {
        return recetaMedicaCollection;
    }

    public void setRecetaMedicaCollection(Collection<RecetaMedica> recetaMedicaCollection) {
        this.recetaMedicaCollection = recetaMedicaCollection;
    }

    @XmlTransient
    public Collection<CantidadMedicamento> getCantidadMedicamentoCollection() {
        return cantidadMedicamentoCollection;
    }

    public void setCantidadMedicamentoCollection(Collection<CantidadMedicamento> cantidadMedicamentoCollection) {
        this.cantidadMedicamentoCollection = cantidadMedicamentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMedicamento != null ? idMedicamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicamento)) {
            return false;
        }
        Medicamento other = (Medicamento) object;
        if ((this.idMedicamento == null && other.idMedicamento != null) || (this.idMedicamento != null && !this.idMedicamento.equals(other.idMedicamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Medicamento[ idMedicamento=" + idMedicamento + " ]";
    }
    
}
