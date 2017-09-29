package com.wymarc.astrolabe.math;

/**
 * Created with IntelliJ IDEA.
 * User: tim.mitchell
 * Date: 4/2/13
 * Time: 6:58 AM
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.TestCase;

public class AstroMathTest extends TestCase {
    public void testNormal() throws Exception {
        assertEquals("377 normalizes to 17", 17.0, AstroMath.normal(377.0));
        assertEquals("-377 normalizes to 343", 343.0, AstroMath.normal(-377.0));
        assertEquals("270 normalizes to 270", 270.0, AstroMath.normal(270.0));
    }

    public void testIntegerPart() throws Exception {
        assertEquals("377.0 returns 377.0", 377.0, AstroMath.integerPart(377.0));
        assertEquals("481.43 normalizes to 481", 481.0, AstroMath.integerPart(481.43));
        assertEquals("481.53 normalizes to 481", 481.0, AstroMath.integerPart(481.53));
    }
}
