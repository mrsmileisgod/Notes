package the.mrsmile.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import the.mrsmile.notes.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        currentFragment(HomeFragment())
        binding.bottomNavBar.setItemSelected(R.id.menu_home, true)
        binding.bottomNavBar.setOnItemSelectedListener {
            when (binding.bottomNavBar.getSelectedItemId()) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment, HomeFragment()).commit()

                }

                R.id.menu_feedback -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, FeedbackFragment()).commit()

            }
        }

    }

    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment).commit()
        }
    }
}