package service;

import model.Operacion;

public class Raices implements Operacion {

    public double numero1;

    public Raices(double numero1) {

        this.numero1 = numero1;

    }

    @Override
    public double ejecutar() {

        return Math.sqrt(numero1);

    }

}