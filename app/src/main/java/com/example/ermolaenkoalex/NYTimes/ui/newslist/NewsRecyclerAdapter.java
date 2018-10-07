package com.example.ermolaenkoalex.NYTimes.ui.newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.NYTimes.R;
import com.example.ermolaenkoalex.NYTimes.events.NewsItemEvent;
import com.example.ermolaenkoalex.NYTimes.model.NewsItem;
import com.example.ermolaenkoalex.NYTimes.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> newsItems;

    @NonNull
    private final LayoutInflater inflater;

    @NonNull
    private final Context context;

    @NonNull
    private final RequestManager imageLoader;

    public NewsRecyclerAdapter(@NonNull Context context, @NonNull List<NewsItem> newsItems) {
        this.newsItems = newsItems;
        inflater = LayoutInflater.from(context);
        this.context = context;

        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(getLayoutResByViewType(viewType), parent, false)
        );
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
        return newsItems.get(position).getCategory().getId();
    }

    private int getLayoutResByViewType(int viewType) {
        return (viewType == 2) ? R.layout.news_item_criminal : R.layout.news_item;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView categoryView;
        private final TextView titleView;
        private final TextView previewView;
        private final TextView dateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_preview);
            categoryView = itemView.findViewById(R.id.tv_category);
            titleView = itemView.findViewById(R.id.tv_title);
            previewView = itemView.findViewById(R.id.tv_preview);
            dateView = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    EventBus.getDefault().post(new NewsItemEvent(newsItems.get(position)));
                }
            });
        }

        public void bind(@NonNull NewsItem newsItem) {
            // Fill views with our data
            imageLoader.load(newsItem.getImageUrl()).into(imageView);
            categoryView.setText(newsItem.getCategory().getName());
            titleView.setText(newsItem.getTitle());
            previewView.setText(newsItem.getPreviewText());
            dateView.setText(StringUtils.formatDate(context, newsItem.getPublishDate()));
        }
    }
}
