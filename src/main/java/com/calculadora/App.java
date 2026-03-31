package com.calculadora;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Stack;

public class App extends Application {

    private String expresion = "";

    @Override
    public void start(Stage stage) {

        TextField pantalla = new TextField();
        pantalla.setEditable(false);
        pantalla.setPrefHeight(50);

        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);

        grid.add(pantalla, 0, 0, 5, 1);

        String[] botones = {
                "7", "8", "9", "/", "(",
                "4", "5", "6", "*", ")",
                "1", "2", "3", "-", "%",
                "0", ".", "=", "+", "C",
                "√", "^"
        };

        int fila = 1, col = 0;

        for (String texto : botones) {

            Button btn = new Button(texto);
            btn.setPrefSize(60, 60);

            btn.setOnAction(e -> manejarEntrada(texto, pantalla));

            grid.add(btn, col, fila);
            col++;

            if (col == 5) {
                col = 0;
                fila++;
            }
        }

        Scene scene = new Scene(grid, 320, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        // 🔥 TECLADO
        scene.setOnKeyPressed(e -> {

            KeyCode code = e.getCode();

            switch (code) {
                case DIGIT0:
                case NUMPAD0:
                    manejarEntrada("0", pantalla);
                    break;
                case DIGIT1:
                case NUMPAD1:
                    manejarEntrada("1", pantalla);
                    break;
                case DIGIT2:
                case NUMPAD2:
                    manejarEntrada("2", pantalla);
                    break;
                case DIGIT3:
                case NUMPAD3:
                    manejarEntrada("3", pantalla);
                    break;
                case DIGIT4:
                case NUMPAD4:
                    manejarEntrada("4", pantalla);
                    break;
                case DIGIT5:
                case NUMPAD5:
                    manejarEntrada("5", pantalla);
                    break;
                case DIGIT6:
                case NUMPAD6:
                    manejarEntrada("6", pantalla);
                    break;
                case DIGIT7:
                case NUMPAD7:
                    manejarEntrada("7", pantalla);
                    break;
                case DIGIT8:
                case NUMPAD8:
                    manejarEntrada("8", pantalla);
                    break;
                case DIGIT9:
                case NUMPAD9:
                    manejarEntrada("9", pantalla);
                    break;

                case ADD:
                    manejarEntrada("+", pantalla);
                    break;
                case SUBTRACT:
                    manejarEntrada("-", pantalla);
                    break;
                case MULTIPLY:
                    manejarEntrada("*", pantalla);
                    break;
                case DIVIDE:
                    manejarEntrada("/", pantalla);
                    break;

                case PERIOD:
                case DECIMAL:
                    manejarEntrada(".", pantalla);
                    break;

                case ENTER:
                    manejarEntrada("=", pantalla);
                    break;
                case BACK_SPACE:
                    if (!expresion.isEmpty()) {
                        expresion = expresion.substring(0, expresion.length() - 1);
                        pantalla.setText(expresion);
                    }
                    break;

                case ESCAPE:
                    expresion = "";
                    pantalla.clear();
                    break;

                case OPEN_BRACKET:
                    manejarEntrada("(", pantalla);
                    break;
                case CLOSE_BRACKET:
                    manejarEntrada(")", pantalla);
                    break;

                default:
                    break;
            }
        });

        stage.setTitle("Calculadora TechRunner");
        stage.setScene(scene);
        stage.show();

        pantalla.requestFocus();
    }

    private void manejarEntrada(String input, TextField pantalla) {

        switch (input) {

            case "C":
                expresion = "";
                pantalla.clear();
                break;

            case "=":
                try {
                    double res = evaluarExpresion(expresion);
                    pantalla.setText(String.valueOf(res));
                    expresion = String.valueOf(res);
                } catch (Exception e) {
                    pantalla.setText("Error");
                    expresion = "";
                }
                break;

            case "%":
                expresion += "/100";
                pantalla.setText(expresion);
                break;

            case "√":
                expresion += "sqrt(";
                pantalla.setText(expresion);
                break;

            case "^":
                expresion += "^";
                pantalla.setText(expresion);
                break;

            default:
                expresion += input;
                pantalla.setText(expresion);
        }
    }

    // 🔥 EVALUADOR REAL (con prioridad y paréntesis)
    private double evaluarExpresion(String exp) {

        exp = exp.replaceAll("sqrt", "√");

        Stack<Double> valores = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {

            char c = exp.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < exp.length() &&
                        (Character.isDigit(exp.charAt(i)) || exp.charAt(i) == '.')) {
                    sb.append(exp.charAt(i++));
                }
                i--;
                valores.push(Double.parseDouble(sb.toString()));
            }

            else if (c == '(') {
                ops.push(c);
            }

            else if (c == ')') {
                while (ops.peek() != '(') {
                    valores.push(aplicarOp(ops.pop(), valores.pop(), valores.pop()));
                }
                ops.pop();
            }

            else if (c == '√') {
                i++; // saltar (
                StringBuilder sb = new StringBuilder();
                while (exp.charAt(i) != ')') {
                    sb.append(exp.charAt(i++));
                }
                valores.push(Math.sqrt(Double.parseDouble(sb.toString())));
            }

            else if ("+-*/^".indexOf(c) != -1) {
                while (!ops.empty() && prioridad(c) <= prioridad(ops.peek())) {
                    valores.push(aplicarOp(ops.pop(), valores.pop(), valores.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.empty()) {
            valores.push(aplicarOp(ops.pop(), valores.pop(), valores.pop()));
        }

        return valores.pop();
    }

    private int prioridad(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return 0;
    }

    private double aplicarOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return b == 0 ? 0 : a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    public static void main(String[] args) {
        launch();
    }
}