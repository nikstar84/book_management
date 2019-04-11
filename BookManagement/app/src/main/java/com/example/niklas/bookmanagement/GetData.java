package com.example.niklas.bookmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.niklas.bookmanagement.data.Book;
import com.example.niklas.bookmanagement.net.SimpleClient;

import org.json.JSONObject;

public class GetData extends AppCompatActivity {

    String ip;
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        final Button autorB = (Button) findViewById(R.id.bAutor);
        final Button titleB = (Button) findViewById(R.id.bTitle);
        final Button isbnB = (Button) findViewById(R.id.bISBN);

        final EditText parameterF = (EditText) findViewById(R.id.tParam);

        view = (TextView) findViewById(R.id.reqResult);

        ip = "192.168.1.114";

        autorB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String req = "GET;BOOKSBYAUTHOR;"+parameterF.getText();
                requestData(req, true);
            }
        });

        titleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String req = "GET;BOOKBYTITLE;"+parameterF.getText();
                requestData(req, false);
            }
        });

        isbnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String req = "GET;BOOKBYID;"+parameterF.getText();
                requestData(req, false);
            }
        });
    }

    void requestData(String str, boolean multiple)
    {
        try {
            String res;
            res = SimpleClient.sendRequestAndReceiveResponse(ip,8000,str);
            if(multiple)
            {
                String [] t = res.split("\n");
                StringBuilder b = new StringBuilder();
                for(String i:t)
                {
                    b.append(new Book(i).toString());
                    if (t.length>1)
                        b.append("\n--------------------\n");
                }
                view.setText(b.toString());
            }
            else
                view.setText(new Book(res).toString());
        }
        catch(Exception e){
            view.setText("Faulty Connection");
            e.printStackTrace();
        }
    }
}
