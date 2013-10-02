package com.abhiroop.facebookfriends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends SherlockFragmentActivity implements Session.StatusCallback {
    private UiLifecycleHelper uiLifecycleHelper;
    private boolean facebookLoginInProgress = false;
    private ProgressBar loginProgressBar;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uiLifecycleHelper = new UiLifecycleHelper(this, this);
        uiLifecycleHelper.onCreate(savedInstanceState);
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginProgressBar = (ProgressBar) findViewById(R.id.loginProgressBar);

        loginButton.setReadPermissions(Arrays.asList("basic_info"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void call(Session session, SessionState state, Exception exception) {
        onSessionStateChange(session, state, exception);
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        Log.e(LoginActivity.class.getCanonicalName(), " Session : " +  session + "\n open : " +
                session.isOpened() + "\n close : " + session.isClosed() + " state : " +
                session.getState() + "\n session == null : " + (session == null));
        if (state.isOpened() && !facebookLoginInProgress) {
            facebookLoginInProgress = true;
            Log.i(LoginActivity.class.getCanonicalName(), "FACEBOOK LOGGED IN!!!!");
            loginProgressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
            Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    loginProgressBar.setVisibility(View.GONE);
                    Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("userId", user.getId());
                    Log.e(LoginActivity.class.getCanonicalName(), " userId : " + user.getId());
                    profileIntent.putExtra("username", user.getUsername());
                    profileIntent.putExtra("name", user.getName());
                    startActivity(profileIntent);
                    finish();
                }
            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i(LoginActivity.class.getCanonicalName(), "FACEBOOK LOGGED OUTTT!!!!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiLifecycleHelper.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiLifecycleHelper.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Session session = Session.getActiveSession();

        if(SessionState.CREATED.equals(session.getState())) {
            loginProgressBar.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
        }

        if(session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiLifecycleHelper.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiLifecycleHelper.onSaveInstanceState(outState);
    }
}
