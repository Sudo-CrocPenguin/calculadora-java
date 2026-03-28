package controller;

import model.Operacion;

public class CalculadoraController {

    public double calcular(Operacion operacion) {
        return operacion.ejecutar();
    }

}
