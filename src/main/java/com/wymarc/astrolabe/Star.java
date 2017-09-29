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

package com.wymarc.astrolabe;

import java.awt.geom.Point2D;

/**
 * provides a source of star position data * 
 */
public class Star {
    public String name = "";
    public double ra = 0.0;
    public double dec = 0.0;
    public Point2D.Double base1 = new Point2D.Double();
    public Point2D.Double base2 = new Point2D.Double();
    public Point2D.Double labelPos = new Point2D.Double();
    public double labelOrient = 0.0;


    /**
     * Generates an array of Star objects
     * A Star object consists of:
     *      name (String) The star's display name
     *      ra (double) right ascension
     *      dec (double) declination
     *	    base1 (Point2D.Double) position one base point of the pointer to the star
     *	    base2 (Point2D.Double) position for the other base point
     *	    labelPos (Point2D.Double) position for the label
     *      labelOrient (double) label orientation  
     * 
     * @return Filled array of Star objects
     */
    public static Star[] getStarsList(String hemiSphere){

        Star[] starsList = new Star[17];

        if (hemiSphere.equals("S")){
            // show southern star set - Note Declination is reversed for the southern projection
            starsList[0] = new Star("Alpha Centaurus",14.75055556,-60.85277778,20,-10,10,-15,25,-7.5,0);
            starsList[1] = new Star("Canopus",6.542222222,-52.80555556,5,55,10,55,7.5,52.5,90);
            starsList[2] = new Star("Achernar",1.735833333,-57.26666667,-20,5,-15,15,-50,-7.5,0);
            starsList[3] = new Star("Altair",19.84636111,8.868333333,-60,-125,-70,-120,-68,-132,55);
            starsList[4] = new Star("Hamal",2.119527778,23.4625,0,0,0,0,-172,113,52);
            starsList[5] = new Star("Arcturus",14.261,19.1825,155,-95,160,-100,150,-90,-35);
            starsList[6] = new Star("Sirius",6.752472222,-16.71611111,5,100,5,90,7.5,90,90);
            starsList[7] = new Star("Procyon",7.655027778,5.225,62,125,57,125,50,127.5,-30);
            starsList[8] = new Star("Regulus",10.1395,11.96722222,0,0,0,0,130,85,-85);
            starsList[9] = new Star("Betelgeuse",5.919527778,7.406944444,-5,140,0,140,0,135,-5);
            starsList[10] = new Star("Rigel",5.242277778,-8.201666667,5,120,5,110,-5,111.5,0);
            starsList[11] = new Star("Bellatrix",5.418833333,6.349722222,-17,135,-25,135,-40,130.5,10);
            starsList[12] = new Star("Markab",23.07936111,15.20525,-200,-55,-195,-65,-202,-63,30);
            starsList[13] = new Star("Aldebaran",4.598666667,16.50916667,0,0,0,0,-87.5,147.5,40);
            starsList[14] = new Star("Spica",13.41986111,-11.16138889,0,0,0,0,80,-40,0);
            starsList[15] = new Star("Aliphard",9.459788889,-8.658602778,125,95,120,80,110,79,30);
            starsList[16] = new Star("Rosalhague",17.58225,12.56,5,-167,5,-160,2.5,-150,-90);

        }else{
            //show northern star set
            starsList[0] = new Star("Altair",19.84636111,8.868333333,-70,115,-60,125,-67.5,120,-59);
            starsList[1] = new Star("Capella",5.278138889,45.99805556,5,-50,5,-60,7,-60,90);
            starsList[2] = new Star("Arcturus",14.261,19.1825,120,20,130,40,93,43,-20);
            starsList[3] = new Star("Sirius",6.752472222,-16.71611111,10,-178,10,-170,3,-176,0);
            starsList[4] = new Star("Procyon",7.655027778,5.225,55,-125,65,-120,50,-130,30);
            starsList[5] = new Star("Deneb",20.6905,45.28027778,-5,35,-5,50,-18,40,0);
            starsList[6] = new Star("Vega",18.61561111,38.78361111,5,90,5,70,7,60,90);
            starsList[7] = new Star("Betelg",5.919527778,7.406944444,0,0,0,0,-3,-109,90);
            starsList[8] = new Star("Rigel",5.242277778,-8.201666667,-20,-135,-30,-135,-23,-137,-4);
            starsList[9] = new Star("Bellatrix",5.418833333,6.349722222,-30,-135,-45,-130,-55,-127,-18);
            starsList[10] = new Star("Markab",23.07936111,15.20525,-105,5,-95,5,-110,2,0);
            starsList[11] = new Star("Aldeb",4.598666667,16.50916667,-5,-100,-5,-115,-25,-98,-40);
            starsList[12] = new Star("Spica",13.41986111,-11.16138889,130,70,130,50,127,47,90);
            starsList[13] = new Star("Alioth",12.90044444,55.95983333,35,-5,45,-5,30,-7,0);
            starsList[14] = new Star("Aliphard",9.459788889,-8.658602778,115,-80,110,-85,102.5,-95,52);
            starsList[15] = new Star("Alpheca",15.57813889,26.71472222,5,75,5,60,15,65,0);
            starsList[16] = new Star("Rosalhague",17.58225,12.56,0,0,0,0,7,95,90);
        }
        return starsList;
    }

    /**
     * Constructor for the Star object
     *
     * @param name Name of the star
     * @param ra Right ascension
     * @param dec Declination
     * @param base1x x coordinate of first base point
     * @param base1y y coordinate of first base point
     * @param base2x x coordinate of second base point
     * @param base2y y coordinate of second base point
     * @param labelPosx x coordinate of label position
     * @param labelPosy y coordinate of label position
     * @param labelOrient Orientation of label 0-359
     *
     */
    public Star (String name,double ra, double dec, double base1x, double base1y,
                 double base2x, double base2y, double labelPosx, double labelPosy, double labelOrient) {
        this.name = name;
        this.ra = ra;
        this.dec = dec;
        this.base1 = new Point2D.Double(base1x, base1y);
        this.base2 = new Point2D.Double(base2x,base2y);
        this.labelPos = new Point2D.Double(labelPosx,labelPosy);
        this.labelOrient = labelOrient;
    }
    /**
     * Default Constructor for the Star object
     *
     */
    public Star () {
        this.name = "";
        this. ra = 0.0;
        this. dec = 0.0;
        this. base1 = new Point2D.Double();
        this. base2 = new Point2D.Double();
        this. labelPos = new Point2D.Double();
        this. labelOrient = 0.0;
    }
}
