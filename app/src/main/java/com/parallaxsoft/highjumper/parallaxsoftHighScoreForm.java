package com.parallaxsoft.highjumper;
import java.io.IOException;
import android.view.View.OnKeyListener;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.parallaxsoft.highscore.parallaxsoftmHighscoreAdapterParallaxsoft;

public class parallaxsoftHighScoreForm extends Activity {
	
	private parallaxsoftmHighscoreAdapterParallaxsoft parallaxsofthighScoreAdapter = null;
	private EditText parallaxsoftparallaxsoftisOnline;
	private TextView parallaxsoftscoreField;
	private Integer score;
	private CheckBox parallaxsoftcheckboxPushOnline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscoreform);
        
        parallaxsofthighScoreAdapter = new parallaxsoftmHighscoreAdapterParallaxsoft(this);
        parallaxsofthighScoreAdapter.open();
        parallaxsoftparallaxsoftisOnline = (EditText) findViewById(R.id.title);
        parallaxsoftparallaxsoftisOnline.setSingleLine(true);
        parallaxsoftparallaxsoftisOnline.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(
							Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				return false;
			}
		});
        
        parallaxsoftscoreField = (TextView) findViewById(R.id.score);
        parallaxsoftcheckboxPushOnline = (CheckBox) findViewById(R.id.postOnline);
        Button parallaxsoftconfirmButton = (Button) findViewById(R.id.confirm);
        parallaxsoftconfirmButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		parallaxsoftsaveState();
        	}
        });
        if(parallaxsoftisOnline())
        	parallaxsoftcheckboxPushOnline.setChecked(true);
        score = (savedInstanceState == null) ? null : (Integer) savedInstanceState.getSerializable("score");
		if (score == null) {
			Bundle extras = getIntent().getExtras();
			score = extras != null ? extras.getInt("score") : null;
		}
		parallaxsoftscoreField.setText(score.toString());
		Cursor cursor = parallaxsofthighScoreAdapter.parallaxsoftfetchLastEntry();
		startManagingCursor(cursor);
		if(cursor.getCount() > 0) {
			parallaxsoftparallaxsoftisOnline.setText(cursor.getString(cursor.getColumnIndexOrThrow(parallaxsoftmHighscoreAdapterParallaxsoft.parallaxsoftmKEY_NAME)));
		}
		cursor.close();		
    }

    private void parallaxsoftsaveState() {
    	String name 	=  parallaxsoftparallaxsoftisOnline.getText().toString();
        String score 	=  parallaxsoftscoreField.getText().toString();
        int isonline = 0;
        if(name.length() > 0) {
        	if(parallaxsoftcheckboxPushOnline.isChecked()) {
        		
        		if(!parallaxsoftisOnline()) {
        			parallaxsofthighScoreAdapter.toastMessage(R.string.hs_error_no_internet);
        		} else {
	        	    HttpClient parallaxsofthttpclient = new DefaultHttpClient();
	        	    HttpPost parallaxsofthttppost = new HttpPost(parallaxsoftSettings.HIGHSCORE_POST_URL);
	
	        	    try {
	        	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        	        nameValuePairs.add(new BasicNameValuePair("name", name));
	        	        nameValuePairs.add(new BasicNameValuePair("score", score));
	        	        parallaxsofthttppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        	        parallaxsofthttpclient.execute(parallaxsofthttppost);
	
	        	    } catch (ClientProtocolException e) {
	        	    } catch (IOException e) {
	        	    }
	        	    
	        	    isonline = 1;
        		}
        	}
        	try {
                parallaxsofthighScoreAdapter.parallaxsoftmcreateHighscore(score, name, isonline);
            } catch (Exception e) {
                Log.w(parallaxsoftSettings.LOG_TAG, "create highscore threw an exception");
                Log.w(parallaxsoftSettings.LOG_TAG, "Maybe a double attempt? HTC Sensation does that for example");
                return;
            }       	
        	
        	parallaxsofthighScoreAdapter.close();
        	
        	setResult(RESULT_OK);
        	finish();
        } else {
        	parallaxsofthighScoreAdapter.toastMessage(R.string.hs_error_name_empty);
        }
    }
	public boolean parallaxsoftisOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo ni = cm.getActiveNetworkInfo();
	    if (ni != null && ni.isAvailable() && ni.isConnected()) {
	        return true;
	    } else {
	        return false; 
	    }
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        parallaxsoftsaveState();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override    
    protected void onDestroy() {        
        super.onDestroy();
         
        if (parallaxsofthighScoreAdapter != null) {
        	parallaxsofthighScoreAdapter.close();
        }
    }
}
