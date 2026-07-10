package com.Spark.AnimeAPI.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attributes{
    public Date createdAt;
    public Date updatedAt;
    public String slug;
    public String synopsis;
    public String description;
    public int coverImageTopOffset;
    @JsonProperty("titles")
    public Titles titles;
    public String canonicalTitle;
    public ArrayList<String> abbreviatedTitles;
    public String averageRating;
    public RatingFrequencies ratingFrequencies;
    public int userCount;
    public int favoritesCount;
    public String startDate;
    public String endDate;
    public Date nextRelease;
    public int popularityRank;
    public int ratingRank;
    public String ageRating;
    public String ageRatingGuide;
    public String subtype;
    public String status;
    public Object tba;
    public PosterImage posterImage;
    public CoverImage coverImage;
    public int episodeCount;
    public int episodeLength;
    public int totalLength;
    public String youtubeVideoId;
    public String showType;
    public boolean nsfw;
}
