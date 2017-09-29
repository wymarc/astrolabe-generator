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
package com.wymarc.astrolabe.generator.printengines.postscript.extras;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;

public class DemoArms {

    private String drawQuadrantScale(double scaleLength){
        String out = "";
        double interval1 = scaleLength/60.0;
        double interval5 = scaleLength/12.0;

        // draw scale
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 15 lineto";
        out += "\n" + scaleLength + " 15 lineto";
        out += "\n" + scaleLength + " 0 lineto";
        out += "\n" + "0 0 lineto stroke";
        out += "\n" + "0 3 moveto";
        out += "\n" + scaleLength + " 3 lineto stroke";

        // mark and label
        out += "\n" + "NormalFont12 setfont";
        for (int i=1; i<=12; i++){
            out += "\n" + "newpath";
            out += "\n" + interval5*i + " 3 moveto";
            out += "\n" + interval5*i + " 15 lineto stroke";
            if (i>1){
                out += "\n" + (interval5*i-14) + " 5 moveto";
            }else{
                out += "\n" + (interval5*i-8) + " 5 moveto";
            }
            out += "\n" + "(" + (i*5) + ") show";
        }
        for (int i=1; i<=60; i++){
            out += "\n" + "newpath";
            out += "\n" + interval1*i + " 0 moveto";
            out += "\n" + interval1*i + " 3 lineto stroke";
        }


        // draw ends
        out += "\n" + scaleLength + " 15 moveto";
        out += "\n" + (scaleLength+24.0) + " 15 lineto stroke";
        out += "\n" + (scaleLength+24.0) + " 0 15 270 90 arc stroke";
        out += "\n" + (scaleLength+24.0) + " -15 moveto";
        out += "\n" + "0 -15 lineto stroke";
        out += "\n" + "0 0 15 90 270 arc stroke";
        out += "\n" + scaleLength + " 0 moveto";
        out += "\n" + (scaleLength+24.0) + " 0 lineto stroke";
        //draw pointer
        out += "\n" + (scaleLength+15.0) + " 0 moveto";
        out += "\n" + (scaleLength+24.0) + " 6 lineto";
        out += "\n" + (scaleLength+24.0) + " -6 lineto";
        out += "\n" + (scaleLength+15.0) + " 0 lineto fill";

        return out;
    }

    private String drawQuadrantOneSineScale(){
        String out = "";
        double scaleLength = 403.0;
        // draw box 403
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 15 lineto";
        out += "\n" + scaleLength + " 15 lineto";
        out += "\n" + scaleLength + " 0 lineto";
        out += "\n" + "0 0 lineto stroke";
        out += "\n" + "0 5 moveto";
        out += "\n" + scaleLength + " 5 lineto stroke";

        // draw ends
        out += "\n" + scaleLength + " 15 moveto";
        out += "\n" + (scaleLength+15.0) + " 5 lineto";
        out += "\n" + (scaleLength+15.0) + " 0 lineto";
        out += "\n" + scaleLength + " 0 lineto stroke";
        out += "\n" + (scaleLength+15.0) + " 0 moveto";
        out += "\n" + (scaleLength+15.0) + " -15 lineto";
        out += "\n" + "0 -15 lineto stroke";
        out += "\n" + "0 0 15 90 270 arc stroke";

        //mark ticks
        double interval = scaleLength/100.0;
        for(int i = 1; i<100; i++){
            out += "\n" + interval*i + " 0 moveto";
            if(i == 10 || i == 20 || i == 30 || i == 40 || i == 50 || i == 60 || i == 70 || i == 80 || i == 90){
                out += "\n" + interval*i + " 15 lineto stroke";
                out += "\n" + "NormalFont8 setfont";
                out += "\n" + (interval*i) + " 7 moveto";
                out += EPSToolKit.centerText(i+"");
            }else{
                out += "\n" + interval*i + " 5 lineto stroke";
            }
        }

        return out;
    }


    public String printArms(){
        String out = "";

        // Write header to file
        out += "%!PS-Adobe-3.0 EPSF-30.";
        out += "\n" + "%%BoundingBox: 0 0 612 792";
        out += "\n" + "%%Title: Quadrant demo arms";
        out += "\n" + "%%Creator: Richard Wymarc";
        out += "\n" + "%%CreationDate: ";
        out += "\n" + "%%EndComments";

        out += "\n" + "mark";
        out += "\n" + "/Quadrant 10 dict def %local variable dictionary";
        out += "\n" + "Quadrant begin";
        out += "\n" + "";
        out += "\n" + "%% setup";
        out += EPSToolKit.fillBackground();
        out += "\n" + "72 730 translate";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += "\n" + "";

        for(int i = 1; i<5; i++){
            out += "\n" + "gsave";
            out += drawQuadrantScale(452.0);
            out += "\n" + "grestore";
            out += "\n" + "0 -60 translate";
        }
        for(int i = 1; i<5; i++){
            out += "\n" + "gsave";
            out += drawQuadrantScale(337.0);
            out += "\n" + "grestore";
            out += "\n" + "0 -60 translate";
        }
        for(int i = 1; i<5; i++){
            out += "\n" + "gsave";
            out += drawQuadrantOneSineScale();
            out += "\n" + "grestore";
            out += "\n" + "0 -60 translate";
        }

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;

    }

}
