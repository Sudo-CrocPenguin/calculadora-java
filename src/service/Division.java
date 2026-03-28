package service;

import model.Operacion;

public class Division implements Operacion {

    private double numero1;
    private double numero2;

    public Division(double numero1, double numero2) {

        this.numero1 = numero1;
        this.numero2 = numero2;

    }

    @Override
    public double ejecutar() {

        if (numero2 == 0) {

            throw new ArithmeticException("No se puede dividir por 0");

        } else {

            return numero1 / numero2;

        }

    }
}
