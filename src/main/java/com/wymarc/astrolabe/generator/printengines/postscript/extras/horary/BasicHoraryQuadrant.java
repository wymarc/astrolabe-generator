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

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;

/**
 * This Plugin will calculate the components of Horary Quadrant and 
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org 
 */

public class BasicHoraryQuadrant {

    /**
     * computes and draws the shadow squares
     *
     * @return returns the ps code for drawing the Shadow Squares
     */
    private String buildShadowSquare() {
        //compute size of box
        // note eventually this will be done by looking at what rings are drawn and figuring
        // the remaining radius
        double shadowSide = 191;
        int count;
        double div = 12.0;
        StringBuilder out = new StringBuilder();

        //Draw bottom right
        out.append("\n").append("% =============== Create Right Shadow Square =================")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" 0 lineto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("0 0 moveto");

        for (count = 1; count < div; count++)// print division lines
        {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append(shadowSide).append(" ").append(-((shadowSide / div) * count)).append(" lineto stroke")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append((shadowSide / div) * count).append(" ").append(-shadowSide).append(" lineto stroke");
        }
        out.append("\n").append("newpath")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 6).append(" 0 lineto")
                .append("\n").append(shadowSide - 6).append(" ").append(-(shadowSide - 6)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 6)).append(" lineto")
                .append("\n").append("0 0 lineto fill")
                .append("\n").append("newpath")
                .append("\n").append("0 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 6).append(" 0 lineto")
                .append("\n").append(shadowSide - 6).append(" ").append(-(shadowSide - 6)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 6)).append(" lineto")
                .append("\n").append("0 0 lineto stroke");

        //draw 1/2 way mark
        out.append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide / 2.0).append(" lineto stroke")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide / 2.0).append(" ").append(-shadowSide).append(" lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 12).append(" 0 lineto")
                .append("\n").append(shadowSide - 12).append(" ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 0 lineto fill")
                .append("\n").append("0 setgray")
                .append("\n").append((shadowSide / 2.0) - 8).append(" ").append(-(shadowSide - 7)).append(" moveto")
                .append("\n").append("NormalFont5 setfont");

        // mark 6 line
        out.append("\n").append("(6) show")
                .append("\n").append((shadowSide - 10)).append(" ").append(-(shadowSide / 2.0)).append(" moveto")
                .append("\n").append("(6) show")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 12).append(" 0 lineto")
                .append("\n").append(shadowSide - 12).append(" ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("0 0 moveto");

        // mark 45 line
        out.append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto stroke") // 45 line
                .append("\n").append(shadowSide - 16).append(" ").append(-(shadowSide - 7)).append(" moveto")
                .append("\n").append("NormalFont5 setfont")
                .append("\n").append("(12) show");

        out.append("\n").append("% =============== End Right Shadow Square =================");

        return out.toString();
    }

    public String printQuadrant() {
        StringBuilder out = new StringBuilder();

        int count;
        Double Ri;

        // Write header to file
        out.append("\n").append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Basic Horary Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments");

        out.append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append("\n").append(EPSToolKit.fillBackground())
                .append("\n").append("108 630 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append("\n").append(EPSToolKit.setUpFonts())
                .append("\n").append(EPSToolKit.setUpCircularText())
                .append("\n").append("gsave")
                .append("\n").append("");

        // draw limbs
        out.append("\n").append("% draw Limb")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("0 -432 rlineto")
                .append("\n").append("36 0 rlineto")
                .append("\n").append("0 396 rlineto")
                .append("\n").append("396 0 rlineto")
                .append("\n").append("0 36 rlineto")
                .append("\n").append("-432 0 rlineto stroke")
                .append("\n").append("");

        // draw quadrants
        out.append("\n").append("% quadrants")
                .append("\n").append("36 -36 translate")
                .append("\n").append("0 0 5 0 360 arc stroke")
                .append("\n").append("0 0 396 270 360 arc stroke")
                .append("\n").append("0 0 386 270 360 arc stroke")
                .append("\n").append("0 0 382 270 360 arc stroke")
                .append("\n").append("");

        // create 1 degree marks
        for (count = 1; count <= 90; count++) {
            out.append("\n").append("382 0 moveto")
                    .append("\n").append("386 0 lineto stroke")
                    .append("\n").append("-1 rotate");
        }
        out.append("\n").append("90 rotate");

        // create 5 degree marks
        for (count = 1; count <= 18; count++) {
            out.append("\n").append("382 0 moveto")
                    .append("\n").append("390 0 lineto stroke")
                    .append("\n").append("-5 rotate");
        }
        out.append("\n").append("90 rotate");

        // create 10 degree marks
        for (count = 1; count <= 9; count++) {
            out.append("\n").append("382 0 moveto")
                    .append("\n").append("396 0 lineto stroke")
                    .append("\n").append("-10 rotate");
        }
        out.append("\n").append("90 rotate");

        //Mark degrees
        out.append("\n").append("/Times-Roman findfont 5 scalefont setfont");
        for (count = 1; count <= 9; count++) {
            out.append("\n").append(EPSToolKit.drawInsideCircularText((count * 10) + "", 5, (-90 + (count * 10) - 0.5), 392));
        }

        //Shadow Square
        out.append("\n").append(buildShadowSquare());

        // mark unequal hour lines
        out.append("\n").append("newpath")
                .append("\n").append("382 0 moveto")
                .append("\n").append("0 0 lineto")
                .append("\n").append("0 -382 lineto")
                .append("\n").append("0 0 382 270 360 arc clip");
        for (count = 1; count <= 6; count++) {
            Ri = (382 / (2 * (Math.sin(Math.toRadians(15 * count)))));
            out.append("\n").append(Ri).append(" 0 ").append(Ri).append(" 180 360 arc stroke");
        }

        //Mark unequal hours
        out.append("\n").append("/Times-Roman findfont 5 scalefont setfont");
        for (count = 1; count <= 5; count++) {
            out.append("\n").append(EPSToolKit.drawInsideCircularText(count + "", 5, (-90 + (count * 15) + 0.5), 380));
        }

        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }
} 