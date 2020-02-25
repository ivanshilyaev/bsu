package by.bsu.coordinates;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class PascalStroke implements Stroke {
    BasicStroke stroke;

    public PascalStroke(float width) {
        this.stroke = new BasicStroke(width);
    }

    @Override
    public Shape createStrokedShape(Shape shape) {
        GeneralPath newShape = new GeneralPath();

        float[] coordinates = new float[2];
        float[] prevCoordinates = new float[2];
        for (PathIterator i = shape.getPathIterator(null); !i.isDone(); i.next()) {
            int type = i.currentSegment(coordinates);
            switch (type) {
                case PathIterator.SEG_MOVETO: {
                    newShape.moveTo(coordinates[0], coordinates[1]);
                    break;
                }
                case PathIterator.SEG_LINETO: {
                    double x1 = prevCoordinates[0];
                    double y1 = prevCoordinates[1];
                    double x2 = coordinates[0];
                    double y2 = coordinates[1];

                    double dx = x2 - x1;
                    double dy = y2 - y1;

                    double x3 = x2 - dx / 2;
                    double y3 = y2 - dy / 2;

                    newShape.lineTo(x1 + dy, y1 - dx);
                    newShape.lineTo(x3 + dy, y3 - dx);
                    newShape.lineTo(x3, y3);
                    newShape.lineTo(x2, y2);

                    break;
                }
                default: {
                    break;
                }
            }
            prevCoordinates[0] = coordinates[0];
            prevCoordinates[1] = coordinates[1];
        }

        return stroke.createStrokedShape(newShape);
    }
}
