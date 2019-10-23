package com.example.flxrexample.quest_ongoing

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.flxrexample.R
import com.example.flxrexample.databinding.QuestOngoingFragmentBinding

class QuestOngoingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestOngoingFragment()
    }

    private lateinit var binding: QuestOngoingFragmentBinding
    private lateinit var viewModel: QuestOngoingViewModel

    private val questOngoingListController = QuestOngoingListController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.quest_ongoing_fragment, container, false)
        binding.questOngoingList.layoutManager = LinearLayoutManager(this.context)
        binding.questOngoingList.adapter = questOngoingListController.adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
        quest_ongoing_btn.setOnClickListener {
            val questAuthIntent = Intent(activity, QuestOngoingAuthActivity::class.java)
            startActivity(questAuthIntent)
        }
        */

        viewModel = ViewModelProviders.of(this).get(QuestOngoingViewModel::class.java)

        viewModel.ongoingQuestLiveData.observe(this, Observer { container ->
            questOngoingListController.setData(container)
        })
    }

}
