package com.example.niklas.bookmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.niklas.bookmanagement.data.Book;
import com.example.niklas.bookmanagement.net.SimpleClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private static String reqUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    TextView view;
    EditText num;
    ImageView imageView;

    String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num =(EditText) findViewById(R.id.editText);

        view = (TextView) findViewById(R.id.textView);

        view.setText("");

        final Button sendButton = (Button) findViewById(R.id.button2);
        final Button cameraButton = (Button) findViewById(R.id.button3);
        final Button checkButton = (Button) findViewById(R.id.button4);
        imageView = (ImageView) findViewById(R.id.imageView);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //requestData(num.getText().toString());
                try {
                    ip = num.getText().toString();
                    final String resp = SimpleClient.sendRequestAndReceiveResponse(ip,8000, "CHECK");
                    /*if(resp.contains("OK"))
                        view.setText("IP OK");
                    else
                        view.setText("IP NOT PROVED");*/
                    view.setText(resp);
                } catch (Exception e) {
                    view.setText("FAILURE");
                }
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText("");
                scanBarcode(v);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String resp = SimpleClient.sendRequestAndReceiveResponse(ip,8000, "NEW"+view.getText().toString());
                    if(resp.contains("ALRIGHT"))
                        view.setText("Successful");
                    else
                        view.setText("Uncertain");
                }
                catch(Exception e)
                {
                    view.setText("Fehler");
                }
            }
        });
    }

    public void scanBarcode(View v)
    {
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0)
        {
            if(resultCode == CommonStatusCodes.SUCCESS)
            {
                if (data != null)
                {
                    try {
                        Barcode barcode = data.getParcelableExtra("barcode");
                        //final BigInteger isbn = new BigInteger(barcode.displayValue);
                        requestData(barcode.displayValue);
                        //view.setText(barcode.displayValue);
                        //num.setText(barcode.displayValue);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    view.setText("Leider kein ISBN gefunden");
                }
            }

        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void requestData(String isbn)
    {
        final String url = reqUrl + isbn;
        final RequestQueue q = Volley.newRequestQueue(this);
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //view.setText(response.substring(0, 400));
                try {
                    JSONObject o = new JSONObject(response);
                    final Book book = new Book(o.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo"));
                    view.setText(book.toJson().toString());

                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(book.getImgLink());
                                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                imageView.setImageBitmap(bmp);
                            }
                            catch(Exception e)
                            {}
                        }
                    }.start();
                    //view.setText(book.toString());
                }
                catch(JSONException e)
                {
                    view.setText(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.setText("Fehler");
            }
        });
        q.add(req);
    }
}
