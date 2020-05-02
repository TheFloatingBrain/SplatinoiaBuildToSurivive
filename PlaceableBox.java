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
public final class PlaceableBox extends GameObject
{
    //Copy constructor.//
    public PlaceableBox() {}

    public PlaceableBox( int null_ ) {
        DefaultConstruct();
    }
    @Override
    public void Init()
    {
        //Set the objects position to where the mouse is relitibe to the camera.//
        position.SetX( InputHandler.mousePosition.GetX() + Renderer.camera.GetX() );
        position.SetY( InputHandler.mousePosition.GetY() + Renderer.camera.GetY() );
        position.SetZ( 0 );
        //Add a renderable box.//
        AddBox( new Box( Color.DARK_GRAY, 64, 64 ) );
        applyPhysics = false;
        static__ = true;
        collidable = true;
        SetID( "PlaceableBox" );
        health = 3;
    }

    @Override
    public void Update()
    {
        //Are we colliding with a splatbox? If so loose health.//
        if( idOfCollider.compareTo( "SplatBox".toUpperCase() ) == 0 )
        {
            health -= .1;
            boxes.getFirst().SetColor( new Color( 255, 0, 0 ) );
            idOfCollider = "";
        }
        else
            boxes.getFirst().SetColor( Color.DARK_GRAY );
        //Are we out of health? If so die.//
        if( health < 0 )
            state = GOState.DES;
        if( InputHandler.currentMouseInput == Input.MOUSE_RIGHT )
        {
            float mouseX = ( InputHandler.mousePosition.GetX() + Renderer.camera.GetX() );
            float mouseY = ( InputHandler.mousePosition.GetY() + Renderer.camera.GetY() );
            if( mouseX < ( position.GetX() + boxes.getFirst().GetWidth() ) && 
                    mouseX > position.GetX() )
            {
                if( mouseY < ( position.GetY() + boxes.getFirst().GetHeight() ) && 
                    mouseY > position.GetY() ) {
                        state = GOState.DES;
                        ++Manager.blocksLeft;
                }
            }
        }
    }

    @Override
    public void Des() {
    }
    
}
