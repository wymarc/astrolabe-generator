package com.wymarc.astrolabe.generator.printengines.postscript.extras.sine;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

/**
 * This Plugin will calculate the components of the a Sine Quadrant with a zodiac scale and
 * print the results to an Encapsulated PostScript (EPS) file. Note that
 * This file is static with no inputs. Software is included for reference.
 * <p>
 * link      http://astrolabeproject.com.com
 * link      http://www.astrolabes.org
 */
public class ZodiacSineQuadrant {
    /**
     * Draws a cutting line
     * @param type 0: Plain, 1: Space for sights, 2: Notch Sight
     * @return eps string
     */
    private String drawCuttingLines(int type) {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw cutting lines =================");
        if (type == 0){
            out.append("\n").append("newpath")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append("0 -562 lineto")
                    .append("\n").append("36 -562 lineto")
                    .append("\n").append("36 -36 526 270 360 arc")
                    .append("\n").append("562 0 lineto")
                    .append("\n").append("0 0 lineto stroke");
        }else if(type == 1){
            out.append("\n").append("newpath")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append("0 -562 lineto")
                    .append("\n").append("36 -562 lineto")
                    .append("\n").append("36 -36 526 270 360 arc")
                    .append("\n").append("562 36 lineto")
                    .append("\n").append("0 36 lineto")
                    .append("\n").append("0 0 lineto stroke");
        }else if (type == 2){
            double interval5 = 414.0 / 12.0;
            out.append("\n").append("newpath")
                    .append("\n").append("0 0 moveto")
                    .append("\n").append("0 -562 lineto")
                    .append("\n").append("36 -562 lineto")
                    .append("\n").append("36 -36 526 270 360 arc")
                    .append("\n").append("562 36 lineto")
                    .append("\n").append(interval5 * 10).append(" 36 lineto")
                    .append("\n").append(interval5 * 10).append(" 18 lineto")
                    .append("\n").append(interval5 * 4).append(" 18 lineto")
                    .append("\n").append(interval5 * 4).append(" 36 lineto")
                    .append("\n").append("0 36 lineto")
                    .append("\n").append("0 0 lineto stroke");
        }

        out.append("\n").append("%% ================ End Draw cutting lines =================");

        return out.toString();
    }

    private String drawOutlines() {
        //459 limb - 36 = 423 (540)
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw Outlines =================")
                .append("\n").append("newpath")
                .append("\n").append("10 -10 moveto")
                .append("\n").append("552 -10 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -13 moveto")
                .append("\n").append("549 -13 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -33 moveto")
                .append("\n").append("549 -33 lineto")
                .append("\n").append("549 -13 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -36 moveto")
                .append("\n").append("552 -36 lineto")
                .append("\n").append("552 -10 lineto stroke");

        out.append("\n").append("newpath")
                .append("\n").append("10 -10 moveto")
                .append("\n").append("10 -552 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("13 -13 moveto")
                .append("\n").append("13 -549  lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("33 -13 moveto")
                .append("\n").append("33 -549 lineto")
                .append("\n").append("13 -549 lineto stroke")
                .append("\n").append("newpath")
                .append("\n").append("36 -13 moveto")
                .append("\n").append("36 -552 lineto")
                .append("\n").append("10 -552 lineto stroke");

        out.append("\n").append("newpath")
                .append("\n").append("36 -36 516 270 360 arc stroke")//494
                .append("\n").append("36 -36 513 270 360 arc stroke")//494
                .append("\n").append("newpath")
                .append("\n").append("36 -36 456 270 360 arc stroke")//494
                .append("\n").append("newpath")
                .append("\n").append("36 -36 453 270 360 arc stroke")//491
                .append("\n").append("newpath")
                .append("\n").append("36 -36 429 270 360 arc stroke")//467
                .append("\n").append("newpath")
                .append("\n").append("36 -36 426 270 360 arc stroke")//464
                .append("\n").append("newpath")
                .append("\n").append("36 -36 420 270 360 arc stroke")//458
                .append("\n").append("newpath")
                .append("\n").append("36 -36 414 270 360 arc stroke")//452
                .append("\n").append("%% ================ End Draw Outlines =================");

        return out.toString();
    }

    private String drawZodiacScale(){
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ start Draw zodiac scale =================");
        out.append("\n").append("-30 rotate")
                .append("\n").append("newpath")
                .append("\n").append("456 0 moveto")
                .append("\n").append("513 0 lineto stroke")
                .append("\n").append("-30 rotate")
                .append("\n").append("newpath")
                .append("\n").append("456 0 moveto")
                .append("\n").append("513 0 lineto stroke")
                .append("\n").append("60 rotate");

        out.append("\n").append("%% ================ start Draw arrow lines =================");
        out.append("\n").append("newpath")
                .append("\n").append("0 0 467 277 299 arc stroke")
                .append("\n").append("0 0 467 307 329 arc stroke")
                .append("\n").append("0 0 467 337 359 arc stroke")

                .append("\n").append("0 0 478 271 293 arc stroke")
                .append("\n").append("0 0 478 301 323 arc stroke")
                .append("\n").append("0 0 478 331 353 arc stroke")

                .append("\n").append("0 0 489 277 299 arc stroke")
                .append("\n").append("0 0 489 307 329 arc stroke")
                .append("\n").append("0 0 489 337 359 arc stroke")

                .append("\n").append("0 0 500 271 293 arc stroke")
                .append("\n").append("0 0 500 301 323 arc stroke")
                .append("\n").append("0 0 500 331 353 arc stroke");
        out.append("\n").append("%% ================ stop Draw arrow lines =================");
        out.append("\n").append("%% ================ start Draw arrow heads =================");
        out.append("\n").append("0 setgray");
        out.append("\n").append("0 -467 29 rightarrowhead")
                .append("\n").append("0 -467 7 rightarrowhead")
                .append("\n").append("0 -467 7.25 rightarrowhead")
                .append("\n").append("0 -467 7.5 rightarrowhead")
                .append("\n").append("0 -467 59 rightarrowhead")
                .append("\n").append("0 -467 37 rightarrowhead")
                .append("\n").append("0 -467 37.25 rightarrowhead")
                .append("\n").append("0 -467 37.5 rightarrowhead")
                .append("\n").append("0 -467 89 rightarrowhead")
                .append("\n").append("0 -467 67 rightarrowhead")
                .append("\n").append("0 -467 67.25 rightarrowhead")
                .append("\n").append("0 -467 67.5 rightarrowhead");
        out.append("\n").append("0 -467 29 rightarrowhead")
                .append("\n").append("0 -489 7 rightarrowhead")
                .append("\n").append("0 -489 7.25 rightarrowhead")
                .append("\n").append("0 -489 7.5 rightarrowhead")
                .append("\n").append("0 -489 59 rightarrowhead")
                .append("\n").append("0 -489 37 rightarrowhead")
                .append("\n").append("0 -489 37.25 rightarrowhead")
                .append("\n").append("0 -489 37.5 rightarrowhead")
                .append("\n").append("0 -489 89 rightarrowhead")
                .append("\n").append("0 -489 67 rightarrowhead")
                .append("\n").append("0 -489 67.25 rightarrowhead")
                .append("\n").append("0 -489 67.5 rightarrowhead");
        out.append("\n").append("0 -478 1 leftarrowhead")
                .append("\n").append("0 -478 23 leftarrowhead")
                .append("\n").append("0 -478 22.75 leftarrowhead")
                .append("\n").append("0 -478 22.5 leftarrowhead")
                .append("\n").append("0 -478 31 leftarrowhead")
                .append("\n").append("0 -478 53 leftarrowhead")
                .append("\n").append("0 -478 52.75 leftarrowhead")
                .append("\n").append("0 -478 52.5 leftarrowhead")
                .append("\n").append("0 -478 61 leftarrowhead")
                .append("\n").append("0 -478 83 leftarrowhead")
                .append("\n").append("0 -478 82.75 leftarrowhead")
                .append("\n").append("0 -478 82.5 leftarrowhead");
        out.append("\n").append("0 -500 1 leftarrowhead")
                .append("\n").append("0 -500 23 leftarrowhead")
                .append("\n").append("0 -500 22.75 leftarrowhead")
                .append("\n").append("0 -500 22.5 leftarrowhead")
                .append("\n").append("0 -500 31 leftarrowhead")
                .append("\n").append("0 -500 53 leftarrowhead")
                .append("\n").append("0 -500 52.75 leftarrowhead")
                .append("\n").append("0 -500 52.5 leftarrowhead")
                .append("\n").append("0 -500 61 leftarrowhead")
                .append("\n").append("0 -500 83 leftarrowhead")
                .append("\n").append("0 -500 82.75 leftarrowhead")
                .append("\n").append("0 -500 82.5 leftarrowhead");
        out.append("\n").append("%% ================ stop Draw arrow heads =================");
        out.append("\n").append("%% ================ label zodiac =================");
        out.append("\n").append("ArialFont10 setfont");
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[0], 10, 273.5, 470));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[1], 10, 303.5, 470));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[2], 10, 333.5, 470));

        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[3], 10, 356.5, 481));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[4], 10, 326.5, 481));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[5], 10, 296.5, 481));

        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[6], 10, 273.5, 492));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[7], 10, 303.5, 492));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[8], 10, 333.5, 492));

        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[9], 10, 356.5, 503));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[10], 10, 326.5, 503));
        out.append("\n").append(EPSToolKit.drawInsideCircularText(Astrolabe.ZODIAC[11], 10, 296.5, 503));


        out.append("\n").append("%% ================ end label zodiac =================");

        out.append("\n").append("0 0 1 setrgbcolor");
        return out.toString();
    }

    private String markScales() {
        StringBuilder out = new StringBuilder();
        //504 radius  387 474
        out.append("\n").append("%% ================ Mark Scales =================")
                .append("\n").append("%% ================ Draw Degree Scale =================");
        // create 1/2 degree marks
        for (int count = 1; count < 180; count++) {
            out.append("\n").append("-0.5 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("420 0 moveto")
                    .append("\n").append("414 0 lineto stroke");
        }
        out.append("\n").append("89.5 rotate");

        // create 1 degree marks
        for (int count = 1; count < 90; count++) {
            out.append("\n").append("-1 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("426 0 moveto")
                    .append("\n").append("414 0 lineto stroke");
        }
        out.append("\n").append("89 rotate");

        // create 5 degree marks
        for (int count = 1; count < 18; count++) {
            out.append("\n").append("-5 rotate")
                    .append("\n").append("newpath")
                    .append("\n").append("429 0 moveto")
                    .append("\n").append("453 0 lineto stroke");
        }
        out.append("\n").append("85 rotate");

        out.append("\n").append("%% ================ End Draw Degree Scale =================")
                .append("\n").append("%% ================ Draw Sine/cosine Scales =================");
        double interval5 = 414.0 / 12.0;
        for (int i = 1; i <= 12; i++) {
            out.append("\n").append("newpath")
                    .append("\n").append(interval5 * i).append(" 3 moveto")
                    .append("\n").append(interval5 * i).append(" 23 lineto stroke");

            out.append("\n").append("newpath")
                    .append("\n").append("-3 ").append(-interval5 * i).append(" moveto")
                    .append("\n").append("-23 ").append(-interval5 * i).append(" lineto stroke");
        }

        out.append("\n").append("%% ================ Draw Sine/cosine Scales =================");


        out.append("\n").append("%% ================ End Mark Scales =================");
        return out.toString();
    }


    private String labelScales() {
        StringBuilder out = new StringBuilder();
        //504 radius  387
        out.append("\n").append("%% ================ label Degree Scale =================")
                .append("\n").append("NormalFont20 setfont");
        //Mark degrees
        for (int count = 5; count <= 85; count = count + 5) {
            String text = Integer.toString(count);
            if (count == 5) {
                text = "  " + text;
            }
            out.append(EPSToolKit.drawInsideCircularText(text, 20, (-90 + count), 447));
        }
        out.append("\n").append("%% ================ End label Degree Scale =================");

        out.append("\n").append("%% ================ label Sine Scale =================");
        double interval1 = 414.0 / 60.0;
        double interval5 = 414.0 / 12.0;
        out.append("\n").append("NormalFont12 setfont");
        for (int i = 1; i <= 12; i++) {
            if (i > 1) {
                out.append("\n").append(interval5 * i - 14).append(" 5 moveto");
            } else {
                out.append("\n").append(interval5 * i - 8).append(" 5 moveto");
            }
            out.append("\n").append("(").append(i * 5).append(") show");

            if (i > 1) {
                out.append("\n").append("-17 ").append(-interval5 * i + 2).append(" moveto");
            } else {
                out.append("\n").append("-11 ").append(-interval5 * i + 2).append(" moveto");
            }
            out.append("\n").append("(").append(i * 5).append(") show");
        }

        return out.toString();
    }

    private String drawGrid() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw grid =================");
        double interval1 = 414.0 / 60.0;
        double radiusSquared = 414.0 * 414.0;
        double length;

        // draw unit lines
        for (int i = 1; i < 60; i++) {
            double xSide = interval1 * i;
            double xSquared = (xSide) * (xSide);
            length = Math.sqrt(radiusSquared - xSquared);

            if ((i==5)||(i==10)||(i==15)||(i==20)||(i==25)||(i==30)||(i==35)||(i==40)||(i==45)||(i==50)||(i==55)){
                out.append("\n" + "0 setgray")
                .append("\n" + ".8 setlinewidth");
            }else{
                out.append("\n" + ".4 setlinewidth")
                .append("\n" + ".5 setgray");
            }

            out.append("\n").append("newpath")
                    .append("\n").append(xSide).append(" 0 moveto")
                    .append("\n").append(xSide).append(" ").append(-length).append(" lineto stroke")
                    .append("\n").append("newpath")
                    .append("\n").append("0 ").append(-xSide).append(" moveto")
                    .append("\n").append(length).append(" ").append(-xSide).append(" lineto stroke");
        }

        return out.toString();
    }

    private String drawAdvancedLines() {
        StringBuilder out = new StringBuilder();
        out.append("\n").append("%% ================ Draw Advanced Lines =================")
                .append("\n").append("%% ================ Draw Intersections =================");

        out.append("\n").append("%% ================ Draw 45 Line =================");
        double length = Math.sqrt((414.0 * 414.0) / 2);
        out.append("\n").append("newpath")
                .append("\n").append("0 0 moveto")
                .append("\n").append(length).append(" ").append(-length).append(" lineto stroke")
                .append("\n").append("%% ================ Draw 45 Line =================");

        out.append("\n").append("%% ================ Draw Tangent Line =================");
        // draw mystery line
        out.append("\n").append("newpath")
                .append("\n").append("414 0 moveto")
                .append("\n").append("0 -414 lineto stroke")
                .append("\n").append("%% ================ End Draw Tangent Line =================");

        out.append("\n").append("%% ================ Draw sine/cosine arcs =================")
                .append("\n").append("newpath")
                .append("\n").append("207 0 207 180 360 arc stroke")
                .append("\n").append("newpath")
                .append("\n").append("0 -207 207 270 90 arc stroke")
                .append("\n").append("%% ================ End Draw sine/cosine arcs =================");

        out.append("\n").append("%% ================ Draw Asr start Arc  =================");
        double interval = 414.0 / 60.0;
        Point2D[] asrLine = AstroMath.defineAsrLine(interval, 1.0);
        out.append("\n").append("newpath")
                .append("\n").append(asrLine[0].getY()).append(" ").append(-asrLine[0].getX()).append(" moveto");     //todo fix this so it isn't reversed yx
        for (int i = 1; i < 90; i++) {
            out.append("\n").append(asrLine[i].getY()).append(" ").append(-asrLine[i].getX()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getY()).append(" ").append(-asrLine[90].getX()).append(" lineto stroke")
                .append("\n").append("%% ================ End Draw Asr start Arc  =================");

        out.append("\n").append("%% ================ Draw Asr End arc =================");
        asrLine = AstroMath.defineAsrLine(interval, 2.0);
        out.append("\n").append("newpath")
                .append("\n").append(asrLine[0].getY()).append(" ").append(-asrLine[0].getX()).append(" moveto");     //todo fix this so it isn't reversed yx
        for (int i = 1; i < 90; i++) {
            out.append("\n").append(asrLine[i].getY()).append(" ").append(-asrLine[i].getX()).append(" lineto");
        }
        out.append("\n").append(asrLine[90].getY()).append(" ").append(-asrLine[90].getX()).append(" lineto stroke")
                .append("\n").append("%% ================ End Draw Asr End arc =================");

        out.append("\n").append("%% ================ Draw obliquity arc =================");
        // draw obliquity arc
        // radius is the Sine of the obliquity angle times the radius of the scale
        double obl = AstroMath.obliquity(AstroMath.getT());
        double oblRadius = Math.sin(Math.toRadians(obl)) * 414.0;
        out.append("\n").append("newpath")
                .append("\n").append("0 0 ").append(oblRadius).append(" 270 0 arc stroke")
                .append("\n").append("%% ================ End Draw obliquity arc =================");

        return out.toString();
    }

    /**
     * The original is 6 3/8" across one limb, this translates to 459 points
     *
     * @return Sting containing the EPS code
     */

    public String printQuadrant() {
        StringBuilder out = new StringBuilder();

        // Write header to file
        out.append("%!PS-Adobe-3.0 EPSF-30.")
                .append("\n").append("%%BoundingBox: 0 0 612 792")
                .append("\n").append("%%Title: Vernier Sine Quadrant")
                .append("\n").append("%%Creator: Richard Wymarc")
                .append("\n").append("%%CreationDate: ")
                .append("\n").append("%%EndComments");

        out.append("\n").append("mark")
                .append("\n").append("/Quadrant 10 dict def %local variable dictionary")
                .append("\n").append("Quadrant begin")
                .append("\n").append("")
                .append("\n").append("%% setup")
                .append("\n").append("36 626 translate")
                .append("\n").append(".4 setlinewidth")
                .append("\n").append("")
                .append(EPSToolKit.setUpFonts())
                .append(EPSToolKit.setUpSymbols())
                .append(EPSToolKit.setUpCircularText());

        out.append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(drawOutlines())
                .append("\n").append("grestore");

        out.append("\n").append("36 -36 translate");

        out.append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(markScales())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(drawZodiacScale())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(labelScales())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(drawGrid())
                .append("\n").append("grestore");

        out.append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(drawAdvancedLines())
                .append("\n").append("grestore");

        out.append("\n").append("-36 36 translate")
                .append("\n").append("gsave")
                .append("\n").append("0 setgray")
                .append(drawCuttingLines(2))
                .append("\n").append("grestore");

        out.append("\n").append("grestore");
        // Write Footer
        out.append("\n").append("% Eject the page")
                .append("\n").append("end cleartomark")
                .append("\n").append("showpage");

        return out.toString();

    }

}