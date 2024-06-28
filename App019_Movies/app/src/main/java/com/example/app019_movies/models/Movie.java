package com.example.app019_movies.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.app019_movies.R;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


public class Movie extends BaseObservable implements Parcelable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

//    @SerializedName("adult")
//    private Boolean adult;

    @SerializedName("backdrop_path")
    private String backdropPath;

//    @SerializedName("genre_ids")
//    private List<Integer> genreIds;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

//    @SerializedName("video")
//    private Boolean video;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("vote_count")
    private Integer voteCount;
    
    // Parcel
    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
//            Movie movie = new Movie();
//            Field[] fields = Movie.class.getDeclaredFields();
//
//            Log.i("TAG", Arrays.toString(fields));
//
//            for (Field field : fields) {
//
//                String fieldName = field.getName();
//
//                if (fieldName.equals("CREATOR")) {
//                    continue;
//                }
//
//                Object fieldValue = source.readValue(field.getClass().getClassLoader());
//                movie.setField(fieldName, fieldValue);
//            }
//
//            Log.i("TAG", movie.posterPath);
//            return movie;
//            return (Movie) source.readParcelable(Movie.class.getClassLoader());.
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @BindingAdapter({"posterPath"})
    public static void loadImage(ImageView imageView, String imageURL) {
        String imagePath = "https:image.tmdb.org/t/p/w500" + imageURL;

        Log.i("IMAGE", imagePath);

        Glide.with(imageView.getContext())
                .load(imagePath)
                .into(imageView);
    }


    public Movie() {}

    public Movie(Parcel source) {
//        Field[] fields = Movie.class.getDeclaredFields();
//
//        Log.i("TAG", Arrays.toString(fields));
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Type fieldType = field.getType();
//
//            if (field.getName().equals("CREATOR")) {
//                continue;
//            }
//
//            Object fieldValue = source.readValue(fieldType.getClass().getClassLoader());
//            try {
//                field.set(this, fieldValue);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }

//        this.id = (Integer) source.readValue(Integer.class.getClassLoader());
//        this.backdropPath = (String) source.readValue(String.class.getClassLoader());
//        this.posterPath = (String) source.readValue(String.class.getClassLoader());
//        Log.i("TAG", this.posterPath);

        this.voteCount = ((Integer) source.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) source.readValue((Integer.class.getClassLoader())));
        this.voteAverage = ((Double) source.readValue((Double.class.getClassLoader())));
        this.title = ((String) source.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) source.readValue((Double.class.getClassLoader())));
        this.posterPath = ((String) source.readValue((String.class.getClassLoader())));
        this.originalLanguage = ((String) source.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) source.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) source.readValue((String.class.getClassLoader())));
        this.overview = ((String) source.readValue((String.class.getClassLoader())));
        this.releaseDate = ((String) source.readValue((String.class.getClassLoader())));
    }

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    @Bindable
//    public Boolean getAdult() {
//        return adult;
//    }
//
//    public void setAdult(Boolean adult) {
//        this.adult = adult;
//    }

    @Bindable
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

//    @Bindable
//    public List<Integer> getGenreIds() {
//        return genreIds;
//    }
//
//    public void setGenreIds(List<Integer> genreIds) {
//        this.genreIds = genreIds;
//    }

    @Bindable
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Bindable
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Bindable
    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @Bindable
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

//    @Bindable
//    public Boolean getVideo() {
//        return video;
//    }
//
//    public void setVideo(Boolean video) {
//        this.video = video;
//    }

    @Bindable
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Bindable
    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        Field[] fields = this.getClass().getDeclaredFields();
//
//        for (Field field : fields) {
//            dest.writeValue(field);
//        }
//    }


    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(title);
//        dest.writeString(backdropPath);
//        dest.writeString(originalLanguage);
//        dest.writeString(originalTitle);
//        dest.writeString(overview);
//        dest.writeDouble(popularity);
//        dest.writeString(posterPath);
//        dest.writeString(releaseDate);
//        dest.writeDouble(voteAverage);
//        dest.writeInt(voteCount);
//        dest.writeParcelable(this, flags);

        this.voteCount = ((Integer) dest.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) dest.readValue((Integer.class.getClassLoader())));
        this.voteAverage = ((Double) dest.readValue((Double.class.getClassLoader())));
        this.title = ((String) dest.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) dest.readValue((Double.class.getClassLoader())));
        this.posterPath = ((String) dest.readValue((String.class.getClassLoader())));
        this.originalLanguage = ((String) dest.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) dest.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) dest.readValue((String.class.getClassLoader())));
        this.overview = ((String) dest.readValue((String.class.getClassLoader())));
        this.releaseDate = ((String) dest.readValue((String.class.getClassLoader())));
    }

//    private void setField(String fieldName, Object fieldValue) {
//        Field field;
//        try {
//            field = this.getClass().getDeclaredField(fieldName);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        }
//
//        field.setAccessible(true);
//
//        try {
//            field.set(this, fieldValue);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
