package eg.com.MtMs.mtmstask.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import eg.com.MtMs.mtmstask.R;


public   class FragmentHandler {

   public static int selectedPosition=0 , selectedPositionInside = 0 ;



   public  static  void   displayView(int position , Activity activity, Fragment fragment, int container) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;

       FragmentTransaction fragmentTransaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(container, fragment);
       fragmentTransaction.addToBackStack(fragment.getClass().getName());
       fragmentTransaction.commit();


//        FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentManager.addToBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        fragmentTransaction.replace(container, fragment);
//        fragmentTransaction.commit();

   }



   public  static  void   displayViewWithAnimation(int position , FragmentActivity activity, Fragment fragment, int FrameID , String Tag) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;
       FragmentManager fragmentManager = activity.getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       fragmentTransaction.replace(FrameID, fragment , Tag);
       fragmentTransaction.commit();
   }
   public  static  void   displayViewWithAnimation(int position , FragmentActivity activity, Fragment fragment, int FrameID ) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;
       FragmentManager fragmentManager = activity.getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       fragmentTransaction.replace(FrameID, fragment );
       fragmentTransaction.commit();
   }

   public  static  void   displayViewWithTag(int position , Context activity, Fragment fragment, int FrameID , String Tag) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;


       ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction()
               .replace(FrameID, fragment)
               .commit();

   }

   public  static  void   displayViewWithbundle(int position , FragmentActivity activity, Fragment fragment, int FrameID, Bundle bundle) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;
       FragmentManager fragmentManager = activity.getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       Fragment fragment1 =  fragment ;
       fragment1.setArguments(bundle);
       fragmentTransaction.replace(FrameID, fragment1);
       fragmentTransaction.commit();
   }

   public  static  void   displayViewWithbundle(int position , FragmentActivity activity, Fragment fragment, Bundle bundle) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;
       FragmentManager fragmentManager = activity.getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       Fragment fragment1 =  fragment ;
       fragment1.setArguments(bundle);
       fragmentTransaction.commit();
   }

   public  static  void   displayViewWithbundle(int position , FragmentActivity activity, Fragment fragment, int FrameID, String Tag, Bundle bundle) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;
       FragmentManager fragmentManager = activity.getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       Fragment fragment1 =  fragment ;
       fragment1.setArguments(bundle);
       fragmentTransaction.replace(FrameID, fragment1, Tag);
       fragmentTransaction.commit();
   }
   public  static  void   displayViewWithbundleAinmatio(int position , FragmentActivity activity, Fragment fragment, int FrameID, String Tag, Bundle bundle) {
       selectedPositionInside =  selectedPosition ;
       selectedPosition = position;
       FragmentManager fragmentManager = activity.getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       Fragment fragment1 =  fragment ;
       fragment1.setArguments(bundle);
       fragmentTransaction.replace(FrameID, fragment1, Tag);
       fragmentTransaction.commit();
   }


   public  static  int   getSelectPoistion(){
       return  selectedPosition ;
   }

   public  static  int   getSelectedPositionInside(){
       return  selectedPositionInside ;
   }

   }





