package eg.com.MtMs.mtmstask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.data.SourceLocation;
import eg.com.MtMs.mtmstask.gui.home.MapActivity;

import static eg.com.MtMs.mtmstask.utils.AppConstant.SOURCE_NAME;

public  class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.VH> {
    ArrayList <SourceLocation> sourceLocationArrayList;
    Context context;

    public SourceAdapter(ArrayList <SourceLocation> sourceLocationArrayList, Context context)
    {
        this.sourceLocationArrayList = sourceLocationArrayList;
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
        SourceLocation sourceLocation = sourceLocationArrayList.get(position);
        holder.locationname.setText(sourceLocation.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SOURCE_NAME = sourceLocation.getName();
                ((MapActivity)context).setSourceName(SOURCE_NAME);

            }
        });


    }

    @Override
    public int getItemCount() {
        return sourceLocationArrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView locationname;


        public VH(@NonNull View itemView) {
            super(itemView);
            locationname = itemView.findViewById(R.id.location_name);

        }
    }
}




