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

package com.wymarc.astrolabe;

/**
 * provides a object for describing locations
 */
public class Location
{
    private double latitude;
    private double longitude;
    private String locationName;

	private int latDegrees;
	private int latMinutes;
	private int latSeconds;
	private String latHemisphere;
	private int lonDegrees;
	private int lonMinutes;
	private int lonSeconds;
	private String lonHemisphere;

    /**
     * constructor
     * 
     * @param nameIn String Location name
     * @param lat double decimal latitude
     * @param lon double decimal longitude
     */
	public Location(String nameIn,double lat,double lon){
		latitude = lat;
		longitude = lon;
		
		if(nameIn == null){
			locationName = "";
		}else{
			locationName = nameIn;
		}			
	}
	
    /**
     * constructor
     * 
     * @param nameIn String Location name
     * @param latDeg int latitude degrees
     * @param latMin int latitude minutes
     * @param latSec int latitude seconds
     * @param latDir String latitude direction (N/S)
     * @param lonDeg int longitude degrees
     * @param lonMin int longitude minutes
     * @param lonSec int longitude seconds
     * @param lonDir String longitude direction (N/S)
     */
	public Location(String nameIn,int latDeg,int latMin,int latSec,String latDir,
                    int lonDeg,int lonMin,int lonSec,String lonDir){
		setLocation(nameIn, latDeg, latMin, latSec, latDir, lonDeg, lonMin, lonSec, lonDir);
	}
	
	public Location(){
		latitude = 0.0;
		longitude = 0.0;
		locationName = "";
	}

	public void setLocation(String nameIn,int latDeg,int latMin,int latSec,String latDir,
			 int lonDeg,int lonMin,int lonSec,String lonDir){
		latDegrees = latDeg;
		latMinutes = latMin;
		latSeconds = latSec;
		latHemisphere = latDir.toUpperCase();
		lonDegrees = lonDeg;
		lonMinutes = lonMin;
		lonSeconds = lonSec;
		lonHemisphere = lonDir.toUpperCase();


		latitude = (double)latDeg + ((double)latMin/60) + ((double)latSec/3600);
		longitude = (double)lonDeg + ((double)lonMin/60) + ((double)lonSec/3600);

		// Note: This current defaults to North and East
		if(latDir.toUpperCase().equals("S")){
			latitude = -latitude;
		}
		if(lonDir.toUpperCase().equals("W")){
			longitude = -longitude;
		}

		if(nameIn == null){
			locationName = "";
		}else{
			locationName = nameIn;
		}
	}

	public void setLocation(String locationIn){
		// assumes the location string has been tested for validity (format: ddmmssh dddmmssh)
		latDegrees = Integer.parseInt(locationIn.substring(0, 2));
		latMinutes = Integer.parseInt(locationIn.substring(2, 4));
		latSeconds = Integer.parseInt(locationIn.substring(4, 6));
		latHemisphere = locationIn.substring(6,7);

		lonDegrees = Integer.parseInt(locationIn.substring(8, 11));
		lonMinutes = Integer.parseInt(locationIn.substring(11, 13));
		lonSeconds = Integer.parseInt(locationIn.substring(13, 15));
		lonHemisphere = locationIn.substring(15,16);



		latitude = (double)latDegrees + ((double)latMinutes/60) + ((double)latSeconds/3600);
		longitude = (double)lonDegrees + ((double)lonMinutes/60) + ((double)lonSeconds/3600);

		// Note: This current defaults to North and East
		if(latHemisphere.toUpperCase().equals("S")) {
			latitude = -latitude;
		}
		if(lonHemisphere.toUpperCase().equals("W")){
			longitude = -longitude;
		}
	}

	public void setLocationName(String nameIn){
		locationName = nameIn;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getDisplayString(){
		return convertLatitudeToString(latitude) + ' ' + convertLongitudeToString(longitude);
	}

	private String convertLatitudeToString(double latIn){
		String direction = "N";
		if(latIn < 0){
			direction = "S";
		}
		return convertToDMS(latIn) + direction;
	}

	private String convertLongitudeToString(double lonIn){
		String direction = "E";
		if(lonIn < 0){
			direction = "W";
		}
		String lon = convertToDMS(lonIn) + direction;
		if (lon.length() < 7){ //1202020W
			lon = "00" + lon;
		}else if (lon.length() < 8){
			lon = "0" + lon;
		}
		return lon;
	}

	private String convertToDMS(double d){
		String deg = "";
		String min = "";
		String sec = "";

		d = Math.abs(d);

		//degrees
		Integer i = (int)Math.floor(d);
		if (i < 10){
			deg = "0";
		}
		deg = deg + i;

		//minutes
		d = d - i;
		d = d * 60;
		i = (int)Math.floor(d);
		if (i < 10){
			min = "0";
		}
		min = min + i;

		//seconds
		d = d - i;
		d = d * 60;
		i = (int)Math.floor(d);
		if (i < 10){
			sec = "0";
		}
		sec = sec + i;

		return deg + min + sec;
	}

	public static boolean isValidLocation(String locationIn){
		String regex = "([0-8][0-9]|[9][0])([0-5][0-9]|[6][0])([0-5][0-9]|[6][0])[NS] ([0][0-9][0-9]|[0-1][0-8][0-9]|[1][8][0])([0-5][0-9]|[6][0])([0-5][0-9]|[6][0])[EW]";
		// first check format
		if (!locationIn.matches(regex)){
			return false;
		}
		// the regex does no filter out locations like: 904545N 1804545E so parse and test
		// from the regex we know the format is ddmmssh dddmmssh
		int latDeg = Integer.parseInt(locationIn.substring(0, 2));
		int latMin = Integer.parseInt(locationIn.substring(2, 4));
		int latSec = Integer.parseInt(locationIn.substring(4, 6));

		int lonDeg = Integer.parseInt(locationIn.substring(8, 11));
		int lonMin = Integer.parseInt(locationIn.substring(11, 13));
		int lonSec = Integer.parseInt(locationIn.substring(13,15));

		if ((latDeg == 90) && (latMin > 0 || latSec > 0) ){
			return false;
		}

		if ((lonDeg == 180) && (lonMin > 0 || lonSec > 0) ){
			return false;
		}

		return true;
	}

	public int getLatDeg(){
		return latDegrees;
	}

	public int getLatMin(){
		return latMinutes;
	}

	public int getLatSec(){
		return latSeconds;
	}

	public String getLatDir(){
		return latHemisphere;
	}

	public int getLonDeg(){
		return lonDegrees;
	}

	public int getLonMin(){
		return lonMinutes;
	}

	public int getLonSec(){
		return lonSeconds;
	}

	public String getLonDir(){
		return lonHemisphere;
	}

}
	
