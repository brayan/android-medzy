package br.com.sailboat.elseapp.view.ui.insert

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.ui.insert.presenter.InsertDrugPresenter
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.frag_insert_drug.*
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class InsertDrugFragment : BaseFragment<InsertDrugPresenter>(), InsertDrugPresenter.View {

    private val REQUEST_NEW_WORKOUT = 0
    private val REQUEST_DETAILS = 1

    override val layoutId: Int get() = R.layout.frag_insert_drug
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): InsertDrugPresenter {
        return InsertDrugPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_insert_drug, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_insert_save -> {
                presenter.onClickMenuSave()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    override fun initViews() {
        initToolbar()

        tvInsertDrugTime.setOnClickListener {
            presenter.onClickTime()
        }

        tvInsertDrugFrequency.setOnClickListener {
            presenter.onClickFrequency()
        }
    }

    override fun updateContentViews() {
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initToolbar() {
        toolbar.setTitle("New Drug")
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }


}
