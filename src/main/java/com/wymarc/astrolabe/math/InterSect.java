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
 * Copyright (c) 2017 Timothy J. Mitchell
 */
package com.wymarc.astrolabe.math;

/**
 * Given two circles defined by center and radius, this class will return the intersection
 * points and intersection angles
 *
 * @author    Timothy J. Mitchell <wymarc@hotmail.com> (Modified from other sources-see below)
 *
 * link      http://astrolabeproject.com
 * link      http://www.astrolabes.org
 *
 *
 * version   3.0
 *
 * This is a mashup of the following two routines with some of my own additions:
 *   Some code ported from C xsect routine by James Morrison published in The Astrolabe: page 378-380
 *   Most code ported from C circle_circle_intersection routine by Tim Voght
 *   And then the lot ported back to Java.
 */
public class InterSect {

    private double xi;
    private double yi;
    private double xi_prime;
    private double yi_prime;
    private double angle1;
    private double angle2;
    private Boolean intersection = false;

    public double getXi() {
        return xi;
    }

    public double getYi() {
        return yi;
    }

    public double getXi_prime() {
        return xi_prime;
    }

    public double getYi_prime() {
        return yi_prime;
    }

    public double getAngle1() {
        return Math.toDegrees(angle1);
    }

    public double getAngle2() {
        return Math.toDegrees(angle2);
    }

    public Boolean getIntersection() {
        return intersection;
    }

    private double polar ( double num, double den ){
        // Polar Arctangent
        double ang = Math.atan2( num , den );
        if (ang < 0.0)
        {
            ang = ang + 2.0 * Math.PI;
        }

        return ang;
    }

    /**
     * Computes Intersection of two circles
     *
     * since   0.1
     *
     * param x0 drawing circle center X
     * param y0 drawing circle center Y
     * param r0 drawing circle radius
     *
     * param x1 clipping circle center X
     * param y1 clipping circle center Y
     * param r1 clipping circle radius
     *
     */
    public InterSect(double x0, double y0, double r0, double x1, double y1, double r1)
    {
        double a;
        double dx;
        double dy;
        double d;
        double h;
        double rx;
        double ry;
        double x2;
        double y2;
        double A1;
        double A2;
        double xi1;
        double yi1;
        double xi_prime1;
        double yi_prime1;

        /* dx and dy are the vertical and horizontal distances between
         * the circle centers.
         */
        dx = x1 - x0;
        dy = y1 - y0;

        /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy*dy) + (dx*dx));
        //d = hypot(dx,dy); // Suggested by Keith Briggs

        /* Check for solvability. */
        if (d > (r0 + r1))
        {
            /* no solution. circles do not intersect. */
            this.intersection = false;
            return;
        }
        if (d < Math.abs(r0 - r1))
        {
            /* no solution. one circle is contained in the other */
            this.intersection = false;
            return;
        }

        /* 'point 2' is the point where the line through the circle
          * intersection points crosses the line between the circle
          * centers.
          */

        /* Determine the distance from point 0 to point 2. */
        a = ((r0*r0) - (r1*r1) + (d*d)) / (2.0 * d) ;

        /* Determine the coordinates of point 2. */
        x2 = x0 + (dx * a/d);
        y2 = y0 + (dy * a/d);

        /* Determine the distance from point 2 to either of the
          * intersection points.
          */
        h = Math.sqrt((r0*r0) - (a*a));

        /* Now determine the offsets of the intersection points from
          * point 2.
          */
        rx = -dy * (h/d);
        ry = dx * (h/d);

        /* Determine the absolute intersection points. */
        this.xi = x2 + rx;
        this.xi_prime = x2 - rx;
        this.yi = y2 + ry;
        this.yi_prime = y2 - ry;

        // Calculate intersection angles
        // first determine the intersections from the center of the circle to be drawn
        xi1 = this.xi-x0;
        xi_prime1 = this.xi_prime-x0;
        yi1 = this.yi-y0;
        yi_prime1 = this.yi_prime-y0;

        if ( xi1 != 0.0 )
        { //Calculate first angle
            A1 = polar( yi1 , xi1) ;
        }
        else
        {
            if ( yi1 > 0.0 )
            {
                A1 = (Math.PI/2.0); //x1 = 0 => a = 90 or 270
            }
            else
            {
                A1 = 3.0 * Math.PI / 2.0 ;
            }
        }

        if ( xi_prime1 != 0.0)
        {//Calculate second angle
            A2 = polar( yi_prime1 , xi_prime1 ) ;
        }
        else
        {
            if (yi_prime1 > 0.0) A2 = Math.PI / 2.0 ;
            else A2 = 3.0 * Math.PI / 2.0 ;
        }

        // Store returned values        //TODO: WTF
        this.angle1 = A1;
        x1 = this.xi;
        y1 = this.yi;
        this.angle2 = A2;
        x2 = this.xi_prime;
        y2 = this.yi_prime;

        // Sort returned values
        if (this.angle1 > this.angle2)
        {
            //swap (angle1,angle2) ;
            double hold;
            hold = this.angle1;
            this.angle1=this.angle2;
            this.angle2=hold;
            //swap (x1,x2) ;
            hold = x1;
            x1=x2;
            x2=hold;
            //swap (y1,y2) ;
            hold = y1;
            y1=y2;
            y2=hold;
        }
        //System.out.println("Angle 1 = " + Math.toDegrees(angle1));
        //System.out.println("Angle 2 = " + Math.toDegrees(angle2));
        this.intersection = true; //Return shows there is an intersection
    }
}



