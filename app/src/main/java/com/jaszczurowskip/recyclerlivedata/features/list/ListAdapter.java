package com.jaszczurowskip.recyclerlivedata.features.list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaszczurowskip.recyclerlivedata.R;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.features.detail.DetailActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jaszczurowskip on 23.10.2018
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomView> {
    private static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";
    private List<ListItem> listOfData;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;

    ListAdapter(List<ListItem> listOfData, Context context, Activity activity) {
        this.listOfData = listOfData;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View v = layoutInflater.inflate(R.layout.item_recycler_list, parent, false);
        return new CustomView(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull CustomView customView, int position) {
        ListItem currentItem = listOfData.get(position);
        //customView.coloredCircle.setImageResource(currentItem.getColorResource());
        Glide.with(customView.container).load(currentItem.getImgResourcePath()).into(customView.coloredCircle);
        customView.message.setText(currentItem.getMessage());
        customView.dateAndTime.setText("Photo was created:    " + currentItem.getItemId());
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    public void startDetailActivity(String itemId, View viewRoot) {
        Intent i = new Intent(activity, DetailActivity.class);
        i.putExtra(EXTRA_ITEM_ID, itemId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setEnterTransition(new Fade(Fade.IN));
            activity.getWindow().setEnterTransition(new Fade(Fade.OUT));

            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity,
                            new Pair<>(viewRoot.findViewById(R.id.imgv_list_item),
                                    context.getString(R.string.transition_drawable)),
                            new Pair<>(viewRoot.findViewById(R.id.label_message),
                                    context.getString(R.string.transition_message)),
                            new Pair<>(viewRoot.findViewById(R.id.label_date),
                                    context.getString(R.string.transition_date_and_time)));
            context.startActivity(i, options.toBundle());
        } else {
            context.startActivity(i);
        }
    }

    class CustomView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView coloredCircle;
        private TextView dateAndTime;
        private TextView message;
        private ViewGroup container;

        CustomView(final View itemView) {
            super(itemView);
            this.coloredCircle = itemView.findViewById(R.id.imgv_list_item);
            this.dateAndTime = itemView.findViewById(R.id.label_date);
            this.message = itemView.findViewById(R.id.label_message);
            this.container = itemView.findViewById(R.id.root_list_item);
            this.container.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            ListItem listItem = listOfData.get(
                    this.getAdapterPosition()
            );
            startDetailActivity(listItem.getItemId(), v);
        }
    }

}