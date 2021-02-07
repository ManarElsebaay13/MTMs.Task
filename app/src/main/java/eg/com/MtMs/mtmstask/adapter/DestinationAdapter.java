package eg.com.MtMs.mtmstask.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.data.DestinationLocation;
import eg.com.MtMs.mtmstask.gui.home.MapActivity;

import static eg.com.MtMs.mtmstask.utils.AppConstant.DESTINATION_NAME;
import static eg.com.MtMs.mtmstask.utils.AppConstant.SOURCE_NAME;


public  class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.VH> {
    ArrayList <DestinationLocation> destinationLocationArrayList;
    Context context;

    public DestinationAdapter(ArrayList<DestinationLocation> destinationLocationArrayList, Context context)
    {
        this.destinationLocationArrayList = destinationLocationArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_card, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        DestinationLocation destinationLocation = destinationLocationArrayList.get(position);
        holder.locationname.setText(destinationLocation.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DESTINATION_NAME = destinationLocation.getName();
                ((MapActivity)context).setDestinationName(DESTINATION_NAME);



            }
        });


    }

    @Override
    public int getItemCount() {
        return destinationLocationArrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView locationname;


        public VH(@NonNull View itemView) {
            super(itemView);
            locationname = itemView.findViewById(R.id.location_name);

        }
    }
}




