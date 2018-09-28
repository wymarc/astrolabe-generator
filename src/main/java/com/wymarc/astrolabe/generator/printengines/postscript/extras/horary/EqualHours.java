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
package com.wymarc.astrolabe.generator.printengines.postscript.extras.horary;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;
import com.wymarc.astrolabe.math.InterSect;
import com.wymarc.astrolabe.math.LineCircleIntersect;
import com.wymarc.astrolabe.math.ThreePointCenter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This Plugin will calculate the components of an equal hours style Horary
 * Quadrant and  print the results to an Encapsulated PostScript (EPS) file.
 * Note that this file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org
 */
public class EqualHours {
    // Note: limit is 25N to 65N
    public static double NORTH_LIMIT = 65.0;
    public static double SOUTH_LIMIT = 25.0;

    //todo add colors, add latitude

    private Astrolabe myAstrolabe = new Astrolabe();
    private boolean isColor = false;  // color version toggle
    private boolean forCAD = false; // Each layer a separate color for using with CAD/cutting software
    private double lineWidth = .4;
    private double workingRadius = 435.0;

    public EqualHours(Astrolabe myAstrolabe, boolean isColor, boolean forCAD) {
        this.myAstrolabe = myAstrolabe;
        this.isColor = isColor;
        this.forCAD = forCAD;
    }

    private String drawCutLines(String orientation) {
        StringBuilder out = new StringBuilder();
        if (forCAD) {
            out.append("\n").append("1 0 0 setrgbcolor"); //set red
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        if (orientation.equals("right")) {
            out.append("\n").append("% draw outlines")
                    .append("\n").append("newpath")
                    .append("\n").append("508 0 moveto")
                    .append("\n").append("508 40 lineto")
                    .append("\n").append("-40 40 lineto")
                    .append("\n").append("-40 -508 lineto")
                    .append("\n").append("0 -508 lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 0 508 270 360 arc stroke");
            if (!forCAD) {
                out.append("\n").append("newpath")
                        .append("\n").append("0 0 3 0 360 arc stroke");
            }

        } else if (orientation.equals("left")) {
            out.append("\n").append("% draw outlines")
                    .append("\n").append("468 0 translate")
                    .append("\n").append("newpath")
                    .append("\n").append("-508 0 moveto")
                    .append("\n").append("-508 40 lineto")
                    .append("\n").append("40 40 lineto")
                    .append("\n").append("40 -508 lineto")
                    .append("\n").append("0 -508 lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 0 508 180 270 arc stroke");
            if (!forCAD) {
                out.append("\n").append("newpath")
                        .append("\n").append("0 0 3 0 360 arc stroke");
            }
        }

        return out.toString();
    }

    private String drawOutline(String orientation) {
        StringBuilder out = new StringBuilder();
        if (forCAD) {
            out.append("\n").append("0 1 0 setrgbcolor"); //set yellow
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        if (orientation.equals("right")) {
            out.append("\n").append("% draw outlines")
                    .append("\n").append("newpath")
                    .append("\n").append("504 0 moveto")
                    .append("\n").append("504 36 lineto")
                    .append("\n").append("-36 36 lineto")
                    .append("\n").append("-36 -504 lineto")
                    .append("\n").append("0 -504 lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 0 504 270 360 arc stroke");
        } else if (orientation.equals("left")) {
            out.append("\n").append("% draw outlines")
                    .append("\n").append("468 0 translate")
                    .append("\n").append("newpath")
                    .append("\n").append("-504 0 moveto")
                    .append("\n").append("-504 36 lineto")
                    .append("\n").append("36 36 lineto")
                    .append("\n").append("36 -504 lineto")
                    .append("\n").append("0 -504 lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 0 504 180 270 arc stroke");
        }

        return out.toString();
    }

    private String drawScales() {
        StringBuilder out = new StringBuilder();
        int count;

        if (forCAD) {
            out.append("\n").append("0 1 0 setrgbcolor"); //set red
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        // draw arms
        out.append("\n").append("% draw arms")
                .append("\n").append("newpath")
                .append("\n").append("504 0 moveto")
                .append("\n").append("-36 0 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 36 moveto")
                .append("\n").append("0 -504 lineto stroke");

        out.append("\n").append("% draw scales")
                .append("\n").append("newpath")
                .append("\n").append("0 0 504 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 494 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 484 270 360 arc stroke");

        out.append("\n").append("newpath")
                .append("\n").append("0 0 469 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 467 270 360 arc stroke");

        out.append("\n").append("newpath")
                .append("\n").append("0 0 452 270 360 arc stroke");

        out.append("\n").append("newpath")
                .append("\n").append("0 0 437 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 435 270 360 arc stroke");

        // create 1 degree marks
        for (count = 0; count < 90; count++) {
            if (count % 5 == 0) { // longer mark at each 5 degrees
                if (count == 45) {//unless it is 45
                    double start = Math.sqrt(((workingRadius / 2.0) * (workingRadius / 2.0)) * 2);
                    out.append("\n").append(start).append(" 0 moveto");
                } else {
                    out.append("\n").append("484 0 moveto");
                }
            } else {
                out.append("\n").append("494 0 moveto");
            }
            out.append("\n").append("504 0 lineto stroke");
            out.append("\n").append("-1 rotate");
        }
        out.append("\n").append("90 rotate");

        //Mark degrees
        out.append("\n").append("0 setgray");
        out.append("\n").append("/Times-Roman findfont 10 scalefont setfont");
        for (count = 1; count <= 9; count++) {
            if (count == 9) {// 90 is offset
                out.append("\n").append(EPSToolKit.drawInsideCircularText("+" + (count * 10) + "", 5, (-90 + (count * 10) - 1.2), 482));
            } else {
                out.append("\n").append(EPSToolKit.drawInsideCircularText("+" + (count * 10) + "+", 5, (-90 + (count * 10)), 482));
            }
        }

        return out.toString();
    }

    private String drawHourLines() {
        StringBuilder out = new StringBuilder();
        double noonRadius = workingRadius / 2.0;

        if (forCAD) {
            out.append("\n").append("0 1 0 setrgbcolor"); //set red
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        out.append("\n").append("% draw hour lines");
        // draw noon line
        out.append("\n").append("newpath")
                .append("\n").append(noonRadius).append(" 0 ").append(noonRadius).append(" 180 360 arc stroke");

        //draw equinox and solstice lines
        double lat = myAstrolabe.getLocation().getLatitude();
        double obl = AstroMath.obliquity(AstroMath.getT());

        double solarSummerSolstice = (90.0 - lat) + obl;
        double solarNoonEquinox = (90.0 - lat);
        double solarWinterSolstice = (90.0 - lat) - obl;

        double summerRadius = 2.0 * Math.cos(Math.toRadians(90.0 - solarSummerSolstice)) * noonRadius;
        double equinoxRadius = 2.0 * Math.cos(Math.toRadians(90.0 - solarNoonEquinox)) * noonRadius;
        double winterRadius = 2.0 * Math.cos(Math.toRadians(90.0 - solarWinterSolstice)) * noonRadius;
        double winterRadius2 = winterRadius - 2;

        InterSect interSectSS = new InterSect(0, 0, summerRadius, noonRadius, 0, noonRadius);
        InterSect interSectEq = new InterSect(0, 0, equinoxRadius, noonRadius, 0, noonRadius);
        InterSect interSectWS = new InterSect(0, 0, winterRadius, noonRadius, 0, noonRadius);
        InterSect interSectWS2 = new InterSect(0, 0, winterRadius2, noonRadius, 0, noonRadius);

        out.append("\n").append("newpath")
                .append("\n").append(" 0 0 ").append(summerRadius).append(" 270 ").append(interSectSS.getAngle2()).append(" arc stroke")
                .append("\n").append("newpath")
                .append("\n").append(" 0 0 ").append(equinoxRadius).append(" 270 ").append(interSectEq.getAngle2()).append(" arc stroke")
                .append("\n").append("newpath")
                .append("\n").append(" 0 0 ").append(winterRadius).append(" 270 ").append(interSectWS.getAngle2()).append(" arc stroke")
                .append("\n").append("newpath")
                .append("\n").append(" 0 0 ").append(winterRadius2).append(" 270 ").append(interSectWS2.getAngle2()).append(" arc stroke");

        int count = 8;

        if (myAstrolabe.getLocation().getLatDeg() > 55) {
            count = 10;
        } else if (myAstrolabe.getLocation().getLatDeg() <= 55 && myAstrolabe.getLocation().getLatDeg() > 50) {
            count = 9;
        } else if (myAstrolabe.getLocation().getLatDeg() <= 50 && myAstrolabe.getLocation().getLatDeg() > 45) {
            count = 8;
        } else if (myAstrolabe.getLocation().getLatDeg() <= 45 && myAstrolabe.getLocation().getLatDeg() > 40) {
            count = 8;
        } else if (myAstrolabe.getLocation().getLatDeg() <= 40 && myAstrolabe.getLocation().getLatDeg() > 35) {
            count = 8;
        } else if (myAstrolabe.getLocation().getLatDeg() <= 35 && myAstrolabe.getLocation().getLatDeg() > 30) {
            count = 8;
        } else if (myAstrolabe.getLocation().getLatDeg() <= 30 && myAstrolabe.getLocation().getLatDeg() >= 25) {
            count = 7;
        }

        for (int i = 1; i < count; i++) {
            // get angles for each arc
            double solarAltitude1 = Math.toDegrees(Math.acos(Math.cos(Math.toRadians(i * 15)) *
                    Math.cos(Math.toRadians(obl)) * Math.cos(Math.toRadians(lat)) +
                    Math.sin(Math.toRadians(obl)) * Math.sin(Math.toRadians(lat))));

            double solarAltitude2 = Math.toDegrees(Math.acos(Math.cos(Math.toRadians(i * 15)) *
                    Math.cos(Math.toRadians(0)) * Math.cos(Math.toRadians(lat)) +
                    Math.sin(Math.toRadians(0)) * Math.sin(Math.toRadians(lat))));

            double solarAltitude3 = Math.toDegrees(Math.acos(Math.cos(Math.toRadians(i * 15)) *
                    Math.cos(Math.toRadians(-obl)) * Math.cos(Math.toRadians(lat)) +
                    Math.sin(Math.toRadians(-obl)) * Math.sin(Math.toRadians(lat))));

            //find intersection point for each angle/arc pair to get three points
            double x;
            double y;
            // polar to Cartesian
            x = Math.cos(Math.toRadians(solarAltitude1)) * summerRadius;
            y = -Math.sin(Math.toRadians(solarAltitude1)) * summerRadius;
            Point2D.Double point1 = new Point2D.Double(x, y);

            x = Math.cos(Math.toRadians(solarAltitude2)) * equinoxRadius;
            y = -Math.sin(Math.toRadians(solarAltitude2)) * equinoxRadius;
            Point2D.Double point2 = new Point2D.Double(x, y);

            x = Math.cos(Math.toRadians(solarAltitude3)) * winterRadius;
            y = -Math.sin(Math.toRadians(solarAltitude3)) * winterRadius;
            Point2D.Double point3 = new Point2D.Double(x, y);

            // compute center and radius of the circle defined by these three points and draw
            ThreePointCenter MyCircle = new ThreePointCenter(point1, point2, point3);

            if (MyCircle.isCircle()) {
                InterSect interSect1 = new InterSect(MyCircle.getCenter().x, MyCircle.getCenter().y,
                        MyCircle.getRadius(), 0, 0, winterRadius);
                InterSect interSect2 = new InterSect(MyCircle.getCenter().x, MyCircle.getCenter().y,
                        MyCircle.getRadius(), 0, 0, workingRadius);

                InterSect interSect3 = new InterSect(0, 0, workingRadius, MyCircle.getCenter().x, MyCircle.getCenter().y,
                        MyCircle.getRadius());

                double angle1;
                double angle2;
                if (i > 5){
//                    angle1 = interSect1.getAngle2();
                    // TODO: 9/7/2018
                    Point2D pointA = new Point2D.Double(0.0, 0.0);
                    Point2D pointB = new Point2D.Double(0.0, -700.0);
                    angle1 = LineCircleIntersect.getCircleLineIntersectionAngles(pointA, pointB, MyCircle.getCenter(), MyCircle.getRadius()).get(1);

                    angle2 = interSect2.getAngle1();
                } else if (i > 4){
//                    angle1 = interSect1.getAngle2();
                    // TODO: 9/7/2018
                    Point2D pointA = new Point2D.Double(0.0, 0.0);
                    Point2D pointB = new Point2D.Double(0.0, -700.0);
                    angle1 = LineCircleIntersect.getCircleLineIntersectionAngles(pointA, pointB, MyCircle.getCenter(), MyCircle.getRadius()).get(1);

                    angle2 = interSect2.getAngle2();
                }else{
                    angle1 = interSect1.getAngle2();
                    angle2 = interSect2.getAngle2();
                }
                double labelAngle1 = interSect3.getAngle1();
                double labelAngle2 = interSect3.getAngle2();

                // label
                out.append("\n").append("0 setgray");
                out.append("\n").append("/Times-Roman findfont 10 scalefont setfont");
                if (labelAngle1 > 270) {
                    out.append("\n").append(EPSToolKit.drawInsideCircularText((12 - i) + "", 5, labelAngle1, workingRadius + 13));
                    out.append("\n").append(EPSToolKit.drawInsideCircularText((i) + "", 5, labelAngle1, workingRadius + 28));
                } else {
                    out.append("\n").append(EPSToolKit.drawInsideCircularText((12 - i) + "", 5, labelAngle2, workingRadius + 13));
                    out.append("\n").append(EPSToolKit.drawInsideCircularText((i) + "", 5, labelAngle2, workingRadius + 28));
                }
                if (i == 1){
                    out.append("\n").append(EPSToolKit.drawInsideCircularText("12", 5, 359, workingRadius + 13));
                }

                if (forCAD) {
                    out.append("\n").append("0 1 0 setrgbcolor"); //set red
                } else if (isColor) {
                    out.append("\n").append("0 setgray");
                } else {
                    out.append("\n").append("0 setgray");
                }

                out.append("\n").append("newpath")
                        .append("\n").append(MyCircle.getCenter().x).append(" ")
                        .append(MyCircle.getCenter().y).append(" ")
                        .append(MyCircle.getRadius()).append(" ")
                        .append(angle1).append(" ").append(angle2).append(" arc stroke");
            }
        }


        return out.toString();
    }

    private String drawBackGrid() {
        StringBuilder out = new StringBuilder();
        if (forCAD) {
            out.append("\n").append("1 0 1 setrgbcolor"); //set red
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        out.append("\n").append("% draw back arms")
                .append("\n").append("468 0 translate");

        // draw arms
        out.append("\n").append("% draw arms")
                .append("\n").append("newpath")
                .append("\n").append("-504 0 moveto")
                .append("\n").append("36 0 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 36 moveto")
                .append("\n").append("0 -504 lineto stroke");

        // draw 31 radials
        for (int i = 1; i < 31; i++) {
            double rotation = (90.0 / 31.0) * i;
            out.append("\n").append(" -").append(rotation).append(" rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("0 -316.8 moveto")
                    .append("\n").append("0 -504 lineto stroke")
                    .append("\n").append(" ").append(rotation).append(" rotate");
        }

        // draw arcs
        for (int i = 1; i < 14; i++) {
            double x = (504 - (i * 14.4));
            out.append("\n").append("newpath")
                    .append("\n").append(" -").append(x).append(" 0 moveto")
                    .append("\n").append(" -").append(x).append(" 36 lineto stroke");

            out.append("\n").append("0 0 ").append(x).append(" 180 270 arc stroke");
        }

        return out.toString();
    }

    private String labelBackGrid() {
        StringBuilder out = new StringBuilder();
        if (forCAD) {
            out.append("\n").append("0 setgray"); //set black
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        out.append("\n").append("% label back arcs");

        out.append("\n").append("468 0 translate");
        // Add months
        out.append("\n").append("NormalFont10 setfont");
        out.append("\n").append("-90 rotate");
        for (int i = 0; i < 12; i++) {
            double x = (489.6 - (i * 14.4)) - 3;
            out.append("\n").append("-30 -").append(x).append(" moveto");
            out.append("\n").append("(").append(Astrolabe.MONTHS[11 - i].substring(0, 3).toUpperCase()).append(") show");
        }
        out.append("\n").append("90 rotate");

        // add days
        double interval = 90.0 / 31.0;
        for (int count = 1; count <= 31; count++) {
            out.append("\n").append(EPSToolKit.drawInsideCircularText(
                    (count) + "", 5, (-180 + (count * interval) - (interval / 2.0)), 500));
        }

        // compute and add data
        ArrayList<String> altitudeList = new ArrayList<>(); //todo test for accuracy
        int currentAltitude = 0;
        double newAltitude = 0.0;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1; i < 366; i++) { //we need 1-365 for this, see Calendar class
            newAltitude = AstroMath.solarNoonAltitude(i, year, myAstrolabe.getLocation().getLatitude());
            if ((int) newAltitude != currentAltitude) {
                altitudeList.add("" + (int) newAltitude);
                currentAltitude = (int) newAltitude;
            } else {
                altitudeList.add("");
            }
        }

        // print data
        int index = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 31; j++) {
                //test for legal date
                if (AstroMath.isLegalDate(j, i + 1, year)) {
                    out.append("\n").append(EPSToolKit.drawInsideCircularText(
                            altitudeList.get(index), 5, (-180 + (j * interval) + (interval / 2.0)), 316.8 + (i * 14.4) + 10));
                    index++;
                } else {
                    out.append("\n").append(EPSToolKit.drawInsideCircularText(
                            "o", 5, (-180 + (j * interval) + (interval / 2.0)), 316.8 + (i * 14.4) + 10));
                }

            }
        }
        return out.toString();
    }

    private String drawMedallion() {
        StringBuilder out = new StringBuilder();

        double medallionRadius = 316.8 / (Math.sqrt(2.0) + 1);

        if (forCAD) {
            out.append("\n").append("0 1 0 setrgbcolor"); //set green
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        // draw medallion
        // move center to center of medallion
        out.append("\n").append(468 - medallionRadius).append(" ").append(-medallionRadius).append(" translate");

        //set initial rotation
        out.append("\n").append("\n -45 rotate");

        //draw circles
        out.append("\n").append("\n" + "newpath")
                .append("\n").append("0 0 ").append(medallionRadius - 5).append(" 0 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 ").append(medallionRadius - 7).append(" 0 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 ").append(medallionRadius - 22).append(" 0 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 ").append(medallionRadius - 37).append(" 0 360 arc stroke");

        // draw box for ribbon
        out.append("\n").append("newpath");
        double length = medallionRadius - 65;
        out.append("\n").append(-length).append(" 15 moveto")
                .append("\n").append(2 * length).append(" 0 rlineto")
                .append("\n").append(0).append(" -30 rlineto")
                .append("\n").append(-2 * length).append(" 0 rlineto")
                .append("\n").append(0 + " 30" + " rlineto stroke");

        //left ribbon
        out.append("\n").append("newpath")
                .append("\n").append(-(length - 15)).append(" 15 moveto")
                .append("\n").append("0 10 rlineto")
                .append("\n").append("-30 0 rlineto")
                .append("\n").append("5 -10 rlineto")
                .append("\n").append("-10 5 rlineto")
                .append("\n").append("0 -20 rlineto")
                .append("\n").append("10 5 rlineto")
                .append("\n").append("-5 -10 rlineto")
                .append("\n").append("15 0 rlineto stroke")
                .append("\n").append("newpath")
                .append("\n").append(-length).append(" 15 moveto")
                .append("\n").append("15 10 rlineto stroke");

        //right ribbon
        out.append("\n").append("newpath")
                .append("\n").append(length - 15).append(" -15 moveto")
                .append("\n").append("0 -10 rlineto")
                .append("\n").append("30 0 rlineto")
                .append("\n").append("-5 10 rlineto")
                .append("\n").append("10 -5 rlineto")
                .append("\n").append("0 20 rlineto")
                .append("\n").append("-10 -5 rlineto")
                .append("\n").append("5 10 rlineto")
                .append("\n").append("-15 0 rlineto stroke")
                .append("\n").append("newpath")
                .append("\n").append(length).append(" -15 moveto")
                .append("\n").append("-15 -10 rlineto stroke");

        if (forCAD) {
            out.append("\n").append("0 setgray"); //set black
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }

        // label segments
        List<String> dominicalSeguenceList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 28; i++) {
            dominicalSeguenceList.add(AstroMath.getDominicalLetter(year + i));
        }

        // label medallion as "Tabula Bisexti"
        out.append("\n").append("%% Label Tabula Bisexti")
                .append("\n").append("NormalFont20 setfont")
                .append("\n").append("newpath")
                .append("\n").append("0 -5 moveto")
                .append("\n").append(EPSToolKit.centerText("Tabula Bisexti"));

        // Label location, Latitude, time correction
        out.append("\n").append("%% Label location and latitude")
                .append("\n").append("NormalFont12 setfont")
                .append("\n").append("newpath")
                .append("\n").append("0 40 moveto")
                .append("\n").append(EPSToolKit.centerText(latitudeLabel()))
                .append("\n").append("newpath")
                .append("\n").append("0 -60 moveto")
                .append("\n").append(EPSToolKit.centerText(myAstrolabe.getLocation().getLocationName()))
                .append("\n").append("NormalFont12 setfont")
                .append("\n").append("newpath")
                .append("\n").append("0 -40 moveto")
                .append("\n").append(EPSToolKit.centerText(AstroMath.getTimeCorrection(myAstrolabe.getLocation().getLonDeg(), myAstrolabe.getLocation().getLonMin(),
                myAstrolabe.getLocation().getLonSec(), myAstrolabe.getLocation().getLonDir(),true)));

        // label year
        out.append("\n").append("%% Label year")
                .append("\n").append("-90 rotate")
                .append("\n").append("NormalFont18 setfont")
                .append("\n").append("-170 -6 moveto")
                .append("\n").append("(").append(year).append(") show");
        if (forCAD) {
            out.append("\n").append("0 1 0 setrgbcolor"); //set green
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }
                out.append("\n").append("newpath")
                .append("\n").append("-175 -10 moveto")
                .append("\n").append("-128 -10 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("-175 +10 moveto")
                .append("\n").append("-128 +10 lineto stroke")
                .append("\n").append("90 rotate");

        // mark segments
        if (forCAD) {
            out.append("\n").append("0 setgray"); //set black
        } else if (isColor) {
            out.append("\n").append("0 setgray");
        } else {
            out.append("\n").append("0 setgray");
        }
        double markRotation = (360.0 / 28.0) / 2.0;
        for (int i = 0; i < 28; i++) {
            double rotation = (360.0 / 28.0) * i;
            out.append("\n").append("-").append(rotation).append(" rotate");
            out.append("\n").append("newpath");
            out.append("\n").append("NormalFont12 setfont");
            if (dominicalSeguenceList.get(i).length() > 1) {
                out.append("\n").append("newpath");
                out.append("\n").append("0 ").append(medallionRadius - 33).append(" moveto");
                out.append("\n").append(EPSToolKit.centerText(dominicalSeguenceList.get(i).substring(0, 1)));
                out.append("\n").append("newpath");
                out.append("\n").append("0 ").append(medallionRadius - 18).append(" moveto");
                out.append("\n").append(EPSToolKit.centerText(dominicalSeguenceList.get(i).substring(1)));

                out.append("\n").append("-").append(markRotation).append(" rotate");
                if (forCAD) {
                    out.append("\n").append("0 1 0 setrgbcolor"); //set green
                } else if (isColor) {
                    out.append("\n").append("0 setgray");
                } else {
                    out.append("\n").append("0 setgray");
                }
                out.append("\n").append("newpath");
                out.append("\n").append(medallionRadius - 7).append(" 0 moveto");
                out.append("\n").append(medallionRadius - 37).append(" 0 lineto stroke");
                out.append("\n").append("0 setgray");
                out.append("\n").append(markRotation).append(" rotate");

            } else {
                out.append("\n").append("0 ").append(medallionRadius - 18).append(" moveto");
                out.append("\n").append(EPSToolKit.centerText(dominicalSeguenceList.get(i)));

                int markIndex = i;
                if (markIndex == 0) {
                    markIndex = dominicalSeguenceList.size() - 1;
                } else {
                    markIndex--;
                }

                if (forCAD) {
                    out.append("\n").append("0 1 0 setrgbcolor"); //set green
                } else if (isColor) {
                    out.append("\n").append("0 setgray");
                } else {
                    out.append("\n").append("0 setgray");
                }
                // if the previous letter was doubled, Mark the tick long
                if (dominicalSeguenceList.get(markIndex).length() == 1) {
                    out.append("\n").append("-").append(markRotation).append(" rotate");
                    out.append("\n").append("newpath");
                    out.append("\n").append(medallionRadius - 7).append(" 0 moveto");
                    out.append("\n").append(medallionRadius - 22).append(" 0 lineto stroke");
                    out.append("\n").append(markRotation).append(" rotate");
                } else {
                    out.append("\n").append("-").append(markRotation).append(" rotate");
                    out.append("\n").append("newpath");
                    out.append("\n").append(medallionRadius - 7).append(" 0 moveto");
                    out.append("\n").append(medallionRadius - 37).append(" 0 lineto stroke");
                    out.append("\n").append(markRotation).append(" rotate");
                }
                out.append("\n").append("0 setgray");
            }

            out.append("\n").append(rotation).append(" rotate");
        }

        return out.toString();
    }

    private String latitudeLabel() {
        int latDegrees = myAstrolabe.getLocation().getLatDeg();
        int latMinutes = myAstrolabe.getLocation().getLatMin();
        String degreeString = latDegrees + "\\260 "; //260 is degree symbol
        String minuteString;
        if (latMinutes < 10) {
            minuteString = "0" + latMinutes + "'";
        } else {
            minuteString = latMinutes + "'";
        }

        return degreeString + minuteString + "N";
    }

    private String getHeader() {
        StringBuilder out = new StringBuilder();

        out.append("\n").append(EPSToolKit.getHeader(myAstrolabe, "Equal Hours Horary Quadrant"))
                .append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup");
        if (myAstrolabe.getShowRegistrationMarks() && !forCAD) {
            out.append("\n").append(EPSToolKit.registrationMarks());
        }
        out.append("\n").append("72 630 translate")
                .append("\n").append(lineWidth).append(" setlinewidth")
                .append("\n")
                .append("\n").append(EPSToolKit.setUpFonts())
                .append("\n").append(EPSToolKit.setUpCircularText())
                .append("\n").append("gsave")
                .append("\n");

        return out.toString();
    }


    /**
     * Draws the quadrant front
     *
     * @return returns the ps code for drawing the quadrant
     */
    public String createQuadrantFront() {
        String out = "";

        //set up header
        out += getHeader();

        // draw cutting lines
        out += "\n" + "gsave";
        out += drawCutLines("right");
        out += "\n" + "grestore";

        // Draw Outline
        out += "\n" + "gsave";
        out += drawOutline("right");
        out += "\n" + "grestore";

        //draw scales
        out += "\n" + "gsave";
        out += drawScales();
        out += "\n" + "grestore";

        //draw hour lines
        out += "\n" + "gsave";
        out += drawHourLines();
        out += "\n" + "grestore";


        return out;
    }

    /**
     * Draws the quadrant front
     *
     * @return returns the ps code for drawing the quadrant
     */
    public String createQuadrantBack() {
        String out = "";

        //set up header
        out += getHeader();

        // draw cutting lines
        out += "\n" + "gsave";
        out += drawCutLines("left");
        out += "\n" + "grestore";

        // Draw Outline
        out += "\n" + "gsave";
        out += drawOutline("left");
        out += "\n" + "grestore";

        // draw grid
        out += "\n" + "gsave";
        out += drawBackGrid();
        out += "\n" + "grestore";

        // Label grid
        out += "\n" + "gsave";
        out += labelBackGrid();
        out += "\n" + "grestore";

        // draw medallion
        out += "\n" + "gsave";
        out += drawMedallion();
        out += "\n" + "grestore";

        return out;
    }
}
