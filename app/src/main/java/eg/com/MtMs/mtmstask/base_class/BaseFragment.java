package eg.com.MtMs.mtmstask.base_class;

import android.content.Context;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import dmax.dialog.SpotsDialog;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.utils.StaticMethods;
import eg.com.MtMs.mtmstask.utils.ToastUtil;


public abstract class BaseFragment extends Fragment implements BaseView {

    SpotsDialog dialog;



    @Override
    public Context getContextBase() {
        return getActivity();
    }

    @Override
    public void showErrorMessageBase(CoordinatorLayout coordinatorLayout, Context context, String errormessage){
        StaticMethods.ShowSnake(coordinatorLayout,context,errormessage);

    }

    @Override
    public void showNoNetworkConnectionBase(CoordinatorLayout coordinatorLayout, Context context){
        StaticMethods.NOConnecting(coordinatorLayout,context);
    }
    @Override
    public void showErrorMessageBase( Context context, String errormessage){
        ToastUtil.showErrorToast(context,errormessage);
    }

    @Override
    public void showNoNetworkConnectionBase( Context context){
        ToastUtil.showErrorToast(context,context.getString(R.string.noconnection));
    }
    @Override
    public void showSuccessMessageBase(Context context, String message) {
        ToastUtil.showSuccessToast(context,message);
    }

    @Override
    public void showSuccessMessageBase(CoordinatorLayout coordinatorLayout, Context context, String message) {
        StaticMethods.ShowSnake(coordinatorLayout,context,message);
    }

    @Override
    public void showloadingviewBase(Context context){
        spotDialoug(context);
    }

    @Override
    public void hideloadingviewBase(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
    private void spotDialoug(Context context){
            dialog = new SpotsDialog(context,getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();
    }


}
