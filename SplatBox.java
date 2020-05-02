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
public class SplatBox extends GameObject
{
    private float jumpInput;
    //Copy constructor.//
    public SplatBox() {}

    public SplatBox( int null_ ) {
        DefaultConstruct();
    }
    @Override
    public void Init()
    {
        jumpInput = 0;
        int player = FindObjectByID( "player" );
        float y = GROUND;
        if( player != NO_ID )
            y = gameObjects.get( player ).GetPosition().GetY();
        position.SetX( gameObjects.get( player ).GetPosition().GetX() + ( float ) ( Math.sin( Math.random() * 10 ) * Math.random() * 1000 ) );
        position.SetY( y - 20 );//( GROUND );
        position.SetZ( 0 );
        AddBox( new Box( new Color( 0, 255, 0 ), 128, 64 ) );
        applyPhysics = true;
        static__ = false;
        collidable = true;
        state = GOState.UPDATE;
        SetID( "SplatBox" );
    }

    @Override
    public void Update()
    {
        int player = FindObjectByID( "player" );
        if( player != NO_ID )
        {
            if( gameObjects.get( player ).GetPosition().GetX() > position.GetX() )
                position.SetX( position.GetX() - 1 );
            else
                position.SetX( position.GetX() - 1 );
        }
        //Jump!//
        //It will reach its starting point at: 3.1416//
        //*cough* requirement *cough*//
        position.SetY( position.GetY() - ( float ) ( 
               Math.sin( ( double ) jumpInput ) * 64 ) );
        jumpInput += .22;
        if( jumpInput >= 6.28 )
        jumpInput = 0;
     }

    @Override
    public void Des() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
