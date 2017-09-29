/**
 * $Id: AstrolabeGenerator.java,v 3.0
 * <p>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2 of
 * the License, or(at your option) any later version.
 * <p>
 * The Astrolabe Generator is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * <p>
 * Copyright (c) 2014, 2015 Timothy J. Mitchell
 */
package com.wymarc.astrolabe.generator.printengines.postscript.extras.sine;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

/**
 * This Plugin will calculate the components of Horary Quadrant and 
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org 
 */

public class VernierSineQuadrant {

    boolean color = false;

    private String drawOutline() {
        //459 limb - 36 = 423 (540)
        StringBuilder out = new StringBuilder();

        out.append("\n").append("%% ================ Draw Outlines =================")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("540 0 lineto")
                .append("\n").append("540 -36 lineto stroke")
                .append("\n").append("0 0 moveto")
                .append("\n").append("0 -540 lineto")
                .append("\n").append("36 -540 lineto stroke")
                .append("\n").append("36 -36 504 270 360 arc stroke");

        out.append("\n").append("newpath")
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
                .append("\n").append("36 -540 lineto stroke")
                .append("\n").append("%% ================ End Draw Outlines =================");

        return out.toString();
    }

    private String drawDegreeScale() {
        StringBuilder out = new StringBuilder();
        //504 radius  387
        out.append("\n").append("%% ================ Draw Degree Scale =================")
                .append("\n").append("0 0 501 270 360 arc stroke")
                .append("\n").append("0 0 477 270 360 arc stroke")
                .append("\n").append("0 0 482 270 360 arc stroke");


        // create 1 degree marks
        for (int count = 1; count < 90; count++) {
            out.append("\n").append("-1 rotate")
                    .append("\n").append("477 0 moveto")
                    .append("\n").append("482 0 lineto stroke");
        }
        out.append("\n").append("89 rotate");

        // create 5 degree marks
        for (int count = 1; count < 18; count++) {
            out.append("\n").append("-5 rotate")
                    .append("\n").append("474 0 moveto")
                    .append("\n").append("501 0 lineto stroke");
        }
        out.append("\n").append("85 rotate");

        out.append("\n").append("NormalFont20 setfont");
        //Mark degrees
        for (int count = 5; count <= 85; count = count + 5) {
            String text = Integer.toString(count);
            if (count == 5) {
                text = "  " + text;
            }
            out.append("\n").append(EPSToolKit.drawInsideCircularText(text, 20, (-90 + count), 497));
        }
        out.append("\n").append("%% ================ End Draw Degree Scale =================");
        return out.toString();
    }

    private String drawSineScale() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw Sine Scale =================");
        double interval1 = 414.0 / 60.0;
        double interval5 = 414.0 / 12.0;
        out.append("\n").append("NormalFont12 setfont");
        for (int i = 1; i <= 12; i++) {
            out.append("\n").append("newpath")
                    .append("\n").append(interval5 * i).append(" 3 moveto")
                    .append("\n").append(interval5 * i).append(" 23 lineto stroke");
            if (i > 1) {
                out.append("\n").append(interval5 * i - 14).append(" 5 moveto");
            } else {
                out.append("\n").append(interval5 * i - 8).append(" 5 moveto");
            }
            out.append("\n").append("(").append(i * 5).append(") show");

            out.append("\n").append("newpath")
                    .append("\n").append("-3 ").append(-interval5 * i).append(" moveto")
                    .append("\n").append("-23 ").append(-interval5 * i).append(" lineto stroke");
            if (i > 1) {
                out.append("\n").append("-17 ").append(-interval5 * i + 2).append(" moveto");
            } else {
                out.append("\n").append("-11 ").append(-interval5 * i + 2).append(" moveto");
            }
            out.append("\n").append("(").append(i * 5).append(") show");
        }

        out.append("\n").append("newpath")
                .append("\n").append("452 0 moveto")
                .append("\n").append("0 0 lineto")
                .append("\n").append("0 -452 lineto")
                .append("\n").append("0 0 414 270 360 arc clip"); // set clipping

        // draw unit lines
        for (int i = 1; i < 60; i++) {
            if ((i == 5) || (i == 10) || (i == 15) || (i == 20) || (i == 25) || (i == 30) || (i == 35) || (i == 40) || (i == 45) || (i == 50) || (i == 55)) {
                out.append("\n").append("0 setgray")
                        .append("\n").append(".8 setlinewidth");
            } else {
                out.append("\n").append(".4 setlinewidth");
                if (color) {
                    out.append("\n").append("1 0 0 setrgbcolor");
                } else {
                    out.append("\n").append(".5 setgray");
                }
            }
            out.append("\n").append("newpath")
                    .append("\n").append(interval1 * i).append(" 0 moveto")
                    .append("\n").append(interval1 * i).append(" -414 lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 ").append(-interval1 * i).append(" moveto")
                    .append("\n").append("414 ").append(-interval1 * i).append(" lineto stroke");
        }
        out.append("\n").append("0 setgray");

        // draw 45 line
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("414 -414 lineto stroke");

        out.append("\n").append("%% ================ Draw asr lines =================");
        // draw asr lines
        if (color) {
            out.append("\n").append("1 0 0 setrgbcolor");
        } else {
            out.append("\n").append("0 setgray");
        }
        for (int i = 0; i < 100; i++) {
            out.append("\n").append(interval1 * i).append(" ").append(-interval1 * 12).append(" drawSmallX")
                    .append("\n").append(interval1 * 12).append(" ").append(-interval1 * i).append(" drawSmallX")
                    .append("\n").append(interval1 * i).append(" ").append(-interval1 * 7).append(" drawSmallX")
                    .append("\n").append(interval1 * 7).append(" ").append(-interval1 * i).append(" drawSmallX");
        }

        out.append("\n").append("%% ================ Draw Asr start Arc  =================");
        Point2D[] asrLine = AstroMath.defineAsrLine(interval1, 1.0);
        out.append("\n").append("newpath")
                .append("\n").append(asrLine[0].getY()).append(" ").append(-asrLine[0].getX()).append(" moveto");     //todo fix this so it isn't reversed yx
        for (int i = 1; i < 90; i++) {
            out.append("\n").append(asrLine[i].getY()).append(" ").append(-asrLine[i].getX()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getY()).append(" ").append(-asrLine[90].getX()).append(" lineto stroke");

        out.append("\n").append("%% ================ Draw Asr End arc =================");
        asrLine = AstroMath.defineAsrLine(interval1, 2.0);
        out.append("\n").append("newpath")
                .append("\n").append(asrLine[0].getY()).append(" ").append(-asrLine[0].getX()).append(" moveto");    //todo fix this so it isn't reversed yx
        for (int i = 1; i < 90; i++) {
            out.append("\n").append(asrLine[i].getY()).append(" ").append(-asrLine[i].getX()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getY()).append(" ").append(-asrLine[90].getX()).append(" lineto stroke");

        out.append("\n").append("0 setgray")
                .append("\n").append("%% ================ Draw tangent Line =================");
        // draw mystery line
        out.append("\n").append("newpath")
                .append("\n").append("414 0 moveto")
                .append("\n").append("0 -414 lineto stroke");

        out.append("\n").append("%% ================ Draw sine/cosine arcs =================");
        // draw sine/cosine arcs
        if (color) {
            out.append("\n").append("0 0 1 setrgbcolor");
        } else {
            out.append("\n").append("0 setgray");
        }
        out.append("\n").append("207 0 207 180 360 arc stroke")
                .append("\n").append("0 -207 207 270 90 arc stroke")
                .append("\n").append("0 setgray")
                .append("\n").append("%% ================ Draw obliquity arc =================");
        // draw obliquity arc
        // radius is the Sine of the obliquity angle times the radius of the scale
        double obl = AstroMath.obliquity(AstroMath.getT());
        double oblRadius = Math.sin(Math.toRadians(obl)) * 414;
        out.append("\n").append("0 0 ").append(oblRadius).append(" 270 0 arc stroke");

        return out.toString();
    }

    private String drawVernier() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw Vernier Scale =================")
                .append("\n").append("0 0 414 270 360 arc stroke")
                .append("\n").append("0 0 424 270 360 arc stroke")
                .append("\n").append("0 0 434 270 360 arc stroke")
                .append("\n").append("0 0 444 270 360 arc stroke")
                .append("\n").append("0 0 454 270 360 arc stroke")
                .append("\n").append("0 0 464 270 360 arc stroke")
                .append("\n").append("0 0 474 270 360 arc stroke");

        // create 5 degree marks
        for (int count = 1; count < 18; count++) {
            out.append("\n").append("-5 rotate")
                    .append("\n").append("414 0 moveto")
                    .append("\n").append("474 0 lineto stroke");
        }
        out.append("\n").append("85 rotate");

        // draw vernier lines
        double x1;
        double y1;
        double x2;
        double y2;
        double angle;

        for (int i = 90; i < 180; i++) {
            angle = Math.toRadians(i);
            x1 = Math.sin(angle) * 414.0;
            y1 = Math.cos(angle) * 414.0;
            angle = Math.toRadians(i + 1);
            x2 = Math.sin(angle) * 474.0;
            y2 = Math.cos(angle) * 474.0;
            out.append("\n").append(x1).append(" ").append(y1).append(" moveto")
                    .append("\n").append(x2).append(" ").append(y2).append(" lineto stroke");
        }

        return out.toString();
    }


    /**
     * The original is 6 3/8" across one limb, this translates to 459 points
     *
     * @return PostScript code for printing quadrant
     */

    public String printQuadrant() {
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Advanced Sine Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments")

                .append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append(EPSToolKit.fillBackground())
                .append("\n").append("36 626 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append(EPSToolKit.setUpFonts())
                .append(EPSToolKit.setUpSymbols())
                .append(EPSToolKit.setUpCircularText())
                .append("\n").append("gsave")
                .append(drawOutline())
                .append("\n").append("grestore")
                .append("\n").append("36 -36 translate")
                .append("\n").append("gsave")
                .append(drawDegreeScale())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawVernier())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawSineScale())
                .append("\n").append("grestore")
                // Write Footer
                .append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }
} 