package com.parallaxsoft.highjumper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class parallaxsoftMenu extends Activity {
	MediaPlayer menuLoop;
	private Toast parallaxsoftloadMessage;
	private Runnable parallaxsoftgameLauncher;
	private Intent parallaxsoftgameIntent;
	private Handler parallaxsoftmHandler;
	private android.widget.Button parallaxsoftmPlayButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
		parallaxsoftloadMessage = Toast.makeText(getApplicationContext(), "loading game...", Toast.LENGTH_SHORT );
		parallaxsoftloadMessage.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		parallaxsoftgameIntent = new Intent (this, parallaxsoftmain.class);
		parallaxsoftmPlayButton = (android.widget.Button)findViewById(R.id.startButton);
		parallaxsoftmPlayButton.setClickable(true);
		parallaxsoftmPlayButton.setEnabled(true);
		parallaxsoftgameLauncher = new Runnable() {
			
			public void run() {
				parallaxsoftmPlayButton.setClickable(false);
		    	parallaxsoftmPlayButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
				startActivityForResult(parallaxsoftgameIntent, 0);
			}
		};
		
		parallaxsoftmHandler = new Handler();
    }
    
    public void parallaxsoftplayGame(View view) {
		parallaxsoftloadMessage.show();
    	parallaxsoftSettings.SHOW_FPS = false;
    	parallaxsoftmHandler.post(parallaxsoftgameLauncher);
    }
    
    public void parallaxsoftplayGameWithFPS(View view) {
		parallaxsoftloadMessage.show();
    	parallaxsoftSettings.SHOW_FPS = true;
    	parallaxsoftmHandler.post(parallaxsoftgameLauncher);
    }
    
    public void parallaxsoftshowScore(View view) {
    	Intent myIntent = new Intent (this, parallaxsoftHighScoreActivity.class);
    	startActivity (myIntent);
    }
    
    public void parallaxsoftshowInfo(View view) {
    	Intent myIntent = new Intent (this, parallaxsoftInfo.class);
    	startActivity (myIntent);
    }
    
    protected void parallaxsoftonActivityResult (int requestCode, int resultCode, Intent data) {
    	if (resultCode == 1) {
    		showDialog(1);
    		parallaxsoftmHandler.postDelayed(new Runnable() {
				
				public void run() {
					parallaxsoftmPlayButton.setClickable(true);
					parallaxsoftmPlayButton.getBackground().clearColorFilter();
				}
			}, 10000);
    	} else {
    		parallaxsoftmPlayButton.setClickable(true);
    		parallaxsoftmPlayButton.getBackground().clearColorFilter();
    	}
    	
    }
    
    public void donate(View view) {
    	Intent parallaxsoftbrowserIntent = new Intent("android.intent.action.VIEW", Uri.parse(parallaxsoftSettings.parallaxsoftURL_DONATE));
    	startActivity(parallaxsoftbrowserIntent);
    }
    
    protected Dialog onCreateDialog(int id) {
    	return new AlertDialog.Builder(this)
		  .setTitle("Error while changing view")
		  .setMessage("System needs some time to free memory. Please try again in 10 seconds.")
		  .setCancelable(true)
		  .create();
    }
}
