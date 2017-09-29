/**
 * $Id: AstrolabeGenerator.java,v 3.0
 * <p/>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2 of
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
 * Copyright (c) 2014, 2015 Timothy J. Mitchell
 */

package com.wymarc.astrolabe.math;

import java.awt.geom.Point2D;

/**
 * Given three points on a plain, this class will compute the common center and the radius
 * of the circle than is defined by those points
 *
 * Date:      1 October 2011
 * Author    Timothy J. Mitchell <wymarc@gmail.com> (Modified from other sources-see below)
 *
 * This is modification based loosely on the C example code circle3 routine by
 * James Morrison published in The Astrolabe
 *
 * Notes:
 *
 * There are two boundary conditions that must be dealt with:
 * 	1. If two of the points have the same y-axis co-ordinate (i.e. 12,7; 87,7)
 * this can cause a divide-by-zero error in the code
 *
 * 	2. If all three points lie on the same line, there is no circle that they define.
 *
 */
public class ThreePointCenter {

    private Point2D.Double center= new Point2D.Double(0,0);
    private double radius = 0;
    private Boolean isCircle = true;

    public Point2D.Double getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public Boolean isCircle() {
        return this.isCircle;
    }

    public ThreePointCenter(Point2D.Double point1, Point2D.Double point2, Point2D.Double point3){
        Point2D.Double pointZ;

        double temp1;
        double temp2;

        double deltaX1;
        double deltaY1;
        double deltaX2;
        double deltaY2;

        // first check to see if the three points are on the same line
        // Use two points to derive the equation of a line between those points
        // then see if the third point is on that line
        if((point1.x == point2.x && point2.x == point3.x)||(point1.y == point2.y && point2.y == point3.y))
        {
            //is a straight horz or vert line
            this.isCircle = false;
            return;
        }

        //Check to make sure point2.x does not equal point3.x. if equal swap out for point3
        if (point2.x == point1.x)
        {
            // swap
            if (point2.x != point3.x)
            {
                // swap point2 and point3
                pointZ = point3;
                point3 = point2;
                point2 = pointZ;
            }else
            {
                // swap point1 and point3
                pointZ = point3;
                point3 = point1;
                point1 = pointZ;
            }
        }

        // now test to see if point3 is on the same line as point1 and point2
        double slope = (point2.y - point1.y) / (point2.x - point1.x);
        double intercept = point1.y - (slope * point1.x);

        if(point3.y == (slope * point3.x) + intercept) //y = mx+b
        {
            //all three points on one line
            this.isCircle = false;
            return;
        }

        // next check to see if point2.y is the same as point1.y or if
        // point3.y is the same as point1.y if so there is a divide-by-zero
        // problem that can be solved by swapping points around.
        // we check above to see if the points were on a horz or vert line, so if
        // point2.y is the same as point1.y then point3.y can't be etc

        if (point2.y == point1.y)
        {
            // swap point1 and point3
            pointZ = point3;
            point3 = point1;
            point1 = pointZ;
        } else if (point3.y == point1.y)
        {
            // swap point1 and point2
            pointZ = point2;
            point2 = point1;
            point1 = pointZ;
        }

        // Find point equidistant from point1, point2 and point 3

        deltaX1 = point2.x - point1.x;
        deltaY1 = point2.y - point1.y;
        temp1 = ((point2.x * point2.x) - (point1.x * point1.x) + (point2.y * point2.y) - (point1.y * point1.y)) / 2.0;
        deltaX2 = point3.x - point1.x;
        deltaY2 = point3.y - point1.y;
        temp2 = ((point3.x * point3.x) - (point1.x * point1.x) + (point3.y * point3.y) - (point1.y * point1.y)) / 2.0;

        // solve for X
        this.center.x = ((temp1 / deltaY1) - (temp2 / deltaY2)) / ((deltaX1 / deltaY1) - (deltaX2 / deltaY2));

        // solve for Y
        this.center.y = (temp1 - (deltaX1 * this.center.x)) / deltaY1;

        this.radius = Math.sqrt(((this.center.x - point1.x) * (this.center.x - point1.x)) + ((this.center.y - point1.y) * (this.center.y - point1.y)));        
    }
}
