package com.example.actividad2;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.actividad2.interfaces.JsonPlaceholderApi;
import com.example.actividad2.models.Post;
import com.example.actividad2.util.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private EditText etId;
    private EditText etTitle;
    private EditText etBody;
    private Button btnRetrieve;
    private Button btnUpdate;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Editar y eliminar");

        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Deshabilita los botones de "Actualizar" y "Eliminar" inicialmente
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postId = Integer.parseInt(etId.getText().toString());

                // Utiliza Retrofit para obtener la publicación por su ID
                JsonPlaceholderApi jsonApi = RetrofitClient.getClient();
                Call<Post> call = jsonApi.getPost(postId);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            Post post = response.body();
                            etTitle.setText(post.getTitle());
                            etBody.setText(post.getBody());

                            // Habilita los botones de "Actualizar" y "Eliminar" después de consultar
                            btnUpdate.setEnabled(true);
                            btnDelete.setEnabled(true);
                        } else {
                            // Manejar errores
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        // Manejar errores de red
                        showErrorDialog();
                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postId = Integer.parseInt(etId.getText().toString());
                String title = etTitle.getText().toString();
                String body = etBody.getText().toString();

                Post updatedPost = new Post(postId, title, body);

                // Utiliza Retrofit para actualizar la publicación
                JsonPlaceholderApi jsonApi = RetrofitClient.getClient();
                Call<Post> call = jsonApi.updatePost(postId, updatedPost);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            // Publicación actualizada con éxito
                            showSuccessDialog("Publicación actualizada con éxito");
                        } else {
                            // Manejar errores
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        // Manejar errores de red
                        showErrorDialog();
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postId = Integer.parseInt(etId.getText().toString());

                // Mostrar una alerta de confirmación antes de eliminar
                showConfirmationDialog(postId);
            }
        });
    }

    // Método para mostrar una alerta de confirmación
    private void showConfirmationDialog(final int postId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar esta publicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Utiliza Retrofit para eliminar la publicación
                JsonPlaceholderApi jsonApi = RetrofitClient.getClient();
                Call<Void> call = jsonApi.deletePost(postId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Publicación eliminada con éxito
                            showSuccessDialog("Publicación eliminada con éxito");
                        } else {
                            // Manejar errores
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showErrorDialog();
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No realizar la eliminación
            }
        });
        builder.show();
    }

    // Método para mostrar una alerta de éxito
    private void showSuccessDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Acción Completada");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Manejar la acción de "atrás" aquí (por ejemplo, cerrar la actividad actual)
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}