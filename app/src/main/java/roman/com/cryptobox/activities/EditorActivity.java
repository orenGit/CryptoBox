package roman.com.cryptobox.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import hugo.weaving.DebugLog;
import roman.com.cryptobox.R;
import roman.com.cryptobox.dataobjects.MockNote;

public class EditorActivity extends AppCompatActivity {

    private boolean mIsInEditingMode = false;

    private EditText mDateEditText;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private MenuItem mMenuItem;
    private MockNote mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //get the note from the intent
        Intent intent = getIntent();
        mNote = intent.getExtras().getParcelable(MockNote.NOTE_KEY_STRING);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_editor_toolbar);
        setSupportActionBar(toolbar);

        //get the views
        mDateEditText = (EditText) findViewById(R.id.activity_editor_note_date);
        mTitleEditText = (EditText) findViewById(R.id.activity_editor_note_title);
        mContentEditText = (EditText) findViewById(R.id.activity_editor_note_content);

        //set the text to the views
        mDateEditText.setText(mNote.getLastModified());
        mTitleEditText.setText(mNote.getTitle());
        mContentEditText.setText(mNote.getContent());

    }

    @Override
    protected void onStop() {
        super.onStop();

        //save the changes
        mNote.setLastModified(mDateEditText.getText().toString());
        mNote.setTitle(mTitleEditText.getText().toString());
        mNote.setContent(mContentEditText.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_editor_menu, menu);
        mMenuItem = menu.findItem(R.id.activity_editor_edit_icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!mIsInEditingMode) {
            startEdit();
        } else {
            finishEdit();
        }
        return true;
    }

    public void onClickDate(View view) {
        //do nothing
    }

    public void onClickTitle(View view) {
        if (!mIsInEditingMode) {
            startEdit();
            mTitleEditText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(mTitleEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void onClickContent(View view) {
        if (!mIsInEditingMode) {
            startEdit();
            mContentEditText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(mContentEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void startEdit() {
        //set title stuff
        mTitleEditText.setFocusableInTouchMode(true);
        mTitleEditText.setFocusable(true);
        mTitleEditText.setBackground(getResources().getDrawable(R.drawable.bg_edit_text, getTheme()));
        //set content stuff
        mContentEditText.setFocusableInTouchMode(true);
        mContentEditText.setFocusable(true);
        mContentEditText.setBackground(getResources().getDrawable(R.drawable.bg_edit_text, getTheme()));

        mIsInEditingMode = true;
        mMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_done, getTheme()));
    }

    @DebugLog
    private void finishEdit() {
        View currentFocus = getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        //set title stuff
        mTitleEditText.setFocusable(false);
        mTitleEditText.setBackgroundColor(Color.TRANSPARENT);
        //set content stuff
        mContentEditText.setFocusable(false);
        mContentEditText.setBackgroundColor(Color.TRANSPARENT);
        mIsInEditingMode = false;
        mMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_edit, getTheme()));

    }
}