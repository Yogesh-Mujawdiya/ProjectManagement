package com.example.projectmanagement.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectmanagement.R;
import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> implements Filterable {

    private List<Project> ProjectList;
    private List<Project> FilteredList;
    Context context;

    public ProjectAdapter(Context context, List<Project> ProjectList) {
        this.context = context ;
        this.ProjectList=ProjectList;
        this.FilteredList = new ArrayList<>(this.ProjectList);
    }

    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_project, null);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectAdapter.ProjectViewHolder holder, int position) {
        Project product = FilteredList.get(position);
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewDomain.setText(String.valueOf(product.getDomain()));
        holder.textViewStudent.setText(String.valueOf(product.getSid()));
        holder.textViewGuide.setText(String.valueOf(product.getGid()));
    }


    @Override
    public int getItemCount() {
        return FilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Project> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(ProjectList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Project item : ProjectList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || item.getDomain().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            FilteredList.clear();
            FilteredList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDomain, textViewStudent, textViewGuide;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.tvTitle);
            textViewDomain = itemView.findViewById(R.id.tvDomain);
            textViewStudent = itemView.findViewById(R.id.tvSid);
            textViewGuide = itemView.findViewById(R.id.tvGid);
        }
    }

}
