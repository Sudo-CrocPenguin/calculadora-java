package com.calculadora;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class App extends Application {

    private String expresion = "";
    private List<String> historial = new ArrayList<>();
    private boolean mostrarHistorial = false;

    @Override
    public void start(Stage stage) {

        TextField pantalla = new TextField();
        pantalla.setEditable(false);
        pantalla.getStyleClass().add("pantalla");

        ListView<String> listaHistorial = new ListView<>();
        listaHistorial.setVisible(false);
        listaHistorial.setManaged(false);

        Button btnHistorial = new Button("⏱");
        btnHistorial.getStyleClass().add("boton");

        btnHistorial.setOnAction(e -> {
            mostrarHistorial = !mostrarHistorial;
            listaHistorial.setVisible(mostrarHistorial);
            listaHistorial.setManaged(mostrarHistorial);
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int i = 0; i < 5; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(20);
            col.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(col);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / 6);
            row.setVgrow(Priority.ALWAYS);
            grid.getRowConstraints().add(row);
        }

        HBox topBar = new HBox(5, pantalla, btnHistorial);
        HBox.setHgrow(pantalla, Priority.ALWAYS);

        grid.add(topBar, 0, 0, 5, 1);

        String[][] layout = {
                { "7", "8", "9", "/", "(" },
                { "4", "5", "6", "*", ")" },
                { "1", "2", "3", "-", "%" },
                { "0", ".", "=", "+", "C" },
                { "√", "^", "", "", "" }
        };

        for (int fila = 0; fila < layout.length; fila++) {
            for (int col = 0; col < layout[fila].length; col++) {

                String texto = layout[fila][col];
                if (texto.isEmpty())
                    continue;

                Button btn = crearBoton(texto, pantalla, listaHistorial);

                grid.add(btn, col, fila + 1);
            }
        }

        HBox root = new HBox(10, grid, listaHistorial);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        grid.prefWidthProperty().bind(scene.widthProperty().multiply(0.7));
        grid.prefHeightProperty().bind(scene.heightProperty());

        scene.setOnKeyPressed(e -> manejarTeclado(e.getCode(), pantalla, listaHistorial));

        stage.setTitle("Calculadora Cyberpunk");
        stage.setScene(scene);
        stage.show();

        pantalla.requestFocus();
    }

    private Button crearBoton(String texto, TextField pantalla, ListView<String> listaHistorial) {

        Button btn = new Button(texto);

        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btn.getStyleClass().add("boton");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-effect: dropshadow(gaussian, #39ff14, 20, 0.6, 0, 0);"));
        btn.setOnMouseExited(e -> btn.setStyle(""));

        btn.setOnAction(e -> manejarEntrada(texto, pantalla, listaHistorial));

        return btn;
    }

    private void manejarEntrada(String input, TextField pantalla, ListView<String> listaHistorial) {

        switch (input) {

            case "C":
                expresion = "";
                pantalla.clear();
                break;

            case "=":
                try {
                    double res = evaluarExpresion(expresion);
                    String resultado = expresion + " = " + res;

                    historial.add(resultado);
                    listaHistorial.getItems().add(resultado);

                    pantalla.setText(String.valueOf(res));
                    expresion = String.valueOf(res);

                } catch (Exception e) {
                    pantalla.setText("Error");
                    expresion = "";
                }
                break;

            case "%":
                expresion += "/100";
                break;

            case "√":
                expresion += "√(";
                break;

            default:
                expresion += input;
        }

        pantalla.setText(expresion);
    }

    private void manejarTeclado(KeyCode code, TextField pantalla, ListView<String> listaHistorial) {

        switch (code) {

            case DIGIT0:
            case NUMPAD0:
                manejarEntrada("0", pantalla, listaHistorial);
                break;
            case DIGIT1:
            case NUMPAD1:
                manejarEntrada("1", pantalla, listaHistorial);
                break;
            case DIGIT2:
            case NUMPAD2:
                manejarEntrada("2", pantalla, listaHistorial);
                break;
            case DIGIT3:
            case NUMPAD3:
                manejarEntrada("3", pantalla, listaHistorial);
                break;
            case DIGIT4:
            case NUMPAD4:
                manejarEntrada("4", pantalla, listaHistorial);
                break;
            case DIGIT5:
            case NUMPAD5:
                manejarEntrada("5", pantalla, listaHistorial);
                break;
            case DIGIT6:
            case NUMPAD6:
                manejarEntrada("6", pantalla, listaHistorial);
                break;
            case DIGIT7:
            case NUMPAD7:
                manejarEntrada("7", pantalla, listaHistorial);
                break;
            case DIGIT8:
            case NUMPAD8:
                manejarEntrada("8", pantalla, listaHistorial);
                break;
            case DIGIT9:
            case NUMPAD9:
                manejarEntrada("9", pantalla, listaHistorial);
                break;

            case ADD:
                manejarEntrada("+", pantalla, listaHistorial);
                break;
            case SUBTRACT:
                manejarEntrada("-", pantalla, listaHistorial);
                break;
            case MULTIPLY:
                manejarEntrada("*", pantalla, listaHistorial);
                break;
            case DIVIDE:
                manejarEntrada("/", pantalla, listaHistorial);
                break;

            case ENTER:
                manejarEntrada("=", pantalla, listaHistorial);
                break;

            case BACK_SPACE:
                if (!expresion.isEmpty()) {
                    expresion = expresion.substring(0, expresion.length() - 1);
                    pantalla.setText(expresion);
                }
                break;

            case ESCAPE:
                manejarEntrada("C", pantalla, listaHistorial);
                break;
        }
    }

    private double evaluarExpresion(String exp) {

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

            else if (c == '(')
                ops.push(c);

            else if (c == ')') {
                while (ops.peek() != '(')
                    valores.push(aplicarOp(ops.pop(), valores.pop(), valores.pop()));
                ops.pop();
            }

            else if (c == '√') {
                i++;
                StringBuilder sb = new StringBuilder();
                while (exp.charAt(i) != ')')
                    sb.append(exp.charAt(i++));
                valores.push(Math.sqrt(Double.parseDouble(sb.toString())));
            }

            else if ("+-*/^".indexOf(c) != -1) {
                while (!ops.isEmpty() && prioridad(c) <= prioridad(ops.peek()))
                    valores.push(aplicarOp(ops.pop(), valores.pop(), valores.pop()));
                ops.push(c);
            }
        }

        while (!ops.isEmpty())
            valores.push(aplicarOp(ops.pop(), valores.pop(), valores.pop()));

        return valores.pop();
    }

    private int prioridad(char op) {
        if (op == '+' || op == '-')
            return 1;
        if (op == '*' || op == '/')
            return 2;
        if (op == '^')
            return 3;
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
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    public static void main(String[] args) {
        launch();
    }
}