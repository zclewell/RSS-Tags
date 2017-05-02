package clewell.rsstags;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by zach on 4/25/17.
 * Manages interactions with Firebase Database
 */

public class FirebaseHelper {
    public static void addTag(Article article, String tag) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = article.getPubDate();
        DatabaseReference myRef = database.getReference(key);
        myRef.push().setValue(tag);
    }
}
