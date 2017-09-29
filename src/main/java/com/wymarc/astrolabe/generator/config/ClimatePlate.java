package com.wymarc.astrolabe.generator.config;

public class ClimatePlate {
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

    private String name;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
