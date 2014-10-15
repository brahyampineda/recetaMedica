/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.receta_medica.entidades;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JorgeRivera
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
    @NamedQuery(name = "Medicamento.findByCantidad", query = "SELECT m FROM Medicamento m WHERE m.cantidad = :cantidad")})
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
    @Column(name = "precio")
    private Integer precio;
    @Size(max = 45)
    @Column(name = "composicion")
    private String composicion;
    @Size(max = 200)
    @Column(name = "indicaciones")
    private String indicaciones;
    @Size(max = 200)
    @Column(name = "precauciones")
    private String precauciones;
    @Size(max = 200)
    @Column(name = "contraindicaciones")
    private String contraindicaciones;
    @Size(max = 45)
    @Column(name = "presentacion")
    private String presentacion;
    @Size(max = 45)
    @Column(name = "dosis_usual")
    private String dosisUsual;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinTable(name = "medico_especialista_has_medicamento", joinColumns = {
        @JoinColumn(name = "Medicamento_idMedicamento", referencedColumnName = "idMedicamento")}, inverseJoinColumns = {
        @JoinColumn(name = "Medico_especialista_idMedico_especialista", referencedColumnName = "idMedico_especialista")})
    @ManyToMany
    private Collection<MedicoEspecialista> medicoEspecialistaCollection;
    @JoinTable(name = "medico_general_has_medicamento", joinColumns = {
        @JoinColumn(name = "Medicamento_idMedicamento", referencedColumnName = "idMedicamento")}, inverseJoinColumns = {
        @JoinColumn(name = "Medico_general_idMedico_general", referencedColumnName = "idMedico_general")})
    @ManyToMany
    private Collection<MedicoGeneral> medicoGeneralCollection;
    @JoinTable(name = "paciente_has_medicamento", joinColumns = {
        @JoinColumn(name = "Medicamento_idMedicamento", referencedColumnName = "idMedicamento")}, inverseJoinColumns = {
        @JoinColumn(name = "Paciente_idPaciente", referencedColumnName = "idPaciente")})
    @ManyToMany
    private Collection<Paciente> pacienteCollection;

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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
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

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDosisUsual() {
        return dosisUsual;
    }

    public void setDosisUsual(String dosisUsual) {
        this.dosisUsual = dosisUsual;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @XmlTransient
    public Collection<MedicoEspecialista> getMedicoEspecialistaCollection() {
        return medicoEspecialistaCollection;
    }

    public void setMedicoEspecialistaCollection(Collection<MedicoEspecialista> medicoEspecialistaCollection) {
        this.medicoEspecialistaCollection = medicoEspecialistaCollection;
    }

    @XmlTransient
    public Collection<MedicoGeneral> getMedicoGeneralCollection() {
        return medicoGeneralCollection;
    }

    public void setMedicoGeneralCollection(Collection<MedicoGeneral> medicoGeneralCollection) {
        this.medicoGeneralCollection = medicoGeneralCollection;
    }

    @XmlTransient
    public Collection<Paciente> getPacienteCollection() {
        return pacienteCollection;
    }

    public void setPacienteCollection(Collection<Paciente> pacienteCollection) {
        this.pacienteCollection = pacienteCollection;
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
