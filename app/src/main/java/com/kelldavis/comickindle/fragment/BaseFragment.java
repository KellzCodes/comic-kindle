package com.kelldavis.comickindle.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evernote.android.state.StateSaver;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.kelldavis.comickindle.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings({"WeakerAccess", "EmptyMethod", "SameReturnValue"})
public abstract class BaseFragment extends Fragment {

    public static final long TOOLBAR_ANIMATION_DURATION = 200;
    public static final long TOOLBAR_ANIMATION_DELAY = 200;

    private Toolbar mToolbar;
    private Unbinder mUnbinder;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StateSaver.restoreInstanceState(this, savedInstanceState);
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateSaver.saveInstanceState(this, outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mToolbar = ButterKnife.findById(getActivity(), R.id.toolbar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected void hideToolbar() {
        int toolbarSize = mToolbar.getHeight();
        mToolbar.setTranslationY(-toolbarSize);
    }

    protected void startToolbarAnimation() {
        mToolbar.animate()
                .translationY(0)
                .setDuration(TOOLBAR_ANIMATION_DURATION)
                .setStartDelay(TOOLBAR_ANIMATION_DELAY);
    }

    protected void injectDependencies() {

    }
}

