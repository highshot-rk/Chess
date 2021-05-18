/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controllers;

import src.AllPieces;
import src.Chess;
import src.controllers.ScreenPrint;
/**
 *
 * @author Simkoniv-PC
 */
public class MovePiece {
    public AllPieces.Piece curPiece;
    public double pieceX;
    public double pieceY;		

    /*
     * constructor
     * @param Piece piece
     */
    public MovePiece(AllPieces.Piece piece) {
        this.curPiece = piece;
        this.pieceX = piece.screenCoordinate.screenX * ScreenPrint.nScreenSize;
        this.pieceY = piece.screenCoordinate.screenY * ScreenPrint.nScreenSize;
    }	
}
