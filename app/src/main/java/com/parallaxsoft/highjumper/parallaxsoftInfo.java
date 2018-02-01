package com.parallaxsoft.highjumper;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class parallaxsoftInfo extends Activity{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Toast.makeText(getApplicationContext(), "names and logos are clickable", Toast.LENGTH_LONG).show();
    }
    
    public void parallaxsoftvisitWebsite(View view) {
    	Intent parallaxsoftbrowserIntent = null;
    	
    	switch (view.getId()) {
    	case R.id.buttonRunnersHigh:
			parallaxsoftbrowserIntent = new Intent("android.intent.action.VIEW", Uri.parse(parallaxsoftSettings.parallaxsoftURL_RUNNERSHIGH));
			break;
		default:
			Log.e("RunnersHigh", "unexpected buttonclick");
			break;
		}
    	
    	if (parallaxsoftbrowserIntent != null) startActivity(parallaxsoftbrowserIntent);
    }
}
