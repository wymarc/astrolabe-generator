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
package com.wymarc.astrolabe.generator.printengines.postscript.extras.sine;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

/**
 * This Plugin will calculate the components of the Sine Quadrant and
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org 
 */

public class SineQuadrantOne {

    boolean color = false;

    private String drawCuttingLines() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw cutting lines =================");
        double interval5 = 452.0 / 12.0;
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("0 -540 lineto")
                .append("\n").append("36 -540 lineto")
                .append("\n").append("36 -36 504 270 360 arc")
                .append("\n").append("540 36 lineto")
                .append("\n").append(interval5 * 10).append(" 36 lineto")
                .append("\n").append(interval5 * 10).append(" 18 lineto")
                .append("\n").append(interval5 * 4).append(" 18 lineto")
                .append("\n").append(interval5 * 4).append(" 36 lineto")
                .append("\n").append("0 36 lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 3 0 360 arc stroke")
                .append("\n").append("%% ================ End Draw cutting lines =================");

        return out.toString();
    }

    private String drawOutlines() {
        //459 limb - 36 = 423 (540)
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw Outlines =================")
                .append("\n").append("newpath")
                .append("\n").append("10 -10 moveto")
                .append("\n").append("540 -10 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -13 moveto")
                .append("\n").append("540 -13 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -33 moveto")
                .append("\n").append("540 -33 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -36 moveto")
                .append("\n").append("540 -36 lineto stroke");

        out.append("\n").append("newpath")
                .append("\n").append("10 -10 moveto")
                .append("\n").append("10 -540 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -13 moveto")
                .append("\n").append("13 -540  lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("33 -13 moveto")
                .append("\n").append("33 -540 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -13 moveto")
                .append("\n").append("36 -540 lineto stroke");

        out.append("\n").append("newpath")
                .append("\n").append("36 -36 494 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 491 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 467 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 464 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 458 270 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 452 270 360 arc stroke")
                .append("\n").append("%% ================ End Draw Outlines =================");

        return out.toString();
    }

    private String markScales() {
        StringBuilder out = new StringBuilder();
        //504 radius  387
        out.append("\n").append("%% ================ Mark Scales =================")
                .append("\n").append("%% ================ Draw Degree Scale =================");
        // create 1/2 degree marks
        for (int count = 1; count < 180; count++) {
            out.append("\n").append("-0.5 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("458 0 moveto")
                    .append("\n").append("452 0 lineto stroke");
        }
        out.append("\n").append("89.5 rotate");

        // create 1 degree marks
        for (int count = 1; count < 90; count++) {
            out.append("\n").append("-1 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("464 0 moveto")
                    .append("\n").append("458 0 lineto stroke");
        }
        out.append("\n").append("89 rotate");

        // create 5 degree marks
        for (int count = 1; count < 18; count++) {
            out.append("\n").append("-5 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("467 0 moveto")
                    .append("\n").append("491 0 lineto stroke");
        }
        out.append("\n").append("85 rotate");

        out.append("\n").append("%% ================ End Draw Degree Scale =================")
                .append("\n").append("%% ================ Draw Sine/cosine Scales =================");
        double interval5 = 452.0 / 12.0;
        for (int i = 1; i <= 12; i++) {
            out.append("\n").append("newpath")
                    .append("\n").append(interval5 * i).append(" 3 moveto")
                    .append("\n").append(interval5 * i).append(" 23 lineto stroke");

            out.append("\n").append("newpath")
                    .append("\n").append("-3 ").append(-interval5 * i).append(" moveto")
                    .append("\n").append("-23 ").append(-interval5 * i).append(" lineto stroke");
        }

        out.append("\n").append("%% ================ Draw Sine/cosine Scales =================");


        out.append("\n").append("%% ================ End Mark Scales =================");
        return out.toString();
    }


    private String labelScales() {
        StringBuilder out = new StringBuilder();
        //504 radius  387
        out.append("\n").append("%% ================ Draw Degree Scale =================")
                .append("\n").append("NormalFont20 setfont");
        //Mark degrees
        for (int count = 5; count <= 85; count = count + 5) {
            String text = Integer.toString(count);
            if (count == 5) {
                text = "  " + text;
            }
            out.append(EPSToolKit.drawInsideCircularText(text, 20, (-90 + count), 487));
        }
        out.append("\n").append("%% ================ End Draw Degree Scale =================");

        out.append("\n").append("%% ================ Draw Sine Scale =================");
        double interval1 = 452.0 / 60.0;
        double interval5 = 452.0 / 12.0;
        out.append("\n").append("NormalFont12 setfont");
        for (int i = 1; i <= 12; i++) {
            if (i > 1) {
                out.append("\n").append(interval5 * i - 14).append(" 5 moveto");
            } else {
                out.append("\n").append(interval5 * i - 8).append(" 5 moveto");
            }
            out.append("\n").append("(").append(i * 5).append(") show");

            if (i > 1) {
                out.append("\n").append("-17 ").append(-interval5 * i + 2).append(" moveto");
            } else {
                out.append("\n").append("-11 ").append(-interval5 * i + 2).append(" moveto");
            }
            out.append("\n").append("(").append(i * 5).append(") show");
        }

        return out.toString();
    }

    private String drawGrid() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw grid =================");
        double interval1 = 452.0 / 60.0;
        double radiusSquared = 452.0 * 452.0;
        double length;

        // draw unit lines
        for (int i = 1; i < 60; i++) {
            double xSide = interval1 * i;
            double xSquared = (xSide) * (xSide);
            length = Math.sqrt(radiusSquared - xSquared);

            out.append("\n").append("newpath")
                    .append("\n").append(xSide).append(" 0 moveto")
                    .append("\n").append(xSide).append(" ").append(-length).append(" lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 ").append(-xSide).append(" moveto")
                    .append("\n").append(length).append(" ").append(-xSide).append(" lineto stroke");
        }

        return out.toString();
    }

    private String markGridIntersections() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw grid Intersections =================")
                .append("\n").append("%% ================ Draw Intersections =================");
        double interval = 452.0 / 12.0;
        double radiusSquared = 452.0 * 452.0;
        double lengthI;
        double lengthJ;

        for (int i = 1; i < 12; i++) {
            double iSide = interval * i;
            double iSquared = (iSide) * (iSide);
            lengthI = Math.sqrt(radiusSquared - iSquared);
            for (int j = 1; j < 12; j++) {
                double jSide = interval * j;
                double jSquared = (jSide) * (jSide);
                lengthJ = Math.sqrt(radiusSquared - jSquared);
                if ((interval * j) < lengthI) {
                    out.append("\n").append(interval * i).append(" ").append(-interval * j).append(" drawX");
                }
                if ((interval * i) < lengthJ) {
                    out.append("\n").append(interval * j).append(" ").append(-interval * i).append(" drawX");
                }
            }
        }

        out.append("\n").append("%% ================ End Draw Intersections =================")
                .append("\n").append("%% ================ Draw Asr Intersections =================");

        interval = 452.0 / 60.0;
        for (int i = 1; i < 59; i++) {
            out.append("\n").append(12 * interval).append(" ").append(-interval * i).append(" drawX");
        }

        for (int i = 1; i < 59; i++) {
            if (!(i == 12)) {  // skip the repeated x
                out.append("\n").append(interval * i).append(" ").append(-12 * interval).append(" drawX");
            }
        }

        out.append("\n").append("%% ================ End Draw Asr Intersections =================")
                .append("\n").append("%% ================ End Draw grid Intersections =================");

        return out.toString();
    }

    private String drawAdvancedLines() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw Advanced Lines =================")
                .append("\n").append("%% ================ Draw Intersections =================");

        out.append("\n").append("%% ================ Draw 45 Line =================");
        double length = Math.sqrt((452.0 * 452.0) / 2);
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(length).append(" ").append(-length).append(" lineto stroke")
                .append("\n").append("%% ================ Draw 45 Line =================");

        out.append("\n").append("%% ================ Draw Mystery Line =================");
        // draw mystery line
        out.append("\n").append("newpath")
                .append("\n").append("452 0 moveto")
                .append("\n").append("0 -452 lineto stroke")
                .append("\n").append("%% ================ End Draw Mystery Line =================");

        out.append("\n").append("%% ================ Draw sine/cosine arcs =================")
                .append("\n").append("newpath")
                .append("\n").append("226 0 226 180 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 -226 226 270 90 arc stroke")
                .append("\n").append("%% ================ End Draw sine/cosine arcs =================");

        out.append("\n").append("%% ================ Draw Asr start Arc  =================");
        double interval = 452.0 / 60.0;
        Point2D[] asrLine = AstroMath.defineAsrLine(interval, 1.0);
        out.append("\n").append("newpath")
                .append("\n").append(asrLine[0].getY()).append(" ").append(-asrLine[0].getX()).append(" moveto");     //todo fix this so it isn't reversed yx
        for (int i = 1; i < 90; i++) {
            out.append("\n").append(asrLine[i].getY()).append(" ").append(-asrLine[i].getX()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getY()).append(" ").append(-asrLine[90].getX()).append(" lineto stroke")
                .append("\n").append("%% ================ End Draw Asr start Arc  =================");

        out.append("\n").append("%% ================ Draw Asr End arc =================");
        asrLine = AstroMath.defineAsrLine(interval, 2.0);
        out.append("\n").append("newpath")
                .append("\n").append(asrLine[0].getY()).append(" ").append(-asrLine[0].getX()).append(" moveto");     //todo fix this so it isn't reversed yx
        for (int i = 1; i < 90; i++) {
            out.append("\n").append(asrLine[i].getY()).append(" ").append(-asrLine[i].getX()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getY()).append(" ").append(-asrLine[90].getX()).append(" lineto stroke")
                .append("\n").append("%% ================ End Draw Asr End arc =================");

        out.append("\n").append("%% ================ Draw obliquity arc =================");
        // draw obliquity arc
        // radius is the Sine of the obliquity angle times the radius of the scale
        double obl = AstroMath.obliquity(AstroMath.getT());
        double oblRadius = Math.sin(Math.toRadians(obl)) * 452;
        out.append("\n").append("newpath")
                .append("\n").append("0 0 ").append(oblRadius).append(" 270 0 arc stroke")
                .append("\n").append("%% ================ End Draw obliquity arc =================");

        return out.toString();
    }

    /**
     * The original is 6 3/8" across one limb, this translates to 459 points
     *
     * @return Sting containing the EPS code
     */

    public String printQuadrant() {
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Sine Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments");

        out.append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append("\n").append("36 626 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append(EPSToolKit.setUpFonts())
                .append(EPSToolKit.setUpSymbols())
                .append(EPSToolKit.setUpCircularText());

        out.append("\n").append("gsave")
                .append("\n").append("0 0 1 setrgbcolor")
                .append(drawOutlines())
                .append("\n").append("grestore");

        out.append("\n").append("36 -36 translate");

        out.append("\n").append("gsave")
                .append("\n").append("1 0 1 setrgbcolor")
                .append(markScales())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 0 0 setrgbcolor")
                .append(labelScales())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 1 0 setrgbcolor")
                .append(drawGrid())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 1 1 setrgbcolor")
                .append(markGridIntersections())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("1 1 0 setrgbcolor")
                .append(drawAdvancedLines())
                .append("\n").append("grestore");

        out.append("\n").append("-36 36 translate")
                .append("\n").append("gsave")
                .append("\n").append("1 0 0 setrgbcolor")
                .append(drawCuttingLines())
                .append("\n").append("grestore");

        out.append("\n").append("grestore");
        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }
} 