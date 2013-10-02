package com.abhiroop.facebookfriends;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity {
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String userID = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("name");
        String username = getIntent().getStringExtra("username");

        final ImageView profilePictureView = (ImageView) findViewById(R.id.profilePicView);
        final Button seeFriendsButton = (Button) findViewById(R.id.clickFriends);
        final ProgressBar profileProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        profilePictureView.setDrawingCacheEnabled(true);

        TextView nameTextView = (TextView) findViewById(R.id.nameText);
        nameTextView.setText(name);

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisc(true).build();
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(activity)
                .defaultDisplayImageOptions(displayImageOptions).build();
        imageLoader.init(imageLoaderConfig);

        imageLoader.displayImage("http://graph.facebook.com/"+userID+"/picture?height=300", profilePictureView);

        seeFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = Session.getActiveSession();
                profileProgressBar.setVisibility(View.VISIBLE);
                Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
                    @Override
                    public void onCompleted(List<GraphUser> users, Response response) {
                      List<UserFriend> userFriends = new ArrayList<UserFriend>();
                      for(GraphUser user : users) {
                          Log.e(ProfileActivity.class.getCanonicalName(), " User Friend : " + user.getId() + " Name :: " + user.getName());
                          userFriends.add(new UserFriend(user.getId(), user.getName()));
                      }
                      profileProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }
}
