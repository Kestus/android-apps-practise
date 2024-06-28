package com.example.app020_paging.DependencyInjection;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.app020_paging.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public RequestManager getGlide(@ApplicationContext Context context) {
        return Glide.with(context)
                .applyDefaultRequestOptions(
                        new RequestOptions()
                                .error(R.drawable.error_image)
                                .placeholder(R.drawable.placeholder_image)
                );

    }
}
