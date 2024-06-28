package com.example.app020_paging.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app020_paging.R;
import com.example.app020_paging.databinding.LoadStateItemBinding;

public class MoviesLoadStateAdapter extends LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder> {

    private View.OnClickListener retryCallback;

    public MoviesLoadStateAdapter(View.OnClickListener retryCallback) {
        this.retryCallback = retryCallback;
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView errorMsg;
        private Button retry;

        public LoadStateViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallback) {
            super(
                    LayoutInflater.from(
                            parent.getContext()
                    ).inflate(
                            R.layout.load_state_item,
                            parent,
                            false
                    )
            );

            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            progressBar = binding.progressBar;
            errorMsg = binding.errorMsg;
            retry = binding.btnRetry;
            retry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState) {
            if (loadState instanceof  LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                errorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }

            progressBar.setVisibility(
                    loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE
            );

            retry.setVisibility(
                    loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE
            );

            errorMsg.setVisibility(
                    loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder viewHolder, @NonNull LoadState loadState) {
        viewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateViewHolder(viewGroup, retryCallback);
    }
}
