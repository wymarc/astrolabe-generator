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
package com.wymarc.astrolabe.generator.printengines.postscript.extras;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;

/**
 * This Plugin will calculate the lines of a dastur and 
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org
 */

public class Dastur {

    public String createDastur() {
        StringBuilder out = new StringBuilder();

        int count = 0;
        int Ri = 0;

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Dastur")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments");

        out.append(EPSToolKit.setUpFonts());

        out.append("\n").append("mark")
                .append("\n").append("/Dastur 10 dict def %local variable dictionary")
                .append("\n").append("Dastur begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append(EPSToolKit.fillBackground())
                .append("\n").append("135 425 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append("\n").append("gsave")
                .append("\n").append("");

        // define box
        out.append("\n").append("/boxpath")
                .append("\n").append("{ newpath")
                .append("\n").append("0 324 moveto")
                .append("\n").append("0 -324 lineto")
                .append("\n").append("324 -324 lineto")
                .append("\n").append("324 324 lineto")
                .append("\n").append("0 324 lineto")
                .append("\n").append("closepath } def");

        // define 1/2 degree box
        out.append("\n").append("/boxpathhalfdegree")
                .append("\n").append("{ newpath")
                .append("\n").append("0 320 moveto")
                .append("\n").append("0 -320 lineto")
                .append("\n").append("320 -320 lineto")
                .append("\n").append("320 320 lineto")
                .append("\n").append("0 320 lineto")
                .append("\n").append("closepath } def");

        // define degree box
        out.append("\n").append("/boxpathdegree")
                .append("\n").append("{ newpath")
                .append("\n").append("0 315 moveto")
                .append("\n").append("0 -315 lineto")
                .append("\n").append("315 -315 lineto")
                .append("\n").append("315 315 lineto")
                .append("\n").append("0 315 lineto")
                .append("\n").append("closepath } def");

        // set up clipping path
        out.append("\n").append("boxpath clip");

        // create 1/2 degree marks
        for (count = 1; count <= 540; count++) {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append("0 500 lineto stroke")
                    .append("\n").append("-.25 rotate");
        }
        out.append("\n").append("135 rotate")
                .append("\n").append("1 setgray")
                .append("\n").append("boxpathhalfdegree fill")
                .append("\n").append("0 setgray");

        // create 1 degree marks
        for (count = 1; count <= 270; count++) {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append("0 500 lineto stroke")
                    .append("\n").append("-.5 rotate");
        }
        out.append("\n").append("135 rotate")
                .append("\n").append("1 setgray")
                .append("\n").append("boxpathdegree fill")
                .append("\n").append("0 setgray");

        // create 5 degree marks
        for (count = 1; count <= 54; count++) {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append("0 500 lineto stroke")
                    .append("\n").append("-2.5 rotate");
        }
        out.append("\n").append("135 rotate")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 290 0 360 arc fill")
                .append("\n").append("0 setgray");

        // create 10 degree marks
        for (count = 1; count <= 28; count++) {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append("0 500 lineto stroke")
                    .append("\n").append("-5 rotate");
        }
        out.append("\n").append("135 rotate")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 80 0 360 arc fill")
                .append("\n").append("0 setgray");

        out.append("\n").append("grestore")
                .append("\n").append("gsave");

        // horiz line
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("324 0 lineto stroke");

        out.append("\n").append("grestore")
                .append("\n").append("");

        // draw Box
        out.append("\n").append("% draw box outline")
                .append("\n").append("boxpath stroke")
                .append("\n").append("");

        // Label
        out.append("\n").append("gsave")
                .append("\n").append("%% Label");
        for (count = 0; count <= 9; count++) {
            out.append("\n").append("newpath")
                    .append("\n").append("NormalFont10 setfont")
                    .append("\n 290 1 moveto")
                    .append("\n").append("(").append(count * 10).append(") show")
                    .append("\n").append("")
                    .append("\n").append("-5 rotate");
        }
        out.append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("%% Label");
        for (count = 1; count <= 17; count++) {
            out.append("\n").append("5 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("NormalFont10 setfont")
                    .append("\n 290 1 moveto")
                    .append("\n").append("(").append(count * 10).append(") show")
                    .append("\n").append("");
        }
        out.append("\n").append("grestore");

        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }
}