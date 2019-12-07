package br.com.opm.anxietyoff.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.firebase.Storage;
import br.com.opm.anxietyoff.model.User;
import br.com.opm.anxietyoff.picasso.CircleTransform;

public class SignInActivity extends AppCompatActivity {


    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private Authentication authentication;
    private Storage storage;
    private EditText editName, editEmail, editPass, editPhone;
    private ImageView profileImage;
    private String currentPhotoPath;
    private ProgressBar progressBar;
    private Uri photoUri=null;
    private File profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        authentication = new Authentication(this);
        storage=new Storage(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
    }

    private void findViews() {
        editName = findViewById(R.id.activity_sign_in_editText_name);
        editEmail = findViewById(R.id.activity_sign_in_editText_email);
        editPass = findViewById(R.id.signIn_editText_pass);
        editPhone = findViewById(R.id.signIn_editText_phone);
        profileImage = findViewById(R.id.activity_sign_in_profile_image);
        progressBar = findViewById(R.id.activity_sign_in_progressBar);
    }

    public void onClickRegister(View view) {
        String name = editName.getText().toString(), email = editEmail.getText().toString(),
                pass = editPass.getText().toString(), phone = editPhone.getText().toString();

        User user = new User(name, email, pass, phone, photoUri);

        authentication.signIn(user);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickCamera(View view) {
        if(storage.isPendences()) Toast.makeText(this, "Aguarde o upload atual terminar", Toast.LENGTH_SHORT);
        else if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private File createProfileFile(File source) throws IOException {
        File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath()+"PNG_ProfilePhoto.png");
        //FODASE ESSA MERDA CARALHO!
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Erro ao criar arquivo de source", Toast.LENGTH_SHORT);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void uCrop() {
        Uri sourceUri = Uri.fromFile(new File(currentPhotoPath));
        try {
            File imageFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao criar arquivo de destino", Toast.LENGTH_SHORT);
        }
        Uri destinationUri = Uri.fromFile(new File(currentPhotoPath));

        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1, 1);
        options.setCircleDimmedLayer(true);
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_TAKE_PHOTO: {
                if (resultCode == Activity.RESULT_OK) uCrop();
                else Toast.makeText(this, "Erro ao resgatar foto", Toast.LENGTH_SHORT);
                break;
            }
            case UCrop.REQUEST_CROP: {
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    File image = new File(resultUri.getPath());
                    storage.uploadProfileImage(image, progressBar);
                    profileImage.setColorFilter(Color.argb(100, 0, 0, 0));
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                    Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_SHORT);
                }
                break;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Acesso à câmera garantido", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Acesso à câmera negado", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setPhoto(Uri onlineUri, Uri localUri){
        profileImage.setColorFilter(null);
        this.photoUri=onlineUri;
        Picasso.get().load(localUri).transform(new CircleTransform()).into(profileImage);
    }

}
