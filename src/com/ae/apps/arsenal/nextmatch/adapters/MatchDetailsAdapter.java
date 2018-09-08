package com.ae.apps.arsenal.nextmatch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ae.apps.arsenal.nextmatch.R;
import com.ae.apps.arsenal.nextmatch.utils.MatchInfoUtils;
import com.ae.apps.arsenal.nextmatch.vo.MatchInfoVO;

public class MatchDetailsAdapter extends BaseAdapter {
	private List<MatchInfoVO>	dataList;
	private LayoutInflater		inflater;

	public MatchDetailsAdapter(Context context, List<MatchInfoVO> list) {
		this.dataList = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int location) {
		return dataList.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.match_detail_item, null);
			holder = new ViewHolder();
			// Find and store some views that we need
			holder.line1 = (TextView) convertView.findViewById(R.id.matchDateText);
			holder.line2 = (TextView) convertView.findViewById(R.id.matchOpponentText);
			holder.competitionType = (TextView) convertView.findViewById(R.id.matchCompetitionTypeText);
			holder.star = (ImageView) convertView.findViewById(R.id.starImage);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MatchInfoVO itemVo = (MatchInfoVO) getItem(position);
		if (null != itemVo) {
			holder.line1.setText(MatchInfoUtils.getDateString(itemVo.getMatchDate()));
			String venueType = itemVo.getVenueType();
			// Arsenal gonna be same in all languages ;) 
			if ("H".equals(venueType)) {
				holder.line2.setText("Arsenal v " + itemVo.getOpponent());
			} else {
				holder.line2.setText(itemVo.getOpponent() + " v Arsenal");
			}

			// Show a star to indicate the star match if present
			if (itemVo.isStarMatch()) {
				holder.star.setVisibility(View.VISIBLE);
			} else {
				holder.star.setVisibility(View.INVISIBLE);
			}
			String competition = itemVo.getCompetition();
			holder.competitionType.setText(competition);
			holder.competitionType.setBackgroundResource(MatchInfoUtils.getCompetitionHighlightColor(competition));
		}
		return convertView;
	}

	/**
	 * Helper class
	 * 
	 * @author Midhun
	 * 
	 */
	class ViewHolder {
		TextView	line1;
		TextView	line2;
		TextView	competitionType;
		TextView	count;
		ImageView	star;
	}

}