package com.ae.apps.arsenal.nextmatch.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ae.apps.arsenal.nextmatch.R;
import com.ae.apps.arsenal.nextmatch.adapters.MatchDetailsAdapter;
import com.ae.apps.arsenal.nextmatch.managers.MatchDataManager;
import com.ae.apps.arsenal.nextmatch.utils.MatchInfoUtils;
import com.ae.apps.arsenal.nextmatch.vo.MatchInfoVO;

public class MainActivity extends ListActivity {

	private static int	MAX_UPCOMING	= 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView aboutText = (TextView) findViewById(R.id.aboutApp);
		aboutText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(), AboutActivity.class));
			}
		});

		// Initialize our data manager
		MatchDataManager dataManager = new MatchDataManager(getBaseContext());

		// Lets request the match manager to give us the latest match details
		List<MatchInfoVO> matchDetailsList = dataManager.getMatchesList(MatchInfoUtils.getTodaysDateIndex());
		if (matchDetailsList != null && matchDetailsList.size() > 0) {
			// This will be the upcoming match
			final MatchInfoVO nextMatchInfoVO = matchDetailsList.get(0);

			// Find the UI elements
			TextView nextMatchDate = (TextView) findViewById(R.id.nextMatchDateText);
			TextView nextOpponent = (TextView) findViewById(R.id.nextMatchOpponentText);
			TextView nextVenue = (TextView) findViewById(R.id.nextMatchVenueText);
			TextView nextCompetitionType = (TextView) findViewById(R.id.nextMatchCompetitionText);
			TextView matchStadiumText = (TextView) findViewById(R.id.nextMatchStadiumText);
			ImageView star = (ImageView) findViewById(R.id.star);
			ImageButton shareButton = (ImageButton) findViewById(R.id.shareButton);

			final Context shareContext = this;
			shareButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					MatchInfoUtils.shareStatus(shareContext, nextMatchInfoVO);
				}
			});

			String venueType = nextMatchInfoVO.getVenueType();

			long nextMatchTimeStamp = 0;
			if (nextMatchInfoVO.getMatchTimestamp() != null) {
				nextMatchTimeStamp = Long.parseLong(nextMatchInfoVO.getMatchTimestamp());
				nextMatchDate.setText(MatchInfoUtils.nextMatchDate(getResources(), nextMatchTimeStamp));
			} else {
				nextMatchDate.setText(MatchInfoUtils.getDateString(nextMatchInfoVO.getMatchDate()));
			}
			nextCompetitionType.setText(MatchInfoUtils.getCompetitionTypeText(nextMatchInfoVO.getCompetition()));
			nextOpponent.setText(nextMatchInfoVO.getOpponent());
			nextVenue.setText(venueType);
			matchStadiumText.setText(nextMatchInfoVO.getStadium());

			// Is this a starred match?
			if (nextMatchInfoVO.isStarMatch()) {
				star.setVisibility(View.VISIBLE);
			} else {
				star.setVisibility(View.INVISIBLE);
			}

			int parentVenueHighlight = MatchInfoUtils.getVenueHighlight(venueType);
			nextVenue.setBackgroundResource(parentVenueHighlight);

			// Remove the first match details
			matchDetailsList.remove(nextMatchInfoVO);

			// Limit the number of upcoming matches as we are a minimalistic app
			if (matchDetailsList.size() > MAX_UPCOMING) {
				matchDetailsList = matchDetailsList.subList(0, MAX_UPCOMING - 1);
			}

			// Do some animations for pleasing ui experience
			View nextMatchDetailsContainer = findViewById(R.id.nextMatchDetailsContainer);
			Animation slideInTopAnimation = AnimationUtils.loadAnimation(this, R.animator.slide_in_top);
			slideInTopAnimation.setStartOffset(200);
			nextMatchDetailsContainer.startAnimation(slideInTopAnimation);

			// animation for list container
			View listContainer = findViewById(R.id.listContainer);
			Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.animator.slide_in_right);
			slideInAnimation.setStartOffset(400);
			listContainer.startAnimation(slideInAnimation);

			// animation for about text
			aboutText.startAnimation(slideInAnimation);

			MatchDetailsAdapter adapter = new MatchDetailsAdapter(getBaseContext(), matchDetailsList);
			setListAdapter(adapter);
		} else {
			// Looks like no upcoming matches
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_about) {
			startActivity(new Intent(getBaseContext(), AboutActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
