package com.example.flxrexample.quest_ongoing

import com.airbnb.epoxy.TypedEpoxyController
import com.example.flxrexample.*
import com.example.flxrexample.quest_model.Container
import com.example.flxrexample.quest_model.OngoingQuestAuthClick

class QuestOngoingListController() : TypedEpoxyController<Container>() {

    override fun buildModels(container: Container?) {
        container?.ongoingQuests?.forEach {
            header {
                id(it.ongoingQuestHeader.id)
                size(it.questConstraints.size)
                completedCount(it.questConstraints.filter { constraint -> constraint.isCompleted }.size)
                ongoingQuestHeader(it.ongoingQuestHeader)
                ongoingQuestHeaderExpanded { model, _, _, _ ->
                    container.ongoingQuestHeaderExpanded(model.ongoingQuestHeader())
                }
            }
            if (it.ongoingQuestHeader.isExpanded) {
                it.questConstraints.forEach { constraint ->
                    content {
                        id(constraint.id)
                        questConstraint(constraint)
                        ongoingQuestAuthClick { model ->
                            it.ongoingQuestAuthClick()
                        }
                    }
                }
                footer {
                    id(it.ongoingQuestFooter.id)
                }
            }
        }
    }
}