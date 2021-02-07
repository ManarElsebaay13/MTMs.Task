package eg.com.MtMs.mtmstask.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import eg.com.MtMs.mtmstask.R;
import eg.com.MtMs.mtmstask.data.dialogData.DialogListener;


public class DialogPoP {
    int gifImageResource;
    private String title, message, positiveBtnText, negativeBtnText, pBtnColor, nBtnColor;
    private Context activity;
    private DialogListener pListener, nListener;
    private boolean cancel;


    public DialogPoP(Builder builder) {
        this.title = builder.title;
        this.message = builder.message;
        this.activity = builder.activity;
        this.pListener = builder.pListener;
        this.nListener = builder.nListener;
        this.pBtnColor = builder.pBtnColor;
        this.nBtnColor = builder.nBtnColor;
        this.positiveBtnText = builder.positiveBtnText;
        this.negativeBtnText = builder.negativeBtnText;
        this.gifImageResource = builder.gifImageResource;
        this.cancel = builder.cancel;
    }


    public static class Builder {
        int gifImageResource;
        private String title, message, positiveBtnText, negativeBtnText, pBtnColor, nBtnColor;
        private Context activity;
        private DialogListener pListener, nListener;
        private boolean cancel;

        public Builder(Context activity) {
            this.activity = activity;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveBtnText(String positiveBtnText) {
            this.positiveBtnText = positiveBtnText;
            return this;
        }

        public Builder setPositiveBtnBackground(String pBtnColor) {
            this.pBtnColor = pBtnColor;
            return this;
        }


        public Builder setNegativeBtnText(String negativeBtnText) {
            this.negativeBtnText = negativeBtnText;
            return this;
        }

        public Builder setNegativeBtnBackground(String nBtnColor) {
            this.nBtnColor = nBtnColor;
            return this;
        }

        //set Positive listener
        public Builder OnPositiveClicked(DialogListener pListener) {
            this.pListener = pListener;
            return this;
        }

        //set Negative listener
        public Builder OnNegativeClicked(DialogListener nListener) {
            this.nListener = nListener;
            return this;
        }

        public Builder isCancellable(boolean cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setGifResource(int gifImageResource) {
            this.gifImageResource = gifImageResource;
            return this;
        }

        /*public Builder hideButtons(){

        }*/

        public DialogPoP build() {
            TextView message1, title1;
            ImageView iconImg;
            Button nBtn, pBtn;

            final Dialog dialog;
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(cancel);
            dialog.setContentView(R.layout.fancygifdialog);


            //getting resources
            title1 = dialog.findViewById(R.id.title);
            message1 = dialog.findViewById(R.id.message);
            nBtn = dialog.findViewById(R.id.negativeBtn);
            pBtn = dialog.findViewById(R.id.positiveBtn);


            title1.setText(title);
            message1.setText(message);
            if (positiveBtnText != null)
                pBtn.setText(positiveBtnText);
            if (negativeBtnText != null)
                nBtn.setText(negativeBtnText);
            if (pBtnColor != null) {
                GradientDrawable bgShape = (GradientDrawable) pBtn.getBackground();
                bgShape.setColor(Color.parseColor(pBtnColor));
            }
            if (nBtnColor != null) {
                GradientDrawable bgShape = (GradientDrawable) nBtn.getBackground();
                bgShape.setColor(Color.parseColor(nBtnColor));
            }
            if (pListener != null) {
                pBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pListener.OnClick();
                        dialog.dismiss();
                    }
                });
            } else {
                pBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }

            if (nListener != null) {
                nBtn.setVisibility(View.VISIBLE);
                nBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nListener.OnClick();

                        dialog.dismiss();
                    }
                });
            }
            dialog.show();
            return new DialogPoP(this);
        }


    }
}
