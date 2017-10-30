package com.asgatech.sharjahmuseums.Activities.Search;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.EditText;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMuseumActivity extends AppCompatActivity implements SearchContract.ModelView {

    @BindView(R.id.et_toolbar_search)
    EditText ToolbarSearchEditText;
    @BindView(R.id.toolbar_home_image_view)
    ImageView toolbarHomeImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SearchPresenter mPresenter;
    private int catId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_museum);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setToolBar();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SearchMuseumCategoryFragment());
        transaction.commit();
        mPresenter = new SearchPresenter(getSupportFragmentManager(), this);
        ToolbarSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(catId, ToolbarSearchEditText.getText().toString());
                handled = true;
            }
            return handled;
        });
    }

    public void setCurrentId(int catId){
        this.catId = catId;
    }

    public void search(int catId, String keyword) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, SearchMuseumResultFragment.newInstance(catId, keyword));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeypad(this);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onResume() {
        Localization.setLanguage(SearchMuseumActivity.this,
                UserData.getLocalization(SearchMuseumActivity.this));
        super.onResume();
    }

    @Override
    public SearchContract.UserAction getPresenter() {
        if (mPresenter != null)
            return mPresenter;
        else
            return new SearchPresenter(getSupportFragmentManager(), this);
    }
}
