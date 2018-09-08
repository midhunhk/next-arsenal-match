/*
 * Copyright 2014 Midhun Harikumar
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ae.apps.arsenal.nextmatch.db;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.preference.PreferenceManager;

/**
 * Get the Match Informtions from the repository
 * 
 * @author Midhun Harikumar
 * 
 */
public class MatchInfoDataBase extends CopiedDataBaseHelper {

	private static String			DATABASE_NAME			= "MatchesInfo.db";
	private static final int		DATABASE_VERSION		= 1;
	private static final int		DATA_VERSION			= 13;

	/* Table names */
	private static final String		TABLE_MATCH_DETAILS		= "match_details";

	/* Table Keys */
	public static final String		KEY_ID					= "_id";
	public static final String		KEY_OPPONENT			= "opponent";
	public static final String		KEY_MATCH_DATE			= "match_date";
	public static final String		KEY_MATCH_TIMESTAMP		= "match_timestamp";
	public static final String		KEY_VENUE_TYPE			= "venue_type";
	public static final String		KEY_VENUE_LOCATION		= "venue_location";
	public static final String		KEY_STAR_MATCH			= "star_match";
	public static final String		KEY_COMPETITION_TYPE	= "competition_type";

	private static final String		PREF_KEY_DATA_VERSION	= "pref_key_data_version";

	/* Projections */
	public static final String[]	MEDIA_PROJECTION		= new String[] { KEY_ID, KEY_OPPONENT, KEY_MATCH_DATE,
			KEY_MATCH_TIMESTAMP, KEY_VENUE_TYPE, KEY_VENUE_LOCATION, KEY_STAR_MATCH, KEY_COMPETITION_TYPE };

	public MatchInfoDataBase(Context context) {
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public MatchInfoDataBase(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		checkDataBaseDataVersion(context);
	}

	/**
	 * Checks if the data version is updated to copy the new database
	 * 
	 * @param context
	 */
	private void checkDataBaseDataVersion(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		int currentDataVersion = preferences.getInt(PREF_KEY_DATA_VERSION, 1);
		if (DATA_VERSION > currentDataVersion) {
			try {
				copyDataBase();
			} catch (IOException e) {
			} finally {
				// Update the data version in the preferences
				preferences.edit().putInt(PREF_KEY_DATA_VERSION, DATA_VERSION).commit();
			}
		}
	}

	public Cursor getAllMatchDetails() {
		return query(TABLE_MATCH_DETAILS, MEDIA_PROJECTION, null, null, null, null, null);
	}

	public Cursor getMatchDetailsFromDate(String fromDate) {
		// String selection = KEY_MATCH_DATE + " >= " + fromDate;
		String[] args = { fromDate };
		return query(TABLE_MATCH_DETAILS, MEDIA_PROJECTION, KEY_MATCH_DATE + " >= ?", args, null, null, null);
	}

}
