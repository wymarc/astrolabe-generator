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
package com.wymarc.astrolabe.generator.printengines.postscript;

import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.ZodiacSigns;
import com.wymarc.astrolabe.math.AstroMath;
import com.wymarc.astrolabe.math.InterSect;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BackPrintEngine {

    private Astrolabe myAstrolabe = new Astrolabe();
    private boolean isLaser = true;

    /**
     * Builds the default offset calendar ring
     *
     * @return  returns the ps code for drawing the Calendar ring
     *
     */
    private String buildCalendarRing(){

        double calendarRadius = myAstrolabe.getMaterRadius() - 32; //astrolabe radius - width of zodiac ring
        int count;
        int count2; // counters
        double lineOfApsides; //angle of line of apsides
        double eccentricityOfOrbit; //orbital eccentricity for this date
        double t; //Time in Julian centuries from J2000.0
        double delta; //offset of calendar ring

        int totalDays; //counter
        String out = "";
        out += "\n" + "%% ================ Draw Offset Calendar Ring =================";
        t = AstroMath.getT();
        lineOfApsides = AstroMath.angleOfLineOfApsides(t);
        out += "\n" + -lineOfApsides + " rotate";// rotate to line up line of apsides with 0 degrees
        out += "\n" + "%% draw Line of Apsides";
        //out += "\n" + ".4 setlinewidth";
        out += "\n" + "newpath";
        out += "\n" + -(myAstrolabe.getMaterRadius() - 30) + " 0 moveto";
        out += "\n" + (myAstrolabe.getMaterRadius() - 30) + " 0 lineto";
        out += "\n" + "stroke";
        //out += "\n" + ".1 setlinewidth";        

        //step 2 find the offset and set the origin to it
        eccentricityOfOrbit = 0.01670862 - (0.000042037*t) - (0.0000001236*(t*t)) + (0.00000000004*(t*t*t));
        delta = 2*eccentricityOfOrbit*calendarRadius;
        calendarRadius = calendarRadius - delta; // fit ring iside zodiac ring
        out += "\n" + -delta + " 0 translate"; // center on calendar center

        //step 3 draw the ring outlines
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc fill"; //use fill to remove hidden parts of line of apsides
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 5) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 10) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 20) + " 0 360 arc stroke";

        //step 4 compute rotation to start with
        double MeanAnomaly = AstroMath.manom(t);// compute Mean Anomaly
        //double MeanAnomaly = AstroMath.normal(((-4.8e-07 * t - .0001559) * t + 35999.0503) * t + 357.5291);    // compute Mean Anomaly
        double calRotation = MeanAnomaly + (myAstrolabe.getLocation().getLongitude()/365.0); //todo test for 0 long
        out += "\n" + calRotation + " rotate"; // line up calendar to proper orientation

        //step 5 draw calendar markings and label
        double increment = 360.0/365.0; // 360/365 number of degrees for each day tick
        for (count = 1; count <= 365; count++){// mark days
            out += "\n" + (calendarRadius - 5) + " 0 moveto";
            out += "\n" + calendarRadius + " 0 lineto stroke";
            out += "\n" + increment + " rotate";
        }
        for (count = 0; count <= 11; count++){// mark months
            out += "\n" + (calendarRadius - 20) + " 0 moveto";
            out += "\n" + calendarRadius + " 0 lineto stroke";
            out += "\n" + (increment * Astrolabe.MONTHSDAYS[count]) + " rotate";
        }
        for (count = 0; count <= 11; count++){// mark fifth and tenth days
            for (count2 = 0; count2 < Astrolabe.MONTHSDAYS[count]; count2++){
                if((count2 == 10)||(count2 == 20)||(count2 == 30)){
                    out += "\n" + (calendarRadius - 10) + " 0 moveto";
                    out += "\n" + calendarRadius + " 0 lineto stroke";
                }
                if((count2 == 5)||(count2 == 15)||(count2 == 25)){
                    out += "\n" + (calendarRadius - 8) + " 0 moveto";
                    out += "\n" + calendarRadius + " 0 lineto stroke";
                }
                out += "\n" + increment + " rotate";
            }
        }
        out += "\n" + "NormalFont10 setfont"; // label months
        totalDays = 0;

        for (count = 0; count <= 11; count++){
            out += EPSToolKit.drawOutsideCircularText(Astrolabe.MONTHS[count], 10,
                    (((totalDays + (Astrolabe.MONTHSDAYS[count] / 2)) * increment)), (calendarRadius - 18));
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "NormalFont5 setfont";  // label tens of days
        totalDays = 0;
        for (count = 0; count <= 11; count++){
            for (count2 = 0; count2 < Astrolabe.MONTHSDAYS[count]; count2++){
                if((count2 == 10)||(count2 == 20)||(count2 == 30)){
                    out += EPSToolKit.drawOutsideCircularText("" + count2, 5,
                            ((totalDays * increment) + (count2 * increment) - 1), (calendarRadius - 9));
                }
            }
            totalDays += Astrolabe.MONTHSDAYS[count];
        }

        //step 5 rotate back and re-center
        //out += "\n" + -calRotation + " rotate";
        out += "\n" + delta + " 0 translate";
        out += "\n" + lineOfApsides + " rotate";
        out += "\n" + "%% ================ End Draw Offset Calendar Ring =================";

        return out;
    }

    /**
     * Builds the concentric calendar ring
     *
     * @return  returns the ps code for drawing the Calendar ring
     *
     */
    private String buildConcentricCalendarRing(){
        double calendarRadius = myAstrolabe.getMaterRadius() - 37; //astrolabe radius - width of zodiac ring
        int count;
        int count2; // counters
        double lineOfApsides; //angle of line of apsides
        double t; //Time in Julian centuries from J2000.0

        String out = "";
        out += "\n" + "%% ================ Draw Concentric Calendar Ring =================";
        t = AstroMath.getT();
        lineOfApsides = AstroMath.angleOfLineOfApsides(t);
        out += "\n" + -lineOfApsides + " rotate";// rotate to line up line of apsides with 0 degrees
        out += "\n" + "%% draw Line of Apsides";
        out += "\n" + "newpath";
        out += "\n" + -(myAstrolabe.getMaterRadius() - 30) + " 0 moveto";
        out += "\n" + (myAstrolabe.getMaterRadius() - 30) + " 0 lineto";
        out += "\n" + "stroke";
        out += "\n" + lineOfApsides + " rotate";// rotate back

        // draw the ring outlines
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc fill"; //use fill to remove hidden parts of line of apsides
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 5) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 10) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 20) + " 0 360 arc stroke";

        double jDay = 1.0/36525.0; // change in T per day

        // draw calendar markings and label
        for (count = 0; count < 365; count++){// mark days
            double rotation = AstroMath.geolong(t + (count * jDay));
            out += "\n" + rotation + " rotate";
            out += "\n" + (calendarRadius - 5) + " 0 moveto";
            out += "\n" + calendarRadius + " 0 lineto stroke";
            out += "\n" + -rotation + " rotate";
        }
        int totalDays = 0;
        for (count = 0; count <= 11; count++){// mark months
            double rotation = AstroMath.geolong(t + (totalDays * jDay));
            out += "\n" + rotation + " rotate";
            out += "\n" + (calendarRadius - 20) + " 0 moveto";
            out += "\n" + calendarRadius + " 0 lineto stroke";
            out += "\n" + -rotation + " rotate";
            totalDays = totalDays + Astrolabe.MONTHSDAYS[count];
        }

        totalDays = 0;
        for (count = 0; count <= 11; count++){// mark fifth and tenth days
            for (count2 = 0; count2 < Astrolabe.MONTHSDAYS[count]; count2++){
                double rotation = AstroMath.geolong(t + ((totalDays + count2) * jDay));
                out += "\n" + rotation + " rotate";
                if((count2 == 10)||(count2 == 20)||(count2 == 30)){
                    out += "\n" + (calendarRadius - 10) + " 0 moveto";
                    out += "\n" + calendarRadius + " 0 lineto stroke";
                }
                if((count2 == 5)||(count2 == 15)||(count2 == 25)){
                    out += "\n" + (calendarRadius - 8) + " 0 moveto";
                    out += "\n" + calendarRadius + " 0 lineto stroke";
                }
                out += "\n" + -rotation + " rotate";
            }
            totalDays = totalDays + Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "NormalFont10 setfont"; // label months
        totalDays = 0;

        for (count = 0; count <= 11; count++){
            double rotation = AstroMath.geolong(t + ((totalDays + (Astrolabe.MONTHSDAYS[count] / 2)) * jDay));
            out += EPSToolKit.drawOutsideCircularText(Astrolabe.MONTHS[count], 10,
                    rotation, (calendarRadius - 18));
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "NormalFont5 setfont";  // label tens of days
        totalDays = 0;
        for (count = 0; count <= 11; count++){
            for (count2 = 0; count2 < Astrolabe.MONTHSDAYS[count]; count2++){
                double rotation = AstroMath.geolong(t + (totalDays + count2) * jDay);
                if((count2 == 10)||(count2 == 20)||(count2 == 30)){
                    out += EPSToolKit.drawOutsideCircularText("" + count2, 5,
                            rotation, (calendarRadius - 9));
                }
            }
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "%% ================ End Draw Concentric Calendar Ring =================";

        return out;
    }

    /**
     * computes and draws the Unequal Hours scales
     *
     * @return  returns the ps code for drawing the The Unequal Hours
     *
     */
    private String buildUnequalHoursBack(){
        //compute size of arc that contains the scale and draw it
        // note eventually this will be done by looking at what rings are drawn and figuring
        // the remaining radius
        double unequalRadius = myAstrolabe.getMaterRadius() - 67;
        int i;
        double Ri;
        InterSect myInterSect;
        String out = "";

        out += "\n" + "%% ================ Draw Unequal Hours =================";

        if ((myAstrolabe.getTopLeft() == 1 || myAstrolabe.getTopLeft() == 3) && myAstrolabe.getTopRight() == 1){
            //both first and second quadrants unequal hours
            out += "\n" + "newpath";
            out += "\n" + -unequalRadius + " 0 moveto";
            out += "\n" + unequalRadius + " 0 lineto";
            out += "\n" + "0 0 " + unequalRadius + " 0 180 arc stroke";
        } else if (myAstrolabe.getTopLeft() == 1 || myAstrolabe.getTopLeft() == 3){
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + "0 " + unequalRadius + " lineto";
            out += "\n" + "0 0 " + unequalRadius + " 90 180 arc";
            out += "\n" + "0 0 lineto stroke";

        }else if (myAstrolabe.getTopRight() == 1){
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + unequalRadius + " 0 lineto";
            out += "\n" + "0 0 " + unequalRadius + " 0 90 arc";
            out += "\n" + "0 0 lineto stroke";
        }

        if (myAstrolabe.getTopLeft() == 1 || myAstrolabe.getTopLeft() == 3){
            // draw unequal hour lines
            for (i = 1; i <= 6; i++){
                Ri = (unequalRadius/(2*(Math.sin(Math.toRadians(15*i)))));
                myInterSect = new InterSect(0, Ri, Ri, 0, 0, unequalRadius);
                if ((i==3)||(i==4)||(i==5)){
                    out += "\n" + "0 " + Ri + " " + Ri + " " + myInterSect.getAngle2() + " 270 arc stroke";
                }else{
                    out += "\n" + "0 " + Ri + " " + Ri + " " + myInterSect.getAngle1() + " 270 arc stroke";
                }
            }
        }

        if (myAstrolabe.getTopRight() == 1){
            // draw unequal hour lines
            for (i = 1; i <= 6; i++){
                Ri = (unequalRadius/(2*(Math.sin(Math.toRadians(15*i)))));
                myInterSect = new InterSect(0, Ri, Ri, 0, 0, unequalRadius);
                if ((i==3)||(i==4)||(i==5)){
                    out += "\n" + "0 " + Ri + " " + Ri +" 270 " + myInterSect.getAngle1() + " arc stroke";
                }else{
                    out += "\n" + "0 " + Ri + " " + Ri +" 270 " + myInterSect.getAngle2() + " arc stroke";
                }
            }
        }

        if ((myAstrolabe.getTopLeft() == 1 || myAstrolabe.getTopLeft() == 3) && myAstrolabe.getTopRight() == 1){
            //both first and second quadrants unequal hours
            out += "\n" + "NormalFont5 setfont";
            for (i = 1; i <= 11; i++){
                out += EPSToolKit.drawOutsideCircularText(Integer.toString(i), 5, (180 - (i*15)), unequalRadius +2);
            }
        }else if(myAstrolabe.getTopLeft() == 1 || myAstrolabe.getTopLeft() == 3){
            out += "\n" + "NormalFont5 setfont";
            for (i = 1; i <= 6; i++){
                out += EPSToolKit.drawOutsideCircularText(Integer.toString(7-i), 5, (90+((i-1)*15)), unequalRadius +2);
            }
        }else if(myAstrolabe.getTopRight() == 1){
            out += "\n" + "NormalFont5 setfont";
            for (i = 1; i <= 6; i++)
            {
                out += EPSToolKit.drawOutsideCircularText(Integer.toString(i), 5, ((i*15)), unequalRadius +2);
            }
        }

        out += "\n" + "%% ================ End Unequal Hours =================";

        return out;
    }

    /**
     * computes and draws the cotangent scale
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the Cotangent Scale
     *
     * param   out the Print String
     *
     */
    private String buildCotangentScale(Astrolabe myAstrolabe){
        // compute the radius of the scale. Should intersect corners of shadow squares, if any
        double cotangentRadius = myAstrolabe.getMaterRadius() - 80;
        String out = "";

        // rotate to place center of scale at 0 degrees and mark
        out += "\n" + "-90 rotate";
        out += "\n" + "newpath";
        out += "\n" + cotangentRadius + " 0 moveto";
        out += "\n" + (cotangentRadius + 10) + " 0 lineto stroke";

        double angle;
        int i;

        for(i=1; i<=21;i++){
            angle = Math.toDegrees(Math.atan((double)i/7));
            out += "\n" + -angle + " rotate";
            out += "\n" + "newpath";
            out += "\n" + cotangentRadius + " 0 moveto";
            out += "\n" + (cotangentRadius + 7) + " 0 lineto stroke";
            //out += "\n" + "0 0 moveto";
            //out += "\n" + "NormalFont5 setfont";
            //out = drawInsideCircularText(out, i.toString(), 5, 0, (cotangentRadius + 10));
            if(i <= 12 || i == 14 || i == 16 || i == 18 || i == 21){
                out += "\n" + "NormalFont5 setfont";
                out += EPSToolKit.drawInsideCircularText(Integer.toString(i), 5, 0, (cotangentRadius + 10));
            }
            out += "\n" + angle + " rotate";
        }

        for(i=1; i<=32;i++){
            angle = Math.toDegrees(Math.atan((double)i/12));
            out += "\n" + angle + " rotate";
            out += "\n" + "newpath";
            out += "\n" + cotangentRadius + " 0 moveto";
            out += "\n" + (cotangentRadius + 7) + " 0 lineto stroke";
            if(i <= 10 || i == 12 || i == 14 || i == 16 || i == 18 || i == 20 || i == 24 || i == 28 || i == 32){
                out += "\n" + "NormalFont5 setfont";
                out += EPSToolKit.drawInsideCircularText(Integer.toString(i), 5, 0, (cotangentRadius + 10));
            }
            out += "\n" + -angle + " rotate";
        }
        //rotate back
        out += "\n" + "+90 rotate";

        // draw scale rings
        double startAngle = 270 - Math.toDegrees(Math.atan((double)21/7));
        double endAngle = 270 + Math.toDegrees(Math.atan((double)32/12));
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + cotangentRadius + " " + startAngle + " " + endAngle + " arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + (cotangentRadius + 5) + " " + startAngle + " " + endAngle + " arc stroke";

        return out;
    }

    /**
     * computes and draws the shadow squares
     *
     * @return returns the ps code for drawing the Shadow Squares
     */
    private String drawShadowSquare() {
        //compute size of box
        // note eventually this will be done by looking at what rings are drawn and figuring
        // the remaining radius
        double shadowRadius = myAstrolabe.getMaterRadius() - 67;
        if(myAstrolabe.getShowCotangentScale()){
            //if we want to show the cotangent scale make room
            shadowRadius = shadowRadius - 13;
        }
        double shadowSide = Math.sqrt((shadowRadius*shadowRadius)/2.0); //from pythagoras
        double div = 12.0;
        StringBuilder out = new StringBuilder();

        if (isLaser){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        if((myAstrolabe.getBottomRight() == 1)||(myAstrolabe.getBottomRight() == 2)||(myAstrolabe.getBottomRight() == 3)) {
            out.append("\n").append("% Shadow square")
                    .append("\n").append("% =============== Create Shadow Square =================")
                    .append("\n").append("newpath")
                    .append("\n").append(shadowSide).append(" 0 moveto")
                    .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto")
                    .append("\n").append("0 ").append(-shadowSide).append(" lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append(shadowSide - 10).append(" 0 moveto")
                    .append("\n").append(shadowSide - 10).append(" ").append(-(shadowSide - 10)).append(" lineto")
                    .append("\n").append("0 ").append(-(shadowSide - 10)).append(" lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append(shadowSide - 24).append(" 0 moveto")
                    .append("\n").append(shadowSide - 24).append(" ").append(-(shadowSide - 24)).append(" lineto")
                    .append("\n").append("0 ").append(-(shadowSide - 24)).append(" lineto stroke");

            for (int count = 1; count < div; count++) {// print division lines
                // determine angle
                double angle = (45.0 / 12.0) * count;
                // determine long and short mark intersections with sides
                double outside = Math.tan(Math.toRadians(angle)) * shadowSide;
                double middle = Math.tan(Math.toRadians(angle)) * (shadowSide - 10);
                double inside = Math.tan(Math.toRadians(angle)) * (shadowSide - 24);

                if (count == 4 || count == 8) {
                    out.append("\n").append("newpath")
                            .append("\n").append(shadowSide - 24).append(" ").append(-inside).append(" moveto")
                            .append("\n").append(shadowSide).append(" ").append(-outside).append(" lineto stroke")
                            .append("\n").append(inside).append(" ").append(-(shadowSide - 24)).append(" moveto")
                            .append("\n").append(outside).append(" ").append(-shadowSide).append(" lineto stroke");
                } else {
                    out.append("\n").append("newpath")
                            .append("\n").append(shadowSide - 10).append(" ").append(-middle).append(" moveto")
                            .append("\n").append(shadowSide).append(" ").append(-outside).append(" lineto stroke")
                            .append("\n").append(middle).append(" ").append(-(shadowSide - 10)).append(" moveto")
                            .append("\n").append(outside).append(" ").append(-shadowSide).append(" lineto stroke");
                }
            }

            // Draw 45 line
            out.append("\n").append("newpath")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto stroke");

            if (isLaser) {
                out.append("\n").append("0 setgray");
            }

            // label
            //determine locations of numbers on middle line
            double four = Math.tan(Math.toRadians(7.5)) * (shadowSide - 10);
            double eight = Math.tan(Math.toRadians(22.5)) * (shadowSide - 10);
            double twelve = Math.tan(Math.toRadians(37.5)) * (shadowSide - 10);

            //Mark degrees
            if (isLaser) {
                out.append("\n").append("ArialFont10 setfont");
            } else {
                out.append("\n").append("NormalFont10 setfont");
            }

            out.append("\n").append("newpath")
                    //.append("\n").append("-10 0 moveto")
                    .append("\n").append(four).append(" ").append(-(shadowSide - 13)).append(" moveto")
                    .append("\n").append(EPSToolKit.centerText("4"))
                    .append("\n").append(eight).append(" ").append(-(shadowSide - 13)).append(" moveto")
                    .append("\n").append(EPSToolKit.centerText("8"))
                    .append("\n").append(twelve).append(" ").append(-(shadowSide - 13)).append(" moveto")
                    .append("\n").append(EPSToolKit.centerText("12"));
            out.append("\n").append("90 rotate");
            out.append("\n").append("newpath")
                    //.append("\n").append("-10 0 moveto")
                    .append("\n").append(-four).append(" ").append(-(shadowSide - 13)).append(" moveto")
                    .append("\n").append(EPSToolKit.centerText("4"))
                    .append("\n").append(-eight).append(" ").append(-(shadowSide - 13)).append(" moveto")
                    .append("\n").append(EPSToolKit.centerText("8"))
                    .append("\n").append(-twelve).append(" ").append(-(shadowSide - 13)).append(" moveto")
                    .append("\n").append(EPSToolKit.centerText("12"));
            out.append("\n").append("-90 rotate");

            out.append("\n").append("% =============== End Shadow Square =================");
        }
        if(myAstrolabe.getBottomLeft() == 4) {
            //Draw bottomleft horz shadow scale
            String out2 = "";
            out2 += "\n" + "% =============== Create Left Horz Shadow scale =================";
            // draw box
            out2 += "\n" + "0 0 1 setrgbcolor";
            out2 += "\n" + "newpath";
            out2 += "\n" + "0 0 moveto";
            out2 += "\n" + "-182 0 lineto";
            out2 += "\n" + "-182 -28 lineto";
            out2 += "\n" + "0 -28 lineto";
            out2 += "\n" + "0 0 lineto stroke";
            out2 += "\n" + "newpath";
            out2 += "\n" + "0 -28 moveto";
            out2 += "\n" + "0 " + (-shadowSide) + " lineto stroke";


            out2 += "\n" + "newpath";
            out2 += "\n" + "0 -23 moveto";
            out2 += "\n" + "-182 -23 lineto stroke";

            for (int count = 0; count < 7; count++) {
                out2 += "\n" + "newpath";
                out2 += "\n" + (-count * 28) + " 0 moveto";
                out2 += "\n" + (-count * 28) + " -28 lineto stroke";
            }

            for (int count = 0; count < 26; count++) {
                out2 += "\n" + "newpath";
                out2 += "\n" + (-count * 7) + " -23 moveto";
                out2 += "\n" + (-count * 7) + " -28 lineto stroke";
            }

            out2 += "\n" + "0 setgray";
            for (int count = 1; count < 7; count++) {
                // label
                out2 += "\n" + (-((count * 28) + 4)) + " -19 moveto";
                out2 += "\n" + "NormalFont10 setfont";
                out2 += EPSToolKit.centerText(Integer.toString(count));
            }

            out2 += "\n" + "% =============== End Left Horz Shadow scale =================";
            out.append(out2);
        }

        return out.toString();
    }

    /**
     * computes and draws the shadow squares
     *
     * @return  returns the ps code for drawing the Shadow Squares
     */
    private String buildShadowSquare(){
        //compute size of box
        // note eventually this will be done by looking at what rings are drawn and figuring
        // the remaining radius
        double shadowRadius = myAstrolabe.getMaterRadius() - 67;
        if(myAstrolabe.getShowCotangentScale()){
            //if we want to show the cotangent scale make room
            shadowRadius = shadowRadius - 13;
        }
        double shadowSide = Math.sqrt((shadowRadius*shadowRadius)/2.0); //from pythagoras
        int count;
        double div = 0.0;
        String out = "";

        if((myAstrolabe.getBottomRight() == 1)||(myAstrolabe.getBottomRight() == 2)||(myAstrolabe.getBottomRight() == 3)){
            //Draw bottom right	
            out += "\n" + "% =============== Create Right Shadow Square =================";
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + shadowSide + " 0 lineto";
            out += "\n" + shadowSide + " "+ (-shadowSide) + " lineto";
            out += "\n" + "0 "+ (-shadowSide) + " lineto";
            out += "\n" + "0 0 lineto stroke";
            out += "\n" + "0 0 moveto";

            //How many divisons? 7 10 12
            if(myAstrolabe.getBottomRight() == 1){
                div = 7.0;
            }else if(myAstrolabe.getBottomRight() == 2){
                div = 10.0;
            }else if(myAstrolabe.getBottomRight() == 3){
                div = 12.0;
            }

            for (count = 1; count < div; count++){
                // print division lines
                out += "\n" + "0 0 moveto";
                out += "\n" + shadowSide + " "+ (-((shadowSide/div)*count)) + " lineto stroke";
                out += "\n" + "0 0 moveto";
                out += "\n" + ((shadowSide/div)*count) + " "+ (-shadowSide) + " lineto stroke";
            }

            out += "\n" + "newpath";
            out += "\n" + "1 setgray";
            out += "\n" + "0 0 moveto";
            out += "\n" + (shadowSide-6) + " 0 lineto";
            out += "\n" + (shadowSide-6) + " "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 0 lineto fill";
            out += "\n" + "newpath";
            out += "\n" + "0 setgray";
            out += "\n" + "0 0 moveto";
            out += "\n" + (shadowSide-6) + " 0 lineto";
            out += "\n" + (shadowSide-6) + " "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 0 lineto stroke";

            if(div == 10 || div == 12){
                // if side is divided into ten or 12 sections
                //draw 1/2 way mark
                out += "\n" + "0 0 moveto";
                out += "\n" + shadowSide + " "+ (-shadowSide/2.0) + " lineto stroke";
                out += "\n" + "0 0 moveto";
                out += "\n" + shadowSide/2.0 + " "+ (-shadowSide) + " lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + "1 setgray";
                out += "\n" + "0 0 moveto";
                out += "\n" + (shadowSide-12) + " 0 lineto";
                out += "\n" + (shadowSide-12) + " "+ (-(shadowSide-12)) + " lineto";
                out += "\n" + "0 "+ (-(shadowSide-12)) + " lineto";
                out += "\n" + "0 0 lineto fill";
                out += "\n" + "0 setgray";
                out += "\n" + ((shadowSide/2.0)-8) + " "+ (-(shadowSide-7)) + " moveto";
                out += "\n" + "NormalFont5 setfont";
                if(div == 10){
                    // mark 5 line
                    out += "\n" + "(5) show";
                    out += "\n" + ((shadowSide-10)) + " "+ (-(shadowSide/2.0)) + " moveto";
                    out += "\n" + "(5) show";
                }if(div == 12){
                    // mark 6 line
                    out += "\n" + "(6) show";
                    out += "\n" + ((shadowSide-10)) + " "+ (-(shadowSide/2.0)) + " moveto";
                    out += "\n" + "(6) show";
                }
            }
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + (shadowSide-12) + " 0 lineto";
            out += "\n" + (shadowSide-12) + " "+ (-(shadowSide-12)) + " lineto";
            out += "\n" + "0 "+ (-(shadowSide-12)) + " lineto";
            out += "\n" + "0 0 lineto stroke";
            out += "\n" + "0 0 moveto";
            // mark 45 line
            out += "\n" + shadowSide + " "+ (-shadowSide) + " lineto stroke"; // 45 line                   
            out += "\n" + (shadowSide-16) + " "+ (-(shadowSide-7)) + " moveto";
            out += "\n" + "NormalFont5 setfont";
            out += "\n" + "("+ Math.round(div) +") show"; // round to get rid of decimal

            // Label
            out += "\n" + shadowSide/2.0 + " "+ (-(shadowSide-15)) + " moveto";
            out += "\n" + "NormalFont8 setfont";
            out += EPSToolKit.centerText("Umbra Recta");

            out += "\n" + 90 + " rotate";
            out += "\n" + -(shadowSide/2.0) + " "+ (-(shadowSide-15)) + " moveto";
            out += "\n" + "NormalFont8 setfont";
            out += EPSToolKit.centerText("Umbra Versa");
            out += "\n" + -90 + " rotate";
            out += "\n" + "% =============== End Right Shadow Square =================";
        }

        if((myAstrolabe.getBottomLeft() == 1)||(myAstrolabe.getBottomLeft() == 2)||(myAstrolabe.getBottomLeft() == 3)){
            //Draw bottomleft
            out += "\n" + "% =============== Create Left Shadow Square =================";
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + (-shadowSide) + " 0 lineto";
            out += "\n" + (-shadowSide) + " "+ (-shadowSide) + " lineto";
            out += "\n" + "0 "+ (-shadowSide) + " lineto";
            out += "\n" + "0 0 lineto stroke";

            out += "\n" + "0 0 moveto";

            //How many divisons? 7 10 12
            if(myAstrolabe.getBottomLeft() == 1){
                div = 7.0;
            }else if(myAstrolabe.getBottomLeft() == 2){
                div = 10.0;
            }else if(myAstrolabe.getBottomLeft() == 3){
                div = 12.0;
            }

            for (count = 1; count < div; count++){
                // print divison lines
                out += "\n" + "0 0 moveto";
                out += "\n" + (-shadowSide) + " "+ (-((shadowSide/div)*count)) + " lineto stroke";
                out += "\n" + "0 0 moveto";
                out += "\n" + (-((shadowSide/div)*count)) + " "+ (-shadowSide) + " lineto stroke";
            }

            out += "\n" + "newpath";
            out += "\n" + "1 setgray";
            out += "\n" + "0 0 moveto";
            out += "\n" + -(shadowSide-6) + " 0 lineto";
            out += "\n" + -(shadowSide-6) + " "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 0 lineto fill";
            out += "\n" + "newpath";
            out += "\n" + "0 setgray";
            out += "\n" + "0 0 moveto";
            out += "\n" + -(shadowSide-6) + " 0 lineto";
            out += "\n" + -(shadowSide-6) + " "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 "+ (-(shadowSide-6)) + " lineto";
            out += "\n" + "0 0 lineto stroke";

            if(div == 10 || div == 12){
                // if side is divided into ten or 12 sections, add a mark at five
                out += "\n" + "0 0 moveto";
                out += "\n" + (-shadowSide) + " "+ (-shadowSide/2.0) + " lineto stroke";
                out += "\n" + "0 0 moveto";
                out += "\n" + (-shadowSide/2.0) + " "+ (-shadowSide) + " lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + "1 setgray";
                out += "\n" + "0 0 moveto";
                out += "\n" + -(shadowSide-12) + " 0 lineto";
                out += "\n" + -(shadowSide-12) + " "+ (-(shadowSide-12)) + " lineto";
                out += "\n" + "0 "+ (-(shadowSide-12)) + " lineto";
                out += "\n" + "0 0 lineto fill";
                out += "\n" + "0 setgray";
                // mark mid line 
                out += "\n" + -((shadowSide/2.0)-6) + " "+ (-(shadowSide-7)) + " moveto";
                out += "\n" + "NormalFont5 setfont";
                if(div == 10){
                    out += "\n" + "(5) show";
                    out += "\n" + (-(shadowSide-8)) + " "+ (-(shadowSide/2.0)) + " moveto";
                    out += "\n" + "(5) show";
                }if(div == 12){
                    out += "\n" + "(6) show";
                    out += "\n" + (-(shadowSide-8)) + " "+ (-(shadowSide/2.0)) + " moveto";
                    out += "\n" + "(6) show";
                }
            }
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + -(shadowSide-12) + " 0 lineto";
            out += "\n" + -(shadowSide-12) + " "+ (-(shadowSide-12)) + " lineto";
            out += "\n" + "0 "+ (-(shadowSide-12)) + " lineto";
            out += "\n" + "0 0 lineto stroke";
            out += "\n" + "0 0 moveto";
            // 45 line        
            out += "\n" + (-shadowSide) + " "+ (-shadowSide) + " lineto stroke";
            // mark 45 line        
            out += "\n" + -(shadowSide-9) + " "+ (-(shadowSide-7)) + " moveto";
            out += "\n" + "NormalFont5 setfont";
            out += "\n" + "("+ Math.round(div) +") show";

            // Label
            out += "\n" + -(shadowSide/2.0) + " "+ (-(shadowSide-15)) + " moveto";
            out += "\n" + "NormalFont8 setfont";
            out += EPSToolKit.centerText("Umbra Recta");

            out += "\n" + -90 + " rotate";
            out += "\n" + (shadowSide/2.0) + " "+ (-(shadowSide-15)) + " moveto";
            out += "\n" + "NormalFont8 setfont";
            out += EPSToolKit.centerText("Umbra Versa");
            out += "\n" + 90 + " rotate";
            out += "\n" + "% =============== End Left Shadow Square =================";
        }

        if(myAstrolabe.getBottomLeft() == 4){
            //Draw bottomleft horz shadow scale
            out += "\n" + "% =============== Create Left Horz Shadow scale =================";
            // draw box
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + "-182 0 lineto";
            out += "\n" + "-182 -28 lineto";
            out += "\n" + "0 -28 lineto";
            out += "\n" + "0 0 lineto stroke";

            out += "\n" + "newpath";
            out += "\n" + "0 -23 moveto";
            out += "\n" + "-182 -23 lineto stroke";

            for (count = 0; count < 7; count++){
                out += "\n" + "newpath";
                out += "\n" + (-count * 28) + " 0 moveto";
                out += "\n" + (-count * 28) + " -28 lineto stroke";
            }

            for (count = 0; count < 26; count++){
                out += "\n" + "newpath";
                out += "\n" + (-count * 7) + " -23 moveto";
                out += "\n" + (-count * 7) + " -28 lineto stroke";
            }

            for (count = 1; count < 7; count++){
                // label
                out += "\n" + (-((count * 28) + 4)) + " -19 moveto";
                out += "\n" + "NormalFont5 setfont";
                out += EPSToolKit.centerText(Integer.toString(count));
            }

            out += "\n" + "% =============== End Left Horz Shadow scale =================";
        }

        if(myAstrolabe.getBottomRight() == 4){
            //Draw bottom right horz shadow scale
            out += "\n" + "% =============== Create Right Horz Shadow scale =================";
            // draw box
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + "182 0 lineto";
            out += "\n" + "182 -28 lineto";
            out += "\n" + "0 -28 lineto";
            out += "\n" + "0 0 lineto stroke";

            out += "\n" + "newpath";
            out += "\n" + "0 -23 moveto";
            out += "\n" + "182 -23 lineto stroke";

            for (count = 0; count < 7; count++)
            {
                out += "\n" + "newpath";
                out += "\n" + (count * 28) + " 0 moveto";
                out += "\n" + (count * 28) + " -28 lineto stroke";
            }

            for (count = 0; count < 26; count++)
            {
                out += "\n" + "newpath";
                out += "\n" + (count * 7) + " -23 moveto";
                out += "\n" + (count * 7) + " -28 lineto stroke";
            }

            for (count = 1; count < 7; count++)// label
            {
                out += "\n" + ((count * 28) + 4) + " -19 moveto";
                out += "\n" + "NormalFont5 setfont";
                out += EPSToolKit.centerText(Integer.toString(count));
            }

            out += "\n" + "% =============== End Right Horz Shadow scale =================";
        }

        if (myAstrolabe.getShowCotangentScale()){
            out += "\n" + "gsave";
            out += buildCotangentScale(myAstrolabe);
            out += "\n" + "grestore";
        }

        return out;
    }

    /**
     * computes and draws the Lunar Mansions
     *
     * @return  returns the ps code for drawing the Lunar Mansions
     */
    private String buildLunarMansions(){
        //compute size of shadow square box
        //Needed even if no shadow Squares are to be printed 
        int count;
        double shadowRadius = myAstrolabe.getMaterRadius() - 67;
        if(myAstrolabe.getShowCotangentScale()){
            //if we want to show the cotangent scale make room
            shadowRadius = shadowRadius - 13;
        }
        double mansionRadius = Math.sqrt((shadowRadius*shadowRadius)/2.0); //from pythagorus
        mansionRadius = mansionRadius - 15; // make space for shadow square scales
        String out = "";

        out += "\n" + "% ==================== Create Lunar Mansions ====================";
        out += "\n" + "% mark background";
        out += "\n" + "1 setgray"; // set to white	    
        out += "\n" + "newpath";
        out += "\n" +  "0 0 moveto";
        out += "\n" +  -mansionRadius + " 0 lineto";
        out += "\n" + "0 0 " + (mansionRadius) + " 180 360 arc";
        out += "\n" +  "0 0 lineto fill";

        out += "\n" + "% Draw circles";
        out += "\n" + "0 setgray";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + (mansionRadius) + " 180 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + (mansionRadius-16) + " 180 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + (mansionRadius-20) + " 180 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + (mansionRadius-55) + " 180 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + (mansionRadius-59) + " 180 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" +  mansionRadius + " 0 moveto";
        out += "\n" +  -mansionRadius + " 0 lineto stroke";

        out += "\n" + "% Draw zodiac divisons";
        out += "\n" + "0 setgray";
        for (count = 1; count < 12; count++){
            out += "\n" + "15 rotate";
            out += "\n" + "newpath";
            out += "\n" + (-mansionRadius) + " 0 moveto";
            out += "\n" + (-(mansionRadius - 16)) + " 0 lineto stroke";
        }
        // rotate back
        out += "\n" + "-165 rotate";

        out += "\n" + "% Draw Lunar House divisons 6.43 degrees each";
        out += "\n" + "0 setgray";
        for (count = 1; count < 28; count++){
            out += "\n" + "6.43 rotate";
            out += "\n" + "newpath";
            out += "\n" + (-(mansionRadius - 20)) + " 0 moveto";
            out += "\n" + (-(mansionRadius - 55)) + " 0 lineto stroke";
        }
        // rotate back
        out += "\n" + "-173.61 rotate";

        // Label
        //Mark Zodiac Labels
        out += "\n" + "gsave";
        out += "\n" + "-97.5 rotate";
        out += "\n" + "NormalFont5 setfont";
        for (count = 0; count <= 11; count++){
            out += "\n" + "15 rotate";
            out += ZodiacSigns.placeSignNumAt(count + 1, new Point2D.Double(0, (-(mansionRadius - 8))), .35, .35);
            //out += EPSToolKit.drawInsideCircularText( myAstrolabe.ZODIAC[count], 5, ((count*15)+187.5), (mansionRadius - 5));
        }
        out += "\n" + "grestore";

        //Mark Mansions Labels
        //out += "\n" + "3.215 rotate";
        out += "\n" + "NormalFont5 setfont";
        for (count = 0; count < 28; count++){
            out += "\n" + (- (mansionRadius -25 )) + " -5 moveto";
            out += "\n" + "(" + Astrolabe.LUNARHOUSESNAMES[count] + ") show";
            out += "\n" + "6.43 rotate";
        }
        //out += "\n" + "3.215 rotate";
        out += "\n" + "-180 rotate";
        return out;
    }

    /**
     * Computes the markings for the back bezel
     *
     * @return  returns the ps code for drawing the Degree Scale
     *
     */
    private String buildBackLimb(){
        double outerRadius = myAstrolabe.getMaterRadius();
        int count;
        int count1;
        String out = "";

        out += "\n" + "% ==================== Create Back ====================";
        out += "\n" + "% Draw outer circle";
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (outerRadius) + " 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";

        out += "\n" + "% Draw degree rings";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (outerRadius - 5) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 10) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 15) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 30) + " 0 360 arc stroke";

        // create 30 degree marks
        for (count = 1; count <= 12; count++){
            out += "\n" + (outerRadius - 30) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "30 rotate";
        }

        // create 10 degree marks
        for (count = 1; count <= 36; count++){
            out += "\n" + (outerRadius - 15) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "10 rotate";
        }

        // create 5 degree marks
        out += "\n" + "5 rotate"; //we make the 5 deg marks by rotating the 10 deg marks 5 deg
        for (count = 1; count <= 36; count++){
            out += "\n" + (outerRadius - 13) + " 0 moveto";
            out += "\n" + (outerRadius - 2) + " 0 lineto stroke";
            out += "\n" + "10 rotate";
        }
        out += "\n" + "-5 rotate"; //rotate back

        // create degree marks
        for (count = 1; count <= 360; count++){
            out += "\n" + (outerRadius - 10) + " 0 moveto";
            out += "\n" + (outerRadius -5) + " 0 lineto stroke";
            out += "\n" + "1 rotate";
        }

        if (myAstrolabe.getShowZodiacSymbols()){
            //Mark Zodiac symbols
            out += "\n" + "gsave";
            out += "\n" + "-75 rotate";
            for (count = 0; count <= 11; count++)
            {
                out += ZodiacSigns.placeSignNumAt(count+1, new Point2D.Double(0,(outerRadius - 22)),.35, .35);
                out += "\n" + "30 rotate";
            }
            out += "\n" + "grestore";
        }else{
            //Mark Zodiac Labels
            out += "\n" + "NormalFont10 setfont";
            for (count = 0; count <= 11; count++){
                out += EPSToolKit.drawOutsideCircularText(Astrolabe.ZODIAC[count], 10, ((count*30)+15), (outerRadius - 25));//
            }
        }

        //Mark Zodiac Degrees
        out += "\n" + "NormalFont5 setfont";
        for (count = 0; count <= 11; count++){
            for (count1 = 1; count1 <= 3; count1++){
                out += EPSToolKit.drawOutsideCircularText(Integer.toString(count1*10), 5, ((count*30)+(count1*10)-1), (outerRadius - 14));
            }
        }

        //Mark degrees 
        out += "\n" + "NormalFont5 setfont";
        for (count = 1; count <= 9; count++){
            out += EPSToolKit.drawOutsideCircularText(Integer.toString(count*10), 5, ((count*10)-1), (outerRadius - 4));
        }
        for (count = 1; count <= 9; count++){
            out += EPSToolKit.drawOutsideCircularText(Integer.toString(count*10), 5, (180-(count*10)+1), (outerRadius - 4));
        }


        out += "\n" + "0 setgray";
        out += "\n" + "%% ==================== End Create Back ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Labels the back of the Astrolabe 
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the Back Labels
     *
     *
     */
    public String labelBack(Astrolabe myAstrolabe){
        // get labels
        String meFecit = "astrolabeproject.com me fecit";
        String owner = myAstrolabe.getUserName();
        String local = myAstrolabe.getLocation().getLocationName();

        int lonDegrees = myAstrolabe.getLocation().getLonDeg();
        int lonMinutes = myAstrolabe.getLocation().getLonMin();
        String degreeString = lonDegrees + "\\260 "; //260 is degree symbol
        String minuteString = lonMinutes +"'";
        String longitudeLabel = "Longitude: " + degreeString + minuteString + myAstrolabe.getLocation().getLonDir();

        // determine label positions
        double fecitPos;
        double ownerPos;
        double correctionPos;
        double lonLabelPos;
        double localPos;
        String out = "";

        if(myAstrolabe.getShowConcentricCalendar()){
            fecitPos = myAstrolabe.getMaterRadius() - 60;
        }else{
            fecitPos = myAstrolabe.getMaterRadius() - 37;
        }
        if(myAstrolabe.getShowCotangentScale()){
            // make room
            ownerPos = myAstrolabe.getMaterRadius() - 91;
            correctionPos = myAstrolabe.getMaterRadius() - 101;
            lonLabelPos = myAstrolabe.getMaterRadius() - 111;
            localPos = myAstrolabe.getMaterRadius() - 121;
        }else{
            ownerPos = myAstrolabe.getMaterRadius() - 78;
            correctionPos = myAstrolabe.getMaterRadius() - 88;
            lonLabelPos = myAstrolabe.getMaterRadius() - 98;
            localPos = myAstrolabe.getMaterRadius() - 108;
        }
        out += "\n" + "NormalFont5 setfont";
        out += EPSToolKit.drawInsideCircularText(meFecit, 5, -90, fecitPos);

        out += "\n" + "NormalFont7 setfont";

        if((owner.length() > 0)&&(!owner.equals("Your Name"))){
            out += "\n" + "0 " + -ownerPos + " moveto";
            out += EPSToolKit.centerText(owner);
        }

        if(myAstrolabe.getShowTimeCorrection()){
            out += "\n" + "0 " + -correctionPos + " moveto";
            out += EPSToolKit.centerText(AstroMath.getTimeCorrection(myAstrolabe.getLocation().getLonDeg(),myAstrolabe.getLocation().getLonMin(),
                    myAstrolabe.getLocation().getLonSec(), myAstrolabe.getLocation().getLonDir(), false));
        }

        out += "\n" + "0 " + -lonLabelPos + " moveto";
        out += EPSToolKit.centerText(longitudeLabel);

        if(local.length() > 0){
            out += "\n" + "0 " + -localPos + " moveto";
            out += EPSToolKit.centerText(local);
        }

        return out;
    }

    public String drawEOT(){
        StringBuilder out = new StringBuilder();
        Double outerRadius = myAstrolabe.getMaterRadius() - 80;
        Double innerRadius = outerRadius/4.0;

        ArrayList<Point2D> points = AstroMath.equationOfTimePoints(innerRadius, outerRadius);

        if (isLaser){
            out.append("\n").append("0 0 1 setrgbcolor");
        }
        out.append("\n").append("newpath");
        out.append("\n").append(points.get(0).getX()).append(" ").append(points.get(0).getY()).append(" moveto");
        for (Point2D point : points){
            out.append("\n").append(point.getX()).append(" ").append(point.getY()).append(" lineto");
        }
        out.append("\n").append("stroke");

        Double outerlimit = myAstrolabe.getMaterRadius() - 80;
        Double innerLimit = outerlimit/4.0;
        boolean label = false;
        double scaling = (outerlimit - innerLimit)/34.0;
        for(int i = -14; i < 18; i = i + 2){
            if((i == 0)||((i%10) == 0)){
                label = true;
            }else if((i%5) == 0){
                label = true;
            }else{
                label = false;
            }
            double r = ((outerlimit+innerLimit)/2.0) - (i * scaling);

            out.append("\n").append("newpath");
            out.append("\n").append("0 0 ").append(r).append(" 0 360 arc stroke");

            if(label){ //todo, southern herishere?
                out.append("\n").append("newpath");
                out.append("\n").append("0 setgray");
                out.append("\n").append("NormalFont6 setfont");
                out.append("\n").append(r).append(" 0  moveto");
                out.append("\n").append(EPSToolKit.centerText(i+""));
                out.append("\n").append(-(r)).append(" 0  moveto");
                out.append("\n").append(EPSToolKit.centerText(i+""));
                if (isLaser){
                    out.append("\n").append("0 0 1 setrgbcolor");
                }
            }

        }
        return out.toString();
    }

    /**
     * Draws the back of the Astrolabe 
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @return  returns the ps code for drawing the back of the astrolabe
     *
     *
     */
    public String createBack(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Astrolabe Back");
        out += "\n" + "%% setup";

        if (myAstrolabe.getShowRegistrationMarks()){
            out += EPSToolKit.registrationMarks();
        }

        out += "\n" + "306 396 translate";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        if (myAstrolabe.getShowThrone()){
            if (isLaser){
                out += "\n" + "1 0 0 setrgbcolor";
            }
            out += "\n" + "gsave";
            if (myAstrolabe.getShapeOption() == 1){
                out += EPSToolKit.buildMaterThrone2(myAstrolabe);
            }else{
                out += EPSToolKit.buildMaterThrone(myAstrolabe);
            }
            out += "\n" + "grestore";
            out += "\n" + "";
            if (isLaser){
                out += "\n" + "gsave";
                out += "\n" + "1 0 0 setrgbcolor";
                out += EPSToolKit.buildScrewMount(myAstrolabe);
                out += "\n" + "0 setgray";
                out += "\n" + "grestore";
            }
        }

        if (myAstrolabe.getShapeOption() == 1){
            out += "\n" + "gsave";
            out += EPSToolKit.buildOctagon(myAstrolabe);
            out += "\n" + "grestore";
        }
        out += "\n" + "gsave";
        out += buildBackLimb();
        out += "\n" + "grestore";
        out += "\n" + "";
        out += "\n" + "gsave";

        if(myAstrolabe.getShowConcentricCalendar()){
            out += buildConcentricCalendarRing();
        }else{
            out += buildCalendarRing();
        }

        out += "\n" + "grestore";
        out += "\n" + "";

        //print third and fourth quadrant
        out += "\n" + "gsave";
        //out += buildShadowSquare();
        out += drawShadowSquare();
        out += "\n" + "grestore";

        out += labelBack(myAstrolabe);
        if (myAstrolabe.getShowLunarMansions()){
            //Arcs
            out += "\n" + "gsave";
            out += buildLunarMansions();
            out += "\n" + "grestore";
        }

        //print first and second quadrant
        out += "\n" + "gsave";
        if ((myAstrolabe.getTopLeft() == 2)||(myAstrolabe.getTopLeft() == 3)){
            //Sin cos grid
            out += "\n" + "gsave";
            SineGrid sineGridTool = new SineGrid(myAstrolabe);
            out += sineGridTool.buildSineGrid();
            out += "\n" + "grestore";
        }
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += buildUnequalHoursBack();
        out += "\n" + "grestore";
        if ((myAstrolabe.getTopRight() == 2)||(myAstrolabe.getTopRight() == 3)){
            //Arcs
            out += "\n" + "gsave";
            ArcsOfTheSigns arcsTool = new ArcsOfTheSigns(myAstrolabe);
            if ((myAstrolabe.getTopRight() == 2)){
                out += arcsTool.buildArcsOfSignsEqual();
            }else{
                out += arcsTool.buildArcsOfSignsProjected();
            }


            out += "\n" + "grestore";
        }

        // EOT
        if(myAstrolabe.getShowEquationOfTime()){
            out += "\n" + drawEOT();
        }

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
