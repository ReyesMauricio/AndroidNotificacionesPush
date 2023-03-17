package com.reyes.notificacionespushfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    TextView tvToken;
    Button btnObtener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvToken = findViewById(R.id.idToken);
        btnObtener = findViewById(R.id.btnObtener);

        registerToFireBaseTopic();
    }

    public void obtenerToken(View v){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(
                new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "No se pudo obtener el token", Toast.LENGTH_SHORT).show();
                        }else tvToken.setText(String.format("Token: %s", task.getResult()));
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error interno", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //SUBSCRIBIENDO TEMA PARA NOTIFICACIONES GLOBALES
    private void registerToFireBaseTopic() {
        Log.d("MainActivity", "Register");
        FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Log.d("MainActivity", "Subscribed Topic");
                    }else {
                        Log.e("Error Topic","Error Task Topic");
                    }
                }
        ).addOnFailureListener(
                e -> {
                    Log.e("Topic error", e.getMessage());
                }
        );
    }
}
