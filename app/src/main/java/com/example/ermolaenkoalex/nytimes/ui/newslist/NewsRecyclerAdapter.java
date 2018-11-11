package com.example.ermolaenkoalex.nytimes.ui.newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> newsItems = new ArrayList<>();

    @NonNull
    private final LayoutInflater inflater;

    @Nullable
    private final OnItemClickListener clickListener;

    @NonNull
    private final Context context;

    @NonNull
    private final RequestManager imageLoader;

    NewsRecyclerAdapter(@NonNull Context context, @Nullable OnItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.inflater = LayoutInflater.from(context);

        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(viewType, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!newsItems.get(position).hasUsCategory()) {
            return R.layout.item_news_us;
        }

        return R.layout.item_news;
    }

    public void setData(List<NewsItem> data) {
        newsItems.clear();
        newsItems.addAll(data);

        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView categoryView;
        private final TextView titleView;
        private final TextView previewView;
        private final TextView dateView;

        ViewHolder(@NonNull View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_preview);
            categoryView = itemView.findViewById(R.id.tv_category);
            titleView = itemView.findViewById(R.id.tv_title);
            previewView = itemView.findViewById(R.id.tv_preview);
            dateView = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(newsItems.get(position));
                }
            });
        }

        void bind(@NonNull NewsItem newsItem) {
            // Fill views with our data
            imageLoader.load(newsItem.getImageUrl()).into(imageView);
            categoryView.setText(newsItem.getCategory());
            titleView.setText(newsItem.getTitle());
            previewView.setText(newsItem.getPreviewText());
            dateView.setText(StringUtils.formatDate(context, newsItem.getPublishDate()));
        }
    }
}
