package shop.ptrowinda.myhome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyBooking> myBooking;

    public BookingAdapter(Context c, ArrayList<MyBooking> p){
        context = c;
        myBooking = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mybooking, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.xnama_graha.setText(myBooking.get(position).getNama_graha());
        holder.xtype_rumah.setText("Perumahan " + myBooking.get(position).getType_rumah());
        holder.xtanggal.setText(myBooking.get(position).getTanggal() + "\nPukul " + myBooking.get(position).getJam() + " WIB");
        holder.xjumlah_booking.setText("x" + myBooking.get(position).getJumlah_booking() + " Booking");

        final String getIdBooking = myBooking.get(position).getId_booking();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomybookingdetails = new Intent(context, MyBookingDetailAct.class);
                gotomybookingdetails.putExtra("id_booking", getIdBooking);
                context.startActivity(gotomybookingdetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myBooking.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_graha, xtype_rumah, xtanggal, xjumlah_booking;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_graha = itemView.findViewById(R.id.xnama_graha);
            xtype_rumah = itemView.findViewById(R.id.xtype_rumah);
            xtanggal = itemView.findViewById(R.id.xtanggal);
            xjumlah_booking = itemView.findViewById(R.id.xjumlah_booking);
        }
    }
}
