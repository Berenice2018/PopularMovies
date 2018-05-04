package de.avkterwey.popularmovies.databinding;
import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class TrailerItemBinding extends android.databinding.ViewDataBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.youtubeThumbImageView, 1);
    }
    // views
    @NonNull
    public final android.support.v7.widget.CardView trailerCardview;
    @NonNull
    public final android.widget.ImageView youtubeThumbImageView;
    // variables
    @Nullable
    private de.avkterwey.popularmovies.data.model.TrailerItem mTrailer;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TrailerItemBinding(@NonNull android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.trailerCardview = (android.support.v7.widget.CardView) bindings[0];
        this.trailerCardview.setTag(null);
        this.youtubeThumbImageView = (android.widget.ImageView) bindings[1];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.trailer == variableId) {
            setTrailer((de.avkterwey.popularmovies.data.model.TrailerItem) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTrailer(@Nullable de.avkterwey.popularmovies.data.model.TrailerItem Trailer) {
        this.mTrailer = Trailer;
    }
    @Nullable
    public de.avkterwey.popularmovies.data.model.TrailerItem getTrailer() {
        return mTrailer;
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
    public static TrailerItemBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static TrailerItemBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup root, boolean attachToRoot, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<TrailerItemBinding>inflate(inflater, de.avkterwey.popularmovies.R.layout.trailer_item, root, attachToRoot, bindingComponent);
    }
    @NonNull
    public static TrailerItemBinding inflate(@NonNull android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static TrailerItemBinding inflate(@NonNull android.view.LayoutInflater inflater, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(de.avkterwey.popularmovies.R.layout.trailer_item, null, false), bindingComponent);
    }
    @NonNull
    public static TrailerItemBinding bind(@NonNull android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    @NonNull
    public static TrailerItemBinding bind(@NonNull android.view.View view, @Nullable android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/trailer_item_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new TrailerItemBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): trailer
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}