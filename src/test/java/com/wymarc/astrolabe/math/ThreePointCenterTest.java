package com.wymarc.astrolabe.math;

import junit.framework.TestCase;

import java.awt.geom.Point2D;

/**
 * Astrolabe Generator
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
 * Copyright (c) 2013 Timothy J. Mitchell
 *
 * @author Timothy J. Mitchell (aka Richard Wymarc)
 * @version 4.0
 */
public class ThreePointCenterTest extends TestCase {

    ThreePointCenter test;

    public void setUp() throws Exception {
        super.setUp();
        Point2D.Double linePoint1 = new Point2D.Double(-10.0,0.0);
        Point2D.Double linePoint2 = new Point2D.Double(0.0,0.0);
        Point2D.Double linePoint3 = new Point2D.Double(10.0,0.0);

        test = new ThreePointCenter(linePoint1,linePoint2,linePoint3);
    }

    public void testGetCenter() throws Exception {

    }

    public void testGetRadius() throws Exception {

    }

    public void testIsCircle() throws Exception {
        assertTrue(!test.isCircle());
    }
}
