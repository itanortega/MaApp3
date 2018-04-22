package com.sime.demophoto;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ExecutorService queue = Executors.newSingleThreadExecutor();

    private EditText Txt_Codigo;
    private ImageView Img_Foto;
    private Button Btn_ObtenerFoto;

    private String localPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localPath = getApplicationContext().getFilesDir().getAbsolutePath();

        Txt_Codigo = (EditText) findViewById(R.id.Txt_Codigo);
        Img_Foto = (ImageView) findViewById(R.id.Img_Foto);
        Btn_ObtenerFoto = (Button) findViewById(R.id.Btn_ObtenerFoto);

        CAFData data = CAFData.dataWithContentsOfFile(localPath + "/LastPhoto.jpg");
        if(data != null){
            Bitmap bitmap = data.toImage();
            if(bitmap != null){
                Img_Foto.setImageBitmap(bitmap);
            }
        }

        Btn_ObtenerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoto();
            }
        });
    }

    public void showPhoto(){
        String code = Txt_Codigo.getText().toString();
        String strUrl = "http://acad.ucaldas.edu.co/fotos/";
        URL url = null;
        Runnable thread = null;

        if(!code.isEmpty()){
            strUrl += code + ".jpg";
            try {
                url = new URL(strUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if(url != null){
                final URL urlTemp = url;
                thread = new Runnable() {
                    @Override
                    public void run() {
                        CAFData data = CAFData.dataWithContentsOfURL(urlTemp);
                        Bitmap bitmap = null;
                        if(data!=null){
                            bitmap = data.toImage();
                            if(bitmap != null){
                                final Bitmap bitmapTmp = bitmap;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Img_Foto.setImageBitmap(bitmapTmp);
                                    }
                                });
                            }
                            data.writeToFile(localPath + "/LastPhoto.jpg", true);
                        }
                    }
                };
            }
        }

        queue.execute(thread);
    }
}
