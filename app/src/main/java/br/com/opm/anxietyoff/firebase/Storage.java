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

import br.com.opm.anxietyoff.ui.activity.SignInActivity;

public class Storage {

    private FirebaseStorage firebaseStorage;
    private Context context;
    private boolean pendences;

    public Storage(Context context) {
        firebaseStorage = FirebaseStorage.getInstance();
        this.context = context;
        this.pendences = false;
    }

    public boolean isPendences() {
        return pendences;
    }

    public void uploadProfileImage(final File file, final ProgressBar progressBar) {

        final StorageReference ref = firebaseStorage.getReference().child("images/profile/" + file.getName());

        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        pendences = true;
        ref.putFile(Uri.fromFile(file)).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = 100.0 * (taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressBar.setProgress((int) progress);
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
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
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);
                if (task.isSuccessful()) {
                    SignInActivity signInActivity = (SignInActivity) context;
                    signInActivity.setPhoto(task.getResult(), Uri.fromFile(file));
                } else
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
