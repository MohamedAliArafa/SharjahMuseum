package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaledbadawy on 9/12/2017.
 */

public class AutoCompleteAdapter extends ArrayAdapter<EventModel> {
    Context context;
    int resource;
    List<EventModel> items, tempItems, suggestions;

    public AutoCompleteAdapter(Context context, int resource, List<EventModel> items) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<EventModel>(items); // this makes the difference.
        suggestions = new ArrayList<EventModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_text_view, parent, false);
        }
        EventModel people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getTitle());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((EventModel) resultValue).getTitle();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (EventModel people : tempItems) {
                    if (people.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<EventModel> filterList = (ArrayList<EventModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (EventModel people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
