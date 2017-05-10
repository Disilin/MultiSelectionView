package com.example.disilin.selection.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.disilin.selection.R;
import com.example.disilin.selection.info.SubSelectionInfo;

import java.util.List;

/**
 *
 * Created by disilin on 2017/5/10.
 */
public class SelectionDialog {
    private static Dialog dialog = null;

    public static void cancel(){
        if (dialog != null){
            dialog.cancel();
        }
    }

    public static void setDialog(Context context, List<SubSelectionInfo> subs, SelectionView.OnMenuItemClickListener listener){
        dialog = new Dialog(context, R.style.Dialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog, null);
        dialog.setContentView(layout);
        SelectionView selectionView = (SelectionView) dialog.findViewById(R.id.selection_view);
        ImageView close = (ImageView) dialog.findViewById(R.id.iv_close);

        selectionView.initDatas(subs, listener);
        ViewGroup.LayoutParams layoutParams = selectionView.getLayoutParams();
        layoutParams.height = selectionView.getRelativeHeitht(subs.size());
        selectionView.setLayoutParams(layoutParams);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        selectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dialog.onWindowAttributesChanged(lp);
        dialog.setCanceledOnTouchOutside(true);

        dialog.show();
    }
}
