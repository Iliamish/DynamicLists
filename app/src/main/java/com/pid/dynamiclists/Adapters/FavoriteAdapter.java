package com.pid.dynamiclists.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pid.dynamiclists.Activities.MainActivity;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Models.StudentInfoObject;
import com.pid.dynamiclists.Models.StudentInfoSpecs;
import com.pid.dynamiclists.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;

    String[] rome = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII"};


    private LayoutInflater inflater;
    private int layout;
    //private static RecyclerViewClickListener itemListener;
    /**
     * The Context.
     */
    Context context;
    List<StudentInfoObject>  favorites;


    public FavoriteAdapter(Context context, List<StudentInfoObject>  favorites) {
        this.favorites = favorites;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder view, int position) {

        FavoriteAdapter.ViewHolder viewHolder  = (FavoriteAdapter.ViewHolder)view;
        StudentInfoObject user = favorites.get(position);

        viewHolder.nameView.setText(user.getDan().get(0));

        List<StudentInfoSpecs> specs = user.getApps();

        for(int i = 0; i < specs.size(); i++) {
            StudentInfoSpecs spec = specs.get(i);
            viewHolder.specsLayouts.get(i).setVisibility(View.VISIBLE);
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.spec_position)).setText(rome[i]);
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.spec)).setText(Html.fromHtml("<b>Направление: </b>" + spec.getSpec(), Html.FROM_HTML_MODE_COMPACT));
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.fac)).setText(Html.fromHtml("<b>Факультет: </b>" + spec.getFac(), Html.FROM_HTML_MODE_COMPACT));
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.form)).setText(Html.fromHtml("<b>Форма обучения: </b>" + spec.getFormname(), Html.FROM_HTML_MODE_COMPACT));
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.sum)).setText(Html.fromHtml("<b>Сумма баллов: </b>" + spec.getSummark(), Html.FROM_HTML_MODE_COMPACT));
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.status)).setText(Html.fromHtml("<b>Статус: </b><font color=\""+ Configuration.statusColorArray[Configuration.getStatusColor(spec.getStatus())]+"\">" + spec.getStatus() + "</font>", Html.FROM_HTML_MODE_COMPACT));
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.sogl)).setText(Html.fromHtml("<b>Согласие: </b>" + (spec.getOrig().equals("1") ? "<font color=\"#00ff00\">" : "<font color=\"#ff0000\">") + (spec.getOrig().equals("1") ? "да" : "нет") + "</font>", Html.FROM_HTML_MODE_COMPACT));
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.plan)).setText("Мест в плане\n" + spec.getKol());
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.current_place)).setText("Текущее\n" + spec.getMesto());
            ((TextView)viewHolder.specsLayouts.get(i).findViewById(R.id.place_with_original)).setText("С согласием\n" + spec.getIforig());
        }

        //viewHolder.nameView.setText(user.getFio());

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
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
        final ImageView arrowView;

        final List<View> specsLayouts;

        final ConstraintLayout layout;
        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean visible;
                    if (arrowView.getRotation() == 270) {
                        arrowView.animate().rotation(0);
                        visible = true;
                    } else {
                        arrowView.animate().rotation(270);
                        visible = false;
                    }

                    for (int i = 0; i < favorites.get(getAdapterPosition()).getApps().size(); i++) {
                        specsLayouts.get(i).setVisibility(visible ? View.VISIBLE : View.GONE);
                    }
                    //itemListener.recyclerViewListClicked(view, getLayoutPosition());
                }
            });
            nameView = view.findViewById(R.id.name_fav);
            arrowView = view.findViewById(R.id.arrow_exp);

            layout = view.findViewById(R.id.fav_constraint);

            View view1 = LayoutInflater.from(view.getContext()).inflate(R.layout.student_info_item, null);
            view1.setId(View.generateViewId());
            layout.addView(view1);


            ConstraintSet set = new ConstraintSet();
            set.clone(layout);
            set.connect(view1.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            set.connect(view1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            set.connect(view1.getId(), ConstraintSet.TOP, R.id.name_fav, ConstraintSet.BOTTOM);
            set.applyTo(layout);

            specsLayouts = new ArrayList<>();
            specsLayouts.add(view1);

            int prevId = view1.getId();

            for(int i = 0; i < 12 ; i++){

                View  view2= LayoutInflater.from(view.getContext()).inflate(R.layout.student_info_item, null);
                view2.setId(View.generateViewId());
                view2.setVisibility(View.GONE);
                layout.addView(view2);

                set = new ConstraintSet();
                set.clone(layout);
                set.connect(view2.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                set.connect(view2.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.connect(view2.getId(), ConstraintSet.TOP, prevId, ConstraintSet.BOTTOM);
                set.applyTo(layout);

                prevId = view2.getId();

                specsLayouts.add(view2);
            }

            for(int i = 0; i < specsLayouts.size() ; i++){
                final int finalI = i;
                specsLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Configuration.chousenSpec = favorites.get(getAdapterPosition()).getApps().get(finalI).getNrecspec();
                        Configuration.chousenFac = favorites.get(getAdapterPosition()).getApps().get(finalI).getNrecfac();
                        Configuration.chousenForm = favorites.get(getAdapterPosition()).getApps().get(finalI).getNrecform();
                        Configuration.chousenFin = favorites.get(getAdapterPosition()).getApps().get(finalI).getNrecfin();
                        Configuration.chousenLevel = favorites.get(getAdapterPosition()).getApps().get(finalI).getLevel();

                        ((MainActivity)context).changeLevel();
                        ((MainActivity)context).goToChousenFragment(R.id.navigation_type);
                    }
                });
            }
        }
    }
}
