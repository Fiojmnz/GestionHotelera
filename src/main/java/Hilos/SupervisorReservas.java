/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hilos;

import Modelo.GestorHotel;
import Modelo.Reserva;
import java.time.LocalDate;
import java.util.function.Consumer;

/**
 *
 * @author AsusVivobook
 */
public class SupervisorReservas implements Runnable {

    private GestorHotel gestor;
    private Consumer<String> mostrarMensaje;
    private Consumer<int[]> actualizarDatos;

    public SupervisorReservas() {
        gestor = GestorHotel.getInstancia();
    }

    public SupervisorReservas(Consumer<String> mensaje,
            Consumer<int[]> datos) {

        gestor = GestorHotel.getInstancia();
        mostrarMensaje = mensaje;
        actualizarDatos = datos;
    }

    @Override
    public void run() {

        enviarMensaje("Supervisor de reservas iniciado");

        for (int i = 1; i <= 5; i++) {

            int revisadas;
            int vencidas = 0;
            int confirmadas = 0;

            synchronized (gestor) {

                java.util.List<Reserva> reservas = gestor.getReservas();

                revisadas = reservas.size();

                for (Reserva r : reservas) {

                    if (r.getEstado().equals("Confirmada")
                            && r.getFechaEntrada().isBefore(LocalDate.now())) {

                        r.setEstado("Vencida");
                        vencidas++;

                        enviarMensaje("Reserva "
                                + r.getCodigo() + " vencida");
                    }

                    if (r.getEstado().equals("Vencida")) {
                        // Contabilizar reservas vencidas
                    }

                    if (r.getEstado().equals("Confirmada")) {
                        confirmadas++;
                    }
                }

                vencidas = 0;

                for (Reserva r : reservas) {
                    if (r.getEstado().equals("Vencida")) {
                        vencidas++;
                    }
                }
            }

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
                    enviarMensaje("Supervisor interrumpido");
                    return;
                }
            }
        }

        enviarMensaje("Supervisor de reservas finalizado");
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
