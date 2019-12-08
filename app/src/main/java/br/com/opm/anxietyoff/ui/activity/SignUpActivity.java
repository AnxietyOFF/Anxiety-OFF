package br.com.opm.anxietyoff.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.opm.anxietyoff.R;
import br.com.opm.anxietyoff.firebase.Authentication;
import br.com.opm.anxietyoff.firebase.Storage;
import br.com.opm.anxietyoff.model.User;

public class SignUpActivity extends AppCompatActivity {


    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int REQUEST_FROM_GALLERY = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    public static final int GALLERY_PERMISSION_CODE = 200;
    private Authentication authentication;
    private Storage storage;
    private EditText editName, editEmail, editPass, editPass2;
    private ImageView profileImage, blackCircle;
    private String currentTempPhotoPath;
    private Uri photoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        authentication = new Authentication(this);
        storage = new Storage(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
    }

    private void findViews() {
        editName = findViewById(R.id.activity_sign_up_editText_name);
        editEmail = findViewById(R.id.activity_sign_up_editText_email);
        editPass = findViewById(R.id.activity_sign_up_editText_pass);
        profileImage = findViewById(R.id.activity_sign_up_profile_image);
        blackCircle = findViewById(R.id.activity_sign_up_black_circle);
        editPass2 = findViewById(R.id.activity_sign_up_editText_pass2);
    }

    public void onClickRegister(View view) {

        if (!storage.isAllComplete())
            Toast.makeText(this, "Aguarde o upload atual terminar", Toast.LENGTH_SHORT).show();

        else {
            String name = editName.getText().toString(), email = editEmail.getText().toString(),
                    pass = editPass.getText().toString(), pass2 = editPass2.getText().toString();
            if (pass.equals(pass2)) {
                User user = new User(name, email, pass, photoUri);
                authentication.signUp(user);
            } else Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (storage.cancelTasks()) finish();
            else
                Toast.makeText(this, "Erro ao cancelar o upload atual, aguarde...", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (storage.cancelTasks()) super.onBackPressed();
        else
            Toast.makeText(this, "Erro ao cancelar o upload atual, aguarde...", Toast.LENGTH_SHORT).show();
    }

    public void onClickCamera(View view) {
        if (!storage.isAllComplete())
            Toast.makeText(this, "Aguarde o upload atual terminar", Toast.LENGTH_SHORT).show();
        else if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            accessCameraIntent();
        }
    }

    public void onClickGallery(View view) {
        if (!storage.isAllComplete())
            Toast.makeText(this, "Aguarde o upload atual terminar", Toast.LENGTH_SHORT).show();
        else if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PERMISSION_CODE);
        } else {
            accessGalleryIntent();
        }
    }

    private void accessGalleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_FROM_GALLERY);
    }

    private void accessCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createTempImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Erro ao criar arquivo de source", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "br.com.opm.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createTempImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + ".png";
        File image = new File(getCacheDir(), imageFileName);
        currentTempPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void uCrop() {
        Uri sourceUri = Uri.fromFile(new File(currentTempPhotoPath));

        Uri destinationUri = null;
        try {
            destinationUri = Uri.fromFile(createTempImageFile()); //pegando URI do arquivo de destino
        } catch (IOException e) {
            Toast.makeText(this, "Erro ao criar arquivo de destino", Toast.LENGTH_SHORT).show();
        }

        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1, 1);
        options.setCircleDimmedLayer(true);
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    public void setPhoto(Uri onlineUri) {
        profileImage.setColorFilter(null);
        this.photoUri = onlineUri;
        Glide.with(this).load(new File(currentTempPhotoPath)).apply(RequestOptions.circleCropTransform())
                .into(profileImage);
        blackCircle.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_TAKE_PHOTO: {
                if (resultCode == Activity.RESULT_OK) uCrop();
                else Toast.makeText(this, "Erro ao resgatar foto", Toast.LENGTH_SHORT).show();
                break;
            }
            case REQUEST_FROM_GALLERY: {
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    currentTempPhotoPath = getRealPathFromURI(selectedImage,
                            this);
                    uCrop();
                }
                break;
            }
            case UCrop.REQUEST_CROP: {
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    File image = new File(resultUri.getPath());
                    storage.uploadProfileImage(image);
                    blackCircle.setVisibility(View.VISIBLE);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                    Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Access", "Acesso à câmera garantido");
                    accessCameraIntent();
                } else {
                    Log.d("Access", "Acesso à câmera negado");
                }
                break;
            }
            case GALLERY_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Access", "Acesso à câmera garantido");
                    accessGalleryIntent();
                } else {
                    Log.d("Access", "Acesso à galeria negado");
                }
                break;
            }
        }

    }

}
