/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {
    // private Point[] pointsArray;
    int numberSgements;
    LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        validateInput(points);
        // // Exception1. points[] is a null
        // if (points == null)
        //     throw new IllegalArgumentException("The input Point[] array is null.");
        // // Exception2. points[] has a null point
        // for (Point point : points) {
        //     if (point == null)
        //         throw new IllegalArgumentException("The input Point[] array has a null point.");
        // }
        // // Exception3. points[] has 2 same point
        // for (int j = 0; j < points.length; j++) {
        //     for (int z = j; z < points.length; z++) {
        //         if (points[j].compareTo(points[z]) == 0)
        //             throw new IllegalArgumentException("The input contains repeated points");
        //     }
        // }

        // this.pointsArray = points;
        // Arrays.sort(pointsArray);
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point.SlopeOrderComparator originalPoint = new Point.SlopeOrderComparator(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    if (originalPoint.compare(points[j], points[k])
                            == 0) // 3 points are collinear
                    {
                        
                    }
                }
            }
        }
    }

    public int numberOfSegments() {       // the number of line segments
        return numberSgements;
    }

    public LineSegment[] segments() {               // the line segments

    }

    private void validateInput(Point[] points) {
        // Exception1. points[] is a null
        if (points == null)
            throw new IllegalArgumentException("The input Point[] array is null.");
        // Exception2. points[] has a null point
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("The input Point[] array has a null point.");
        }
        // Exception3. points[] has 2 same point
        for (int j = 0; j < points.length; j++) {
            for (int z = j; z < points.length; z++) {
                if (points[j].compareTo(points[z]) == 0)
                    throw new IllegalArgumentException("The input contains repeated points.");
            }
        }
    }

    public static void main(String[] args) {

    }
}
