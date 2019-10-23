package com.example.flxrexample.quest_ongoing

import com.airbnb.epoxy.TypedEpoxyController
import com.example.flxrexample.content
import com.example.flxrexample.footer
import com.example.flxrexample.header

class QuestOngoingListController : TypedEpoxyController<Container>() {

    override fun buildModels(container: Container?) {
        container?.ongoingQuests?.forEach {
            header {
                id(it.ongoingQuestHeader.id)
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
                    }
                }
                footer {
                    id(it.ongoingQuestFooter.id)
                }
            }
        }
    }
}