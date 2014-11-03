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
public class RecetaMedicaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idReceta_medica")
    private int idRecetamedica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Consulta_idConsulta")
    private int consultaidConsulta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Consulta_Medico_idMedico")
    private int consultaMedicoidMedico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Consulta_Medico_Usuario_idUsuario")
    private String consultaMedicoUsuarioidUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Consulta_Paciente_idPaciente")
    private int consultaPacienteidPaciente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Consulta_Paciente_Usuario_idUsuario")
    private String consultaPacienteUsuarioidUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Medicamento_idMedicamento")
    private int medicamentoidMedicamento;

    public RecetaMedicaPK() {
    }

    public RecetaMedicaPK(int idRecetamedica, int consultaidConsulta, int consultaMedicoidMedico, String consultaMedicoUsuarioidUsuario, int consultaPacienteidPaciente, String consultaPacienteUsuarioidUsuario, int medicamentoidMedicamento) {
        this.idRecetamedica = idRecetamedica;
        this.consultaidConsulta = consultaidConsulta;
        this.consultaMedicoidMedico = consultaMedicoidMedico;
        this.consultaMedicoUsuarioidUsuario = consultaMedicoUsuarioidUsuario;
        this.consultaPacienteidPaciente = consultaPacienteidPaciente;
        this.consultaPacienteUsuarioidUsuario = consultaPacienteUsuarioidUsuario;
        this.medicamentoidMedicamento = medicamentoidMedicamento;
    }

    public int getIdRecetamedica() {
        return idRecetamedica;
    }

    public void setIdRecetamedica(int idRecetamedica) {
        this.idRecetamedica = idRecetamedica;
    }

    public int getConsultaidConsulta() {
        return consultaidConsulta;
    }

    public void setConsultaidConsulta(int consultaidConsulta) {
        this.consultaidConsulta = consultaidConsulta;
    }

    public int getConsultaMedicoidMedico() {
        return consultaMedicoidMedico;
    }

    public void setConsultaMedicoidMedico(int consultaMedicoidMedico) {
        this.consultaMedicoidMedico = consultaMedicoidMedico;
    }

    public String getConsultaMedicoUsuarioidUsuario() {
        return consultaMedicoUsuarioidUsuario;
    }

    public void setConsultaMedicoUsuarioidUsuario(String consultaMedicoUsuarioidUsuario) {
        this.consultaMedicoUsuarioidUsuario = consultaMedicoUsuarioidUsuario;
    }

    public int getConsultaPacienteidPaciente() {
        return consultaPacienteidPaciente;
    }

    public void setConsultaPacienteidPaciente(int consultaPacienteidPaciente) {
        this.consultaPacienteidPaciente = consultaPacienteidPaciente;
    }

    public String getConsultaPacienteUsuarioidUsuario() {
        return consultaPacienteUsuarioidUsuario;
    }

    public void setConsultaPacienteUsuarioidUsuario(String consultaPacienteUsuarioidUsuario) {
        this.consultaPacienteUsuarioidUsuario = consultaPacienteUsuarioidUsuario;
    }

    public int getMedicamentoidMedicamento() {
        return medicamentoidMedicamento;
    }

    public void setMedicamentoidMedicamento(int medicamentoidMedicamento) {
        this.medicamentoidMedicamento = medicamentoidMedicamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRecetamedica;
        hash += (int) consultaidConsulta;
        hash += (int) consultaMedicoidMedico;
        hash += (consultaMedicoUsuarioidUsuario != null ? consultaMedicoUsuarioidUsuario.hashCode() : 0);
        hash += (int) consultaPacienteidPaciente;
        hash += (consultaPacienteUsuarioidUsuario != null ? consultaPacienteUsuarioidUsuario.hashCode() : 0);
        hash += (int) medicamentoidMedicamento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaMedicaPK)) {
            return false;
        }
        RecetaMedicaPK other = (RecetaMedicaPK) object;
        if (this.idRecetamedica != other.idRecetamedica) {
            return false;
        }
        if (this.consultaidConsulta != other.consultaidConsulta) {
            return false;
        }
        if (this.consultaMedicoidMedico != other.consultaMedicoidMedico) {
            return false;
        }
        if ((this.consultaMedicoUsuarioidUsuario == null && other.consultaMedicoUsuarioidUsuario != null) || (this.consultaMedicoUsuarioidUsuario != null && !this.consultaMedicoUsuarioidUsuario.equals(other.consultaMedicoUsuarioidUsuario))) {
            return false;
        }
        if (this.consultaPacienteidPaciente != other.consultaPacienteidPaciente) {
            return false;
        }
        if ((this.consultaPacienteUsuarioidUsuario == null && other.consultaPacienteUsuarioidUsuario != null) || (this.consultaPacienteUsuarioidUsuario != null && !this.consultaPacienteUsuarioidUsuario.equals(other.consultaPacienteUsuarioidUsuario))) {
            return false;
        }
        if (this.medicamentoidMedicamento != other.medicamentoidMedicamento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utp.receta_medica.entidades.RecetaMedicaPK[ idRecetamedica=" + idRecetamedica + ", consultaidConsulta=" + consultaidConsulta + ", consultaMedicoidMedico=" + consultaMedicoidMedico + ", consultaMedicoUsuarioidUsuario=" + consultaMedicoUsuarioidUsuario + ", consultaPacienteidPaciente=" + consultaPacienteidPaciente + ", consultaPacienteUsuarioidUsuario=" + consultaPacienteUsuarioidUsuario + ", medicamentoidMedicamento=" + medicamentoidMedicamento + " ]";
    }
    
}
