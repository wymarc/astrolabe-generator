package com.wymarc.astrolabe.generator.printengines.postscript;

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.Location;
import com.wymarc.astrolabe.generator.printengines.postscript.util.ZodiacSigns;
import com.wymarc.astrolabe.math.AstroMath;

import java.awt.geom.Point2D;

public class ArcsOfTheSigns {
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

	private Astrolabe myAstrolabe;
	
	/**
	 * Computes the great circle azimuth to Mecca(Qibla)for a given location 
	 * and then returns the angle of the sun when it is at that azimuth
	 *
	 * @param   observer: a Location defining the given location
	 * @param   zodiacAngle: the location of the sum on the zodiac rel to Aries 0 
	 * @return sun's altitude 
	 */
	private Double sunsAltitudeAtQiblaAngle(Location observer, Double zodiacAngle){				
		Double qibla = AstroMath.qiblaAngle(observer);
		
		//Compute Sun's altitude when it is at that azimuth
		// Too complicated to discuss here. See Morrison[141,142]
		// tan alt0 = cos qibla/tan observer.latitude	Morrison[142]
		Double alt0 = AstroMath.normal(Math.toDegrees(Math.atan(Math.cos(Math.toRadians(qibla)) / Math.tan(Math.toRadians(observer.getLatitude())))));
		
		Double declination = AstroMath.normal(Math.toDegrees(Math.asin(Math.sin(Math.toRadians(23.44)) * Math.sin(Math.toRadians(zodiacAngle)))));
	
		// sin alt1 = cos alt0 sin declination/sin observer.latitude   Morrison[142]
		Double alt1 = AstroMath.normal(Math.toDegrees(Math.asin((Math.cos(Math.toRadians(alt0)) * Math.sin(Math.toRadians(declination))) /
                Math.sin(Math.toRadians(observer.getLatitude())))));
		
		
		return alt0 - alt1;		
	}	

	/**
	 * Returns an list of city names and locations 
	 * Todo: replace with call to config file
	 *
	 * @return list of city locations
	 */
	private Location[] getCities(){			
		Location[] citys = new Location[4];
		citys[0] = new Location("Const",41,1,0,"N",28,58,0,"E");		
		citys[1] = new Location("Isfah",32,40,0,"N",51,38,0,"E");
		citys[2] = new Location("Cairo",30,3,0,"N",31,15,0,"E");
		citys[3] = new Location("Bagh",33,20,0,"N",44,26,0,"E");
		//citys[4] = new Location("Tunis",36,48,24,"N",10,10,53,"E");
		//citys[] = new Location("Harran",35,51,0,"N",39,0,0,"E");
		
		return citys;
	}
	
	/**
	 * computes and draws the Arcs of the Signs equally spaced
	 * and then computes and adds the Noon Sun lines and the Qibla Lines
	 * 
	 * @return eps string
	 */
	public String buildArcsOfSignsEqual(){
	    int count;
	    int count1;
	    String out = "";
	        
	    // compute size of arc that contains the scale and draw it
	    // note eventually this will be done by looking at what rings are drawn and figuring
	    // the remaining radius
	    Double outerRadius = myAstrolabe.getMaterRadius() - 67;
	    Double innerRadius = outerRadius - 108;
	    Double arcInterval;
	        
	    //draw 30 degree arcs
	    arcInterval = 18.0;
		out += "\n" + "0 setgray";
		StringBuilder sb = new StringBuilder();
	    for (count = 0; count < 7; count++){
            sb.append("\n").append("newpath");
            sb.append("\n").append("0 0 ").append(innerRadius + (arcInterval * count)).append(" 0 90 arc stroke");
	    }
        out += "\n" + sb.toString();

	    //draw end lines
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "newpath";
	    out += "\n" +  "0 " + innerRadius + " moveto";
	    out += "\n" +  "0 " + outerRadius + " lineto stroke";    
	    
	    //mark off space for labels (2x5 degrees)
	    out += "\n" + "5 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "5 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "70 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "5 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "-85 rotate"; // rotate back
	    
	    //draw 10 degree arcs
	    out += "\n" + "[2 2] 0 setdash"; // set dashed line
	    arcInterval = 6.0;
		out += "\n" + "0 setgray";
        sb = new StringBuilder();
	    for (count = 1; count < 18; count++){
		    if (count != 3 && count != 6 && count != 9 && count != 12 && count != 15)// ignore existing lines
		    {
                sb.append("\n").append("newpath");
                sb.append("\n").append("0 0 ").append(innerRadius + (arcInterval * count)).append(" 10 80 arc stroke");
		    }
	    }
        out += "\n" + sb.toString();
	    out += "\n" + "[] 0 setdash"; // set solid line 
	    
	    // draw Noon altitude lines
	    int[] latList = {35,40,45,50}; //target latitudes
	    Double noonAlt;
	    Double radius;
	    Double interval = (outerRadius-innerRadius)/180;
		Point2D.Double currentPoint;
	    Point2D.Double previousPoint;

        sb = new StringBuilder();
	    for (count1 = 0; count1 < latList.length; count1++){
		    noonAlt = AstroMath.sunsNoonAltitude((double)latList[count1],-90.0);
			previousPoint = new Point2D.Double((innerRadius * Math.cos(Math.toRadians(noonAlt))),
	    									(innerRadius * Math.sin(Math.toRadians(noonAlt))));	// set start	    
		    
		    for (count = -85; count <= 90; count = count + 5){
		    	noonAlt = AstroMath.sunsNoonAltitude((double)latList[count1],(double)count);
		    	radius = innerRadius + ((count + 90) * interval);
		    	currentPoint = new Point2D.Double((radius * Math.cos(Math.toRadians(noonAlt))),
		    								(radius * Math.sin(Math.toRadians(noonAlt))));
                sb.append("\n").append("newpath");
                sb.append("\n").append(previousPoint.x).append(" ").append(previousPoint.y).append(" moveto");
                sb.append("\n").append(currentPoint.x).append(" ").append(currentPoint.y).append(" lineto stroke");
			    previousPoint = currentPoint;        	    
		    }
		    // Label noon altitude lines
            sb.append("\n").append(noonAlt - 90).append(" rotate");
            sb.append("\n").append("NormalFont5 setfont");
            sb.append("\n").append("0 ").append(outerRadius + 2 ).append(" moveto");
            sb.append("\n").append("(").append(latList[count1]).append(") show");
            sb.append("\n").append(-(noonAlt - 90)).append(" rotate");
	    }

		//Qibla lines
		Location[] cities = getCities();
		for(count1 = 0; count1 < cities.length; count1++){
			Double qiblaAlt = sunsAltitudeAtQiblaAngle(cities[count1],-90.0);
			previousPoint = new Point2D.Double((innerRadius * Math.cos(Math.toRadians(qiblaAlt))),
	    									(innerRadius * Math.sin(Math.toRadians(qiblaAlt))));	// set start	  
		    for (count = -85; count <= 90; count = count + 5){
		    	qiblaAlt = sunsAltitudeAtQiblaAngle(cities[count1],(double)count);
		    	radius = innerRadius + ((count + 90) * interval);
		    	currentPoint = new Point2D.Double((radius * Math.cos(Math.toRadians(qiblaAlt))),
		    								(radius * Math.sin(Math.toRadians(qiblaAlt))));
                sb.append("\n").append("newpath");
                sb.append("\n").append(previousPoint.x).append(" ").append(previousPoint.y).append(" moveto");
                sb.append("\n").append(currentPoint.x).append(" ").append(currentPoint.y).append(" lineto stroke");
			    previousPoint = currentPoint;        	    
		    }
		    // Label Qibla arcs
            sb.append("\n").append(qiblaAlt).append(" rotate");
            sb.append("\n").append("NormalFont5 setfont");
            sb.append("\n").append(outerRadius - 15 ).append(" 0 moveto");
            sb.append("\n").append("(").append(cities[count1].getLocationName()).append(") show");
            sb.append("\n").append(-(qiblaAlt)).append(" rotate");
		}
        out += "\n" + sb.toString();

	    // label arcs with the correct Zodiac symbols
	    out += "\n" + "%% Label Vertical";
	    out += "\n" + "gsave";
		out += "\n" + "-2.5 rotate";
		
	    String[] ZodiacLabels = {"Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius"};
	    String[] ZodiacLabels2 = {"Gemini", "Taurus", "Aries", "Pisces", "Aquarius", "Capricorn"};
	    double y;
	    Double scale = 0.3;
	    
	    sb = new StringBuilder();
	    for(int i = 0; i < 6; i++){
	    	y = outerRadius-9-(i*18);
	    	scale = scale - 0.02;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels[i], new Point2D.Double(0, y), scale, scale));
	    }
        sb.append("\n").append("-5 rotate");
	    scale = 0.3;    
	    for(int i = 0; i < 6; i++){    	
	    	y = outerRadius-9-(i*18);
	    	scale = scale - 0.02;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels2[i], new Point2D.Double(0, y), scale, scale));
	    }
        out += "\n" + sb.toString();
	         
		out += "\n" + "%% End Label Vertical";
		out += "\n" + "grestore";
	
	    out += "\n" + "%% Label Horiz";
	    out += "\n" + "gsave";
		out += "\n" + "2.5 rotate";
		
	    Double x;
	    scale = 0.3;

        sb = new StringBuilder();
	    for(int i = 0; i < 6; i++){    	
	    	x = outerRadius-9-(i*18);
	    	scale = scale - 0.02;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels[5-i], new Point2D.Double(x, 0), scale, scale));
	    }
        sb.append("\n").append("5 rotate");
	    scale = 0.3;    
	    for(int i = 0; i < 6; i++){    	
	    	x = outerRadius-9-(i*18);
	    	scale = scale - 0.02;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels2[5-i], new Point2D.Double(x, 0), scale, scale));
	    }
        out += "\n" + sb.toString();
	         
		out += "\n" + "%% End Label Horiz";
		out += "\n" + "grestore";
	
		return out;
	} 			

	/**
	 * computes and draws the Arcs of the Signs projected
	 * and then computes and adds the Noon Sun lines and the Qibla Lines
	 *
	 * @return eps string
	 */
	public String buildArcsOfSignsProjected(){
	    int count;
	    int count1;
	    String out = "";
	        
	    // compute size of arc that contains the scale.
	    // note eventually this will be done by looking at what rings are drawn and figuring
	    // the remaining radius
	    Double outerRadius = myAstrolabe.getMaterRadius() - 67;
	
		// compute equator line radius - because the size and placement of the arcs scale 
		// will vary, we can't depend on using the myAstrolabe.equator function
		Double radEQ = outerRadius * (Math.tan(Math.toRadians((90 - 23.44) / 2.0)));
		Double innerRadius = radEQ * (Math.tan(Math.toRadians((90 - 23.44) / 2.0)));		
		
		//draw arcs
		out += "\n" + "0 setgray";
		
		Double zodiacPos;
		Double decl;
		Double radius;

        StringBuilder sb = new StringBuilder();
		for (count = -9; count <= 9; count++){
			zodiacPos = (double)count * 10;
			decl = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(23.44))* Math.sin(Math.toRadians(zodiacPos)))); 
			radius = radEQ * Math.tan(Math.toRadians(90 - decl)/2.0);
			if(count == -9 || count == -6 || count == -3 || count == 0 ||
				count == 3 || count == 6 || count == 9){
                sb.append("\n").append("[] 0 setdash"); // set solid line
                sb.append("\n").append("newpath");
                sb.append("\n").append("0 0 ").append(radius).append(" 0 90 arc stroke");
			}else{
                sb.append("\n").append("[1 1] 0 setdash"); // set dashed line
                sb.append("\n").append("newpath");
                sb.append("\n").append("0 0 ").append(radius).append(" 10 80 arc stroke");
			}			 		
		}
        out += "\n" + sb.toString();

		out += "\n" + "[] 0 setdash"; // set solid line
			
		//draw end lines
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";
	    out += "\n" + "newpath";
	    out += "\n" +  "0 " + innerRadius + " moveto";
	    out += "\n" +  "0 " + outerRadius + " lineto stroke";
	    
	    //mark off space for labels (2x5 degrees)
	    out += "\n" + "5 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "5 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "70 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "5 rotate";
	    out += "\n" + "newpath";
	    out += "\n" +  innerRadius + " 0 moveto";
	    out += "\n" +  outerRadius + " 0 lineto stroke";    
	    out += "\n" + "-85 rotate"; // rotate back    
	    
	    // draw Noon altitude lines for 35,40,45,50 deg lat
	    int[] latList = {35,40,45,50}; //target latitudes
	    Double noonAlt;
	    Point2D.Double currentPoint;
        Point2D.Double previousPoint;

        sb = new StringBuilder();
        for (count1 = 0; count1 < latList.length; count1++){
		    noonAlt = AstroMath.sunsNoonAltitude((double)latList[count1],-90.0);
			previousPoint = new Point2D.Double((innerRadius * Math.cos(Math.toRadians(noonAlt))),
	    									(innerRadius * Math.sin(Math.toRadians(noonAlt))));	// set start	    
		    
		    for (count = -85; count <= 90; count = count + 5){
				zodiacPos = (double)-count;
				decl = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(23.44))* Math.sin(Math.toRadians(zodiacPos)))); 
				radius = radEQ * Math.tan(Math.toRadians(90 - decl)/2.0);
		    	noonAlt = AstroMath.sunsNoonAltitude((double)latList[count1],(double)count);
		    	currentPoint = new Point2D.Double((radius * Math.cos(Math.toRadians(noonAlt))),
		    								(radius * Math.sin(Math.toRadians(noonAlt))));
                sb.append("\n").append("newpath");
                sb.append("\n").append(previousPoint.x).append(" ").append(previousPoint.y).append(" moveto");
                sb.append("\n").append(currentPoint.x).append(" ").append(currentPoint.y).append(" lineto stroke");
			    previousPoint = currentPoint;        	    
		    }
		    // Label Noon altitude lines
            sb.append("\n").append(noonAlt - 90).append(" rotate");
            sb.append("\n").append("NormalFont5 setfont");
            sb.append("\n").append("0 ").append(outerRadius + 2 ).append(" moveto");
            sb.append("\n").append("(").append(latList[count1]).append(") show");
            sb.append("\n").append(-(noonAlt - 90)).append(" rotate");
	    }

		//Qibla lines
		Location[] cities = getCities();
		for(count1 = 0; count1 < cities.length; count1++){
			Double qiblaAlt = sunsAltitudeAtQiblaAngle(cities[count1],-90.0);
			previousPoint = new Point2D.Double((innerRadius * Math.cos(Math.toRadians(qiblaAlt))),
	    									(innerRadius * Math.sin(Math.toRadians(qiblaAlt))));	// set start	  
		    for (count = -85; count <= 90; count = count + 5){
		    	zodiacPos = (double)-count;
				decl = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(23.44))* Math.sin(Math.toRadians(zodiacPos)))); 
				radius = radEQ * Math.tan(Math.toRadians(90 - decl)/2.0);
		    	qiblaAlt = sunsAltitudeAtQiblaAngle(cities[count1],(double)count);
		    	currentPoint = new Point2D.Double((radius * Math.cos(Math.toRadians(qiblaAlt))),
		    								(radius * Math.sin(Math.toRadians(qiblaAlt))));
                sb.append("\n").append("newpath");
                sb.append("\n").append(previousPoint.x).append(" ").append(previousPoint.y).append(" moveto");
                sb.append("\n").append(currentPoint.x).append(" ").append(currentPoint.y).append(" lineto stroke");
			    previousPoint = currentPoint;        	    
		    }
		    // Label Qibla lines
            sb.append("\n").append(qiblaAlt).append(" rotate");
            sb.append("\n").append("NormalFont5 setfont");
            sb.append("\n").append(outerRadius - 15 ).append(" 0 moveto");
            sb.append("\n").append("(").append(cities[count1].getLocationName()).append(") show");
            sb.append("\n").append(-(qiblaAlt)).append(" rotate");
		}
        out += "\n" + sb.toString();
		
		// compute radial locations for zodiac symbols use variation of code to project lines
		// Note: each line dividing the symbols is a different distance from its neighbors, so 
		// we need to get the radial position of each divider line and use the half-way point 
		// between each pair.
		Double[] dividerRadialPos = new Double[7];
		Double[] zodiacRadialPos = new Double[7];
		int index = 0;
		for (count = -9; count <= 9; count++){
			zodiacPos = (double)count * 10;
			decl = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(23.44))* Math.sin(Math.toRadians(zodiacPos))));
			radius = radEQ * Math.tan(Math.toRadians(90 - decl)/2.0);
			if(count == -9 || count == -6 || count == -3 || count == 0 ||
				count == 3 || count == 6 || count == 9){
			    dividerRadialPos[index] = radius;
			    index++; 
			} 		
		}
		
		for (count = 0; count < dividerRadialPos.length -1; count++){
			zodiacRadialPos[count] = (((dividerRadialPos[count+1]+dividerRadialPos[count])/2.0));
		}
			
	    // label arcs with the correct Zodiac symbols
	    out += "\n" + "%% Label Vertical";
	    out += "\n" + "gsave";
		out += "\n" + "-2.5 rotate";
		
	    String[] ZodiacLabels = {"Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius"};
	    String[] ZodiacLabels2 = {"Gemini", "Taurus", "Aries", "Pisces", "Aquarius", "Capricorn"};
	    Double y;
	    Double scale = 0.3;

        sb = new StringBuilder();
	    for(int i = 0; i < 6; i++){    	
	    	y = zodiacRadialPos[i];
	    	scale = scale - 0.025;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels[i], new Point2D.Double(0, y), scale, scale));
	    }
        sb.append("\n").append("-5 rotate");
	    scale = 0.3;    
	    for(int i = 0; i < 6; i++){
	    	y = zodiacRadialPos[i];
	    	scale = scale - 0.025;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels2[i], new Point2D.Double(0, y), scale, scale));
	    }
        out += "\n" + sb.toString();
	         
		out += "\n" + "%% End Label Vertical";
		out += "\n" + "grestore";
	
	    out += "\n" + "%% Label Horiz";
	    out += "\n" + "gsave";
		out += "\n" + "2.5 rotate";
		
	    Double x;
	    scale = 0.3;

        sb = new StringBuilder();
	    for(int i = 0; i < 6; i++){
	    	x = zodiacRadialPos[i];
	    	scale = scale - 0.025;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels[5-i], new Point2D.Double(x, 0), scale, scale));
	    }
        sb.append("\n").append("5 rotate");
	    scale = 0.3;    
	    for(int i = 0; i < 6; i++){
	    	x = zodiacRadialPos[i];
	    	scale = scale - 0.025;
            sb.append("\n").append(ZodiacSigns.placeSignAt(ZodiacLabels2[5-i], new Point2D.Double(x, 0), scale, scale));
	    }
        out += "\n" + sb.toString();

		out += "\n" + "%% End Label Horiz";
		out += "\n" + "grestore";	
	
		return out;
	} 

	ArcsOfTheSigns(Astrolabe astrolabeIn){
		myAstrolabe = astrolabeIn;
	}	
}
