package com.ae.apps.arsenal.nextmatch.vo;

public class MatchInfoVO {

	private String	opponent;
	private String	matchDate;
	private String	venueType;
	private String	competition;
	private String	stadium;
	private String	matchTimestamp;
	private boolean	starMatch;

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponentTeam) {
		this.opponent = opponentTeam;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}

	public String getCompetition() {
		return competition;
	}

	public void setCompetition(String competition) {
		this.competition = competition;
	}

	public boolean isStarMatch() {
		return starMatch;
	}

	public void setStarMatch(boolean starMatch) {
		this.starMatch = starMatch;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getMatchTimestamp() {
		return matchTimestamp;
	}

	public void setMatchTimestamp(String matchTimestamp) {
		this.matchTimestamp = matchTimestamp;
	}
}
