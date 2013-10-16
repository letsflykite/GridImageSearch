package com.codepath.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterActivity extends Activity {

	Spinner spinnerColorFilter, spinnerImageSize, spinnerImageType;
	EditText siteFilter;
//	
//	Button btnSave;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		spinnerColorFilter = (Spinner) findViewById(R.id.spinnerColorFilter);
		spinnerImageSize = (Spinner) findViewById(R.id.spinnerImageSize);
		spinnerImageType = (Spinner) findViewById(R.id.spinnerImageType);
		siteFilter = (EditText) findViewById(R.id.etSiteFilter);
		
//		btnSave = (Button) findViewById(R.id.btnSave);
		 		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterColorArray = ArrayAdapter.createFromResource(this,
		        R.array.color_array, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapterImageSizeArray = ArrayAdapter.createFromResource(this,
		        R.array.image_size_array, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapterImageTypeArray = ArrayAdapter.createFromResource(this,
		        R.array.image_type_array, android.R.layout.simple_spinner_item);

		// Specify the layout to use when the list of choices appears
		adapterColorArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterImageSizeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterImageTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner
		spinnerColorFilter.setAdapter(adapterColorArray);
		spinnerImageSize.setAdapter(adapterImageSizeArray);
		spinnerImageType.setAdapter(adapterImageTypeArray);
		
		spinnerColorFilter.setOnItemSelectedListener(new OnItemSelectedListener(){
		    public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos).toString();
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }
		});
		
		//access bundled data in the opened activity
//		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
//		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
//		//ivImage.setImageUrl(url);
//		ivImage.setImageUrl(result.getFullUrl());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}
	//save button click handler
	public void onSaveFilter(View v) {
	    String colorSelected = spinnerColorFilter.getSelectedItem().toString();
	    String imageSizeSelected = spinnerImageSize.getSelectedItem().toString();
	    String imageTypeSelected = spinnerImageType.getSelectedItem().toString();
	    String siteSelected = siteFilter.getText().toString();
	    Toast.makeText(this,
		    		"OnSaveFilter : " + 
		                    "\ncolorSelected : "+ colorSelected + 
		                    "\nimageSizeSelected : "+ imageSizeSelected +
		                    "\nimageTypeSelected : "+imageTypeSelected+
		                    "\nsiteSelected : "+siteSelected,
		    			Toast.LENGTH_SHORT).show();

		  // Prepare data intent 
		  Intent data = new Intent();
		  data.putExtra("colorSelected", colorSelected);
		  data.putExtra("imageSizeSelected", imageSizeSelected);
		  data.putExtra("imageTypeSelected", imageTypeSelected);
		  data.putExtra("imageSizeSelected", imageSizeSelected);
		  data.putExtra("siteSelected", siteSelected);
		  // Activity finished ok, return the data
		  setResult(RESULT_OK, data); // set result code and bundle data for response
		  finish(); // closes the activity, pass data to parent
	}
}
