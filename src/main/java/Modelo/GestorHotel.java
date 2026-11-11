/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AsusVivobook
 */
public class GestorHotel {

    private static GestorHotel instancia;

    private ArrayList<Huesped> huespedes;
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Reserva> reservas;

    private String recepcionistaActivo;

    private GestorHotel() {
        huespedes = new ArrayList<>();
        habitaciones = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public static synchronized GestorHotel getInstancia() {
        if (instancia == null) {
            instancia = new GestorHotel();
        }
        return instancia;
    }

    public synchronized boolean agregarHuesped(Huesped huesped) {

        for (Huesped h : huespedes) {
            if (h.getCedula().equals(huesped.getCedula())) {
                return false;
            }
        }

        huespedes.add(huesped);
        return true;
    }

    public synchronized boolean agregarHabitacion(Habitacion habitacion) {

        for (Habitacion h : habitaciones) {
            if (h.getNumero() == habitacion.getNumero()) {
                return false;
            }
        }

        habitaciones.add(habitacion);
        return true;
    }

    public synchronized boolean agregarReserva(Reserva reserva) {

        for (Reserva r : reservas) {
            if (r.getCodigo().equals(reserva.getCodigo())) {
                return false;
            }
        }

        reservas.add(reserva);
        return true;
    }

    public synchronized List<Huesped> getHuespedes() {
        return new ArrayList<>(huespedes);
    }

    public synchronized List<Habitacion> getHabitaciones() {
        return new ArrayList<>(habitaciones);
    }

    public synchronized List<Reserva> getReservas() {
        return new ArrayList<>(reservas);
    }

    public synchronized String getRecepcionistaActivo() {
        return recepcionistaActivo;
    }

    public synchronized void setRecepcionistaActivo(String recepcionistaActivo) {
        this.recepcionistaActivo = recepcionistaActivo;
    }
}
