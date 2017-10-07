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
package com.wymarc.astrolabe.generator.gui.tabbedview;

import com.wymarc.astrolabe.generator.AstrolabeGenerator;
import com.wymarc.astrolabe.generator.gui.GeneratorGui;

import javax.swing.*;
import java.awt.*;

public class ThumbNail extends JPanel{
    private JPanel frontThumbnail = null;
    private JPanel backThumbnail = null;
    private JPanel reteThumbnail = null;
    private JPanel quadrantThumbnail = null;

    private int thumbWidth = 0;
    private int thumbHeight = 0;

    private JLabel background = null;
    private JLabel regMarks = null;
    private JLabel reteClassic = null;
    private JLabel reteClassicZodiac = null;
    private JLabel reteModern = null;
    private JLabel throne1 = null;
    private JLabel throne2 = null;
    private JLabel octagon = null;
    private JLabel almucantarDegree_1 = null;
    private JLabel almucantarDegree_2 = null;
    private JLabel almucantarDegree_5 = null;
    private JLabel almucantarDegree_10 = null;
    private JLabel limbAlpha = null;
    private JLabel limbAlphaDegrees = null;
    private JLabel limbArabic = null;
    private JLabel limbArabicDegrees = null;
    private JLabel limbRoman = null;
    private JLabel limbRomanDegrees = null;
    private JLabel azimuthLines = null;
    private JLabel horizons = null;
    private JLabel housesOfHeaven = null;
    private JLabel nauticum = null;
    private JLabel twilightLine = null;
    private JLabel unequalHoursFront = null;
    private JLabel eot = null;
    private JLabel arcsEqual = null;
    private JLabel arcsProjected = null;
    private JLabel shadowSquareLeft = null;
    private JLabel shadowSquareLeftCotan = null;
    private JLabel shadowSquareRight = null;
    private JLabel shadowSquareRightCotan = null;
    private JLabel shadowScaleLeft = null;
    private JLabel shadowScaleRight = null;
    private JLabel backLimb = null;
    private JLabel backLimbZodiac = null;
    private JLabel unequalHoursBoth = null;
    private JLabel coTangentScale = null;
    private JLabel lunarMansions = null;
    private JLabel topLeftSine = null;
    private JLabel topLeftSineCosine = null;
    private JLabel unequalTopLeft = null;
    private JLabel unequalTopRight = null;

    private JLabel simpleHorary = null;
    private JLabel advancedHorary = null;
    private JLabel equalHoursHorary = null;
    private JLabel sineQuadrant = null;
    private JLabel colorSineQuadrant = null;
    private JLabel advancedSine = null;

    public ThumbNail(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // create the misc layers for all front views
        regMarks = createLayer("gui/images/misc/reg.png");
        reteClassic = createLayer("gui/images/misc/rete_classic.png");
        reteClassicZodiac = createLayer("gui/images/misc/rete_classicZ.png");
        reteModern = createLayer("gui/images/misc/rete_modern.png");
        throne1 = createLayer("gui/images/misc/throne.png");
        throne2 = createLayer("gui/images/misc/throne2.png");
        octagon = createLayer("gui/images/misc/octagon.png");
        background = new JLabel();
        background.setBounds(0, 0, thumbWidth, thumbHeight);
        background.setOpaque(true);
        background.setBackground(Color.WHITE);

        // create the layers for the front view
        almucantarDegree_1 = createLayer("gui/images/front/1degree.png");
        almucantarDegree_2 = createLayer("gui/images/front/2degree.png");
        almucantarDegree_5 = createLayer("gui/images/front/5degree.png");
        almucantarDegree_10 = createLayer("gui/images/front/10degree.png");
        limbAlpha = createLayer("gui/images/front/alpha_h.png");
        limbAlphaDegrees = createLayer("gui/images/front/alpha_d.png");
        limbArabic = createLayer("gui/images/front/arabic_h.png");
        limbArabicDegrees = createLayer("gui/images/front/arabic_d.png");
        limbRoman = createLayer("gui/images/front/roman_h.png");
        limbRomanDegrees = createLayer("gui/images/front/roman_d.png");
        azimuthLines = createLayer("gui/images/front/azimuth.png");
        horizons = createLayer("gui/images/front/horizons.png");
        housesOfHeaven = createLayer("gui/images/front/houses.png");
        nauticum = createLayer("gui/images/front/nauticum.png");
        twilightLine = createLayer("gui/images/front/twilight.png");
        unequalHoursFront = createLayer("gui/images/front/unequalfront.png");

        // create the layers for the back view
        eot = createLayer("gui/images/back/eot.png");
        arcsEqual = createLayer("gui/images/back/arc_equal.png");
        arcsProjected = createLayer("gui/images/back/arc_proj.png");
        shadowSquareLeft = createLayer("gui/images/back/b_l_shadow.png");
        shadowSquareLeftCotan = createLayer("gui/images/back/b_l_cotan.png");
        shadowSquareRight = createLayer("gui/images/back/b_r_shadow.png");
        shadowSquareRightCotan = createLayer("gui/images/back/b_r_cotan.png");
        shadowScaleLeft = createLayer("gui/images/back/b_l_horz.png");
        shadowScaleRight = createLayer("gui/images/back/b_r_horz.png");
        backLimb = createLayer("gui/images/back/backlimb.png");
        backLimbZodiac = createLayer("gui/images/back/backlimbZ.png");
        unequalHoursBoth = createLayer("gui/images/back/both_unequal.png");
        coTangentScale = createLayer("gui/images/back/cotan.png");
        lunarMansions = createLayer("gui/images/back/lunar_mansions.png");
        topLeftSine = createLayer("gui/images/back/t_l_sine.png");
        topLeftSineCosine = createLayer("gui/images/back/t_l_sinecos.png");
        unequalTopLeft = createLayer("gui/images/back/t_l_unequal.png");
        unequalTopRight = createLayer("gui/images/back/t_r_unequal.png");

        // create the layers for the quadrant view
        simpleHorary = createLayer("gui/images/quadrants/simplehorary.png");
        advancedHorary = createLayer("gui/images/quadrants/advancedhorary.png");
        equalHoursHorary = createLayer("gui/images/quadrants/equalhourshorary.png");
        sineQuadrant = createLayer("gui/images/quadrants/sinequadrant.png");
        colorSineQuadrant = createLayer("gui/images/quadrants/colorsinequadrant.png");
        advancedSine = createLayer("gui/images/quadrants/advancedsinequadrant.png");

    }

    private JPanel getFrontThumbNail(){
        if (null == frontThumbnail){
            frontThumbnail = new JPanel();
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(thumbWidth, thumbHeight));

            layeredPane.add(background, 0, 0);
            layeredPane.add(regMarks, 0, 0);
            layeredPane.add(octagon, 0, 0);
            layeredPane.add(throne1, 0, 0);
            layeredPane.add(throne2,0, 0);
            layeredPane.add(almucantarDegree_1, 0, 0);
            layeredPane.add(almucantarDegree_2, 0, 0);
            layeredPane.add(almucantarDegree_5, 0, 0);
            layeredPane.add(almucantarDegree_10, 0, 0);
            layeredPane.add(limbAlpha, 0, 0);
            layeredPane.add(limbAlphaDegrees, 0, 0);
            layeredPane.add(limbArabic, 0, 0);
            layeredPane.add(limbArabicDegrees, 0, 0);
            layeredPane.add(limbRoman, 0, 0);
            layeredPane.add(limbRomanDegrees, 0, 0);
            layeredPane.add(azimuthLines, 0, 0);
            layeredPane.add(horizons, 0, 0);
            layeredPane.add(housesOfHeaven, 0, 0);
            layeredPane.add(nauticum, 0, 0);
            layeredPane.add(twilightLine, 0, 0);
            layeredPane.add(unequalHoursFront, 0, 0);

            frontThumbnail.add(layeredPane);
        }
        return frontThumbnail;
    }

    private void blankFrontThumbNail(){
        regMarks.setVisible(false);
        throne1.setVisible(false);
        throne2.setVisible(false);
        octagon.setVisible(false);
        limbAlpha.setVisible(false);
        limbAlphaDegrees.setVisible(false);
        limbArabic.setVisible(false);
        limbArabicDegrees.setVisible(false);
        limbRoman.setVisible(false);
        limbRomanDegrees.setVisible(false);
        almucantarDegree_1.setVisible(false);
        almucantarDegree_2.setVisible(false);
        almucantarDegree_5.setVisible(false);
        almucantarDegree_10.setVisible(false);
        azimuthLines.setVisible(false);
        housesOfHeaven.setVisible(false);
        twilightLine.setVisible(false);
        unequalHoursFront.setVisible(false);
        horizons.setVisible(false);
        nauticum.setVisible(false);
    }

    public JPanel updateFrontThumbNail(){
        frontThumbnail = getFrontThumbNail();
        blankFrontThumbNail();

        if (GeneratorGui.MY_ASTROLABE.getShowRegistrationMarks()){
            regMarks.setVisible(true);
        }

        if (GeneratorGui.MY_ASTROLABE.getFrontPrintOption() != 1){
            if (GeneratorGui.MY_ASTROLABE.getShowThrone() && GeneratorGui.MY_ASTROLABE.getShapeOption() == 0){
                throne1.setVisible(true);
            }else if (GeneratorGui.MY_ASTROLABE.getShowThrone() && GeneratorGui.MY_ASTROLABE.getShapeOption() == 1){
                throne2.setVisible(true);
            }

            if (GeneratorGui.MY_ASTROLABE.getShapeOption() == 1){
                octagon.setVisible(true);
            }

            if(GeneratorGui.MY_ASTROLABE.getHourMarkings() == 0 && GeneratorGui.MY_ASTROLABE.getDegreeScaleType() > 0){ //todo add option for 0-360
                limbRomanDegrees.setVisible(true);
            }else if(GeneratorGui.MY_ASTROLABE.getHourMarkings() == 0){
                limbRoman.setVisible(true);
            }else if(GeneratorGui.MY_ASTROLABE.getHourMarkings() == 1 && GeneratorGui.MY_ASTROLABE.getDegreeScaleType() > 0){
                limbArabicDegrees.setVisible(true);
            }else if(GeneratorGui.MY_ASTROLABE.getHourMarkings() == 1){
                limbArabic.setVisible(true);
            }else if(GeneratorGui.MY_ASTROLABE.getHourMarkings() == 2 && GeneratorGui.MY_ASTROLABE.getDegreeScaleType() > 0){
                limbAlphaDegrees.setVisible(true);
            }else if(GeneratorGui.MY_ASTROLABE.getHourMarkings() == 2){
                limbAlpha.setVisible(true);
            }
        }

        if (GeneratorGui.MY_ASTROLABE.getFrontPrintOption() != 2 && GeneratorGui.MY_ASTROLABE.getFrontPrintOption() != 3){
            if (GeneratorGui.MY_ASTROLABE.getShowHorizonPlate()){
                horizons.setVisible(true);
            }else{
                if(GeneratorGui.MY_ASTROLABE.getAlmucanterInterval() == 1){
                    almucantarDegree_1.setVisible(true);
                }else if(GeneratorGui.MY_ASTROLABE.getAlmucanterInterval() == 2){
                    almucantarDegree_2.setVisible(true);
                }else if(GeneratorGui.MY_ASTROLABE.getAlmucanterInterval() == 5){
                    almucantarDegree_5.setVisible(true);
                }else if(GeneratorGui.MY_ASTROLABE.getAlmucanterInterval() == 10){
                    almucantarDegree_10.setVisible(true);
                }

                if(GeneratorGui.MY_ASTROLABE.getShowAzimuthLines()){
                    azimuthLines.setVisible(true);
                }

                if(GeneratorGui.MY_ASTROLABE.getShowHousesofHeavenLines()){
                    housesOfHeaven.setVisible(true);
                }

                if(GeneratorGui.MY_ASTROLABE.getShowTwilightLines()){
                    twilightLine.setVisible(true);
                }

                if(GeneratorGui.MY_ASTROLABE.getShowUnequalHoursLines()){
                    unequalHoursFront.setVisible(true);
                }
            }
        }

        if (GeneratorGui.MY_ASTROLABE.getFrontPrintOption() == 3){
            nauticum.setVisible(true);
        }

        return frontThumbnail;
    }

    private JPanel getBackThumbNail(){
        if (null == backThumbnail){
            backThumbnail = new JPanel();
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(thumbWidth, thumbHeight));

            layeredPane.add(background, 0, 0);
            layeredPane.add(regMarks, 0, 0);
            layeredPane.add(octagon, 0, 0);
            layeredPane.add(throne1, 0, 0);
            layeredPane.add(throne2, 0, 0);
            layeredPane.add(backLimb, 0, 0);
            layeredPane.add(backLimbZodiac, 0, 0);
            layeredPane.add(arcsEqual, 0, 0);
            layeredPane.add(arcsProjected, 0, 0);
            layeredPane.add(shadowSquareLeft, 0, 0);
            layeredPane.add(shadowSquareLeftCotan, 0, 0);
            layeredPane.add(shadowSquareRight, 0, 0);
            layeredPane.add(shadowSquareRightCotan, 0, 0);
            layeredPane.add(shadowScaleLeft, 0, 0);
            layeredPane.add(shadowScaleRight, 0, 0);
            layeredPane.add(unequalHoursBoth, 0, 0);
            layeredPane.add(coTangentScale, 0, 0);
            layeredPane.add(lunarMansions, 0, 0);
            layeredPane.add(topLeftSine, 0, 0);
            layeredPane.add(topLeftSineCosine, 0, 0);
            layeredPane.add(unequalTopLeft, 0, 0);
            layeredPane.add(unequalTopRight, 0, 0);
            layeredPane.add(eot, 0, 0);

            backThumbnail.add(layeredPane);
        }
        return backThumbnail;
    }

    private void blankBackThumbNail(){
        regMarks.setVisible(false);
        throne1.setVisible(false);
        throne2.setVisible(false);
        octagon.setVisible(false);
        backLimb.setVisible(false);
        backLimbZodiac.setVisible(false);
        arcsEqual.setVisible(false);
        arcsProjected.setVisible(false);
        shadowSquareLeft.setVisible(false);
        shadowSquareLeftCotan.setVisible(false);
        shadowSquareRight.setVisible(false);
        shadowSquareRightCotan.setVisible(false);
        shadowScaleLeft.setVisible(false);
        shadowScaleRight.setVisible(false);
        unequalHoursBoth.setVisible(false);
        coTangentScale.setVisible(false);
        lunarMansions.setVisible(false);
        unequalHoursFront.setVisible(false);
        topLeftSine.setVisible(false);
        topLeftSineCosine.setVisible(false);
        unequalTopLeft.setVisible(false);
        unequalTopRight.setVisible(false);
        eot.setVisible(false);
    }

    public JPanel updateBackThumbNail() {
        backThumbnail = getBackThumbNail();
        blankBackThumbNail();

        if (GeneratorGui.MY_ASTROLABE.getShowRegistrationMarks()){
            regMarks.setVisible(true);
        }

        //"Show Climate and Mater", "Climate only", "Mater only", "Mater and Nauticum"
        if (GeneratorGui.MY_ASTROLABE.getShowThrone() && GeneratorGui.MY_ASTROLABE.getShapeOption() == 0) {
            throne1.setVisible(true);
        } else if (GeneratorGui.MY_ASTROLABE.getShowThrone() && GeneratorGui.MY_ASTROLABE.getShapeOption() == 1) {
            throne2.setVisible(true);
        }

        if (GeneratorGui.MY_ASTROLABE.getShapeOption() == 1) {
            octagon.setVisible(true);
        }

        backLimb.setVisible(true);

        if (GeneratorGui.MY_ASTROLABE.getShowZodiacSymbols()){
            backLimbZodiac.setVisible(true);
        }


        if (GeneratorGui.MY_ASTROLABE.getTopLeft() == 1 && GeneratorGui.MY_ASTROLABE.getTopRight() == 1) {
            unequalHoursBoth.setVisible(true);
        }else {
            if (GeneratorGui.MY_ASTROLABE.getTopLeft() == 1) {
                unequalTopLeft.setVisible(true);
            } else if (GeneratorGui.MY_ASTROLABE.getTopLeft() == 2) {
                topLeftSine.setVisible(true);
            } else if (GeneratorGui.MY_ASTROLABE.getTopLeft() == 3) {
                topLeftSineCosine.setVisible(true);
            }

            if (GeneratorGui.MY_ASTROLABE.getTopRight() == 1) {
                unequalTopRight.setVisible(true);
            } else if (GeneratorGui.MY_ASTROLABE.getTopRight() == 2) {
                arcsEqual.setVisible(true);
            } else if (GeneratorGui.MY_ASTROLABE.getTopRight() == 3) {
                arcsProjected.setVisible(true);
            }
        }

        if (GeneratorGui.MY_ASTROLABE.getBottomLeft() == 1 || GeneratorGui.MY_ASTROLABE.getBottomLeft() == 2 || GeneratorGui.MY_ASTROLABE.getBottomLeft() == 3) {
            if (GeneratorGui.MY_ASTROLABE.getShowCotangentScale()){
                shadowSquareLeftCotan.setVisible(true);
            }else{
                shadowSquareLeft.setVisible(true);
            }
        } else if (GeneratorGui.MY_ASTROLABE.getBottomLeft() == 4) {
            shadowScaleLeft.setVisible(true);
        }

        if (GeneratorGui.MY_ASTROLABE.getBottomRight() == 1 || GeneratorGui.MY_ASTROLABE.getBottomRight() == 2 || GeneratorGui.MY_ASTROLABE.getBottomRight() == 3) {
            if (GeneratorGui.MY_ASTROLABE.getShowCotangentScale()){
                shadowSquareRightCotan.setVisible(true);
            }else{
                shadowSquareRight.setVisible(true);
            }
        } else if (GeneratorGui.MY_ASTROLABE.getBottomRight() == 4) {
            shadowScaleRight.setVisible(true);
        }

        if (GeneratorGui.MY_ASTROLABE.getShowCotangentScale()){
            coTangentScale.setVisible(true);
        }

        if (GeneratorGui.MY_ASTROLABE.getShowLunarMansions()){
            lunarMansions.setVisible(true);
        }

        if (GeneratorGui.MY_ASTROLABE.getShowEquationOfTime()){
            eot.setVisible(true);
        }

        return backThumbnail;
    }

    private JPanel getQuadrantThumbNail(){
        if (null == quadrantThumbnail){
            quadrantThumbnail = new JPanel();
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(thumbWidth, thumbHeight));

            layeredPane.add(simpleHorary, 0, 0);
            layeredPane.add(advancedHorary, 0, 0);
            layeredPane.add(equalHoursHorary, 0, 0);
            layeredPane.add(sineQuadrant, 0, 0);
            layeredPane.add(colorSineQuadrant, 0, 0);
            layeredPane.add(advancedSine, 0, 0);

            quadrantThumbnail.add(layeredPane);
        }
        return quadrantThumbnail;
    }

    private void blankQuadrantThumbNail(){
        simpleHorary.setVisible(false);
        advancedHorary.setVisible(false);
        equalHoursHorary.setVisible(false);
        sineQuadrant.setVisible(false);
        colorSineQuadrant.setVisible(false);
        advancedSine.setVisible(false);
    }

    public JPanel updateQuadrantThumbNail(String hoverObject) {
        quadrantThumbnail = getQuadrantThumbNail();
        blankQuadrantThumbNail();

        if (hoverObject.equals("UnequalHorary")){
            simpleHorary.setVisible(true);
        }else if (hoverObject.equals("AdvancedUnequalHorary")){
            advancedHorary.setVisible(true);
        }else if (hoverObject.equals("EqualHoursHorary")){
            equalHoursHorary.setVisible(true);
        }else if (hoverObject.equals("SineQuadrant")){
            sineQuadrant.setVisible(true);
        }else if (hoverObject.equals("ColorSineQuadrant")){
            colorSineQuadrant.setVisible(true);
        }else if (hoverObject.equals("AdvancedSine")){
            advancedSine.setVisible(true);
        }

        return quadrantThumbnail;
    }

    private JPanel getReteThumbNail(){
        if (null == reteThumbnail){
            reteThumbnail = new JPanel();
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(thumbWidth, thumbHeight));

            layeredPane.add(background, 0, 0);
            layeredPane.add(reteClassic, 0, 0);
            layeredPane.add(reteClassicZodiac, 0, 0);
            layeredPane.add(reteModern, 0, 0);

            reteThumbnail.add(layeredPane);
        }
        return reteThumbnail;
    }

    private void blankReteThumbNail(){
        reteClassic.setVisible(false);
        reteClassicZodiac.setVisible(false);
        reteModern.setVisible(false);
    }

    public JPanel updateReteThumbNail() {
        reteThumbnail = getReteThumbNail();
        blankReteThumbNail();

        if (GeneratorGui.MY_ASTROLABE.getReteType() == 0){
            reteClassic.setVisible(true);
        }else if(GeneratorGui.MY_ASTROLABE.getReteType() == 1){
            reteClassicZodiac.setVisible(true);
        }else if(GeneratorGui.MY_ASTROLABE.getReteType() == 2){
            reteModern.setVisible(true);
        }

        return reteThumbnail;
    }

    private JLabel createLayer(String path){
        ImageIcon icon = createImageIcon(path);
        if (null == icon){
            return null;
        }
        JLabel iconLabel = new JLabel(icon);
        if(thumbWidth == 0){
            thumbWidth = icon.getIconWidth();
            thumbHeight = icon.getIconHeight();
        }
        iconLabel.setBounds(0, 0, thumbWidth, thumbHeight);
        return iconLabel;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = AstrolabeGenerator.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
