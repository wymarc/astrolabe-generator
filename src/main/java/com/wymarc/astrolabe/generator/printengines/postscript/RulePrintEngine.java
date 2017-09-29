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
import com.wymarc.astrolabe.generator.printengines.postscript.util.EPSToolKit;

public class RulePrintEngine {

    private Astrolabe myAstrolabe = new Astrolabe();

    /**
     * Draws a single Rule
     *
     * @param   scale    Is the scale wanted?
     * @return  returns the ps code for drawing a single-ended rule
     *
     */
    private String buildSingleRule(Boolean scale, boolean showLabel){
        double ruleLength = myAstrolabe.getInnerLimbRadius() + 8;

        String out = "";
        out += "\n" + "%% ==================== Create Single Rule ====================";
        out += "\n" + "";

        // draw right rule
        out += "\n" + "%% Draw rule";
        out += "\n" + "newpath";
        out += "\n" + "0 25 moveto";
        out += "\n" + (ruleLength-30)+" 25 lineto";
        out += "\n" + ruleLength+" 5 lineto";
        out += "\n" + ruleLength+" 0 lineto";
        out += "\n" + "25 0 lineto";
        out += "\n" + "25 5 lineto";
        out += "\n" + ruleLength+" 5 lineto";
        out += "\n" + "stroke";
        out += "\n" + "";

        // draw rule pivot
        out += "\n" + "%% Mark pivot";
        out += "\n" + "0 0 25 0 360 arc stroke";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";
        out += "\n" + "";

        if (scale)
        {
            // mark rule
            double r;
            int h;
            Boolean label;
            int labelNum;

            for(int i = -22; i <= 50; i++)
            {
                r = myAstrolabe.getEquatorRadius()*(Math.tan(Math.toRadians((90-i)/2.0)));
                if((i == 0)||((i%10) == 0))
                {
                    h = 10;
                    label = true;
                }
                else
                {
                    h = 5;
                    label = false;
                }
                out += "\n" + "newpath";
                out += "\n" + r + " 0 moveto";
                out += "\n" + r + " " + h + " lineto stroke";
                if(label)
                {
                    out += "\n" + "newpath";
                    out += "\n" + "NormalFont6 setfont";
                    out += "\n" + (r-5) + " 10  moveto";                    
				    if (myAstrolabe.getLocation().getLatDir().equals("S"))
				    {
				    	labelNum = -i; //invert scale if southern Hemisphere
				    }else
				    {
				    	labelNum = i;
				    }
                    out += "\n" + "( " + labelNum + ") show";
                }
            }
        }

        if (showLabel){
            // label rule
            out += "\n" + "newpath";
            out += "\n" + "NormalFont20 setfont";
            out += "\n" + 0 + " -55  moveto";
            out += "\n" + "(Single Rule) show";
        }

        out += "\n" + "%% ==================== End Create Single Rule ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws a double Rule
     *
     * @param   scale    Is the scale wanted?
     * @return  returns the ps code for drawing a Double-ended rule
     *
     */
    private String buildDoubleRule(Boolean scale, boolean showLabel){
        double ruleLength = myAstrolabe.getInnerLimbRadius() + 8;

        String out = "";
        out += "\n" + "%% ==================== Create Double Rule ====================";
        out += "\n" + "";

        // draw left rule
        out += "\n" + "newpath";
        out += "\n" + "0 -25 moveto";
        out += "\n" + -(ruleLength-30)+" -25 lineto";
        out += "\n" + -ruleLength+" -5 lineto";
        out += "\n" + -ruleLength+" 0 lineto";
        out += "\n" + "-25 0 lineto";
        out += "\n" + "-25 -5 lineto";
        out += "\n" + -ruleLength+" -5 lineto";
        out += "\n" + "stroke";
        out += "\n" + "";

        // draw right rule
        out += "\n" + "%% Draw rule";
        out += "\n" + "newpath";
        out += "\n" + "0 25 moveto";
        out += "\n" + (ruleLength-30)+" 25 lineto";
        out += "\n" + ruleLength+" 5 lineto";
        out += "\n" + ruleLength+" 0 lineto";
        out += "\n" + "25 0 lineto";
        out += "\n" + "25 5 lineto";
        out += "\n" + ruleLength+" 5 lineto";
        out += "\n" + "stroke";
        out += "\n" + "";

        // draw rule pivot
        out += "\n" + "%% Mark pivot";
        out += "\n" + "0 0 25 0 360 arc stroke";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";
        out += "\n" + "";

        if (scale)
        {
            // mark rule
            double r;
            int h;
            Boolean label;
            int labelNum;

            for(int i = -22; i <= 50; i++)
            {
                r = myAstrolabe.getEquatorRadius()*(Math.tan(Math.toRadians((90-i)/2.0)));
                if((i == 0)||((i%10) == 0))
                {
                    h = 10;
                    label = true;
                }
                else
                {
                    h = 5;
                    label = false;
                }
                out += "\n" + "newpath";
                out += "\n" + r + " 0 moveto";
                out += "\n" + r + " " + h + " lineto stroke";
                if(label)
                {
                    out += "\n" + "newpath";
                    out += "\n" + "NormalFont6 setfont";
                    out += "\n" + (r-5) + " 10  moveto";
				    if (myAstrolabe.getLocation().getLatDir().equals("S"))
				    {
				    	labelNum = -i; //invert scale if southern Hemisphere
				    }else
				    {
				    	labelNum = i;
				    }
                    out += "\n" + "( " + labelNum + ") show";
                }

            }
        }

        if (showLabel){
            // label rule
            out += "\n" + "newpath";
            out += "\n" + "NormalFont20 setfont";
            out += "\n" + 0 + " -55  moveto";
            out += "\n" + "(Double Rule) show";
        }

        out += "\n" + "%% ==================== End Create Double Rule ====================";
        out += "\n" + "";

        return out;
    }

    /**
     * Draws the alidade
     *
     * @param   counterChange    Is the alidade counter-changed?
     * @return  returns the ps code for drawing the alidade
     *
     */
    private String buildAlidade(Boolean counterChange, boolean showLabel){
        double ruleLength = myAstrolabe.getMaterRadius() - 5;

        String out = "";
        out += "\n" + "%% ==================== Create Alidade ====================";
        out += "\n" + "";

        // draw left Alidade
        out += "\n" + "newpath";
        out += "\n" + "0 -25 moveto";
        out += "\n" + -(ruleLength-30)+" -25 lineto";
        out += "\n" + -ruleLength+" -5 lineto";
        out += "\n" + -ruleLength+" 0 lineto";
        out += "\n" + "-25 0 lineto";
        out += "\n" + "-25 -5 lineto";
        out += "\n" + -ruleLength+" -5 lineto";
        out += "\n" + "stroke";
        out += "\n" + "";

        if(counterChange)
        {
            // draw right Alidade arm counterchanged
            out += "\n" + "%% Draw rule";
            out += "\n" + "newpath";
            out += "\n" + "0 25 moveto";
            out += "\n" + (ruleLength-30)+" 25 lineto";
            out += "\n" + ruleLength+" 5 lineto";
            out += "\n" + ruleLength+" 0 lineto";
            out += "\n" + "25 0 lineto";
            out += "\n" + "25 5 lineto";
            out += "\n" + ruleLength+" 5 lineto";
            out += "\n" + "stroke";
            out += "\n" + "";
        }else
        {
            // do not counterchange the Alidade
            out += "\n" + "%% Draw rule";
            out += "\n" + "newpath";
            out += "\n" + "0 -25 moveto";
            out += "\n" + (ruleLength-30)+" -25 lineto";
            out += "\n" + ruleLength+" -5 lineto";
            out += "\n" + ruleLength+" 0 lineto";
            out += "\n" + "25 0 lineto";
            out += "\n" + "25 -5 lineto";
            out += "\n" + ruleLength+" -5 lineto";
            out += "\n" + "stroke";
            out += "\n" + "";
        }

        // draw Alidade pivot
        out += "\n" + "%% Mark pivot";
        out += "\n" + "0 0 25 0 360 arc stroke";
        out += "\n" + "0 0 5 0 360 arc stroke";
        out += "\n" + "newpath";
        out += "\n" + "-5 0 moveto";
        out += "\n" + "5 0 lineto stroke";
        out += "\n" + "newpath";
        out += "\n" + "0 5 moveto";
        out += "\n" + "0 -5 lineto stroke";
        out += "\n" + "";

        // Equation of time scale
        if(myAstrolabe.getShowEquationOfTime()){
            Double outerlimit = myAstrolabe.getMaterRadius() - 80;
            Double innerLimit = outerlimit/4.0;
            int h = 0;
            boolean label = false;
            double scaling = (outerlimit - innerLimit)/34.0;
            for(int i = -17; i < 17; i++){
                if((i == 0)||((i%10) == 0)){
                    h = 10;
                    label = true;
                }else if((i%5) == 0){
                    h = 7;
                    label = true;
                }else{
                    h = 5;
                    label = false;
                }
                double r = ((outerlimit+innerLimit)/2.0) - (i * scaling);
                if(counterChange)
                {
                    out += "\n" + "newpath";
                    out += "\n" + r + " 0 moveto";
                    out += "\n" + r + " " + h + " lineto stroke";

                    if(label){ //todo, southern herishere?
                        out += "\n" + "newpath";
                        out += "\n" + "NormalFont6 setfont";
                        out += "\n" + (r-5) + " 10  moveto";
                        out += "\n" + "( " + i + ") show";
                    }
                }else{
                    out += "\n" + "newpath";
                    out += "\n" + r + " 0 moveto";
                    out += "\n" + r + " " + -h + " lineto stroke";

                    if(label){ //todo, southern herishere?
                        out += "\n" + "newpath";
                        out += "\n" + "NormalFont6 setfont";
                        out += "\n" + (r-5) + " -10  moveto";
                        out += "\n" + "( " + i + ") show";
                    }
                }
            }
        }

        if (showLabel){
            // label on the sheet
            out += "\n" + "newpath";
            out += "\n" + "NormalFont20 setfont";
            out += "\n" + 0 + " -55  moveto";
            out += "\n" + "(Alidade) show";
        }

        out += "\n" + "%% ==================== End Create Alidade ====================";
        out += "\n" + "";

        return out;
    }


    /**
     * Draws the alidade and rules using EPS
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @param   showLabel       is the sheet labeled
     * @return  returns the ps code for drawing the rules and pointers
     *
     */
    public String createRule(Astrolabe myAstrolabeIn, boolean showLabel){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Astrolabe Generator Alidade and Rules");
        out += "\n" + "%% setup";
        out += "\n" + ".1 setlinewidth";
        out += EPSToolKit.setUpFonts();
        out += "\n" + "gsave";
        out += "\n" + "306 110 translate";
        out += "\n" + "";
        out += buildSingleRule(true, false);
        out += "\n" + "";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "306 220 translate";
        out += "\n" + "";
        out += buildSingleRule(false, showLabel);
        out += "\n" + "";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "306 330 translate";
        out += "\n" + "";
        out += buildDoubleRule(true, false);
        out += "\n" + "";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "306 440 translate";
        out += "\n" + "";
        out += buildDoubleRule(false, showLabel);
        out += "\n" + "";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "306 550 translate";
        out += "\n" + "";
        out += buildAlidade(true, false);
        out += "\n" + "";
        out += "\n" + "grestore";
        out += "\n" + "gsave";
        out += "\n" + "306 660 translate";
        out += "\n" + "";
        out += buildAlidade(false, showLabel);
        out += "\n" + "";
        out += "\n" + "grestore";

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }

    /**
     * Draws a sheet of ten alidades using EPS
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @param  counterChange   flag indicating whether or not to counterchange
     * @return  returns the ps code for drawing the rules and pointers     *
     *
     */
    public String buildAlidadeSheet(Astrolabe myAstrolabeIn, Boolean counterChange){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Alidade Sheet");
        out += "\n" + "%% setup";
        out += "\n" + ".1 setlinewidth";

        for(int i = 1; i <= 10; i++)
        {
            out += "\n" + "gsave";
            out += "\n" + "306 " + i*72 + " translate";
            out += "\n" + "";
            out += buildAlidade(counterChange, false);
            out += "\n" + "";
            out += "\n" + "grestore";
        }
        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }

    /**
     * Draws a sheet of 10 rules using EPS
     *
     * @param   myAstrolabeIn    the astrolabe object
     * @param  counterChange   flag indicating whether or not to counterchange
     * @return  returns the ps code for drawing the rules and pointers
     *
     */
    public String buildRulesSheet(Astrolabe myAstrolabeIn, Boolean counterChange){

        myAstrolabe = myAstrolabeIn;

        // Write header to file
        String out = "";
        out += EPSToolKit.getHeader(myAstrolabe,"Rules Sheet");
        out += "\n" + "%% setup";
        out += "\n" + ".1 setlinewidth";
        out += EPSToolKit.setUpFonts();

        for(int i = 1; i <= 10; i++)
        {
            out += "\n" + "gsave";
            out += "\n" + "306 " + i*72 + " translate";
            out += "\n" + "";
            out += buildDoubleRule(counterChange, false);
            out += "\n" + "";
            out += "\n" + "grestore";
        }

        // Write Footer
        out += "\n" + "% Eject the page";
        out += "\n" + "end cleartomark";
        out += "\n" + "showpage";

        return out;
    }

}
