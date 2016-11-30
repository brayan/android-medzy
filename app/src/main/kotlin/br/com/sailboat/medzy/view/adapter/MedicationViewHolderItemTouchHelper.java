package br.com.sailboat.medzy.view.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import br.com.sailboat.medzy.view.adapter.view_holder.MedicationViewHolder;


public class MedicationViewHolderItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private MedicationViewHolderItemTouchHelper.Callback callback;

    private Context context;

    private Paint paint;

    public MedicationViewHolderItemTouchHelper(Context context, Callback callback) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        setContext(context);
        setCallback(callback);

        paint = new Paint();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            View itemView = viewHolder.itemView;

            paint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));

            if (isSwipeDireita(dX)) {

                float rightValue = (float) itemView.getRight();

                if ((itemView.getLeft() + dX) < rightValue) {
                    rightValue = itemView.getLeft() + dX;
                }

                c.drawRect((float) itemView.getLeft(),
                        (float) itemView.getTop(),
                        rightValue,
                        (float) itemView.getBottom(), paint);

            } else {

                float leftValue = (float) itemView.getLeft();

                if ((itemView.getRight() + dX) > leftValue) {
                    leftValue = itemView.getRight() + dX;
                }

                c.drawRect(leftValue,
                        (float) itemView.getTop(),
                        (float) itemView.getRight(),
                        (float) itemView.getBottom(), paint);

            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if (isMedsViewHolder(viewHolder)) {
            MedicationViewHolder holder = (MedicationViewHolder) viewHolder;
            getCallback().onSwiped(holder.getAdapterPosition(), direction);
        }

    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (!isMedsViewHolder(viewHolder)) {
            return 0;
        }
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return super.getSwipeEscapeVelocity(defaultValue * 3);
    }

    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return super.getSwipeVelocityThreshold(defaultValue * 2);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private boolean isSwipeDireita(float dX) {
        return dX > 0;
    }

    private boolean isMedsViewHolder(RecyclerView.ViewHolder viewHolder) {
        return viewHolder instanceof MedicationViewHolder;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onSwiped(int adapterPosition, int direction);
    }

}
