package com.example.roomwithaview

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roomwithaview.data.Word
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        initRecyclerView()
        setListeners()

    }

    private fun setListeners() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        fab.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }
        addButton.setOnClickListener {
            wordViewModel.insertWord(Word(editText.text.toString()))
            editText.text=null
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Handler().postDelayed({
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it.windowToken,0)
                },500)
        }

    }

    private fun initRecyclerView() {
        val adapter = WordListAdapter(this,wordViewModel)
        recyclerView.adapter = adapter
        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { adapter.setWords(it) }
        })
    }
}
