package com.calculadora.controller;

import com.calculadora.model.operaciones.*;

public class CalculadoraController {

    public String calcular(String op, String n1, String n2) {

        try {
            double a = Double.parseDouble(n1);
            double b = n2.isEmpty() ? 0 : Double.parseDouble(n2);

            Operacion operacion;

            switch (op) {
                case "+":
                    operacion = new Suma();
                    break;
                case "-":
                    operacion = new Resta();
                    break;
                case "*":
                    operacion = new Multiplicacion();
                    break;
                case "/":
                    operacion = new Division();
                    break;
                case "^":
                    operacion = new Potencia();
                    break;
                case "√":
                    operacion = new Raiz();
                    break;
                default:
                    return "Error";
            }

            return String.valueOf(operacion.calcular(a, b));

        } catch (Exception e) {
            return "Error";
        }
    }
}