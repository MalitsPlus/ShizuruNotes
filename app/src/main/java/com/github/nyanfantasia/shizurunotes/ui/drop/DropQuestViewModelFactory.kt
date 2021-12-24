package com.github.nyanfantasia.shizurunotes.ui.drop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.nyanfantasia.shizurunotes.data.Item
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelQuest
import java.lang.reflect.InvocationTargetException

class DropQuestViewModelFactory(
    private val sharedQuest: SharedViewModelQuest,
    private val equipmentList: List<Item>?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            modelClass.getConstructor(SharedViewModelQuest::class.java, List::class.java)
                .newInstance(sharedQuest, equipmentList)
        } catch (e: NoSuchMethodException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}