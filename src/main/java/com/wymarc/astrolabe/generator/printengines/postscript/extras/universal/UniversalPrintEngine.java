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
package com.wymarc.astrolabe.generator.printengines.postscript.extras.universal;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.math.InterSect;
import com.wymarc.astrolabe.math.ThreePointCenter;

import java.awt.geom.Point2D;

public class UniversalPrintEngine {

    private Astrolabe myAstrolabe = new Astrolabe();
    private boolean forCAD = false; // Each layer a separate color for using with CAD/cutting software

    /**
     * Draws the plate and its lines
     *
     * @return  returns the ps code for drawing the plate
     *
     */
    private String buildPlate(){
        String out = "";
        out += "\n" + "%% ==================== Build Plate ====================";
        out += "\n" + "newpath";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (myAstrolabe.getUniversalLimbRadius()) + " 0 360 arc stroke";
        out += "\n" + "";
        out += "\n" + "%% Find center of page and mark it";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "%% draw compass points";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "newpath";
        out += "\n" + -myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
        out += "\n" + myAstrolabe.getUniversalLimbRadius() + " 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -myAstrolabe.getUniversalLimbRadius() + " moveto";
        out += "\n" + "0 " + myAstrolabe.getUniversalLimbRadius() + " lineto stroke";
        out += "\n" + "";
        out += "\n" + "%% ==================== End Build Plate ====================";
        out += "\n" + "";

        return out;
    }

    private String buildSaphea(boolean isRete){
        String out = "";
        out += "\n" + "%% ==================== Build Saphea ====================";

        // Draw Polar arcs
        // Equation is:
        //  x = R * tan(a/2)
        // Where x is the intersection of the arc on the x axis, R is the radius of the plate and a is the longitude
        // Use the poles and x to find the center and radius
        Point2D.Double northPoint = new Point2D.Double(0,myAstrolabe.getUniversalLimbRadius());
        Point2D.Double southPoint = new Point2D.Double(0,-(myAstrolabe.getUniversalLimbRadius()));
        Point2D.Double equatorPoint = new Point2D.Double(0,0);
        ThreePointCenter MyCircle;
        for (int count = 1; count < 18; count++ ){
            equatorPoint.x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians((count*5)/2.0));
            MyCircle = new ThreePointCenter(northPoint, southPoint, equatorPoint );
            if (MyCircle.isCircle()){
                InterSect interSect1 = new InterSect(MyCircle.getCenter().x,  MyCircle.getCenter().y, MyCircle.getRadius(), 0, 0, myAstrolabe.getUniversalLimbRadius());
                double angle11 = interSect1.getAngle1();
                double angle12 = interSect1.getAngle2();
                InterSect interSect2 = new InterSect(-MyCircle.getCenter().x,  MyCircle.getCenter().y, MyCircle.getRadius(), 0, 0, myAstrolabe.getUniversalLimbRadius());
                double angle21 = interSect2.getAngle1();
                double angle22 = interSect2.getAngle2();

                if (!forCAD){
                    if ( count % 2 != 0 ) {
                        out += "\n" + ".5 setgray";
                    }
                }
                if (!isRete) {
                    out += "\n" + "newpath";
                    out += "\n" + MyCircle.getCenter().x + " " + MyCircle.getCenter().y + " " + MyCircle.getRadius() + " " + angle12 + " " + angle11 + " arc stroke";
                    out += "\n" + "newpath";
                    out += "\n" + (-MyCircle.getCenter().x) + " " + MyCircle.getCenter().y + " " + MyCircle.getRadius() + " " + angle21 + " " + angle22 + " arc stroke";
                }else {
                    out += "\n" + "newpath";
                    out += "\n" + MyCircle.getCenter().x + " " + MyCircle.getCenter().y + " " + MyCircle.getRadius() + " " + 0 + " " + angle11 + " arc stroke";
                    out += "\n" + "newpath";
                    out += "\n" + (-MyCircle.getCenter().x) + " " + MyCircle.getCenter().y + " " + MyCircle.getRadius() + " " + angle21 + " " + 180 + " arc stroke";
                }
                if (!forCAD){
                    if ( count % 2 != 0 ) {
                        out += "\n" + "0 setgray";
                    }
                }

            }
        }

        // Draw Parallels
        // Equation is:
        //  y = R/sin a
        //  r = R/tan a
        // Where y is the y-axis position of the center, r is the radius of the circle,
        // R is the radius of the plate and a is the latitude angle

        // First clear poles above 86 degrees for neatness
        double y = myAstrolabe.getUniversalLimbRadius()/Math.sin(Math.toRadians(86));
        double r = myAstrolabe.getUniversalLimbRadius()/Math.tan(Math.toRadians(86));
        out += "\n" + "1 setgray";
        out += "\n" + "0 " + y + " " + r + " 0 360 arc fill";
        if (!isRete) {
            out += "\n" + "0 -" + y + " " + r + " 0 360 arc fill";
        }
        out += "\n" + "0 setgray";


        for (int count = 1; count <= 44; count++){
            y = myAstrolabe.getUniversalLimbRadius()/Math.sin(Math.toRadians(count*2));
            r = myAstrolabe.getUniversalLimbRadius()/Math.tan(Math.toRadians(count*2));

            InterSect interSect1 = new InterSect(0, y, r, 0, 0, myAstrolabe.getUniversalLimbRadius());
            double angle11 = interSect1.getAngle1();
            double angle12 = interSect1.getAngle2();
            InterSect interSect2 = new InterSect(0, -y, r, 0, 0, myAstrolabe.getUniversalLimbRadius());
            double angle21 = interSect2.getAngle1();
            double angle22 = interSect2.getAngle2();

            out += "\n" + "newpath";

            if (forCAD){
                out += "\n" + "0 " + y + " " + r + " " + angle11 + " " + angle12 + " arc stroke";
                if (!isRete) {
                    out += "\n" + "0 -" + y + " " + r + " " + angle21 + " " + angle22 + " arc stroke";
                }
            }else{
                if (count % 5 == 0 ){
                    out += "\n" + "0 " + y + " " + r + " " + angle11 + " " + angle12 + " arc stroke";
                    if (!isRete) {
                        out += "\n" + "0 -" + y + " " + r + " " + angle21 + " " + angle22 + " arc stroke";
                    }
                }else{
                    out += "\n" + ".5 setgray";
                    out += "\n" + "0 " + y + " " + r + " " + angle11 + " " + angle12 + " arc stroke";
                    if (!isRete) {
                        out += "\n" + "0 -" + y + " " + r + " " + angle21 + " " + angle22 + " arc stroke";
                    }
                    out += "\n" + "0 setgray";
                }
            }
        }

        if (!isRete) {
            // Label hours = Note I'm kludging this, no need for mathematical exactness
            // First define the parallel that we will use for the arc of hours (30.5)
            y = myAstrolabe.getUniversalLimbRadius() / Math.sin(Math.toRadians(30.5));
            r = myAstrolabe.getUniversalLimbRadius() / Math.tan(Math.toRadians(30.5));
            double fontSize = 7.0;
            out += "\n" + "newpath";
            out += "\n" + "NormalFont7 setfont";
            // define angles kludge
            double[] angles = {3.7, 8.0, 12.4, 17.2, 23.0};
            double[] anglesLeft = {4.4, 8.7, 13.2, 18.2, 23.9};
            out += "\n" + "0 " + y + " translate";
            out += EPSToolKit.drawInsideCircularText("6", fontSize, 269.5, r);
            for (int i = 0; i < 5; i++) {
                out += EPSToolKit.drawInsideCircularText((i + 7) + "", fontSize, (270.0 + angles[i]), r);
            }
            for (int i = 0; i < 5; i++) {
                out += EPSToolKit.drawInsideCircularText((5 - i) + "", fontSize, (270.0 - anglesLeft[i]), r);
            }
            out += "\n" + "0 " + -y + " translate";

            out += "\n 180 rotate";
            out += "\n" + "newpath";
            out += "\n" + "NormalFont7 setfont";
            // define angles kludge
            out += "\n" + "0 " + y + " translate";
            out += EPSToolKit.drawInsideCircularText("6", fontSize, 270.5, r);
            for (int i = 0; i < 5; i++) {
                out += EPSToolKit.drawInsideCircularText((i + 7) + "", fontSize, (270.0 + angles[i]), r);
            }
            for (int i = 0; i < 5; i++) {
                out += EPSToolKit.drawInsideCircularText((5 - i) + "", fontSize, (270.0 - anglesLeft[i]), r);
            }
            out += "\n" + "0 " + -y + " translate";
            out += "\n -180 rotate";
        }

        // Draw tropics
        if (!isRete) {
            y = myAstrolabe.getUniversalLimbRadius()/Math.sin(Math.toRadians(23.44));
            r = myAstrolabe.getUniversalLimbRadius()/Math.tan(Math.toRadians(23.44));
            InterSect interSect1 = new InterSect(0, y, r, 0, 0, myAstrolabe.getUniversalLimbRadius());
            double angle11 = interSect1.getAngle1();
            double angle12 = interSect1.getAngle2();
            InterSect interSect2 = new InterSect(0, -y, r, 0, 0, myAstrolabe.getUniversalLimbRadius());
            double angle21 = interSect2.getAngle1();
            double angle22 = interSect2.getAngle2();

            out += "\n" + "newpath";
            out += "\n" + "[4 4] 0 setdash"; // set dashed line
            out += "\n" + "1 0 0 setrgbcolor";
            out += "\n" + "0 " + y + " " + r + " " + angle11 + " " + angle12 + " arc stroke";
            out += "\n" + "0 -" + y + " " + r + " " + angle21 + " " + angle22 + " arc stroke";
            out += "\n" + "0 setgray";
            out += "\n" + "[] 0 setdash"; // set solid line
        }

        // Draw Ecliptic
        // todo: Divide by degrees and calendar options
        if (!isRete) {
            out += "\n 23.44 rotate";
            out += "\n" + "1 0 0 setrgbcolor";
            //draw line
            out += "\n" + "newpath";
            out += "\n" + -myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
            out += "\n" + myAstrolabe.getUniversalLimbRadius() + " 0 lineto stroke";
            //draw ticks
            double x;
            for (int i = 1; i < 90; i++){
                x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians(i/2.0));
                if (i % 10 == 0){
                    out += "\n" + "newpath";
                    out += "\n" + x + " 5 moveto";
                    out += "\n" + x + " -5 lineto stroke";
                    out += "\n" + "newpath";
                    out += "\n" + -x + " 5 moveto";
                    out += "\n" + -x + " -5 lineto stroke";
                }else{
                    out += "\n" + "newpath";
                    out += "\n" + x + " 2 moveto";
                    out += "\n" + x + " -2 lineto stroke";
                    out += "\n" + "newpath";
                    out += "\n" + -x + " 2 moveto";
                    out += "\n" + -x + " -2 lineto stroke";
                }
            }

            //label signs
            out += "\n" + "NormalFont10 setfont";
            for (int i = 0; i < 3; i++){
                int[] loc = {15, 45, 75};
                x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians(loc[i]/2.0));
                out += "\n" + x + " 5" + " moveto";
                out += EPSToolKit.centerText(Astrolabe.ZODIAC[i]);
                out += "\n" + -x + " 5" + " moveto";
                out += EPSToolKit.centerText(Astrolabe.ZODIAC[11 - i]);
            }
            out += "\n 180 rotate"; // flip
            for (int i = 0; i < 3; i++){
                int[] loc = {15, 45, 75};
                x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians(loc[i]/2.0));
                out += "\n" + x + " 5" + " moveto";
                out += EPSToolKit.centerText(Astrolabe.ZODIAC[i + 6]);
                out += "\n" + -x + " 5" + " moveto";
                out += EPSToolKit.centerText(Astrolabe.ZODIAC[5 - i]);
            }

            out += "\n" + "0 setgray";
        }

        out += "\n" + "";
        out += "\n" + "%% ==================== End Build Saphea ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws the limb of the Astrolabe and labels it
     *
     * @return  returns the ps code for drawing the limb
     *
     */
    private String drawLimbScale(){
        int count;
        Double rotationIncrement;
        String out = "";

        // show the front degree scale
        rotationIncrement = 1.0;
        // create degree marks
        for (count = 1; count <= 360; count++){
            out += "\n" + myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
            out += "\n" + (myAstrolabe.getUniversalLimbRadius() + 5) + " 0 lineto stroke";
            out += "\n" + rotationIncrement + " rotate";
        }
        // create 10 degree marks
        for (count = 1; count <= 36; count++){
            out += "\n" + myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
            out += "\n" + (myAstrolabe.getUniversalLimbRadius() + 10) + " 0 lineto stroke";
            out += "\n" + 10*rotationIncrement + " rotate";
        }
        // create 5 degree marks
        out += "\n" + "5 rotate";
        for (count = 1; count <= 36; count++){
            out += "\n" + myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
            out += "\n" + (myAstrolabe.getUniversalLimbRadius() + 7) + " 0 lineto stroke";
            out += "\n" + 10*rotationIncrement + " rotate";
        }
        out += "\n" + "-5 rotate";
        return out;
    }

    /**
     * Labels the limb of the Astrolabe
     *
     */
    private String labelLimbScale(){
        String out = "";
        int count;

        // Label the degree scale
        Double labelRadius;
        Double fontSize;

        labelRadius =(myAstrolabe.getUniversalLimbRadius() + 10);
        fontSize = 8.0;
        out += "\n" + "NormalFont8 setfont";

        //Mark degrees
        for (int i = 0; i < 4; i++){
            for (count = 1; count <= 9; count++){
                out += EPSToolKit.drawOutsideCircularText((count*10)+"", fontSize, ((count*10)), labelRadius);
            }
            out += "\n" + "90 rotate";
        }
        return out;
    }


    /**
     * Draws the limb of the Astrolabe and labels it
     *
     * since   0.1
     *
     */
    private String buildMaterLimb(){
        String out = "";
        out += "\n" + "%% ==================== Create Mater Limb ====================";
        out += "\n" + "% Draw outer circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + myAstrolabe.getMaterRadius() + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (myAstrolabe.getUniversalLimbRadius()) + " 0 360 arc stroke";

        out += drawLimbScale();
        out += labelLimbScale();

        out += "\n" + "%% ==================== End Create Mater Limb ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws the front of an astrolabe using EPS
     *
     *@param myAstrolabeIn the astrolabe object
     *@return  returns the ps code for drawing the front of the astrolabe
     *
     */
    public String createRete(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Universal Astrolabe Rete");
        out += "\n" + "%% setup";

        if (myAstrolabe.getShowRegistrationMarks())
        {
            out += EPSToolKit.registrationMarks();
        }

        out += "\n" + "306 396 translate";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "";

        // Draw the Saphea
        out += "\n" + "gsave";
        out += buildSaphea(true);
        out += "\n" + "grestore";
        out += "\n" + "";

        // Draw edge and diameters
        out += buildPlate();

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }

    public String createRegula(Astrolabe myAstrolabeIn){
        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Universal Astrolabe Rule");
        out += "\n" + "%% setup";
        out += "\n" + "306 250 translate";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();

        // Draw the Regula
        out += "\n" + "gsave";
        double regulaLength = myAstrolabe.getUniversalLimbRadius();

        out += "\n" + "%% ==================== Create Regula ====================";
        out += "\n" + "";

        // draw rule
        out += "\n" + "newpath";
        out += "\n" + "0 -20 moveto";
        out += "\n" + -regulaLength + " -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + -regulaLength + "0 0 20 180 270 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + -(regulaLength + 20) + " 0 moveto";
        out += "\n" + (regulaLength + 20) + " 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + regulaLength + "0 0 20 270 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + regulaLength + " -20 moveto";
        out += "\n" + "0 -20 lineto stroke";

        out += "\n" + "";

        // draw rule pivot
        out += "\n" + "%% Mark pivot";
        out += "\n" + "0 0 15 0 180 arc stroke";
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 5 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";
        out += "\n" + "";

        //mark
        out += "\n" + "newpath";
        out += "\n" + -myAstrolabe.getUniversalLimbRadius() + " -4 moveto";
        out += "\n" + myAstrolabe.getUniversalLimbRadius() + " -4 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + -myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
        out += "\n" + -myAstrolabe.getUniversalLimbRadius() + " -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + myAstrolabe.getUniversalLimbRadius() + " 0 moveto";
        out += "\n" + myAstrolabe.getUniversalLimbRadius() + " -20 lineto stroke";
        //draw ticks
        double x;
        for (int i = 1; i < 90; i++){
            x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians(i/2.0));
            if (i % 10 == 0){
                out += "\n" + "newpath";
                out += "\n" + x + " 0 moveto";
                out += "\n" + x + " -20 lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + -x + " 0 moveto";
                out += "\n" + -x + " -20 lineto stroke";
            }else if (i % 5 == 0){
                out += "\n" + "newpath";
                out += "\n" + x + " 0 moveto";
                out += "\n" + x + " -8 lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + -x + " 0 moveto";
                out += "\n" + -x + " -8 lineto stroke";
            }else{
                out += "\n" + "newpath";
                out += "\n" + x + " 0 moveto";
                out += "\n" + x + " -4 lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + -x + " 0 moveto";
                out += "\n" + -x + " -4 lineto stroke";
            }
        }

        //label ticks
        for (int i = 1; i <= 9; i++){
            x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians((i * 10)/2.0));
            out += "\n" + "NormalFont7 setfont";
            out += "\n" + (x - 1) + " -10" + " moveto";
            out += "\n" + EPSToolKit.rightJustifyText((i * 10) + "");
            if (i < 9){// We will do 270 later
                out += "\n" + -(x + 1) + " -10" + " moveto";
                out += "\n" + EPSToolKit.rightJustifyText((360 - (i * 10)) + "");
            }
        }
        for (int i = 0; i <= 9; i++){
            x = myAstrolabe.getUniversalLimbRadius()*Math.tan(Math.toRadians((i * 10)/2.0));
            out += "\n" + "NormalFont7 setfont";
            if (i < 9){// don't need 90
                out += "\n" + x + " -18" + " moveto";
                out += "\n" + "( " + (180 - (i * 10)) + ") show";
            }
            if (i > 0){// We only need one 0
                out += "\n" + -x + " -18" + " moveto";
                out += "\n" + "( " + (180 + (i * 10)) + ") show";
            }
        }

        // Draw the Brachiolus
        double sectionLength = myAstrolabe.getUniversalLimbRadius()/2;
        out += "\n" + "0 150 translate";

        //right pivot
        out += "\n" + "newpath";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";

        //left pivot
        out += "\n" + -sectionLength + " 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + -(sectionLength + 5) + " 0 moveto";
        out += "\n" + -(sectionLength - 5) + " 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + -sectionLength + " 5 moveto";
        out += "\n" + -sectionLength + " -5 lineto stroke";

        //outline
        out += "\n" + "0 0 15 270 90 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 15 moveto";
        out += "\n" + "-5 15 lineto";
        out += "\n" + "-25 5 lineto";
        double bar = sectionLength-50;
        out += "\n" + -(bar +25) + " 5 lineto";
        out += "\n" + -(bar +45) + " 15 lineto";
        out += "\n" + -(bar +50) + " 15 lineto stroke";
        out += "\n" + -sectionLength + " 0 15 90 270 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + -(bar +50) + " -15 moveto";
        out += "\n" + -(bar +45) + " -15 lineto";
        out += "\n" + -(bar +25) + " -5 lineto";
        out += "\n" + "-25 -5 lineto";
        out += "\n" + "-5 -15 lineto";
        out += "\n" + "0 -15 lineto stroke";
        out += "\n" + "";

        //second section
        out += "\n" + "0 75 translate";

        //pivot
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";

        //outline
        out += "\n" + "0 0 15 90 270 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 15 moveto";
        out += "\n" + "5 15 lineto";
        out += "\n" + "20 5 lineto";
        out += "\n" + (sectionLength - 17) + " 8 lineto";
        out += "\n" + (sectionLength - 10) + " 3 lineto";
        out += "\n" + sectionLength + " 0 lineto";
        out += "\n" + (sectionLength - 10) + " -3 lineto";
        out += "\n" + (sectionLength - 17) + " -8 lineto";
        out += "\n" + "20 -5 lineto";
        out += "\n" + "5 -15 lineto";
        out += "\n" + "0 -15 lineto stroke";

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }

    /**
     * Draws the front of an astrolabe using EPS
     *
     *@param myAstrolabeIn the astrolabe object
     *@return  returns the ps code for drawing the front of the astrolabe
     *
     */
    public String createPlate(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Universal Astrolabe");
        out += "\n" + "%% setup";

        if (myAstrolabe.getShowRegistrationMarks())
        {
            out += EPSToolKit.registrationMarks();
        }

        out += "\n" + "306 396 translate";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        if (myAstrolabe.getShowThrone()){
            out += "\n" + "gsave";
            if (myAstrolabe.getShapeOption() == 1){
                out += EPSToolKit.buildMaterThrone2(myAstrolabe);
            }else{
                out += EPSToolKit.buildMaterThrone(myAstrolabe);
                out += "\n" + "1 setgray";
                out += "\n" + "0 0 " + (myAstrolabe.getMaterRadius()) + " 0 360 arc fill";
            }
            out += "\n" + "grestore";
            out += "\n" + "";
        }

        if (myAstrolabe.getShapeOption() == 1){
            out += "\n" + "gsave";
            out += EPSToolKit.buildOctagon(myAstrolabe);
            out += "\n" + "grestore";
        }

        // Draw the Saphea
        out += "\n" + "gsave";
        out += buildSaphea(false);
        out += "\n" + "grestore";
        out += "\n" + "";

        // Build the limb
        out += "\n" + "gsave";
        out += buildMaterLimb();
        out += "\n" + "grestore";
        out += "\n" + "";

        // Build the plate
        out += "\n" + "gsave";
        out += buildPlate();
        out += "\n" + "grestore";
        out += "\n" + "";

        // mark pivot point
        out += "\n" + "%% Mark pivot";
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 5 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";
        out += "\n" + "";

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }
}
