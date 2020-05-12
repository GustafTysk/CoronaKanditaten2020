package com.example.coronakanditaten2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.libraries.places.api.Places;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int CONTENT_VIEW_ID = 10101010;
    private FragmentTransaction transaction;
    ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
    LoginFragment loginFragment = new LoginFragment();


    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private NonSwipeableViewPager mViewPager;

    private SectionsStatePagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (NonSwipeableViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        setViewPager(1);

    }


    @Override
    public void onClick(View view) {

    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(resetPasswordFragment, "Reset Password");           // 0
        adapter.addFragment(loginFragment, "Log In");                           // 1
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        switch (mViewPager.getCurrentItem()){
            case 1:
                Toast.makeText(this,"There is no back action", Toast.LENGTH_SHORT);
                break;
            case 0:
                setViewPager(1);
                break;
            default:

        }
        return;
    }

}
