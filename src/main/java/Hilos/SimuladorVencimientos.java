/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Modelo.GestorHotel;
import Modelo.Reserva;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author AsusVivobook
 */
public class SimuladorVencimientos implements Runnable {

    private GestorHotel gestor;
    private Consumer<String> mostrarMensaje;
    private Consumer<int[]> actualizarDatos;

    public SimuladorVencimientos(
            Consumer<String> mensaje,
            Consumer<int[]> datos) {

        gestor = GestorHotel.getInstancia();
        mostrarMensaje = mensaje;
        actualizarDatos = datos;
    }

    @Override
    public void run() {

        enviarMensaje("Iniciando simulación de vencimientos...");

        for (int i = 1; i <= 5; i++) {

            LocalDate fechaSimulada = LocalDate.now().plusDays(i);

            int revisadas = 0;
            int vencidas = 0;
            int confirmadas = 0;

            synchronized (gestor) {

                List<Reserva> reservas = gestor.getReservas();

                for (Reserva r : reservas) {

                    if (r.getEstado().equals("Confirmada")) {

                        revisadas++;

                        if (r.getFechaEntrada().isBefore(fechaSimulada)) {
                            vencidas++;
                        } else {
                            confirmadas++;
                        }
                    }
                }
            }

            enviarMensaje("Día simulado " + i
                    + " - Fecha: " + fechaSimulada);

            enviarDatos(new int[]{
                revisadas,
                vencidas,
                confirmadas
            });

            enviarMensaje("Ciclo " + i + " completado");

            if (i < 5) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    enviarMensaje("Simulación interrumpida");
                    return;
                }
            }
        }

        enviarMensaje("Simulación finalizada. "
                + "Los estados reales no fueron modificados");
    }

    private void enviarMensaje(String mensaje) {
        if (mostrarMensaje != null) {
            mostrarMensaje.accept(mensaje);
        }
    }

    private void enviarDatos(int[] datos) {
        if (actualizarDatos != null) {
            actualizarDatos.accept(datos);
        }
    }
}
