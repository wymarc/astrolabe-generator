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
 * This Plugin will calculate the components of the Sine Quadrant and
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org 
 */
 
public class SineQuadrant {

    boolean color = false;

    private String drawOutline(){
        //459 limb - 36 = 423 (540)
        String out = "";
        out += "\n" + "%% ================ Draw Outlines =================";
        out += "\n" + "newpath";
        out += "\n" + "0 0 moveto";
        out += "\n" + "540 0 lineto";
        out += "\n" + "540 -36 lineto stroke";
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 -540 lineto";
        out += "\n" + "36 -540 lineto stroke";
        out += "\n" + "36 -36 504 270 360 arc stroke";

        out += "\n" + "newpath";
        out += "\n" + "10 -10 moveto";
        out += "\n" + "540 -10 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "13 -13 moveto";
        out += "\n" + "540 -13 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "13 -33 moveto";
        out += "\n" + "540 -33 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "13 -36 moveto";
        out += "\n" + "540 -36 lineto stroke";

        out += "\n" + "newpath";
        out += "\n" + "10 -10 moveto";
        out += "\n" + "10 -540 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "13 -13 moveto";
        out += "\n" + "13 -540  lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "33 -13 moveto";
        out += "\n" + "33 -540 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "36 -13 moveto";
        out += "\n" + "36 -540 lineto stroke";

        // sight
        double interval5 = 452.0/12.0;
        out += "\n" + "newpath";
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 36 lineto";
        out += "\n" + interval5*4 + " 36 lineto";
        out += "\n" + interval5*4 + " 18 lineto";
        out += "\n" + interval5*10 + " 18 lineto";
        out += "\n" + interval5*10 + " 36 lineto";
        out += "\n" + "540 36 lineto";
        out += "\n" + "540 0 lineto stroke";
        out += "\n" + "%% ================ End Draw Outlines =================";

        return out;
    }

    private String drawDegreeScale(){
        String out = "";
        //504 radius  387
        out += "\n" + "%% ================ Draw Degree Scale =================";
        out += "\n" + "0 0 494 270 360 arc stroke";
        out += "\n" + "0 0 491 270 360 arc stroke";
        out += "\n" + "0 0 467 270 360 arc stroke";
        out += "\n" + "0 0 464 270 360 arc stroke";
        out += "\n" + "0 0 458 270 360 arc stroke";
        out += "\n" + "0 0 452 270 360 arc stroke";

        // create 1/2 degree marks
        for (int count = 1; count < 180; count++)
        {
            out += "\n" + "-0.5 rotate";
            out += "\n" + "458 0 moveto";
            out += "\n" + "452 0 lineto stroke";
        }
        out += "\n" + "89.5 rotate";

        // create 1 degree marks
        for (int count = 1; count < 90; count++)
        {
            out += "\n" + "-1 rotate";
            out += "\n" + "464 0 moveto";
            out += "\n" + "458 0 lineto stroke";
        }
        out += "\n" + "89 rotate";

        // create 5 degree marks
        for (int count = 1; count < 18; count++)
        {
            out += "\n" + "-5 rotate";
            out += "\n" + "467 0 moveto";
            out += "\n" + "491 0 lineto stroke";
        }
        out += "\n" + "85 rotate";

        out += "\n" + "NormalFont20 setfont";
        //Mark degrees
        for (int count = 5; count <= 85; count = count + 5){
            String text = Integer.toString(count);
            if(count == 5){
                text = "  " + text;
            }
            out += EPSToolKit.drawInsideCircularText(text, 20, (-90+count), 487);
        }
        out += "\n" + "%% ================ End Draw Degree Scale =================";
        return out;
    }

   private String drawSineScale(){
       String out = "";
       out += "\n" + "%% ================ Draw Sine Scale =================";
       double interval1 = 452.0/60.0;
       double interval5 = 452.0/12.0;
       out += "\n" + "NormalFont12 setfont";
       for (int i=1; i<=12; i++){
           out += "\n" + "newpath";
           out += "\n" + interval5*i + " 3 moveto";
           out += "\n" + interval5*i + " 23 lineto stroke";
           if (i>1){
               out += "\n" + (interval5*i-14) + " 5 moveto";
           }else{
               out += "\n" + (interval5*i-8) + " 5 moveto";
           }
           out += "\n" + "(" + (i*5) + ") show";

           out += "\n" + "newpath";
           out += "\n" + "-3 " + (-interval5*i) + " moveto";
           out += "\n" + "-23 " + (-interval5*i) + " lineto stroke";
           if (i>1){
               out += "\n" + "-17 " + (-interval5*i+2) + " moveto";
           }else{
               out += "\n" + "-11 " + (-interval5*i+2) + " moveto";
           }
           out += "\n" + "(" + (i*5) + ") show";
       }

       out += "\n" + "newpath";
       out += "\n" + "452 0 moveto";
       out += "\n" + "0 0 lineto";
       out += "\n" + "0 -452 lineto";
       out += "\n" + "0 0 452 270 360 arc clip"; // set clipping

       // draw unit lines
       for (int i=1; i<60; i++){
           if ((i==5)||(i==10)||(i==15)||(i==20)||(i==25)||(i==30)||(i==35)||(i==40)||(i==45)||(i==50)||(i==55)){
               out += "\n" + "0 setgray";
               out += "\n" + ".8 setlinewidth";
           }else{
               out += "\n" + ".4 setlinewidth";
               out += "\n" + ".5 setgray";
           }
           out += "\n" + "newpath";
           out += "\n" + interval1*i +" 0 moveto";
           out += "\n" + interval1*i +" -452 lineto stroke";
           out += "\n" + "newpath";
           out += "\n" + "0 " + (-interval1*i) +" moveto";
           out += "\n" + "452 " + (-interval1*i) + " lineto stroke";
       }
       out += "\n" + "0 setgray";

       // draw 45 line
       out += "\n" + "newpath";
       out += "\n" + "0 0 moveto";
       out += "\n" + "452 -452 lineto stroke";

       out += "\n" + "%% ================ Draw asr lines =================";
       // draw asr lines
       for (int i=0; i<100; i++){
           out += "\n" + interval1*i + " " + -interval1*12 + " drawSmallX";
           out += "\n" + interval1*12 + " " + -interval1*i + " drawSmallX";
           out += "\n" + interval1*i + " " + -interval1*7 + " drawSmallX";
           out += "\n" + interval1*7 + " " + -interval1*i + " drawSmallX";
       }

       out += "\n" + "%% ================ Draw Asr start Arc  =================";
       if (color){
           out += "\n" + "1 0 0 setrgbcolor";
       }else{
           out += "\n" + "0 setgray";
       }
       Point2D[] asrLine = AstroMath.defineAsrLine(interval1, 1.0);
       out += "\n" + "newpath";
       out += "\n" + asrLine[0].getY() + " " + (-asrLine[0].getX()) + " moveto";     //todo fix this so it isn't reversed yx
       for (int i=1; i<90; i++){
           out += "\n" + asrLine[i].getY() + " " + (-asrLine[i].getX()) + " lineto";
       }
       out += "\n" + asrLine[90].getY() + " " + (-asrLine[90].getX()) + " lineto stroke";

       out += "\n" + "%% ================ Draw Asr End arc =================";
       asrLine = AstroMath.defineAsrLine(interval1, 2.0);
       out += "\n" + "newpath";
       out += "\n" + asrLine[0].getY() + " " + (-asrLine[0].getX()) + " moveto";     //todo fix this so it isn't reversed yx
       for (int i=1; i<90; i++){
           out += "\n" + asrLine[i].getY() + " " + (-asrLine[i].getX()) + " lineto";
       }
       out += "\n" + asrLine[90].getY() + " " + (-asrLine[90].getX()) + " lineto stroke";

       out += "\n" + "0 setgray";
       out += "\n" + "%% ================ Draw tangent Line =================";
       // draw mystery line
       out += "\n" + "newpath";
       out += "\n" + "452 0 moveto";
       out += "\n" + "0 -452 lineto stroke";

       out += "\n" + "%% ================ Draw sine/cosine arcs =================";
       // draw sine/cosine arcs
       if (color){
           out += "\n" + "0 0 1 setrgbcolor";
       }else{
           out += "\n" + "0 setgray";
       }
       out += "\n" + "226 0 226 180 360 arc stroke";
       out += "\n" + "0 -226 226 270 90 arc stroke";
       out += "\n" + "0 setgray";
       out += "\n" + "%% ================ Draw obliquity arc =================";
       // draw obliquity arc
       // radius is the Sine of the obliquity angle times the radius of the scale
       if (color){
           out += "\n" + "1 0 1 setrgbcolor";
       }else{
           out += "\n" + "0 setgray";
       }
       double obl = AstroMath.obliquity(AstroMath.getT());
       double oblRadius = Math.sin(Math.toRadians(obl)) * 452;
       out += "\n" + "0 0 " + oblRadius + " 270 0 arc stroke";

       return out;
   }

    public String print2Quadrants(){
        String out = "";

        // Write header to file
        out += "%!PS-Adobe-3.0 EPSF-30.";
        out += "\n" + "%%BoundingBox: 0 0 612 792";
        out += "\n" + "%%Title: Horary Quadrant";
        out += "\n" + "%%Creator: Richard Wymarc";
        out += "\n" + "%%CreationDate: ";
        out += "\n" + "%%EndComments";

        out += "\n" + "mark";
        out += "\n" + "/Quadrant 10 dict def %local variable dictionary";
        out += "\n" + "Quadrant begin";
        out += "\n" + "";
        out += "\n" + "%% setup";
        out += EPSToolKit.fillBackground();
        out += "\n" + "36 756 translate";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpSymbols();
        out += EPSToolKit.setUpCircularText();
        out += "\n" + "gsave";
        out += drawOutline();
        out += "\n" + "grestore";
        out += "\n" + "36 -36 translate";
        out += "\n" + "gsave";
        out += drawDegreeScale();
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += drawSineScale();
        out += "\n" + "grestore";

        out += "\n" + "180 rotate";
        out += "\n" + "-500 692 translate";

        out += "\n" + "gsave";
        out += drawOutline();
        out += "\n" + "grestore";
        out += "\n" + "36 -36 translate";
        out += "\n" + "gsave";
        out += drawDegreeScale();
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += drawSineScale();
        out += "\n" + "grestore";
        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;

    }


    /**
     * The original is 6 3/8" across one limb, this translates to 459 points
     *
     * @return eps code to print out sine quadrant
     */

    public String printQuadrant(boolean useColor){
	    this.color = useColor;

        String out = "";
	    
	    // Write header to file
	    out += "%!PS-Adobe-3.0 EPSF-30.";
	    out += "\n" + "%%BoundingBox: 0 0 612 792";
        if (this.color){
            out += "\n" + "%%Title: Color Sine Quadrant";
        }else{
            out += "\n" + "%%Title: Sine Quadrant";
        }
	    out += "\n" + "%%Creator: Richard Wymarc";
	    out += "\n" + "%%CreationDate: ";
	    out += "\n" + "%%EndComments";
	
	    out += "\n" + "mark";
	    out += "\n" + "/Quadrant 10 dict def %local variable dictionary";
	    out += "\n" + "Quadrant begin";
	    out += "\n" + "";
	    out += "\n" + "%% setup";
	    out += EPSToolKit.fillBackground();
	    out += "\n" + "36 626 translate";
	    out += "\n" + ".4 setlinewidth";
	    out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpSymbols();
	    out += EPSToolKit.setUpCircularText();
        out += "\n" + "gsave";
        out += drawOutline();
        out += "\n" + "grestore";
        out += "\n" + "36 -36 translate";
	    out += "\n" + "gsave";
	    out += drawDegreeScale();
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += drawSineScale();
        out += "\n" + "grestore";
        // Write Footer
	    out += "\n" + "% Eject the page";
	    out += "\n" + "end cleartomark";
	    out += "\n" + "showpage";
	    
	    return out;
	        
	}
} 