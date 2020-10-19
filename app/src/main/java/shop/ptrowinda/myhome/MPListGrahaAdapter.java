package shop.ptrowinda.myhome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MPListGrahaAdapter extends RecyclerView.Adapter<MPListGrahaAdapter.MyViewHolder> {
    Context context;
    ArrayList<MPListGraha> mpListGraha;

    public MPListGrahaAdapter(Context c, ArrayList<MPListGraha> p){
        context = c;
        mpListGraha = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.mp_item_listgraha, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.xnama_graha.setText(mpListGraha.get(position).getNama_graha());
        holder.xtype_rumah.setText(mpListGraha.get(position).getType_rumah());
        holder.xharga_graha.setText("Harga Booking : Rp. "+ NumberFormat.getNumberInstance().format(mpListGraha.get(position).getHarga_graha()));
        final  String getUrl_thumbnail = mpListGraha.get(position).getUrl_thumbnail();
        Picasso.with(this.context).load(getUrl_thumbnail).centerCrop().fit().into(holder.thumbnail_graha);

        final String getNama_graha = mpListGraha.get(position).getNama_graha();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoeditgraha = new Intent(context, MarketingPanelEditGraha.class);
                gotoeditgraha.putExtra("nama_graha", getNama_graha);
                context.startActivity(gotoeditgraha);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mpListGraha.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_graha, xtype_rumah, xharga_graha;
        ImageView thumbnail_graha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_graha = itemView.findViewById(R.id.xnama_graha);
            xtype_rumah = itemView.findViewById(R.id.xtype_rumah);
            xharga_graha = itemView.findViewById(R.id.xharga_graha);
            thumbnail_graha = itemView.findViewById(R.id.thumbnail_graha);
        }
    }
}
