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
package com.wymarc.astrolabe.generator.printengines.postscript.extras.horary;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;

import java.util.Calendar;

/**
 * This Plugin will calculate the components of Horary Quadrant and
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org
 */

// todo: needs limits on latitude, move latitude label out of the way, look at redoing to add color and set up for cutting

public class AdvancedHoraryQuadrant {

    /**
     * computes and draws the shadow squares
     *
     * @return returns the ps code for drawing the Shadow Squares
     */
    private String drawShadowSquare() {
        //compute size of box
        double shadowRadius = 403.0;
        double shadowSide = Math.sqrt((shadowRadius * shadowRadius) / 2.0); //from pythagoras
        double div = 12.0;
        StringBuilder out = new StringBuilder();

        out.append("\n").append("% Shadow square")
                //Draw bottom right
                .append("\n").append("% =============== Create Shadow Square =================")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" 0 lineto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("0 0 moveto");

        for (int count = 1; count < div; count++)// print division lines
        {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append(shadowSide).append(" ").append(-((shadowSide / div) * count)).append(" lineto stroke")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append(((shadowSide / div) * count)).append(" ").append(-shadowSide).append(" lineto stroke");
        }
        out.append("\n").append("newpath")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 12).append(" 0 lineto")
                .append("\n").append(shadowSide - 12).append(" ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 0 lineto fill")
                .append("\n").append("newpath")
                .append("\n").append("0 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 12).append(" 0 lineto")
                .append("\n").append(shadowSide - 12).append(" ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 0 lineto stroke")

                //draw 1/2 way mark
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide / 2.0).append(" lineto stroke")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide / 2.0).append(" ").append(-shadowSide).append(" lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 24).append(" 0 lineto")
                .append("\n").append(shadowSide - 24).append(" ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 0 lineto fill")
                .append("\n").append("0 setgray")

                // mark 6 line
                .append("\n").append((shadowSide / 2.0) - 17).append(" ").append(-(shadowSide - 15)).append(" moveto")
                .append("\n").append("NormalFont10 setfont")
                .append("\n").append("(6) show")
                .append("\n").append((shadowSide - 20)).append(" ").append(-(shadowSide / 2.0 - 12)).append(" moveto")
                .append("\n").append("(6) show")

                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 24).append(" 0 lineto")
                .append("\n").append(shadowSide - 24).append(" ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("0 0 moveto")

                // mark 45 line
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto stroke") // 45 line
                .append("\n").append(shadowSide - 31).append(" ").append(-(shadowSide - 15)).append(" moveto")
                .append("\n").append("NormalFont10 setfont")
                .append("\n").append("(12) show")

                .append("\n").append("% =============== End Right Shadow Square =================");

        return out.toString();
    }

    private String drawOutline(Astrolabe myAstrolabe) {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("% draw outlines")
                .append("\n").append("-36 36 translate")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("540 0 lineto")
                .append("\n").append("540 -36 lineto")
                .append("\n").append("36 -36 lineto")
                .append("\n").append("36 -540 lineto")
                .append("\n").append("0 -540 lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 504 270 360 arc stroke")
                .append("\n").append("72 -490 moveto")
                .append("\n").append("NormalFont10 setfont");

        int latDegrees = myAstrolabe.getLocation().getLatDeg();
        int latMinutes = myAstrolabe.getLocation().getLatMin();
        String degreeString = latDegrees + "\\260 "; //260 is degree symbol
        String minuteString;
        if (latMinutes < 10) {
            minuteString = "0" + latMinutes + "'";
        } else {
            minuteString = latMinutes + "'";
        }
        String label = degreeString + minuteString;
        out.append("\n").append("(").append(label).append(") show");

        return out.toString();
    }

    private String drawDegreeScale() {
        StringBuilder out = new StringBuilder();

        // draw arcs
        out.append("\n").append("% degree scale")
                .append("\n").append("0 0 494 270 360 arc stroke")
                .append("\n").append("0 0 490 270 360 arc stroke")
                .append("\n").append("");

        // create 1 degree marks
        for (int count = 1; count <= 90; count++) {
            out.append("\n").append("490 0 moveto")
                    .append("\n").append("494 0 lineto stroke")
                    .append("\n").append("-1 rotate");
        }
        out.append("\n").append("90 rotate");

        // create 5 degree marks
        for (int count = 1; count <= 18; count++) {
            out.append("\n").append("490 0 moveto")
                    .append("\n").append("498 0 lineto stroke")
                    .append("\n").append("-5 rotate");
        }
        out.append("\n").append("90 rotate");

        // create 10 degree marks
        for (int count = 1; count <= 9; count++) {
            out.append("\n").append("490 0 moveto")
                    .append("\n").append("504 0 lineto stroke")
                    .append("\n").append("-10 rotate");
        }
        out.append("\n").append("90 rotate")

                //Mark degrees
                .append("\n").append("/Times-Roman findfont 8 scalefont setfont");
        for (int count = 1; count <= 9; count++) {
            out.append(EPSToolKit.drawInsideCircularText((count * 10) + "", 5, (-90 + (count * 10) - .75), 502));
        }

        return out.toString();
    }

    private String drawCotangentScale() {
        StringBuilder out = new StringBuilder();
        double cotangentRadius = 475;

        // draw arcs
        out.append("\n").append("% cotangent scale")
                .append("\n").append("")

                // rotate to place center of scale at 0 degrees and mark
                .append("\n").append("-90 rotate")
                .append("\n").append("newpath")
                .append("\n").append(cotangentRadius).append(" 0 moveto")
                .append("\n").append(cotangentRadius + 10).append(" 0 lineto stroke");

        double angle;
        for (int i = 1; i <= 48; i++) {
            angle = Math.toDegrees(Math.atan((double) i / 12));
            out.append("\n").append(angle).append(" rotate")
                    .append("\n").append("newpath")
                    .append("\n").append(cotangentRadius).append(" 0 moveto")
                    .append("\n").append(cotangentRadius + 7).append(" 0 lineto stroke");
            if (i <= 10 || i == 12 || i == 14 || i == 16 || i == 18 || i == 20 || i == 24 || i == 28 ||
                    i == 32 || i == 36 || i == 40 || i == 44 || i == 48) {
                out.append("\n").append("NormalFont8 setfont")
                        .append(EPSToolKit.drawInsideCircularText(Integer.toString(i), 8, 0, (cotangentRadius + 13)));
            }
            out.append("\n").append(-angle).append(" rotate");
        }
        //rotate back
        out.append("\n").append("+90 rotate")

                // draw scale rings
                .append("\n").append("newpath")
                .append("\n").append("0 0 ").append(cotangentRadius).append(" 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 0 ").append(cotangentRadius + 5).append(" 270 360 arc stroke");

        return out.toString();
    }

    private String drawTick(double alt, int start, int end, String text) {
        StringBuilder out = new StringBuilder();

        out.append("\n").append(alt).append(" rotate")
                .append("\n").append("0 ").append(start).append(" moveto")
                .append("\n").append("0 ").append(end).append(" lineto stroke")
                .append("\n").append(-alt).append(" rotate");
        if (!text.equals("")) {
            out.append("\n").append("NormalFont8 setfont");
            double test = alt + 180;
            if (end == 438) {
                out.append(EPSToolKit.drawInsideCircularText(text, 8, -89.4 + test, 430));
            } else if (start == 440) {
                out.append(EPSToolKit.drawInsideCircularText(text, 8, -90.6 + test, 453));
            }
        }
        return out.toString();
    }

    private String drawCalendarScale(double lat) {
        StringBuilder out = new StringBuilder();
        double alt;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        // draw arcs
        out.append("\n").append("% Calendar scale")
                .append("\n").append("0 0 403 270 360 arc stroke");

        alt = AstroMath.solarNoonAltitude(11, 22, year, lat);
        out.append(drawTick(alt - 183, 423, 455, ""));
        double lowAngle = (alt - 3) + 270;
        alt = AstroMath.solarNoonAltitude(5, 21, year, lat);
        out.append(drawTick(alt - 177, 423, 455, ""));
        double highAngle = (alt + 3) + 270;
        out.append("\n").append("0 0 423 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 433 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 438 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 440 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 445 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke")
                .append("\n").append("0 0 455 ").append(lowAngle).append(" ").append(highAngle).append(" arc stroke");

        alt = AstroMath.solarNoonAltitude(0, 1, year, lat) - 180;
        out.append(drawTick(alt, 423, 438, "J"));
        alt = AstroMath.solarNoonAltitude(0, 5, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(0, 10, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(0, 15, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(0, 20, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(0, 25, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(0, 30, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));

        alt = AstroMath.solarNoonAltitude(1, 1, year, lat) - 180;
        out.append(drawTick(alt, 423, 438, "F"));
        alt = AstroMath.solarNoonAltitude(1, 5, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(1, 10, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(1, 15, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(1, 20, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(1, 25, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));

        alt = AstroMath.solarNoonAltitude(2, 1, year, lat) - 180;
        out.append(drawTick(alt, 423, 438, "M"));
        alt = AstroMath.solarNoonAltitude(2, 5, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(2, 10, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(2, 15, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(2, 20, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(2, 25, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(2, 30, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));


        alt = AstroMath.solarNoonAltitude(3, 1, year, lat) - 180;
        out.append(drawTick(alt, 423, 438, "A"));
        alt = AstroMath.solarNoonAltitude(3, 5, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(3, 10, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(3, 15, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(3, 20, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(3, 25, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));

        alt = AstroMath.solarNoonAltitude(4, 1, year, lat) - 180;
        out.append(drawTick(alt, 423, 438, "M"));
        alt = AstroMath.solarNoonAltitude(4, 5, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(4, 10, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(4, 15, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(4, 20, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(4, 25, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(4, 30, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));


        alt = AstroMath.solarNoonAltitude(5, 1, year, lat) - 180;
        out.append(drawTick(alt, 423, 438, "J"));
        alt = AstroMath.solarNoonAltitude(5, 5, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(5, 10, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(5, 15, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(5, 20, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));
        alt = AstroMath.solarNoonAltitude(5, 25, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));


        alt = AstroMath.solarNoonAltitude(6, 1, year, lat) - 180;
        out.append(drawTick(alt, 440, 455, "J"));
        alt = AstroMath.solarNoonAltitude(6, 5, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(6, 10, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(6, 15, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(6, 20, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(6, 25, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(6, 30, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));

        alt = AstroMath.solarNoonAltitude(7, 1, year, lat) - 180;
        out.append(drawTick(alt, 440, 455, "A"));
        alt = AstroMath.solarNoonAltitude(7, 5, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(7, 10, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(7, 15, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(7, 20, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(7, 25, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(7, 30, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));

        alt = AstroMath.solarNoonAltitude(8, 1, year, lat) - 180;
        out.append(drawTick(alt, 440, 455, "S"));
        alt = AstroMath.solarNoonAltitude(8, 5, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(8, 10, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(8, 15, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(8, 20, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(8, 25, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));

        alt = AstroMath.solarNoonAltitude(9, 1, year, lat) - 180;
        out.append(drawTick(alt, 440, 455, "O"));
        alt = AstroMath.solarNoonAltitude(9, 5, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(9, 10, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(9, 15, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(9, 20, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(9, 25, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(9, 30, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));

        alt = AstroMath.solarNoonAltitude(10, 1, year, lat) - 180;
        out.append(drawTick(alt, 440, 455, "N"));
        alt = AstroMath.solarNoonAltitude(10, 5, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(10, 10, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(10, 15, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(10, 20, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(10, 25, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));

        alt = AstroMath.solarNoonAltitude(11, 1, year, lat) - 180;
        out.append(drawTick(alt, 440, 455, "D"));
        alt = AstroMath.solarNoonAltitude(11, 5, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(11, 10, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(11, 15, year, lat) - 180;
        out.append(drawTick(alt, 440, 445, ""));
        alt = AstroMath.solarNoonAltitude(11, 20, year, lat) - 180;
        out.append(drawTick(alt, 440, 449, ""));
        alt = AstroMath.solarNoonAltitude(11, 25, year, lat) - 180;
        out.append(drawTick(alt, 433, 438, ""));
        alt = AstroMath.solarNoonAltitude(11, 30, year, lat) - 180;
        out.append(drawTick(alt, 429, 438, ""));

        return out.toString();
    }

    private String drawUnequalHours() {
        StringBuilder out = new StringBuilder();
        // mark unequal hour lines
        // draw arcs
        out.append("\n").append("% unequal hours")
                .append("\n").append("newpath")
                .append("\n").append("382 0 moveto")
                .append("\n").append("0 0 lineto")
                .append("\n").append("0 -382 lineto")
                .append("\n").append("0 0 403 270 360 arc clip");
        double Ri;
        for (int count = 1; count <= 6; count++) {
            Ri = (403 / (2 * (Math.sin(Math.toRadians(15 * count)))));
            out.append("\n").append(Ri).append(" 0 ").append(Ri).append(" 180 360 arc stroke");
        }

        //Mark unequal hours
        out.append("\n").append("NormalFont8 setfont");
        for (int count = 1; count <= 5; count++) {
            out.append(EPSToolKit.drawInsideCircularText(count + "", 5, (-90 + (count * 15) + 0.5), 398));
        }
        return out.toString();
    }

    private String drawSineScale() {
        StringBuilder out = new StringBuilder();
        double scaleLength = 403.0;
        // draw box 403
        out.append("\n").append("0 0 moveto")
                .append("\n").append("0 15 lineto")
                .append("\n").append(scaleLength).append(" 15 lineto")
                .append("\n").append(scaleLength).append(" 0 lineto stroke")
                .append("\n").append("0 5 moveto")
                .append("\n").append(scaleLength).append(" 5 lineto stroke");
        //mark ticks
        double interval = scaleLength / 100.0;
        for (int i = 1; i < 100; i++) {
            out.append("\n").append(interval * i).append(" 0 moveto");
            if (i == 10 || i == 20 || i == 30 || i == 40 || i == 50 || i == 60 || i == 70 || i == 80 || i == 90) {
                out.append("\n").append(interval * i).append(" 15 lineto stroke")
                        .append("\n").append("NormalFont8 setfont")
                        .append("\n").append(interval * i).append(" 7 moveto")
                        .append(EPSToolKit.centerText(i + ""));
            } else {
                out.append("\n").append(interval * i).append(" 5 lineto stroke");
            }
        }

        // draw cosine curve
        out.append("\n").append("0 ").append(-scaleLength / 2.0).append(" ").append(scaleLength / 2.0).append(" 270 90 arc stroke");

        // draw obliquity arc
        // radius is the Sine of the obliquity angle times the radius of the scale
        double obl = AstroMath.obliquity(AstroMath.getT());
        double oblRadius = Math.sin(Math.toRadians(obl)) * scaleLength;
        out.append("\n").append("0 0 ").append(oblRadius).append(" 270 0 arc stroke");

        return out.toString();
    }


    public String printQuadrant(Astrolabe myAstrolabe) {
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Adavanced Horary Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments")

                .append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append(EPSToolKit.fillBackground())
                .append("\n").append("72 630 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append(EPSToolKit.setUpFonts())
                .append(EPSToolKit.setUpCircularText())
                .append("\n").append("gsave")
                .append("\n").append("");

        out.append(drawShadowSquare())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawDegreeScale())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawCotangentScale())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawCalendarScale(myAstrolabe.getLocation().getLatitude()))
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawSineScale())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawOutline(myAstrolabe))
                .append("\n").append("grestore")
                .append(drawUnequalHours());
        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }


} 