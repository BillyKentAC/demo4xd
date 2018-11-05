package com.software.miedo.demo4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.miedo.demo4.data.ComentariosRequestAPI;
import com.software.miedo.demo4.data.RestaurantesRequestAPI;
import com.software.miedo.demo4.data.ServiceGenerator;
import com.software.miedo.demo4.model.PostComentario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    EditText et_descripcion, et_username;
    ImageView iv_foto;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle("Agrega un comentario");

        id = getIntent().getStringExtra("id");

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        et_descripcion = (EditText) findViewById(R.id.et_description);
        et_username = (EditText) findViewById(R.id.et_username);
        iv_foto = (ImageView) findViewById(R.id.iv_foto);

        et_descripcion.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et_descripcion.setRawInputType(InputType.TYPE_CLASS_TEXT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comment, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;

            case R.id.toolbar_camara:
                dispatchTakePictureIntent();
                return true;

            case R.id.toolbar_enviar:
                enviarComentario();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void enviarComentario() {
        String comentario = et_username.getText() + "@" + et_descripcion.getText();
        //Log.i("JIJI", "ID: " + id);
        comentario = comentario.trim();

        ComentariosRequestAPI api = ServiceGenerator.createService(ComentariosRequestAPI.class);

        Call<String> call = api.postComment(comentario, "b5404121-f800-468f-9d7d-512ee26bef51");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Log.i("JIJI", response.body());
                    Log.i("JIJI", "Codigo : " + response.code());

                    if (response.code() == 201) {
                        Toast.makeText(CommentActivity.this, "Funciono xd", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("JIJI", t.getMessage());
                Toast.makeText(CommentActivity.this, "Error de envío", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.software.miedo.demo4.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if (imageBitmap != null) {
                iv_foto.setImageBitmap(imageBitmap);
            }*/
            try {
                File file = new File(mCurrentPhotoPath);
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(), Uri.fromFile(file));

                if (bitmap != null) {
                    iv_foto.setImageBitmap(bitmap);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error de I/O", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "Error al capturar la foto.", Toast.LENGTH_SHORT).show();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
