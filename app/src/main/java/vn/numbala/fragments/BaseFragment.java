package vn.numbala.fragments;

import android.support.v4.app.Fragment;

import okhttp3.OkHttpClient;

/**
 * Create By: Nhut Chau(nchau@kolabs.co)
 * Time: 12/2/17 2:26 PM.
 * Project Name: Numbala
 */

public class BaseFragment extends Fragment {
    protected final OkHttpClient client = new OkHttpClient();
}
