package com.example.buzzrfrontend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzrfrontend.DRVinterface.LoadMore;
import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.Navigation;
import com.example.buzzrfrontend.ui.dashboardView.DashboardActivity;
import com.google.android.gms.maps.model.Dash;

import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder
{
    public ProgressBar progressBar;


    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }
}

class itemViewHolder extends RecyclerView.ViewHolder
{
    public TextView name;


    public itemViewHolder(@NonNull View itemView)
    {
        super(itemView);
        name = itemView.findViewById(R.id.name);

    }
}

public class DynamicRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<DynamicRVModel> items;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;

    ApplicationVar appVar;

    @Override
    public int getItemViewType(int position)
    {
        return items.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(LoadMore loadMore)
    {
        this.loadMore=loadMore;
    }


    public DynamicRVAdapter(RecyclerView recyclerView, Activity activity, List<DynamicRVModel> items)
    {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold))
                {
                    if(loadMore!=null)
                    {
                        loadMore.onLoadMore();
                    }
                    isLoading=true;
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item_layout,parent,false);

            return new itemViewHolder(view);
        }
        else if(viewType==VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_progress_bar,parent,false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position)
    {
        if(holder instanceof itemViewHolder)
        {
            DynamicRVModel item = items.get(position);
            itemViewHolder viewHolder = (itemViewHolder) holder;
            viewHolder.name.setText(items.get(position).getName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    appVar = (ApplicationVar) activity.getApplicationContext();
                    //Toast.makeText(activity, items.get(position).getName() + " selected", Toast.LENGTH_SHORT).show();
                    appVar.getNav().openToBarberProfileActivity(items.get(position).getId());

                    //position comes from above
                    notifyDataSetChanged();

                }
            });
        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;

        }
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public void setLoaded()
    {
        isLoading = false;
    }

}
