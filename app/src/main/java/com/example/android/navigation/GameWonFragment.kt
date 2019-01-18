/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)
        //TODO: 変数の名前。こういう時なんて名前が良い？つかどうしてこれでIntentが成立するかどうかを確かめられるの？
        val canResolveIntent = getShareIntent().resolveActivity(activity!!.packageManager) == null
        if (canResolveIntent) {
            menu?.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //TODO: 以下の強制的アンラップをなんとかしたい
        when (item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {
        val additiveType = "text/plain"
        //TODO: 以下の強制的アンラップをなんとかしたい
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        val additiveValue = getString(R.string.share_success_text,
                args.numCorrect,
                args.numQuestions)
        //TODO: メソッドチェーンが読みづらい・・・
        return ShareCompat.IntentBuilder.from(activity)
                .setText(additiveValue)
                .setType(additiveType)
                .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}
