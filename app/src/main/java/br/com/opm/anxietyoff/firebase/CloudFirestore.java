package br.com.opm.anxietyoff.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import br.com.opm.anxietyoff.model.User;
import br.com.opm.anxietyoff.ui.activity.StudentInterfaceActivity;

public class CloudFirestore {

    private FirebaseFirestore db;

    public CloudFirestore() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveUser(User user, String UID) {
        db.collection("users").document(UID).set(user);
    }

    public void configureFirestoreSettings() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getUserByUID(String UID, final Context context){
        DocumentReference docRef = db.collection("cities").document(UID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user=task.getResult().toObject(User.class);
                }
                else
                    Toast.makeText(context, "Erro em recuperar dados do usuário, tente reconectar-se.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
