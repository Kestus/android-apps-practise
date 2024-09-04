package com.kes.app032_kt_navdrawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.kes.app032_kt_navdrawer.fragments.HomeFragment
import com.kes.app032_kt_navdrawer.fragments.MessagesFragment
import com.kes.app032_kt_navdrawer.fragments.SettingsFragment
import com.kes.app032_kt_navdrawer.fragments.SignInFragment

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var btnMenu: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.button_menu)


        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // navigation functionality
        navigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment(), it.title.toString())
                }
                R.id.message -> {
                    replaceFragment(MessagesFragment(), it.title.toString())
                }
                R.id.settings -> {
                    replaceFragment(SettingsFragment(), it.title.toString())
                }
                R.id.signin -> {
                    replaceFragment(SignInFragment(), it.title.toString())
                }
                else -> { false }
            }
        }

        btnMenu.setOnClickListener() {
            drawerLayout.openDrawer(navigationView)
        }

    }

    private fun replaceFragment(fragment: Fragment, title: String): Boolean {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

        drawerLayout.closeDrawers()
        setTitle(title)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}