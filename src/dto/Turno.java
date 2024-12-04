/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author sysadmin
 */
public class Turno {
    
    private String idTurno;
    private String turno;
    private String horaTurnoEntrada;
    private String horaTurnoSalida;
    private Integer cantidadDeHoras;

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHoraTurnoEntrada() {
        return horaTurnoEntrada;
    }

    public void setHoraTurnoEntrada(String horaTurnoEntrada) {
        this.horaTurnoEntrada = horaTurnoEntrada;
    }

    public String getHoraTurnoSalida() {
        return horaTurnoSalida;
    }

    public void setHoraTurnoSalida(String horaTurnoSalida) {
        this.horaTurnoSalida = horaTurnoSalida;
    }

    public Integer getCantidadDeHoras() {
        return cantidadDeHoras;
    }

    public void setCantidadDeHoras(Integer cantidadDeHoras) {
        this.cantidadDeHoras = cantidadDeHoras;
    }
        
}
