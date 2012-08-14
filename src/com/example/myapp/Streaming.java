package com.example.myapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
public class Streaming{
	
    MediaPlayer mediaPlayer;
    Boolean firstClick = true;
    Boolean onPause = true;
    
    public void buttonAction(){
    	if(firstClick && onPause){
            firstClick=false;
            playStreaming();
        	}
            else if (!firstClick && onPause){
            	onPause = false;
            	play();
            }
            else{
            	pauseStreaming();
            	onPause = true;
        }
    }
	
	private void playStreaming() {
    	String mFile = "http://67.212.166.178:8092/";
    	mediaPlayer = new MediaPlayer();
          
	    try {	        
	    	mediaPlayer.setDataSource(mFile);
	    	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    	mediaPlayer.prepareAsync();
           
	    	mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
            
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();  
            }});         
          } 
          catch (Exception e) {           
          }	        
    }
    
    public void pauseStreaming(){
    	mediaPlayer.pause();
    }
    
    public void play(){	        
    	mediaPlayer.start();
    }        
}
