/*
Project: Hand of Destiny
*/

import java.util.ArrayList;

public class WaveTracker implements Comparable<WaveTracker> {

    private int wave; //wave number

    //constructor
    public WaveTracker(int wave){
        this.wave = wave;
    }
    public WaveTracker(){
        this.wave = 1;
    }

    //setter
    public void setWave(int wave) {
        this.wave = wave;
    }
    //getter
    public int getWave() {
        return wave;
    }

    //this increments the wave count by 1
    public int waveincrease(ArrayList<Integer> wave){
        if (wave.get(1) == 1){
            return (getWave() + 1);
        }

        return getWave();
    }

    //gets used in Gameutil to compare highscores
    @Override
    public int compareTo(WaveTracker other) { 
        return Integer.compare(this.wave, other.getWave());
    }
}
