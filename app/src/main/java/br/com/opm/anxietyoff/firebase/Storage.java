package br.com.opm.anxietyoff.firebase;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.ui.activity.SignUpActivity;

public class Storage {

    private FirebaseStorage firebaseStorage;
    private Context context;
    private List<UploadTask> tasks;

    public Storage(Context context) {
        firebaseStorage = FirebaseStorage.getInstance();
        this.context = context;
        this.tasks=new ArrayList<>();
    }

    public boolean isAllComplete() {

        for(int i=0;i<tasks.size();i++){
            if(!tasks.get(i).isComplete()) return false;
        }

        return true;
    }

    public boolean cancelTasks(){
        boolean successful=true;
        for(int i=0;i<tasks.size();i++){
            UploadTask aux = tasks.get(i);
            if(!aux.isComplete() && !aux.cancel())successful=false;
        }
        return successful;
    }

    public void uploadProfileImage(final File file) {

        final StorageReference ref = firebaseStorage.getReference().child("images/profile/" + file.getName());
        final SignUpActivity signUpActivity =(SignUpActivity) context;
        final ProgressBar progressBar= signUpActivity.findViewById(R.id.activity_sign_in_progressBar);

        progressBar.setVisibility(View.VISIBLE);

        UploadTask uploadTask = ref.putFile(Uri.fromFile(file));
        uploadTask.addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) throw task.getException();
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    signUpActivity.setPhoto(task.getResult());
                } else
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        tasks.add(uploadTask);//adiciona task na lista de tasks
    }

}
