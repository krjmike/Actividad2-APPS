package com.example.actividad2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_create) {
                    // Abre la actividad de creación de publicaciones
                    Intent createIntent = new Intent(MainActivity.this, FormActivity.class);
                    startActivity(createIntent);
                } else if (id == R.id.menu_read) {
                    // Abre la actividad de lista de publicaciones
                    Intent readIntent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(readIntent);
                } else if (id == R.id.menu_update) {
                    // Abre la actividad de detalles para editar la publicación
                    Intent updateIntent = new Intent(MainActivity.this, DetailsActivity.class);
                    // Agrega información adicional, como el ID de la publicación que se va a editar
                    updateIntent.putExtra("action", "edit");
                    startActivity(updateIntent);
                } else if (id == R.id.menu_delete) {
                    // Abre la actividad de detalles para eliminar la publicación
                    Intent deleteIntent = new Intent(MainActivity.this, DetailsActivity.class);
                    // Agrega información adicional, como el ID de la publicación que se va a eliminar
                    deleteIntent.putExtra("action", "delete");
                    startActivity(deleteIntent);
                }

                drawerLayout.closeDrawers(); // Cierra el menú de navegación
                return true;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView);
            } else {
                drawerLayout.openDrawer(navigationView);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}