package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

import analog.AnalogObserver;
import digital.DigitalObserver;

public class ClocksControllers extends Observable implements ActionListener {
	
	private int hour = 15;
	private int min = 0;
	private int sec = 0;
	
	private Timer timer;
	private int ms = 0;
	
	private boolean hourSelected = false;
	private boolean minSelected = false;
	
	public ClocksControllers() {

		this.addObserver(new AnalogObserver(this));
		this.addObserver(new DigitalObserver(this));
		
		timer = new Timer(100, this);
		timer.start();
		
	}
	
	public void setTime(int hour, int min, int sec) {
		
		// Em ordem
		min = min + (sec/60);
		this.sec = sec % 60;
		
		hour = hour + (min/60);
		this.min = min % 60;
		
		this.hour = hour % 24;
		
		setChanged();
		notifyObservers();
		//System.out.println("hora:" + this.hour + "\t\tmin:" + this.min + "\t\tsec:" + this.sec);
	}
	
	public void plusSec() {
		if (sec+1 < 60)
			this.setTime(hour, min, sec+1);
		else
			this.setTime(hour, min, 0);
	}
	
	public void plusMin() {
		if (min+1 < 60)
			this.setTime(hour, min+1, sec);
		else
			this.setTime(hour, 0, sec);
	}
	
	public void plusHour() {
		this.setTime(hour+1, min, sec);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ms = ms + 100;
		if (ms >= 1000) {
			sec += (ms/1000);
			ms = ms % 1000;
			
			setTime(hour, min, sec);
		}
		
		timer.restart();
		
	}

	public int getHour() {
		return hour;
	}

	public int getMin() {
		return min;
	}

	public int getSec() {
		return sec;
	}
	
	public boolean isHourSelected() {
		return hourSelected;
	}

	public boolean isMinSelected() {
		return minSelected;
	}

	public void setHourSelected(boolean s) {
		this.hourSelected = s;
	}
	
	public void setMinSelected(boolean s) {
		this.minSelected = s;
	}

}
