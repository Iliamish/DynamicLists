package com.pid.dynamiclists.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pid.dynamiclists.Interfaces.RecyclerViewClickListener;
import com.pid.dynamiclists.Models.ShortStudent;
import com.pid.dynamiclists.Models.Student;
import com.pid.dynamiclists.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_EMPTY_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 2;

    private LayoutInflater inflater;
    private int layout;
    private static RecyclerViewClickListener itemListener;
    /**
     * The Context.
     */
    Context context;
    private List<ShortStudent> users;

    /**
     * Instantiates a new Search adapter.
     *
     * @param context      the context
     * @param users        the users
     */
    public SearchAdapter(Context context, RecyclerViewClickListener itemListener, List<ShortStudent> users) {
        this.users = users;
        this.context = context;
        this.itemListener = itemListener;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Clear.
     */
    public void clear() {
        users.clear();
        this.notifyDataSetChanged();
    }

    /**
     * Add.
     *
     * @param item the item
     */
    public void add(ShortStudent item) {
        users.add(item);
        this.notifyItemInserted(users.size() - 1);
    }

    /**
     * Remove loader.
     */
    public void removeLoader() {
        if(users.size() != 0){
            if(users.get(users.size()-1) == null) {
                users.remove(users.size() - 1);
                this.notifyItemRemoved(users.size());
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dropdown_item, parent, false);
            return new SearchAdapter.ViewHolder(view);
        } else if (viewType == VIEW_TYPE_EMPTY_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nothing_found, parent, false);
            return new SearchAdapter.NothingFound(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new SearchAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder view, int position) {
        if (view instanceof SearchAdapter.ViewHolder) {
            SearchAdapter.ViewHolder viewHolder  = (SearchAdapter.ViewHolder)view;
            ShortStudent user = users.get(position);

            viewHolder.nameView.setText(user.getFio());

            /*switch (user.getType()){
                case 0:
                    viewHolder.image.setImageDrawable(context.getDrawable(R.drawable.ic_school_black_24dp));
                    break;
                case 1:
                    viewHolder.image.setImageDrawable(context.getDrawable(R.drawable.ic_lecturer));
                    break;
                case 3:
                    viewHolder.image.setImageDrawable(context.getDrawable(R.drawable.ic_group));
                    break;
            }*/
        }else if (view instanceof SearchAdapter.NothingFound) {
            SearchAdapter.NothingFound viewHolder  = (SearchAdapter.NothingFound)view;
            //User user = users.get(position);

            // viewHolder.dateView.setText(new StringBuilder(params[8] + ": " + scheduleClass.getDate() + " ," + daysOfWeek[scheduleClass.getDayOfWeek()]));
        }else if (view instanceof SearchAdapter.LoadingViewHolder) {
            showLoadingView((SearchAdapter.LoadingViewHolder) view, position);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(users.get(position) == null){
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    /**
     * Empty boolean.
     *
     * @return the boolean
     */
    public boolean empty() {
        return users.isEmpty();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        /**
         * The Name view.
         */
        final TextView nameView; /**
         * The Info view.
         */
        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        ViewHolder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.recyclerViewListClicked(view, getLayoutPosition());
                }
            });
            nameView = view.findViewById(R.id.text1);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Progress bar.
         */
        ProgressBar progressBar;

        /**
         * Instantiates a new Loading view holder.
         *
         * @param itemView the item view
         */
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private class NothingFound extends RecyclerView.ViewHolder {

        /**
         * The Date view.
         */
        TextView dateView, /**
         * The No classes view.
         */
        noClassesView;

        /**
         * Instantiates a new Nothing found.
         *
         * @param itemView the item view
         */
        public NothingFound(@NonNull View itemView) {
            super(itemView);
            noClassesView = itemView.findViewById(R.id.no_classes);
        }
    }

    private void showLoadingView(SearchAdapter.LoadingViewHolder viewHolder, int position) {
        viewHolder.progressBar.setIndeterminate(true);
        //ProgressBar would be displayed
    }
}
