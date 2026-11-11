/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author AsusVivobook
 */
public class Reserva {

    private String codigo;
    private Huesped huesped;
    private Habitacion habitacion;
    private int cantidadPersonas;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalDate fechaSalidaReal;
    private int noches;
    private long montoTotal;
    private String estado;
    private String recepcionista;

    public Reserva(String codigo, Huesped huesped,
            Habitacion habitacion, int cantidadPersonas,
            LocalDate fechaEntrada, LocalDate fechaSalida,
            String recepcionista) {

        this.codigo = codigo;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.recepcionista = recepcionista;

        this.fechaSalidaReal = null;
        this.noches = (int) ChronoUnit.DAYS.between(
                fechaEntrada, fechaSalida);

        this.montoTotal = noches * habitacion.getTarifa();
        this.estado = "Confirmada";
    }

    public String getCodigo() {
        return codigo;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public LocalDate getFechaSalidaReal() {
        return fechaSalidaReal;
    }

    public int getNoches() {
        return noches;
    }

    public long getMontoTotal() {
        return montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public String getRecepcionista() {
        return recepcionista;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaSalidaReal(LocalDate fechaSalidaReal) {
        this.fechaSalidaReal = fechaSalidaReal;
    }
}
