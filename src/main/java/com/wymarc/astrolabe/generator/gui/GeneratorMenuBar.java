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
package com.wymarc.astrolabe.generator.gui;

import com.wymarc.astrolabe.generator.config.AstrolabeExample;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class GeneratorMenuBar extends JMenuBar {
    public GeneratorMenuBar(GeneratorGui generator) {
        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription("File operations");
        add(menu);

        menuItem = new JMenuItem("Save to folder", KeyEvent.VK_S);
        menuItem.getAccessibleContext().setAccessibleDescription("Save to folder");
        menuItem.addActionListener(generator);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save to Zip", KeyEvent.VK_Z);
        menuItem.getAccessibleContext().setAccessibleDescription("Save to Zip");
        menuItem.addActionListener(generator);
        menu.add(menuItem);

        menuItem = new JMenuItem("Print all", KeyEvent.VK_P);
        menuItem.getAccessibleContext().setAccessibleDescription("Print all");
        menuItem.addActionListener(generator);
        menuItem.setEnabled(GeneratorGui.HAS_PRINTER_SUPPORT);
        menu.add(menuItem);

        menuItem = new JMenuItem("Print current view", KeyEvent.VK_C);
        menuItem.getAccessibleContext().setAccessibleDescription("Print current view");
        menuItem.addActionListener(generator);
        menuItem.setEnabled(GeneratorGui.HAS_PRINTER_SUPPORT);
        menu.add(menuItem);

        menu.addSeparator();
        menu.add( new ExitAction() );

        menu = new JMenu("Predefined Astrolabes");
        menu.setMnemonic(KeyEvent.VK_P);
        menu.getAccessibleContext().setAccessibleDescription("Predefined Astrolabes");
        menuItem.addActionListener(generator);
        add(menu);

        List<AstrolabeExample> astrolabeList = GeneratorGui.MY_CONFIG.getAstrolabeExamples();
        for (AstrolabeExample astrolabe:astrolabeList ){
            menuItem = new JMenuItem(astrolabe.getName());
            menuItem.getAccessibleContext().setAccessibleDescription(astrolabe.getName());
            menuItem.addActionListener(generator);
            menu.add(menuItem);
        }

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menu.getAccessibleContext().setAccessibleDescription("Help");
        menuItem.addActionListener(generator);
        add(menu);

        menuItem = new JMenuItem("About", KeyEvent.VK_A);
        menuItem.getAccessibleContext().setAccessibleDescription("About");
        menuItem.addActionListener(generator);
        menu.add(menuItem);

        menuItem = new JMenuItem("Program Help", KeyEvent.VK_P);
        menuItem.getAccessibleContext().setAccessibleDescription("Program Help");
        menuItem.addActionListener(generator);
        menu.add(menuItem);

        menuItem = new JMenuItem("Astrolabe Resources", KeyEvent.VK_R);
        menuItem.getAccessibleContext().setAccessibleDescription("Astrolabe Resources");
        menuItem.addActionListener(generator);
        menu.add(menuItem);

        menuItem = new JMenuItem("Assembly Instructions");
        menuItem.getAccessibleContext().setAccessibleDescription("Assembly Instructions");
        menuItem.addActionListener(generator);
        menu.add(menuItem);
    }
}
