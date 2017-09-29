package com.wymarc.astrolabe.generator.gui.tabbedview;

import javax.swing.*;

public class GeneratorTabbedPane extends JTabbedPane {
    /**
     * $Id: AstrolabeGenerator,v 3.0
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
     * Copyright (c) 2014, 2015, 2016, 2017 Timothy J. Mitchell
     */
    private FrontPanel frontPanel;
    private BackPanel backPanel;
    private RetePanel retePanel;
    private ExtrasPanel extrasPanel;
    private QuadrantPanel quadrantPanel;

    public GeneratorTabbedPane(){
        super();
        addTab("Front", null, getFrontPanel(), "Front options");
        addTab("Back", null, getBackPanel(), "Back options");
        addTab("Rete and Rules", null, getRetePanel(), "Rete and Rules options");
        addTab("Quadrants", null, getQuadrantPanel(), "Quadrants options");
        addTab("Extras", null, getExtrasPanel(), "Extras to add");
    }

    public FrontPanel getFrontPanel() {
        if(null == frontPanel){
            frontPanel = new FrontPanel();
        }
        return frontPanel;
    }

    public BackPanel getBackPanel() {
        if(null == backPanel){
            backPanel = new BackPanel();
        }
        return backPanel;
    }

    public RetePanel getRetePanel() {
        if(null == retePanel){
            retePanel = new RetePanel();
        }
        return retePanel;
    }

    public ExtrasPanel getExtrasPanel() {
        if(null == extrasPanel){
            extrasPanel = new ExtrasPanel();
        }
        return extrasPanel;
    }

    public QuadrantPanel getQuadrantPanel() {
        if(null == quadrantPanel){
            quadrantPanel = new QuadrantPanel();
        }
        return quadrantPanel;
    }

    public void updateControls(){
        //todo
        frontPanel.updateControls();
        backPanel.updateControls();
        retePanel.updateControls();
    }
}
