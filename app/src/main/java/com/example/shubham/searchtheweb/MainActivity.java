package com.example.shubham.searchtheweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String searchString;
    EditText searchBox;
    TextView errorMessage;
    Button google;
    Button yahoo;
    Button ddg;
    Button searchButton;
    Button stackoverflow;
    Intent callBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBox = (EditText) findViewById(R.id.search_text);
        google= (Button) findViewById(R.id.google_search_button);
        yahoo= (Button) findViewById(R.id.yahoo_search_button);
        ddg= (Button) findViewById(R.id.ddg_search_button);
        stackoverflow= (Button) findViewById(R.id.stackoverflow_search_button);
        errorMessage= (TextView) findViewById(R.id.search_error_text);
        callBrowser = new Intent(MainActivity.this, BrowserActivity.class);
        searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = searchBox.getText().toString();
                //errorMessage.setText(searchString);
                if(searchString.isEmpty()) {
                    errorMessage.setVisibility(View.VISIBLE);
                    google.setVisibility(View.INVISIBLE);
                    yahoo.setVisibility(View.INVISIBLE);
                    ddg.setVisibility(View.INVISIBLE);
                    stackoverflow.setVisibility(View.INVISIBLE);
                }
                else{
                    errorMessage.setVisibility(View.INVISIBLE);
                    google.setVisibility(View.VISIBLE);
                    yahoo.setVisibility(View.VISIBLE);
                    ddg.setVisibility(View.VISIBLE);
                    stackoverflow.setVisibility(View.VISIBLE);
                }
            }
        });
        google.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String search = searchString.replace(' ','+');
                String query = "https://www.google.com/#q="+search;
                callBrowser.putExtra("url_query",query);
                startActivity(callBrowser);
            }
        });
        yahoo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String search = searchString.replace(' ','+');
                String query = "https://search.yahoo.com/search;_ylt=A2oKmLQCQzxZKWMAyoa7HAx.;_ylc=X1MDMjExNDcyMzAwMwRfcgMyBGZyA3NmcARncHJpZAN3c1hYTU9uVlRkS3JKVjZ0blFZcFZBBG5fcnNsdAMwBG5fc3VnZwMxMARvcmlnaW4DaW4uc2VhcmNoLnlhaG9vLmNvbQRwb3MDMARwcXN0cgMEcHFzdHJsAwRxc3RybAM1BHF1ZXJ5A2hhcHB5BHRfc3RtcAMxNDk3MTIxNTQ5?p="+search+"&fr2=sb-top-in.search&fr=sfp&vm=r";
                callBrowser.putExtra("url_query",query);
                startActivity(callBrowser);
            }
        });
        ddg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String search = searchString.replace(' ','+');
                String query = "https://duckduckgo.com/?q="+search+"&t=hz&ia=web";
                callBrowser.putExtra("url_query",query);
                startActivity(callBrowser);
            }
        });
        stackoverflow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String search = searchString.replace(' ','+');
                String query = "https://stackoverflow.com/search?q="+search;
                callBrowser.putExtra("url_query",query);
                startActivity(callBrowser);
            }
        });
    }
}
