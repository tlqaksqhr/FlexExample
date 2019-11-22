package com.example.flxrexample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.flxrexample.profile.MyProfileFragment
import com.example.flxrexample.quest.QuestMainFragment
import com.example.flxrexample.quest_make.QuestMakeFragment
import com.example.flxrexample.quest_ongoing.QuestOngoingFragment
import com.example.flxrexample.ranking.RankingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var selectedFragment : Fragment
    private var isOngoing : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()

        if(intent.extras != null){
            isOngoing = intent.extras?.getInt("goOngoing")!!
            if(isOngoing != null){
                selectedFragment = QuestOngoingFragment()
                changeFragment(selectedFragment)
            }
        }
    }

    private fun setupUI(){

        selectedFragment = QuestMainFragment()
        navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.action_quest_main -> {
                    selectedFragment = QuestMainFragment()
                }
                R.id.action_quest_ongoing -> {
                    selectedFragment = QuestOngoingFragment()
                }
                R.id.action_ranking -> {
                    selectedFragment = RankingFragment()
                }
                R.id.action_quest_create -> {
                    selectedFragment = QuestMakeFragment()
                }
                R.id.action_my_profile -> {
                    selectedFragment = MyProfileFragment()
                }
                else -> {
                    selectedFragment = QuestMainFragment()
                }
            }
            changeFragment(selectedFragment)
            true
        }

        changeFragment(selectedFragment)
    }

    private fun changeFragment(selectedFragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container,selectedFragment)
        transaction.commit()
    }
}