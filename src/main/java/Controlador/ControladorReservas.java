/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;
import Modelo.Huesped;
import Modelo.Habitacion;
import Modelo.Reserva;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author AsusVivobook
 */
public class ControladorReservas {

    private GestorHotel gestor;

    public ControladorReservas() {
        gestor = GestorHotel.getInstancia();
    }

    public String registrarReserva(Huesped huesped,
            Habitacion habitacion, int personas,
            LocalDate entrada, LocalDate salida) {

        if (huesped == null || habitacion == null) {
            return "Debe seleccionar un huésped y una habitación";
        }

        if (entrada == null || salida == null) {
            return "Debe ingresar las fechas";
        }

        if (entrada.isBefore(LocalDate.now())) {
            return "La fecha de entrada no puede ser anterior a hoy";
        }

        if (!salida.isAfter(entrada)) {
            return "La fecha de salida debe ser posterior a la entrada";
        }

        if (personas <= 0) {
            return "La cantidad de personas debe ser mayor que cero";
        }

        if (personas > habitacion.getCapacidad()) {
            return "La habitación no tiene capacidad suficiente";
        }

        List<Reserva> reservas = gestor.getReservas();

        for (Reserva r : reservas) {

            if (r.getHabitacion().getNumero() == habitacion.getNumero()
                    && (r.getEstado().equals("Confirmada")
                    || r.getEstado().equals("Hospedado"))) {

                boolean seSuperpone
                        = entrada.isBefore(r.getFechaSalida())
                        && salida.isAfter(r.getFechaEntrada());

                if (seSuperpone) {
                    return "La habitación no está disponible en esas fechas";
                }
            }
        }

        String codigo = "RES" + (reservas.size() + 1);

        Reserva reserva = new Reserva(
                codigo,
                huesped,
                habitacion,
                personas,
                entrada,
                salida,
                gestor.getRecepcionistaActivo()
        );

        if (gestor.agregarReserva(reserva)) {
            return "Reserva registrada correctamente. Código: " + codigo;
        }

        return "No se pudo registrar la reserva";
    }
}
