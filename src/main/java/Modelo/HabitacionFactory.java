/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author AsusVivobook
 */
public class HabitacionFactory {

    public static Habitacion crearHabitacion(String tipo, int numero) {

        switch (tipo.toLowerCase()) {

            case "individual":
                return new HabitacionIndividual(numero);

            case "doble":
                return new HabitacionDoble(numero);

            case "familiar":
                return new HabitacionFamiliar(numero);

            default:
                throw new IllegalArgumentException("Tipo de habitación no válido");
        }
    }
}
