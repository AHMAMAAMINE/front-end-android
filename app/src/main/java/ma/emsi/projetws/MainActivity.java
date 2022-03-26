package ma.emsi.projetws;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ma.emsi.projetws.beans.Etudiant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button add;
    RequestQueue requestQueue;
    private ImageView imageView;
    private  String picturePath=null;
    String insertUrl = "http://192.168.1.113//PhpProject1/ws/createEtudiant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);
        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        ville = (Spinner) findViewById(R.id.ville);
        add = (Button) findViewById(R.id.add);
        m = (RadioButton) findViewById(R.id.m);
        f = (RadioButton) findViewById(R.id.f);
        add.setOnClickListener(this);
        imageView =(ImageView) findViewById(R.id.imageView2);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("ok","ok");
        if (v == add) {
            Log.d("oks","oks");
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST,
                    insertUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d(TAG, response);

                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();

                    Collection<Etudiant> etudiants = new Gson().fromJson(response, type);
                    for(Etudiant e : etudiants){
                        Log.d(TAG, e.toString());
                    }
                    Intent intent = new Intent(MainActivity.this, RecycleView.class);
                    startActivity(intent);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ok","erreur");
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String sexe = "";
                    if(m.isChecked())
                        sexe = "homme";
                    else
                        sexe = "femme";
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("nom", nom.getText().toString());
                    params.put("prenom", prenom.getText().toString());
                    params.put("ville", ville.getSelectedItem().toString());
                    params.put("sexe", sexe);
                    return params;
                }
            };
            requestQueue.add(request);
        }
        if(v==imageView){

            chooseImage(this);

        }
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode==RESULT_OK){
            Uri selectedImage=data.getData();
            String[] filePath={MediaStore.Images.Media.DATA};
            Cursor cursor=this.getContentResolver().query(selectedImage,filePath,null,null,null);
            cursor.moveToFirst();
            int columnIndex= cursor.getColumnIndex(filePath[0]);
            String imgPath=cursor.getString(columnIndex);
            cursor.close();
            Bitmap image= BitmapFactory.decodeFile((imgPath));
            imageView.setImageBitmap(image);
        }else if(requestCode==0 && resultCode==RESULT_OK){
            Bitmap image= BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(image);
        }
        else{
            Toast.makeText(this,"aucune image n'est selectionne",Toast.LENGTH_SHORT).show();
        }
    }
    private void prendreUnePhoto(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            String time=new SimpleDateFormat("yyyyMMdd__HHmmss").format(new Date());

            File photoDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File pictureFile=File.createTempFile("photo-"+time,".jpg",photoDir);
                picturePath=pictureFile.getAbsolutePath();
                Log.d("amineahmma", MainActivity.this.getApplicationContext().getPackageName()+".provider");
                Uri photoUri= FileProvider.getUriForFile(MainActivity.this,
                        MainActivity.this.getApplicationContext().getPackageName()+".provider",
                        pictureFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent,0);

            }catch (IOException e){
                e.printStackTrace();
            }
            }
    }

    private void chooseImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    prendreUnePhoto();
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
}
