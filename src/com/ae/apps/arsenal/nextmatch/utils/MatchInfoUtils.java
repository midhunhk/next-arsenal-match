package com.ae.apps.arsenal.nextmatch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import com.ae.apps.arsenal.nextmatch.R;
import com.ae.apps.arsenal.nextmatch.vo.MatchInfoVO;

/**
 * Utility methods
 * 
 * @author user
 * 
 */
public class MatchInfoUtils {
	private static final Locale	DEFAULT_LOCALE			= Locale.getDefault();
	private static final String	DAY_OF_WEEK				= "EEE, ";
	private static final String	DATE_FOR_DISPLAY		= "d MMM y";
	private static final String	TIME_FOR_DISPLAY		= ", hh:mm a";
	private static final String	DATE_FOR_INDEX			= "yyMMdd";
	private static final double	MILLISECONDS_PER_DAY	= 24 * 3600 * 1000;

	public static String getDateString(String rawDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(DATE_FOR_INDEX, DEFAULT_LOCALE).parse(rawDate);
		} catch (ParseException e) {
			date = Calendar.getInstance(DEFAULT_LOCALE).getTime();
		}
		return new SimpleDateFormat(DAY_OF_WEEK + DATE_FOR_DISPLAY, DEFAULT_LOCALE).format(date).toString();
	}

	public static String getTodaysDateIndex() {
		Calendar calendar = Calendar.getInstance(DEFAULT_LOCALE);
		return new SimpleDateFormat(DATE_FOR_INDEX, DEFAULT_LOCALE).format(calendar.getTime()).toString();
	}

	public static int getVenueHighlight(String venueType) {
		if ("A".equals(venueType)) {
			return R.color.holo_blue_bright;
		} else if ("H".equals(venueType)) {
			return R.color.holo_red_dark;
		} else {
			return R.color.holo_purple;
		}
	}

	public static int getCompetitionTypeText(String competition) {
		if ("EPL".equals(competition)) {
			return R.string.competition_epl;
		} else if ("UCL".equals(competition)) {
			return R.string.competition_ucl;
		} else if ("COC".equals(competition)) {
			return R.string.competition_coc;
		} else if ("FAC".equals(competition)) {
			return R.string.competition_fc;
		}
		return R.string.empty_text;
	}

	public static int getCompetitionHighlightColor(String competition) {
		int highlightColor;
		if ("EPL".equals(competition)) {
			highlightColor = R.color.holo_red_light;
		} else if ("UCL".equals(competition)) {
			highlightColor = R.color.holo_orange_light;
		} else if ("COC".equals(competition)) {
			highlightColor = R.color.holo_green_light;
		} else if ("FAC".equals(competition)) {
			highlightColor = R.color.holo_blue_light;
		} else {
			highlightColor = R.color.holo_purple;
		}
		return highlightColor;
	}

	public static String nextMatchDate(Resources resources, long matchStartTimeStamp) {
		Date matchDate = new Date(matchStartTimeStamp);
		long days = daysTill(matchStartTimeStamp);
		SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FOR_DISPLAY, DEFAULT_LOCALE);
		if (days == 0) {
			return resources.getString(R.string.str_today) + timeFormat.format(matchDate);
		} else if (days == 1) {
			return resources.getString(R.string.str_tomorrow) + timeFormat.format(matchDate);
		} else {
			return new SimpleDateFormat(DATE_FOR_DISPLAY + TIME_FOR_DISPLAY, DEFAULT_LOCALE).format(matchDate)
					.toString();
		}
	}

	/**
	 * Share some data on social stream
	 * 
	 * @param context
	 * @param matchInfoVo
	 */
	public static void shareStatus(Context context, MatchInfoVO matchInfoVo) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");

		// Make some useful data to share
		Resources resources = context.getResources();
		String shareSubject = resources.getString(R.string.str_share_status_subject, matchInfoVo.getOpponent());
		String shareBody = resources.getString(R.string.str_share_status_detail, matchInfoVo.getOpponent(),
				getDateString(matchInfoVo.getMatchDate()), matchInfoVo.getStadium());

		// Put the macth data as extras
		intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
		intent.putExtra(Intent.EXTRA_TEXT, shareBody);

		// Start the chooser activity to let the user choose an app to share
		String shareVia = context.getResources().getString(R.string.str_share_via);
		context.startActivity(Intent.createChooser(intent, shareVia));
	}

	/**
	 * Returns the number of days till a future date
	 * 
	 * @param futureDate
	 * @return
	 */
	public static long daysTill(long futureDate) {
		long diff = futureDate - Calendar.getInstance(DEFAULT_LOCALE).getTimeInMillis();
		return (long) Math.floor(diff / MILLISECONDS_PER_DAY);
	}
}
