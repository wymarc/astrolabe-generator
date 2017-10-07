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

import com.wymarc.astrolabe.Astrolabe;
import com.wymarc.astrolabe.generator.gui.GeneratorGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RetePanel extends JPanel implements ActionListener,MouseListener {
    private JComboBox reteOptionsCombo = null;
    private ThumbNail thumbNail = null;

    public RetePanel() {
        super();
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Rete style:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,10,5,5);
        c.gridx = 0;
        c.gridy = 0;
        optionsPanel.add(label,c);
        c.gridx = 1;
        c.gridy = 0;
        optionsPanel.add(getReteOptionsCombo(),c);

        leftPanel.add(optionsPanel, BorderLayout.NORTH);
        add(leftPanel,BorderLayout.WEST);

        add(getThumbNail(),BorderLayout.EAST);
    }

    public JPanel getThumbNail(){
        if (null == thumbNail){
            thumbNail = new ThumbNail();
        }
        return thumbNail.updateReteThumbNail();
    }

    private JComboBox getReteOptionsCombo(){
        if (null == reteOptionsCombo){
            reteOptionsCombo = new JComboBox(Astrolabe.RETEOPTIONS);
            reteOptionsCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getReteType());
            reteOptionsCombo.setToolTipText("Select type of rete");
            reteOptionsCombo.setActionCommand("Rete");
            reteOptionsCombo.addActionListener(this);
            reteOptionsCombo.addMouseListener(this);
        }
        return reteOptionsCombo;
    }

    public void updateControls(){
        reteOptionsCombo.setSelectedIndex(GeneratorGui.MY_ASTROLABE.getReteType());
        getThumbNail().updateUI();
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Rete")) {
            GeneratorGui.MY_ASTROLABE.setReteType(getReteOptionsCombo().getSelectedIndex());
            getThumbNail().updateUI();
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        String text = "";
        Object obj = e.getSource();
        if (obj instanceof JComboBox){
            text = ((JComboBox) obj).getToolTipText();
        }else if (obj instanceof JCheckBox){
            text = ((JCheckBox) obj).getToolTipText();
        }

        GeneratorGui.setStatusLabel(text);
    }

    public void mouseExited(MouseEvent e) {
        GeneratorGui.setStatusLabel("");
    }
}
