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
import com.wymarc.astrolabe.math.InterSect;
import com.wymarc.astrolabe.math.ThreePointCenter;

import java.awt.geom.Point2D;

public class FrontPrintEngine {

    private Astrolabe myAstrolabe = new Astrolabe();

    /**
     * computes the circles for the tropics and equator
     * given the diameter of the plate
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the tropics
     *
     */
    private String computeTropics(Astrolabe myAstrolabe){
        // draws the circles for the tropics and equator
        String out = "";
        out += "\n" + "%% ================ Draw Tropics =================";
        out += "\n" + "0 0 " + myAstrolabe.getCapricornRadius() + " 0 360 arc stroke";
        out += "\n" + "0 0 " + myAstrolabe.getEquatorRadius() + " 0 360 arc stroke";
        out += "\n" + "0 0 " + myAstrolabe.getCancerRadius() + " 0 360 arc stroke";
        out += "\n" + "%% ================ End Tropics =================";

        return out;
    }

    /**
     * places the latitude label on the plate
     *
     * @param myAstrolabe The astrolabe object
     *                    
     * @return  returns the ps code for drawing the plate label
     *
     */
    private String labelPlate(Astrolabe myAstrolabe){
        int latDegrees = myAstrolabe.getLocation().getLatDeg();

        int latMinutes = myAstrolabe.getLocation().getLatMin();
        String degreeString = latDegrees + "\\260 "; //260 is degree symbol
        String minuteString;
        if(latMinutes < 10)
        {
            minuteString = "0" + latMinutes + "'";
        }else
        {
            minuteString = latMinutes + "'";
        }

        String label = degreeString + minuteString;
        if (myAstrolabe.getLocation().getLatDir().equals("S")){
            label = " " + label + "S";
        }

        Double y = (-((myAstrolabe.getCapricornRadius()+ myAstrolabe.getEquatorRadius()) / 2.0));

        String out = "";
        out += "\n" + "%% ================ Draw Front Labels =================";
        out += "\n" + "%% Label latitude of plate";
        out += "\n" + "NormalFont12 setfont";
        out += "\n 0 " + y + " moveto";
        out += EPSToolKit.centerText(label);
        out += "\n" + "";
        out += "\n" + "%% ================ End Front Labels =================";

        return out;
    }

    /**
     * Computes the almucantars
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the almucantars
     *
     */
    private String computeAlmucantars(Astrolabe myAstrolabe){
        int interval = myAstrolabe.getAlmucanterInterval();
        int altitude = 0;
        double radius;
        double center;
        String label = "";

        String out = "";
        if(myAstrolabe.getLocation().getLatDeg()==0){
            altitude = interval; // fixes issue with equator
        }
        out += "\n" + "%% ================ Draw Almucantars =================";
        StringBuilder sb = new StringBuilder();
        while (altitude <= 85)  //stop at 85 deg
        {
            sb.append("\n").append("newpath");
            //Compute center
            center = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) / (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(altitude))));
            //compute radius
            radius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(altitude)) / (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(altitude))));

            if(((interval == 1)||(interval == 2))&&((altitude == 10)||(altitude == 30)||(altitude == 50)||(altitude == 70)))
            {
                // if interval is every 1 or 2 degrees mark every tenth with heavier lines
                sb.append("\n").append(".4 setlinewidth");
            }else if((altitude == 0)||(altitude == 20)||(altitude == 40)||(altitude == 60)||(altitude == 80))
            {
                // if interval is every 5 or 10 mark every 20 deg with heavier lines
                sb.append("\n").append(".4 setlinewidth");
            }else
            {
                sb.append("\n").append(".1 setlinewidth");
            }
            sb.append("\n").append("0 ").append(center).append(" ").append(radius).append(" 0 360 arc stroke");

            if((altitude == 20)||(altitude == 40)||(altitude == 60))
            {
                if(altitude == 20)
                {
                    if (myAstrolabe.getLocation().getLatitude() < 0){
                        label = "-2 0 ";
                    }else{
                        label = "2 0";
                    }
                }
                if(altitude == 40)
                {
                    if (myAstrolabe.getLocation().getLatitude() < 0){
                        label = "-4 0 ";
                    }else{
                        label = "4 0";
                    }
                }
                if(altitude == 60)
                {
                    if (myAstrolabe.getLocation().getLatitude() < 0){
                        label = "-6 0 ";
                    }else{
                        label = "6 0";
                    }
                }
                //mark altitude
                sb.append("\n").append("%% Label Almucantar");
                sb.append("\n").append("NormalFont6 setfont");
                sb.append("\n").append(0).append(" ").append(((center + radius)+1)).append(" moveto");
                sb.append(EPSToolKit.centerText(label));
                sb.append("\n").append(0).append(" ").append(((center - radius)+1)).append(" moveto");
                sb.append(EPSToolKit.centerText(label));

            }
            altitude = altitude + interval;
        }

        out += "\n" + sb.toString();
        // Twilight line
        if (myAstrolabe.getShowTwilightLines()){//todo: consolidate this to simplify
            out += "\n" + "[4 4] 0 setdash"; // set dashed line

            if (myAstrolabe.getShowAllTwilightLines()){
                int count = 1;
                sb = new StringBuilder();
                while (count <= 3)
                {
                    altitude = -6 * count;
                    //Compute center
                    center = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(myAstrolabe.getLocation().getLatitude())) / (Math.sin(Math.toRadians(myAstrolabe.getLocation().getLatitude())) + Math.sin(Math.toRadians(altitude))));
                    //compute radius
                    radius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(altitude)) / (Math.sin(Math.toRadians(myAstrolabe.getLocation().getLatitude())) + Math.sin(Math.toRadians(altitude))));
                    //compute clipping
                    InterSect myInterSect = new InterSect(0, center, radius, 0, 0, myAstrolabe.getCancerRadius());
                    if (myInterSect.getIntersection())
                    {
                        sb.append("\n").append("0 ").append(center).append(" ").append(radius).append(" ")
                                .append(myInterSect.getAngle2()).append(" ").append(myInterSect.getAngle1()).append(" arc stroke");
                    }else
                    {
                        sb.append("\n").append("0 ").append(center).append(" ").append(radius).append(" 0 360 arc stroke");
                    }

                    count++;
                }
                out += "\n" + sb.toString();
            }else{
                // compute and draw just the 18 degree twilight line
                altitude = -18;
                //Compute center
                center = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) / (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(altitude))));
                //compute radius
                radius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(altitude)) / (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(altitude))));
                //compute clipping
                InterSect myInterSect = new InterSect(0, center, radius, 0, 0, myAstrolabe.getCancerRadius());
                if (myInterSect.getIntersection())
                {
                    out += "\n" + "0 " + center + " " + radius + " "+myInterSect.getAngle2()+" "+myInterSect.getAngle1()+" arc stroke";
                }else
                {
                    out += "\n" + "0 " + center + " " + radius + " 0 360 arc stroke";
                }
            }

            out += "\n" + "[] 0 setdash"; // set solid line
        }
        out += "\n" + "%% ================ End Almucantars =================";

        return out;
    }

    /**
     * Computes the horizon plate
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the Hozizon plate
     *
     */
    private String computeHorizonPlate(Astrolabe myAstrolabe){
        int latitude = 10;
        int interval = 5;
        double radius;
        double center;
        String label = "";

        String out = "";
        out += "\n" + "%% ================ Draw Horizons =================";
        StringBuilder sb = new StringBuilder();
        while (latitude <= 70) //10 to 70
        {
            sb.append("\n").append("newpath");
            //Compute center
            center = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(latitude)) / (Math.sin(Math.toRadians(latitude)) + Math.sin(Math.toRadians(0))));
            //compute radius
            radius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(0)) / (Math.sin(Math.toRadians(latitude)) + Math.sin(Math.toRadians(0))));
            //draw
            sb.append("\n").append("0 ").append(center).append(" ").append(radius).append(" 0 360 arc stroke");
            if((latitude == 20)||(latitude == 40)||(latitude == 60))
            {
                if(latitude == 20)
                {
                    label = "2 0";
                }
                if(latitude == 40)
                {
                    label = "4 0";
                }
                if(latitude == 60)
                {
                    label = "6 0";
                }
                //mark altitude
                sb.append("\n").append("%% Label Almucantar");
                sb.append("\n").append("NormalFont6 setfont");
                sb.append("\n").append(0).append(" ").append(((center - radius)-2)).append(" moveto");
                sb.append("\n").append(EPSToolKit.centerText(label));

            }
            latitude = latitude + interval;
        }

        out += "\n" + sb.toString();

        out += "\n" + "%% ================ End Horizons =================";

        return out;
    }

    /**
     * Computes the unequal hour lines
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the Unequal hour lines
     *
     */
    private String computeUnequalHours(Astrolabe myAstrolabe){
        String out = "";

        out += "\n" + "%% ================ Draw Unequal Hours =================";

        if (Math.abs(myAstrolabe.getLocation().getLatitude()) > 60.0){
            // unequal hours do not draw properly above about 60 degrees
            return "";
        }else if (Math.abs(myAstrolabe.getLocation().getLatitude()) < 0.5){
            // at the equator the unequal hour lines are not arcs, but radial lines
            // Below a half degree latitude they don't draw properly, so substitute radial lines
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < 12; i++){
                sb.append("\n").append("-15 rotate");
                sb.append("\n").append(myAstrolabe.getCapricornRadius()).append(" 0 moveto");
                sb.append("\n").append(myAstrolabe.getCancerRadius()).append(" 0 lineto stroke");
            }
            out += "\n" + sb.toString();
            out += "\n" + "165 rotate";
        }else{
            double temp = (Math.tan(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))))*(Math.tan(Math.toRadians(23.44)));
            if (temp > 1)
            {
                temp = temp -1;
            }
            double cI = Math.toDegrees(Math.acos(temp))/6.0;
            double radius;
            double x;
            double centerX;
            double centerY;
            StringBuilder sb = new StringBuilder();
            for(int i = 1; i<=6; i++)
            {
                x =((Math.pow(myAstrolabe.getEquatorRadius(),2))-(Math.pow(myAstrolabe.getCancerRadius(),2)))/(2*myAstrolabe.getCancerRadius()*Math.sin(Math.toRadians((15*i)-(cI*i))));
                radius = Math.sqrt(Math.pow(myAstrolabe.getEquatorRadius(),2)+Math.pow(x,2));
                centerX = x*(Math.cos(Math.toRadians(15.0*i)));
                centerY = x*(Math.sin(Math.toRadians(15.0*i)));
                //compute clipping
                InterSect myInterSect = new InterSect(centerX, centerY, radius, 0, 0, myAstrolabe.getCancerRadius());
                InterSect myInterSect2 = new InterSect(-centerX, centerY, radius, 0, 0, myAstrolabe.getCancerRadius());
                sb.append("\n").append(centerX).append(" ").append(centerY).append(" ")
                        .append(radius).append(" ").append(myInterSect.getAngle2()).append(" 360 arc stroke");
                sb.append("\n").append(-centerX).append(" ").append(centerY).append(" ")
                        .append(radius).append(" ").append("90").append(" ").append(myInterSect2.getAngle1()).append(" arc stroke");
            }
            out += "\n" + sb.toString();
        }
        out += "\n" + "%% ================ End Unequal Hours =================";
        return out;
    }


    /**
     * Computes the azimuth lines
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the azimuth lines
     *
     */
    private String computeAzimuthLines(Astrolabe myAstrolabe){
        String out = "";       // todo problem with azimuth intervals less than 5 at less the 1 degree

        //Compute Azimuths
        out += "\n" + "%% ================ Draw Azimuth Lines =================";
        if (Math.abs(myAstrolabe.getLocation().getLatitude()) == 90.0){
            // at 90 degrees latitude the azimuths are not circles but radial lines
            // Horizon circle will be at the equator line
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 180; i = i + myAstrolabe.getAzimuthInterval()){
                sb.append("\n").append(-myAstrolabe.getEquatorRadius()).append(" 0 moveto");
                sb.append("\n").append(myAstrolabe.getEquatorRadius()).append(" 0 lineto stroke");
                sb.append("\n").append(myAstrolabe.getAzimuthInterval()).append(" rotate");
            }
            out += "\n" + sb.toString();
        }else{
            // draw circles for azimuths
            double azLine = myAstrolabe.getEquatorRadius() / Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude())));
            double centerY = -(azLine - (myAstrolabe.getEquatorRadius() * Math.tan(Math.toRadians((90 - Math.abs(myAstrolabe.getLocation().getLatitude())) / 2.0))));
            double centerX;
            double radius;

            //Compute center of Horizon Circle
            double hCenter = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) /
                (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(0))));
            //Compute radius of Horizon Circle
            double hRadius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(0)) /
                (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(0))));

            int azimuth;

            if (myAstrolabe.getAzimuthInterval() == 5)
            {
                azimuth = -85;
            }else
            {
                azimuth = -90;
            }

            StringBuilder sb = new StringBuilder();
            while (azimuth <= 89)
            {
                //Compute center
                centerX = azLine * Math.tan(Math.toRadians(azimuth));
                //compute radius
                radius = azLine / Math.cos(Math.toRadians(azimuth));

                // mark 0, 45 and 90 with heavier lines
                if((azimuth == 0)||(azimuth == 45)||(azimuth == -45)){
                    sb.append("\n").append(".4 setlinewidth");
                }else{
                    sb.append("\n").append(".1 setlinewidth");
                }

                if (Math.abs(myAstrolabe.getLocation().getLatitude()) == 0.0){
                    // there is no horizon circle at 0 degrees, so intersecting will not work
                    // use hardcoded 0-180 instead
                    sb.append("\n").append(centerX).append(" ").append(centerY).append(" ")
                            .append(radius).append(" ").append("0 ").append("180").append(" arc stroke");
                }else{
                    //compute intersection with horizon circle
                    InterSect myInterSect = new InterSect(centerX, centerY, radius, 0, hCenter, hRadius);
                    sb.append("\n").append(centerX).append(" ").append(centerY).append(" ")
                            .append(radius).append(" ").append(myInterSect.getAngle1()).append(" ").append(myInterSect.getAngle2()).append(" arc stroke");
                }
                azimuth = azimuth + myAstrolabe.getAzimuthInterval();
            }
            out += "\n" + sb.toString();
        }
        out += "\n" + "%% ================ End Azimuth Lines =================";
        return out;
    }


    /**
     * Overwrites the zenith with a 10 deg circle and places an X at zenith
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the zenith
     *
     */
    private String clearZenith(Astrolabe myAstrolabe){

        double zenithCenter = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) /
            (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(80))));
        double zenithRadius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(80)) /
            (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(80))));

        String out = "";

        //draw over zenith
        out += "\n" + "1 setgray";
        out += "\n" + "0 " + zenithCenter + " " + zenithRadius + " 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 " + zenithCenter + " " + zenithRadius + " 0 360 arc stroke";

        if (Math.abs(myAstrolabe.getLocation().getLatitude()) < 90.0){
            // at 90 degrees the zenith is the center of the plate, so no mark is necessary
            //reprint 0 azimuth line
            out += "\n" + ".4 setlinewidth";
            //Compute center of Horizon Circle
            double hCenter = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) /
                    (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(0))));
            //Compute radius of Horizon Circle
            double hRadius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(0)) /
                    (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) + Math.sin(Math.toRadians(0))));

            double azLine = myAstrolabe.getEquatorRadius() / Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude())));
            double centerY = -(azLine - (myAstrolabe.getEquatorRadius() * Math.tan(Math.toRadians((90 - Math.abs(myAstrolabe.getLocation().getLatitude())) / 2.0))));
            double centerX = azLine * Math.tan(Math.toRadians(0));
            double radius = azLine / Math.cos(Math.toRadians(0));

            if (Math.abs(myAstrolabe.getLocation().getLatitude()) == 0.0){
                // there is no horizon circle at 0 degrees, so intersecting will not work
                // use hardcoded 0-180 instead
                out += "\n" + centerX + " " + centerY + " " + radius + " " + "0" + " "
                        + "180" + " arc stroke";
            }else{
                //compute intersection with horizon circle
                InterSect myInterSect = new InterSect(centerX, centerY, radius, 0, hCenter, hRadius);
                out += "\n" + centerX + " " + centerY + " " + radius + " " + myInterSect.getAngle1() + " "
                        + myInterSect.getAngle2() + " arc stroke";
            }

            out += "\n" + ".1 setlinewidth";
        }
        return out;
    }

    /**
     * Draws the plate and its lines
     *
     * @return  returns the ps code for drawing the plate
     *
     */
    private String buildPlate(){
        String out = "";
        out += "\n" + "%% ==================== Build Plate ====================";
        if (myAstrolabe.getFrontPrintOption() == 1)
        {
            // if the plate alone is being drawn, draw line for edge (inner rim of limb)
            out += "\n" + "newpath";
            out += "\n" + "0 setgray";
            out += "\n" + "0 0 " + (myAstrolabe.getInnerLimbRadius()) + " 0 360 arc stroke";
            out += "\n" + "";
        }
        out += "\n" + "%% Set Clipping";
        out += "\n" + "0 0 " + myAstrolabe.getCapricornRadius() + " 0 360 arc clip";
        out += "\n" + "";
        out += "\n" + "%% Find center of page and mark it";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "";
        out += "\n" + "%% draw The Tropics";
        out += "\n" + "newpath";
        out += computeTropics(myAstrolabe);
        out += "\n" + "";
        if (myAstrolabe.getShowHorizonPlate()) // if we want the horizon plate
        {
            out += "\n" + "newpath";
            out += computeHorizonPlate(myAstrolabe);
        }else // otherwise... 
        {
            out += "\n" + "%% Label the Plate";
            out += "\n" + "newpath";
            out += labelPlate(myAstrolabe);
            out += "\n" + "";
            out += "\n" + "%% draw The Almucantars";
            out += "\n" + "newpath";
            out += computeAlmucantars(myAstrolabe);
            out += "\n" + "";
            if (myAstrolabe.getShowUnequalHoursLines())
            {
                out += "\n" + "%% draw The Unequal Hour Lines";
                out += "\n" + "newpath";
                out += computeUnequalHours(myAstrolabe);
                out += "\n" + "";
            }
            if (myAstrolabe.getShowAzimuthLines())
            {
                out += "\n" + "%% draw The azimuth Lines";
                out += "\n" + "newpath";
                out += computeAzimuthLines(myAstrolabe);
                out += "\n" + "";
            }
            if (myAstrolabe.getShowHousesofHeavenLines())
            {
                out += "\n" + "%% draw The azimuth Lines";
                out += "\n" + "newpath";
                out += computeHousesofHeaven(myAstrolabe);
                out += "\n" + "";
            }
            out += "\n" + "%% Clear zenith circle";
            out += clearZenith(myAstrolabe);
        }
        out += "\n" + "%% draw compass points";
        out += "\n" + ".4 setlinewidth";
        out += "\n" + "newpath";
        out += "\n" + -myAstrolabe.getCapricornRadius() + " 0 moveto";
        out += "\n" + myAstrolabe.getCapricornRadius() + " 0 lineto";
        out += "\n" + "0 " + -myAstrolabe.getCapricornRadius() + " moveto";
        out += "\n" + "0 " + myAstrolabe.getCapricornRadius() + " lineto";
        out += "\n" + "stroke";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "";
        out += "\n" + "%% ==================== End Build Plate ====================";
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

        if ((myAstrolabe.getDegreeScaleType() == 1)||(myAstrolabe.getDegreeScaleType() == 2))// If the scale is in degrees, not minutes
        {
            if ((myAstrolabe.getHourMarkings() == 2)||(myAstrolabe.getHourMarkings() == 3)) // Symbol markings
            {
                // show the front degree scale
                out += "\n" + "0 0 " + (myAstrolabe.getMaterRadius() - 4) + " 0 360 arc stroke";
                out += "\n" + "0 0 " + (myAstrolabe.getInnerLimbRadius() + 10) + " 0 360 arc stroke";
                rotationIncrement = 1.0;
                // create degree marks
                StringBuilder sb = new StringBuilder();
                for (count = 1; count <= 360; count++)
                {
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 5).append(" 0 moveto");
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 10).append(" 0 lineto stroke");
                    sb.append("\n").append(rotationIncrement).append(" rotate");
                }
                // create 5 degree marks
                for (count = 1; count <= 72; count++)
                {
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius()).append(" 0 moveto");
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 10).append(" 0 lineto stroke");
                    sb.append("\n").append(5*rotationIncrement).append(" rotate");
                }
                out += "\n" + sb.toString();
            }else // anything else
            {
                // show the front degree scale
                StringBuilder sb = new StringBuilder();
                out += "\n" + "0 0 " + (myAstrolabe.getInnerLimbRadius() + 20) + " 0 360 arc stroke";
                rotationIncrement = 1.0;
                // create degree marks
                for (count = 1; count <= 360; count++)
                {
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 15).append(" 0 moveto");
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 20).append(" 0 lineto stroke");
                    sb.append("\n").append(rotationIncrement).append(" rotate");
                }
                // create 10 degree marks
                for (count = 1; count <= 36; count++)
                {
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 20).append(" 0 moveto");
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius()).append(" 0 lineto stroke");
                    sb.append("\n").append(10*rotationIncrement).append(" rotate");
                }
                // create 5 degree marks
                sb.append("\n").append("5 rotate");
                for (count = 1; count <= 36; count++)
                {
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 10).append(" 0 moveto");
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 20).append(" 0 lineto stroke");
                    sb.append("\n").append(10*rotationIncrement).append(" rotate");
                }
                sb.append("\n").append("-5 rotate");
                // create hour marks 15 degree
                for (count = 1; count <= 24; count++)
                {
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 20).append(" 0 moveto");
                    sb.append("\n").append(myAstrolabe.getInnerLimbRadius() + 25).append(" 0 lineto stroke");
                    sb.append("\n").append(15*rotationIncrement).append(" rotate");
                }
                out += "\n" + sb.toString();
            }
        } else // Scale is in minutes not degrees
        {
            out += "\n" + "0 0 " + (myAstrolabe.getInnerLimbRadius() + 5) + " 0 360 arc stroke";
            // create 5 minute marks
            rotationIncrement = (double)360/1440; //degrees per minute
            for (count = 1; count <= 288; count++)
            {
                out += "\n" + (myAstrolabe.getInnerLimbRadius() + 5) + " 0 moveto";
                out += "\n" + myAstrolabe.getInnerLimbRadius() + " 0 lineto stroke";
                out += "\n" + (5 * rotationIncrement) + " rotate";
            }
            // create 15 minute marks
            for (count = 1; count <= 96; count++)
            {
                out += "\n" + (myAstrolabe.getInnerLimbRadius() + 10) + " 0 moveto";
                out += "\n" + myAstrolabe.getInnerLimbRadius() + " 0 lineto stroke";
                out += "\n" + (15 * rotationIncrement) + " rotate";
            }
            // create hour marks
            for (count = 1; count <= 24; count++)
            {
                out += "\n" + (myAstrolabe.getInnerLimbRadius() + 15) + " 0 moveto";
                out += "\n" + myAstrolabe.getInnerLimbRadius() + " 0 lineto stroke";
                out += "\n" + (60 * rotationIncrement) + " rotate";
            }
        }
        return out;
    }

    /**
     * Labels the limb of the Astrolabe
     *
     */
    private String labelLimbScale(){
        String out = "";
        int count;
        int radiusOffset;
        String fontSizeText;
        String[] hourArray;

        // check for degree scale
        if (myAstrolabe.getDegreeScaleType() != 0){ //There is a Degree scale
            radiusOffset = 8;
            fontSizeText = "NormalFont8";
        }else{ // no degree scale
            radiusOffset = 18;
            fontSizeText = "NormalFont12";
        }

        //Mark Hours
        if(myAstrolabe.getHourMarkings() == 0){//Roman
            // check for southern hemisphere
            if (myAstrolabe.getLocation().getLatDir().equals("S"))            {
                hourArray = Astrolabe.S_ROMANHOURS;
            }else{
                hourArray = Astrolabe.ROMANHOURS;
            }

            out += "\n" + fontSizeText + " setfont";
            for (count = 0; count <= 11; count++)            {
                out += EPSToolKit.drawOutsideCircularText(hourArray[count], 10, (-90+(count*15)), (myAstrolabe.getMaterRadius() - radiusOffset));
            }
            for (count = 0; count <= 11; count++)            {
                out += EPSToolKit.drawOutsideCircularText(hourArray[count], 10, (-270+(count*15)), (myAstrolabe.getMaterRadius() - radiusOffset));
            }

        }else if(myAstrolabe.getHourMarkings() == 1){//Arabic
            // check for southern hemisphere
            if (myAstrolabe.getLocation().getLatDir().equals("S")){
                hourArray = Astrolabe.S_ARABICHOURS;
            }else{
                hourArray = Astrolabe.ARABICHOURS;
            }

            // mark hours
            out += "\n" + fontSizeText + " setfont";
            for (count = 0; count <= 11; count++){
                out += EPSToolKit.drawOutsideCircularText(hourArray[count], 10, (-90+(count*15)), (myAstrolabe.getMaterRadius() - radiusOffset));
            }
            for (count = 0; count <= 11; count++){
                out += EPSToolKit.drawOutsideCircularText(hourArray[count], 10, (-270+(count*15)), (myAstrolabe.getMaterRadius() - radiusOffset));
            }

        }else if(myAstrolabe.getHourMarkings() == 2){//symbol
            out += EPSToolKit.setUpSymbols();
            out += "\n" + "NormalFont12 setfont";
            // draw cross at noon
            out += "\n" + "0 " + (myAstrolabe.getMaterRadius() - 14) + " cross";
            for (count = 0; count < 23; count++){
                out += EPSToolKit.drawOutsideCircularText(Astrolabe.SYMBOLLETTERS[count], 10, (-(-90+((count+1)*15))), (myAstrolabe.getMaterRadius() - 18));
            }
            out += "\n" + "7.5 rotate";
            for (count = 0; count <= 23; count++){
                out += "\n" + (myAstrolabe.getMaterRadius() - 14) + " 0 diamond";
                out += "\n" + "15 rotate";
            }
            out += "\n" + "-7.5 rotate";
        }

        // Label the degree scale, if any
        Double labelRadius;
        Double fontSize;

        if(myAstrolabe.getHourMarkings() != 3){//if there is a visible Hour Scale
            labelRadius =(myAstrolabe.getInnerLimbRadius() + 5);
            fontSize = 8.0;
            out += "\n" + "NormalFont8 setfont";
        }else{
            labelRadius = (myAstrolabe.getMaterRadius() - 18);
            fontSize = 10.0;
            out += "\n" + "NormalFont12 setfont";
        }

        if(myAstrolabe.getDegreeScaleType() == 1){//0-90
            //Mark degrees
            for (count = 1; count <= 9; count++){
                out += EPSToolKit.drawOutsideCircularText((count*10)+"", fontSize, ((count*10)), labelRadius);
            }
            for (count = 1; count < 9; count++){
                out += EPSToolKit.drawOutsideCircularText((count*10)+"", fontSize, (180-(count*10)), labelRadius);
            }
            for (count = 1; count <= 9; count++){
                out += EPSToolKit.drawOutsideCircularText((count*10)+"", fontSize, (-(count*10)), labelRadius);
            }
            for (count = 1; count < 9; count++){
                out += EPSToolKit.drawOutsideCircularText((count*10)+"", fontSize, -((180-(count*10))), labelRadius);
            }
            //Mark 00
            out += EPSToolKit.drawOutsideCircularText("00", fontSize, 0, labelRadius);
            out += EPSToolKit.drawOutsideCircularText("00", fontSize, 180, labelRadius);
        }else if(myAstrolabe.getDegreeScaleType() == 2){//0-360
            // draw cross at noon
            for (count = 0; count < 36; count++){
                out += EPSToolKit.drawOutsideCircularText((count*10)+"", fontSize, (-(-90+(count*10))), labelRadius);
            }
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
        out += "\n" + "1 setgray";
        out += "\n" + "0 0 " + (myAstrolabe.getMaterRadius()) + " 0 360 arc fill";
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + myAstrolabe.getMaterRadius() + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (myAstrolabe.getInnerLimbRadius()) + " 0 360 arc stroke";

        out += drawLimbScale();
        out += labelLimbScale();

        out += "\n" + "%% ==================== End Create Mater Limb ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws the Quadratum Nauticum of an astrolabe using EPS
     *
     * @return  returns the ps code for drawing the Quadratum Nauticum
     *
     */
    public String buildNauticum(){
        //compute size of box
        double boxRadius = myAstrolabe.getCapricornRadius();
        double boxHalfSide = Math.sqrt((boxRadius*boxRadius)/2.0); //from pythagorus
        double currentBoxHalfSide;
        double degreeInterval;
        int count;
        int count1;

        String out = "";
        out += "\n" + "0 setgray";

        //draw box	
        currentBoxHalfSide = boxHalfSide;
        out += "\n" + "% =============== Create Nauticum =================";
        out += "\n" + "newpath";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " moveto";
        out += "\n" + currentBoxHalfSide + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + currentBoxHalfSide + " lineto";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " lineto stroke";

        currentBoxHalfSide = currentBoxHalfSide-4;
        out += "\n" + "newpath";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " moveto";
        out += "\n" + currentBoxHalfSide + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + currentBoxHalfSide + " lineto";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " lineto stroke";

        currentBoxHalfSide = currentBoxHalfSide-12;
        out += "\n" + "newpath";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " moveto";
        out += "\n" + currentBoxHalfSide + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + currentBoxHalfSide + " lineto";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " lineto stroke";

        currentBoxHalfSide = currentBoxHalfSide-4;
        out += "\n" + "newpath";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " moveto";
        out += "\n" + currentBoxHalfSide + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + (-currentBoxHalfSide) + " lineto";
        out += "\n" + (-currentBoxHalfSide) + " " + currentBoxHalfSide + " lineto";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " lineto stroke";

        // draw degrees
        currentBoxHalfSide = boxHalfSide - 20;
        degreeInterval = currentBoxHalfSide/90.0;

        for (count1 = 0; count1 < 4; count1++)
        {

            for (count = 0; count <= 90; count++)
            {
                out += "\n" + "newpath";// top
                out += "\n" + (count * degreeInterval) + " " + currentBoxHalfSide + " moveto";
                out += "\n" + (count * degreeInterval) + " " + (currentBoxHalfSide + 4) + " lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + (-(count * degreeInterval)) + " " + currentBoxHalfSide + " moveto";
                out += "\n" + (-(count * degreeInterval)) + " " + (currentBoxHalfSide + 4) + " lineto stroke";
            }
            out += "\n" + "90 rotate";
        }

        // draw 5 degree marks
        currentBoxHalfSide = boxHalfSide - 20;
        degreeInterval = currentBoxHalfSide/18.0;

        for (count1 = 0; count1 < 4; count1++)
        {
            for (count = 0; count <= 18; count++)
            {
                out += "\n" + "newpath";// top
                out += "\n" + (count * degreeInterval) + " " + currentBoxHalfSide + " moveto";
                out += "\n" + (count * degreeInterval) + " " + (currentBoxHalfSide + 8) + " lineto stroke";
                out += "\n" + "newpath";
                out += "\n" + (-(count * degreeInterval)) + " " + currentBoxHalfSide + " moveto";
                out += "\n" + (-(count * degreeInterval)) + " " + (currentBoxHalfSide + 8) + " lineto stroke";
            }
            out += "\n" + "90 rotate";
        }

        // draw 10 degree marks
        currentBoxHalfSide = boxHalfSide - 20;
        degreeInterval = currentBoxHalfSide/9.0;

        for (count1 = 0; count1 < 4; count1++)
        {
            for (count = 0; count <= 9; count++)
            {
                out += "\n" + "newpath";// top
                out += "\n" + (count * degreeInterval) + " " + currentBoxHalfSide + " moveto";
                out += "\n" + (count * degreeInterval) + " " + (currentBoxHalfSide + 16) + " lineto stroke";
                if (count > 0) // mark tens
                {
                    out += "\n" + (count * degreeInterval) + " "+ (currentBoxHalfSide + 8) + " moveto";
                    out += "\n" + "NormalFont5 setfont";
                    out += EPSToolKit.centerText(Integer.toString(count * 10));
                }

                if ((count == 3)||(count == 6)) //draw grid at 30 and 60
                {
                    out += "\n" + "newpath";
                    out += "\n" + (count * degreeInterval) + " " + currentBoxHalfSide + " moveto";
                    out += "\n" + (count * degreeInterval) + " " + (-currentBoxHalfSide) + " lineto stroke";
                }

                out += "\n" + "newpath";
                out += "\n" + (-(count * degreeInterval)) + " " + currentBoxHalfSide + " moveto";
                out += "\n" + (-(count * degreeInterval)) + " " + (currentBoxHalfSide + 16) + " lineto stroke";
                if (count > 0) // mark tens
                {
                    out += "\n" + (-(count * degreeInterval)) + " "+ (currentBoxHalfSide + 8) + " moveto";
                    out += "\n" + "NormalFont5 setfont";
                    out += EPSToolKit.centerText(Integer.toString(count * 10));
                }
            }
            out += "\n" + "90 rotate";
        }

        // draw diagonal divisions
        out += "\n" + "newpath";
        out += "\n" + "0 " + currentBoxHalfSide + " moveto";
        out += "\n" + "0 " + (-currentBoxHalfSide) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + currentBoxHalfSide + " 0" + " moveto";
        out += "\n" + (-currentBoxHalfSide) + " 0" + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + currentBoxHalfSide + " " + currentBoxHalfSide + " moveto";
        out += "\n" + (-currentBoxHalfSide) + " " + (-currentBoxHalfSide) + " lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + (-currentBoxHalfSide) + " " + currentBoxHalfSide + " moveto";
        out += "\n" + currentBoxHalfSide + " " + (-currentBoxHalfSide) + " lineto stroke";

        // draw wind rose
        double windRose = boxHalfSide/2.0;
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + windRose + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (windRose - 4) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (windRose - 16) + " 0 360 arc stroke";
        out += "\n" + "0 0 " + (windRose - 20) + " 0 360 arc stroke";

        for (count = 0; count < 32; count++)
        {
            out += "\n" + "newpath";
            out += "\n" + "0 " + (windRose - 4) + " moveto";
            out += "\n" + "0 " + (windRose - 16) + " lineto stroke";
            out += "\n" + "11.25 rotate";
        }

        for (count = 0; count < 16; count++)
        {
            out += "\n" + "newpath";
            out += "\n" + "0 0 moveto";
            out += "\n" + "0 " + (windRose - 16) + " lineto stroke";
            out += "\n" + "22.5 rotate";
        }

        // Label
        out += "\n" + "0 " + (boxHalfSide + 10) + " moveto";
        out += "\n" + "NormalFont20 setfont";
        out += EPSToolKit.centerText("SEPTENTRIO");
        out += "\n" + "0 " + (-(boxHalfSide + 20)) + " moveto";
        out += "\n" + "NormalFont20 setfont";
        out += EPSToolKit.centerText("MERIDIES");
        out += "\n" + "90 rotate";
        out += "\n" + "0 " + (boxHalfSide + 10) + " moveto";
        out += "\n" + "NormalFont20 setfont";
        out += EPSToolKit.centerText("OCCIDENS");
        out += "\n" + "180 rotate";
        out += "\n" + "0 " + (boxHalfSide + 10) + " moveto";
        out += "\n" + "NormalFont20 setfont";
        out += EPSToolKit.centerText("ORIENS");
        out += "\n" + "90 rotate";

        return out;
    }

    /**
     * Draws the houses of heaven of an astrolabe using EPS
     *
     * @param   myAstrolabe    the astrolabe object
     * @return  returns the ps code for drawing the houses of heaven
     *
     */
    private String computeHousesofHeaven(Astrolabe myAstrolabe){
        String out = "";
        if (Math.abs(myAstrolabe.getLocation().getLatitude()) > 60.0){  //todo remove multiple calls to myAstrolabe.getLocation()
            // Houses do not draw properly above about 60 degrees
            return "";
        }

        out += "\n" + "%% ================ Draw Houses of Heaven Lines =================";
        out += "\n" + "[2 2] 0 setdash"; // set dashed line

        if (Math.abs(myAstrolabe.getLocation().getLatitude()) < 1.0){
            // at the equator the lines are not arcs, but radial lines
            // Below a half degree latitude they don't draw properly, so substitute radial lines
            for (int i = 0; i <= 5; i++)
            {
                out += "\n" + "30 rotate";
                out += "\n" + (-myAstrolabe.getCapricornRadius()) + " 0 moveto";
                out += "\n" + (myAstrolabe.getCapricornRadius()) + " 0 lineto stroke";
            }
            out += "\n" + "-180 rotate";
        }else{

            Point2D.Double northPoint = new Point2D.Double(0,0);
            Point2D.Double southPoint = new Point2D.Double(0,0);
            Point2D.Double equatorPoint = new Point2D.Double(0,0);

            //Compute center of horizon
            Double center = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude()))) / (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude())))));
            //compute radius of horizon
            Double radius = myAstrolabe.getEquatorRadius() * (Math.cos(Math.toRadians(0)) / (Math.sin(Math.toRadians(Math.abs(myAstrolabe.getLocation().getLatitude())))));

            // Locate North point
            northPoint.y = center-radius;
            northPoint.x = 0.0;
            // locate South point - Note: where horizon crosses meridian
            southPoint.y = center+radius;
            southPoint.x = 0.0;

            ThreePointCenter MyCircle;

            for (int i = 0; i <= 2; i++)        {
                equatorPoint.x = Math.cos(Math.toRadians(30 * i)) * myAstrolabe.getEquatorRadius();
                equatorPoint.y = Math.sin(Math.toRadians(30 * i)) * myAstrolabe.getEquatorRadius();

                MyCircle = new ThreePointCenter(northPoint, southPoint, equatorPoint );

                if (MyCircle.isCircle()){
                    out += "\n" + MyCircle.getCenter().x + " " + MyCircle.getCenter().y + " " + MyCircle.getRadius() + " 0 360 arc stroke";
                    out += "\n" + (-MyCircle.getCenter().x) + " " + MyCircle.getCenter().y + " " + MyCircle.getRadius() + " 0 360 arc stroke";
                }
            }
        }
        out += "\n" + "[] 0 setdash"; // set solid line
        out += "\n" + "%% ================ End Draw Houses of Heaven Lines =================";
        out += "\n" + "%% ================ Label Houses of Heaven =================";
        out += "\n" + "NormalFont12 setfont";
        out += "\n" + ".5 setgray";

        // check for southern hemisphere
        String[] hourArray;
        if (myAstrolabe.getLocation().getLatDir().equals("S")){
            hourArray = Astrolabe.S_ROMAN;
        }else
        {
            hourArray = Astrolabe.ROMAN;
        }


        for (int i = 0; i <= 11; i++)
        {
            out += EPSToolKit.drawOutsideCircularText(hourArray[i], 10, (-165+(i*30)), (myAstrolabe.getEquatorRadius() -5));
        }
        out += "\n" + "0 setgray";
        out += "\n" + "%% ================ End Label Houses of Heaven =================";

        return out;
    }

    /**
     * Draws the front of an astrolabe using EPS
     *
     *@param myAstrolabeIn the astrolabe object
     *@return  returns the ps code for drawing the front of the astrolabe
     *
     */
    public String createFront(Astrolabe myAstrolabeIn){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Astrolabe Front");
        out += "\n" + "%% setup";

        if (myAstrolabe.getShowRegistrationMarks())
        {
            out += EPSToolKit.registrationMarks();
        }

        out += "\n" + "306 396 translate";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();

        if (myAstrolabe.getShowThrone() && myAstrolabe.getFrontPrintOption() != 1){
            out += "\n" + "gsave";
            if (myAstrolabe.getShapeOption() == 1)
            {
                out += EPSToolKit.buildMaterThrone2(myAstrolabe);
            }else
            {
                out += EPSToolKit.buildMaterThrone(myAstrolabe);
            }
            out += "\n" + "grestore";
            out += "\n" + "";
        }
        if (myAstrolabe.getFrontPrintOption() != 1) // not just the plate
        {
            if (myAstrolabe.getShapeOption() == 1){
                out += "\n" + "gsave";
                out += EPSToolKit.buildOctagon(myAstrolabe);
                out += "\n" + "grestore";
            }
            out += "\n" + "gsave";
            out += buildMaterLimb();
            out += "\n" + "grestore";
            out += "\n" + "";
        }

        if (myAstrolabe.getFrontPrintOption() == 3) // if the mater and quad
        {
            out += "\n" + "gsave";
            out += buildNauticum();
            out += "\n" + "grestore";
            out += "\n" + "";

        }
        if (myAstrolabe.getFrontPrintOption() == 0 || myAstrolabe.getFrontPrintOption() == 1) // if not just the mater
        {
            out += "\n" + "gsave";
            out += buildPlate();
            out += "\n" + "grestore";
            out += "\n" + "";
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
