package com.wymarc.astrolabe.generator.printengines.postscript.extras.volvelles;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.generator.printengines.postscript.util.ZodiacSigns;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

/**
 * Generates parts for a Lunar Volvelle
 */
public class Lunar {

    private Astrolabe myAstrolabe = new Astrolabe();
    private double lineWidth = .4;
    private boolean color = true;

    /**
     * Sets up routine to draw the cross markings for the volvelle
     * @return string containing the ps code for the cross
     */
    private static String setUpCrossCross(){
        String out = "";
        out += "\n" + "%% ================ Set Up CrossCross routine =================";
        out += "\n" + "/crosscross {";
        out += "\n" + "newpath      ";
        out += "\n" + "moveto       ";
        out += "\n" + "-1 1 rmoveto  ";
        out += "\n" + "0 4 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 -4 rlineto";
        out += "\n" + "4 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "-4 0 rlineto";
        out += "\n" + "0 -4 rlineto";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 4 rlineto ";
        out += "\n" + "-4 0 rlineto";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "-2 0 rlineto ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 2 rlineto  ";
        out += "\n" + "2 0 rlineto  ";
        out += "\n" + "0 -2 rlineto ";
        out += "\n" + "closepath    ";
        out += "\n" + "}def         ";
        out += "\n" + "";
        out += "\n" + "%% ================ End Set Up CrossCross routine =================";

        return out;
    }

    /**
     * Builds the concentric calendar ring
     *
     * @return  returns the ps code for drawing the Calendar ring
     *
     */
    private String buildConcentricCalendarRing(){
        double calendarRadius = 192.0;
        int count;
        int count2; // counters
        double t; //Time in Julian centuries from J2000.0

        String out = "";
        out += "\n" + "%% ================ Draw Concentric Calendar Ring =================";
        t = AstroMath.getT();

        // draw the ring outlines
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc stroke"; //use fill to remove hidden parts of line of apsides
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 3) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (calendarRadius - 15) + " 0 360 arc stroke";

        double jDay = 1.0/36525.0; // change in T per day

        // draw calendar markings and label
        for (count = 0; count < 365; count++){// mark days
            double rotation = AstroMath.geolong(t + (count * jDay));
            out += "\n" + rotation + " rotate";
            out += "\n" + (calendarRadius - 3) + " 0 moveto";
            out += "\n" + calendarRadius + " 0 lineto stroke";
            out += "\n" + -rotation + " rotate";
        }
        int totalDays = 0;
        for (count = 0; count <= 11; count++){// mark months
            double rotation = AstroMath.geolong(t + (totalDays * jDay));
            out += "\n" + rotation + " rotate";
            out += "\n" + (calendarRadius - 27) + " 0 moveto";
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
                    out += "\n" + (calendarRadius - 15) + " 0 moveto";
                    out += "\n" + calendarRadius + " 0 lineto stroke";
                }
                if((count2 == 5)||(count2 == 15)||(count2 == 25)){
                    out += "\n" + (calendarRadius - 15) + " 0 moveto";
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
            if (color && count % 2 == 0){
                out += "\n" + "1 0 0 setrgbcolor";
            }else{
                out += "\n" + "0 setgray";
            }
            out += EPSToolKit.drawInsideCircularText(Astrolabe.MONTHS[count], 10,
                    rotation, (calendarRadius - 18));
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "0 setgray";
        out += "\n" + "NormalFont10 setfont";  // label tens of days
        totalDays = 0;
        for (count = 0; count <= 11; count++){
            for (count2 = 0; count2 < Astrolabe.MONTHSDAYS[count]; count2++){
                double rotation = AstroMath.geolong(t + (totalDays + count2) * jDay);
                if((count2 == 10)||(count2 == 20)||(count2 == 30)){
                    out += EPSToolKit.drawInsideCircularText("" + count2, 10,
                            rotation - 2, (calendarRadius - 6));
                }
            }
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "%% ================ End Draw Concentric Calendar Ring =================";

        return out;
    }

    /**
     * Computes the markings for the zodiac scale
     *
     * @return  returns the ps code for drawing the zodiac Scale
     *
     */
    private String buildZodiac(){
        double outerRadius = 165.0;
        int count;
        int count1;
        String out = "";

        out += "\n" + "% ==================== Create zodiac ====================";
        out += "\n" + "% Draw outer circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";

        out += "\n" + "% Draw degree rings";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (outerRadius - 3) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 15) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 27) + " 0 360 arc stroke";

        // create 30 degree marks
        for (count = 1; count <= 12; count++){
            out += "\n" + (outerRadius - 27) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "30 rotate";
        }

        // create 10 degree marks
        for (count = 1; count <= 36; count++){
            out += "\n" + (outerRadius - 15) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "10 rotate";
        }

        // create degree marks
        for (count = 1; count <= 360; count++){
            out += "\n" + (outerRadius - 3) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "1 rotate";
        }

        //Mark Zodiac Labels
        out += "\n" + "NormalFont10 setfont";
        for (count = 0; count <= 11; count++){
            if (color){
                if (count == 0 || count == 4 || count == 8){
                    out += "\n" + "1 0 0 setrgbcolor";
                }else if (count == 2 || count == 6 || count == 10){
                    out += "\n" + "0 0 1 setrgbcolor";
                }else if (count == 3 || count == 7 || count == 11){
                    out += "\n" + "0 1 0 setrgbcolor";
                }else{
                    out += "\n" + "0 setgray";
                }
            }else{
                out += "\n" + "0 setgray";
            }
            out += EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[count], 10, ((count * 30) + 15),
                    (outerRadius - 18));//
        }
        out += "\n" + "0 setgray";

        //Mark Zodiac Degrees
        out += "\n" + "NormalFont10 setfont";
        for (count = 0; count <= 11; count++){
            for (count1 = 1; count1 <= 3; count1++){
                out += EPSToolKit.drawInsideCircularText(Integer.toString(count1 * 10), 10, ((count * 30) +
                        (count1 * 10) - 2), (outerRadius - 6));
            }
        }

        out += "\n" + "0 setgray";
        out += "\n" + "%% ==================== End Create zodiac ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Computes the markings Sun Disc
     *
     * @return  returns the ps code for drawing the sun disc
     *
     */
    private String buildSunDisc(){
        double outerRadius = 96;
        double workingRadius = 189.0;
        int count;
        String out = "";

        out += "\n" + "% ==================== Create sun disc ====================";
        out += "\n" + "% Sun pointer";
        out += "\n" + "0 setgray";
        out += "\n" + "newpath";
        out += "\n" + outerRadius + " 0 moveto";
        out += "\n" + workingRadius + " 0 lineto";
        out += "\n" + (workingRadius -10) + " 10 lineto";
        out += "\n" + (workingRadius -100) + " 50 lineto";
        out += "\n" + "0 50 lineto stroke";

        out += "\n" + "% secondary pointers";
        out += "\n" + "0 setgray";
        out += "\n" + "newpath";
        out += "\n" + -outerRadius + " 0 moveto";
        out += "\n" + "-114 0 lineto";
        out += "\n" + -(outerRadius - 10) + " -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 114 lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 -114 lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";

        out += "\n" + "% Draw outer circle";
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";

        out += "\n" + "% inner circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (outerRadius - 3) + " 0 360 arc stroke";

        out += "\n" + "% innermost circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (outerRadius - 15) + " 0 360 arc stroke";

//        out += "\n" + "newpath";
//        out += "\n" + (-78.0/2.0) + " 0 21 0 360 arc stroke";
//        out += "\n" + "newpath";
//        out += "\n" + (78.0/2.0) + " 0 21 0 360 arc stroke";
//
//        out += "\n" + "newpath";
//        out += "\n" + "0 " + (-78.0/2.0) + " 21 0 360 arc stroke";
//        out += "\n" + "newpath";
//        out += "\n" + "0 " + (78.0/2.0) + " 21 0 360 arc stroke";

        out += "\n" + "% sun circle";
        out += "\n" + "0 setgray";
        out += "\n" + "21 0 39 0 360 arc stroke";
        out += "\n" + "0 0 60 0 360 arc stroke";

        // create day marks
        out += "\n" + "gsave";
        double step = 360.0/118.0;
        for (count = 0; count < 118; count++){
            out += "\n" + "newpath";
            if ( count % 4 == 0){
                out += "\n" + (outerRadius - 15) + " 0 moveto";
            }else{
                out += "\n" + (outerRadius - 3) + " 0 moveto";
            }
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + step + " rotate";
        }
        out += "\n" + "grestore";

        out += "\n" + "NormalFont12 setfont";
        for (count = 1; count <= 29; count++){
            if (color && count % 2 != 0){
                out += "\n" + "1 0 0 setrgbcolor";
            }else{
                out += "\n" + "0 setgray";
            }
            out += EPSToolKit.drawInsideCircularText(Integer.toString(count), 5, ((count -0.5) * (360.0/29.5)), (outerRadius - 5));
        }

        out += "\n" + "0 setgray";

        out += "\n" + "%% ==================== End Create sun disc ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Computes the markings for the moon disc
     *
     * @return  returns the ps code for drawing the moon disc
     *
     */
    private String buildMoonDisc(){
        double outerRadius = 78.0;
        double workingRadius = 189.0;
        String out = "";

        out += "\n" + "% ==================== Create moon disc ====================";
        out += "\n" + "% Moon pointer";
        out += "\n" + "0 setgray";
        out += "\n" + "newpath";
        out += "\n" + outerRadius + " 0 moveto";
        out += "\n" + workingRadius + " 0 lineto";
        out += "\n" + (workingRadius -10) + " 10 lineto";
        out += "\n" + (workingRadius -100) + " 50 lineto";
        out += "\n" + "0 50 lineto stroke";

        out += "\n" + "% secondary pointers";
        out += "\n" + "0 setgray";
        out += "\n" + "newpath";
        out += "\n" + -outerRadius + " 0 moveto";
        out += "\n" + "-96 0 lineto";
        out += "\n" + -(outerRadius - 10) + " -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 96 lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 -96 lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";
        out += "\n" + "gsave";
        out += "\n -30 rotate";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 96 lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 -96 lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";
        out += "\n 30 rotate";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n 30 rotate";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 96 lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 -96 lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";
        out += "\n -30 rotate";
        out += "\n" + "grestore";

        out += "\n" + "% Draw outer circle";
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (outerRadius + 3) + " 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (outerRadius + 3) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 12) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 15) + " 0 360 arc stroke";

        out += "\n" + "% secondary pointer lines";
        out += "\n" + "newpath"; // opposition
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -(outerRadius - 15) + " 0 lineto stroke";
        out += "\n" + "newpath"; // 60
        double x = Math.sin(Math.toRadians(-30.0)) * (outerRadius - 15);
        double y = Math.cos(Math.toRadians(-30.0)) * (outerRadius - 15);
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -x + " " + y + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -x + " " + -y + " lineto stroke ";
        out += "\n" + "newpath"; // 90
        x = Math.sin(Math.toRadians(0.0)) * (outerRadius - 15);
        y = Math.cos(Math.toRadians(0.0)) * (outerRadius - 15);
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -x + " " + y + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -x + " " + -y + " lineto stroke";
        out += "\n" + "newpath"; // 120
        x = Math.sin(Math.toRadians(30.0)) * (outerRadius - 15);
        y = Math.cos(Math.toRadians(30.0)) * (outerRadius - 15);
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -x + " " + y + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + (outerRadius - 18) + " 0 moveto";
        out += "\n" + -x + " " + -y + " lineto stroke";

        out += "\n" + "% phase cutout";
        out += "\n" + "1 setgray";
        out += "\n" + "newpath";
        out += "\n" + (outerRadius/2.0) + " 0 21 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "newpath";
        out += "\n" + (outerRadius/2.0) + " 0 21 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + ((outerRadius/2.0)-5) + " 0 moveto";
        out += "\n" + ((outerRadius/2.0)+5) + " 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + (outerRadius/2.0) + " -5 moveto";
        out += "\n" + (outerRadius/2.0) + " 5 lineto stroke";

        out += "\n" + "0 setgray";
        out += "\n" + "%% ==================== End Create moon disc ====================";
        out += "\n" + "";

        return out;
    }


    /**
     * Computes the markings for the day length scale
     *
     * @return  returns the ps code for drawing the day length scale
     *
     */
    private String buildDayLengthScale(){
        double innerRadius = 96 + 12; //12 for scale inside this one
        String out = "";

        out += "\n" + "% ==================== Create day length scale ====================";
        out += "\n" + "% middle circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 15) + " 0 360 arc stroke";

        out += "\n" + "% outer circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 27) + " 0 360 arc stroke";

        double[] marks = { 24.0, 60.0, 90.0, 120.0, 156.0 };
        double[] ticks = { 33.0, 42.0, 51.0, 67.5, 75.0, 82.5, 97.5, 105.0, 112.5, 129.0, 138.0, 147.0 };
        String[] innerLabels= {"4", "5", "6", "7", "8", "8", "7", "6", "5", "4"};
        String[] outerLabels= {"8", "7", "6", "5", "4", "4", "5", "6", "7", "8"};

        // mark day length lines for research - marks out to calendar ring, replaces similar section below
//        out += "\n" + "gsave";
//        out += "\n" + "90 rotate";
//        for (double angle : marks){
//            out += "\n" + angle + " rotate";
//            out += "\n" + (innerRadius + 3) + " 0 moveto";
//            out += "\n" + (innerRadius + 127) + " 0 lineto stroke";
//            out += "\n" + (-(innerRadius + 3)) + " 0 moveto";
//            out += "\n" + (-(innerRadius + 127)) + " 0 lineto stroke";
//            out += "\n" + (-angle) + " rotate";
//        }
//        out += "\n" + "-90 rotate";
//        out += "\n" + "grestore";
//        out += "\n" + "gsave";
//        out += "\n" + "90 rotate";
//        for (double angle : ticks){
//            out += "\n" + angle + " rotate";
//            out += "\n" + (innerRadius + 3) + " 0 moveto";
//            out += "\n" + (innerRadius + 127) + " 0 lineto stroke";
//            out += "\n" + (-(innerRadius + 3)) + " 0 moveto";
//            out += "\n" + (-(innerRadius + 127)) + " 0 lineto stroke";
//            out += "\n" + (-angle) + " rotate";
//        }
//        out += "\n" + (-(innerRadius + 127)) + " 0 moveto";
//        out += "\n" + (innerRadius + 127) + " 0 lineto stroke";
//        out += "\n" + "-90 rotate";
//        out += "\n" + "grestore";

        // mark day length lines
        int count = 0;
        out += "\n" + "gsave";
        out += "\n" + "NormalFont10 setfont";
        out += "\n" + "90 rotate";
        for (double angle : marks){
            out += "\n" + angle + " rotate";
            out += "\n" + (innerRadius + 3) + " 0 moveto";
            out += "\n" + (innerRadius + 27) + " 0 lineto stroke";
            out += "\n" + (-(innerRadius + 3)) + " 0 moveto";
            out += "\n" + (-(innerRadius + 27)) + " 0 lineto stroke";
            out += "\n" + (-angle) + " rotate";
            if (color){
                out += "\n" + "1 0 0 setrgbcolor";
            }else{
                out += "\n" + "0 setgray";
            }
            out += EPSToolKit.drawInsideCircularText(innerLabels[count], 10, angle, (innerRadius + 12));
            out += EPSToolKit.drawInsideCircularText(innerLabels[count], 10, -angle, (innerRadius + 12));
            out += "\n" + "0 setgray";
            out += EPSToolKit.drawInsideCircularText(outerLabels[count], 10, angle, (innerRadius + 24));
            out += EPSToolKit.drawInsideCircularText(outerLabels[count], 10, -angle, (innerRadius + 24));
            count++;
        }
        out += "\n" + "-90 rotate";
        out += "\n" + "grestore";

        // create hour ticks
        out += "\n" + "gsave";
        out += "\n" + "90 rotate";
        for (double angle : ticks){
            out += "\n" + angle + " rotate";
            out += "\n" + (innerRadius + 15) + " 0 1 0 360 arc fill";
            out += "\n" + (-(innerRadius + 15)) + " 0 1 0 360 arc fill";
            out += "\n" + (-angle) + " rotate";
        }
        out += "\n" + "-90 rotate";
        out += "\n" + "grestore";

        // crosses
        out += "\n" + "0 setgray";
        if (color){
            out += "\n" + "0 0 1 setrgbcolor";
        }else{
            out += "\n" + ".5 setgray";
        }
        out += "\n" + "0 123 crosscross fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 -123 crosscross fill";
        out += "\n" + "%% ==================== End create day length scale ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Computes the markings for the noon altitude scale
     *
     * @return  returns the ps code for drawing the noon altitude scale
     *
     */
    private String buildNoonAltitudeScale(){
        double innerRadius = 96;
        String out = "";
        out += "\n" + "gsave";
        out += "\n" + "% ==================== Create noon altitude scale ====================";
        out += "\n" + "% Draw inner circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + innerRadius + " 0 360 arc stroke";

        out += "\n" + "% innermost circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 12) + " 0 360 arc stroke";

        out += "\n" + "% innermost circle";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 15) + " 0 360 arc stroke";

        out += "\n" + "grestore";

        // compute max and min altitude of the sun at the latitude of Oxford
        // latitude of Oxford: 51.8N
        // Obliquity: 23.44
        double oxford = 51.8;
        double max = (90.0 - oxford) + 23.44; //todo kludge
        double min = (90.0 - oxford) - 23.44;

        // round results to nearest 5 degrees, round up for min, down for max
        max = 61.0; //todo kludge
        min = 5.0*(Math.ceil(Math.abs(min/5.0)));

        // mark every 5 degrees between min and max inclusive
        // create hour ticks
        out += "\n" + "gsave";
        out += "\n" + "NormalFont10 setfont";
        for (double alt = min; alt <= max; alt = alt + 5.0 ){
            String label = Integer.toString((int)alt);
            double angle = AstroMath.zodiacAngleForNoonAltitude(oxford, alt);
            out += "\n" + angle + " rotate";
            out += "\n" + innerRadius + " 0 moveto";
            out += "\n" + (innerRadius + 15) + " 0 lineto stroke";
            out += "\n" + (-angle) + " rotate";
            out += "\n" + (180 - angle) + " rotate";
            out += "\n" + innerRadius + " 0 moveto";
            out += "\n" + (innerRadius + 15) + " 0 lineto stroke";
            out += "\n" + (-(180 - angle)) + " rotate";
            if (color){
                out += "\n" + "1 0 0 setrgbcolor";
            }else{
                out += "\n" + "0 setgray";
            }
            out += EPSToolKit.drawInsideCircularText(label, 10, (angle - 4), (innerRadius + 10));
            out += EPSToolKit.drawInsideCircularText(label, 10, ((180 - angle) + 4), (innerRadius + 10));
            out += "\n" + "0 setgray";
        }
        out += "\n" + "grestore";

        // mark every degree between min and max inclusive
        // create hour ticks
        out += "\n" + "gsave";
        for (double alt = min; alt <= max; alt = alt + 1.0 ){
            double angle = AstroMath.zodiacAngleForNoonAltitude(oxford, alt);
            out += "\n" + angle + " rotate";
            out += "\n" + (innerRadius + 12) + " 0 moveto";
            out += "\n" + (innerRadius + 15) + " 0 lineto stroke";
            out += "\n" + (-angle) + " rotate";
            out += "\n" + (180 - angle) + " rotate";
            out += "\n" + (innerRadius + 12) + " 0 moveto";
            out += "\n" + (innerRadius + 15) + " 0 lineto stroke";
            out += "\n" + (-(180 - angle)) + " rotate";
        }
        out += "\n" + "grestore";
        out += "\n" + "%% ==================== End create noon altitude scale ====================";
        out += "\n" + "";

        return out;
    }


    /**
     * Generates the ps code for drawing the base sheet of the Volvelle
     * @return returns the ps code for drawing the base sheet with the clock face and wind rose
     */
    private String buildVolvelleBase(){
        double innerRadius = 192.0;
        String out = "";

        out += "\n" + "% ==================== Create base sheet ====================";
        out += "\n" + "% ==================== Create clock ring ====================";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + innerRadius + " 0 360 arc stroke";

        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 12) + " 0 360 arc stroke";

        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 15) + " 0 360 arc stroke";

        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (innerRadius + 18) + " 0 360 arc stroke";

        out += "\n" + "gsave";
        double rotationIncrement = (double)360/1440; //degrees per minute
        int count;
        // create 15 minute marks
        for (count = 1; count <= 96; count++){
            out += "\n" + (innerRadius + 12) + " 0 moveto";
            out += "\n" + (innerRadius + 15) + " 0 lineto stroke";
            out += "\n" + (15 * rotationIncrement) + " rotate";
        }
        // create hour marks
        for (count = 1; count <= 24; count++){
            out += "\n" + innerRadius + " 0 moveto";
            out += "\n" + (innerRadius + 15) + " 0 lineto stroke";
            out += "\n" + (60 * rotationIncrement) + " rotate";
        }
        out += "\n" + "grestore";

        String[] hourArray = Astrolabe.ARABICHOURS;
        out += "\n" + "NormalFont12 setfont";
        if (color){
            out += "\n" + "1 0 0 setrgbcolor";
        }else{
            out += "\n" + "0 setgray";
        }
        for (count = 0; count <= 11; count++){
            out += EPSToolKit.drawOutsideCircularText(hourArray[count], 10, (-90+(count*15)), (innerRadius + 2));
        }
        for (count = 0; count <= 11; count++){
            out += EPSToolKit.drawOutsideCircularText(hourArray[count], 10, (-270+(count*15)), (innerRadius + 2));
        }
        out += "\n" + "0 setgray";

        out += "\n" + "% ==================== End Create clock ring ====================";

        out += "\n" + "% ==================== Create wind rose ====================";

        out += "\n" + "gsave";
        rotationIncrement = (double)360/32; //degrees per compass point
        // create compass point marks
        for (count = 0; count < 32; count++){
            out += "\n" + (innerRadius + 15) + " 0 moveto";
            out += "\n" + (innerRadius + 18) + " 0 lineto stroke";
            out += "\n" + rotationIncrement + " rotate";
        }
        out += "\n" + "grestore";

        out += "\n" + "NormalFont20 setfont";
        out += "\n" + "0 " + (innerRadius + 22) + " moveto";
        if (color){
            out += "\n" + "1 0 0 setrgbcolor";
        }else{
            out += "\n" + "0 setgray";
        }
        out += EPSToolKit.centerText("South");
        out += "\n" + "90 rotate";
        out += "\n" + "0 " + (innerRadius + 22) + " moveto";
        if (color){
            out += "\n" + "0 0 1 setrgbcolor";
        }else{
            out += "\n" + "0 setgray";
        }
        out += EPSToolKit.centerText("East");
        out += "\n" + "90 rotate";
        out += "\n" + "0 " + (innerRadius + 22) + " moveto";
        if (color){
            out += "\n" + "1 0 0 setrgbcolor";
        }else{
            out += "\n" + "0 setgray";
        }
        out += EPSToolKit.centerText("North");
        out += "\n" + "90 rotate";
        out += "\n" + "0 " + (innerRadius + 22) + " moveto";
        if (color){
            out += "\n" + "0 0 1 setrgbcolor";
        }else{
            out += "\n" + "0 setgray";
        }
        out += EPSToolKit.centerText("West");
        out += "\n" + "90 rotate";
        out += "\n" + "0 setgray";

        String[] compassArray = {"West","W b N", "WNW", "NW b W", "Northwest", "NW b N", "NNW", "N b W", "North", "N b E",
                "NNE", "NE b N", "Northeast", "NE b E", "EnE", "E b N", "East", "E b S", "ESE", "SE b E", "Southeast",
                "SE b S", "SSE", "S b E", "South", "S b W", "SSW", "SW b S", "Southwest", "SW b W", "WSW", "W b S"};

        out += "\n" + "gsave";
        rotationIncrement = (double)360/32; //degrees per compass point
        // create compass point marks
        out += "\n" + "NormalFont12 setfont";
        for (count = 0; count < 32; count++){
            if (count != 0 && count != 8 && count != 16 && count != 24){
                if (color && count % 2 == 0){
                    out += "\n" + "1 0 0 setrgbcolor";
                }else{
                    out += "\n" + "0 setgray";
                }
                out += "\n" + "(" + compassArray[count] + ") " + (innerRadius + 22) + " -5 moveto show";
            }
            out += "\n" + (-rotationIncrement) + " rotate";
        }
        out += "\n" + "grestore";

        out += "\n" + "%% ==================== end create wind rose ====================";
        out += "\n" + "% ==================== end create base sheet ====================";
        out += "\n" + "";

        return out;
    }


    /**
     * Generates the ps code for drawing the zodiac calendar disc of the Volvelle
     *
     * @param   myAstrolabeIn    the astrolabe object (used for some routines)
     * @return  returns the ps code for drawing the zodiac calendar disc
     *
     */
    public String createVolvelleZodiacCalendarDisc(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Lunar Volvelle Calendar");
        out += "\n" + "%% setup";

        out += "\n" + "306 396 translate";
        out += "\n" + lineWidth + " setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();
        out += setUpCrossCross();

        out += "\n" + "% ==================== Create zodiac calendar disc ====================";
        out += "\n" + "gsave";
        out += buildConcentricCalendarRing();
        out += "\n" + "grestore";
        out += "\n" + "";

        out += "\n" + "gsave";
        out += buildZodiac();
        out += "\n" + "grestore";
        out += "\n" + "";

        out += "\n" + "gsave";
        out += buildNoonAltitudeScale();
        out += "\n" + "grestore";
        out += "\n" + "";

        out += "\n" + "gsave";
        out += buildDayLengthScale();
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
        out += "\n" + "% ==================== end create zodiac calendar disc ====================";

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }

    /**
     * Generates the ps code for drawing the sun disc of the Volvelle
     *
     * @param   myAstrolabeIn    the astrolabe object (used for some routines)
     * @return  returns the ps code for drawing the sun disc
     *
     */
    public String createVolvelleSunDisc(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Lunar Volvelle Sun");
        out += "\n" + "%% setup";

        out += "\n" + "306 396 translate";
        out += "\n" + lineWidth + " setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        out += "\n" + "gsave";
        out += buildSunDisc();
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

    /**
     * Generates the ps code for drawing the moon disc of the Volvelle
     *
     * @param   myAstrolabeIn    the astrolabe object (used for some routines)
     * @return  returns the ps code for drawing the moon disc
     *
     */
    public String createVolvelleMoonDisc(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Lunar Volvelle Sun");
        out += "\n" + "%% setup";

        out += "\n" + "306 396 translate";
        out += "\n" + lineWidth + " setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        out += "\n" + "gsave";
        out += buildMoonDisc();
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

    /**
     * Generates the ps code for drawing the base page of the Volvelle
     *
     * @param   myAstrolabeIn    the astrolabe object (used for some routines)
     * @return  returns the ps code for drawing the base page
     *
     */
    public String createBasePage(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Lunar Volvelle Sun");
        out += "\n" + "%% setup";

        out += "\n" + "306 396 translate";
        out += "\n" + lineWidth + " setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        out += "\n" + "gsave";
        out += buildVolvelleBase();
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
