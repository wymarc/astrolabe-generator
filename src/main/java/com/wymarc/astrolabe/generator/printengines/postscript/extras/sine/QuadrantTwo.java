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
package com.wymarc.astrolabe.generator.printengines.postscript.extras.sine;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

/**
 * This Plugin will calculate the components of a sine Quadrant and
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org 
 */

public class QuadrantTwo {

    private String drawOutline(){
        //459 limb - 36 = 423 (540)
        StringBuilder out = new StringBuilder();
        out.append("\n").append("% draw outlines")
        .append("\n").append("newpath")
        .append("\n").append("0 0 moveto")
        .append("\n").append("423 0 lineto")
        .append("\n").append("423 -36 lineto stroke")
        .append("\n").append("0 0 moveto")
        .append("\n").append("0 -423 lineto")
        .append("\n").append("36 -423 lineto stroke")
        .append("\n").append("36 -36 387 270 360 arc stroke");

        out.append("\n").append("newpath")
        .append("\n").append("10 -10 moveto")
        .append("\n").append("423 -10 lineto stroke")
        .append("\n").append("newpath")
        .append("\n").append("13 -13 moveto")
        .append("\n").append("423 -13 lineto stroke")
        .append("\n").append("newpath")
        .append("\n").append("13 -33 moveto")
        .append("\n").append("423 -33 lineto stroke")
        .append("\n").append("newpath")
        .append("\n").append("13 -36 moveto")
        .append("\n").append("423 -36 lineto stroke");

        out.append("\n").append("newpath")
        .append("\n").append("10 -10 moveto")
        .append("\n").append("10 -423 lineto stroke")
        .append("\n").append("newpath")
        .append("\n").append("13 -13 moveto")
        .append("\n").append("13 -423  lineto stroke")
        .append("\n").append("newpath")
        .append("\n").append("33 -13 moveto")
        .append("\n").append("33 -423 lineto stroke")
        .append("\n").append("newpath")
        .append("\n").append("36 -13 moveto")
        .append("\n").append("36 -423 lineto stroke");

        return out.toString();
    }

    private String drawDegreeScale(){
        StringBuilder out = new StringBuilder();
        //504 radius  387
        out.append("\n").append("0 0 377 270 360 arc stroke")
        .append("\n").append("0 0 374 270 360 arc stroke")
        .append("\n").append("0 0 350 270 360 arc stroke")
        .append("\n").append("0 0 347 270 360 arc stroke")
        .append("\n").append("0 0 337 270 360 arc stroke");

        // create 1 degree marks
        for (int count = 1; count < 90; count++)
        {
            out.append("\n").append("-1 rotate")
            .append("\n").append("347 0 moveto")
            .append("\n").append("337 0 lineto stroke");
        }
        out.append("\n").append("89 rotate");

        // create 5 degree marks
        for (int count = 1; count < 18; count++)
        {
            out.append("\n").append("-5 rotate")
            .append("\n").append("350 0 moveto")
            .append("\n").append("374 0 lineto stroke");
        }
        out.append("\n").append("85 rotate");

        out.append("\n").append("NormalFont20 setfont");
        //Mark degrees
        for (int count = 5; count <= 85; count = count + 5){
            String text = Integer.toString(count);
            if(count == 5){
                text = "  " + text;
            }
            out.append(EPSToolKit.drawInsideCircularText(text, 20, (-90+count), 368));
        }

        return out.toString();
    }

    private String drawSineScale(){
        StringBuilder out = new StringBuilder();
        double interval1 = 337.0/60.0;
        double interval5 = 337.0/12.0;
        out.append("\n").append("NormalFont12 setfont");
        for (int i=1; i<=12; i++){
            out.append("\n").append("newpath")
            .append("\n").append(interval5*i).append(" 3 moveto")
            .append("\n").append(interval5*i).append(" 23 lineto stroke");
            if (i>1){
                out.append("\n").append(interval5*i-14).append(" 5 moveto");
            }else{
                out.append("\n").append(interval5*i-8).append(" 5 moveto");
            }
            out.append("\n").append("(").append(i*5).append(") show");

            out.append("\n").append("newpath")
            .append("\n").append("-3 ").append(-interval5*i).append(" moveto")
            .append("\n").append("-23 ").append(-interval5*i).append(" lineto stroke");
            if (i>1){
                out.append("\n").append("-17 ").append(-interval5*i+2).append(" moveto");
            }else{
                out.append("\n").append("-11 ").append(-interval5*i+2).append(" moveto");
            }
            out.append("\n").append("(").append(i*5).append(") show");
        }

        out.append("\n").append("newpath")
        .append("\n").append("337 0 moveto")
        .append("\n").append("0 0 lineto")
        .append("\n").append("0 -337 lineto")
        .append("\n").append("0 0 337 270 360 arc clip"); // set clipping

        out.append("\n").append(".5 setgray");
        // draw unit lines
        for (int i=1; i<100; i++){
            out.append("\n").append("newpath")
            .append("\n").append(interval1*i).append(" 0 moveto")
            .append("\n").append(interval1*i).append(" -337 lineto stroke")
            .append("\n").append("newpath")
            .append("\n").append("0 ").append(-interval1*i).append(" moveto")
            .append("\n").append("337 ").append(-interval1*i).append(" lineto stroke");
        }
        out.append("\n").append("0 setgray");

        // draw unit lines
        for (int i=0; i<20; i++){
            for (int j=0; j<100; j++){
                out.append("\n").append(interval1*j).append(" ").append(-interval5*i).append(" drawX")
                .append("\n").append(interval5*i).append(" ").append(-interval1*j).append(" drawX");
            }
        }

        // draw asr lines
        //out.append("\n").append(".5 setgray";
        for (int i=0; i<100; i++){
            out.append("\n").append(interval1*i).append(" ").append(-interval1*12).append(" drawSmallX")
            .append("\n").append(interval1*12).append(" ").append(-interval1*i).append(" drawSmallX");
        }

        Point2D[] asrLine = AstroMath.defineAsrLine(interval1, 1.0);
        out.append("\n").append("newpath")
        .append("\n").append(asrLine[0].getX()).append(" ").append(-asrLine[0].getY()).append(" moveto");
        for (int i=1; i<90; i++){
            out.append("\n").append(asrLine[i].getX()).append(" ").append(-asrLine[i].getY()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getX()).append(" ").append(-asrLine[90].getY()).append(" lineto stroke");

        // draw sine/cosine arcs
        out.append("\n").append("168.5 0 168.5 180 360 arc stroke")
        .append("\n").append("0 -168.5 168.5 270 90 arc stroke");

        // draw obliquity arc
        // radius is the Sine of the obliquity angle times the radius of the scale
        double obl = AstroMath.obliquity(AstroMath.getT());
        double oblRadius = Math.sin(Math.toRadians(obl)) * 337;
        out.append("\n").append("0 0 ").append(oblRadius).append(" 270 0 arc stroke");

        return out.toString();
    }

    public String print2Quadrants(){
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
        .append("\n").append("%%BoundingBox: 0 0 612 792")
        .append("\n").append("%%Title: Horary Quadrant")
        .append("\n").append("%%Creator: Richard Wymarc")
        .append("\n").append("%%CreationDate: ")
        .append("\n").append("%%EndComments");

        out.append("\n").append("mark")
        .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
        .append("\n").append("Quadrant begin")
        .append("\n").append("")
        .append("\n").append("%% setup")
        .append(EPSToolKit.fillBackground())
        .append("\n").append("36 756 translate")
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
                .append(drawSineScale())
        .append("\n").append("grestore");

        out.append("\n").append("180 rotate")
        .append("\n").append("-500 692 translate");

        out.append("\n").append("gsave")
                .append(drawOutline())
        .append("\n").append("grestore")
        .append("\n").append("36 -36 translate")
        .append("\n").append("gsave")
                .append(drawDegreeScale())
        .append("\n").append("grestore")
        .append("\n").append("gsave")
                .append(drawSineScale())
        .append("\n").append("grestore");

        // Write Footer
        out.append("\n").append("% Eject the page")
        .append("\n").append("end cleartomark")
        .append("\n").append("showpage");

        return out.toString();

    }

    /**
     * The original is 6 3/8" across one limb, this translates to 459 points
     *
     * @return Postscript code for printing quadrant
     */

    public String printQuadrant(){
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
        .append("\n").append("%%BoundingBox: 0 0 612 792")
        .append("\n").append("%%Title: Horary Quadrant")
        .append("\n").append("%%Creator: Richard Wymarc")
        .append("\n").append("%%CreationDate: ")
        .append("\n").append("%%EndComments");

        out.append("\n").append("mark")
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
        .append(drawSineScale())
        .append("\n").append("grestore");
        // Write Footer
        out.append("\n").append("% Eject the page")
        .append("\n").append("end cleartomark")
        .append("\n").append("showpage");

        return out.toString();

    }
} 