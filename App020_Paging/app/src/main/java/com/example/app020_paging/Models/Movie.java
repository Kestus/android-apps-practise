package com.example.app020_paging.Models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Movie {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private double voteAverage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movie movie = (Movie) object;
        return Double.compare(voteAverage, movie.voteAverage) == 0
                && Objects.equals(id, movie.id)
                && Objects.equals(title, movie.title)
                && Objects.equals(overview, movie.overview)
                && Objects.equals(posterPath, movie.posterPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, overview, posterPath, voteAverage);
    }
}
