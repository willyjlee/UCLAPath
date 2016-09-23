package william_lee.labs.fun.UCLAPath;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by william_lee on 9/12/16.
 */
public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<entry> entries;
    private LayoutInflater layoutInflater;

    public Adapter (Context context, ArrayList<entry>entries) {
        this.context = context;
        this.entries = entries;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        entry entry = entries.get(position);
        View view = layoutInflater.inflate(R.layout.entry_view, null);

        TextView description = (TextView) view.findViewById(R.id.description);
        TextView datetime = (TextView) view.findViewById(R.id.datetime);
        TextView status = (TextView) view.findViewById(R.id.status);

        description.setText(entry.getDescription());
        datetime.setText(entry.getDatetime());
        status.setText(entry.getStatus());

        description.setBackgroundResource(R.color.uclaBlue);
        datetime.setBackgroundResource(R.color.uclaBlue);
        status.setBackgroundResource(R.color.uclaYellow);




        return view;
    }

}
