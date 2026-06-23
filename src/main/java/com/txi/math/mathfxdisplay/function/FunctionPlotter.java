package com.txi.math.mathfxdisplay.function;

import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FunctionPlotter extends Canvas {
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionPlotter.class);

    private static final double HORIZONTAL_MARGIN = 50d;
    private static final double VERTICAL_MARGIN   = 50d;

    private PlotRange plotRange;

    // Darstellungsoptionen
 //   private Color gridColor = Color.LIGHTGRAY;
    private Color gridColor = Color.BLACK;
    private Color axisColor = Color.BLACK;
    private Color functionColor = Color.BLUE;
    private double functionStrokeWidth = 2.5;
    private double stepSize = 0.01;
    private java.util.function.DoubleUnaryOperator function;

    public FunctionPlotter() {
        this.plotRange =  new PlotRange(VERTICAL_MARGIN, HORIZONTAL_MARGIN, -10, 10, -2, 2);
     //   this.setWidth(600);
     //   this.setHeight(400);

        // Standardfunktion: f(x) = sin(x)
        this.function = Math::sin;

        widthProperty().addListener(this::widthOrHeightChanged);
        heightProperty().addListener(this::widthOrHeightChanged);
    }



    private void widthOrHeightChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        LOGGER.info("widthChanged: old {}  new {}", oldValue, newValue);
        draw();
    }


    // Setter für die Funktion (z.B. x -> x*x für x²)
    public void setFunction(java.util.function.DoubleUnaryOperator func) {
        this.function = func;
        draw();
    }



    // Hauptzeichnen-Routine
    public void draw() {
        GraphicsContext graphContext = getGraphicsContext2D();
        // clear convas
        graphContext.clearRect(0, 0, getWidth(), getHeight());

        drawGrid(graphContext);
        drawAxis(graphContext);
        drawFunction(graphContext);
    }


    private void drawGrid(GraphicsContext graphContext ) {
        graphContext.setStroke(gridColor);
        graphContext.setLineWidth(0.5);
        drawHorizontalY(graphContext);
        drawVerticalX(graphContext);
    }

    private void drawVerticalX(GraphicsContext graphContext) {
        double scaleX = this.plotRange.scaleX(getWidth());
        int startX = this.plotRange.startX();
        int endX = this.plotRange.endX();
        double lineEnd = this.plotRange.lineEndX(getHeight());
        for (int x = startX; x <= endX; x++) {
            if (x != 0) {
                double pixelX = this.plotRange.pixelX(scaleX, x);
                LOGGER.debug("vertical x: {} pixelX: {} lineEnd {}", x, pixelX, lineEnd);
                graphContext.strokeLine(pixelX, HORIZONTAL_MARGIN, pixelX, lineEnd);
            }
        }
    }

    /**
     * draw the hoirzontal lines
     * @param graphContext graphic context
     */
    private void drawHorizontalY(GraphicsContext graphContext) {
        double scaleY = this.plotRange.scaleY(getHeight());
        int startY = this.plotRange.startY();
        int endY =   this.plotRange.endY();
        double lineEnd =   this.plotRange.lineEndY(getWidth());
        for (int y = startY; y <= endY; y++) {
            if (y != 0) {
                double pixelY = this.plotRange.pixelY(scaleY, y);
                LOGGER.debug("horizontal y: {} pixelY: {} lineEnd {}", y, pixelY,  lineEnd);
                graphContext.strokeLine(VERTICAL_MARGIN, pixelY, lineEnd, pixelY);
            }
        }
    }

    private void drawAxis(GraphicsContext graphContext) {
        if (this.plotRange.isAxisVisible()) {
            graphContext.setStroke(axisColor);
            graphContext.setLineWidth(1.5);
            drawHorizontalAxis(graphContext);
            drawVerticalAxis(graphContext);
        }
    }

    private void drawHorizontalAxis(GraphicsContext graphContext) {
        if (this.plotRange.isHorizontalAxisVisible()) {
            double scaleX = this.plotRange.scaleX(getWidth());
            double xZero = this.plotRange.pixelX(scaleX, 0);
            double lineEnd = this.plotRange.lineEndX(getHeight());
            graphContext.strokeLine(xZero, HORIZONTAL_MARGIN, xZero, lineEnd);
            graphContext.strokeLine(xZero - 5, HORIZONTAL_MARGIN+10, xZero, HORIZONTAL_MARGIN);
            graphContext.strokeLine(xZero + 5, HORIZONTAL_MARGIN+10, xZero, HORIZONTAL_MARGIN);
        }
    }

    private void drawVerticalAxis(GraphicsContext graphContext) {
        if (this.plotRange.isVerticalAxisVisible()) {
            double scaleY = this.plotRange.scaleY(getHeight());
            double yZero = this.plotRange.pixelY(scaleY, 0);
            double lineEnd = this.plotRange.lineEndY(getWidth());
            graphContext.strokeLine(VERTICAL_MARGIN, yZero, lineEnd, yZero);
            graphContext.strokeLine(lineEnd - 10, yZero - 5, lineEnd, yZero);
            graphContext.strokeLine(lineEnd - 10, yZero + 5, lineEnd, yZero);
        }
    }



    private void drawFunction(GraphicsContext graphContext) {
        double scaleX = this.plotRange.scaleX(getWidth());
        double scaleY = this.plotRange.scaleY(getHeight());
        double stepSize = this.plotRange.stepSize();

        graphContext.setStroke(functionColor);
        graphContext.setLineWidth(functionStrokeWidth);

        double previousX = Double.NaN;
        double previousY = Double.NaN;
        for (double mathX = this.plotRange.xMin(); mathX <= this.plotRange.xMax(); mathX +=stepSize) {
            try {
                double pixelX = this.plotRange.pixelX(scaleX, mathX);
                double fctY = function.applyAsDouble(mathX);
                double pixelY = this.plotRange.pixelY(scaleY, fctY);
                if (!Double.isNaN(previousX) && !Double.isNaN(previousY)) {
                    graphContext.strokeLine(previousX, previousY, pixelX, pixelY);
                }
                previousX = pixelX;
                previousY = pixelY;
            } catch (Exception exception) {
                previousX = Double.NaN;
                previousY = Double.NaN;
                exception.printStackTrace();
            }
        }
        /*
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

         */
    }


}
