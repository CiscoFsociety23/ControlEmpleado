/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Time;

/**
 *
 * @author sysadmin
 */
public class CorreccionAsistenciaReq {
    
    private String idAsistencia;
    private String horaEntrada;
    private String horaSalida;
    private String motivo;

    public CorreccionAsistenciaReq(String idAsistencia, String horaEntrada, String horaSalida, String motivo) {
        this.idAsistencia = idAsistencia;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.motivo = motivo;
    }
        
    public String getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(String idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }  
        
}
