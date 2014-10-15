/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.receta_medica.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
 * @author JorgeRivera
 */
@Entity
@Table(name = "paciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findByIdPaciente", query = "SELECT p FROM Paciente p WHERE p.pacientePK.idPaciente = :idPaciente"),
    @NamedQuery(name = "Paciente.findByEstrato", query = "SELECT p FROM Paciente p WHERE p.estrato = :estrato"),
    @NamedQuery(name = "Paciente.findByGenero", query = "SELECT p FROM Paciente p WHERE p.genero = :genero"),
    @NamedQuery(name = "Paciente.findByPeso", query = "SELECT p FROM Paciente p WHERE p.peso = :peso"),
    @NamedQuery(name = "Paciente.findByTieneSisben", query = "SELECT p FROM Paciente p WHERE p.tieneSisben = :tieneSisben"),
    @NamedQuery(name = "Paciente.findByAltura", query = "SELECT p FROM Paciente p WHERE p.altura = :altura"),
    @NamedQuery(name = "Paciente.findByPracticaDeporte", query = "SELECT p FROM Paciente p WHERE p.practicaDeporte = :practicaDeporte"),
    @NamedQuery(name = "Paciente.findByDireccion", query = "SELECT p FROM Paciente p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Paciente.findByNombreContacto", query = "SELECT p FROM Paciente p WHERE p.nombreContacto = :nombreContacto"),
    @NamedQuery(name = "Paciente.findByTelefonoContacto", query = "SELECT p FROM Paciente p WHERE p.telefonoContacto = :telefonoContacto"),
    @NamedQuery(name = "Paciente.findByUsuarioidUsuario", query = "SELECT p FROM Paciente p WHERE p.pacientePK.usuarioidUsuario = :usuarioidUsuario")})
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PacientePK pacientePK;
    @Size(max = 1)
    @Column(name = "estrato")
    private String estrato;
    @Size(max = 45)
    @Column(name = "genero")
    private String genero;
    @Size(max = 45)
    @Column(name = "peso")
    private String peso;
    @Size(max = 1)
    @Column(name = "tiene_sisben")
    private String tieneSisben;
    @Size(max = 45)
    @Column(name = "altura")
    private String altura;
    @Size(max = 1)
    @Column(name = "practica_deporte")
    private String practicaDeporte;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "nombre_contacto")
    private String nombreContacto;
    @Size(max = 45)
    @Column(name = "telefono_contacto")
    private String telefonoContacto;
    @JoinTable(name = "paciente_has_tratamiento", joinColumns = {
        @JoinColumn(name = "Paciente_idPaciente", referencedColumnName = "idPaciente")}, inverseJoinColumns = {
        @JoinColumn(name = "Tratamiento_idTratamiento", referencedColumnName = "idTratamiento")})
    @ManyToMany
    private Collection<Tratamiento> tratamientoCollection;
    @ManyToMany(mappedBy = "pacienteCollection")
    private Collection<Medicamento> medicamentoCollection;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Paciente() {
    }

    public Paciente(PacientePK pacientePK) {
        this.pacientePK = pacientePK;
    }

    public Paciente(int idPaciente, int usuarioidUsuario) {
        this.pacientePK = new PacientePK(idPaciente, usuarioidUsuario);
    }

    public PacientePK getPacientePK() {
        return pacientePK;
    }

    public void setPacientePK(PacientePK pacientePK) {
        this.pacientePK = pacientePK;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTieneSisben() {
        return tieneSisben;
    }

    public void setTieneSisben(String tieneSisben) {
        this.tieneSisben = tieneSisben;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPracticaDeporte() {
        return practicaDeporte;
    }

    public void setPracticaDeporte(String practicaDeporte) {
        this.practicaDeporte = practicaDeporte;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    @XmlTransient
    public Collection<Tratamiento> getTratamientoCollection() {
        return tratamientoCollection;
    }

    public void setTratamientoCollection(Collection<Tratamiento> tratamientoCollection) {
        this.tratamientoCollection = tratamientoCollection;
    }

    @XmlTransient
    public Collection<Medicamento> getMedicamentoCollection() {
        return medicamentoCollection;
    }

    public void setMedicamentoCollection(Collection<Medicamento> medicamentoCollection) {
        this.medicamentoCollection = medicamentoCollection;
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
        hash += (pacientePK != null ? pacientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.pacientePK == null && other.pacientePK != null) || (this.pacientePK != null && !this.pacientePK.equals(other.pacientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Paciente[ pacientePK=" + pacientePK + " ]";
    }
    
}
