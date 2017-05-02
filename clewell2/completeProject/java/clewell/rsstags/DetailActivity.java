package clewell.rsstags;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by zach on 4/24/17.
 */

public class DetailActivity extends AppCompatActivity {
    private TextView title;
    private TextView pubDate;
    private WebView web;
    private RecyclerView tags;
    private Button addButton;
    private String tag = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Intent intent = getIntent();
        final Context context = this;
        final Article article = intent.getParcelableExtra(ArticleAdapter.ARTICLE);
        final ArrayList<String> tagSet = new ArrayList<>();

        title = (TextView) findViewById(R.id.name);
        title.setText(article.getTitle());

        pubDate = (TextView) findViewById(R.id.date);
        pubDate.setText(article.getPubDate());

        web = (WebView) findViewById(R.id.article);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(article.getLink());

        final TagAdapter myAdapter = new TagAdapter(tagSet);
        tags = (RecyclerView) findViewById(R.id.tags);
        tags.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tags.setAdapter(myAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(article.getPubDate());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Takes tags and adds them to the tagSet if they are not already there
                for (DataSnapshot curr : dataSnapshot.getChildren()) {
                    if (!tagSet.contains(curr.getValue().toString())) {
                        tagSet.add(curr.getValue().toString());
                    }
                }
                //Notify adapter in order to update recycle view
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //do nothing
            }
        });


        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set up a dialog box for user to enter new tag

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText textField = new EditText(context);
                textField.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(textField);
                builder.setTitle("New Tag");

                //Setting up the submit actions
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tag = textField.getText().toString();
                        if (tag.equals("")) {
                            Toast.makeText(context, "Cannot enter empty tag", Toast.LENGTH_SHORT).show();
                        } else {
                            tag = tag.toLowerCase();
                            tag = tag.substring(0, 1).toUpperCase() + tag.substring(1);
                            FirebaseHelper.addTag(article, tag);
                            Toast.makeText(context, "Added: " + tag, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Setting up cancel action
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
}
