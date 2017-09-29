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
package com.wymarc.astrolabe.generator.printengines.postscript;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.Star;
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;
import com.wymarc.astrolabe.generator.printengines.postscript.util.ZodiacSigns;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

public class RetePrintEngine {

    private Astrolabe myAstrolabe = new Astrolabe();

    /**
     * Class object to define a line on the ecliptic
     *
     */
    private class EclipticLine{
        public Point2D.Double start = new Point2D.Double(0.0,0.0);
        public Point2D.Double end = new Point2D.Double(0.0,0.0);

        /**
         * Constructor for the EclipticLine object
         *
         * @param start Beginning point
         * @param end Beginning point
         *
         */
        EclipticLine (Point2D.Double start , Point2D.Double end) {
            this.start.setLocation(start);
            this.end.setLocation(end);
        }
    }

    /**
     * Generates EPS to draw the Rete outlines
     *
     * @return String containing EPS for drawing the rete outlines
     */
    private String  buildRete(){

        double plateEdge = myAstrolabe.getInnerLimbRadius();
        double capricorn = myAstrolabe.getCapricornRadius();
        double cancer = myAstrolabe.getCancerRadius();
        double equator = myAstrolabe.getEquatorRadius();
        int reteType = myAstrolabe.getReteType();

        String out = "";
        out += "\n" + "%% ==================== Create Rete ====================";
        out += "\n" + "";

        if(reteType == 0 || reteType == 1){
            // draw outer circle at radius of Tropic of Capricorn
            out += "\n" + "%% Draw outer circle";
            out += "\n" + ".7 setgray";
            out += "\n" + "0 0 " + plateEdge + " 0 360 arc fill";
            out += "\n" + "1 setgray";
            out += "\n" + "0 0 " + (plateEdge - 15) + " 0 360 arc fill";

            // compute locaton of zodiac ring and draw it
            out += "\n" + "%% Draw Zodiac";

            double zodiacRadius = (capricorn + cancer) / 2.0;
            double deltaY = capricorn - zodiacRadius;

            // move center to zodiac center
            out += "\n" + "0 " + deltaY + " translate";
            // draw whitespace around zodiac
            out += "\n" + "1 setgray";
            out += "\n" + "0 0 " + (zodiacRadius + 20) + " 0 360 arc fill";
            // draw zodiac ring and remove center
            out += "\n" + ".7 setgray";
            out += "\n" + "0 0 " + zodiacRadius + " 0 360 arc fill";
            out += "\n" + "1 setgray";
            out += "\n" + "0 0 " + (zodiacRadius - 25) + " 0 360 arc fill";
            // re-center
            out += "\n" + "0 " + (-deltaY) + " translate";

            // Mark center pivot
            out += "\n" + ".7 setgray";
            out += "\n" + "%% Mark rule pivot";
            out += "\n" + "newpath";
            out += "\n" + "0 0 25 0 360 arc fill";

            // mark index
            out += "\n" + "%% Mark index";
            out += "\n" + "newpath";
            out += "\n" + "-8 " +(plateEdge-30)+ " moveto";
            out += "\n" + "-8 " +(plateEdge-4)+ " lineto";
            out += "\n" + "-4 " +(plateEdge)+ " lineto";
            out += "\n" + "4 " +(plateEdge)+ " lineto";
            out += "\n" + "8 " +(plateEdge-4)+ " lineto";
            out += "\n" + "8 " +(plateEdge-30)+ " lineto";
            out += "\n" + "-8 " +(plateEdge-30)+ " lineto";
            out += "\n" + "fill";
            out += "\n" + "0 setgray";
            out += "\n" + "newpath";
            out += "\n" + "0 " +(plateEdge-7)+ " moveto";
            out += "\n" + "0 " +(plateEdge)+ " lineto stroke";
            out += "\n" + ".7 setgray";

            // mark vertical support
            out += "\n" + "10 setlinewidth";
            out += "\n" + "%% Mark vertical";
            out += "\n" + "newpath";
            out += "\n" + "5 " + equator + " moveto";
            out += "\n" + "5 55 lineto";
            out += "\n" + "-5 55 lineto";
            out += "\n" + "-5 0 lineto";
            out += "\n" + "5 0 lineto";
            out += "\n" + "5 -75 lineto";
            out += "\n" + "-5 -75 lineto";
            out += "\n" + "-5 " + (-equator) + " lineto stroke";
            out += "\n" + "newpath";
            out += "\n" + "5 " + (-equator) + " moveto";
            out += "\n" + "5 " + (-capricorn) + " lineto stroke";

            // mark horizonal support
            out += "\n" + "%% Mark Horizonal";
            out += "\n" + "newpath";
            out += "\n" + (-capricorn) + " -5 moveto";
            out += "\n" + "-120 -5 lineto";
            out += "\n" + "-120 5 lineto";
            out += "\n" + "-60 5 lineto";
            out += "\n" + "-60 -5 lineto";
            out += "\n" + "60 -5 lineto";
            out += "\n" + "60 5 lineto";
            out += "\n" + "120 5 lineto";
            out += "\n" + "120 -5 lineto";
            out += "\n" + capricorn + " -5 lineto stroke";

            // draw top arc
            out += "\n" + "newpath";
            out += "\n" + "105 140 moveto";
            out += "\n" + "0 0 " + (equator + 5) + " 60 120 arc";
            out += "\n" + "-105 140 lineto";
            out += "\n" + "stroke";

            // draw bottom arc
            out += "\n" + "newpath";
            out += "\n" + "-180 -110 moveto";
            out += "\n" + "0 0 " + (equator + 5) + " 210 330 arc";
            out += "\n" + "180 -110 lineto";
            out += "\n" + "stroke";

            // draw top supports
            out += "\n" + "newpath";
            out += "\n" + "-170 120 moveto";
            out += "\n" + "-125 100 lineto stroke";
            out += "\n" + "newpath";
            out += "\n" + "170 120 moveto";
            out += "\n" + "125 100 lineto stroke";

            out += "\n" + ".1 setlinewidth";

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

        //mark rim of rete
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + plateEdge + " 0 360 arc stroke";

        out += "\n" + "%% ==================== End Create Rete ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Generates EPS to draw the Zodiac on the rete
     *
     * @return String containing EPS for drawing the rete outlines
     *
     *  Calculates the end points for the lines that represent each degree of solar
     *  longitude around the ecliptic and stores the coordinates in the 
     *  ArrayList<EclipticLine> eclipticTic.
     *  The coordinates of the inner end of the lines at 10 and 20 deg are stored in
     *  the array "label" to define the position of the labels. The ecliptic divisions
     *  are the same for north and south latitudes.
     *
     */
    private String zodiac() {
        String out = "";
        double lin;
        double gl;
        double xro;
        double yro;
        int i;
        int j; //counter for ten degree lines
        int k; // counter for fifteen degree lines (zodiac names)
        Boolean tenLine;
        Boolean fifteenLine;

        HashMap<String, Double> lonLine;

        @SuppressWarnings("unchecked")
        HashMap<String, Double>[] label = new HashMap[24];
        @SuppressWarnings("unchecked")
        HashMap<String, Double>[] name = new HashMap[12];

        @SuppressWarnings("unchecked")
        ArrayList<EclipticLine> eclipticTic = new ArrayList();

        // Sidereal time pointer
        Point2D.Double ticStart = new Point2D.Double(-myAstrolabe.getInnerLimbRadius(), 0.0);
        Point2D.Double ticEnd = new Point2D.Double(-myAstrolabe.getEquatorRadius() + myAstrolabe.LONGL, 0.0);
        EclipticLine tic = new EclipticLine(ticStart,ticEnd);
        eclipticTic.add(tic);

        //Count ecliptic lines
        j = 0;
        k = 0;

        // Calculate geocentric longitude for each degree from Aries 0
        for (i = 1; i <= 360; i++) {
            gl = (i + 180)%360;
            //if (gl == 0)
            if (i == 90){
                ticStart = new Point2D.Double(0.0,-myAstrolabe.getCancerRadius());
                ticEnd = new Point2D.Double(0.0,-myAstrolabe.getCancerRadius() + myAstrolabe.LONGL);
                tic = new EclipticLine(ticStart,ticEnd);
                eclipticTic.add(tic);
                continue;
            }
            lonLine = ecliptic(gl); //Get line for longitude

            // Calculate line ends and tic length
            tenLine = false;
            fifteenLine = false;
            if (i%30 == 0){
                lin = myAstrolabe.LONGL;
            }else{
                if((i%10) == 0){
                    lin = myAstrolabe.TENL;
                    tenLine = true;
                }else if((i%5) == 0){
                    lin = myAstrolabe.MED;
                    if((i%15) == 0){ 
                    	// if 15 degree mark place note for Names                   
                        fifteenLine = true;
                    }
                }else{
                    lin = myAstrolabe.SHORT;
                }
            }
            ticStart = new Point2D.Double(lonLine.get("xb"), lonLine.get("yb"));
            xro = (lonLine.get("rr") - lin) * Math.cos(lonLine.get("b"));
            yro = (lonLine.get("rr") - lin) * Math.sin(lonLine.get("b"));
            ticEnd = new Point2D.Double(xro, yro);
            tic = new EclipticLine(ticStart,ticEnd);
            eclipticTic.add(tic);

            if( tenLine ){
            	//Save 10 deg positions for labels
                label[j] = new HashMap<String, Double>();
                label[j].put("angle",Math.toDegrees(lonLine.get("b")));
                label[j].put("x",xro);
                label[j].put("y",yro);
                j++ ;
            }

            if( fifteenLine ){
            //Save 15 deg positions for Names
                name[k] = new HashMap<String, Double>();
                name[k].put("angle",Math.toDegrees(lonLine.get("b")));
                name[k].put("x",xro);
                name[k].put("y",yro);
                k++ ;
            }
        }

        //draw ecliptic
        out += "\n" + "%% ================ Draw Ecliptic Zodiac =================";

        // compute locaton of zodiac ring and draw it
        out += "\n" + "newpath";
        out += "\n" + "%% Draw Zodiac";
        double zodiacRadius = (myAstrolabe.getCapricornRadius() + myAstrolabe.getCancerRadius()) / 2.0;
        double deltaY = myAstrolabe.getCapricornRadius() - zodiacRadius;
        // move center to zodiac center
        out += "\n" + "0 " + deltaY + " translate";
        // draw zodiac ring
        out += "\n" + "0 setgray";
        out += "\n" + "0 0 " + zodiacRadius + " 0 360 arc stroke";
        // re-center
        out += "\n" + "0 " + (-deltaY) + " translate";

        //draw ticks
        for(EclipticLine thisTic:eclipticTic){
            out += "\n" + "newpath";
            out += "\n" + thisTic.start.x + " "+ thisTic.start.y +" moveto";
            out += "\n" + thisTic.end.x + " " + thisTic.end.y + " lineto stroke";
        }

        // translate positions saved in label[] and names[] from centered on rete to
        // centered on ecliptic, then compute angle for each
        //
        // Note that deltaY contains the y coord diffence and the x coord diff is 0

        // translate
        for(i = 0; i < 24; i++){
            label[i].put("y",label[i].get("y")-deltaY);
        }
        for(i = 0; i < 12; i++){
            name[i].put("y",name[i].get("y")-deltaY);
        }

        //compute angle from 0,0 to x,y , with angle measured from left horizontal anticlockwise
        // var angle = Math.atan2(y2 - y1,x2 - x1);
        for(i = 0; i < 24; i++){
            label[i].put("angle",Math.toDegrees(Math.atan2(label[i].get("y") - 0,label[i].get("x") - 0)));
        }
        for(i = 0; i < 12; i++){
            name[i].put("angle",Math.toDegrees(Math.atan2(name[i].get("y") - 0,name[i].get("x") - 0)));
        }
        
	    String marking;
        // center on ecliptic
        out += "\n" + "0 " + deltaY + " translate";
        if (myAstrolabe.getLocation().getLatDir().equals("S")){
            out += "\n" + "NormalFont5 setfont";
		    //mark ten degree lines
		    marking = "20";
			for(i = 0; i < 24; i++){
				out += EPSToolKit.drawOutsideCircularText(marking, 10, label[i].get("angle"), (zodiacRadius - 12));
		        if(marking.equals("20")){
		        	marking = "10";
		        }else{
		        	marking = "20";
		        }				
			}
			if (myAstrolabe.getShowZodiacSymbols()){
				//Mark Zodiac Symbols
			    out += "\n" + "gsave";
				out += "\n" + "-90 rotate";
				for (i = 0; i <= 11; i++){
			        out += "\n" + "gsave";
			        out += "\n" + name[i].get("angle") +" rotate";
			        out += ZodiacSigns.placeSignNumAt(12 - i, 0.0, (zodiacRadius - 17), .4, .4);
			        out += "\n" + "grestore";               
			    }	    
				out += "\n" + "grestore"; 			
			}else{
				// place sign names
				out += "\n" + "NormalFont8 setfont";	
				for(i = 0; i < 12; i++){
					out += EPSToolKit.drawOutsideCircularText(Astrolabe.ZODIAC[11-i], 10, name[i].get("angle"), (zodiacRadius - 20));
				}    				
			}	    	
	    }else
	    {
	        out += "\n" + "NormalFont5 setfont";
	        //mark ten degree lines
	        marking = "10";
	        for(i = 0; i < 24; i++){
	            out += EPSToolKit.drawOutsideCircularText(marking, 10, label[i].get("angle"), (zodiacRadius - 12));
	            if(marking.equals("10")){
	                marking = "20";
	            }else{
	                marking = "10";
	            }
	        }
	        
			if (myAstrolabe.getReteType() == 1){// we want zodiac symbols
				//Mark Zodiac Symbols
			    out += "\n" + "gsave";
				out += "\n" + "-90 rotate";
				for (i = 0; i <= 11; i++){
			        out += "\n" + "gsave";
			        out += "\n" + name[i].get("angle") +" rotate"; 
			        out += ZodiacSigns.placeSignNumAt(i+1,0.0,(zodiacRadius - 17),.4, .4);
			        out += "\n" + "grestore";               
			    }	    
				out += "\n" + "grestore"; 			
			}else{
		        // place sign names
		        out += "\n" + "NormalFont8 setfont";
		        for(i = 0; i < 12; i++){
		            out += EPSToolKit.drawOutsideCircularText(Astrolabe.ZODIAC[i], 10, name[i].get("angle"), (zodiacRadius - 20));
		        }		
			}
	    }

        // recenter back
        out += "\n" + "0 " + (-deltaY) + " translate";
        out += "\n" + "%% ================ END Ecliptic Zodiac =================";

        return out;
    }


//    //=============================================================================
//    // Divide ecliptic by calendar
//    private String calendar(Astrolabe myAstrolabe){
//        double dt;
//        double td;
//        double lc;
//        double lin;
//        double gl;
//        double xro;
//        double yro;
//        int i;
//        int j;
//        int k;
//        int lines;
//        Boolean tenLine;
//        double t; //Time in Julian centuries from J2000.0
//        t = AstroMath.getT();
//
//        HashMap<String, Double> lonLine;
//
//        @SuppressWarnings("unchecked")
//        HashMap<String, Double>[] label = new HashMap[24];
//        @SuppressWarnings("unchecked")
//        HashMap<String, Double>[] name = new HashMap[12];
//
//        ArrayList<EclipticLine> eclipticTic = new ArrayList<EclipticLine>();
//
//        // Sidereal time pointer
//        Point2D.Double ticStart = new Point2D.Double(-myAstrolabe.getInnerLimbRadius(), 0.0);
//        Point2D.Double ticEnd = new Point2D.Double(-myAstrolabe.getEquatorRadius() + myAstrolabe.LONGL, 0.0);
//        EclipticLine tic = new EclipticLine(ticStart,ticEnd);
//        eclipticTic.add(tic);
//
//        //Count ecliptic lines
//        lines = 1;
//        j = 0;
//        k = 0;
//
//        // Calculate geocentric longitude for each day from Jan 0
//        dt = 1.0 / 36525.0; //Change in T per day
//        lc = myAstrolabe.getLongitude() / (360.0 * 36525.0); //Longitude correction
//        td = t + lc; //Starting T is Jan 0
//
//        //Calculate ecliptic for each month of year
//        for (i = 0; i <= 11; i++){
//            //Calculate ecliptic tics for each day of month
//            for (k = 0; k <= (Astrolabe.MONTHSDAYS[i] - 1); k++){
//                gl = AstroMath.geolong(td);
//                gl -= 180.0;
//                if (gl < 0.0){
//                    gl = gl + 360.0;
//                }
//                lonLine = ecliptic(gl); //Get line for longitude
//                td += dt;
//                tenLine = false;
//                if (k == 0){
//                	//Line length
//                    lin = myAstrolabe.LONGL ;
//                }else{
//                    if ((k%10) == 0){
//                        lin = myAstrolabe.TENL;
//                        tenLine = true;
//                    }else if ((k%5) == 0){
//                        lin = myAstrolabe.MED;
//                    }else{
//                        lin = myAstrolabe.SHORT;
//                    }
//                }
//                if (k == 30){
//                    lin =  myAstrolabe.SHORT;
//                    tenLine = false;
//                }
//
//				xro = (lonLine.rr - lin) * Math.cos(lonLine.b);
//				yro = (lonLine.rr - lin) * Math.sin(lonLine.xb);
//
//				ticStart = new Point2D.Double(lonLine.get("xb"),lonLine.get("yb"));
//				ticEnd = new Point2D.Double(xro,yro);
//				tic = new EclipticLine(ticStart,ticEnd);
//                eclipticTic.add(tic);
//                lines++;
//
//	            if( tenLine ){
//	            //Save 10 deg positions for labels
//	                label[j] = new HashMap();
//	                label[j].put("angle",Math.toDegrees(lonLine.get("b")));
//	                label[j].put("x",xro);
//	                label[j].put("y",yro);
//	                j++ ;
//	            }
//            }
//        }
//        lines--; //Adjust line count to final value
//
//        //draw ecliptic
//        String out = "";
//        out += "\n" + "%% ================ Draw Ecliptic Calendar =================";
//        Iterator iter = eclipticTic.iterator();
//        while (iter.hasNext()){
//            tic = (EclipticLine)iter.next();
//            out += "\n" + "newpath";
//            out += "\n" + tic.start.x + " "+ tic.start.y +" moveto";
//            out += "\n" + tic.end.x + " " + tic.end.y + " lineto stroke";
//        }
//        out += "\n" + "%% ================ END Ecliptic Calendar =================";
//
//        return out;
//    }



    private HashMap<String, Double> ecliptic (double gl){
        double a;
        double m1;
        double qa;
        double qb;
        double qc;
        double ac;
        double f1;
        double f2;
        double f3;
        double x1;
        double x2;
        double y1;
        double y2;
        double b1;
        double b2;

        double t; //Time in Julian centuries from J2000.0
        t = AstroMath.getT();

        double rec = (myAstrolabe.getCapricornRadius() + myAstrolabe.getCancerRadius()) / 2.0;
        double yec = myAstrolabe.getCapricornRadius() - rec;
        double obr = Math.toRadians(AstroMath.obliquity(t));
        double yep = myAstrolabe.getEquatorRadius() * Math.tan(obr/2.0);

        @SuppressWarnings("unchecked")
        HashMap<String, Double> result = new HashMap();

        if( gl%90 == 0 ){
        	//Points at quadrants
            result.put("b", Math.toRadians(gl));
            result.put("xb", myAstrolabe.getEquatorRadius() * Math.signum(90-gl) + myAstrolabe.getEquatorRadius() * ((gl == 270)?1:0));

            if ((gl==180)||(gl==0)){
                result.put("yb", 0.0);
            }else if (gl == 90){
                result.put("yb", myAstrolabe.getCapricornRadius());
            }else if (gl == 270){
                result.put("yb", myAstrolabe.getCancerRadius());
            }
        }else{ 
        	//Points not at quadrants
            a = Math.toRadians(gl);
            m1 = ( myAstrolabe.getEquatorRadius() * Math.sin(a) - yep ) / (myAstrolabe.getEquatorRadius() * Math.cos(a)); //Slope from ecl pole to equ
            qa = 1.0 + m1*m1; //Quadratic Terms
            qb = -2.0 * (yep + yec * m1*m1);
            qc = yep*yep + m1*m1 * ( yec*yec - rec*rec );
            ac = 4.0 * qa * qc; //4AC Part of Discriminanat
            b2 = qb*qb; //B^2 Part of Discriminant
            f1 = -qb;
            f2 = Math.sqrt((b2 - ac));
            f3 = 2.0 * qa; //Parts of Quadratic Solution
            y1 = (f1 + f2) / f3;
            y2 = (f1 - f2) / f3; //Roots are Y-Coordinates of Intersection
            x1 = (y1 - yep) / m1; //X-Coordinates are Where Int. Line Meets Y
            x2 = (y2 - yep) / m1;
            b1 = AstroMath.polar(y1,x1); //Angles
            b2 = AstroMath.polar(y2,x2);

            //Determine correct roots
            if ( gl <= 180.0){
                if (b1 < b2){
                    result.put("b", b1);
                    result.put("xb", x1);
                    result.put("yb", y1);
                }else{
                    result.put("b", b2);
                    result.put("xb", x2);
                    result.put("yb", y2);
                }
            }else{
                if (b1 > b2){
                    result.put("b", b1);
                    result.put("xb", x1);
                    result.put("yb", y1);
                }else{
                    result.put("b", b2);
                    result.put("xb", x2);
                    result.put("yb", y2);
                }
            }
            if ( (gl > 90.0) && (result.get("b") < (Math.PI/2.0)) ){
                if (b1 > b2){
                    result.put("b", b1);
                    result.put("xb", x1);
                    result.put("yb", y1);
                }else{
                    result.put("b", b2);
                    result.put("xb", x2);
                    result.put("yb", y2);
                }
            }
        }

        // Calculate radius vector
        double rr = Math.sqrt( (result.get("xb"))*(result.get("xb")) + (result.get("yb"))*(result.get("yb")));
        result.put("rr",rr);

        return result;
    }

    private String placeStar(Star star){
		// compute angle and radius
        double angle;
        double radius;
        if (myAstrolabe.getLocation().getLatDir().equals("S")){
			//southern hemisphere		
	        angle = (-star.ra * 15) + 180;
	        radius = myAstrolabe.getEquatorRadius() * Math.tan(Math.toRadians(90 - (-star.dec))/2.0);
		}else{
	        angle = (star.ra * 15) + 180;
	        radius = myAstrolabe.getEquatorRadius() * Math.tan(Math.toRadians(90 - star.dec)/2.0);
		}        
        Point2D.Double starLocation = new Point2D.Double((radius * Math.cos(Math.toRadians(angle))),(radius * Math.sin(Math.toRadians(angle))));

        String out = "";
        out += "\n" + "newpath";
        out += "\n" + starLocation.x + " " + starLocation.y + " 1 0 360 arc fill";
        out += "\n" + "newpath";
        out += "\n" + "%% Label Star";
        out += "\n" + "NormalFont6 setfont";
        out += "\n" + starLocation.x + " " + starLocation.y + " moveto";
        out += "\n" + "( " + star.name + ") show";

        return out;
    }

    private String drawStars(){
        //get star position
        String out = "";
        out += "\n" + "%% ================ Draw Stars =================";
        Star[] stars = Star.getStarsList(myAstrolabe.getLocation().getLatDir());
        for(Star star:stars){
            HashMap<String,Double> star1 = AstroMath.precess(star.ra,star.dec);
            star.ra = star1.get("precessedRA");
            star.dec = star1.get("precessedDec");
            out += placeStar(star);
        }
        out += "\n" + "%% ================ END Stars =================";
        return out;
    }

    private String placeStarPointer(Star starPointer){
        String out = "";
		// compute angle and radius
        double angle;
        double radius;
        if (myAstrolabe.getLocation().getLatDir().equals("S")){
			//southern hemisphere
		    angle = (-starPointer.ra * 15) + 180;
	        radius = myAstrolabe.getEquatorRadius() * Math.tan(Math.toRadians(90 - (-starPointer.dec))/2.0);
		}else{
	        angle = (starPointer.ra * 15) + 180;
	        radius = myAstrolabe.getEquatorRadius() * Math.tan(Math.toRadians(90 - starPointer.dec)/2.0);
		}        
        Point2D.Double starLocation = new Point2D.Double((radius * Math.cos(Math.toRadians(angle))),(radius * Math.sin(Math.toRadians(angle))));

        // draw pointer
        if(starPointer.base1.x == 0 && starPointer.base1.y == 0 && starPointer.base2.x == 0 && starPointer.base2.y == 0){ 
        	// if the star position is over an arm of the rete we need a hole not a pointer
            out += "\n" + "1 setgray"; //white
            out += "\n" + "newpath";
            out += "\n" + starLocation.x + " " + starLocation.y + " 3 0 360 arc fill";
            out += "\n" + "0 setgray"; //black
            out += "\n" + "newpath";
            out += "\n" + starLocation.x + " " + starLocation.y + " 1 0 360 arc fill";
        }else{
            out += "\n" + ".7 setgray"; //grey
            out += "\n" + "newpath";
            out += "\n" + starPointer.base1.x +" " + starPointer.base1.y + " moveto";
            out += "\n" + starPointer.base2.x +" " + starPointer.base2.y + " lineto";
            out += "\n" + starLocation.x +" " + starLocation.y + " lineto";
            out += "\n" + starPointer.base1.x +" " + starPointer.base1.y + " lineto fill";
            out += "\n" + "0 setgray"; //black
        }
        return out;
    }

    private String labelStarPointer(Star starLabel){
        String out = "";
        // label star
        //20 300 12 80 (text1) outputtext
        out += "\n" + "newpath";
        out += "\n" + "%% Label Star";
        out += "\n" + starLabel.labelPos.x + " " + starLabel.labelPos.y + " 6 " + starLabel.labelOrient + " (" + starLabel.name + ") outputtext";

        return out;
    }

    private String drawStarPointers(){
        String out = "";
        //get star position
        out += "\n" + "%% ================ Draw Star pointers =================";
        Star[] stars = Star.getStarsList(myAstrolabe.getLocation().getLatDir());
        out += "\n" + "%% ================ Draw pointers =================";
        for(Star star:stars){
            //get precessed star position
            Star starPointer = new Star();
            HashMap<String,Double> star1 = AstroMath.precess(star.ra,star.dec);
            starPointer.ra = star1.get("precessedRA");
            starPointer.dec = star1.get("precessedDec");
            starPointer.base1 = new Point2D.Double(star.base1.x, star.base1.y);
            starPointer.base2 = new Point2D.Double(star.base2.x, star.base2.y);

            // draw star pointer
            out += placeStarPointer(starPointer);
        }
        out += "\n" + "%% ================ Label pointers =================";
        for(Star star:stars){
            Star starLabel = new Star();
            starLabel.name = star.name;
            starLabel.labelPos = new Point2D.Double(star.labelPos.x, star.labelPos.y);
            starLabel.labelOrient = star.labelOrient;

            // Label star pointer
            out += labelStarPointer(starLabel);
        }
        out += "\n" + "%% ================ END pointers =================";
        return out;
    }

    private String grid(Astrolabe myAstrolabe){
        // creates a 5 point grid over the rete for placing star points
        // and other editing

        double limit = myAstrolabe.getInnerLimbRadius() + 10;

        String out = "";

        // draw horizontal and vertical lines every 5 points
        for(int i = 0; i< limit; i = i+5){
            // draw horz lines
            out += "\n" + "newpath";
            out += "\n" + -limit +" " + i + " moveto";
            out += "\n" + limit +" " + i + " lineto stroke";
            out += "\n" + "newpath";
            out += "\n" + -limit +" " + -i + " moveto";
            out += "\n" + limit +" " + -i + " lineto stroke";

            // draw vert lines
            out += "\n" + "newpath";
            out += "\n" + i +" " + -limit + " moveto";
            out += "\n" + i +" " + limit + " lineto stroke";
            out += "\n" + "newpath";
            out += "\n" + -i +" " + -limit + " moveto";
            out += "\n" + -i +" " + limit + " lineto stroke";
        }

        // mark tics every 20 points
        for(int i = 0; i< limit; i = i+20){
            // draw horz tic
            out += "\n" + "newpath";
            out += "\n" + limit + " " + i + " moveto";
            out += "\n" + (limit + 5) + " " + i + " lineto stroke";

            out += "\n" + "newpath";
            out += "\n" + limit + " " + -i + " moveto";
            out += "\n" + (limit + 5) + " " + -i + " lineto stroke";


            // draw vert tic
            out += "\n" + "newpath";
            out += "\n" + i + " " + limit + " moveto";
            out += "\n" + i + " " + (limit + 5) + " lineto stroke";

            out += "\n" + "newpath";
            out += "\n" + -i + " " + limit + " moveto";
            out += "\n" + -i + " " + (limit + 5) + " lineto stroke";
        }

        return out;
    }

    /**
     * computes and draws an arc of a given width
     * at a given postion
     *
     * since   2.0
     *
     */
    private String drawCenteredArc(double startAngle, double endAngle, double arcRadius, double arcWidth){
        String out = "";
        out += "\n" + arcWidth + " setlinewidth";
        out += "\n" + "newpath";
        out += "\n" + "0 0 " + arcRadius + " " + startAngle + " " + endAngle + " arc stroke";
        out += "\n" + ".1 setlinewidth";
        return out;
    }


    /**
     * Draws the rete and rules using EPS
     *
     * since   0.1
     *
     *
     * return returns String containing EPS code for Rete printing
     *
     */
    public String createRete(Astrolabe myAstrolabeIn){
        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Astrolabe Rete");
        out += "\n" + "%% setup";
        out += "\n" + ".1 setlinewidth";
        out += "\n" + "";
        out += EPSToolKit.setUpFonts();
        out += EPSToolKit.setUpCircularText();
        out += EPSToolKit.setUpTextRotation();
        out += "\n" + "gsave";
        if (myAstrolabe.getShowRegistrationMarks()){
            out += EPSToolKit.registrationMarks();
        }

        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "306 396 translate";
        out += "\n" + "";
        out += buildRete();

        if(myAstrolabe.getReteType() == 0 || myAstrolabe.getReteType() == 1){
            out += drawStarPointers();
        }else{
            out += drawStars();
        }

        out += zodiac();

        //fileIn = grid(fileIn); //comment out in production
        out += "\n" + "";
        out += "\n" + "grestore";

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }
}
