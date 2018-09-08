package com.ae.apps.arsenal.nextmatch.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.widget.Toast;

import com.ae.apps.arsenal.nextmatch.db.MatchInfoDataBase;
import com.ae.apps.arsenal.nextmatch.vo.MatchInfoVO;

/**
 * Manager class for the data
 * 
 * @author Midhun
 * 
 */
public class MatchDataManager {
	private static final String	TAG			= "MatchDataManager";
	private MatchInfoDataBase	mDataBase	= null;

	public MatchDataManager(Context context) {
		mDataBase = new MatchInfoDataBase(context);

		try {
			// Create a database
			mDataBase.createDataBase();
		} catch (IOException ioe) {
			Toast.makeText(context, "Unable to create database" + ioe.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Get the media list
	 * 
	 * @param fromDate
	 * @return
	 */
	public List<MatchInfoVO> getMatchesList(String fromDate) {
		// SELECT _id, opponent, match_date, venue_type, star_match FROM match_details WHERE match_date >= 140101;
		Cursor cursor = null;
		try {
			// Open the db
			mDataBase.openDataBase();
			cursor = mDataBase.getMatchDetailsFromDate(fromDate);
		} catch (SQLException sqle) {
			Log.e(TAG, "" + sqle.getMessage());
		}

		List<MatchInfoVO> dataList = new ArrayList<MatchInfoVO>();
		if (null != cursor) {
			MatchInfoVO itemVo = null;
			// Lets cache the db column indexes
			int opponentIndex = cursor.getColumnIndex(MatchInfoDataBase.KEY_OPPONENT);
			int matchDateIndex = cursor.getColumnIndex(MatchInfoDataBase.KEY_MATCH_DATE);
			int matchTimestamp = cursor.getColumnIndex(MatchInfoDataBase.KEY_MATCH_TIMESTAMP);
			int venueIndex = cursor.getColumnIndex(MatchInfoDataBase.KEY_VENUE_TYPE);
			int starMatchIndex = cursor.getColumnIndex(MatchInfoDataBase.KEY_STAR_MATCH);
			int competitionIndex = cursor.getColumnIndex(MatchInfoDataBase.KEY_COMPETITION_TYPE);
			int locationIndex = cursor.getColumnIndex(MatchInfoDataBase.KEY_VENUE_LOCATION);

			while (cursor.moveToNext()) {
				// Create a new VO object and map the values from the cursor
				itemVo = new MatchInfoVO();
				itemVo.setOpponent(cursor.getString(opponentIndex));
				itemVo.setMatchDate(cursor.getString(matchDateIndex));
				itemVo.setVenueType(cursor.getString(venueIndex));
				// SQLite don't have a boolean type
				itemVo.setStarMatch(cursor.getString(starMatchIndex).equals("1"));
				itemVo.setCompetition(cursor.getString(competitionIndex));
				itemVo.setStadium(cursor.getString(locationIndex));
				itemVo.setMatchTimestamp(cursor.getString(matchTimestamp));

				dataList.add(itemVo);
			}
		}
		mDataBase.close();
		return dataList;
	}

}