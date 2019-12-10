package br.com.opm.anxietyoff.firebase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

import br.com.opm.anxietyoff.lib.DateFactory;
import br.com.opm.anxietyoff.model.Answers;
import br.com.opm.anxietyoff.ui.fragment.RecyclerListFragment;

public class CloudFirestore {

    private final Context context;
    RecyclerListFragment frag;
    private FirebaseFirestore db;
    private Authentication authentication;

    public CloudFirestore(Context context) {
        this.context = context;
        authentication = new Authentication(context);
        db = FirebaseFirestore.getInstance();
    }

    public CloudFirestore(Context context, RecyclerListFragment frag) {
        this.context = context;
        this.frag = frag;
        authentication = new Authentication(context);
        db = FirebaseFirestore.getInstance();
    }

    public void configureFirestoreSettings() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
    }

    /*public void getUserByUID(String UID, final Context context){
        DocumentReference docRef = db.collection("users").document(UID);

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
    }*/

    public void addBeckTestAnswers(Answers answers) {
        String UID = authentication.getCurrentUser().getUid();
        String id= DateFactory.dateToString(answers.getDate());
        db.collection("tests/" + UID + "/beck").document(id).set(answers);
    }

    public void getBeckTestAnswers() {
        String UID = authentication.getCurrentUser().getUid();
        Query query = db.collection("tests/" + UID + "/beck").orderBy("date", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Answers> answers = task.getResult().toObjects(Answers.class);
                    frag.updateReportRecyclerView(answers);
                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void deleteBeckTestAnswers(Answers answers){
        String UID = authentication.getCurrentUser().getUid();
        String id=DateFactory.dateToString(answers.getDate());
        db.collection("tests/" + UID + "/beck").document(id)
            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
