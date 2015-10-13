package de.xants.triitus.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.xants.triitus.R;
import de.xants.triitus.activities.ActivityNippelDetail;
import de.xants.triitus.content.CM;
import de.xants.triitus.model.Nippel;
import de.xants.triitus.otto.ActivityEvent;
import de.xants.triitus.viewholder.LayoutViewHolder;

/**
 * Created by Don on 10.10.2015.
 */
final class NippelViewHolder extends LayoutViewHolder implements View.OnClickListener {

    private Nippel mNippel = null;
    private TextView mTxtTitle;
    private ImageView mIvCover;

    public NippelViewHolder(ViewGroup viewGroup) {
        super(viewGroup, R.layout.vh_nippel);
        this.mTxtTitle = (TextView) this.itemView.findViewById(R.id.txtTitle);
        this.mIvCover = (ImageView) this.itemView.findViewById(R.id.ivCover);
        this.itemView.setOnClickListener(this);
    }

    public void setData(@NonNull Nippel nippel) {
        this.mNippel = nippel;
        this.mTxtTitle.setText(nippel.getTitle());
        CM.PICASSO().cancelRequest(this.mIvCover);
        CM.PICASSO().load(nippel.getImage()).fit().into(this.mIvCover);
    }

    @Override
    public void onClick(View v) {
        if (v == this.itemView) {
            Log.d("test", "test");
            Bundle bundle = new Bundle();
            bundle.putString("id", this.mNippel.getId());
            CM.BUS().post(ActivityEvent
                    .create(ActivityNippelDetail.class,
                            bundle,
                            Pair.create((View) this.mIvCover, "cover"),
                            Pair.create((View) this.mTxtTitle, "title")));
        }
    }
}
