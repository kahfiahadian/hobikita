package com.example.rpl.hobi_kita_rev;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.rpl.hobi_kita_rev.R;
import com.example.rpl.hobi_kita_rev.Constant;
import com.example.rpl.hobi_kita_rev.FragmentFoto;

import java.util.ArrayList;

public class myupload extends AppCompatActivity {

    private FragmentFoto frag;
    ArrayList<FotoModel> listPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myupload);
        ButterKnife.bind(this);  //Binding ButterKnife pada activity
        setTitle("My Uploads");

        //Mengatur tab dan fragment pada tab menggunakan fragmentstatepageritemadapter dari library SmartTabLayout
        FragmentStatePagerItemAdapter adapter = new FragmentStatePagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(FragmentPagerItem.of("My Photo", FragmentFoto.class, FragmentFoto.arguments("myphoto")))
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        viewPager.setAdapter(adapter); //masukkan fragment pada adapter viewpager
        //viewPagerTab.setViewPager(viewPager); //mengatur tab pada viewpager


    }
    //method untuk implement menu pada activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spesific_menu, menu); // inflate atau memasukkan menu
        return super.onCreateOptionsMenu(menu);
    }

    //method untuk handling menu yang di klik dari daftar di menu yang di implement
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnLogout:
                Constant.mAuth.signOut(); //logout firebase
                Constant.currentUser = null; //set global variable user null
                startActivity(new Intent(this, Login.class)); //panggil login activity
                finish();
                break;
            case R.id.mnhome:
                startActivity(new Intent(this, MainActivity.class)); //panggil main activity
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
