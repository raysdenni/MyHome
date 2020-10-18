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

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.MyViewHolder> {
    Context context;
    ArrayList<ListUser> listUser;

    public ListUserAdapter(Context c, ArrayList<ListUser> p){
        context = c;
        listUser = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_listuser, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xnama_lengkap.setText(listUser.get(position).getNama_lengkap());
        holder.xlevel.setText(listUser.get(position).getLevel());
        holder.xsaldo.setText("Saldo : Rp. "+ NumberFormat.getNumberInstance().format(listUser.get(position).getUser_balance()));
        final  String getUrl_photo_profile = listUser.get(position).getUrl_photo_profile();
        Picasso.with(this.context).load(getUrl_photo_profile).centerCrop().fit().into(holder.foto_home_user);

        final String getUsername = listUser.get(position).getUsername();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoeditusers = new Intent(context, AdminPanelEditUser.class);
                gotoeditusers.putExtra("username", getUsername);
                context.startActivity(gotoeditusers);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_lengkap, xlevel, xsaldo;
        ImageView foto_home_user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_lengkap = itemView.findViewById(R.id.xnama_lengkap);
            xlevel = itemView.findViewById(R.id.xlevel);
            xsaldo = itemView.findViewById(R.id.xsaldo);
            foto_home_user = itemView.findViewById(R.id.foto_home_user);

        }
    }
}
