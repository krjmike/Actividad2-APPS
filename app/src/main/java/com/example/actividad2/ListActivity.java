package com.example.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.actividad2.interfaces.JsonPlaceholderApi;
import com.example.actividad2.models.Post;
import com.example.actividad2.models.PostAdapter;
import com.example.actividad2.util.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private List<Post> postList;
    private ArrayAdapter<Post> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listView = findViewById(R.id.listView);
        postList = new ArrayList<>();
        PostAdapter adapter = new PostAdapter(this, postList);
        listView.setAdapter(adapter);

        // Utiliza Retrofit para obtener la lista de publicaciones y añadirla al adaptador
        JsonPlaceholderApi jsonApi = RetrofitClient.getClient();
        Call<List<Post>> call = jsonApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> posts = response.body();
                    postList.addAll(posts);
                    adapter.notifyDataSetChanged(); // Actualiza la vista de lista
                } else {
                    // Manejar errores
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Error de Red");
                builder.setMessage("No se pudo completar la operación debido a un problema de red.");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Puede tomar acciones adicionales aquí si es necesario
                    }
                });
                builder.show();
            }
        });

        listView.setAdapter(adapter);


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Manejar la acción de "atrás" aquí (por ejemplo, cerrar la actividad actual)
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}