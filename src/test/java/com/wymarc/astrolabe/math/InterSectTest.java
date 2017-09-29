package com.wymarc.astrolabe.math;

import junit.framework.TestCase;

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
public class InterSectTest extends TestCase {

    InterSect test = new InterSect(0.0,0.0,10.0,10.0,0.0,10.0); // intersects
    InterSect test1 = new InterSect(0.0,0.0,10.0,25.0,0.0,10.0); // no intersection

    public void testGetXi() throws Exception {

    }

    public void testGetYi() throws Exception {

    }

    public void testGetXi_prime() throws Exception {

    }

    public void testGetYi_prime() throws Exception {

    }

    public void testGetAngle1() throws Exception {

    }

    public void testGetAngle2() throws Exception {

    }

    public void testGetIntersection() throws Exception {
        if (test.getIntersection() && !test1.getIntersection()){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }
}
