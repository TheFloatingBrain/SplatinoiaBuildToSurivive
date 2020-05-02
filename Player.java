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
public final class Player extends GameObject
{
    private static final float SPEED = 8;
    private float jumpInput = 0;
    //Copy constructor.//
    public Player() {}

    public Player( int null_ ) {
        DefaultConstruct();
    }
    @Override
    public final void Init()
    {
        position.SetX( 0 );
        position.SetY( GROUND );
        position.SetZ( 0 );
        Renderer.camera.SetX( position.GetX() - ( float ) Manager.currentWindow.get().getBounds().getCenterX() );
        Renderer.camera.SetY( position.GetY() - ( float ) Manager.currentWindow.get().getBounds().getCenterY() );
        AddBox( new Box( new Color( 0, 0, 255 ), 32, 64 ) );
        AddBox( new Box( new Color( 139, 119, 101 ), 32, 8 ) );
        applyPhysics = false;
        static__ = false;
        collidable = true;
        state = GOState.UPDATE;
        SetID( "Player" );
        health = 100;
    }
    @Override
    public final void Update()
    {
        //Are we colliding with a splatbox? If so loose health.//
        if( idOfCollider.compareTo( "SplatBox".toUpperCase() ) == 0 )
        {
            health -= .5;
            boxes.getFirst().SetColor( new Color( 255, 0, 0 ) );
            idOfCollider = "";
        }
        else
            boxes.getFirst().SetColor( new Color( 0, 0, 255 ) );
        //Are we out of health? If so die.//
        if( health < 0 )
            state = GOState.DES;
        //To place boxes!//
        if( InputHandler.currentMouseInput == Input.MOUSE_LEFT && Manager.blocksLeft > 0 )
        {
            //Limits the number of blocks a player can place.//
            --Manager.blocksLeft;
            GameObject.AddGameObject( new PlaceableBox( 0 ) );
            InputHandler.currentMouseInput = Input.NONE;
        }
        //So the player never goes underground.//
        if( position.GetY() > GROUND )
            position.SetY( GROUND );
        //Move the player.//
        if( InputHandler.currentKeyInput == Input.LEFT )
            position.SetX( position.GetX() + SPEED );
        if( InputHandler.currentKeyInput == Input.RIGHT )
            position.SetX( position.GetX() - SPEED );
        if( InputHandler.currentKeyInput == Input.UP && jumpInput == 0 )
            jumpInput += .0001;
        //Jump!//
        if( jumpInput != 0 && colliding == SolidManager.NO_COLLISION )
        {
            //It will reach its starting point at: 3.1416//
            //*cough* requirement *cough*//
            position.SetY( position.GetY() - ( float ) ( 
                    Math.sin( ( double ) jumpInput ) * SPEED * 2 ) );
            jumpInput += .22;
            if( jumpInput >= 6.28 )
                jumpInput = 0;
        }
        /*Make the camera follow the player.
        This is done after the player moves so
        the camera moves with the latest information.*/
        Renderer.camera.SetX( position.GetX() - ( float ) Manager.currentWindow.get().getBounds().getCenterX() );
        Renderer.camera.SetY( position.GetY() - ( float ) Manager.currentWindow.get().getBounds().getCenterY() );

    }

    @Override
    public void Des() {
    }
}
