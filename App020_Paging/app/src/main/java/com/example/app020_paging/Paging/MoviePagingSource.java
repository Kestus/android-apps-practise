package com.example.app020_paging.Paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.app020_paging.Api.ApiClient;
import com.example.app020_paging.Models.Movie;
import com.example.app020_paging.Models.Response;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer, Movie> {
    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        try {
            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            return ApiClient
                    .getApiInterface()
                    .getMoviesByPage(page)
                    .subscribeOn(Schedulers.io())
                    .map(Response::getMovies)
                    .map(movies -> toLoadResult(movies, page))
                    .onErrorReturn(LoadResult.Error::new);
        } catch (Exception e) {
            Log.e("TAG", "ERROR");
            return Single.just(new LoadResult.Error<>(e));
        }
    }

    private LoadResult<Integer, Movie> toLoadResult(List<Movie> movies, int page) {
        return new LoadResult.Page<>(
                movies,
                page == 1 ? null : page-1,
                page+1
        );
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return 0;
    }
}
