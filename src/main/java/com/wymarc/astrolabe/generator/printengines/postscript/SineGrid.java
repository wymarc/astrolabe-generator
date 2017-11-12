package com.wymarc.astrolabe.generator.printengines.postscript;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.math.AstroMath;

public class SineGrid {
    private Astrolabe myAstrolabe = new Astrolabe();

    /**
     * computes and draws the Sin/Cos scale on the first quarter (top left)
     *
     * @return  returns the ps code for drawing the SinCos Scale
     */
    public String buildSineGrid(){

        double myX;
        double myY;		// X/Y Coordinates
        int i;
        String out = "";

        //compute size of arc that contains the scale and draw it
        // note eventually this will be done by looking at what rings are drawn and figuring
        // the remaining radius
        double radius = myAstrolabe.getMaterRadius() - 67;

        //Print outline of scale

        out += "\n" + "%% ================ Draw Sin/Cos Scale =================";
        out += "\n" + "newpath";
        out += "\n" + "0 0 moveto";
        out += "\n" + "0 " + radius + " lineto";
        out += "\n" + "0 0 " + radius + " 90 180 arc";
        out += "\n" + "0 0 lineto stroke";

        // By default we show just the Sine Scale
        out += "\n" + "%% ================ Draw Sin Scale =================";
        if (myAstrolabe.getGridPerDegree()){
            // draw by degree
            for (int j = 1; j < 90; j++){
                double angleR = Math.toRadians(j);
                double x = Math.sin(angleR)*radius;
                double y = Math.cos(angleR)*radius;
                out += "\n" + "newpath";
                out += "\n" + "0 " + y + " moveto";
                out += "\n" + -x + " " + y + " lineto stroke";
            }
            // mark sine divisions
            double step = radius/60.0;
            int divisions = 60;
            if (myAstrolabe.getUse100()){
                step = radius/50.0;
                divisions = 50;
            }
            for (i = 0; i <= divisions; i++) {
                if (i > 0 && i % 5 == 0 ){
                    myY = step * i;
                    out += "\n" + "newpath";
                    out += "\n" + "0 " + myY + " moveto";
                    out += "\n" + "2 " + myY + " lineto stroke";
                }
            }
        }else {
            double step = radius/60.0;
            int divisions = 60;
            if (myAstrolabe.getUse100()){
                step = radius/50.0;
                divisions = 50;
            }

            for (i = 0; i <= divisions; i++) {
                myY = step * i;
                myX = Math.sqrt((radius * radius) - (myY * myY)); // from circle eq
                out += "\n" + "newpath";
                if (i > 0 && i % 5 == 0 ){
                    out += "\n" + "[2 2] 0 setdash"; //dash every 5th line
                }else{
                    out += "\n" + "[] 0 setdash";
                }
                out += "\n" + "0 " + myY + " moveto";
                out += "\n" + -myX + " " + myY + " lineto stroke";
            }
        }

        out += "\n" + "%% ================ Draw Cos Scale =================";
        if (myAstrolabe.getShowCosine()) {
            if (myAstrolabe.getGridPerDegree()) {
                // draw by degree
                for (int j = 1; j < 90; j++) {
                    double angleR = Math.toRadians(j);
                    double x = Math.sin(angleR) * radius;
                    double y = Math.cos(angleR) * radius;
                    out += "\n" + "newpath";
                    out += "\n" + -x + " 0 moveto";
                    out += "\n" + -x + " " + y + " lineto stroke";
                }
                // mark cosine divisions
                double step = radius/60.0;
                int divisions = 60;
                if (myAstrolabe.getUse100()){
                    step = radius/50.0;
                    divisions = 50;
                }
                for (i = 0; i <= divisions; i++) {
                    if (i > 0 && i % 5 == 0 ){
                        myX = step * i;
                        out += "\n" + "newpath";
                        out += "\n" + -myX + " 0 moveto";
                        out += "\n" + -myX + " -2 lineto stroke";
                    }
                }
            } else {
                double step = radius/60.0;
                int divisions = 60;
                if (myAstrolabe.getUse100()){
                    step = radius/50.0;
                    divisions = 50;
                }

                for (i = 0; i <= divisions; i++) {
                    myX = step * i;
                    myY = Math.sqrt((radius * radius) - (myX * myX)); // from circle eq
                    out += "\n" + "newpath";
                    if (i > 0 && i % 5 == 0 ){
                        out += "\n" + "[2 2] 0 setdash"; //dash every 5th line
                    }else{
                        out += "\n" + "[] 0 setdash";
                    }
                    out += "\n" + -myX + " 0 moveto";
                    out += "\n" + -myX + " " + myY + " lineto stroke";
                }
            }
        }
        out += "\n" + "[] 0 setdash";

        out += "\n" + "%% ================ Draw Radials =================";
        if (myAstrolabe.getShowRadials()) {
            for (int j = 1; j < 6; j++){
                double angleR = Math.toRadians(j * 15.0);
                double x = Math.sin(angleR)*radius;
                double y = Math.cos(angleR)*radius;
                out += "\n" + "newpath";
                out += "\n" + "0 0 moveto";
                out += "\n" + -x + " " + y + " lineto stroke";
            }
        }

        out += "\n" + "%% ================ Draw Arcs =================";
        if (myAstrolabe.getShowArcs()) {
            for (int j = 1; j < 9; j++){
                double angleR = Math.toRadians(j * 10.0);
                double r = Math.sin(angleR)*radius;
                out += "\n" + "newpath";
                out += "\n" + "0 0 " + r + " 90 180 arc stroke";
            }
        }

        out += "\n" + "%% ================ Draw Obliqity =================";
        if (myAstrolabe.getShowObliqityArc()) {
            double obl = AstroMath.obliquity(AstroMath.getT());
            double angleR = Math.toRadians(obl);
            double r = Math.sin(angleR)*radius;
            out += "\n" + "newpath";
            out += "\n" + "[3 3] 0 setdash";
            out += "\n" + "0 0 " + r + " 90 180 arc stroke";
            out += "\n" + "[] 0 setdash";
        }

        // todo: Need to add the scales to the axis
        out += "\n" + "%% ================ End Sin/Cos Scale =================";

        return out;
    }


    public SineGrid(Astrolabe myAstrolabe) {
        this.myAstrolabe = myAstrolabe;
    }
}
