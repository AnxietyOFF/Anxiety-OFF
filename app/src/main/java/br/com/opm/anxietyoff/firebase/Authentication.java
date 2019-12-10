package br.com.opm.anxietyoff.firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import br.com.opm.anxietyoff.model.User;
import br.com.opm.anxietyoff.ui.activity.LoginActivity;
import br.com.opm.anxietyoff.ui.activity.StudentInterfaceActivity;
import br.com.opm.anxietyoff.ui.activity.SuccessfulPasswordResetActivity;

public class Authentication {

    private final FirebaseAuth mAuth;
    private final Context context;
    private ProgressDialog dialog;

    public Authentication(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("pt_br");
    }

    public boolean check() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    private boolean isNull(String email, String password) {
        if (email.equals("") || password.equals("")) {
            Toast.makeText(context, "Campos inválidos", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void signUp(final User user) {

        if (isNull(user.getEmail(), user.getPassword())) return;

        dialog = ProgressDialog.show(context, "Cadastro",
                "Carregando...", true);
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            profileUpdate(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(context, StudentInterfaceActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        context.startActivity(intent);
                                    } else
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    private Task<Void> profileUpdate(User user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getName()).setPhotoUri(user.getPhotoUri()).build();
        return mAuth.getCurrentUser().updateProfile(profileUpdates);
    }

    public void login(String email, String password) {

        if (isNull(email, password)) return;
        dialog = ProgressDialog.show(context, "Login",
                "Carregando...", true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, StudentInterfaceActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                        } else
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void changePassword(String email) {
        dialog = ProgressDialog.show(context, "Resetar senha",
                "Enviando email...", true);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Intent intent = new Intent(context, SuccessfulPasswordResetActivity.class);
                    context.startActivity(intent);
                } else
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logoff() {
        mAuth.signOut();
    }

    public void delete(String pass) {

        dialog = ProgressDialog.show(context, "Autenticar senha",
                "Verificando...", true);

        FirebaseUser user = getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), pass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmação de exclusão de conta");
                    builder.setMessage("Tem certeza que quer excluir sua conta? Essa ação não pode ser desfeita.");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            dialog = ProgressDialog.show(context, "Excluir Conta",
                                    "Excluindo...", true);
                            getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    if (task.isSuccessful())
                                        context.startActivity(new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    else
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    AlertDialog alerta = builder.create();
                    alerta.show();
                } else
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

}
