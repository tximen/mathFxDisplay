package com.txi.math.mathfxdisplay.function;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class FunctionPlotter extends Canvas {


    private double xMin = -10;
    private double xMax = 10;
    private double yMin = -10;
    private double yMax = 10;

    // Darstellungsoptionen
    private Color gridColor = Color.LIGHTGRAY;
    private Color axisColor = Color.BLACK;
    private Color functionColor = Color.BLUE;
    private double functionStrokeWidth = 2.5;
    private double stepSize = 0.01;
    private java.util.function.DoubleUnaryOperator function;

    public FunctionPlotter() {
        this.setWidth(600);
        this.setHeight(400);

        // Standardfunktion: f(x) = sin(x)
        this.function = Math::sin;

        // Neuzeichnen bei Größenänderung
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());

        draw();
    }

    // Setter für die Funktion (z.B. x -> x*x für x²)
    public void setFunction(java.util.function.DoubleUnaryOperator func) {
        this.function = func;
        draw();
    }

    // Bereich setzen und neu zeichnen
    public void setRange(double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        draw();
    }

    // Hauptzeichnen-Routine
    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        double width = getWidth();
        double height = getHeight();

System.err.println("width: " + width + " height: " + height);
        // Canvas löschen
        gc.clearRect(0, 0, width, height);

        // Koordinatensystem-Transformation berechnen
        // Pixel-Koordinaten: (0,0) ist oben-links
        // Mathematisch: (xMin, yMax) ist oben-links, (xMax, yMin) ist unten-rechts
        double scaleX = width / (xMax - xMin);
        double scaleY = height / (yMin - yMax); // Negativ wegen invertierter Y-Achse

        // Pixel-Koordinate zu Mathematik-Koordinate
        java.util.function.DoubleUnaryOperator toMathX = (px) -> xMin + (px / scaleX);
        java.util.function.DoubleUnaryOperator toMathY = (py) -> yMax + (py / scaleY); // Invertiert

        // --- 1. Gitter zeichnen ---
        drawGrid(gc, scaleX, scaleY);

        // --- 2. Achsen zeichnen ---
        drawAxes(gc, scaleX, scaleY);

        // --- 3. Funktion zeichnen ---
        drawFunction(gc, scaleX, scaleY);
    }


    private void drawGrid(GraphicsContext gc, double scaleX, double scaleY) {
        double width = getWidth();
        double height = getHeight();

        gc.setStroke(gridColor);
        gc.setLineWidth(0.5);

        // Vertikale Gitterlinien (ganzzahlige x-Werte)
        for (int x = (int) Math.ceil(xMin); x <= (int) Math.floor(xMax); x++) {
            if (x == 0) continue; // Achse wird separat gezeichnet
            double pixelX = (x - xMin) * scaleX;
            System.out.println("stroke " + pixelX);
            gc.strokeLine(pixelX, 0, pixelX, height);
        }

        // Horizontale Gitterlinien (ganzzahlige y-Werte)
        for (int y = (int) Math.ceil(yMin); y <= (int) Math.floor(yMax); y++) {
            if (y == 0) continue;
            double pixelY = (yMax - y) * scaleY; // Invertiert
            gc.strokeLine(0, pixelY, width, pixelY);
        }
    }

    private void drawAxes(GraphicsContext gc, double scaleX, double scaleY) {
        double width = getWidth();
        double height = getHeight();

        gc.setStroke(axisColor);
        gc.setLineWidth(1.5);

        // X-Achse (wenn sichtbar)
        if (yMin <= 0 && yMax >= 0) {
            double yZero = (yMax - 0) * scaleY;
            gc.strokeLine(0, yZero, width, yZero);

            // Pfeilspitze für X-Achse
            gc.strokeLine(width - 10, yZero - 5, width, yZero);
            gc.strokeLine(width - 10, yZero + 5, width, yZero);
        }

        // Y-Achse (wenn sichtbar)
        if (xMin <= 0 && xMax >= 0) {
            double xZero = (0 - xMin) * scaleX;
            gc.strokeLine(xZero, 0, xZero, height);

            // Pfeilspitze für Y-Achse
            gc.strokeLine(xZero - 5, 10, xZero, 0);
            gc.strokeLine(xZero + 5, 10, xZero, 0);
        }

        // Achsenbeschriftungen (vereinfacht)
        gc.setFill(axisColor);
        gc.setLineWidth(1);
        for (int x = (int) Math.ceil(xMin); x <= (int) Math.floor(xMax); x++) {
            if (x == 0) continue;
            double pixelX = (x - xMin) * scaleX;
            double pixelY = (yMax - 0) * scaleY;
            if (yMin <= 0 && yMax >= 0) {
                gc.fillText(String.valueOf(x), pixelX - 10, pixelY + 20);
            }
        }
        for (int y = (int) Math.ceil(yMin); y <= (int) Math.floor(yMax); y++) {
            if (y == 0) continue;
            double pixelX = (0 - xMin) * scaleX;
            double pixelY = (yMax - y) * scaleY;
            if (xMin <= 0 && xMax >= 0) {
                gc.fillText(String.valueOf(y), pixelX + 10, pixelY + 5);
            }
        }
    }

    private void drawFunction(GraphicsContext gc, double scaleX, double scaleY) {
        double width = getWidth();
        double height = getHeight();

        gc.setStroke(functionColor);
        gc.setLineWidth(functionStrokeWidth);

        // Kurve Punkt für Punkt zeichnen
        double previousX = Double.NaN;
        double previousY = Double.NaN;

        for (double px = 0; px <= width; px += stepSize * scaleX) { // Dynamische Schrittweite
            double mathX = xMin + (px / scaleX);
            double mathY;

            try {
                mathY = function.applyAsDouble(mathX);
            } catch (Exception e) {
                // Bei Fehlern (z.B. Division durch Null) den Punkt überspringen
                previousX = Double.NaN;
                previousY = Double.NaN;
                continue;
            }

            // Prüfen, ob der Wert im sichtbaren Bereich liegt
            if (mathY < yMin || mathY > yMax || Double.isNaN(mathY) || Double.isInfinite(mathY)) {
                previousX = Double.NaN;
                previousY = Double.NaN;
                continue;
            }

            double pixelX = px;
            double pixelY = (yMax - mathY) * scaleY;

            if (!Double.isNaN(previousX) && !Double.isNaN(previousY)) {
                // Linie zum vorherigen Punkt ziehen
                gc.strokeLine(previousX, previousY, pixelX, pixelY);
            }

            previousX = pixelX;
            previousY = pixelY;
        }
    }
}
