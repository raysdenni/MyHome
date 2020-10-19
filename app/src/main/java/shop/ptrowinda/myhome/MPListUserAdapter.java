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

public class MPListUserAdapter extends RecyclerView.Adapter<MPListUserAdapter.MyViewHolder> {
    Context context;
    ArrayList<MPListUser> mpListUser;

    public MPListUserAdapter(Context c, ArrayList<MPListUser> p){
        context = c;
        mpListUser = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.mp_item_listuser, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xnama_lengkap.setText(mpListUser.get(position).getNama_lengkap());
        holder.xemail_address.setText(mpListUser.get(position).getEmail_address());
        holder.xsaldo.setText("Saldo : Rp. "+ NumberFormat.getNumberInstance().format(mpListUser.get(position).getUser_balance()));
        final  String getUrl_photo_profile = mpListUser.get(position).getUrl_photo_profile();
        Picasso.with(this.context).load(getUrl_photo_profile).centerCrop().fit().into(holder.foto_home_user);

        final String getUsername = mpListUser.get(position).getUsername();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoeditusers = new Intent(context, MarketingPanelTopup.class);
                gotoeditusers.putExtra("username", getUsername);
                context.startActivity(gotoeditusers);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mpListUser.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_lengkap, xemail_address, xsaldo;
        ImageView foto_home_user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_lengkap = itemView.findViewById(R.id.xnama_lengkap);
            xemail_address = itemView.findViewById(R.id.xemail_address);
            xsaldo = itemView.findViewById(R.id.xsaldo);
            foto_home_user = itemView.findViewById(R.id.foto_home_user);

        }
    }
}
