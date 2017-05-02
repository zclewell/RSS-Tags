package clewell.rsstags;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void ArticleConstructorTest() throws Exception {
        Article article = new Article("title","description","link","pubDate");
        assertTrue(article.getTitle().equals("title"));
        assertTrue(article.getDescription().equals("description"));
        assertTrue(article.getLink().equals("link"));
        assertTrue(article.getPubDate().equals("pubDate"));
    }

    @Test
    public void ArticleSetterTest() throws Exception {
        Article article = new Article("","","","");
        article.setTitle("title");
        article.setDescription("description");
        article.setLink("link");
        article.setPubDate("pubDate");
        assertTrue(article.getTitle().equals("title"));
        assertTrue(article.getDescription().equals("description"));
        assertTrue(article.getLink().equals("link"));
        assertTrue(article.getPubDate().equals("pubDate"));
    }

    @Test
    public void addDataTest() {
        String TEST = "test";
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String[] check = new String[3];
        check[0] = "first";
        check[1] = "second";
        check[2] = "third";
        DatabaseReference myRef = database.getReference(TEST);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //data checked on data change
                int tempIndex = 0;
                for(DataSnapshot curr : dataSnapshot.getChildren()) { //iterates over all the data
                    assertTrue(curr.getValue().equals(check[tempIndex])); //checks data is correct
                    tempIndex++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.removeValue(); //clears old data
        myRef.push().setValue("first");
        myRef.push().setValue("second");
        myRef.push().setValue("third");
        myRef.removeValue(); //checks empty


    }

}
