/*
   This file is part of Splatinoia: Build To Survive.

    Splatinoia: Build To Survive is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Splatinoia: Build To Survive is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Splatinoia: Build To Survive.  If not, see <http://www.gnu.org/licenses/>.

        Copyright 2012 Christopher Greeley
 */

import java.awt.Color;

/**
 *
 * @author christophergreeley
 */
//Meant to be used on its own.//
public final class PositionedBox extends Box
{
    private XYZ position;
    
    //Convienence.//
    public PositionedBox( float width, float height ) {
        Construct( width, height );
    }
    public PositionedBox( Color color, float width, float height ) {
        Construct( color, width, height  );
    }
    public PositionedBox( Color color ) {
        this.color = color;
    }
    public PositionedBox( XYZ position ) {
        this.position = position;
    }
    public PositionedBox( float width, float height, XYZ position ) {
        Construct( width, height, position );
    }
    public PositionedBox( Color color, float width, float height, XYZ position ) {
        Construct( color, width, height, position );
    }
    public PositionedBox() {}
    
    
    /*Private because no further classes will derive from this.
     Non - final because this class is final.
     Just like this is the FINAL PROGRAM!!!*/
    private void Construct( float width, float height, XYZ position ) {
        Construct( width, height );
        this.position = position;
    }
    private void Construct( Color color, float width, float height, XYZ position ) {
        Construct( color, width, height );
        this.position = position;
    }

    public void SetPosition( XYZ position ) {
        this.position = position;
    }
    public XYZ GetPosition() {
        return position;
    }
}
