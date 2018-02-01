package com.parallaxsoft.highjumper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

import com.parallaxsoft.highscore.parallaxsoftmHighscoreAdapterParallaxsoft;

public class parallaxsoftHighScoreActivity extends Activity {
	
	private parallaxsoftmHighscoreAdapterParallaxsoft parallaxsofthighScoreAdapter = null;
	private static final String parallaxsoftPOST_HIGHSCORE_URL = parallaxsoftSettings.HIGHSCORE_POST_URL;
	private static final String parallaxsoftGET_HIGHSCORE_URL = parallaxsoftSettings.HIGHSCORE_GET_URL;
	private TableLayout parallaxsofthighscoreTable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        parallaxsofthighScoreAdapter = new parallaxsoftmHighscoreAdapterParallaxsoft(this);
        parallaxsofthighScoreAdapter.open();
        parallaxsofthighscoreTable = (TableLayout) findViewById(R.id.parallaxsofthighscoreTable);
        final Context context = this;
        final Handler handler = new Handler();
        findViewById(R.id.buttonLocalHighscore).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(context, R.string.hs_loading_local, Toast.LENGTH_SHORT).show();
				handler.postDelayed(new Runnable() {
					
					public void run() {
						parallaxsoftshowLocalScore();
					}
				}, 500);
			}
		});

        findViewById(R.id.buttonOnlineHighscore).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(context, R.string.hs_loading_online, Toast.LENGTH_SHORT).show();
				handler.postDelayed(new Runnable() {
					
					public void run() {
						parallaxsoftshowOnlineScore();
					}
				}, 500);
			}
		});

        Toast.makeText(context, R.string.hs_loading_local, Toast.LENGTH_SHORT).show();
		handler.postDelayed(new Runnable() {
			
			public void run() {
				parallaxsoftshowLocalScore();
			}
		}, 500);
    }
    
    private void parallaxsoftshowLocalScore() {
    	parallaxsofthighscoreTable.removeAllViews();
    	Cursor c = parallaxsofthighScoreAdapter.parallaxsoftfetchScores("0");
    	if (c.isAfterLast()) {
            Toast.makeText(this, R.string.hs_no_data, Toast.LENGTH_SHORT).show();
    		return;
    	}
    	int currentPlace = 1;
    	
    	do {

    		final String placeString = ""+(currentPlace++)+".";
    		final String scoreString = c.getString(2);
    		final String nameString = c.getString(1);
    		
    		View additional;
    		
    		if (c.getString(3).equalsIgnoreCase("0")) {
    			additional = new Button(this);
    			final Context context = this;
    			final int id = c.getInt(0);
    			additional.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						AlertDialog.Builder alert = new AlertDialog.Builder(context);
				
				        alert.setTitle("Push this score online ?");
				        alert.setMessage("Name: " + nameString + "\nScore: " + scoreString);
				        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if(!parallaxsoftisOnline()) {
				        		parallaxsofthighScoreAdapter.toastMessage(R.string.hs_error_no_internet);
				        	} else {
				        	    HttpClient parallaxsofthttpclient = new DefaultHttpClient();
				        	    HttpPost parallaxsofthttppost = new HttpPost(parallaxsoftPOST_HIGHSCORE_URL);
				
				        	    try {
				        	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				        	        nameValuePairs.add(new BasicNameValuePair("name", nameString));
				        	        nameValuePairs.add(new BasicNameValuePair("score", scoreString));
				        	        parallaxsofthttppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				        	        parallaxsofthttpclient.execute(parallaxsofthttppost);
				        	        parallaxsofthighScoreAdapter.parallaxsoftupdateScore(id, 1);
				        	        parallaxsofthighScoreAdapter.toastMessage(R.string.hs_pushed_online);
				        	        
				        	        runOnUiThread(new Runnable() {
										
										public void run() {
											parallaxsoftshowLocalScore();
											
										}
									});
				        	    } catch (ClientProtocolException e) {
				        	    } catch (IOException e) {
				        	    }        		
				        	}        	
				          }
				        });
				        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				          public void onClick(DialogInterface dialog, int whichButton) {
				          }
				        });
				        alert.show();  
					}
				});
    			additional.setBackgroundResource(R.drawable.highscore_submit);
    			
    			LayoutParams parallaxsoftparamsOfSubmitButton = new LayoutParams(0, LayoutParams.MATCH_PARENT, 3.0f);
            	additional.setLayoutParams(parallaxsoftparamsOfSubmitButton);
    		} else {
    			additional = new TextView(this, null, android.R.attr.textAppearanceSmallInverse);
    			((TextView)additional).setText("is online");

        		LayoutParams parallaxsoftparamsOfAdditional = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3.0f);
        		parallaxsoftparamsOfAdditional.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        		additional.setLayoutParams(parallaxsoftparamsOfAdditional);
    		}
    		
    		parallaxsoftgenerateLine(placeString, scoreString, nameString, additional);
    		
    	} while(c.moveToNext());
    	
    	
    	
    }
    
    private void parallaxsoftshowOnlineScore() {
    	
    	
    	
    	if(!parallaxsoftisOnline()) {
    		Toast.makeText(this, R.string.hs_error_no_internet, Toast.LENGTH_SHORT).show();
    	} else {

        	parallaxsofthighscoreTable.removeAllViews();
        	
	    	try {
	    		HttpClient client = new DefaultHttpClient();  
	    		String getURL = parallaxsoftGET_HIGHSCORE_URL + "?size=" + Integer.toString(parallaxsoftSettings.onlineHighscoreLimit);
	    		HttpGet get = new HttpGet(getURL);
	    		HttpResponse parallaxsoftresponseGet = client.execute(get);
	    		HttpEntity parallaxsoftresEntityGet = parallaxsoftresponseGet.getEntity();
	    		if (parallaxsoftresEntityGet != null) {
	    			JSONArray jArray = new JSONArray(EntityUtils.toString(parallaxsoftresEntityGet));
					String nameString;
					String scoreString;
					String timeStamp;
	    			for(int i = 0; i < jArray.length(); i++) {
	    				nameString = jArray.getJSONObject(i).getString("name");
	    				scoreString = jArray.getJSONObject(i).getString("score");
	    				timeStamp = jArray.getJSONObject(i).getString("created_at");
	    				View additional = new TextView(this, null, android.R.attr.textAppearanceSmallInverse);
	        			((TextView)additional).setText(timeStamp);
	            		LayoutParams parallaxsoftparamsOfAdditional = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3.0f);
	            		parallaxsoftparamsOfAdditional.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
	            		additional.setLayoutParams(parallaxsoftparamsOfAdditional);
	            		parallaxsoftgenerateLine(""+(i+1), scoreString, nameString, additional);
	    			}             
	    		}

	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
    	}

    }
    
    
    private void parallaxsoftgenerateLine(String placeString, String scoreString, String nameString, View additional) {
    	
    	TextView place = new TextView(this, null, android.R.attr.textAppearanceLargeInverse);
		place.setText(placeString);
		LayoutParams paramsOfPlace = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 2.0f);
		paramsOfPlace.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
		place.setLayoutParams(paramsOfPlace);
		TextView score = new TextView(this, null, android.R.attr.textAppearanceMediumInverse);
		score.setText(scoreString);
		LayoutParams parallaxsoftparamsOfScore = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 3.0f);
		parallaxsoftparamsOfScore.gravity = Gravity.CENTER;
		score.setLayoutParams(parallaxsoftparamsOfScore);
		TextView name = new TextView(this, null, android.R.attr.textAppearanceMediumInverse);
		name.setText(nameString);
		LayoutParams parallaxsoftparamsOfName = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 10.0f);
		parallaxsoftparamsOfName.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
		name.setLayoutParams(parallaxsoftparamsOfName);
		parallaxsoftaddLine(place, score, name, additional);
    }
    
    private void parallaxsoftaddLine(View place, View score, View name, View additional) {
    	TableRow tr = new TableRow(this);
    	tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    	tr.addView(place);
    	tr.addView(score);
    	tr.addView(name);
    	tr.addView(additional);
    	parallaxsofthighscoreTable.addView(tr);
    	ImageView line = new ImageView(this);
    	line.setBackgroundResource(R.drawable.highscore_line);
    	parallaxsofthighscoreTable.addView(line);
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
    protected void onDestroy() {        
        super.onDestroy();
         
        if (parallaxsofthighScoreAdapter != null) {
        	parallaxsofthighScoreAdapter.close();
        }
    }
}
