package com.patels95.sanam.ribbit.view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.patels95.sanam.ribbit.R;
import com.patels95.sanam.ribbit.model.FileHelper;
import com.patels95.sanam.ribbit.model.ParseConstants;

import java.util.ArrayList;
import java.util.List;

public class RecipientsActivity extends ActionBarActivity
        implements RecipientsFragment.OnFragmentInteractionListener {

    public static final String TAG = RecipientsActivity.class.getSimpleName();

    protected static MenuItem mSendMenuItem;
    protected Uri mMediaUri;
    protected String mFileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_recipients);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, RecipientsFragment.newInstance(), getString(R.string.recipients_tag))
                .commit();
//        fragmentManager.executePendingTransactions();

        mMediaUri = getIntent().getData();
        mFileType = getIntent().getExtras().getString(ParseConstants.KEY_FILE_TYPE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipients, menu);
        mSendMenuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            ParseObject message = createMessage();
//            send(message);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected ParseObject createMessage() {
        ParseObject message = new ParseObject(ParseConstants.CLASS_MESSAGES);
        RecipientsFragment recipientsFragment = (RecipientsFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.recipients_tag));
        message.put(ParseConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
        message.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
        message.put(ParseConstants.KEY_RECIPIENT_IDS, recipientsFragment.getRecipientIds());
        message.put(ParseConstants.KEY_FILE_TYPE, mFileType);

        byte[] fileBytes = FileHelper.getByteArrayFromFile(this, mMediaUri);
        if (fileBytes == null) {
            return null;
        }
        else {
            if (mFileType.equals(ParseConstants.TYPE_IMAGE)) {
                fileBytes = FileHelper.reduceImageForUpload(fileBytes);
            }
        }

        return message;
    }

    public static MenuItem getMenuItem(){
        return mSendMenuItem;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
