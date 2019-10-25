package com.example.flxrexample.quest_make

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.flxrexample.R
import kotlinx.android.synthetic.main.quest_make_fragment.*

class QuestMakeFragment : Fragment(){

    companion object {
        fun newInstance() = QuestMakeFragment()
    }

    private lateinit var viewModel: QuestMakeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quest_make_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuestMakeViewModel::class.java)

        quest_make_condition_list.layoutManager = LinearLayoutManager(this.context)
    }

}
