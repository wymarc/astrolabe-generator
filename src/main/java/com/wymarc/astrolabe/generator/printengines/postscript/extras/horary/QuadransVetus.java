package com.wymarc.astrolabe.generator.printengines.postscript.extras.horary;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;

/**
 * This Plugin will calculate the components of A quadrans ventus and
 * print the results to an Encapsulated PostScript (EPS) file.
 *
 * <p>
 * link      http://astrolabeproject.com
 * link      http://www.astrolabes.org
 */

public class QuadransVetus {

    private boolean isLaser = true;

    private String drawDegreeScale() {
        StringBuilder out = new StringBuilder();

        // draw arcs
        out.append("\n").append("% degree scale")
                .append("\n").append("0 0 494 270 360 arc stroke")
                .append("\n").append("0 0 490 270 360 arc stroke")
                .append("\n").append("");

        // create 1 degree marks
        for (int count = 1; count <= 90; count++) {
            out.append("\n").append("490 0 moveto")
                    .append("\n").append("494 0 lineto stroke")
                    .append("\n").append("-1 rotate");
        }
        out.append("\n").append("90 rotate");

        // create 5 degree marks
        for (int count = 1; count <= 18; count++) {
            out.append("\n").append("490 0 moveto")
                    .append("\n").append("498 0 lineto stroke")
                    .append("\n").append("-5 rotate");
        }
        out.append("\n").append("90 rotate");

        // create 10 degree marks
        for (int count = 1; count <= 9; count++) {
            out.append("\n").append("490 0 moveto")
                    .append("\n").append("504 0 lineto stroke")
                    .append("\n").append("-10 rotate");
        }
        out.append("\n").append("90 rotate");

        if (isLaser){
            out.append("\n").append("0 1 0 setrgbcolor");
        }
        //Mark degrees
        out.append("\n").append("/Times-Roman findfont 8 scalefont setfont");
        for (int count = 1; count <= 9; count++) {
            out.append(EPSToolKit.drawInsideCircularText((count * 10) + "", 5, (-90 + (count * 10) - .75), 502));
        }
        if (isLaser){
            out.append("\n").append("0 setgray");
        }

        return out.toString();
    }

    /**
     * computes and draws the shadow squares
     *
     * @return returns the ps code for drawing the Shadow Squares
     */
    private String drawShadowSquare() {
        //compute size of box
        double shadowRadius = 403.0;
        double shadowSide = Math.sqrt((shadowRadius * shadowRadius) / 2.0); //from pythagoras
        double div = 12.0;
        StringBuilder out = new StringBuilder();

        out.append("\n").append("% Shadow square")
                //Draw bottom right
                .append("\n").append("% =============== Create Shadow Square =================")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" 0 lineto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 ").append(-shadowSide).append(" lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("0 0 moveto");

        for (int count = 1; count < div; count++)// print division lines
        {
            out.append("\n").append("0 0 moveto")
                    .append("\n").append(shadowSide).append(" ").append(-((shadowSide / div) * count)).append(" lineto stroke")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append(((shadowSide / div) * count)).append(" ").append(-shadowSide).append(" lineto stroke");
        }
        out.append("\n").append("newpath")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 12).append(" 0 lineto")
                .append("\n").append(shadowSide - 12).append(" ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 0 lineto fill")
                .append("\n").append("newpath")
                .append("\n").append("0 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 12).append(" 0 lineto")
                .append("\n").append(shadowSide - 12).append(" ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 12)).append(" lineto")
                .append("\n").append("0 0 lineto stroke")

                //draw 1/2 way mark
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide).append(" ").append(-shadowSide / 2.0).append(" lineto stroke")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide / 2.0).append(" ").append(-shadowSide).append(" lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("1 setgray")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 24).append(" 0 lineto")
                .append("\n").append(shadowSide - 24).append(" ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 0 lineto fill")
                .append("\n").append("0 setgray");

        // mark 6 line
        if (isLaser){
            out.append("\n").append("0 1 0 setrgbcolor");
        }
        out.append("\n").append((shadowSide / 2.0) - 17).append(" ").append(-(shadowSide - 15)).append(" moveto")
                .append("\n").append("NormalFont10 setfont")
                .append("\n").append("(6) show")
                .append("\n").append((shadowSide - 20)).append(" ").append(-(shadowSide / 2.0 - 12)).append(" moveto")
                .append("\n").append("(6) show");
        if (isLaser){
            out.append("\n").append("0 setgray");
        }
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide - 24).append(" 0 lineto")
                .append("\n").append(shadowSide - 24).append(" ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 ").append(-(shadowSide - 24)).append(" lineto")
                .append("\n").append("0 0 lineto stroke")
                .append("\n").append("0 0 moveto")

                // mark 45 line
                .append("\n").append(shadowSide).append(" ").append(-shadowSide).append(" lineto stroke") // 45 line
                .append("\n").append(shadowSide - 31).append(" ").append(-(shadowSide - 15)).append(" moveto");
        if (isLaser){
            out.append("\n").append("0 1 0 setrgbcolor");
        }
        out.append("\n").append("NormalFont10 setfont")
                .append("\n").append("(12) show");
        if (isLaser){
            out.append("\n").append("0 setgray");
        }
        out.append("\n").append("% =============== End Right Shadow Square =================");

        return out.toString();
    }

    private String drawOutline() {
        StringBuilder out = new StringBuilder();

        if (isLaser){
            out.append("\n").append("1 0 0 setrgbcolor");
        }

        out.append("\n").append("% draw outlines")
                .append("\n").append("-36 36 translate")
                .append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append("0 -540 lineto")
                .append("\n").append("36 -540 lineto")
                .append("\n").append("36 -36 504 270 360 arc")
                .append("\n").append("540 0 lineto")
                .append("\n").append("0 0 lineto stroke");

        if (isLaser){
            out.append("\n").append("0 setgray");
        }

        return out.toString();
    }

    public String printQuadrant(Astrolabe myAstrolabe) {
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Quadrans Vetus Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments")

                .append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append(EPSToolKit.fillBackground())
                .append("\n").append("72 630 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append(EPSToolKit.setUpFonts())
                .append(EPSToolKit.setUpCircularText())
                .append("\n").append("");

        out.append("\n").append("gsave")
                .append(drawDegreeScale())
                .append("\n").append("grestore")
//                .append("\n").append("gsave")
//                .append(drawShadowSquare())
//                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawOutline())
                .append("\n").append("grestore");
//                .append("\n").append("gsave")


//                .append(drawCotangentScale())
//                .append("\n").append("grestore")
//                .append("\n").append("gsave")
//                .append(drawCalendarScale(myAstrolabe.getLocation().getLatitude()))
//                .append("\n").append("grestore")
//                .append("\n").append("gsave")
//                .append(drawSineScale())
//                .append("\n").append("grestore")
//                .append("\n").append("gsave")
//                .append(drawOutline(myAstrolabe))
//                .append("\n").append("grestore")
//                .append("\n").append("gsave")
//                .append(drawUnequalHours())
//                .append("\n").append("grestore");
        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }

}
