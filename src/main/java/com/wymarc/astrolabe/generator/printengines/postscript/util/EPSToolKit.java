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
package com.wymarc.astrolabe.generator.printengines.postscript.util;

import com.wymarc.astrolabe.Astrolabe;

import java.util.Date;

public class EPSToolKit {

    /**
     * generates EPS header code for file
     *
     * @param  myAstrolabe The current Astrolabe
     * @param  Title The files title
     * @return String EPS output
     */
    public static String getHeader(Astrolabe myAstrolabe, String Title){

        Date Now = new Date();

        String out = "%!PS-Adobe-3.0 EPSF-30.";
        out += "\n" + "%%BoundingBox: 0 0 612 792";
        out += "\n" + "%%Title: " + Title;
        out += "\n" + "%%Creator: Richard Wymarc";
        out += "\n" + "%%CreationDate: " + Now.toString();
        out += "\n" + "%%EndComments";
        out += "\n" + "%% Astrolabe Generator Postscript file";
        out += "\n" + "%% for Latitude: " + myAstrolabe.getLocation().getLatitude();
        out += "\n" + "%% for Longitude: " + myAstrolabe.getLocation().getLongitude();
        out += "\n" + "";
        out += "\n" + "mark";
        out += "\n" + "/Astrolabe 10 dict def %local variable dictionary";
        out += "\n" + "Astrolabe begin";
        out += "\n" + "";
        return out;
    }

    public static String setClippingBox(double x1, double y1, double x2, double y2){
        String out = "";
        out += "\n" + "newpath";
        out += "\n" + x1 + " " + y1 + " moveto";
        out += "\n" + x1 + " " + y2 + " lineto";
        out += "\n" + x2 + " " + y2 + " lineto";
        out += "\n" + x2 + " " + y1 + " lineto";
        out += "\n" + "closepath clip";

        return out;
    }



    /**
     * Fills the page (8.5" x 11") with white.
     *
     * @return String EPS output
     */
    public static String fillBackground(){
        // Fills the page (8.5" x 11") with white.    
        String out = "";
        out += "\n" + "% Fill Background";
        out += "\n" + "1 setgray";
        out += "\n" + "newpath";
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 792 lineto";
        out += "\n" + "612 792 lineto";
        out += "\n" + "612 0 lineto";
        out += "\n" + "0 0 lineto";
        out += "\n" + "fill";
        out += "\n" + "0 setgray";

        return out;
    }

    /**
     * Draws four registration marks to ease assembly and fills background
     *
     * @return String EPS output
     */
    public static String registrationMarks(){
        String out = "";
        out += "\n" + "%% ================ Draw Registration Marks =================";
        // Fills the page (8.5" x 11") with white.    
        out += fillBackground();

        // Marks the page (8.5" x 11") with corner Reg marks
        out += "\n" + "% Registration Marks";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "36 36 5 0 360 arc stroke";
        out += "\n" + "30 36 moveto";
        out += "\n" + "50 36 lineto stroke";
        out += "\n" + "36 30 moveto";
        out += "\n" + "36 50 lineto stroke";
        out += "\n" + "576 36 5 0 360 arc stroke";
        out += "\n" + "582 36 moveto";
        out += "\n" + "562 36 lineto stroke";
        out += "\n" + "576 30 moveto";
        out += "\n" + "576 50 lineto stroke";
        out += "\n" + "576 756 5 0 360 arc stroke";
        out += "\n" + "582 756 moveto";
        out += "\n" + "562 756 lineto stroke";
        out += "\n" + "576 762 moveto";
        out += "\n" + "576 742 lineto stroke";
        out += "\n" + "36 756 5 0 360 arc stroke";
        out += "\n" + "30 756 moveto";
        out += "\n" + "50 756 lineto stroke";
        out += "\n" + "36 762 moveto";
        out += "\n" + "36 742 lineto stroke";
        //612 x 792
        out += "\n" + "%% ================ End Registration Marks =================";
        return out;
    }

    /**
     * Centers a given text string at the current point
     *
     * @param textString String to be centered
     * @return Centered string EPS output
     */
    public static String centerText(String textString){
        return "\n" + "(" + textString + ") dup stringwidth pop 2 div neg 0 rmoveto show";
    }

    /**
     * Sets up fonts
     *
     * Run once at the beginning of the PS/EPS  file, after the header code
     *
     * @return String EPS output
     *
     */
    public static String setUpFonts(){
        String out = "";
        out += "\n" + "%% ================ Set Up Fonts =================";
        out += "\n" + "/NormalFont /Times-Roman findfont";
        out += "\n" + "dup length dict begin {1 index /FID ne {def} {pop pop} ifelse} forall";
        out += "\n" + "/Encoding ISOLatin1Encoding def currentdict end";
        out += "\n" + "/Times-Roman-ISOLatin1 exch definefont";
        out += "\n" + "def";
        out += "\n" + "/NormalFont5 NormalFont 5 scalefont def";
        out += "\n" + "/NormalFont6 NormalFont 6 scalefont def";
        out += "\n" + "/NormalFont7 NormalFont 7 scalefont def";
        out += "\n" + "/NormalFont8 NormalFont 8 scalefont def";
        out += "\n" + "/NormalFont10 NormalFont 10 scalefont def";
        out += "\n" + "/NormalFont12 NormalFont 12 scalefont def";
        out += "\n" + "/NormalFont14 NormalFont 14 scalefont def";
        out += "\n" + "/NormalFont16 NormalFont 16 scalefont def";
        out += "\n" + "/NormalFont18 NormalFont 18 scalefont def";
        out += "\n" + "/NormalFont20 NormalFont 20 scalefont def";

        out += "\n" + "%% ================ End Set Up Fonts =================";

        return out;
    }

    /**
     * Sets up PS routines to print rotated text
     *
     * Run once at the beginning of the PS/EPS  file, after the header code
     *
     * @return String EPS output
     */
    public static String setUpTextRotation(){
        String out = "";
        out += "\n" + "%% ================ Set Up Rotated Text Routine =================";
        out += "\n" + "% text rotation function";
        out += "\n" + "% x y fontsize rotation (text) outputtext";
        out += "\n" + "% ex: 20 300 12 80 (text1) outputtext";
        out += "\n" + "/outputtext {";
        out += "\n" + "   /data exch def";
        out += "\n" + "   /rot exch def";
        out += "\n" + "   /xfont exch def";
        out += "\n" + "   /y1 exch def";
        out += "\n" + "   /x1 exch def";
        out += "\n" + "   /Times-Roman findfont";
        out += "\n" + "   xfont scalefont";
        out += "\n" + "   setfont";
        out += "\n" + "   x1 y1 moveto";
        out += "\n" + "   rot rotate";
        out += "\n" + "   data show";
        out += "\n" + "   rot neg rotate";
        out += "\n" + "} def";
        out += "\n";

        return out;
    }

    /**
     * Sets up a set of PS routines to print circular text
     *
     * Postscript routine gleefully stolen and modified from the "Blue Book":
     * PostScript Language Tutorial and Cookbook, Adobe Systems Inc.
     *
     * Run once at the beginning of the PS/EPS  file, after the header code
     *
     * @return String EPS output
     */
    public static String setUpCircularText(){
        String out = "";
        out += "\n" + "%% ================ Set Up Circular Text Routine =================";
        out += "\n" + "/outsidecircletext";
        out += "\n" + "{ circtextdict begin";
        out += "\n" + "/radius exch def";
        out += "\n" + "/centerangle exch def";
        out += "\n" + "/ptsize exch def";
        out += "\n" + "/str exch def";
        out += "\n" + "/xradius radius ptsize 4 div add def";
        out += "\n" + "";
        out += "\n" + "gsave";
        out += "\n" + "centerangle str findhalfangle add rotate";
        out += "\n" + "";
        out += "\n" + "str";
        out += "\n" + "{ /charcode exch def";
        out += "\n" + "( ) dup 0 charcode put outsideplacechar";
        out += "\n" + "} forall";
        out += "\n" + "grestore";
        out += "\n" + "end";
        out += "\n" + "} def";
        out += "\n" + "";
        out += "\n" + "/insidecircletext";
        out += "\n" + "{ circtextdict begin";
        out += "\n" + "/radius exch def /centerangle exch def";
        out += "\n" + "/ptsize exch def /str exch def";
        out += "\n" + "/xradius radius ptsize 3 div sub def";
        out += "\n" + "gsave";
        out += "\n" + "centerangle str findhalfangle sub rotate";
        out += "\n" + "str";
        out += "\n" + "{ /charcode exch def";
        out += "\n" + "( ) dup 0 charcode put insideplacechar";
        out += "\n" + "} forall";
        out += "\n" + "grestore";
        out += "\n" + "end";
        out += "\n" + "} def";
        out += "\n" + "";
        out += "\n" + "/circtextdict 16 dict def";
        out += "\n" + "circtextdict begin";
        out += "\n" + "/findhalfangle";
        out += "\n" + "{ stringwidth pop 2 div";
        out += "\n" + "2 xradius mul pi mul div 360 mul";
        out += "\n" + "} def";
        out += "\n" + "";
        out += "\n" + "/outsideplacechar";
        out += "\n" + "{ /char exch def";
        out += "\n" + "/halfangle char findhalfangle def";
        out += "\n" + "gsave";
        out += "\n" + "halfangle neg rotate";
        out += "\n" + "radius 0 translate";
        out += "\n" + "-90 rotate";
        out += "\n" + "char stringwidth pop 2 div neg 0 moveto";
        out += "\n" + "char show";
        out += "\n" + "grestore";
        out += "\n" + "halfangle 2 mul neg rotate";
        out += "\n" + "} def";
        out += "\n" + "";
        out += "\n" + "/insideplacechar";
        out += "\n" + "{ /char exch def";
        out += "\n" + "/halfangle char findhalfangle def";
        out += "\n" + "gsave";
        out += "\n" + "halfangle rotate";
        out += "\n" + "radius 0 translate";
        out += "\n" + "90 rotate";
        out += "\n" + "char stringwidth pop 2 div neg 0 moveto";
        out += "\n" + "char show";
        out += "\n" + "grestore";
        out += "\n" + "halfangle 2 mul rotate";
        out += "\n" + "} def";
        out += "\n" + "/pi 3.1415923 def";
        out += "\n" + "end";
        out += "\n" + "";
        out += "\n" + "%% ================ End Circular Text Routine =================";

        return out;
    }

    /**
     * Calls PS routine to print circular text: outsidecircletext
     *
     * @param   textIn    	String to be printed
     * @param   pointSize   Pointsize the string was set to
     * @param   angle    	Angle the text is to be centered on
     * @param   radius    	Radius of the circle
     *
     * @return String EPS output
     */
    public static String drawOutsideCircularText(String textIn, double pointSize, double angle, double radius){
        return  "\n" + "(" +textIn+ ") "+ pointSize +" "+ angle +" " +radius+ " outsidecircletext";
    }

    /**
     * Calls PS routine to print circular text: Center Above
     *
     * @param   textIn    	String to be printed
     * @param   pointSize   Pointsize the string was set to
     * @param   angle    	Angle the text is to be centered on
     * @param   radius    	Radius of the circle
     *
     * @return String EPS output
     */
    public static String drawInsideCircularText(String textIn, double pointSize, double angle, double radius){
        // requires that setUpCircularText above is run first
        return "\n" + "(" +textIn+ ") "+ pointSize +" "+ angle +" " +radius+ " insidecircletext";
    }

    /**
     * Sets up definitions for symbols
     *
     * @return String EPS output
     */
    public static String setUpSymbols(){
        // call as "x y drawX"
        String out = "";
        out += "\n" + "/drawX";
        out += "\n" + "{gsave";
        out += "\n" + "newpath";
        out += "\n" + "moveto";
        out += "\n" + "-1.5 -1.5 rmoveto";
        out += "\n" + "3 3 rlineto";
        out += "\n" + "-3 0 rmoveto";
        out += "\n" + "3 -3 rlineto stroke";
        out += "\n" + "grestore}def";
        out += "\n";
        // call as "x y drawSmallX"
        out += "\n" + "/drawSmallX";
        out += "\n" + "{gsave";
        out += "\n" + "newpath";
        out += "\n" + "moveto";
        out += "\n" + "-1 -1 rmoveto";
        out += "\n" + "2 2 rlineto";
        out += "\n" + "-2 0 rmoveto";
        out += "\n" + "2 -2 rlineto stroke";
        out += "\n" + "grestore}def";
        out += "\n";
        // call as "x y cross"
        out += "\n" + "/cross";
        out += "\n" + "{gsave";
        out += "\n" + "newpath";
        out += "\n" + "moveto";
        out += "\n" + "-4 6 rlineto";
        out += "\n" + "8 0 rlineto";
        out += "\n" + "-4 -6 rlineto";
        out += "\n" + "6 4 rlineto";
        out += "\n" + "0 -8 rlineto";
        out += "\n" + "-6 4 rlineto";
        out += "\n" + "4 -6 rlineto";
        out += "\n" + "-8 0 rlineto";
        out += "\n" + "4 6 rlineto";
        out += "\n" + "-6 -4 rlineto";
        out += "\n" + "0 8 rlineto";
        out += "\n" + "+6 -4 rlineto fill";
        out += "\n" + "grestore}def";
        out += "\n";
        // call as "x y diamond"
        out += "\n" + "/diamond";
        out += "\n" + "{";
        out += "\n" + "/y exch def";
        out += "\n" + "/x exch def";
        out += "\n" + "gsave";
        out += "\n" + "newpath";
        out += "\n" + "x y moveto";
        out += "\n" + "0 6 rmoveto";
        out += "\n" + "6 -6 rlineto";
        out += "\n" + "-6 -6 rlineto";
        out += "\n" + "-6 6 rlineto";
        out += "\n" + "6 6 rlineto";
        out += "\n" + "-3 -3 rmoveto";
        out += "\n" + "6 -6 rlineto";
        out += "\n" + "0 6 rmoveto";
        out += "\n" + "-6 -6 rlineto stroke";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "newpath";
        out += "\n" + "1 setgray";
        out += "\n" + "x y 2 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "x y 2 0 360 arc stroke";
        out += "\n" + "grestore}def";

        return out;
    }

    /**
     * Draws the astrolabe throne
     *
     * since   0.1
     *
     */
    public static String buildMaterThrone(Astrolabe myAstrolabe){
        String out = "";
        Double innerRadius = (72 * myAstrolabe.getPlateDiameter()) / 2;
        Double limb = 72 * myAstrolabe.getLimbWidth();
        Double outerRadius = innerRadius + limb;

        out += "\n" + "%% ================ Draw Throne =================";
        out += "\n" + "0 " + (outerRadius + 28) +" 7 0 360 arc stroke";
        out += "\n" + "0 " + (outerRadius + 28) +" 38 0 180 arc stroke";
        out += "\n" + "-38 " + (outerRadius - 10) +" 38 90 270 arc stroke";
        out += "\n" + "38 " + (outerRadius - 10) +" 38 -90 90 arc stroke";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "0 " + (outerRadius + 25) +" moveto";
        out += "\n" + "0 " + (outerRadius + 31) +" lineto stroke";
        out += "\n" + "-3 " + (outerRadius + 28) +" moveto";
        out += "\n" + "3 " + (outerRadius + 28) +" lineto stroke";
        out += "\n" + "%% ================ Draw Throne =================";
        return out;
    }

    /**
     * Draws alternatee astrolabe throne
     *
     * since   2.0
     *
     */
    public static String buildMaterThrone2(Astrolabe myAstrolabe){
        String out = "";
        Double innerRadius = (72 * myAstrolabe.getPlateDiameter()) / 2;
        Double limb = 72 * myAstrolabe.getLimbWidth();
        Double outerRadius = innerRadius + limb;

        out += "\n" + "%% ================ Draw Throne =================";
        out += "\n" + "0 " + (outerRadius + 20) +" 7 0 360 arc stroke";
        out += "\n" + "-60 " + outerRadius +" moveto";
        out += "\n" + "-30 " + (outerRadius + 40) +" lineto";
        out += "\n" + "30 " + (outerRadius + 40) +" lineto";
        out += "\n" + "60 " + outerRadius +" lineto stroke";

        out += "\n" + ".1 setlinewidth";
        out += "\n" + "0 " + (outerRadius + 17) +" moveto";
        out += "\n" + "0 " + (outerRadius + 23) +" lineto stroke";
        out += "\n" + "-3 " + (outerRadius + 20) +" moveto";
        out += "\n" + "3 " + (outerRadius + 20) +" lineto stroke";
        out += "\n" + "%% ================ Draw Throne =================";
        return out;
    }

    /**
     * Draws an decorative octagonal frame around the limb
     *
     * Since 2.0
     *
     */
    public static String buildOctagon(Astrolabe myAstrolabe)
    {
        /**
         * The equation for the vertexes of an octagon is
         * (r*cos(a), r*sin(a)) where r is the radius of the
         * inscribed circle and a is the angle of the vertex
         *
         */
        String fileOut = "";
        Double radius = myAstrolabe.getMaterRadius() + 25;
        Double degreeInterval = 45.0;
        fileOut += "\n" + "%% ==================== Create Octagon Limb ====================";
        fileOut += "\n" + "1 setgray";
        fileOut += "\n" + -degreeInterval/2.0 + " rotate";
        fileOut += "\n" + "newpath";
        fileOut += "\n" + radius + " 0 moveto";
        for(int i = 1; i<= 8; i++){
            fileOut += "\n" + radius*Math.cos(Math.toRadians(degreeInterval * i)) + " " + radius*Math.sin(Math.toRadians(degreeInterval * i)) + " lineto";
        }
        fileOut += " fill";
        fileOut += "\n" + "0 setgray";
        fileOut += "\n" + "newpath";
        fileOut += "\n" + radius + " 0 moveto";

        for(int i = 1; i<= 8; i++){
            fileOut += "\n" + radius*Math.cos(Math.toRadians(degreeInterval * i)) + " " + radius*Math.sin(Math.toRadians(degreeInterval * i)) + " lineto";
        }
        fileOut += " stroke";

        radius = radius -3;

        fileOut += "\n" + "newpath";
        fileOut += "\n" + radius + " 0 moveto";

        for(int i = 1; i<= 8; i++){
            fileOut += "\n" + radius*Math.cos(Math.toRadians(degreeInterval * i)) + " " + radius*Math.sin(Math.toRadians(degreeInterval * i)) + " lineto";
        }
        fileOut += " stroke";
        fileOut += "\n" + degreeInterval/2.0 + " rotate";

        fileOut += "\n" + "%% ==================== End Create Octagon Limb ====================";
        fileOut += "\n" + "";

        return fileOut;
    }
}
