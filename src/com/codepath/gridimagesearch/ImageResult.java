package com.codepath.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//translate JSon to java object
public class ImageResult implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 2636389073439281291L;
	//describe which fields
	//parse JSON into this object
	private String fullUrl;
	private String thumbUrl;
	
	//constructor for a model
	//tends to accept JSONObject used to show image
	//a dictionary of keys
	//command shift O
	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
			//title
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
		}
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public String toString() {
		return this.thumbUrl;
	}
	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
		// iterate and aggregate
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int x = 0; x < array.length(); x++) {
			try {
				results.add(new ImageResult(array.getJSONObject(x)));
			}catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
