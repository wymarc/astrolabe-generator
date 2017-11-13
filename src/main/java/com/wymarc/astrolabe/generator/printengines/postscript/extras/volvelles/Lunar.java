package com.wymarc.astrolabe.generator.printengines.postscript.extras.volvelles;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.generator.printengines.postscript.util.ZodiacSigns;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

/**
 * Created by user on 11/5/2017.
 */
public class Lunar {

    private Astrolabe myAstrolabe = new Astrolabe();
    private double lineWidth = .4;
    private double workingRadius = 215.0;


    /**
     * Builds the concentric calendar ring
     *
     * @return  returns the ps code for drawing the Calendar ring
     *
     */
    private String buildConcentricCalendarRing(){
        double calendarRadius = workingRadius;
        int count;
        int count2; // counters
        double lineOfApsides; //angle of line of apsides
        double t; //Time in Julian centuries from J2000.0

        String out = "";
        out += "\n" + "%% ================ Draw Concentric Calendar Ring =================";
        t = AstroMath.getT();

        // draw the ring outlines
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (calendarRadius) + " 0 360 arc stroke"; //use fill to remove hidden parts of line of apsides
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
            out += EPSToolKit.drawInsideCircularText(Astrolabe.MONTHS[count], 10,
                    rotation, (calendarRadius - 12));
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "NormalFont5 setfont";  // label tens of days
        totalDays = 0;
        for (count = 0; count <= 11; count++){
            for (count2 = 0; count2 < Astrolabe.MONTHSDAYS[count]; count2++){
                double rotation = AstroMath.geolong(t + (totalDays + count2) * jDay);
                if((count2 == 10)||(count2 == 20)||(count2 == 30)){
                    out += EPSToolKit.drawInsideCircularText("" + count2, 5,
                            rotation, (calendarRadius - 6));
                }
            }
            totalDays += Astrolabe.MONTHSDAYS[count];
        }
        out += "\n" + "%% ================ End Draw Concentric Calendar Ring =================";

        return out;
    }

    /**
     * Computes the markings for the back bezel
     *
     * @return  returns the ps code for drawing the Degree Scale
     *
     */
    private String buildZodiac(){
        double outerRadius = workingRadius - 20;
        int count;
        int count1;
        String out = "";

        out += "\n" + "% ==================== Create zodiac ====================";
        out += "\n" + "% Draw outer circle";
//        out += "\n" + "1 setgray";
//        out += "\n" + "0 0 " + (outerRadius) + " 0 360 arc stroke";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";

        out += "\n" + "% Draw degree rings";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + (outerRadius - 5) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 10) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 25) + " 0 360 arc stroke";

        // create 30 degree marks
        for (count = 1; count <= 12; count++){
            out += "\n" + (outerRadius - 25) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "30 rotate";
        }

        // create 10 degree marks
        for (count = 1; count <= 36; count++){
            out += "\n" + (outerRadius - 10) + " 0 moveto";
            out += "\n" + outerRadius + " 0 lineto stroke";
            out += "\n" + "10 rotate";
        }

        // create 5 degree marks
        out += "\n" + "5 rotate"; //we make the 5 deg marks by rotating the 10 deg marks 5 deg
        for (count = 1; count <= 36; count++){
            out += "\n" + (outerRadius - 8) + " 0 moveto";
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
                out += ZodiacSigns.placeSignNumAt(count + 1, new Point2D.Double(0, (outerRadius - 22)), .35, .35);
                out += "\n" + "30 rotate";
            }
            out += "\n" + "grestore";
        }else{
            //Mark Zodiac Labels
            out += "\n" + "NormalFont10 setfont";
            for (count = 0; count <= 11; count++){
                out += EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[count], 10, ((count * 30) + 15), (outerRadius - 14));//
            }
        }

        //Mark Zodiac Degrees
        out += "\n" + "NormalFont5 setfont";
        for (count = 0; count <= 11; count++){
            for (count1 = 1; count1 <= 3; count1++){
                out += EPSToolKit.drawInsideCircularText(Integer.toString(count1 * 10), 5, ((count * 30) + (count1 * 10) - 1), (outerRadius - 1));
            }
        }

        out += "\n" + "0 setgray";
        out += "\n" + "%% ==================== End Create zodiac ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Computes the markings for the back bezel
     *
     * @return  returns the ps code for drawing the Degree Scale
     *
     */
    private String buildSunDisk(){
        double outerRadius = 96;
        int count;
        int count1;
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
        out += "\n" + -(outerRadius + 10) + " 0 lineto";
        out += "\n" + -(outerRadius - 10) + " -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 " + (outerRadius + 10) + " lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 " + -(outerRadius + 10) + " lineto";
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

        out += "\n" + "% sun circle";
        out += "\n" + "0 setgray";
        out += "\n" + "30 0 48 0 360 arc stroke";

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

//        out += "\n" + "NormalFont12 setfont";
//        for (count = 1; count <= 30; count++){
//            out += EPSToolKit.drawInsideCircularText(Integer.toString(count), 5, (count * 12), (outerRadius - 4));
//        }

        out += "\n" + "0 setgray";
        out += "\n" + "%% ==================== End Create zodiac ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Computes the markings for the back bezel
     *
     * @return  returns the ps code for drawing the Degree Scale
     *
     */
    private String buildMoonDisk(){
        double outerRadius = 78.0;
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
        out += "\n" + -(outerRadius + 10) + " 0 lineto";
        out += "\n" + -(outerRadius - 10) + " -20 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 " + (outerRadius + 10) + " lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 " + -(outerRadius + 10) + " lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";
        out += "\n" + "gsave";
        out += "\n -30 rotate";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 " + (outerRadius + 10) + " lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 " + -(outerRadius + 10) + " lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";
        out += "\n 30 rotate";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n 30 rotate";
        out += "\n" + "newpath";
        out += "\n" + "0 " + outerRadius + " moveto";
        out += "\n" + "0 " + (outerRadius + 10) + " lineto";
        out += "\n" + "-20 " + (outerRadius - 10) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 " + -outerRadius + " moveto";
        out += "\n" + "0 " + -(outerRadius + 10) + " lineto";
        out += "\n" + "20 " + -(outerRadius - 10) + " lineto stroke";
        out += "\n -30 rotate";
        out += "\n" + "grestore";

        out += "\n" + "% Draw outer circle";
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + outerRadius + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (outerRadius - 3) + " 0 360 arc stroke";
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

        out += "\n" + "0 setgray";
        out += "\n" + "%% ==================== End Create zodiac ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws the back of the Astrolabe
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @return  returns the ps code for drawing the back of the astrolabe
     *
     *
     */
    public String createVolvelleBase(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Lunar Volvelle");
        out += "\n" + "%% setup";

        out += "\n" + "306 396 translate";
        out += "\n" + lineWidth + " setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        out += "\n" + "gsave";
        out += buildZodiac();
        out += "\n" + "grestore";
        out += "\n" + "";
        out += "\n" + "gsave";
        out += buildConcentricCalendarRing();
        out += "\n" + "grestore";

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
     * Draws the back of the Astrolabe
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @return  returns the ps code for drawing the back of the astrolabe
     *
     *
     */
    public String createVolvelleSun(Astrolabe myAstrolabeIn){

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
        out += buildSunDisk();
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
     * Draws the back of the Astrolabe
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @return  returns the ps code for drawing the back of the astrolabe
     *
     *
     */
    public String createVolvelleMoon(Astrolabe myAstrolabeIn){

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
        out += buildMoonDisk();
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
