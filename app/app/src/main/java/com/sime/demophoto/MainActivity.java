package com.sime.demophoto;

import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText Txt_Codigo;
    private ImageView Img_Foto;
    private Button Btn_ObtenerFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Txt_Codigo = (EditText) findViewById(R.id.Txt_Codigo);
        Img_Foto = (ImageView) findViewById(R.id.Img_Foto);
        Btn_ObtenerFoto = (Button) findViewById(R.id.Btn_ObtenerFoto);

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
                thread = new Runnable() {
                    @Override
                    public void run() {

                    }
                };
            }
        }
    }
}
