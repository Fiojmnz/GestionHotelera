/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;
import Modelo.Habitacion;
import Modelo.HabitacionFactory;

/**
 *
 * @author AsusVivobook
 */
public class ControladorHabitaciones {

    private GestorHotel gestor;

    public ControladorHabitaciones() {
        gestor = GestorHotel.getInstancia();
    }

    public String registrarHabitacion(String numero, String tipo) {

        if (numero == null || numero.trim().isEmpty()) {
            return "Debe ingresar el número de habitación";
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            return "Debe seleccionar un tipo de habitación";
        }

        try {
            int num = Integer.parseInt(numero);

            if (num <= 0) {
                return "El número debe ser mayor que cero";
            }

            Habitacion habitacion
                    = HabitacionFactory.crearHabitacion(tipo, num);

            if (gestor.agregarHabitacion(habitacion)) {
                return "Habitación registrada correctamente";
            } else {
                return "El número de habitación ya existe";
            }

        } catch (NumberFormatException e) {
            return "El número de habitación debe ser válido";
        } catch (IllegalArgumentException e) {
            return "El tipo de habitación no es válido";
        }
    }
}
