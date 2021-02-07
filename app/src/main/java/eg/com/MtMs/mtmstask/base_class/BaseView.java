package eg.com.MtMs.mtmstask.base_class;

import android.content.Context;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public interface BaseView {
    void showErrorMessageBase(CoordinatorLayout coordinatorLayout, Context context, String errormessage);

    void showNoNetworkConnectionBase(CoordinatorLayout coordinatorLayout, Context context);

    void showErrorMessageBase(Context context, String errormessage);

    void showSuccessMessageBase(Context context, String errormessage);

    void showSuccessMessageBase(CoordinatorLayout coordinatorLayout, Context context, String errormessage);

    void showNoNetworkConnectionBase(Context context);

    void showloadingviewBase(Context context);

    void hideloadingviewBase();

    Context getContextBase();
}
