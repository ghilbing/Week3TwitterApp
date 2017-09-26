package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static int REQUEST_CODE = 200;

    @Bind(R.id.etNewTweet)
    EditText etNewTweet;
    @Bind(R.id.btnNewTweet)
    Button btnNewTweet;

    TwitterClient client = TwitterApp.getRestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.bind(this);

        btnNewTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.postUpdate(etNewTweet.getText().toString(), new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // go to timeline
                                Tweet tweet = null;
                                tweet = Tweet.fromJson(response);

                                Intent intent = new Intent();
                                intent.putExtra("tweet", tweet);
                                setResult(REQUEST_CODE, intent);
                                finish();
                            }
                        }

                );
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
