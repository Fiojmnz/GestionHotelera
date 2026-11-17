/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionhotelera;

import Vista.FrmLogin;

/**
 *
 * @author HP I3
 */
public class GestionHotelera {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });
    }
}
