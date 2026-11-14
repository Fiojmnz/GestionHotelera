/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GestorHotel;

/**
 *
 * @author AsusVivobook
 */
public class ControladorLogin {

    private GestorHotel gestor;

    public ControladorLogin() {
        gestor = GestorHotel.getInstancia();
    }

    public boolean iniciarSesion(String usuario, String contrasena) {

        if (usuario == null || contrasena == null) {
            return false;
        }

        if (usuario.equals("recepcion1")
                && contrasena.equals("Hotel123")) {

            gestor.setRecepcionistaActivo(usuario);
            return true;
        }

        if (usuario.equals("recepcion2")
                && contrasena.equals("Hotel456")) {

            gestor.setRecepcionistaActivo(usuario);
            return true;
        }

        return false;
    }

    public void cerrarSesion() {
        gestor.setRecepcionistaActivo(null);
    }
}
