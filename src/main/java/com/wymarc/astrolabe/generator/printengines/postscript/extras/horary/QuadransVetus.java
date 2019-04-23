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
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        out.append("\n").append("% draw inner outlines")
                .append("\n").append("newpath")
                .append("\n").append("36 -36 moveto")
                .append("\n").append("36 -535 lineto")
                .append("\n").append("36 -36 499 270 360 arc")
                .append("\n").append("36 -36 lineto stroke");

        if (isLaser){
            out.append("\n").append("0 setgray");
        }

        return out.toString();
    }


    private String drawDegreeScale() {
        StringBuilder out = new StringBuilder();

        if (isLaser){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        // draw arcs
        out.append("\n").append("% degree scale")
                .append("\n").append("0 0 489 270 360 arc stroke")
                .append("\n").append("0 0 460 270 360 arc stroke")
                .append("\n").append("0 0 455 270 360 arc stroke")
                .append("\n").append("");

        // create 1 degree marks
        for (int count = 0; count <= 89; count++) {
            if (count > 0){
                if (count % 5 == 0) { // longer mark at each 5 degrees
                    out.append("\n").append("460 0 moveto");
                }else{
                    out.append("\n").append("489 0 moveto");
                }
                out.append("\n").append("499 0 lineto stroke");
            }
            out.append("\n").append("-1 rotate");
        }
        out.append("\n").append("89 rotate");

        if (isLaser){
            out.append("\n").append("0 setgray");
        }

        //Mark degrees
        if (isLaser){
            out.append("\n").append("ArialFont16 setfont");
        }else{
            out.append("\n").append("NormalFont16 setfont");
        }

        for (int count = 1; count <= 18; count++) {
            out.append(EPSToolKit.drawInsideCircularText((count * 5) + "", 16, (-89 + (count * 5)) - 2.5, 480));
        }

        return out.toString();
    }

    private String drawCalendarScale(double latitude) {
        StringBuilder out = new StringBuilder();
        if (isLaser){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

        // draw arcs
        out.append("\n").append("% Space for calendar")
                .append("\n").append("0 0 343 270 360 arc stroke")
                .append("\n").append("0 0 338 270 360 arc stroke")
                .append("\n").append("");

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
        double shadowRadius = 338.0;
        double shadowSide = Math.sqrt((shadowRadius * shadowRadius) / 2.0); //from pythagoras
        double div = 12.0;
        StringBuilder out = new StringBuilder();

        if (isLaser){
            out.append("\n").append("0 0 1 setrgbcolor");
        }

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
            double angle = (45.0/12.0) * count;
            // determine long and short mark intersections with sides
            double outside = Math.tan(Math.toRadians(angle)) * shadowSide;
            double middle = Math.tan(Math.toRadians(angle)) * (shadowSide - 10);
            double inside = Math.tan(Math.toRadians(angle)) * (shadowSide - 24);

            if (count == 4 || count == 8){
                out.append("\n").append("newpath")
                        .append("\n").append((shadowSide - 24) + " " + (-inside) + " moveto")
                        .append("\n").append(shadowSide + " " + (-outside) + " lineto stroke")
                        .append("\n").append(inside + " " + (-(shadowSide - 24)) + " moveto")
                        .append("\n").append(outside + " " + (-shadowSide) + " lineto stroke");
            }else{
                out.append("\n").append("newpath")
                        .append("\n").append((shadowSide - 10) + " " + (-middle) + " moveto")
                        .append("\n").append(shadowSide + " " + (-outside) + " lineto stroke")
                        .append("\n").append(middle + " " + (-(shadowSide - 10)) + " moveto")
                        .append("\n").append(outside + " " + (-shadowSide) + " lineto stroke");
            }
        }

        // Draw 45 line
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(shadowSide + " " + (-shadowSide) + " lineto stroke");

        if (isLaser){
            out.append("\n").append("0 setgray");
        }

        // label
        //determine locations of numbers on middle line
        double four = Math.tan(Math.toRadians(7.5)) * (shadowSide - 10);
        double eight = Math.tan(Math.toRadians(22.5)) * (shadowSide - 10);
        double twelve = Math.tan(Math.toRadians(37.5)) * (shadowSide - 10);

        //Mark degrees
        if (isLaser){
            out.append("\n").append("ArialFont16 setfont");
        }else{
            out.append("\n").append("NormalFont16 setfont");
        }

        out.append("\n").append("newpath")
                //.append("\n").append("-10 0 moveto")
                .append("\n").append((shadowSide - 10) + " " + four + " moveto")
                .append("\n").append(EPSToolKit.centerText("4"));


        out.append("\n").append("% =============== End Shadow Square =================");

        return out.toString();
    }

    private String drawUnequalHours() {
        StringBuilder out = new StringBuilder();
        // mark unequal hour lines
        // draw arcs
        out.append("\n").append("% unequal hours")
                .append("\n").append("newpath")
                .append("\n").append("382 0 moveto")
                .append("\n").append("0 0 lineto")
                .append("\n").append("0 -382 lineto")
                .append("\n").append("0 0 403 270 360 arc clip");
        double Ri;
        for (int count = 1; count <= 6; count++) {
            Ri = (403 / (2 * (Math.sin(Math.toRadians(15 * count)))));
            out.append("\n").append(Ri).append(" 0 ").append(Ri).append(" 180 360 arc stroke");
        }

        //Mark unequal hours
        out.append("\n").append("NormalFont8 setfont");
        for (int count = 1; count <= 5; count++) {
            out.append(EPSToolKit.drawInsideCircularText(count + "", 5, (-90 + (count * 15) + 0.5), 398));
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
                .append("\n").append("gsave")
                .append(drawOutline())
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawCalendarScale(myAstrolabe.getLocation().getLatitude()))
                .append("\n").append("grestore")
                .append("\n").append("gsave")
                .append(drawShadowSquare())
                .append("\n").append("grestore");
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
