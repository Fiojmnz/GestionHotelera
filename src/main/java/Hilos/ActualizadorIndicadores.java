/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Modelo.GestorHotel;
import Modelo.Habitacion;
import Modelo.Reserva;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author AsusVivobook
 */
public class ActualizadorIndicadores implements Runnable {

    private GestorHotel gestor;

    public ActualizadorIndicadores() {
        gestor = GestorHotel.getInstancia();
    }

    @Override
    public void run() {

        System.out.println("Actualizador de indicadores iniciado");

        for (int i = 1; i <= 5; i++) {

            synchronized (gestor) {

                List<Habitacion> habitaciones = gestor.getHabitaciones();
                List<Reserva> reservas = gestor.getReservas();

                int ocupadas = 0;
                int confirmadas = 0;
                int vencidas = 0;

                for (Habitacion h : habitaciones) {

                    for (Reserva r : reservas) {

                        if (r.getHabitacion().getNumero() == h.getNumero()
                                && r.getEstado().equals("Hospedado")) {
                            ocupadas++;
                            break;
                        }
                    }
                }

                for (Reserva r : reservas) {

                    if (r.getEstado().equals("Confirmada")) {
                        confirmadas++;
                    }

                    if (r.getEstado().equals("Vencida")) {
                        vencidas++;
                    }
                }

                long ingresos = calcularIngresos(reservas, 0);

                int desocupadas = habitaciones.size() - ocupadas;

                System.out.println("----- INDICADORES DEL HOTEL -----");
                System.out.println("Total de habitaciones: " + habitaciones.size());
                System.out.println("Habitaciones ocupadas: " + ocupadas);
                System.out.println("Habitaciones desocupadas: " + desocupadas);
                System.out.println("Reservas confirmadas: " + confirmadas);
                System.out.println("Reservas vencidas: " + vencidas);
                System.out.println("Ingresos esperados: ₡" + ingresos);
            }

            System.out.println("Indicadores: ciclo " + i + " completado");

            if (i < 5) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Actualizador interrumpido");
                    return;
                }
            }
        }

        System.out.println("Actualizador de indicadores finalizado");
    }

    private long calcularIngresos(List<Reserva> reservas, int indice) {

        if (indice >= reservas.size()) {
            return 0;
        }

        long monto = 0;

        if (reservas.get(indice).getEstado().equals("Confirmada")) {
            monto = reservas.get(indice).getMontoTotal();
        }

        return monto + calcularIngresos(reservas, indice + 1);
    }
}
