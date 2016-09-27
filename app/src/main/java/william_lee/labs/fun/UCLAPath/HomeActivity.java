package william_lee.labs.fun.UCLAPath;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedp;
    private final String INDEX_KEY = "index";
    private final String DAY_KEY = "day";
    private Calendar cal;
    private boolean weekend;


    private ListView listView;
    private Adapter adap;
    private ArrayList<entry> list;

    private int savedIndex;
    private int savedDay;
    private int curDay;

    private AlertDialog.Builder askBuilder;
    private DialogInterface.OnClickListener askListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        curDay=getDay();
        if(checkWeekend(curDay)){
            list=new ArrayList<>();
            fixAdapter();
            Toast.makeText(getApplicationContext(), "none today!", Toast.LENGTH_SHORT).show();
        }
        else {
            sharedp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            //int curDay = getDay();
            if (!sharedp.contains(INDEX_KEY) && !sharedp.contains(DAY_KEY)
                    || sharedp.getInt(DAY_KEY, 0)!=curDay) {
                SharedPreferences.Editor edit = sharedp.edit();
                edit.putInt(INDEX_KEY, 0);
                edit.putInt(DAY_KEY, curDay);
                edit.commit();
                Toast.makeText(getApplicationContext(), "commited1", Toast.LENGTH_SHORT).show();
            }
            savedIndex = sharedp.getInt(INDEX_KEY, 0);
            savedDay = sharedp.getInt(DAY_KEY, Calendar.MONDAY);
            loadList(curDay);
            if(savedIndex>=list.size()){
                Toast.makeText(getApplicationContext(), "Done today!", Toast.LENGTH_LONG).show();
            }

            fixAdapter();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    entry get = (entry) adap.getItem(position);

                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+get.getLatitude()+","+get.getLongitude()+"&mode=w");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    if(position == savedIndex){
                        //ask
                        askListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        list.get(position).setStatus("Done");
                                        fixAdapter();
                                        SharedPreferences.Editor edit = sharedp.edit();
                                        int nextInd = Math.min(savedIndex+1, list.size());
                                        edit.putInt(INDEX_KEY, nextInd);
                                        edit.putInt(DAY_KEY, curDay);
                                        edit.commit();
                                        Toast.makeText(getApplicationContext(), "committed2", Toast.LENGTH_SHORT).show();
                                        savedIndex = nextInd;
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        askBuilder = new AlertDialog.Builder(HomeActivity.this);
                        askBuilder.setMessage("Are you done with this destination: "+get.getDescription()+"?")
                                .setPositiveButton("Yes",askListener).setNegativeButton("No",askListener).show();
                    }
                }
            });
        }
    }

    public void fixAdapter(){
        adap = new Adapter(HomeActivity.this, list);
        listView.setAdapter(adap);
    }

    public void loadList(int day){
        list = new ArrayList<>();
        switch(day){
            case Calendar.MONDAY:
                list.add(new entry("Math 170A - Lec 1 - MS 6229",34.069653, -118.442877, "9:00-9:50 AM"));
                list.add(new entry("COM SCI 1 - DIS 1A - PAB 1434A",34.070816, -118.441618,"2:00-2:50 PM"));
                list.add(new entry("COM SCI 31 - LEC 2 - Haines 39", 34.073014, -118.441189, "4:00-5:50 PM"));
                break;
            case Calendar.TUESDAY:
                list.add(new entry("EPS SCI 3 - Lec 1 - Moore 100",34.070484, -118.442739,"12:30-1:45 PM"));
                list.add(new entry("EPS SCI 3 - DIS 1E - Geology 5644",34.069295, -118.441794,"3:00-3:50 PM"));
                break;
            case Calendar.WEDNESDAY:
                list.add(new entry("Math 170A - Lec 1 - MS 6229",34.069653, -118.442877, "9:00-9:50 AM"));
                list.add(new entry("COM SCI 1 - SEM 1 - Dodd 147",34.073077, -118.439289,"2:00-2:50 PM"));
                list.add(new entry("COM SCI 31 - LEC 2 - Haines 39", 34.073014, -118.441189, "4:00-5:50 PM"));
                break;
            case Calendar.THURSDAY:
                list.add(new entry("Math 170A - DIS 1A - MS 6229",34.069653, -118.442877, "9:00-9:50 AM"));
                list.add(new entry("EPS SCI 3 - Lec 1 - Moore 100",34.070484, -118.442739,"12:30-1:45 PM"));
                break;
            case Calendar.FRIDAY:
                list.add(new entry("Math 170A - Lec 1 - MS 6229",34.069653, -118.442877, "9:00-9:50 AM"));
                list.add(new entry("COM SCI 31 - DIS 2F - Boelter 5419",34.069454, -118.443153,"4:00-5:50 PM"));
                break;
        }
        //TODO: mark things as done;
        for(int i=0;i<list.size();i++){
            list.get(i).setStatus(i<savedIndex ? "Done" : "Not done");
        }

    }

    public int getDay(){
        cal=Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public boolean checkWeekend(int day){
        return day==Calendar.SATURDAY||day==Calendar.SUNDAY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
