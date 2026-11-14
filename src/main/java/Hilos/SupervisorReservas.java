/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Modelo.GestorHotel;
import Modelo.Reserva;
import java.time.LocalDate;

/**
 *
 * @author AsusVivobook
 */
public class SupervisorReservas implements Runnable {

    private GestorHotel gestor;

    public SupervisorReservas() {
        gestor = GestorHotel.getInstancia();
    }

    @Override
    public void run() {

        System.out.println("Supervisor de reservas iniciado");

        for (int i = 1; i <= 5; i++) {

            synchronized (gestor) {

                for (Reserva r : gestor.getReservas()) {

                    if (r.getEstado().equals("Confirmada")
                            && r.getFechaEntrada().isBefore(LocalDate.now())) {

                        r.setEstado("Vencida");

                        System.out.println(
                                "Reserva " + r.getCodigo() + " vencida");
                    }
                }
            }

            System.out.println("Supervisor: ciclo " + i + " completado");

            if (i < 5) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Supervisor interrumpido");
                    return;
                }
            }
        }

        System.out.println("Supervisor de reservas finalizado");
    }
}