package com.github.malitsplus.shizurunotes.ui.menu

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.malitsplus.shizurunotes.R
import com.github.malitsplus.shizurunotes.common.I18N
import com.github.malitsplus.shizurunotes.databinding.FragmentMenuBinding
import com.github.malitsplus.shizurunotes.databinding.ViewCommunityBinding
import com.github.malitsplus.shizurunotes.ui.BottomNaviFragmentDirections
import com.github.malitsplus.shizurunotes.user.UserSettings
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(
            inflater, R.layout.fragment_menu, container, false
        ).apply {
            clickListener = View.OnClickListener {
                when(it.id){
                    R.id.constraint_dungeon ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavDungeon())
                    R.id.constraint_calendar ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavCalendar())
                    R.id.constraint_setting ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavSettingContainer())
                    R.id.constraint_rank_comparison ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavRankCompare())
                    R.id.constraint_hatsune ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavHatsuneStage())
                    R.id.constraint_sp_event ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavSpEvent())
                    R.id.constraint_secret_dungeon ->
                        it.findNavController().navigate(
                            BottomNaviFragmentDirections.actionNavBottomNavigationToNavSecretDungeon())

//                    R.id.constraint_community -> {
//                        val communityView = DataBindingUtil.inflate<ViewCommunityBinding>(
//                            layoutInflater, R.layout.view_community, container, false
//                        ).apply {
//                            if (UserSettings.get().getLanguage() == "zh") {
//                                val spannable = SpannableString(I18N.getString(R.string.community_dialog_text)).apply {
//                                    setSpan(StrikethroughSpan(), 54, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                                }
//                                textView11.text = spannable
//                            } else {
//                                textView11.text = I18N.getString(R.string.community_dialog_text)
//                            }
//                            clickListener = View.OnClickListener {
//                                val transferIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://jgchat.net/i/g2xUk79U"))
//                                startActivity(transferIntent)
//                            }
//                        }.root
//                        MaterialAlertDialogBuilder(requireContext())
//                            .setView(communityView)
//                            .show()
//                    }
                }
            }
        }

        return binding.root
    }

}
