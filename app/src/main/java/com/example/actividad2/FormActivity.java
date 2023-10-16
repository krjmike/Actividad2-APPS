package com.example.actividad2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.actividad2.interfaces.JsonPlaceholderApi;
import com.example.actividad2.models.Post;
import com.example.actividad2.util.RetrofitClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class FormActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextBody;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBody = findViewById(R.id.editTextBody);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtén los datos de los EditText
                String title = editTextTitle.getText().toString();
                String body = editTextBody.getText().toString();

                // Crea un objeto Post con los datos
                Post newPost = new Post(1, title, body); // Supongamos que el usuario ID es 1

                // Utiliza Retrofit para crear una nueva publicación
                JsonPlaceholderApi jsonApi = RetrofitClient.getClient();
                Call<Post> call = jsonApi.createPost(newPost);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                            builder.setTitle("Acción Completada");
                            builder.setMessage("La publicación se ha creado exitosamente.");
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Vuelve a la actividad anterior
                                    finish();
                                }
                            });
                            builder.show();
                        } else {
                            // Manejar errores
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
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
            }
        });
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