package service;

import model.Operacion;

public class Resta implements Operacion {

    private double numero1;
    private double numero2;

    public Resta(double numero1, double numero2) {

        this.numero1 = numero1;
        this.numero2 = numero2;

    }

    @Override
    public double ejecutar() {

        return numero1 - numero2;

    }

}
