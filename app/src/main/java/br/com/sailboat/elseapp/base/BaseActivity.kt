package br.com.sailboat.elseapp.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.sailboat.elseapp.R

abstract class BaseActivity<T : Fragment> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_layout)
        checkStateAndAddFragment(savedInstanceState)
    }

    private fun checkStateAndAddFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.frame_layout, newFragmentInstance())
        }
    }

    private fun addFragment(@IdRes id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(id, fragment).commit()
    }

    protected abstract fun newFragmentInstance(): T

}