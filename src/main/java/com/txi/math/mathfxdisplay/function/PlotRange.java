package com.txi.math.mathfxdisplay.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record PlotRange(double verticalMargin, double horizontalMargin, double xMin, double xMax, double yMin, double yMax) {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlotRange.class);
    // lower offset
    private static final double OFFSET = 50d;

    public boolean isAxisVisible() {
        return isHorizontalAxisVisible() && isVerticalAxisVisible();
    }

    public boolean isHorizontalAxisVisible() {
       return xMin <= 0 && xMax >= 0;
    }

    public boolean isVerticalAxisVisible() {
        return yMin <= 0 && yMax >= 0;
    }

    public double lineEndX(double height) {
        return height - this.horizontalMargin;
    }


    public double lineEndY(double width) {
        return width - this.verticalMargin;
    }

    public double scaleX(double width) {
        double verticalScale = (width - verticalMargin - OFFSET) / (xMax - xMin);
        LOGGER.info("vertical margin {}: scale: {} width: {} xMax: {} xMin: {}", verticalMargin,  verticalScale,  width, xMax, xMin);
        return verticalScale;
    }

    public double scaleY(double height) {
        double horizontalScale = (height - horizontalMargin - OFFSET) / (yMax - yMin);
        LOGGER.info("horizontal margin {}: scale: {} height: {} yMax: {} yMin: {}", horizontalMargin,  horizontalScale,  height, yMax, yMin);
        return horizontalScale;
    }

    public int startX() {
        int start = (int) Math.ceil(xMin);
        LOGGER.info("startX {} xMin {}" , start, xMin);
        return start;
    }

    public int startY() {
        int start = (int) Math.ceil(yMin);
        LOGGER.info("startY {} yMin {}" , start, yMin);
        return start;
    }

    public double stepSize() {
        return (xMax - xMin) / 100d;
    }

    public int endX() {
        int end = (int) Math.ceil(xMax);
        LOGGER.info("endX {} xMax {}" , end, xMax);
        return end;
    }

    public int endY() {
        int end = (int) Math.floor(yMax);
        LOGGER.info("endY {} yMax {}" , end, yMax);
        return end;
    }




    public double pixelX(double scaleX, double x) {
        return verticalMargin + ((xMax - x) * scaleX);
    }

    public double pixelY(double scaleY, double y) {
       return horizontalMargin + ((yMax - y) * scaleY);
    }
}
