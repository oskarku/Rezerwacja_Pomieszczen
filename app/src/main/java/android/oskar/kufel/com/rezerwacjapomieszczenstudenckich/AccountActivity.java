package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountDetale.AccountDetalFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.CalendarFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class AccountActivity extends AppCompatActivity
{

    private FrameLayout frameLayout;
    private AccountModelView accountModelView;
    private String nameFirstFragmentonBackStack = null;

    SharedPreferences pref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref=getApplicationContext().getSharedPreferences(KeepKey.KEY_NAME_SHARED, MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();




        accountModelView = ViewModelProviders.of(this).get(AccountModelView.class);

        frameLayout = (FrameLayout) findViewById(R.id.frame_layout_account);


        ///TODO zobaczyć jak odczytac dane

        accountModelView.checkLoginTypeAcount(pref.getString(KeepKey.KEY_TYPE_ACCOUNT, null));




//        if(getSupportFragmentManager().getBackStackEntryAt(0).getName()!=null){
//            nameFirstFragmentonBackStack = getSupportFragmentManager().getBackStackEntryAt(0).getName();
//
//        }

        getSupportFragmentManager().popBackStack(nameFirstFragmentonBackStack, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);



        drawer.addDrawerListener(toggle);









        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.nav_gallery) {

                } else if (id == R.id.nav_slideshow) {

                } else if (id == R.id.nav_manage) {

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });


        /*
        Metoda wykorzystujaca zmieniajace sie dane jest to uzywane w modelviewmodel
         */
        final Observer<Boolean>  booleanObserverInhabit = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.navigator_menu_account);


                    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                            // Handle navigation view item clicks here.
                            int id = menuItem.getItemId();

                            if (id == R.id.nav_student_event_menu) {


                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.frame_layout_account, new CalendarFragment());
                                ft.commit();


                            } else if (id == R.id.nav_student_personality_date) {

                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.frame_layout_account, new AccountDetalFragment());
                                ft.commit();


                            } else if (id == R.id.nav_student_logout) {

                                pref.edit().clear().commit();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);



                            }

                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                            return true;

                        }
                    });



                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStack(KeepKey.KEY_FRAGMENT_CALENDAR, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    getSupportFragmentManager().popBackStack(nameFirstFragmentonBackStack, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_layout_account, new CalendarFragment());
                    ft.addToBackStack(KeepKey.KEY_FRAGMENT_CALENDAR);
                    ft.commit();




                }

            }
        };
        accountModelView.getIsInhabit().observe(this, booleanObserverInhabit);




        final Observer<Boolean> booleanObserverInChef = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

            }
        };
        accountModelView.getIsCHeif().observe(this, booleanObserverInChef);



        final Observer<Boolean> booleanObserverIsPortier = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

            }
        };
        accountModelView.getIsPortier().observe(this, booleanObserverIsPortier);





    }










    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
