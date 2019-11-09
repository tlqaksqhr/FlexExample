package com.example.flxrexample.quest_ongoing

import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import com.example.flxrexample.*
import com.example.flxrexample.quest_model.Container

class QuestFavoriteListController : TypedEpoxyController<Container>() {
    
    override fun buildModels(container: Container?) {
        container?.favoriteQuests?.forEach {
            favoriteHeader {
                id(it.favoriteQuestHeader.id)
                favoriteQuestHeader(it.favoriteQuestHeader)
                favoriteQuestHeaderExpanded { model, _, _, _ ->
                    container.favoriteQuestHeaderExpanded(model.favoriteQuestHeader())
                }
            }
            if (it.favoriteQuestHeader.isExpanded) {
                it.questConstraints.forEach { constraint ->
                    favoriteContent {
                        id(constraint.id)
                        questConstraint(constraint)
                    }
                }
                favoriteFooter {
                    id(it.favoriteQuestFooter.id)
                }
            }
        }
    }
}