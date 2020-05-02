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

/**
 *
 * @author christophergreeley
 */
public class XYZ
{
    //In case I want to inherit later.//
    protected float x, y, z;

    public XYZ( float x_, float y_, float z_ ) {
        Construct( x_, y_, z_ );
    }
    
    public XYZ() {}
    
    protected final void Construct( float x_, float y_, float z_ )
    {
        x = x_;
        y = y_;
        z = z_;
    }
    
    public final float GetX() {
        return x;
    }
    public final float GetY() {
        return y;
    }
    public final float GetZ() {
        return z;
    }
    public final void SetX( float x ) {
        this.x = x;
    }
    public final void SetY( float y ) {
        this.y = y;
    }
    public final void SetZ( float z ) {
        this.z = z;
    }
}
