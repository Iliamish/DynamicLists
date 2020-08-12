package com.pid.dynamiclists.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pid.dynamiclists.Activities.MainActivity;
import com.pid.dynamiclists.Configuration;
import com.pid.dynamiclists.Fragments.StudentFragment;
import com.pid.dynamiclists.Models.DynamicListObject;
import com.pid.dynamiclists.Models.Place;
import com.pid.dynamiclists.Models.Student;
import com.pid.dynamiclists.Models.StudentInfoObject;
import com.pid.dynamiclists.Models.Subject;
import com.pid.dynamiclists.Network.NetworkService;
import com.pid.dynamiclists.R;
import com.pid.dynamiclists.StorageIO.StorageIO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DynamicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int CATEGORY_TYPE = 0;
    final int STUDENT_TYPE = 1;

    List<Student> students;

    private List<Place> places;

    private List<Subject> subjects;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    Context context;

    public DynamicListAdapter(Context context, DynamicListObject object) {
        this.students = object.getList();
        this.places = object.getPlaces();
        this.subjects = object.getSubjects();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == STUDENT_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_2, parent, false);
            return new DynamicListAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_priority_line, parent, false);
            return new DynamicListAdapter.CategoryHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder view, int position) {
        if (view instanceof DynamicListAdapter.ViewHolder) {
            DynamicListAdapter.ViewHolder viewHolder  = (DynamicListAdapter.ViewHolder)view;
            Student student = students.get(position) ;

            viewBinderHelper.setOpenOnlyOne(true);
            viewBinderHelper.bind(viewHolder.swipelayout, String.valueOf(position));
            viewBinderHelper.closeLayout(String.valueOf(position));

            viewHolder.nameView.setText(student.getFio().length() > 30 ? student.getFio().substring(0, 27) + "..." : student.getFio());
            viewHolder.numView.setText(student.getMesto());
            viewHolder.pointsView.setText((Configuration.chousenLevel != 3 ? "Общий балл: " : "Конкурсный балл: ") + (!student.getCategnum().equals("50") ? student.getSummark() : "БВИ"));
            viewHolder.blankView.setText(Html.fromHtml("Согласие: " + (student.getSogl().equals("1") ? "<font color=\"#00ff00\">" : "<font color=\"#ff0000\">") + (student.getSogl().equals("1") ? "да" : "нет") + "</font>", Html.FROM_HTML_MODE_COMPACT));
            viewHolder.statusLabelView.setText("Статус: ");
            viewHolder.statusView.setText(student.getStatus());

            switch (student.getStatus()){
                case "зачислен":
                    viewHolder.statusView.setTextColor(context.getResources().getColor(R.color.colorGreen, context.getTheme()));
                    break;
                case "к зачислению":
                    viewHolder.statusView.setTextColor(context.getResources().getColor(R.color.colorOrange, context.getTheme()));
                    break;
                case "заявление принято":
                    viewHolder.statusView.setTextColor(context.getResources().getColor(R.color.colorPrimary, context.getTheme()));
                    break;
                default:
                    viewHolder.statusView.setTextColor(context.getResources().getColor(R.color.colorRed, context.getTheme()));
                    break;
            }
//            viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            viewHolder.mainLayout.setBackgroundColor(Color.parseColor(Configuration.waveColorArray[Integer.parseInt(student.getWave())]));

            viewHolder.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetworkService.getInstance().getJSONApiUNN().getAbiturient(student.getNrecabit()).enqueue(new Callback<StudentInfoObject>() {
                        @Override
                        public void onResponse(Call<StudentInfoObject> call, Response<StudentInfoObject> response){
                            System.out.println(response.body());
                            Configuration.currentScrollPosition = position;
                            ((MainActivity)context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container2, new StudentFragment(response.body(), student.getNrecabit()), "StudentFragment_Tag").addToBackStack("List_tag").commit();
                        }

                        @Override
                        public void onFailure(Call<StudentInfoObject> call, Throwable t) {
                            System.out.println(call.request().url());
                            System.out.println("Error occurred while getting request!");
                            t.printStackTrace();
                        }
                    });
                }
            });

            List<String> marks = student.getMarksList();

            if(student.isExpanded) {
                viewHolder.bottomLayout.setVisibility(View.VISIBLE);
            }else{
                viewHolder.bottomLayout.setVisibility(View.GONE);

            }

            viewHolder.favourite.setChecked(student.isFavourite);

            for (int i = 0; i < subjects.size(); i++){

                viewHolder.marksViews.get(i).setText((marks.get(i).equals("-1") ? "\u231b" : marks.get(i)));
                if(student.getCategnum().equals("50")){
                    viewHolder.marksViews.get(i).setVisibility(View.GONE);
                    viewHolder.subjViews.get(i).setVisibility(View.GONE);
                }else{
                    viewHolder.marksViews.get(i).setVisibility(View.VISIBLE);
                    viewHolder.subjViews.get(i).setVisibility(View.VISIBLE);
                }

                if(i < subjects.size() -1){
                    viewHolder.subjViews.get(i).setText(subjects.get(i).getName() + ":");
                }else{
                    if(subjects.get(i).getName().substring(0, subjects.get(i).getName().length() - 5).equals("Балл за индивидуальные достижения")) {
                        viewHolder.subjViews.get(i).setText("Индивидуальные достижения: ");
                        viewHolder.subjViews.get(i).setVisibility(View.VISIBLE);
                        viewHolder.marksViews.get(i).setVisibility(View.VISIBLE);
                    }else{
                        viewHolder.subjViews.get(i).setText(subjects.get(i).getName() + ":");
                    }
                }
            }


        }else if(view instanceof DynamicListAdapter.CategoryHolder){
            DynamicListAdapter.CategoryHolder viewHolder  = (DynamicListAdapter.CategoryHolder)view;
            Student student = students.get(position);

            String[] params = view.itemView.getContext().getResources().getStringArray(R.array.categories);



            int pos = Integer.parseInt(student.getCategnum());
            switch (pos){
                case 8:
                    pos = 5;
                    break;
                case 10:
                    pos = 6;
                    break;
                case 50:
                    pos = 8;
                    break;
                case 100:
                    pos = 8;
                    break;
                case -1:
                    pos = 9;
                    break;
                    default:
                        break;
            }

            int pos2 = 0;
            for (int i = 0; i < places.size(); i++) {
                if(places.get(i).getCategnum().equals(student.getCategnum())){
                    pos2 = i;
                    break;
                }
            }

            if(places.size() != 0) {
                viewHolder.nameView.setText(params[pos] + " Количество мест: " + places.get(pos2).getValue());
            }else{
                viewHolder.nameView.setText(params[pos]);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(students.get(position).getFio() != null)
            return STUDENT_TYPE;
        else
            return CATEGORY_TYPE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView nameView, numView, pointsView, blankView, statusView, statusLabelView;
        final ConstraintLayout bottomLayout, mainLayout;
        final List<TextView> marksViews;
        final List<TextView> subjViews;
        final ToggleButton favourite;

        final SwipeRevealLayout swipelayout;
        final Button moreBtn;

        @Override
        public void onClick(View v) {
            if(bottomLayout.getVisibility() == View.GONE) {

                bottomLayout.setVisibility(View.VISIBLE);
                /*bottomLayout.setAlpha(0f);

                bottomLayout.animate().alpha(1f).setDuration(200).setListener(null);*/

                students.get(getAdapterPosition()).isExpanded = true;
                //view.setElevation(8);
                //bottomLayout.setElevation(1);
            }else{
                bottomLayout.setVisibility(View.GONE);
                students.get(getAdapterPosition()).isExpanded = false;

                //view.setElevation(2);
            }
        }


        ViewHolder(View view){
            super(view);

            view.setOnClickListener(this);

            swipelayout = view.findViewById(R.id.swipelayout);

            moreBtn = view.findViewById(R.id.more_info);
            bottomLayout = view.findViewById(R.id.linear_bottom);
            mainLayout = view.findViewById(R.id.main_layout);

            mainLayout.setOnClickListener(this);
            nameView = view.findViewById(R.id.fio);
            numView = view.findViewById(R.id.position);
            pointsView = view.findViewById(R.id.points);
            blankView = view.findViewById(R.id.blank);
            statusView = view.findViewById(R.id.status);
            statusLabelView = view.findViewById(R.id.status_label);
            favourite = view.findViewById(R.id.fav_button);

            favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    students.get(getAdapterPosition()).isFavourite = isChecked;

                    String fileInput = StorageIO.readFile(context.getFilesDir(), "favoriteList");

                    List<String> list;

                    if(isChecked){
                        if(fileInput.equals("")) {
                            list = new ArrayList<>();
                        }
                        else{
                            Type listType = new TypeToken<ArrayList<String>>() {
                            }.getType();
                            list = new Gson().fromJson(fileInput, listType);
                        }
                        if(list.indexOf(students.get(getAdapterPosition()).getNrecabit()) == -1) {
                            list.add(students.get(getAdapterPosition()).getNrecabit());
                        }
                    }else{
                        Type listType = new TypeToken<ArrayList<String>>() {
                        }.getType();
                        list = new Gson().fromJson(fileInput, listType);

                        list.remove(students.get(getAdapterPosition()).getNrecabit());
                    }
                    StorageIO.writeFile(context.getFilesDir(), "favoriteList", new Gson().toJson(list));
                }
            });

            marksViews = new ArrayList<>();

            marksViews.add((TextView) view.findViewById(R.id.mark1));
            marksViews.add((TextView) view.findViewById(R.id.mark2));
            marksViews.add((TextView) view.findViewById(R.id.mark3));
            marksViews.add((TextView) view.findViewById(R.id.mark4));
            marksViews.add((TextView) view.findViewById(R.id.mark5));
            marksViews.add((TextView) view.findViewById(R.id.mark6));


            subjViews = new ArrayList<>();

            subjViews.add((TextView) view.findViewById(R.id.subj1));
            subjViews.add((TextView) view.findViewById(R.id.subj2));
            subjViews.add((TextView) view.findViewById(R.id.subj3));
            subjViews.add((TextView) view.findViewById(R.id.subj4));
            subjViews.add((TextView) view.findViewById(R.id.subj5));
            subjViews.add((TextView) view.findViewById(R.id.subj6));


            for (int i = 0; i < subjects.size(); i++){
                marksViews.get(i).setVisibility(View.VISIBLE);
                subjViews.get(i).setVisibility(View.VISIBLE);
            }

            /*infoView = view.findViewById(R.id.text2);
            image = view.findViewById(R.id.type_icon);*/
        }
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{
        final TextView nameView;


        CategoryHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.categ);
        }
    }
}
