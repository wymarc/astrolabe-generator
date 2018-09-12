/**
 * $Id: AstrolabeGenerator.java,v 3.1
 * <p/>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or(at your option) any later version.
 * <p/>
 * The Astrolabe Generator is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * <p/>
 * Copyright (c) 2018 Timothy J. Mitchell
 */
package com.wymarc.astrolabe.math;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given a circle defined by center and radius, and a line segment defined by its end points
 * this class will return the intersection points and intersection angles
 *
 * @author    Timothy J. Mitchell (wymarc@hotmail.com) (Modified from other sources-see below)
 *
 * link      http://astrolabeproject.com
 *
 * Modified from https://stackoverflow.com/questions/13053061/circle-line-intersection-points
 */
public class LineCircleIntersect {

    /**
     * Given a circle defined by center and radius, and a line segment defined by its end points
     * this will return the intersection points
     * @param pointA One endpoint of the line segment (Point2D)
     * @param pointB The other endpoint (Point2D)
     * @param center The center of the circle (Point2D)
     * @param radius The radius of the circle
     * @return A list containing the points of intersection
     */
    public static List<Point2D> getCircleLineIntersectionPoints(Point2D pointA,
                                                                Point2D pointB, Point2D center, double radius) {
        double baX = pointB.getX() - pointA.getX();
        double baY = pointB.getY() - pointA.getY();
        double caX = center.getX() - pointA.getX();
        double caY = center.getY() - pointA.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point2D p1 = new Point2D.Double(pointA.getX() - baX * abScalingFactor1, pointA.getY()
                - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Point2D p2 = new Point2D.Double(pointA.getX() - baX * abScalingFactor2, pointA.getY()
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    /**
     * Given a circle defined by center and radius, and a line segment defined by its end points
     * this will return the angles of any intersection points
     * @param pointA One endpoint of the line segment (Point2D)
     * @param pointB The other endpoint (Point2D)
     * @param center The center of the circle (Point2D)
     * @param radius The radius of the circle
     * @return A list containing the angles of intersection in degrees
     */
    public static List<Double> getCircleLineIntersectionAngles(Point2D pointA, Point2D pointB,
                                                               Point2D center, double radius){
        List<Double> angles = new ArrayList<>();
        // Get the intersection points
        List<Point2D> points = getCircleLineIntersectionPoints(pointA, pointB, center, radius);

        // There are three possible results:
        //      The line segment does not intersect (no points)
        //      The line intersects once (one point)
        //      The line intersects twice (two points)

        if (points == null){
            return angles;
        }else{
            for (Point2D point : points){
                angles.add(Math.toDegrees(AstroMath.cartesianToPolar(point)));
            }
        }
        return angles;
    }

}
