/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;
import Modelo.Huesped;

/**
 *
 * @author AsusVivobook
 */
public class ControladorHuespedes {

    private GestorHotel gestor;

    public ControladorHuespedes() {
        gestor = GestorHotel.getInstancia();
    }

    public String registrarHuesped(String cedula, String nombre,
            String telefono, String correo) {

        if (cedula == null || cedula.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || telefono == null || telefono.trim().isEmpty()
                || correo == null || correo.trim().isEmpty()) {

            return "Todos los campos son obligatorios";
        }

        int cantidadArrobas = correo.length() - correo.replace("@", "").length();

        if (cantidadArrobas != 1
                || correo.startsWith("@")
                || correo.endsWith("@")
                || !correo.substring(correo.indexOf("@") + 1).contains(".")) {

            return "El correo electrónico no es válido";
        }

        Huesped huesped = new Huesped(
                cedula, nombre, telefono, correo);

        if (gestor.agregarHuesped(huesped)) {
            return "Huésped registrado correctamente";
        } else {
            return "La identificación ya está registrada";
        }
    }
}
