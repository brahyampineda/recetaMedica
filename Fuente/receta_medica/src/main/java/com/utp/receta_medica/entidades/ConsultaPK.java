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
public class ConsultaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idConsulta")
    private int idConsulta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Medico_idMedico")
    private int medicoidMedico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Medico_Usuario_idUsuario")
    private String medicoUsuarioidUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Paciente_idPaciente")
    private int pacienteidPaciente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Paciente_Usuario_idUsuario")
    private String pacienteUsuarioidUsuario;

    public ConsultaPK() {
    }

    public ConsultaPK(int idConsulta, int medicoidMedico, String medicoUsuarioidUsuario, int pacienteidPaciente, String pacienteUsuarioidUsuario) {
        this.idConsulta = idConsulta;
        this.medicoidMedico = medicoidMedico;
        this.medicoUsuarioidUsuario = medicoUsuarioidUsuario;
        this.pacienteidPaciente = pacienteidPaciente;
        this.pacienteUsuarioidUsuario = pacienteUsuarioidUsuario;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getMedicoidMedico() {
        return medicoidMedico;
    }

    public void setMedicoidMedico(int medicoidMedico) {
        this.medicoidMedico = medicoidMedico;
    }

    public String getMedicoUsuarioidUsuario() {
        return medicoUsuarioidUsuario;
    }

    public void setMedicoUsuarioidUsuario(String medicoUsuarioidUsuario) {
        this.medicoUsuarioidUsuario = medicoUsuarioidUsuario;
    }

    public int getPacienteidPaciente() {
        return pacienteidPaciente;
    }

    public void setPacienteidPaciente(int pacienteidPaciente) {
        this.pacienteidPaciente = pacienteidPaciente;
    }

    public String getPacienteUsuarioidUsuario() {
        return pacienteUsuarioidUsuario;
    }

    public void setPacienteUsuarioidUsuario(String pacienteUsuarioidUsuario) {
        this.pacienteUsuarioidUsuario = pacienteUsuarioidUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idConsulta;
        hash += (int) medicoidMedico;
        hash += (medicoUsuarioidUsuario != null ? medicoUsuarioidUsuario.hashCode() : 0);
        hash += (int) pacienteidPaciente;
        hash += (pacienteUsuarioidUsuario != null ? pacienteUsuarioidUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultaPK)) {
            return false;
        }
        ConsultaPK other = (ConsultaPK) object;
        if (this.idConsulta != other.idConsulta) {
            return false;
        }
        if (this.medicoidMedico != other.medicoidMedico) {
            return false;
        }
        if ((this.medicoUsuarioidUsuario == null && other.medicoUsuarioidUsuario != null) || (this.medicoUsuarioidUsuario != null && !this.medicoUsuarioidUsuario.equals(other.medicoUsuarioidUsuario))) {
            return false;
        }
        if (this.pacienteidPaciente != other.pacienteidPaciente) {
            return false;
        }
        if ((this.pacienteUsuarioidUsuario == null && other.pacienteUsuarioidUsuario != null) || (this.pacienteUsuarioidUsuario != null && !this.pacienteUsuarioidUsuario.equals(other.pacienteUsuarioidUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.ConsultaPK[ idConsulta=" + idConsulta + ", medicoidMedico=" + medicoidMedico + ", medicoUsuarioidUsuario=" + medicoUsuarioidUsuario + ", pacienteidPaciente=" + pacienteidPaciente + ", pacienteUsuarioidUsuario=" + pacienteUsuarioidUsuario + " ]";
    }
    
}
