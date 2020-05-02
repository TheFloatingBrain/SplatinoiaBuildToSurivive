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
public enum Input
{
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT,
    MOUSE_LEFT,
    MOUSE_RIGHT,
    CLOSE_BOX;
    @Override
    public String toString()
    {
        if( this == UP )
            return "UP";
        if( this == DOWN )
            return "DOWN";
        if( this == LEFT )
            return "LEFT";
        if( this == RIGHT )
            return "RIGHT";
        if( this == MOUSE_LEFT )
            return "MOUSE_LEFT";
        if( this == MOUSE_RIGHT )
            return "MOUSE_RIGHT";
        if( this == CLOSE_BOX )
            return "CLOSE_BOX";
        return "NONE";
    }
}
