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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author christophergreeley
 */
public class InputHandler implements KeyListener, MouseListener
{
    public static int button;
    public static Input currentKeyInput;
    public static Input currentMouseInput;
    public static XYZ mousePosition;
    
    public InputHandler() {
    }

    public InputHandler( boolean null_ )
    {
        if( null_ == true )
        {
            mousePosition = new XYZ();
            button = MouseEvent.NOBUTTON;
            currentKeyInput = Input.NONE;
            currentMouseInput = Input.NONE;
        }
    }
    //Get keyboard input.//
    @Override
    public void keyPressed( KeyEvent event )
    {
        int keyCode = event.getKeyCode();
        //For some odd reason, VK_RIGHT is left and vise versa...//
        if( keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D )
            currentKeyInput = Input.LEFT;
        if( keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A )
            currentKeyInput = Input.RIGHT;
        if( keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W )
            currentKeyInput = Input.UP;
        if( keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S )
            currentKeyInput = Input.DOWN;
        if( keyCode == KeyEvent.VK_H )
            currentKeyInput = Input.CLOSE_BOX;
    }
    //Say "there is no key down".//
    @Override
    public void keyReleased( KeyEvent ke ) {
        currentKeyInput = Input.NONE;
    }
    //Get mouse input.//
    @Override
    public void mousePressed( MouseEvent event )
    {
        button = event.getButton();
        if( button == 1 )
            currentMouseInput = Input.MOUSE_LEFT;
        else
            currentMouseInput = Input.MOUSE_RIGHT;
        mousePosition.SetX( event.getX() );
        mousePosition.SetY( event.getY() );
        mousePosition.SetZ( 0 );

    }
    //Say "there is no mouse button down.//
    @Override
    public void mouseReleased( MouseEvent event ) {
        button = MouseEvent.NOBUTTON;
    }
    
    //Uneeded but nessisary.//
    @Override
    public void keyTyped( KeyEvent ke ) {
    }
    @Override
    public void mouseClicked( MouseEvent event ) {   
    }
    @Override
    public void mouseEntered( MouseEvent event ) {
    }
    @Override
    public void mouseExited( MouseEvent event ) {
    }
}
