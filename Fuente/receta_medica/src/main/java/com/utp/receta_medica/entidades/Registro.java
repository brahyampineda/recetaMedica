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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JorgeRivera
 */
@Entity
@Table(name = "registro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r"),
    @NamedQuery(name = "Registro.findByIdRegistro", query = "SELECT r FROM Registro r WHERE r.registroPK.idRegistro = :idRegistro"),
    @NamedQuery(name = "Registro.findByEstado", query = "SELECT r FROM Registro r WHERE r.estado = :estado"),
    @NamedQuery(name = "Registro.findByPerfil", query = "SELECT r FROM Registro r WHERE r.perfil = :perfil"),
    @NamedQuery(name = "Registro.findByUsuarioidUsuario", query = "SELECT r FROM Registro r WHERE r.registroPK.usuarioidUsuario = :usuarioidUsuario"),
    @NamedQuery(name = "Registro.findByAdministradoridAdministrador", query = "SELECT r FROM Registro r WHERE r.registroPK.administradoridAdministrador = :administradoridAdministrador"),
    @NamedQuery(name = "Registro.findByAdministradorUsuarioidUsuario", query = "SELECT r FROM Registro r WHERE r.registroPK.administradorUsuarioidUsuario = :administradorUsuarioidUsuario")})
public class Registro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegistroPK registroPK;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @Size(max = 45)
    @Column(name = "perfil")
    private String perfil;
    @JoinColumns({
        @JoinColumn(name = "Administrador_idAdministrador", referencedColumnName = "idAdministrador", insertable = false, updatable = false),
        @JoinColumn(name = "Administrador_Usuario_idUsuario", referencedColumnName = "Usuario_idUsuario", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Administrador administrador;
    @JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Registro() {
    }

    public Registro(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Registro(int idRegistro, String usuarioidUsuario, String administradoridAdministrador, String administradorUsuarioidUsuario) {
        this.registroPK = new RegistroPK(idRegistro, usuarioidUsuario, administradoridAdministrador, administradorUsuarioidUsuario);
    }

    public RegistroPK getRegistroPK() {
        return registroPK;
    }

    public void setRegistroPK(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
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
        hash += (registroPK != null ? registroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.registroPK == null && other.registroPK != null) || (this.registroPK != null && !this.registroPK.equals(other.registroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.Registro[ registroPK=" + registroPK + " ]";
    }
    
}
