package service;

import model.Operacion;

public class Multiplicacion implements Operacion {

    public double numero1;
    public double numero2;

    public Multiplicacion(double numero1, double numero2) {

        this.numero1 = numero1;
        this.numero2 = numero2;

    }

    @Override
    public double ejecutar() {

        return numero1 * numero2;

    }

}