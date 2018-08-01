package com.gmail.yudhapratama539.responsimobile;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.gmail.yudhapratama539.responsimobile.adapter.RecyclerViewWhislist;

import static com.gmail.yudhapratama539.responsimobile.FormActivity.REQUEST_UPDATE;
import static com.gmail.yudhapratama539.responsimobile.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvNotes;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    private Cursor list;
    private RecyclerViewWhislist adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("MyWhistlist");

        rvNotes = (RecyclerView) findViewById(R.id.rv_whislist);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        adapter = new RecyclerViewWhislist(this);
        adapter.setListWishes(list);
        rvNotes.setAdapter(adapter);

        new LoadNoteAsync().execute();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add) {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, FormActivity.REQUEST_ADD);
        }
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setListWishes(list);
            adapter.notifyDataSetChanged();

            try {
                if (list.getCount() == 0) {
                    showSnackbarMessage("Tidak ada data saat ini");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FormActivity.REQUEST_ADD) {
            if (resultCode == FormActivity.RESULT_ADD) {
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil ditambahkan");
            }
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormActivity.RESULT_UPDATE) {
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil diubah");
            } else if (resultCode == FormActivity.RESULT_DELETE) {
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }
}