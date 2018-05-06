package de.avkterwey.popularmovies.databinding;
import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class ActivityDetailBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.detail_thumb, 1);
        sViewsWithIds.put(R.id.detail_title, 2);
        sViewsWithIds.put(R.id.detail_releaseDate, 3);
        sViewsWithIds.put(R.id.textlabeldate, 4);
        sViewsWithIds.put(R.id.detail_rating, 5);
        sViewsWithIds.put(R.id.textlabelrating, 6);
        sViewsWithIds.put(R.id.youtube_fragment, 7);
        sViewsWithIds.put(R.id.trailers_label, 8);
        sViewsWithIds.put(R.id.trailers_recyclerview, 9);
        sViewsWithIds.put(R.id.overview_label, 10);
        sViewsWithIds.put(R.id.detail_overview, 11);
        sViewsWithIds.put(R.id.reviews_label, 12);
        sViewsWithIds.put(R.id.reviews_recyclerview, 13);
        sViewsWithIds.put(R.id.fab_detail, 14);
    }
    // views
    @NonNull
    public final android.widget.TextView detailOverview;
    @NonNull
    public final android.widget.TextView detailRating;
    @NonNull
    public final android.widget.TextView detailReleaseDate;
    @NonNull
    public final android.widget.ImageView detailThumb;
    @NonNull
    public final android.widget.TextView detailTitle;
    @NonNull
    public final android.support.design.widget.FloatingActionButton fabDetail;
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    public final android.widget.TextView overviewLabel;
    @NonNull
    public final android.widget.TextView reviewsLabel;
    @NonNull
    public final android.support.v7.widget.RecyclerView reviewsRecyclerview;
    @NonNull
    public final android.widget.TextView textlabeldate;
    @NonNull
    public final android.widget.TextView textlabelrating;
    @NonNull
    public final android.widget.TextView trailersLabel;
    @NonNull
    public final android.support.v7.widget.RecyclerView trailersRecyclerview;
    @NonNull
    public final android.widget.FrameLayout youtubeFragment;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.detailOverview = (android.widget.TextView) bindings[11];
        this.detailRating = (android.widget.TextView) bindings[5];
        this.detailReleaseDate = (android.widget.TextView) bindings[3];
        this.detailThumb = (android.widget.ImageView) bindings[1];
        this.detailTitle = (android.widget.TextView) bindings[2];
        this.fabDetail = (android.support.design.widget.FloatingActionButton) bindings[14];
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.overviewLabel = (android.widget.TextView) bindings[10];
        this.reviewsLabel = (android.widget.TextView) bindings[12];
        this.reviewsRecyclerview = (android.support.v7.widget.RecyclerView) bindings[13];
        this.textlabeldate = (android.widget.TextView) bindings[4];
        this.textlabelrating = (android.widget.TextView) bindings[6];
        this.trailersLabel = (android.widget.TextView) bindings[8];
        this.trailersRecyclerview = (android.support.v7.widget.RecyclerView) bindings[9];
        this.youtubeFragment = (android.widget.FrameLayout) bindings[7];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    @NonNull
    public static ActivityDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDetailBinding>inflate(inflater, de.avkterwey.popularmovies.R.layout.activity_detail, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static ActivityDetailBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(de.avkterwey.popularmovies.R.layout.activity_detail, null, false), bindingComponent);
    }
    @NonNull
    public static ActivityDetailBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static ActivityDetailBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}