/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


	
public class Logger{
    public String path = "log.txt";
    public Logger() {

    }

    public void setLoger(String text) throws IOException {
        File file = new File(this.path);
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        PrintWriter pr = new PrintWriter(br);
        pr.println(text);
        pr.close();
        br.close();
        fr.close();
    }
}