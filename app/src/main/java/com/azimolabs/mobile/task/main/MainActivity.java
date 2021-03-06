package com.azimolabs.mobile.task.main;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.azimolabs.mobile.task.GithubAppComponent;
import com.azimolabs.mobile.task.R;
import com.azimolabs.mobile.task.base.BaseActivity;
import com.azimolabs.mobile.task.base.BasePresenter;
import com.azimolabs.mobile.task.github.ActivityComponent;
import com.azimolabs.mobile.task.utils.KeyboardHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends BaseActivity {

    @BindView(R.id.pbLoadingRepos)
    ProgressBar pbLoadingRepos;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.tvUserError)
    TextView tvUserError;

    @Inject
    MainActivityPresenter presenter;
    @Inject
    KeyboardHelper keyboardHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btnCta)
    public void ctaClicked() {
        presenter.openReposListForUser(etUserName.getText().toString());
    }

    @Override
    protected ActivityComponent onCreateComponent(GithubAppComponent githubAppComponent) {
        MainActivityComponent component = githubAppComponent.plus(new MainActivityComponent.MainActivityModule(this));
        component.inject(this);
        return component;
    }

    @OnTextChanged(R.id.etUserName)
    public void textChanged() {
        presenter.textChanged();
    }

    @Override
    protected BasePresenter getBasePresenter() {
        return presenter;
    }

    public void showError(UserFieldError error) {
        tvUserError.setText(error.getErrorMessage());
        tvUserError.setVisibility(View.VISIBLE);
        etUserName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.error), PorterDuff.Mode.SRC_ATOP);
    }

    public void hideError() {
        tvUserError.setVisibility(View.INVISIBLE);
        etUserName.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
    }

    public void showLoading() {
        pbLoadingRepos.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        pbLoadingRepos.setVisibility(View.INVISIBLE);
    }

    public void hideKeyboard() {
        keyboardHelper.hideKeyboard();
    }

}
