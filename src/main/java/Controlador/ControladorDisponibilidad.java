/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;
import Modelo.Habitacion;
import Modelo.Reserva;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AsusVivobook
 */
public class ControladorDisponibilidad {

    private GestorHotel gestor;

    public ControladorDisponibilidad() {
        gestor = GestorHotel.getInstancia();
    }

    public List<Habitacion> consultarDisponibilidad(LocalDate fecha) {

        List<Habitacion> disponibles = new ArrayList<>();

        if (fecha == null) {
            return disponibles;
        }

        List<Habitacion> habitaciones = gestor.getHabitaciones();

        for (Habitacion h : habitaciones) {
            if (estaDisponible(h, fecha)) {
                disponibles.add(h);
            }
        }

        return disponibles;
    }

    private boolean estaDisponible(Habitacion habitacion, LocalDate fecha) {

        List<Reserva> reservas = gestor.getReservas();

        for (Reserva r : reservas) {

            if (r.getHabitacion().getNumero() == habitacion.getNumero()
                    && (r.getEstado().equals("Confirmada")
                    || r.getEstado().equals("Hospedado"))) {

                if (!fecha.isBefore(r.getFechaEntrada())
                        && fecha.isBefore(r.getFechaSalida())) {
                    return false;
                }
            }
        }

        return true;
    }

    public int contarDisponibles(LocalDate fecha) {

        List<Habitacion> habitaciones = gestor.getHabitaciones();

        return contarRecursivo(habitaciones, fecha, 0);
    }

    private int contarRecursivo(List<Habitacion> habitaciones,
            LocalDate fecha, int indice) {

        if (indice >= habitaciones.size()) {
            return 0;
        }

        int cantidad = 0;

        if (estaDisponible(habitaciones.get(indice), fecha)) {
            cantidad = 1;
        }

        return cantidad + contarRecursivo(
                habitaciones, fecha, indice + 1);
    }
}
