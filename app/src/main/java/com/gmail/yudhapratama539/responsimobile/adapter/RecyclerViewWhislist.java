package com.gmail.yudhapratama539.responsimobile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.yudhapratama539.responsimobile.FormActivity;
import com.gmail.yudhapratama539.responsimobile.R;
import com.gmail.yudhapratama539.responsimobile.model.Whislist;

import static  com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.CONTENT_URI;

public class RecyclerViewWhislist extends RecyclerView.Adapter<RecyclerViewWhislist.WhislistViewHolder> {
    private Cursor listNotes;
    private Activity activity;

    public RecyclerViewWhislist(Activity activity) {
        this.activity = activity;
    }

    public void setListWishes(Cursor listNotes) {
        this.listNotes = listNotes;
    }

    @Override
    public WhislistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_whislist, parent, false);
        return new WhislistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WhislistViewHolder holder, int position) {
        final Whislist whislist = getItem(position);
        holder.tvKeinginan.setText(whislist.getKeinginan());
        holder.tvTanggal.setText(whislist.getDate());
        holder.tvHarga.setText(whislist.getHarga());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, FormActivity.class);

                // Set intent dengan data uri row catetan by id
                // content://com.dicoding.mynotesapp/catetan/id
                Uri uri = Uri.parse(CONTENT_URI + "/" + whislist.getId());
                intent.setData(uri);
                //intent.putExtra(FormActivity.EXTRA_POSITION, position);
                //intent.putExtra(FormActivity.EXTRA_NOTE, catetan);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private Whislist getItem(int position) {
        if (!listNotes.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Whislist(listNotes);
    }

    class WhislistViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal, tvKeinginan, tvHarga;
        CardView cvNote;

        public WhislistViewHolder(View itemView) {
            super(itemView);
            tvTanggal = (TextView) itemView.findViewById(R.id.tv_item_date);
            tvKeinginan = (TextView) itemView.findViewById(R.id.tv_item_keinginan);
            tvHarga = (TextView) itemView.findViewById(R.id.tv_item_harga);
            cvNote = (CardView) itemView.findViewById(R.id.cv_item_wish);
        }
    }

    static class CustomOnItemClickListener implements View.OnClickListener {
        private int position;
        private OnItemClickCallback onItemClickCallback;

        public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
            this.position = position;
            this.onItemClickCallback = onItemClickCallback;
        }

        @Override
        public void onClick(View view) {
            onItemClickCallback.onItemClicked(view, position);
        }

        public interface OnItemClickCallback {
            void onItemClicked(View view, int position);
        }
    }
}
