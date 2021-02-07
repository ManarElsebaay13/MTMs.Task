package eg.com.MtMs.mtmstask.base_class;

import android.content.Context;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import dmax.dialog.SpotsDialog;
import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.app.MTMsApp;
import eg.com.MtMs.mtmstask.utils.StaticMethods;
import eg.com.MtMs.mtmstask.utils.ToastUtil;


public abstract class BaseActivity< V extends BasePresenter> extends AppCompatActivity
        implements BaseView{

    SpotsDialog dialog;

    private void spotDialoug(Context context){
        dialog = new SpotsDialog(context,getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public Context getContextBase() {
        return MTMsApp.getAppContext();
    }

    @Override
    public void showErrorMessageBase(CoordinatorLayout coordinatorLayout, Context context, String errormessage){
        StaticMethods.ShowSnake(coordinatorLayout,context,errormessage);
    }

    @Override
    public void showErrorMessageBase( Context context, String errormessage){
        ToastUtil.showErrorToast(context,errormessage);
    }

    @Override
    public void showNoNetworkConnectionBase(CoordinatorLayout coordinatorLayout, Context context){
        StaticMethods.NOConnecting(coordinatorLayout,context);
    }
    @Override
    public void showNoNetworkConnectionBase( Context context){
        ToastUtil.showErrorToast(context,context.getString(R.string.noconnection));
    }

    public  void hideKeyboard(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LanguageSettings.setLocalenew(getContextBase());
//    }

    }
