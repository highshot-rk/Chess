/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import src.Chess;
import static src.Chess.main;
import src.controllers.ScreenPrint;
import sun.rmi.runtime.Log;

/*
*  service class for processing mouse event
*/

public class ProcessingMouseEvent implements MouseListener {
    public ScreenPrint screenPrint;

    /*
     * constructor
     */
    public ProcessingMouseEvent(ScreenPrint screenPrint) {
        this.screenPrint = screenPrint;
        screenPrint.addMouseListener(this);
    }

        /*
         * mouse press event
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if(!main.isRunning())
                return;

            if(!screenPrint.bAnimation && screenPrint.play.checkRunning) {
                screenPrint.play.selectLocation(new Screen.Coordinate(e.getX() / ScreenPrint.nScreenSize, e.getY() / ScreenPrint.nScreenSize), screenPrint);
                screenPrint.repaint();   
            }
        }

    @Override
    public void mouseClicked(MouseEvent e) {    	
    }

        @Override
        public void mouseReleased(MouseEvent e) {
        }		

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }
}
