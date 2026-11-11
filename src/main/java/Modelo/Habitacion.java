/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author AsusVivobook
 */
public abstract class Habitacion {

    protected int numero;

    public Habitacion(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public abstract int getCapacidad();

    public abstract long getTarifa();
}
