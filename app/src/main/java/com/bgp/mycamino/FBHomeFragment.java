package com.bgp.mycamino;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import static com.facebook.FacebookSdk.getApplicationContext;
import static net.danlew.android.joda.ResUtils.getIdentifier;

public class FBHomeFragment extends Fragment {

    private ImageView profile_pic = null;
    private TextView tv = null;
    private Button logoutButton = null;
    private Button shareButton = null;
    private Profile profile = null;
    private ShareDialog shareDialog;
    private String cityname;
    private String cityimage;
    private String fbplaceid;
    private String alberguename;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fbhome_fragment, container, false);
        profile_pic = (ImageView) view.findViewById(R.id.profile_pic);
        tv = (TextView) view.findViewById(R.id.tv_name);
        logoutButton = (Button) view.findViewById(R.id.logout_button);
        shareButton = (Button) view.findViewById(R.id.share_button);
        shareDialog = new ShareDialog(this);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        Intent intent = getActivity().getIntent();
        cityname = intent.getStringExtra("cityname");
        cityimage = intent.getStringExtra("cityimage");
        fbplaceid = intent.getStringExtra("fbplaceid");
        alberguename = intent.getStringExtra("alberguename");
        Resources res = this.getResources();
        final int imageID = res.getIdentifier(cityimage, "drawable", "com.bgp.mycamino");


        if (bundle != null) {
            profile = (Profile) bundle.getParcelable(FBLoginFragment.PARCEL_KEY);
        } else {
            profile = Profile.getCurrentProfile();
        }

//        Toast.makeText(getApplicationContext(), String.valueOf(imageID), Toast.LENGTH_LONG).show();

        if(alberguename != null) {
            tv.setText("Hi " + profile.getName() + "\n \nCheck In from " + alberguename);
        } else {
            tv.setText("Hi " + profile.getName() + "\n \nCheck In from " + cityname);
        }

        Picasso.with(getActivity())
//                .load(profile.getProfilePictureUri(300, 300).toString())
                .load(imageID)
                .resize(600,300)
                .centerCrop()
                .onlyScaleDown()
                .into(profile_pic);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = BitmapFactory.decodeResource(getResources(), imageID);

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
//                Toast.makeText(getApplicationContext(), String.valueOf(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://com.bgp.mycamino/drawable/"+cityimage)), Toast.LENGTH_LONG).show();

                if(alberguename != null) {
                    String albname = alberguename + " at";
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle(albname + " " + cityname)
                                .setContentDescription(
                                        "Shared using MyCamino - Francés on Android.   Get it from Google Play")
                                .setContentUrl(Uri.parse("https://www.facebook.com/mycaminoapp"))
                                .setPlaceId(fbplaceid)
                                .setImageUrl(Uri.parse("http://bestwebapp.hu/caminoimages/" + cityimage + ".jpg"))
                                .build();

                        shareDialog.show(linkContent);
                    }
                } else {
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle(cityname)
                                .setContentDescription(
                                        "Shared using MyCamino - Francés on Android.   Get it from Google Play")
                                .setContentUrl(Uri.parse("https://www.facebook.com/mycaminoapp"))
                                .setPlaceId(fbplaceid)
                                .setImageUrl(Uri.parse("http://bestwebapp.hu/caminoimages/" + cityimage + ".jpg"))
                                .build();

                        shareDialog.show(linkContent);
                    }
                }
            }
        });
    }

    private void logout() {
        LoginManager.getInstance().logOut();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, new FBLoginFragment());
        fragmentTransaction.commit();
    }

}
