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

import javax.swing.*;

public class GeneratorTabbedPane extends JTabbedPane {
    private FrontPanel frontPanel;
    private BackPanel backPanel;
    private RetePanel retePanel;
    private AstrolabeVariationsPanel astrolabeVariationsPanel;
    private ExtrasPanel extrasPanel;
    private QuadrantPanel quadrantPanel;

    public GeneratorTabbedPane(){
        super();
        addTab("Front", null, getFrontPanel(), "Front options");
        addTab("Back", null, getBackPanel(), "Back options");
        addTab("Rete and Rules", null, getRetePanel(), "Rete and Rules options");
        addTab("Astrolabe Variations", null, getAstrolabeVariationsPanel(), "Astrolabe Variations");
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

    public AstrolabeVariationsPanel getAstrolabeVariationsPanel() {
        if(null == astrolabeVariationsPanel){
            astrolabeVariationsPanel = new AstrolabeVariationsPanel();
        }
        return astrolabeVariationsPanel;
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
