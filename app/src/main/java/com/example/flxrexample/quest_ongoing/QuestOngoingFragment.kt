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
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.airbnb.epoxy.EpoxyRecyclerView

import com.example.flxrexample.R
import com.example.flxrexample.databinding.QuestOngoingFragmentBinding

class QuestOngoingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestOngoingFragment()
    }

    private lateinit var binding: QuestOngoingFragmentBinding
    private lateinit var viewModel: QuestOngoingViewModel

    private val questOngoingListController = QuestOngoingListController()
    private val questFavoriteListController = QuestFavoriteListController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.quest_ongoing_fragment, container, false)

        binding.questOngoingList.layoutManager = LinearLayoutManager(this.context)
        binding.questOngoingList.adapter = questOngoingListController.adapter

        binding.questOngoingFavoriteList.layoutManager = LinearLayoutManager(this.context)
        binding.questOngoingFavoriteList.adapter = questFavoriteListController.adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, QuestOngoingViewModelFactory(this::transitionQuestAuthPage)).get(
            QuestOngoingViewModel::class.java)

        viewModel.ongoingQuestLiveData.observe(this, Observer { container ->
            questOngoingListController.setData(container)
            questFavoriteListController.setData(container)
            binding.questOngoingCountBtn.text = "${container.ongoingQuests.size}/5"
            binding.questOngoingFavoriteCountBtn.text = "${container.favoriteQuests.size}/15"
        })
    }

    private fun transitionQuestAuthPage(){
        val questAuthIntent = Intent(activity, QuestOngoingAuthActivity::class.java)
        startActivity(questAuthIntent)
    }

}