package com.example.app019_movies.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestResult extends BaseObservable implements Parcelable {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<Movie> movies;

    @SerializedName("total_pages")
    private Integer totalPages;

    @SerializedName("total_results")
    private Integer totalResults;

    // Parcelable Creator
    public static final Parcelable.Creator<RequestResult> CREATOR = new Creator<RequestResult>() {
        @Override
        public RequestResult createFromParcel(Parcel source) {
            return new RequestResult(source);
        }

        @Override
        public RequestResult[] newArray(int size) {
            return (new RequestResult[size]);
        }
    };


    public RequestResult() {}

    public RequestResult(Parcel source) {
        this.page = (Integer) source.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) source.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) source.readValue(Integer.class.getClassLoader());
        source.readList(this.movies, Movie.class.getClassLoader());
    }


    @Bindable
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Bindable
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Bindable
    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Bindable
    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(movies);
    }

}
