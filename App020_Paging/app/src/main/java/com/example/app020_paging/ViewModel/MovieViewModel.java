package com.example.app020_paging.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.example.app020_paging.Models.Movie;
import com.example.app020_paging.Paging.MoviePagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class MovieViewModel extends ViewModel {

    public Flowable<PagingData<Movie>> moviePagingFlowable;

    public MovieViewModel() {
        MoviePagingSource moviePagingSource = new MoviePagingSource();

        Pager<Integer, Movie> pager = new Pager<>(
                new PagingConfig(
                        20,
                        20,
                        false,
                        20,
                        20*499
                ),
                () -> moviePagingSource
        );

        // Flowable
        moviePagingFlowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(moviePagingFlowable, coroutineScope);
    }
}
