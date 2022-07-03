package br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.R;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys.NewSportActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.activitys.SportActivity;
import br.edu.ifsp.arq.ads.dmos5.ifitness_dmos5.model.ActivityHistory;

public class ActivityAdapter extends ArrayAdapter<ActivityHistory> {

    private List<ActivityHistory> activitys;
    private Context context;

    public ActivityAdapter(Context context, int textViewResourceId, List<ActivityHistory> objects) {
        super(context, textViewResourceId, objects);
        this.activitys = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_list_layout, null);
        }

        ActivityHistory  activity = activitys.get(position);
        if(activity ==  null){
            return v;
        }

        TextView type = (TextView) v.findViewById(R.id.activity_type);
        TextView date = (TextView) v.findViewById(R.id.activity_date);
        TextView distance = (TextView) v.findViewById(R.id.activity_dist_data);
        TextView duration = (TextView) v.findViewById(R.id.activity_duration_data);

        Button btnEdt = (Button) v.findViewById(R.id.btn_edt);
        btnEdt.setOnClickListener(view -> edit(position));

        Button btnDel = (Button) v.findViewById(R.id.btn_del);
        btnDel.setOnClickListener(view -> delete(position));

        type.setText(activity.getType().getType());
        date.setText(activity.getDate());
        distance.setText(String.format("%.3f Km", activity.getDistance()));
        duration.setText(String.format("%.1f min", activity.getDuration()));

        return v;
    }

    private void edit(int pos){
        Intent intent = new Intent(context, NewSportActivity.class);
        intent.putExtra("activity", activitys.get(pos));
        context.startActivity(intent);
    }

    private void delete(int pos){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
        msgBox.setTitle("Excluindo...");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Deseja realmente excluir esta atividade?");

        msgBox.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SportActivity sa = (SportActivity) context;
                sa.deleteActivity(pos);
            }
        });

        msgBox.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        msgBox.show();
    }
}
