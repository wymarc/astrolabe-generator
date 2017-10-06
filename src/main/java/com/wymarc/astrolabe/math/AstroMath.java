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

package com.wymarc.astrolabe.math;

import com.wymarc.astrolabe.Location;

import java.awt.geom.Point2D;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class AstroMath {

    /**
     * normalizes angle to 0 - 360
     * ex: 405 => 45
     * 
     * modified from Morrison
     *
     * @param angle double decimal angle to be normalized
     * @return normalized angle
     */
    public static double normal(double angle){
        while (angle < 0){
        	angle += 360.0;
        }
        return (((angle%360.0)/360.0) * 360.0);
    }

    /**
     * Returns the angle of the line of apsides
     * @param t Julian Century
     * @return angle of the line of apsides
     */
    public static double angleOfLineOfApsides(double t){
        return 180 - (102.937348 + (1.7195269*t) + (0.00045962*(t*t)) + (0.000000499*(t*t*t)));
    }

    /**
     * Computes Julian century (T) from current date
     *
     * modified from Morrison
     *
     * @return Julian century
     */
    public static double getT(){
        Calendar calNow = Calendar.getInstance();

        Calendar calMidniteJan1 = new GregorianCalendar(calNow.get(Calendar.YEAR), 0, 1); // remember January is 0
        Calendar calEpoch = new GregorianCalendar(2000, 0, 1);

        double julianDay = 2451544.5 + ((calMidniteJan1.getTime().getTime()-calEpoch.getTime().getTime())/(1000.0*60.0*60.0*24.0));
        return (julianDay-2451545.0)/36525.0;
    }

    /**
     * Computes Julian day
     *
     * modified from Morrison
     *
     * @param month int month (0-11) 
     * @param day int day of month (1-31)
     * @param year int Year (4 digit)
     * @param ut double time as decimal (ex 1300.15)
     * @return Julian century
     */
    public static double julianDay(int month, int day, int year, double ut){
        int jMonth;
        int jYear;
        double jDay;
        double wv;
        double dayJ;

        month++; // note: in java month is 0-11 we need 1-12

        if (month > 2){
            jMonth = month + 1;
            jYear  = year;
        }else{
            jMonth = month + 13;
            jYear  = year - 1;
        }

        dayJ = day + ut/24.0;
        jDay = integerPart(365.25 * jYear) + integerPart(30.6001 * jMonth) + dayJ + 1720994.5;

        if (jDay <= 2361220){ // if date prior to 9/2/1752 return julian day
            return jDay;
        }else{
            wv = integerPart(0.01 * year);
            return jDay + 2 - wv + integerPart(wv * 0.25);
        }
    }

    /**
     * Computes Eccentricity of Earth's orbit from Julian century
     *
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Eccentricity
     */    
    public static double ecc(double T){
        return ((-1.236E-07 * T - 4.2037E-05) * T + .016708617) ;
    }

    /**
     * Computes Sun's eccentric anomaly ( in radians) from Julian century
     * todo rename
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Sun's eccentric anomaly angle in radians
     */ 
    public static double kepler(double T){
        double m;
        double e0;
        double e;
        double ea;
        m = Math.toRadians(manom(T)) ;
        ea = m ;
        e = ecc(T) ;
        do{
            e0 = ea; ea = m + e * Math.sin(e0);
        }while (Math.abs(ea - e0) > 1.0e-09);
        return (ea);
    }

    /**
     * Computes Sun's True anomaly from eccentric anomaly (degrees)
     * todo change to radians
     * todo check what is input
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Sun's True anomaly angle in degrees
     */
    public static double tanom(double T){
        double ea;
        double e;
        double ta;

        ea = kepler(T);
        e = ecc(T);
        ta = Math.atan(Math.tan(ea / 2.0) * Math.sqrt((1.0 + e) / (1.0 - e)));
        if ((ta < 0.0) || (ta > 0.0 && ea > Math.PI)){
            ta = ta + Math.PI;
        }
        ta = 2.0 * ta;
        if ((ta < Math.PI) && (ea >= 3.0 * (Math.PI/2.0)))
        {
            ta = ta + Math.PI;
        }
        return (Math.toDegrees(ta));
    }

    /**
     * Computes Sun's mean anomaly from T (degrees)
     * todo change to radians
     * todo check what is input
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Sun's mean anomaly angle in degrees
     */
    public static double manom(double T){
        return (normal(((-4.8e-07 * T - .0001559) * T + 35999.0503) * T + 357.5291));
    }

    /**
     * Computes Sun's mean longitude from T (degrees)
     * todo change to radians
     * todo check what is input
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Sun's mean longitude angle in degrees
     */
    public static double mlong (double T){
        return (normal(((.000000021 * T) + .00030368 * T + 36000.7698231) * T + 280.466449)) ;
    }

    /**
     * Computes Sun's geocentric longitude from T (degrees)
     * todo change to radians
     * todo check what is input
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Sun's geocentric longitude angle in degrees
     */
    public static double geolong(double T){
        return (normal(tanom(T) + mlong(T) - manom(T))) ;
    }

    /**
     * Computes Sun's declination from T (degrees)
     * todo change to radians
     * todo check what is input
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Sun's declination angle in radians
     */
    public static double solarDeclination(double T){
        return (Math.toDegrees(Math.asin(Math.sin(Math.toRadians(obliquity(T))) * Math.sin(Math.toRadians(geolong(T))))));
    }

    /**
     * Computes Sun's altitude at local noon for a given date and location
     *
     * @param month int month number (0-11)
     * @param day int day of month number (1-31)
     * @param year int 4 digit year
     * @param latitude of observer in decimal degrees
     * @return  solar altitude at noon in decimal degrees
     */
    public static double solarNoonAltitude(int month, int day, int year, double latitude){
        //Calculate jday for noon of date to get Sun’s noon altitude
        double jday = julianDay( month, day, year, 12.0) ; //Julian day for date
        double T = (jday - 2451545.0) / 36525.0 ; //T for astronomy calc
        //Sun's max altitude for day (in degrees)
        return (90 - latitude + solarDeclination(T));
    }

    /**
     * Computes Sun's altitude at local noon for a given date and location
     *
     * @param dayOfYear int day of year number (0-365)
     * @param year int 4 digit year
     * @param latitude of observer in decimal degrees
     * @return  solar altitude at noon in decimal degrees
     */
    public static double solarNoonAltitude(int dayOfYear, int year, double latitude){
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        gc.set(GregorianCalendar.YEAR, year);
        return solarNoonAltitude(gc.get(GregorianCalendar.MONTH),gc.get(GregorianCalendar.DAY_OF_MONTH),
                year,latitude);
    }

    /**
     * Tests the numbers given and returns true if it is a legal date.
     * Example: 2004-02-15 is legal, 2004-02-31 is not.
     *
     * @param day day of month (1-31)
     * @param month month (1-12)
     * @param year (4 digit)
     * @return validity of date
     */
    public static boolean isLegalDate(int day, int month, int year){
        String dateString = year + "-" + month + "-" + (day + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return sdf.parse(dateString, new ParsePosition(0)) != null;
    }


    // Polar arctangent todo
    public static double polar( double num, double den){
        double ang;
        ang = Math.atan2( num , den );
        if (ang < 0.0)
        {
            ang = ang + 2.0 * Math.PI;
        }
        return ang;
    }

    // todo
    public static int sgn(double num){
        if(num > 0) {
            return 1;
        } else if(num < 0) {
            return -1;
        } else {
            return 0;
        }
    }


    /**
     * Computes Obliquity of ecliptic from T
     * todo change to radians
     * todo check what is input
     * modified from Morrison
     *
     * @param T double Julian century 
     * @return Obliquity of ecliptic angle in degrees
     */
    public static double obliquity(double T){
        return (((5.036111e-07 * T - 1.638889e-07) * T - .013004167) * T + 23.4392911);
    }


    /**
     * Precess star from J2000.0 coordinates to current
     *	
     * modified from Morrison
     *
     * @param ra double right ascension 
     * @param decl double declination
     * @return HashMap(String,Double):
     *		precessedRA
     *		precessedDec
     */
    public static HashMap<String,Double> precess(double ra, double decl){

        //compute T, the julian century
        double T = getT();

        // Precess star from J2000.0 coordinates
        // * Code ported and modified from C precess routine by James Morrison
        //	 published in The Astrolabe: page 347
        double raRad = Math.toRadians(ra * 15.0); //RA radians
        double decRad = Math.toRadians(decl); //Declination to radians
        double zeta = Math.toRadians((((.017998 * T + .30188) * T + 2306.2181) * T) / 3600.0);
        double z = Math.toRadians((((.018203 * T + 1.09468) * T + 2306.2181) * T) / 3600.0);
        double theta = Math.toRadians((((-.041833 * T + -.42665) * T + 2004.3109) * T) / 3600.0);
        double a = Math.cos(decRad) * Math.sin(raRad + zeta);
        double b = Math.cos(theta) * Math.cos(decRad) * Math.cos(raRad + zeta) - Math.sin(theta) * Math.sin(decRad);
        double c = Math.sin(theta) * Math.cos(decRad) * Math.cos(raRad + zeta) + Math.cos(theta) * Math.sin(decRad);

        double precessedRA = normal(Math.toDegrees(z + Math.atan2(a, b)))/15.0 ;
        double precessedDec = Math.toDegrees(Math.asin(c)) ;

        HashMap<String,Double> precessedLocation = new HashMap<String, Double>();
        precessedLocation.put("precessedRA",precessedRA);
        precessedLocation.put("precessedDec",precessedDec);

        return precessedLocation;
    }

    /**
     * Computes the angle of the sun at local noon for a given location and date
     * and then returns it
     *
     * @param   latitude: The latitude of the observer
     * @param   zodiacAngle: the location of the sum on the zodiac rel to Aries 0
     *
     * @return angle
     */
    public static Double sunsNoonAltitude(Double latitude,Double zodiacAngle){
        Double declination = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(23.44)) * Math.sin(Math.toRadians(zodiacAngle))));
        return 90 - latitude + declination;
    }

    /**
     * Computes the great circle azimuth to Mecca(Qibla)for a given location
     *
     * @param   observer: a Location defining the given location
     * @return Qibla direction
     */
    public static Double qiblaAngle(Location observer){
        //Equation taken from page 141 of Morrison's The Astrolabe:
        //
        //						sin(lonQ -lon1)
        //	tan q = ------------------------------------------------
        //			cos lat1 * tan latQ - sin lat1 * cos(lonQ -lon1)
        //
        // where Mecca is (latQ,lonQ) and the observer is (lat1,lon1)
        // note: q is azimuth from south
        //

        //Compute Direction to Mecca(qibla)
        Location mecca = new Location("Mecca",21,25,24,"N",39,49,24,"E"); //212524N0394924E Kaaba Location
        Double lonDiff = mecca.getLongitude() - observer.getLongitude();

        Double a = Math.sin(Math.toRadians(lonDiff));
        Double b = Math.cos(Math.toRadians(observer.getLatitude()))*Math.tan(Math.toRadians(mecca.getLatitude()));
        Double c = Math.sin(Math.toRadians(observer.getLatitude()))*Math.cos(Math.toRadians(lonDiff));

        return  AstroMath.normal(Math.toDegrees(Math.atan(a / ((b) - (c)))));

    }

    /**
     * Computes time correction for location within time zone. time zones are 1 hour wide so the difference 
     * between the suns positon and therefore local solar time will vary 60 minutes across a timezone
     *	
     * @param lonDegree double longitude degrees
     * @param lonMin double longitude minutes
     * @param lonSec double longitude seconds
     * @param lonDir String longitude direction (E/W)
     * @return time correct string
     */
    public static String getTimeCorrection(double lonDegree, double lonMin, double lonSec, String lonDir){
        double longitude = lonDegree + lonMin/60.0 + lonSec/3600.0;
        if(lonDir.equals("W")){
            longitude = -longitude;
        }
        int timeZone;
        double offset;
        int minutes;
        int seconds;
        String correction;

        timeZone = (int)Math.abs(longitude/15.0);
        if((Math.abs(longitude) - (timeZone*15)) > 7.5 ){
            timeZone++;
        }
        offset = (Math.abs(longitude) - (timeZone*15))*4;
        minutes = (int)offset;
        seconds = Math.abs((int)((offset-minutes)*60));
        correction = "Time correction: " + minutes + " Minutes " +  seconds + " Seconds";

        return correction;
    }

//    /**
//     * After Herbert O. Ramp, Equation of Time – Comparison of Approximating Formulae , Compendium of
//     * the North American Sundial Society, Vol. 18, No. 1, pp. 20‐22, March 2011.
//     *
//     * @param dayIn Day of the year (Jan 1 = 1 etc)
//     * @return Time correction for the day of the year in decimal minutes
//     */
//    public static double equationOfTime(int dayIn){
//        double temp = 22.0/7.0*(dayIn*360.0/365.2422-80.535132)/180.0;
//        double minuteCorrection = (
//                (-107.0605*Math.sin(temp))
//                -(428.6697*Math.cos(temp))
//                +(596.1009*Math.sin(2*temp))
//                -(2.0898*Math.cos(2*temp))
//                +(4.4173*Math.sin(3*temp))
//                +(19.2776*Math.cos(3*temp))
//                +(12.7338*Math.sin(4*temp)))/60.0;
//
//        return minuteCorrection; //EqT in minutes
//	}

    /**
     * Calculate the Equation of Time from 'day of year'
     * Originally written by Del Smith, 2016-11-29
     * Form "Equation of Time" Wikipedia entry
     * https://en.wikipedia.org/wiki/Equation_of_time#Calculating_the_equation_of_time
     * Downloaded 03 Oct 2017 and modified
     *
     * @param day represents the day of the year with 0 being January 1
     * @return value for the Equation of Time in decimal minutes
     */
    public static double equationOfTime(int day) {
        double meanVelocity = Math.toRadians(360.0/365.24);    // Mean angular orbital velocity of Earth in radians/day
        double obliquity = Math.toRadians(obliquity(getT())); // Obliquity of the Earth in radians
        double approxAngle = meanVelocity * (day + 10);
        // Angle the Earth would move on its orbit at mean velocity from the December solstice until the day specified
        // 10 comes from the approximate number of days from December solstice to January 1
        double correctedAngle = approxAngle + Math.toRadians(1.914) * Math.sin(meanVelocity * (day - 2));
        // Angle the Earth would move from solstice to specified day, with a correction for Earth's orbital eccentricity
        // 2 is the number of days from January 1 to Earth's perihelion
        // 1.914 deg is a first-order correction for the Earth's orbital eccentricity, 0.0167 (360/pi * 0.0167)
        double angleDifference = Math.atan(Math.tan(correctedAngle) / Math.cos(obliquity));
        // Difference between the angle moved at mean velocity and at the corrected velocity, projected onto equatorial plane
        angleDifference = approxAngle - angleDifference; // Split into multiple lines for better readability
        angleDifference = angleDifference / Math.PI; // Divided by pi to get the value in half-turns
        return 720 * (angleDifference - Math.round(angleDifference)); // Return the Equation of Time in seconds
        // Corrected for any excess integer count half turns
        // 720 comes from the number of minutes it takes for Earth to complete a half turn (12h * 60m)
    }

    /**
     * Returns an array with the points for a circular graph representing the Equation of time
     *
     * @param innerLimit Innermost limit of the graph from the center
     * @param outerlimit Outermost limit of the graph from the center
     * @return An ArrayList of Point2D
     */
    public static ArrayList<Point2D> equationOfTimePoints(double innerLimit, double outerlimit){
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        double scaling = (outerlimit - innerLimit)/34.0; //-17 to 17

        // First compute the angle of the first day of the year to use to align the EOT angle to the calendar ring
        double t = getT();
        double meanVelocity = 360.0/365.24; // Mean angular orbital velocity of Earth in degrees/day
        double offsetAngle = angleOfLineOfApsides(t) + meanVelocity * 2; //January 1 is two days from the perihelion

        for (int i = 0; i < 366; i++){
            //for each day of the current year, compute the eot adjustment in minutes
            double minutes = equationOfTime(i);
            double degrees = normal((i * (360.0/365.0))- offsetAngle);
            double r = ((outerlimit+innerLimit)/2.0) - (minutes * scaling);
            double x = r * Math.cos(Math.toRadians(degrees));
            double y = r * Math.sin(Math.toRadians(degrees));
            points.add(new Point2D.Double(x,y));
        }

        return points;
    }

    /**
     * return integer part of number
     * 25.3345 => 25.0
     *	
     * modified from Morrison
     *
     * @param x double decimal number in todo: is this needed instead of Math.floor?
     * @return integer part of x
     */   
    public static double integerPart(double x)    {
        return ((x >= 0) ? Math.floor(x) : Math.ceil(x)) ;
    }

    /**
     * Defines the Asr line for a sine quadrant.
     * plots one point for each of 90 degrees.
     *
     * Based on the Asr definition from the Maliki, Shafi'i, and Hanbali schools:
     * Asr begins when the length of any vertical gnomon's shadow equals the length
     * of the gnomon itself plus the length of that gnomon's shadow at noon.
     *
     * @param interval
     *
     * @param  multiplier 1 gives the start of Asr (+ the gnomon length), 2 gives the end (+ twice the gnomon length)
     *
     * @return  Array of Point2d objects defining the Asr line
     */
    public static Point2D[] defineAsrLine (double interval, double multiplier){
        Point2D[] lineDef = new Point2D[91];
        for(int i = 0; i <= 90; i++){
            double angle = (double)i;
            lineDef[i] = asrPoint(angle, interval, multiplier);
        }

        return lineDef;
    }

    private static Point2D asrPoint(double meridianAlt, double interval, double multiplier){
        double meridianAltRadians = Math.toRadians(meridianAlt);
        double meridianX = (12.0/Math.sin(meridianAltRadians))* Math.cos(meridianAltRadians);
        double asrX = meridianX + (12.0 * multiplier);
        double asrAltRadians = Math.atan(12/asrX);

        double y = Math.sin(asrAltRadians) * 60.0;
        double x = y/Math.tan(meridianAltRadians);

        if(!(x > 0.0)){
            x = 60.0;
        }

        return new Point2D.Double(x*interval,y*interval);
    }

    /**
     * Determines the altitude of the Sun at the beginning of the Asr prayer hour
     * given the Sun's altitude at local noon.
     *
     * Based on the Asr definition from the Maliki, Shafi'i, and Hanbali schools:
     * Asr begins when the length of any vertical gnomon's shadow equals the length
     * of the gnomon itself plus the length of that gnomon's shadow at noon.
     *
     * @param meridianAlt Sun's altitude at local noon in degrees.
     *
     * @return  Asr angle in degrees
     */
    private static double asrAltitude(Double meridianAlt){
        double meridianAltRadians = Math.toRadians(meridianAlt);

        double meridianX = (12.0/Math.sin(meridianAltRadians))* Math.cos(meridianAltRadians);
        double asrX = meridianX + 12.0;
        double asrAltRadians = Math.atan(12/asrX);

        return Math.toDegrees(asrAltRadians);
    }

    /**
     * Given the year, returns the Dominical letter for the that year
     * @param year  an integer representing the year
     * @return   The Dominical letter or letters for the year
     */
    public static String getDominicalLetter(int year) {
        int century = year / 100;
        int yearOfCentury = year % 100;
        int sum = 2 * century - century / 4 - yearOfCentury - yearOfCentury / 4;
        while (sum < 0)
            sum = sum + 7;
        String letter = Character.toString((char) (sum % 7 + 'A'));
        if (isLeapYear(year))
            letter = Character.toString((char) ((sum + 1) % 7 + 'A')).concat(letter);
        return letter;
    }

    /**
     * Determines if a given year is a leap year
     *
     * @param year an integer representing the year
     * @return true if a leap teat, false if not
     */
    public static boolean isLeapYear(int year) {
        // The year is a leap year if:
        // The year is divisible by 4 but not by 100
        // If the year is divisible by 400

        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
    }
}