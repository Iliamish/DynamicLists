package com.pid.dynamiclists.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pid.dynamiclists.Activities.MainActivity;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Models.StudentInfoSpecs;
import com.pid.dynamiclists.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private int layout;

    String[] rome = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII"};
    /**
     * The Context.
     */
    Context context;
    private List<StudentInfoSpecs> specs;

    /**
     * Instantiates a new Search adapter.
     *
     * @param context      the context
     * @param specs        the specs
     */
    public StudentInfoAdapter(Context context, List<StudentInfoSpecs> specs) {
        this.specs = specs;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_info_item, parent, false);
        return new StudentInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder view, int position) {
            StudentInfoAdapter.ViewHolder viewHolder  = (StudentInfoAdapter.ViewHolder)view;
            StudentInfoSpecs spec = specs.get(position);

            viewHolder.positionView.setText(rome[position]);
            viewHolder.specView.setText(Html.fromHtml("<b>Направление: </b>" + spec.getSpec(), Html.FROM_HTML_MODE_COMPACT));
            viewHolder.facView.setText(Html.fromHtml("<b>Факультет: </b>"  + spec.getFac(), Html.FROM_HTML_MODE_COMPACT));
            viewHolder.formView.setText(Html.fromHtml("<b>Форма обучения: </b>"  + spec.getFormname(), Html.FROM_HTML_MODE_COMPACT));
            viewHolder.sumView.setText(Html.fromHtml("<b>Сумма баллов: </b>"  + spec.getSummark(), Html.FROM_HTML_MODE_COMPACT));
            viewHolder.statusView.setText(Html.fromHtml("<b>Статус: </b><font color=\""+ Configuration.statusColorArray[Configuration.getStatusColor(spec.getStatus())]+"\">" + spec.getStatus()  + "</font>", Html.FROM_HTML_MODE_COMPACT));
            viewHolder.soglView.setText(Html.fromHtml("<b>Согласие: </b>" + (spec.getOrig().equals("1") ? "<font color=\"#00ff00\">" : "<font color=\"#ff0000\">") + (spec.getOrig().equals("1") ? "да" : "нет") + "</font>", Html.FROM_HTML_MODE_COMPACT));
            viewHolder.planView.setText("Мест в плане\n" + spec.getKol());
            viewHolder.currentPlaceView.setText("Текущее\n" + spec.getMesto());
            viewHolder.placeWithOrigView.setText("С согласием\n" + spec.getIforig());
    }

    @Override
    public int getItemCount() {
        return specs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView positionView, facView, specView, formView, sumView, statusView, soglView, planView, currentPlaceView, placeWithOrigView;
        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        ViewHolder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Configuration.chousenSpec = specs.get(getAdapterPosition()).getNrecspec();
                    Configuration.chousenFac = specs.get(getAdapterPosition()).getNrecfac();
                    Configuration.chousenForm = specs.get(getAdapterPosition()).getNrecform();
                    Configuration.chousenFin = specs.get(getAdapterPosition()).getNrecfin();
                    Configuration.chousenLevel = specs.get(getAdapterPosition()).getLevel();

                    ((MainActivity)context).changeLevel();
                    ((MainActivity)context).goToChousenFragment(R.id.navigation_type);
                }
            });
            facView = view.findViewById(R.id.fac);
            positionView = view.findViewById(R.id.spec_position);
            specView = view.findViewById(R.id.spec);
            formView = view.findViewById(R.id.form);
            sumView = view.findViewById(R.id.sum);
            statusView = view.findViewById(R.id.status);
            soglView = view.findViewById(R.id.sogl);
            planView = view.findViewById(R.id.plan);
            currentPlaceView = view.findViewById(R.id.current_place);
            placeWithOrigView = view.findViewById(R.id.place_with_original);
        }
    }
}
