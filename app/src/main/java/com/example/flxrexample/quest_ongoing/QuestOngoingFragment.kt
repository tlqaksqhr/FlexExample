package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.flxrexample.R
import kotlinx.android.synthetic.main.quest_ongoing_fragment.*

class QuestOngoingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestOngoingFragment()
    }

    private lateinit var viewModel: QuestOngoingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.quest_ongoing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        quest_ongoing_btn.setOnClickListener {
            val questAuthIntent = Intent(activity, QuestOngoingAuthActivity::class.java)
            startActivity(questAuthIntent)
        }

        viewModel = ViewModelProviders.of(this).get(QuestOngoingViewModel::class.java)
    }

}
