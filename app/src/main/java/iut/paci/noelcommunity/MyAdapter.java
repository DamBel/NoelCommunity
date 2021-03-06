package iut.paci.noelcommunity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Damien_Admin2 on 10/02/2017.
 */

public class MyAdapter extends ArrayAdapter {

    private Context context;
    private List<District> data;
    private int customViewResourceId;

    public MyAdapter(Context context, int customViewResourceId, List<District> items){

        super(context, customViewResourceId, items);
        this.customViewResourceId = customViewResourceId;
        this.data = items;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View layout = convertView;
        ViewHolder holder;

        if (layout == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            layout = inflater.inflate(customViewResourceId, parent, false);
            holder = new ViewHolder();
            holder.id = (TextView) layout.findViewById(R.id.id);
            holder.nomImage = (TextView) layout.findViewById(R.id.nom);
            holder.image = (ImageView) layout.findViewById(R.id.img);
            layout.setTag(holder);
        } else {
            holder = (ViewHolder) layout.getTag();
        }

        District item = data.get(position);

        holder.id.setText(String.valueOf(item.getId()));
        holder.nomImage.setText(item.getName());
        holder.image.setImageResource(item.getImageResourceId());

        final District d = data.get(position);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MapActivity.class);

                Bundle extra = new Bundle();
                extra.putSerializable("district", d);

                intent.putExtras(extra);

                getContext().startActivity(intent);
            }
        });

        return layout;
    }

    static class ViewHolder {
        TextView id;
        TextView nomImage;
        ImageView image;

    }

}
