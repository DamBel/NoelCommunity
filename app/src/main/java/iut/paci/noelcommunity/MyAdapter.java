package iut.paci.noelcommunity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        ViewHolder holder = null;

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

        //TextView nameTV = (TextView) layout.findViewById(R.id.nom);
        //TextView numTV = (TextView) layout.findViewById(R.id.id);
        //ImageView flagIV = (ImageView) layout.findViewById(R.id.img);

        //nameTV.setText(data.get(position).getName());
        //flagIV.setImageResource(data.get(position).getImageResourceId());
        //numTV.setText(data.get(position).getId());

        return layout;
    }

    static class ViewHolder {
        TextView id;
        TextView nomImage;
        ImageView image;

    }

}
