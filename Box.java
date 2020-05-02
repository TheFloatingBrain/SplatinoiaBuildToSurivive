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

//Meant to be used in a GameObject.//
public class Box
{
    protected float width, height;
    protected Color color;
    
    //Convienence.//
    public Box( float width, float height ) {
        Construct( width, height );
    }
    public Box( Color color, float width, float height ) {
        Construct( color, width, height  );
    }
    public Box( Color color ) {
        this.color = color;
    }
    public Box() {}
    
    /*Protected, because no one else needs to see it, final, beacuse
    PositionedBox does not need to overidde it.*/
    protected final void Construct( float width, float height ) {
        this.width = width;
        this.height = height;
    }
    protected final void Construct( Color color, float width, float height ) {
        Construct( width, height );
        this.color = color;
    }

    public final void SetColor(Color color) {
        this.color = color;
    }
    public final void SetHeight( float height ) {
        this.height = height;
    }
    public final void SetWidth( float width ) {
        this.width = width;
    }
    public final Color GetColor() {
        return color;
    }
    public final float GetHeight() {
        return height;
    }
    public final float GetWidth() {
        return width;
    }
}
