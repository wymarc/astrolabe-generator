package com.wymarc.astrolabe.generator.gui;

import javax.swing.*;

public class FocusGrabber implements Runnable {
    private JComponent component;

    public FocusGrabber(JComponent component) {
        this.component = component;
    }

    public void run() {
        component.grabFocus();
    }
}
