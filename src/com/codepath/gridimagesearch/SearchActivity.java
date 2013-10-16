package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class SearchActivity extends Activity {
	private static final int REQUEST_CODE = 0;
	//declare the views
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
    String colorSelected="", imageSizeSelected="", imageTypeSelected="", siteSelected="";
	//store the url
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //find view by id
        setupViews();
        
        //array of items
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        //attach
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener(){
        	@Override
        	public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
        		//all we need is position
        		//bring up a window
        		Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
        		ImageResult imageResult = imageResults.get(position);
        		//bundle parameter
        		//i.putExtra("url", imageResult.getFullUrl());
        		//pass an imageResult
        		i.putExtra("result", imageResult);
        		startActivity(i);
        	}
        });
        
//        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Whatever code is needed to append new items to your AdapterView
                // probably sending out a network request and appending items to your adapter. 
                // Use the page or the totalItemsCount to retrieve correct data.
	    	loadDataFromAPI(8, page); 
                // or customLoadMoreDataFromApi(totalItemsCount); 
	    }
        });
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

	//find the views by Id in xml
    public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
    }
    
    //action bar icon click handler
    public void onSetupFilter(MenuItem mi) {
    	// handle click here
    	Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();
		//bring up a window
		Intent i = new Intent(getApplicationContext(), FilterActivity.class);
		//bundle parameter
		//i.putExtra("url", imageResult.getFullUrl());
		//pass an imageResult
		//i.putExtra("result", imageResult);
		startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

        colorSelected = data.getExtras().getString("colorSelected");
        imageSizeSelected = data.getExtras().getString("imageSizeSelected");
        imageTypeSelected = data.getExtras().getString("imageTypeSelected");
        siteSelected = data.getExtras().getString("siteSelected");
        
 	    Toast.makeText(this,
	    		"OnActivityResult : " + 
	                    "\ncolorSelected : "+ colorSelected + 
	                    "\nimageSizeSelected : "+ imageSizeSelected +
	                    "\nimageTypeSelected : "+imageTypeSelected+
	                    "\nsiteSelected : "+siteSelected,
	    			Toast.LENGTH_SHORT).show();
      }
    } 
    
    //search button click handler
    public void onImageSearch(View v) {

   	  loadDataFromAPI(8, 0);
//    	Toast.makeText(this, "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+
//    	"start="+0+"&v=1.0&q="+Uri.encode(query), Toast.LENGTH_LONG).show();
//    	https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=0&v=1.0&q=android

    }

	private void loadDataFromAPI(int rsz, int page) {
    	String query = etQuery.getText().toString();
    	Toast.makeText(this, "Searching for "+query, Toast.LENGTH_LONG).show();
    	 
		AsyncHttpClient client = new AsyncHttpClient();
    	// send out ajax request
    	//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
		//String searchQuery = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" + query;
		
		int start = page *rsz;
    	client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz="+rsz+
    	"&start="+start+"&imgcolor="+colorSelected+"&imgtype="+imageTypeSelected
    	+"&imgsz="+imageSizeSelected+"&as_sitesearch="+siteSelected
    	+"&v=1.0&q="+Uri.encode(query), new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONObject response) {
    			JSONArray imageJsonResults = null;
    			//parse the API response: JSONArray
    			try {
    				imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
    				imageResults.clear();
//    				imageResults.addAll(ImageResult.fromJSONArray(imageJsonResults));
//    				imageAdapter.notify();
    				imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
    			    //log the imageResults to the console
    				Log.d("DEBUG", imageResults.toString());
    			} catch (JSONException e){
    				e.printStackTrace();
    			}
    		}
    		@Override
    		public void onFailure(Throwable e, JSONObject errorResponse) {
    			Log.d("DEBUG", "failure is hit!!!!");
    		}
    	});
	}

}
