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
package com.wymarc.astrolabe;

import javax.swing.*;
import java.util.List;

/**
 * This class exists to serve as a holder for settings and common functions
 */
public class Astrolabe {

    public static final String[] SHOWOPTIONS = { "Climate and mater", "Climate only", "Mater only", "Mater and nauticum"};
    public static final String[] SHAPEOPTIONS = { "Classic", "Octagonal"};
    public static final String[] HOUROPTIONS = { "Roman", "Arabic", "Alphabet"};
    public static final String[] DEGREESCALEOPTIONS = { "None", "0-90", "0-360"};
    public static final String[] ALTITUDEINTERVALOPTIONS = { "1", "2", "5", "10"};
    public static final String[] TOPLEFTOPTIONS = { "Blank", "Unequal hours", "Sine scale", "Both"};
    public static final String[] TOPRIGHTOPTIONS = { "Blank", "Unequal hours", "Arcs of the signs (equal)", "Arcs of the signs (projected)"};
    public static final String[] BOTTOMLEFTOPTIONS = { "Blank", "7 Shadow square", "10 Shadow square", "12 Shadow square", "Horizontal shadow scale"};
    public static final String[] BOTTOMRIGHTOPTIONS = { "Blank", "7 shadow square", "10 shadow square", "12 shadow square", "Horizontal shadow scale"};
    public static final String[] RETEOPTIONS = { "Classic rete", "Classic rete with zodiac", "Modern rete"};


    public static final String[] ROMANHOURS = { "XII", "XI", "X", "IX", "VIII", "VII", "VI", "V", "IIII", "III", "II", "I" };
    public static final String[] S_ROMANHOURS = { "XII", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI" };

    public static final String[] ARABICHOURS = { "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1" };
    public static final String[] S_ARABICHOURS = { "12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };

    public static final String[] ROMAN = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" };
    public static final String[] S_ROMAN = { "I", "XII", "XI", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II" };

    public static final String[] SYMBOLLETTERS = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "V", "X", "Y", "Z" };

    public static final String[] ZODIAC = { "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra",
            "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces" };

    public static final String[] MONTHS = { "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December" };

    public static final int[] MONTHSDAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public static final String[] LUNARHOUSESNAMES = {"Alnath","Allothaim","Achaomazon","Aldebaram",
            "Alchatay","Alhanna","Aldimiach","Alnaza","Archaam","Algelioche","Azobra","Alzarpha",
            "Alhaire","Achureth","Agrapha","Azubene","Alchil","Alchas","Allatha","Abnahaya",
            "Abeda","Sadahacha","Zabadola","Sadabath","Sadalbracha","Alpharg","Alcharya","Albotham" };


    private String userName;                    // name of user
    private Location location;                  // location of astrolabe

    private double plateDiameter;               // diameter of the plate in inches
    private double limbWidth;                   // width of limb in inches
    private int degreeInterval;                 // in degrees
    private int azimuthInterval;                // in degrees
    private int almucanterInterval;             // in degrees
    private int degreeScaleType;                // show/hide Front degree scale
    private boolean showAzimuthLines;           // show/hide azimuth lines
    private boolean showTwilightLines;          // show/hide twilight lines
    private boolean showAllTwilightLines;       // show all three twilight lines
    private boolean showUnequalHoursLines;      // show/hide unequal hour lines on plate
    private boolean showHousesofHeavenLines;    // show/hide houses lines on plate
    private boolean showThrone;          		// print the mater throne
    private int shapeOption;					// selects shape of mater and throne
    private boolean zodiacSymbols;				// Use zodiac symbols on ecliptic instead of labels
    private int hourMarkings;               	// use roman numerals/arabic/alphabet

    private boolean showCosine;                 // show the cosine scale on the sine quadrant scale
    private boolean use100;                     // show the 100 scale as opposed to the 60 scale
    private boolean gridPerDegree;              // show the grid lines for every degree
    private boolean showRadials;                // show the 15 degree radial lines
    private boolean showArcs;                   // show 20 degree arcs
    private boolean showObliqityArc;            // show the obliqity arc

    private boolean showTimeCorrection;		    // Print time correction on back
    private boolean showCotangentScale;		    // Print cotangent scale on back
    private boolean concentricCalendar;         // use the concentric calendar
    private int frontPrintOption;				// 0 print both, 1 print Mater only, 2 print Climate only
    private boolean showHorizonPlate;         	// show horizon plate
    private boolean showLunarMansions;        	// show lunar mansions
    private boolean showEquationOfTime;         // show Equation of Time scale

    private boolean showRegistrationMarks;      // print registation marks for assembly

    private int reteType;     		            // rete type

    private double capricornRadius;             // radius of the tropic of capricorn in points
    private double equatorRadius;               // radius of the equator in points
    private double cancerRadius;                // radius of the tropic of cancer in points
    private double materRadius;                 // radius of the tropic of cancer plus limb width in points
    private double innerLimbRadius;             // radius of the inner limb edge in points
    private double universalLimbRadius;         // radius of the inner limb edge in points for the universal astrolabe
    private double plateGap;            		// width of the space on the edge of the plates in points

    private int topLeft;						// contents of back
    private int topRight;
    private int bottomLeft;
    private int bottomRight;

    private boolean printUniversalAstrolabe;    // print Universal Astrolabe sheets
    private boolean printAlidadeSheet;     	    // print alidadeSheet page
    private boolean printRuleSheet;     		// print ruleSheet page
    private boolean counterChanged;             // print alidadeSheet and ruleSheet counterchanged

    private boolean printBasicHoraryQuadrant;   //print the basic type horary quadrant as an extra
    private boolean printAdvancedHoraryQuadrant;//print the adv type horary quadrant as an extra
    private boolean printEqualHoursHoraryQuadrant;//print the equal hours type horary quadrant as an extra
    private boolean printSineQuadrant;          //print the sine quadrant as an extra
    private boolean printColorSineQuadrant;     //print the colored sine quadrant as an extra
    private boolean printAdvancedSineQuadrant;  //print the advanced sine quadrant as an extra

    private List<JCheckBox> climateSetCheckboxes; // holder for the climate plate set checkboxes

    private String filePath = null;               //holder for session save path

    // rete ecliptic lines
    public int LONGL = 20; // marks between signs
    public int MED = 7;	// 5 degree marks
    public int SHORT = 5; //degree ticks
    public int TENL = 10; // 10 degree marks


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPlateDiameter() {
        return plateDiameter;
    }

    public void setPlateDiameter(double plateDiameter) {
        this.plateDiameter = plateDiameter;
    }

    public double getLimbWidth() {
        return limbWidth;
    }

    public void setLimbWidth(double limbWidth) {
        this.limbWidth = limbWidth;
    }

    public int getDegreeInterval() {
        return degreeInterval;
    }

    public void setDegreeInterval(int degreeInterval){
        this.degreeInterval = degreeInterval;

        if (degreeInterval == 0)
        {
            this.almucanterInterval = 1;
            this.azimuthInterval = 5;
        }else if (degreeInterval == 1)
        {
            this.almucanterInterval = 2;
            this.azimuthInterval = 5;
        }else if (degreeInterval == 2)
        {
            this.almucanterInterval = 5;
            this.azimuthInterval = 5;
        }else
        {
            this.almucanterInterval = 10;
            this.azimuthInterval = 10;
        }
    }

    public int getAzimuthInterval() {
        return azimuthInterval;
    }

    public void setAzimuthInterval(int azimuthInterval) {
        this.azimuthInterval = azimuthInterval;
    }

    public int getAlmucanterInterval() {
        return almucanterInterval;
    }

    public void setAlmucanterInterval(int almucanterInterval) {
        this.almucanterInterval = almucanterInterval;
    }

    public int getDegreeScaleType(){
        return degreeScaleType;
    }

    public void setDegreeScaleType(int degreeScaleType){
        this.degreeScaleType = degreeScaleType;
    }

    public boolean getShowAzimuthLines() {
        return showAzimuthLines;
    }

    public void setShowAzimuthLines(boolean showAzimuthLines) {
        this.showAzimuthLines = showAzimuthLines;
    }

    public boolean getShowTwilightLines() {
        return showTwilightLines;
    }

    public void setShowTwilightLines(boolean showTwilightLines) {
        this.showTwilightLines = showTwilightLines;
    }

    public boolean getShowAllTwilightLines() {
        return showAllTwilightLines;
    }

    public void setShowAllTwilightLines(boolean showAllTwilightLines) {
        this.showAllTwilightLines = showAllTwilightLines;
    }

    public boolean getShowUnequalHoursLines() {
        return showUnequalHoursLines;
    }

    public void setShowUnequalHoursLines(boolean showUnequalHoursLines) {
        this.showUnequalHoursLines = showUnequalHoursLines;
    }

    public boolean getShowHousesofHeavenLines() {
        return showHousesofHeavenLines;
    }

    public void setShowHousesofHeavenLines(boolean showHousesofHeavenLines) {
        this.showHousesofHeavenLines = showHousesofHeavenLines;
    }

    public boolean getShowThrone() {
        return showThrone;
    }

    public void setShowThrone(boolean showThrone) {
        this.showThrone = showThrone;
    }

    public Boolean getShowZodiacSymbols(){
        return this.zodiacSymbols;
    }

    public void setShowZodiacSymbols(Boolean zodiacSymbols){
        this.zodiacSymbols = zodiacSymbols;
    }

    public int getShapeOption(){
        return this.shapeOption;
    }

    public void setShapeOption(int shapeOption){
        this.shapeOption = shapeOption;
    }

    public int getHourMarkings() {
        return hourMarkings;
    }

    public void setHourMarkings(int hourMarkings) {
        this.hourMarkings = hourMarkings;
    }

    public boolean getShowCosine() {
        return showCosine;
    }

    public void setShowCosine(boolean showCosine) {
        this.showCosine = showCosine;
    }

    public boolean getUse100() {
        return use100;
    }

    public void setUse100(boolean use100) {
        this.use100 = use100;
    }

    public boolean getGridPerDegree() {
        return gridPerDegree;
    }

    public void setGridPerDegree(boolean gridPerDegree) {
        this.gridPerDegree = gridPerDegree;
    }

    public boolean getShowRadials() {
        return showRadials;
    }

    public void setShowRadials(boolean showRadials) {
        this.showRadials = showRadials;
    }

    public boolean getShowArcs() {
        return showArcs;
    }

    public void setShowArcs(boolean showArcs) {
        this.showArcs = showArcs;
    }

    public boolean getShowObliqityArc() {
        return showObliqityArc;
    }

    public void setShowObliqityArc(boolean showObliqityArc) {
        this.showObliqityArc = showObliqityArc;
    }

    public boolean getShowTimeCorrection() {
        return showTimeCorrection;
    }

    public void setShowTimeCorrection(boolean showTimeCorrection) {
        this.showTimeCorrection = showTimeCorrection;
    }

    public boolean getShowCotangentScale() {
        return showCotangentScale;
    }

    public void setShowCotangentScale(boolean showCotangentScale) {
        this.showCotangentScale = showCotangentScale;
    }

    public boolean getShowConcentricCalendar() {
        return concentricCalendar;
    }

    public void setShowConcentricCalendar(boolean concentricCalendar) {
        this.concentricCalendar = concentricCalendar;
    }

    public int getFrontPrintOption() {
        return frontPrintOption;
    }

    public void setFrontPrintOption(int frontPrintOption) {
        this.frontPrintOption = frontPrintOption;
    }

    public boolean getShowHorizonPlate() {
        return showHorizonPlate;
    }

    public void setShowHorizonPlate(boolean showHorizonPlate) {
        this.showHorizonPlate = showHorizonPlate;
    }

    public boolean getShowLunarMansions() {
        return showLunarMansions;
    }

    public void setShowLunarMansions(boolean showLunarMansions) {
        this.showLunarMansions = showLunarMansions;
    }

    public boolean getShowEquationOfTime() {
        return showEquationOfTime;
    }

    public void setShowEquationOfTime(boolean showEquationOfTime) {
        this.showEquationOfTime = showEquationOfTime;
    }

    public boolean getShowRegistrationMarks() {
        return showRegistrationMarks;
    }

    public void setShowRegistrationMarks(boolean showRegistrationMarks) {
        this.showRegistrationMarks = showRegistrationMarks;
    }

    public int getReteType() {
        return reteType;
    }

    public void setReteType(int reteType) {
        this.reteType = reteType;
    }

    public double getCapricornRadius() {
        return capricornRadius;
    }

    public void setCapricornRadius(double capricornRadius) {
        this.capricornRadius = capricornRadius;
    }

    public double getEquatorRadius() {
        return equatorRadius;
    }

    public void setEquatorRadius(double equatorRadius) {
        this.equatorRadius = equatorRadius;
    }

    public double getCancerRadius() {
        return cancerRadius;
    }

    public void setCancerRadius(double cancerRadius) {
        this.cancerRadius = cancerRadius;
    }

    public double getMaterRadius() {
        return materRadius;
    }

    public void setMaterRadius(double materRadius) {
        this.materRadius = materRadius;
    }

    public double getUniversalLimbRadius() {
        return universalLimbRadius;
    }

    public double getInnerLimbRadius() {
        return innerLimbRadius;
    }

    public void setInnerLimbRadius(double innerLimbRadius) {
        this.innerLimbRadius = innerLimbRadius;
    }

    public double getPlateGap() {
        return plateGap;
    }

    public void setPlateGap(double plateGap) {
        this.plateGap = plateGap;
    }

    public int getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(int topLeft) {
        this.topLeft = topLeft;
    }

    public int getTopRight() {
        return topRight;
    }

    public void setTopRight(int topRight) {
        this.topRight = topRight;
    }

    public int getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(int bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public int getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(int bottomRight) {
        this.bottomRight = bottomRight;
    }

    public boolean getPrintUniversalAstrolabe() {
        return printUniversalAstrolabe;
    }

    public void setPrintUniversalAstrolabe(boolean printUniversalAstrolabe) {
        this.printUniversalAstrolabe = printUniversalAstrolabe;
    }

    public boolean getPrintAlidadeSheet() {
        return printAlidadeSheet;
    }

    public void setPrintAlidadeSheet(boolean printAlidadeSheet) {
        this.printAlidadeSheet = printAlidadeSheet;
    }

    public boolean getPrintRuleSheet() {
        return printRuleSheet;
    }

    public void setPrintRuleSheet(boolean printRuleSheet) {
        this.printRuleSheet = printRuleSheet;
    }

    public boolean isCounterChanged() {
        return counterChanged;
    }

    public void setCounterChanged(boolean counterChanged) {
        this.counterChanged = counterChanged;
    }

    public boolean getPrintBasicHoraryQuadrant() {
        return printBasicHoraryQuadrant;
    }

    public void setPrintBasicHoraryQuadrant(boolean printBasicHoraryQuadrant) {
        this.printBasicHoraryQuadrant = printBasicHoraryQuadrant;
    }

    public boolean getPrintAdvancedHoraryQuadrant() {
        return printAdvancedHoraryQuadrant;
    }

    public void setPrintAdvancedHoraryQuadrant(boolean printAdvancedHoraryQuadrant) {
        this.printAdvancedHoraryQuadrant = printAdvancedHoraryQuadrant;
    }

    public boolean getPrintEqualHoursHoraryQuadrant() {
        return printEqualHoursHoraryQuadrant;
    }

    public void setPrintEqualHoursHoraryQuadrant(boolean printEqualHoursHoraryQuadrant) {
        this.printEqualHoursHoraryQuadrant = printEqualHoursHoraryQuadrant;
    }

    public boolean getPrintSineQuadrant() {
        return printSineQuadrant;
    }

    public void setPrintSineQuadrant(boolean printSineQuadrant) {
        this.printSineQuadrant = printSineQuadrant;
    }

    public boolean getPrintColorSineQuadrant() {
        return printColorSineQuadrant;
    }

    public void setPrintColorSineQuadrant(boolean printColorSineQuadrant) {
        this.printColorSineQuadrant = printColorSineQuadrant;
    }

    public boolean getPrintAdvancedSineQuadrant() {
        return printAdvancedSineQuadrant;
    }

    public void setPrintAdvancedSineQuadrant(boolean printAdvancedSineQuadrant) {
        this.printAdvancedSineQuadrant = printAdvancedSineQuadrant;
    }

    public List<JCheckBox> getClimateSetCheckboxes() {
        return climateSetCheckboxes;
    }

    public void setClimateSetCheckboxes(List<JCheckBox> climateSetCheckboxes) {
        this.climateSetCheckboxes = climateSetCheckboxes;
    }

    public Location getLocation(){
        return this.location;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Astrolabe(){
        // initialize settings
        this.userName = "Your Name";
        this.location = new Location("Pennsic War",40,58,35,"N",80,8,9,"W");
        this.plateDiameter = 6;
        this.plateGap = 14; //gap on edge of plate
        this.limbWidth = .5;
        this.degreeInterval = 2; // 5 Degrees
        this.azimuthInterval = 5;
        this.almucanterInterval = 5;
        this.degreeScaleType = 0;
        this.showAzimuthLines = true;
        this.showTwilightLines = true;
        this.showAllTwilightLines = false;
        this.showHousesofHeavenLines = true;
        this.showUnequalHoursLines = false;
        this.showThrone = true;
        this.shapeOption = 1;
        this.zodiacSymbols = false;
        this.hourMarkings = 0;

        this.showRegistrationMarks = true;
        this.showTimeCorrection = true;
        this.showCotangentScale = true;
        this.concentricCalendar = false;
        this.showLunarMansions = false;
        this.showEquationOfTime = false;
        this.frontPrintOption = 0; // default to printing both
        this.showHorizonPlate = false;

        this.reteType = 0;

        this.topLeft = 1; // Unequal hours
        this.topRight = 1; // Unequal hours
        this.bottomLeft = 1; // 7 Shadow Square
        this.bottomRight = 2; // 12 Shadow Square
        this.printUniversalAstrolabe = false;
        this.printAlidadeSheet = false;
        this.printRuleSheet = false;
        this.counterChanged = true;

        // todo this needs to have the eps code removed
        this.capricornRadius = ((72 * this.plateDiameter) / 2.0) - this.plateGap;
        this.equatorRadius = this.capricornRadius * (Math.tan(Math.toRadians((90 - 23.44) / 2.0)));
        this.cancerRadius = this.equatorRadius * (Math.tan(Math.toRadians((90 - 23.44) / 2.0)));
        this.materRadius = ((72 * this.plateDiameter) / 2.0) + (this.limbWidth * 72);
        this.innerLimbRadius = (72 * this.plateDiameter) / 2.0;
        this.universalLimbRadius = (72 * (this.plateDiameter + 0.5)) / 2.0;
    }

}
