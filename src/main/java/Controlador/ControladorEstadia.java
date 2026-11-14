/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;
import Modelo.Reserva;
import java.time.LocalDate;

/**
 *
 * @author AsusVivobook
 */
public class ControladorEstadia {

    private GestorHotel gestor;

    public ControladorEstadia() {
        gestor = GestorHotel.getInstancia();
    }

    public String realizarCheckIn(String codigo) {

        for (Reserva r : gestor.getReservas()) {

            if (r.getCodigo().equals(codigo)) {

                if (!r.getEstado().equals("Confirmada")) {
                    return "La reserva no está confirmada";
                }

                if (!r.getFechaEntrada().equals(LocalDate.now())) {
                    return "El check-in solo se permite en la fecha de entrada";
                }

                r.setEstado("Hospedado");

                return "Check-in realizado correctamente";
            }
        }

        return "No se encontró la reserva";
    }

    public String realizarCheckOut(String codigo) {

        for (Reserva r : gestor.getReservas()) {

            if (r.getCodigo().equals(codigo)) {

                if (!r.getEstado().equals("Hospedado")) {
                    return "La reserva no se encuentra hospedada";
                }

                r.setFechaSalidaReal(LocalDate.now());
                r.setEstado("Finalizada");

                return "Check-out realizado correctamente";
            }
        }

        return "No se encontró la reserva";
    }
}
