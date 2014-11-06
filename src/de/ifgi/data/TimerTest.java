package de.ifgi.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;






public class TimerTest extends Thread {
	int trees = 0;

	 public void run() {
	int delay = 1000; //milliseconds
	
	  ActionListener taskPerformer = new ActionListener() {
	    

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			trees+=trees+2000;
			System.out.println(trees);
		}
	  };
	  new Timer(delay, taskPerformer).start();
	}
	public static void main(String[] args){
		
		TimerTest test = new TimerTest();
		new Thread(test).start();
	}
}
