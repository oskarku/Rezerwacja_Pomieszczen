package android.oskar.kufel.com.rezerwacjapomieszczenstudenckich;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.AccountDetale.AccountDetalFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.CHeffView.PanelCHeffFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.RezervationView.AddRezervationFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.CalendarFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.MyRezervationFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.ViewInhabitant.RezervtionOtherSalFragment;
import android.oskar.kufel.com.rezerwacjapomieszczenstudenckich.keep.KeepKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.view.View;
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

        /*
        ponizsze metody wczytuje jakie fragmenty powinny byc
         */
        accountModelView.checkLoginTypeAcount(pref.getString(KeepKey.KEY_TYPE_ACCOUNT, null));






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

                                switchFragment(KeepKey.KEY_FRAGMENT_CALENDAR, new CalendarFragment());


                            }

                            else if( id == R.id.nav_student_myEvent){

                                switchFragment(KeepKey.KEY_FRAGMENT_MY_REZERWATION, new MyRezervationFragment());

                            }



                            else if (id == R.id.nav_student_personality_date) {

                                switchFragment(KeepKey.KEY_FRAGMMENT_MY_DETAL_ACCOUNT, new AccountDetalFragment());


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



                   switchFragment(KeepKey.KEY_FRAGMENT_CALENDAR, new CalendarFragment());

                }

            }
        };
        accountModelView.getIsInhabit().observe(this, booleanObserverInhabit);




        final Observer<Boolean> booleanObserverInChef = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                if(aBoolean){


                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_cheff);


                    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                            // Handle navigation view item clicks here.
                            int id = menuItem.getItemId();





                             if (id == R.id.nav_cheff_personality_date) {

                               switchFragment(KeepKey.KEY_FRAGMMENT_MY_DETAL_ACCOUNT, new AccountDetalFragment());


                            }

                            else if (id== R.id.nav_cheff_panel){
                                 switchFragment(KeepKey.KEY_FRAGMENT_CHEFF, new PanelCHeffFragment());
                             }


                            else if (id == R.id.nav_cheff_personality_date_logout) {

                                pref.edit().clear().commit();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(intent);



                            }

                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                            return true;

                        }
                    });



                    switchFragment(KeepKey.KEY_FRAGMENT_CHEFF, new PanelCHeffFragment());


                }


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

    public void  switchFragment(String keyTag, Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(keyTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_layout_account, fragment);
        ft.addToBackStack(keyTag);
        ft.commit();
    }










    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();

        }
        return super.onPrepareOptionsMenu(menu);
    }



    public void setInformationToabotherFragment(String item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RezervtionOtherSalFragment rezervtionOtherSalFragment = new RezervtionOtherSalFragment();
        Bundle b1 = new Bundle();
        b1.remove("tytul");
        b1.putString("tytul", item);
        rezervtionOtherSalFragment.setArguments(b1);
        ft.add(R.id.frame_layout_add_rezerwation,rezervtionOtherSalFragment);
        ft.commit();


    }
}
